package org.acreetionos.launcher.gestures.handlers

import android.content.Context
import org.acreetionos.launcher.LawnchairLauncher
import org.acreetionos.launcher.animateToAllApps

class OpenAppSearchGestureHandler(context: Context) : GestureHandler(context) {

    override suspend fun onTrigger(launcher: LawnchairLauncher) {
        val searchUiManager = launcher.appsView.searchUiManager
        searchUiManager.setDirectFocus(true)
        searchUiManager.editText?.showKeyboard()
        launcher.animateToAllApps()
    }
}
