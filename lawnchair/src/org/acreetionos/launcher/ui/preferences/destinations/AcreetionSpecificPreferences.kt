/*
 * Copyright 2026, AcreetionOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package org.acreetionos.launcher.ui.preferences.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.acreetionos.launcher.preferences.getAdapter
import org.acreetionos.launcher.preferences2.preferenceManager2
import org.acreetionos.launcher.ui.preferences.LocalIsExpandedScreen
import org.acreetionos.launcher.ui.preferences.components.controls.SwitchPreference
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceGroup
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceLayout
import com.android.launcher3.R

private val AcreetionGreen = Color(0xFF2ECC71)
private val AcreetionPurple = Color(0xFF9B59B6)
private val StormBlue = Color(0xFF61AFEF)
private val CardBg = Color(0xFF161B22)
private val BorderColor = Color(0xFF30363D)
private val TextMuted = Color(0xFF8B949E)

/**
 * AcreetionOS-specific launcher settings — unique features not found in stock AOSP.
 */
@Composable
fun AcreetionSpecificPreferences(
    modifier: Modifier = Modifier,
) {
    val prefs2 = preferenceManager2()

    PreferenceLayout(
        label = "AcreetionOS Features",
        modifier = modifier,
        backArrowVisible = !LocalIsExpandedScreen.current,
    ) {

        // ─── Sovereignty Info Banner ──────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(AcreetionGreen.copy(alpha = 0.07f))
                .border(1.dp, AcreetionGreen.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                .padding(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Shield,
                    contentDescription = null,
                    tint = AcreetionGreen,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        "AcreetionOS — Built on LineageOS",
                        fontWeight = FontWeight.Bold,
                        color = AcreetionGreen,
                        fontSize = 13.sp,
                    )
                    Text(
                        "These features are exclusive to the AcreetionOS ecosystem.",
                        color = TextMuted,
                        fontSize = 12.sp,
                    )
                }
            }
        }

        // ─── Quick Panel Settings ─────────────────────────────────────────────
        PreferenceGroup(heading = "Quick Panel (Swipe Down)") {
            SwitchPreference(
                adapter = prefs2.acreetionPrivacyShield.getAdapter(),
                label = "Enable AcreetionOS Quick Panel",
                description = "Replaces swipe-down with branded AcreetionOS overlay panel",
            )
            SwitchPreference(
                adapter = prefs2.acreetionVpnSandbox.getAdapter(),
                label = "Show VPN Sandbox Toggle",
                description = "Quick tile to enable/disable on-device SOCKS proxy sandbox",
            )
            SwitchPreference(
                adapter = prefs2.acreetionZeroTelemetry.getAdapter(),
                label = "Show Privacy Shield Tile",
                description = "Privacy Shield quick toggle visible in swipe panel",
            )
        }

        // ─── Search & Privacy ─────────────────────────────────────────────────
        PreferenceGroup(heading = "Search Privacy & Isolation") {
            SwitchPreference(
                adapter = prefs2.acreetionSearchPrivacy.getAdapter(),
                label = "Query De-Identification",
                description = "Strip UTM params, referrers, and tracking tokens before every search",
            )
            SwitchPreference(
                adapter = prefs2.acreetionVpnSandbox.getAdapter(),
                label = "On-Device VPN Proxy Sandbox",
                description = "Route search requests through 127.0.0.1:9050 without host telemetry",
            )
            SwitchPreference(
                adapter = prefs2.acreetionDegoogledCore.getAdapter(),
                label = "Enforce De-Googled Core",
                description = "Block all Google/Microsoft service calls at the launcher level",
            )
        }

        // ─── Live Feed Preferences ────────────────────────────────────────────
        PreferenceGroup(heading = "Arch Linux & AcreetionOS Feed") {
            SwitchPreference(
                adapter = prefs2.acreetionPrivacyShield.getAdapter(),
                label = "Show Live Feed Panel",
                description = "Display Arch Linux + AcreetionOS RSS feed when swiping right",
            )
            SwitchPreference(
                adapter = prefs2.acreetionClipboardGuard.getAdapter(),
                label = "Discord Community Overlay",
                description = "Show AcreetionOS Discord widget in the feed panel sidebar",
            )
        }

        // ─── System & Recents ────────────────────────────────────────────────
        PreferenceGroup(heading = "System Protection") {
            SwitchPreference(
                adapter = prefs2.acreetionRecentsPrivacy.getAdapter(),
                label = "Recents Task Redaction",
                description = "Redact screenshots of sensitive apps in task switcher overview",
            )
            SwitchPreference(
                adapter = prefs2.acreetionClipboardGuard.getAdapter(),
                label = "Clipboard Snooping Guard",
                description = "Block background apps from silently reading the clipboard",
            )
        }

        // ─── Identity & Appearance ────────────────────────────────────────────
        PreferenceGroup(heading = "AcreetionOS Identity") {
            SwitchPreference(
                adapter = prefs2.acreetionPrivacyShield.getAdapter(),
                label = "Show Acreetion Green Defaults",
                description = "Apply AcreetionOS Acreetion Green (#2ECC71) as default accent",
            )
            SwitchPreference(
                adapter = prefs2.acreetionZeroTelemetry.getAdapter(),
                label = "AcreetionOS Boot Animation",
                description = "Use custom Acreetion boot splash when launcher initializes",
            )
        }

        // ─── About & Attribution ─────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CardBg)
                .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
                .padding(16.dp),
        ) {
            Column {
                Text(
                    "AcreetionOS Project",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Co-Lead Developers: Natalie Cole-Clift Spiva & Darren Clift\n" +
                        "Location: Spokane, WA · Built on LineageOS\n" +
                        "Website: acreetionos.org · Discord: discord.acreetionos.org",
                    color = TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                )
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
