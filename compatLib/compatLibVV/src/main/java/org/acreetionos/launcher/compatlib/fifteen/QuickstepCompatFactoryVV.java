package org.acreetionos.launcher.compatlib.fifteen;

import android.window.RemoteTransition;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import org.acreetionos.launcher.compatlib.ActivityManagerCompat;
import org.acreetionos.launcher.compatlib.ActivityOptionsCompat;
import org.acreetionos.launcher.compatlib.RemoteTransitionCompat;
import org.acreetionos.launcher.compatlib.fourteen.QuickstepCompatFactoryVU;

@RequiresApi(35)
public class QuickstepCompatFactoryVV extends QuickstepCompatFactoryVU {

    @NonNull
    @Override
    public ActivityManagerCompat getActivityManagerCompat() {
        return new ActivityManagerCompatVV();
    }

    @NonNull
    @Override
    public ActivityOptionsCompat getActivityOptionsCompat() {
        return new ActivityOptionsCompatVV();
    }

    @NonNull
    @Override
    public RemoteTransitionCompat getRemoteTransitionCompat() {
        return RemoteTransition::new;
    }
}
