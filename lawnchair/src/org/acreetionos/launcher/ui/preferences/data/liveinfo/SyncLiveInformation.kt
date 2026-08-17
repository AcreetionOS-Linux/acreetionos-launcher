package org.acreetionos.launcher.ui.preferences.data.liveinfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import org.acreetionos.launcher.preferences2.asState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SyncLiveInformation(
    liveInformationManager: LiveInformationManager = liveInformationManager(),
) {
    val enabled by liveInformationManager.enabled.asState()
    val endpoint by liveInformationManager.endpoint.asState()

    LaunchedEffect(enabled, endpoint) {
        if (enabled) {
            getLiveInformation(endpoint)?.let { liveInformation ->
                liveInformationManager.liveInformation.set(liveInformation)
            }
        }
    }
}
