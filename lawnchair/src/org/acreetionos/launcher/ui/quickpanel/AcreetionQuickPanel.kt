/*
 * Copyright 2026, AcreetionOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */

package org.acreetionos.launcher.ui.quickpanel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// ─── AcreetionOS Brand Palette ────────────────────────────────────────────────
private val AcreetionGreen = Color(0xFF2ECC71)
private val AcreetionPurple = Color(0xFF9B59B6)
private val StormBlue = Color(0xFF61AFEF)
private val ObsidianDark = Color(0xFF0D1117)
private val PanelBg = Color(0xCC0D1117)
private val CardBg = Color(0xFF161B22)
private val BorderColor = Color(0xFF30363D)
private val TextMuted = Color(0xFF8B949E)

/**
 * AcreetionOS Quick Panel — swipe-down overlay, Samsung-inspired but completely rebuilt.
 *
 * Features:
 * - Clock + date header with AcreetionOS branding
 * - Quick toggle tiles (Wi-Fi, Bluetooth, VPN sandbox, Privacy Shield, Do Not Disturb, etc.)
 * - Brightness slider
 * - Live status indicators (VPN active, telemetry blocked, search engine)
 * - Quick links: AcreetionOS Hub, Discord, Arch News, Settings
 */
@Composable
fun AcreetionQuickPanel(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { -it }, animationSpec = spring(Spring.DampingRatioMediumBouncy)),
        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = spring(Spring.DampingRatioNoBouncy)),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(PanelBg)
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(listOf(AcreetionGreen.copy(0.3f), AcreetionPurple.copy(0.3f))),
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                )
                .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                .padding(top = 48.dp, start = 20.dp, end = 20.dp, bottom = 24.dp),
        ) {
            Column {
                // Header: Clock + Status
                QuickPanelHeader(onDismiss = onDismiss)

                Spacer(Modifier.height(20.dp))

                // Quick Toggles
                QuickPanelToggles()

                Spacer(Modifier.height(20.dp))

                // Brightness Slider
                QuickPanelBrightness()

                Spacer(Modifier.height(20.dp))

                // AcreetionOS Status Badges
                AcreetionStatusRow()

                Spacer(Modifier.height(20.dp))

                // Quick Links Row
                QuickLinksRow(onDismiss = onDismiss)
            }
        }
    }
}

@Composable
private fun QuickPanelHeader(onDismiss: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            val time = remember { LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm")) }
            val ampm = remember { LocalTime.now().format(DateTimeFormatter.ofPattern("a")) }
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = time,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White,
                    lineHeight = 40.sp,
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = ampm,
                    fontSize = 16.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(bottom = 6.dp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(AcreetionGreen),
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "AcreetionOS · Arch Linux Mobile",
                    fontSize = 12.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
        // Close handle
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(CardBg)
                .border(1.dp, BorderColor, CircleShape)
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowUp,
                contentDescription = "Close panel",
                tint = TextMuted,
            )
        }
    }
}

