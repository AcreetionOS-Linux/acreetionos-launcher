/*
 * Copyright 2026, AcreetionOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */

package org.acreetionos.launcher.ui.preferences.destinations


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

// Brand palette
private val AcreetionGreen = Color(0xFF2ECC71)
private val AcreetionPurple = Color(0xFF9B59B6)
private val AcreetionDark = Color(0xFF0D1117)
private val AcreetionCard = Color(0xFF161B22)
private val AcreetionBorder = Color(0xFF30363D)
private val AcreetionMuted = Color(0xFF8B949E)

/**
 * AcreetionOS Settings Dashboard — completely rebuilt for sovereignty & control
 */
@Composable
fun AcreetionSettingsDashboard(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {},
) {
    val prefs2 = preferenceManager2()

    PreferenceLayout(
        label = "AcreetionOS Settings",
        modifier = modifier,
        backArrowVisible = !LocalIsExpandedScreen.current,
    ) {
        // ─── Header Identity Banner ───────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(AcreetionCard)
                .border(1.dp, AcreetionBorder, RoundedCornerShape(16.dp))
                .padding(20.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(AcreetionGreen.copy(alpha = 0.15f))
                        .border(1.5.dp, AcreetionGreen.copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Security,
                        contentDescription = null,
                        tint = AcreetionGreen,
                        modifier = Modifier.size(28.dp),
                    )
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        "AcreetionOS Launcher",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White,
                    )
                    Text(
                        "Sovereign · Zero-Telemetry · LineageOS",
                        fontSize = 12.sp,
                        color = AcreetionGreen,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }

        Spacer(Modifier.height(4.dp))

        // ─── Section: Identity & Sovereignty ────────────────────────────────
        AcreetionSettingsSection(title = "Identity & Sovereignty", icon = Icons.Rounded.Shield) {
            AcreetionSettingsTile(
                icon = Icons.Rounded.VerifiedUser,
                iconTint = AcreetionGreen,
                title = "Privacy Shield",
                subtitle = "Zero telemetry, query sandbox, clipboard guard",
                onClick = { onNavigate("privacy_shield") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.Fingerprint,
                iconTint = AcreetionGreen,
                title = "De-Googled Core",
                subtitle = "No Google/Microsoft tracking services whatsoever",
                onClick = { onNavigate("degoogled") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.VpnKey,
                iconTint = AcreetionGreen,
                title = "On-Device VPN Sandbox",
                subtitle = "Search proxied through local loopback (127.0.0.1:9050)",
                onClick = { onNavigate("vpn_sandbox") },
            )
        }

        // ─── Section: Appearance ─────────────────────────────────────────────
        AcreetionSettingsSection(title = "Appearance", icon = Icons.Rounded.Palette) {
            AcreetionSettingsTile(
                icon = Icons.Rounded.ColorLens,
                iconTint = AcreetionPurple,
                title = "Brand Theme & Colors",
                subtitle = "Acreetion Green, Storm Blue, Obsidian Dark & more",
                onClick = { onNavigate("theme_colors") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.Apps,
                iconTint = AcreetionPurple,
                title = "Icon Customization",
                subtitle = "Icon packs, shapes, size & label styling",
                onClick = { onNavigate("icons") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.FontDownload,
                iconTint = AcreetionPurple,
                title = "Typography",
                subtitle = "Font family, weight and scale",
                onClick = { onNavigate("typography") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.Wallpaper,
                iconTint = AcreetionPurple,
                title = "Wallpaper & Blur",
                subtitle = "Home screen blur, wallpaper scroll parallax",
                onClick = { onNavigate("wallpaper") },
            )
        }

        // ─── Section: Home & Navigation ──────────────────────────────────────
        AcreetionSettingsSection(title = "Home & Navigation", icon = Icons.Rounded.Home) {
            AcreetionSettingsTile(
                icon = Icons.Rounded.GridView,
                iconTint = AcreetionGreen,
                title = "Home Screen Layout",
                subtitle = "Grid columns, icon spacing, desktop density",
                onClick = { onNavigate("home_screen") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.DragHandle,
                iconTint = AcreetionGreen,
                title = "Dock & Hotseat",
                subtitle = "Bottom bar apps, shape, and transparency",
                onClick = { onNavigate("dock") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.SwipeUp,
                iconTint = AcreetionGreen,
                title = "Swipe & Gesture Controls",
                subtitle = "Swipe-up, swipe-down, double-tap, long-press",
                onClick = { onNavigate("gestures") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.TouchApp,
                iconTint = AcreetionGreen,
                title = "Quick Panel Customization",
                subtitle = "Swipe-down tiles, layout & AcreetionOS overlays",
                onClick = { onNavigate("quick_panel") },
            )
        }

        // ─── Section: Search & AI ────────────────────────────────────────────
        AcreetionSettingsSection(title = "Search & Privacy Engine", icon = Icons.Rounded.Search) {
            AcreetionSettingsTile(
                icon = Icons.Rounded.ManageSearch,
                iconTint = Color(0xFF61AFEF),
                title = "Search Engine",
                subtitle = "Default: Qwant — change to DDG, Brave, Kagi, StartPage",
                onClick = { onNavigate("search") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.FilterAlt,
                iconTint = Color(0xFF61AFEF),
                title = "Search Result Filters",
                subtitle = "Control apps, files, contacts, settings in results",
                onClick = { onNavigate("search_filters") },
            )
        }

        // ─── Section: Community & Feeds ──────────────────────────────────────
        AcreetionSettingsSection(title = "Community & Feeds", icon = Icons.Rounded.Forum) {
            AcreetionSettingsTile(
                icon = Icons.Rounded.RssFeed,
                iconTint = Color(0xFFF39C12),
                title = "Live News Feed",
                subtitle = "Arch Linux & AcreetionOS RSS in swipe panel",
                onClick = { onNavigate("acreetion_feed") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.Chat,
                iconTint = Color(0xFF7289DA),
                title = "Discord Community Widget",
                subtitle = "Live AcreetionOS Discord overlay integration",
                onClick = { onNavigate("discord_widget") },
            )
        }

        // ─── Section: System ─────────────────────────────────────────────────
        AcreetionSettingsSection(title = "System & LineageOS", icon = Icons.Rounded.Settings) {
            AcreetionSettingsTile(
                icon = Icons.Rounded.SystemUpdate,
                iconTint = AcreetionMuted,
                title = "Backup & Restore",
                subtitle = "Export and import full launcher configuration",
                onClick = { onNavigate("backup") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.BugReport,
                iconTint = AcreetionMuted,
                title = "Developer & Debug",
                subtitle = "Feature flags, experimental options",
                onClick = { onNavigate("debug") },
            )
            AcreetionSettingsTile(
                icon = Icons.Rounded.Info,
                iconTint = AcreetionMuted,
                title = "About AcreetionOS",
                subtitle = "Version, team, links & open source licenses",
                onClick = { onNavigate("about") },
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun AcreetionSettingsSection(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AcreetionMuted,
                modifier = Modifier.size(16.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = title.uppercase(),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp,
                color = AcreetionMuted,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(AcreetionCard)
                .border(1.dp, AcreetionBorder, RoundedCornerShape(16.dp)),
        ) {
            content()
        }
    }
}

@Composable
private fun AcreetionSettingsTile(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconTint.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(22.dp),
            )
        }
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFF3F4F6),
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = AcreetionMuted,
                lineHeight = 16.sp,
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
            contentDescription = null,
            tint = AcreetionBorder,
            modifier = Modifier.size(18.dp),
        )
    }
}
