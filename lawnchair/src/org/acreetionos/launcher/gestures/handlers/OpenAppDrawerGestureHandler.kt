package org.acreetionos.launcher.gestures.handlers

import android.content.Context
import org.acreetionos.launcher.LawnchairLauncher
import org.acreetionos.launcher.animateToAllApps

class OpenAppDrawerGestureHandler(context: Context) : GestureHandler(context) {

    override suspend fun onTrigger(launcher: LawnchairLauncher) {
        launcher.animateToAllApps()
    }
}