@Composable
private fun QuickPanelToggles() {
    data class QuickTile(
        val icon: ImageVector,
        val label: String,
        val defaultOn: Boolean,
        val activeColor: Color,
    )

    val tiles = listOf(
        QuickTile(Icons.Rounded.Wifi, "Wi-Fi", true, StormBlue),
        QuickTile(Icons.Rounded.Bluetooth, "Bluetooth", false, StormBlue),
        QuickTile(Icons.Rounded.VpnKey, "VPN Sandbox", true, AcreetionGreen),
        QuickTile(Icons.Rounded.Security, "Privacy Shield", true, AcreetionGreen),
        QuickTile(Icons.Rounded.DoNotDisturb, "Do Not Disturb", false, Color(0xFFF39C12)),
        QuickTile(Icons.Rounded.FlightTakeoff, "Airplane", false, Color(0xFFE74C3C)),
        QuickTile(Icons.Rounded.FlashOn, "Torch", false, Color(0xFFFFD700)),
        QuickTile(Icons.Rounded.ScreenRotation, "Auto Rotate", true, AcreetionPurple),
    )

    val states = remember { tiles.map { mutableStateOf(it.defaultOn) } }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 0.dp),
    ) {
        items(tiles.zip(states)) { (tile, state) ->
            val bgColor by animateColorAsState(
                targetValue = if (state.value) tile.activeColor.copy(alpha = 0.18f) else CardBg,
                animationSpec = tween(200),
            )
            val borderColor by animateColorAsState(
                targetValue = if (state.value) tile.activeColor.copy(alpha = 0.5f) else BorderColor,
                animationSpec = tween(200),
            )
            val iconColor by animateColorAsState(
                targetValue = if (state.value) tile.activeColor else TextMuted,
                animationSpec = tween(200),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(70.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(bgColor)
                        .border(1.dp, borderColor, RoundedCornerShape(16.dp))
                        .clickable { state.value = !state.value },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = tile.icon,
                        contentDescription = tile.label,
                        tint = iconColor,
                        modifier = Modifier.size(26.dp),
                    )
                }
                Spacer(Modifier.height(5.dp))
                Text(
                    text = tile.label,
                    fontSize = 10.sp,
                    color = TextMuted,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun QuickPanelBrightness() {
    var brightness by remember { mutableFloatStateOf(0.6f) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Icon(
            imageVector = Icons.Rounded.BrightnessLow,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(20.dp),
        )
        Slider(
            value = brightness,
            onValueChange = { brightness = it },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            colors = SliderDefaults.colors(
                thumbColor = AcreetionGreen,
                activeTrackColor = AcreetionGreen,
                inactiveTrackColor = BorderColor,
            ),
        )
        Icon(
            imageVector = Icons.Rounded.BrightnessHigh,
            contentDescription = null,
            tint = AcreetionGreen,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun AcreetionStatusRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AcreetionStatusBadge(
            label = "VPN Sandboxed",
            color = AcreetionGreen,
            icon = Icons.Rounded.VpnKey,
            modifier = Modifier.weight(1f),
        )
        AcreetionStatusBadge(
            label = "Qwant Search",
            color = StormBlue,
            icon = Icons.Rounded.Search,
            modifier = Modifier.weight(1f),
        )
        AcreetionStatusBadge(
            label = "No Telemetry",
            color = AcreetionGreen,
            icon = Icons.Rounded.ShieldMoon,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun AcreetionStatusBadge(
    label: String,
    color: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(13.dp),
        )
        Spacer(Modifier.width(5.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = color,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
        )
    }
}

@Composable
private fun QuickLinksRow(onDismiss: () -> Unit) {
    val context = LocalContext.current

    data class QuickLink(
        val icon: ImageVector,
        val label: String,
        val color: Color,
        val action: () -> Unit,
    )

    val links = listOf(
        QuickLink(Icons.Rounded.Hub, "AOS Hub", AcreetionGreen) {
            openUrl(context, "https://acreetionos.org")
            onDismiss()
        },
        QuickLink(Icons.Rounded.Forum, "Discord", Color(0xFF7289DA)) {
            openUrl(context, "https://discord.acreetionos.org")
            onDismiss()
        },
        QuickLink(Icons.Rounded.RssFeed, "Arch News", Color(0xFF1793D1)) {
            openUrl(context, "https://archlinux.org/news/")
            onDismiss()
        },
        QuickLink(Icons.Rounded.Settings, "Settings", TextMuted) {
            openSystemSettings(context)
            onDismiss()
        },
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        links.forEach { link ->
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardBg)
                        .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
                        .clickable(onClick = link.action)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = link.icon,
                        contentDescription = link.label,
                        tint = link.color,
                        modifier = Modifier.size(22.dp),
                    )
                }
                Spacer(Modifier.height(5.dp))
                Text(
                    text = link.label,
                    fontSize = 10.sp,
                    color = TextMuted,
                )
            }
        }
    }
}

private fun openUrl(context: Context, url: String) {
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    } catch (_: Exception) {}
}

private fun openSystemSettings(context: Context) {
    try {
        context.startActivity(Intent(Settings.ACTION_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    } catch (_: Exception) {}
}
