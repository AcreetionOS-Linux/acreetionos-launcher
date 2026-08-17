package org.acreetionos.launcher.search.algorithms.engine.provider.web

import android.net.Uri
import android.util.Log
import org.acreetionos.launcher.search.privacy.AcreetionPrivacySandbox
import org.acreetionos.launcher.util.kotlinxJson
import com.android.launcher3.R
import java.lang.reflect.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Provides web search suggestions from Qwant (Privacy-focused EU engine, default for AcreetionOS).
 */
object QwantWebSearchProvider : WebSearchProvider {
    override val label = R.string.search_provider_qwant
    override val iconRes = R.drawable.ic_search
    override val id: String = "qwant"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.qwant.com/")
            .client(AcreetionPrivacySandbox.createSandboxedHttpClient())
            .addConverterFactory(StringConverterFactory.create())
            .build()
    }

    private val service: QwantService by lazy {
        retrofit.create(QwantService::class.java)
    }

    private interface QwantService {
        @GET("v3/suggest")
        suspend fun getSuggestions(
            @Query("q") query: String,
            @Query("client") client: String = "opensearch",
        ): Response<String>
    }

    override fun getSuggestions(query: String): Flow<List<String>> = flow {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        if (sanitized.isBlank()) {
            emit(emptyList())
            return@flow
        }
        try {
            val encodedQuery = Uri.encode(sanitized)
            val response = service.getSuggestions(query = encodedQuery)
            if (response.isSuccessful) {
                val responseBody = response.body() ?: ""
                val jsonArray = JSONArray(responseBody)
                val suggestionsArray = jsonArray.getJSONArray(1)
                val results = (0 until suggestionsArray.length()).map { suggestionsArray.getString(it) }
                emit(results)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override fun getSearchUrl(query: String): String {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        val encodedQuery = Uri.encode(sanitized)
        return "https://www.qwant.com/?q=$encodedQuery&t=web"
    }

    override fun toString(): String = id
}

/**
 * Provides web search suggestions from DuckDuckGo.
 */
object DuckDuckGoWebSearchProvider : WebSearchProvider {
    override val label = R.string.search_provider_duckduckgo
    override val iconRes = R.drawable.ic_duckduckgo
    override val id: String = "duckduckgo"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://ac.duckduckgo.com/")
            .client(AcreetionPrivacySandbox.createSandboxedHttpClient())
            .addConverterFactory(kotlinxJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val service: DuckDuckGoService by lazy {
        retrofit.create(DuckDuckGoService::class.java)
    }

    private interface DuckDuckGoService {
        @GET("ac/")
        suspend fun getSuggestions(
            @Query("q") query: String,
            @Query("type") type: String = "json",
        ): Response<ResponseBody>
    }

    override fun getSuggestions(query: String): Flow<List<String>> = flow {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        if (sanitized.isBlank()) {
            emit(emptyList())
            return@flow
        }

        try {
            val encodedQuery = Uri.encode(sanitized)
            val response = service.getSuggestions(query = encodedQuery)

            if (response.isSuccessful) {
                val responseBody = response.body()?.string() ?: ""
                val suggestions = parseDuckDuckGoResponse(responseBody)
                emit(suggestions)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override fun getSearchUrl(query: String): String {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        val encodedQuery = Uri.encode(sanitized)
        return "https://duckduckgo.com/?q=$encodedQuery"
    }

    private fun parseDuckDuckGoResponse(responseBody: String): List<String> {
        return try {
            val jsonElement = Json.parseToJsonElement(responseBody)
            jsonElement.jsonArray.map { it.jsonObject["phrase"]!!.jsonPrimitive.content }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun toString(): String = id
}

/**
 * Provides web search from Brave Search (Privacy-focused engine).
 */
object BraveWebSearchProvider : WebSearchProvider {
    override val label = R.string.search_provider_brave
    override val iconRes = R.drawable.ic_search
    override val id: String = "brave"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://search.brave.com/")
            .client(AcreetionPrivacySandbox.createSandboxedHttpClient())
            .addConverterFactory(StringConverterFactory.create())
            .build()
    }

    private val service: BraveService by lazy {
        retrofit.create(BraveService::class.java)
    }

    private interface BraveService {
        @GET("api/suggest")
        suspend fun getSuggestions(
            @Query("q") query: String,
        ): Response<String>
    }

    override fun getSuggestions(query: String): Flow<List<String>> = flow {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        if (sanitized.isBlank()) {
            emit(emptyList())
            return@flow
        }
        try {
            val encodedQuery = Uri.encode(sanitized)
            val response = service.getSuggestions(query = encodedQuery)
            if (response.isSuccessful) {
                val responseBody = response.body() ?: ""
                val jsonArray = JSONArray(responseBody)
                val suggestionsArray = jsonArray.getJSONArray(1)
                val results = (0 until suggestionsArray.length()).map { suggestionsArray.getString(it) }
                emit(results)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override fun getSearchUrl(query: String): String {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        val encodedQuery = Uri.encode(sanitized)
        return "https://search.brave.com/search?q=$encodedQuery"
    }

    override fun toString(): String = id
}

/**
 * Provides web search suggestions from StartPage.
 */
object StartPageWebSearchProvider : WebSearchProvider {
    override val label = R.string.search_provider_startpage
    override val iconRes = R.drawable.ic_startpage
    override val id: String = "startpage"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.startpage.com/")
            .client(AcreetionPrivacySandbox.createSandboxedHttpClient())
            .addConverterFactory(StringConverterFactory.create())
            .build()
    }

    private val service: StartPageService by lazy {
        retrofit.create(StartPageService::class.java)
    }

    private interface StartPageService {
        @GET("suggestions")
        suspend fun getSuggestions(
            @Query("q") query: String,
            @Query("format") format: String = "opensearch",
        ): Response<String>
    }

    override fun getSuggestions(query: String): Flow<List<String>> = flow {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        if (sanitized.isBlank()) {
            emit(emptyList())
            return@flow
        }

        try {
            val encodedQuery = Uri.encode(sanitized)
            val response = service.getSuggestions(query = encodedQuery)

            if (response.isSuccessful) {
                val responseBody = response.body() ?: ""
                val suggestions = parseStartPageResponse(responseBody)
                emit(suggestions)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override fun getSearchUrl(query: String): String {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        val encodedQuery = Uri.encode(sanitized)
        return "https://www.startpage.com/sp/search?query=$encodedQuery"
    }

    private fun parseStartPageResponse(responseBody: String): List<String> {
        return try {
            val jsonArray = JSONArray(responseBody)
            val suggestionsArray = jsonArray.getJSONArray(1)
            (0 until suggestionsArray.length()).map { suggestionsArray.getString(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun toString(): String = id
}

/**
 * Provides web search suggestions from StartPage EU.
 */
object StartPageEUWebSearchProvider : WebSearchProvider {
    override val label = R.string.search_provider_startpage_eu
    override val iconRes = R.drawable.ic_startpage
    override val id: String = "startpage-eu"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://eu.startpage.com/")
            .client(AcreetionPrivacySandbox.createSandboxedHttpClient())
            .addConverterFactory(StringConverterFactory.create())
            .build()
    }

    private val service: StartPageEUService by lazy {
        retrofit.create(StartPageEUService::class.java)
    }

    private interface StartPageEUService {
        @GET("suggestions")
        suspend fun getSuggestions(
            @Query("q") query: String,
            @Query("format") format: String = "opensearch",
        ): Response<String>
    }

    override fun getSuggestions(query: String): Flow<List<String>> = flow {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        if (sanitized.isBlank()) {
            emit(emptyList())
            return@flow
        }

        try {
            val encodedQuery = Uri.encode(sanitized)
            val response = service.getSuggestions(query = encodedQuery)

            if (response.isSuccessful) {
                val responseBody = response.body() ?: ""
                val suggestions = parseStartPageResponse(responseBody)
                emit(suggestions)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override fun getSearchUrl(query: String): String {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        val encodedQuery = Uri.encode(sanitized)
        return "https://eu.startpage.com/sp/search?query=$encodedQuery"
    }

    private fun parseStartPageResponse(responseBody: String): List<String> {
        return try {
            val jsonArray = JSONArray(responseBody)
            val suggestionsArray = jsonArray.getJSONArray(1)
            (0 until suggestionsArray.length()).map { suggestionsArray.getString(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun toString(): String = id
}

/**
 * Provides web search suggestions from Kagi.
 */
object KagiWebSearchProvider : WebSearchProvider {
    override val label = R.string.search_provider_kagi
    override val iconRes = R.drawable.ic_kagi
    override val id: String = "kagi"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://kagi.com/")
            .client(AcreetionPrivacySandbox.createSandboxedHttpClient())
            .addConverterFactory(StringConverterFactory.create())
            .build()
    }

    private val service: KagiService by lazy {
        retrofit.create(KagiService::class.java)
    }

    private interface KagiService {
        @GET("api/autosuggest")
        suspend fun getSuggestions(
            @Query("q") query: String,
        ): Response<String>
    }

    override fun getSuggestions(query: String): Flow<List<String>> = flow {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        if (sanitized.isBlank()) {
            emit(emptyList())
            return@flow
        }

        try {
            val encodedQuery = Uri.encode(sanitized)
            val response = service.getSuggestions(query = encodedQuery)

            if (response.isSuccessful) {
                val responseBody = response.body() ?: ""
                val suggestions = parseKagiResponse(responseBody)
                emit(suggestions)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override fun getSearchUrl(query: String): String {
        val sanitized = AcreetionPrivacySandbox.sanitizeQuery(query)
        val encodedQuery = Uri.encode(sanitized)
        return "https://kagi.com/search?q=$encodedQuery"
    }

    private fun parseKagiResponse(responseBody: String): List<String> {
        return try {
            val jsonArray = JSONArray(responseBody)
            val suggestionsArray = jsonArray.getJSONArray(1)
            (0 until suggestionsArray.length()).map { suggestionsArray.getString(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun toString(): String = id
}

class StringConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *>? {
        if (type == String::class.java) {
            return Converter<ResponseBody, String> { value -> value.string() }
        }
        return null
    }

    companion object {
        fun create() = StringConverterFactory()
    }
}
