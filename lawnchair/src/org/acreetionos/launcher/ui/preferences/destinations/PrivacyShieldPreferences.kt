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

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.acreetionos.launcher.preferences2.preferenceManager2
import org.acreetionos.launcher.preferences.getAdapter
import org.acreetionos.launcher.ui.preferences.LocalIsExpandedScreen
import org.acreetionos.launcher.ui.preferences.components.controls.MainSwitchPreference
import org.acreetionos.launcher.ui.preferences.components.controls.SwitchPreference
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceGroup
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceLayout
import com.android.launcher3.R

@Composable
fun PrivacyShieldPreferences(
    modifier: Modifier = Modifier,
) {
    val prefs2 = preferenceManager2()
    val shieldAdapter = prefs2.acreetionPrivacyShield.getAdapter()
    val zeroTelemetryAdapter = prefs2.acreetionZeroTelemetry.getAdapter()
    val searchPrivacyAdapter = prefs2.acreetionSearchPrivacy.getAdapter()
    val recentsPrivacyAdapter = prefs2.acreetionRecentsPrivacy.getAdapter()
    val clipboardGuardAdapter = prefs2.acreetionClipboardGuard.getAdapter()

    PreferenceLayout(
        label = stringResource(id = R.string.acreetion_privacy_shield_title),
        modifier = modifier,
        backArrowVisible = !LocalIsExpandedScreen.current,
    ) {
        // Status Badge Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (shieldAdapter.state.value) Color(0xFF0F291E) else Color(0xFF221A1A),
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (shieldAdapter.state.value) Color(0xFF1E5E41) else Color(0xFF4A2828),
            ),
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            if (shieldAdapter.state.value) Color(0xFF2ECC71).copy(alpha = 0.2f)
                            else Color(0xFFE74C3C).copy(alpha = 0.2f),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (shieldAdapter.state.value) Icons.Rounded.VerifiedUser else Icons.Rounded.Shield,
                        contentDescription = null,
                        tint = if (shieldAdapter.state.value) Color(0xFF2ECC71) else Color(0xFFE74C3C),
                        modifier = Modifier.size(28.dp),
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = if (shieldAdapter.state.value) "Privacy Shield Active" else "Privacy Shield Paused",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF3F4F6),
                    )
                    Text(
                        text = if (shieldAdapter.state.value) "Zero telemetry & tracking blocked" else "Some privacy guardrails are relaxed",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (shieldAdapter.state.value) Color(0xFF2ECC71) else Color(0xFFE74C3C),
                    )
                }
            }
        }

        PreferenceGroup {
            MainSwitchPreference(
                adapter = shieldAdapter,
                label = stringResource(id = R.string.acreetion_privacy_shield_title),
                description = stringResource(id = R.string.acreetion_privacy_shield_desc),
            ) {}
        }

        PreferenceGroup(
            heading = "Telemetry & Search Protection",
        ) {
            SwitchPreference(
                adapter = zeroTelemetryAdapter,
                label = stringResource(id = R.string.acreetion_zero_telemetry_title),
                description = stringResource(id = R.string.acreetion_zero_telemetry_desc),
                enabled = shieldAdapter.state.value,
            )
            SwitchPreference(
                adapter = searchPrivacyAdapter,
                label = stringResource(id = R.string.acreetion_search_privacy_title),
                description = stringResource(id = R.string.acreetion_search_privacy_desc),
                enabled = shieldAdapter.state.value,
            )
            SwitchPreference(
                adapter = prefs2.acreetionVpnSandbox.getAdapter(),
                label = stringResource(id = R.string.acreetion_vpn_sandbox_title),
                description = stringResource(id = R.string.acreetion_vpn_sandbox_desc),
                enabled = shieldAdapter.state.value,
            )
            SwitchPreference(
                adapter = prefs2.acreetionDegoogledCore.getAdapter(),
                label = stringResource(id = R.string.acreetion_degoogled_core_title),
                description = stringResource(id = R.string.acreetion_degoogled_core_desc),
                enabled = shieldAdapter.state.value,
            )
        }

        PreferenceGroup(
            heading = "System & Recents Guard",
        ) {
            SwitchPreference(
                adapter = recentsPrivacyAdapter,
                label = stringResource(id = R.string.acreetion_recents_privacy_title),
                description = stringResource(id = R.string.acreetion_recents_privacy_desc),
                enabled = shieldAdapter.state.value,
            )
            SwitchPreference(
                adapter = clipboardGuardAdapter,
                label = stringResource(id = R.string.acreetion_clipboard_guard_title),
                description = stringResource(id = R.string.acreetion_clipboard_guard_desc),
                enabled = shieldAdapter.state.value,
            )
        }

        // Privacy Guarantee Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            ),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "AcreetionOS Privacy Guarantee",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "AcreetionOS does not collect, transmit, or monetize search queries, app usage habits, device serial numbers, or telemetry data. All computations remain strictly on-device.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    lineHeight = 16.sp,
                )
            }
        }
    }
}
