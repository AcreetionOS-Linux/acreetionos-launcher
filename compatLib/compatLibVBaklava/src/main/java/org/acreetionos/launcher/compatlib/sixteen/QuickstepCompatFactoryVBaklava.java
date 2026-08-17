package org.acreetionos.launcher.compatlib.sixteen;

import android.window.RemoteTransition;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import org.acreetionos.launcher.compatlib.ActivityManagerCompat;
import org.acreetionos.launcher.compatlib.ActivityOptionsCompat;
import org.acreetionos.launcher.compatlib.RemoteTransitionCompat;
import org.acreetionos.launcher.compatlib.fifteen.QuickstepCompatFactoryVV;

@RequiresApi(36)
public class QuickstepCompatFactoryVBaklava extends QuickstepCompatFactoryVV {

    @NonNull
    @Override
    public ActivityManagerCompat getActivityManagerCompat() {
        return new ActivityManagerCompatVBaklava();
    }

    @NonNull
    @Override
    public ActivityOptionsCompat getActivityOptionsCompat() {
        return new ActivityOptionsCompatVBaklava();
    }

    @NonNull
    @Override
    public RemoteTransitionCompat getRemoteTransitionCompat() {
        return RemoteTransition::new;
    }
}
