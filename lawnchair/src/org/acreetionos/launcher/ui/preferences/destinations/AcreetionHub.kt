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

package org.acreetionos.launcher.ui.preferences.destinations

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Timeline
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import org.acreetionos.launcher.ui.preferences.LocalIsExpandedScreen
import org.acreetionos.launcher.ui.preferences.components.NavigationActionPreference
import org.acreetionos.launcher.ui.preferences.components.controls.ClickablePreference
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceGroup
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceLayout
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceTemplate
import org.acreetionos.launcher.ui.preferences.navigation.AcreetionFeed
import com.android.launcher3.BuildConfig
import com.android.launcher3.R

@Composable
fun AcreetionHub(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    PreferenceLayout(
        label = stringResource(id = R.string.acreetion_hub_title),
        modifier = modifier,
        backArrowVisible = !LocalIsExpandedScreen.current,
    ) {
        // Hero Mission Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF131B2E),
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E2A47)),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_home_comp),
                        contentDescription = "AcreetionOS",
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "AcreetionOS Ecosystem",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF3F4F6),
                        )
                        Text(
                            text = "System Sovereignty & Privacy First",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF2ECC71),
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "A user-friendly, privacy-focused operating system and mobile environment built on Arch Linux foundations, engineered for absolute stability, repository sovereignty, zero telemetry, and minimal bloat.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB2B2B2),
                    lineHeight = 20.sp,
                )
            }
        }

        // Core Mission Pillars
        PreferenceGroup(
            heading = "Core Mission Pillars",
        ) {
            MissionPillarItem(
                icon = Icons.Rounded.Verified,
                iconTint = Color(0xFF2ECC71),
                title = "Repository & System Sovereignty",
                description = "Independent build pipelines with full user control, predictable updates, and zero corporate tracking.",
            )
            MissionPillarItem(
                icon = Icons.Rounded.Shield,
                iconTint = Color(0xFF61AFEF),
                title = "Privacy First & Zero Telemetry",
                description = "Your data stays on your device. Zero diagnostic tracking, no telemetry beacons, and client-side processing.",
            )
            MissionPillarItem(
                icon = Icons.Rounded.Code,
                iconTint = Color(0xFF9B59B6),
                title = "Minimal Bloat & Pure Performance",
                description = "Every package and feature serves a direct purpose. Clean footprint optimized for everyday reliability.",
            )
        }

        // Ecosystem Portals & Tools
        PreferenceGroup(
            heading = "Ecosystem Portals",
        ) {
            NavigationActionPreference(
                label = "Live News Feed & Discord",
                subtitle = "Arch Linux & AcreetionOS live RSS feeds + Discord community chat",
                destination = AcreetionFeed,
            )
            ClickablePreference(
                label = "AcreetionOS Documentation & Wiki",
                subtitle = "Guides, hardware setups, architecture, and troubleshooting",
                onClick = {
                    openUrl(context, "https://acreetionos.org/wiki.html")
                },
            )
            ClickablePreference(
                label = "Live Git Tracker",
                subtitle = "Real-time monitor of development progress and repository sovereignty",
                onClick = {
                    openUrl(context, "https://acreetionos.org/git-tracker.html")
                },
            )
            ClickablePreference(
                label = "Infrastructure & Service Status",
                subtitle = "Live availability status of mirrors, repos, and community services",
                onClick = {
                    openUrl(context, "https://acreetionos.org/status.html")
                },
            )
            ClickablePreference(
                label = "Official Website",
                subtitle = "acreetionos.org — Central project hub & download mirrors",
                onClick = {
                    openUrl(context, "https://acreetionos.org")
                },
            )
            ClickablePreference(
                label = "Official Discord Community",
                subtitle = "Join our community for real-time support and collaboration",
                onClick = {
                    openUrl(context, "https://discord.gg/VHqQkJASw7")
                },
            )
            ClickablePreference(
                label = "Source Code on GitHub",
                subtitle = "github.com/AcreetionOS-Code — 100% open source software",
                onClick = {
                    openUrl(context, "https://github.com/AcreetionOS-Code")
                },
            )
        }

        // Leadership & Governance
        PreferenceGroup(
            heading = "Project Leadership",
        ) {
            ClickablePreference(
                label = "Natalie Cole-Clift Spiva",
                subtitle = "Co-Lead Developer (Spokane, WA)",
                onClick = {
                    openUrl(context, "https://github.com/spivanatalie64")
                },
            )
            ClickablePreference(
                label = "Darren Clift",
                subtitle = "Co-Lead Developer (Spokane, WA)",
                onClick = {
                    openUrl(context, "https://github.com/darrenclift")
                },
            )
        }
    }
}

@Composable
private fun MissionPillarItem(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    PreferenceTemplate(
        modifier = modifier.padding(vertical = 4.dp),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        description = {
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp,
            )
        },
        startWidget = {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconTint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp),
                )
            }
        },
    )
}

private fun openUrl(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}
