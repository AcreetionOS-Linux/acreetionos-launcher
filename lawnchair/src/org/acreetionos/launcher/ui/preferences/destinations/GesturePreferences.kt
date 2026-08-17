package org.acreetionos.launcher.ui.preferences.destinations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.acreetionos.launcher.gestures.handlers.SleepMode
import org.acreetionos.launcher.preferences.getAdapter
import org.acreetionos.launcher.preferences2.preferenceManager2
import org.acreetionos.launcher.ui.preferences.LocalIsExpandedScreen
import org.acreetionos.launcher.ui.preferences.components.GestureHandlerPreference
import org.acreetionos.launcher.ui.preferences.components.controls.ListPreference
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceGroup
import org.acreetionos.launcher.ui.preferences.components.layout.PreferenceLayout
import com.android.launcher3.R

@Composable
fun GesturePreferences(
    modifier: Modifier = Modifier,
) {
    val prefs = preferenceManager2()
    PreferenceLayout(
        label = stringResource(id = R.string.gestures_label),
        backArrowVisible = !LocalIsExpandedScreen.current,
        modifier = modifier,
    ) {
        PreferenceGroup {
            GestureHandlerPreference(
                adapter = prefs.doubleTapGestureHandler.getAdapter(),
                label = stringResource(id = R.string.gesture_double_tap),
            )
            GestureHandlerPreference(
                adapter = prefs.swipeUpGestureHandler.getAdapter(),
                label = stringResource(id = R.string.gesture_swipe_up),
            )
            GestureHandlerPreference(
                adapter = prefs.swipeDownGestureHandler.getAdapter(),
                label = stringResource(id = R.string.gesture_swipe_down),
            )
            GestureHandlerPreference(
                adapter = prefs.twoFingerSwipeUpGestureHandler.getAdapter(),
                label = stringResource(id = R.string.gesture_two_finger_swipe_up),
            )
            GestureHandlerPreference(
                adapter = prefs.twoFingerSwipeDownGestureHandler.getAdapter(),
                label = stringResource(id = R.string.gesture_two_finger_swipe_down),
            )
            GestureHandlerPreference(
                adapter = prefs.homePressGestureHandler.getAdapter(),
                label = stringResource(id = R.string.gesture_home_tap),
            )
            GestureHandlerPreference(
                adapter = prefs.backPressGestureHandler.getAdapter(),
                label = stringResource(id = R.string.gesture_back_tap),
            )
        }
        PreferenceGroup(heading = stringResource(id = R.string.sleep_mode_label)) {
            ListPreference(
                adapter = prefs.sleepMode.getAdapter(),
                entries = SleepMode.entries(),
                label = stringResource(id = R.string.sleep_mode_label),
            )
        }
    }
}
