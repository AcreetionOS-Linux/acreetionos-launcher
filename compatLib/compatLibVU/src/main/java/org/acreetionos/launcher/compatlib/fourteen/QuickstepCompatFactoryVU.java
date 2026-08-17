package org.acreetionos.launcher.compatlib.fourteen;

import android.window.RemoteTransition;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import org.acreetionos.launcher.compatlib.ActivityManagerCompat;
import org.acreetionos.launcher.compatlib.ActivityOptionsCompat;
import org.acreetionos.launcher.compatlib.RemoteTransitionCompat;
import org.acreetionos.launcher.compatlib.thirteen.QuickstepCompatFactoryVT;

@RequiresApi(34)
public class QuickstepCompatFactoryVU extends QuickstepCompatFactoryVT {

    @NonNull
    @Override
    public ActivityManagerCompat getActivityManagerCompat() {
        return new ActivityManagerCompatVU();
    }

    @NonNull
    @Override
    public ActivityOptionsCompat getActivityOptionsCompat() {
        return new ActivityOptionsCompatVU();
    }

    @NonNull
    @Override
    public RemoteTransitionCompat getRemoteTransitionCompat() {
        return RemoteTransition::new;
    }
}
