package org.acreetionos.launcher.ui.preferences.destinations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.acreetionos.launcher.backup.ui.restoreBackupOpener
import org.acreetionos.launcher.backup.ui.restoreNovaBackupOpener
import org.acreetionos.launcher.ui.preferences.LocalIsExpandedScreen
import org.acreetionos.launcher.ui.preferences.components.NavigationActionPreference
import org.acreetionos.launcher.ui.preferences.components.controls.ClickablePreference
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceGroup
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceLayout
import org.acreetionos.launcher.ui.preferences.navigation.CreateBackup
import com.android.launcher3.R

@Composable
fun BackupAndRestorePreference(
    modifier: Modifier = Modifier,
) {
    PreferenceLayout(
        label = stringResource(R.string.backup_and_restore_label),
        backArrowVisible = !LocalIsExpandedScreen.current,
        modifier = modifier,
    ) {
        PreferenceGroup {
            NavigationActionPreference(
                label = stringResource(R.string.create_backup),
                subtitle = stringResource(R.string.create_backup_description),
                destination = CreateBackup,
            )
            ClickablePreference(
                label = stringResource(R.string.restore_backup),
                subtitle = stringResource(R.string.restore_backup_description),
                onClick = restoreBackupOpener(),
            )
        }
        PreferenceGroup {
            ClickablePreference(
                label = stringResource(R.string.restore_nova_backup),
                subtitle = stringResource(R.string.restore_nova_backup_description),
                onClick = restoreNovaBackupOpener(),
            )
        }
    }
}
