package org.acreetionos.launcher.smartspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import org.acreetionos.launcher.ui.preferences.PreferenceActivity
import org.acreetionos.launcher.ui.preferences.navigation.SmartspaceWidget

class SmartspacePreferencesShortcut : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(PreferenceActivity.createIntent(this, SmartspaceWidget))
        finish()
    }
}
