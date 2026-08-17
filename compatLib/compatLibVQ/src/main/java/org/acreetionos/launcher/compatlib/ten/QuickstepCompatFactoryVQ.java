package org.acreetionos.launcher.compatlib.ten;

import android.window.RemoteTransition;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import org.acreetionos.launcher.compatlib.ActivityManagerCompat;
import org.acreetionos.launcher.compatlib.ActivityOptionsCompat;
import org.acreetionos.launcher.compatlib.QuickstepCompatFactory;
import org.acreetionos.launcher.compatlib.RemoteTransitionCompat;

@RequiresApi(29)
public class QuickstepCompatFactoryVQ implements QuickstepCompatFactory {
    protected final String TAG = getClass().getCanonicalName();

    @NonNull
    @Override
    public ActivityManagerCompat getActivityManagerCompat() {
        return new ActivityManagerCompatVQ();
    }

    @NonNull
    @Override
    public ActivityOptionsCompat getActivityOptionsCompat() {
        return new ActivityOptionsCompatVQ();
    }

    @NonNull
    @Override
    public RemoteTransitionCompat getRemoteTransitionCompat() {
        return RemoteTransition::new;
    }
}
