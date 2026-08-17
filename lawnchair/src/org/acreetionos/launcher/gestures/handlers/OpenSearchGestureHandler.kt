package org.acreetionos.launcher.gestures.handlers

import android.content.Context
import org.acreetionos.launcher.LawnchairLauncher
import org.acreetionos.launcher.preferences2.PreferenceManager2
import org.acreetionos.launcher.qsb.LawnQsbLayout

class OpenSearchGestureHandler(context: Context) : GestureHandler(context) {

    override suspend fun onTrigger(launcher: LawnchairLauncher) {
        val prefs = PreferenceManager2.getInstance(launcher)
        val searchProvider = LawnQsbLayout.getSearchProvider(launcher, prefs)
        searchProvider.launch(launcher)
    }
}
