/*
 * Copyright 2026, AcreetionOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acreetionos.launcher.search.privacy

import android.net.Uri
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * On-device Privacy Sandbox & Local VPN/Proxy Router for AcreetionOS.
 *
 * Enforces zero-telemetry, strips identifier headers, prevents cross-session tracking,
 * and routes search requests through isolated on-device network sockets.
 */
object AcreetionPrivacySandbox {

    private const val TAG = "AcreetionPrivacySandbox"
    private const val PRIVACY_USER_AGENT = "Mozilla/5.0 (Android; AcreetionOS-Mobile; Zero-Telemetry; Linux x86_64; rv:128.0) Gecko/20100101 Firefox/128.0"

    var isVpnProxyEnabled: Boolean = false
    var proxyHost: String = "127.0.0.1"
    var proxyPort: Int = 9050 // Default local Tor/SOCKS or Privoxy HTTP port

    /**
     * Creates an isolated OkHttpClient configured strictly for anonymous, de-identified queries.
     */
    fun createSandboxedHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(PrivacyHeaderInterceptor())

        if (isVpnProxyEnabled) {
            try {
                val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(proxyHost, proxyPort))
                builder.proxy(proxy)
                Log.d(TAG, "Search queries sandboxed through on-device proxy $proxyHost:$proxyPort")
            } catch (e: Exception) {
                Log.w(TAG, "Failed to bind proxy socket, falling back to direct secure TLS", e)
            }
        }

        return builder.build()
    }

    /**
     * Strips query parameters containing tracking beacons, user tokens, and referrers.
     */
    fun sanitizeQuery(query: String): String {
        return query.trim()
            .replace(Regex("(?i)\\b(utm_[a-z]+|gclid|fbclid|msclkid|mc_eid|ref_src)=[^&]*"), "")
            .replace(Regex("&+"), "&")
            .trimEnd('&', '?')
    }

    /**
     * Interceptor that strips all telemetry, device fingerprints, and corporate tracking headers.
     */
    private class PrivacyHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val cleanRequestBuilder = originalRequest.newBuilder()
                .header("User-Agent", PRIVACY_USER_AGENT)
                .header("DNT", "1")
                .header("Sec-GPC", "1") // Global Privacy Control
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("Accept-Encoding", "gzip, deflate, br")
                .removeHeader("X-Requested-With")
                .removeHeader("Sec-Ch-Ua")
                .removeHeader("Sec-Ch-Ua-Mobile")
                .removeHeader("Sec-Ch-Ua-Platform")
                .removeHeader("X-Goog-Api-Key")
                .removeHeader("X-Android-Package")
                .removeHeader("X-Android-Cert")
                .removeHeader("Cookie") // Zero persistent tracking cookies

            return chain.proceed(cleanRequestBuilder.build())
        }
    }
}
