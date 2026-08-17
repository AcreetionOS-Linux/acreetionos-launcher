package org.acreetionos.launcher.compatlib.eleven;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import org.acreetionos.launcher.compatlib.ActivityManagerCompat;
import org.acreetionos.launcher.compatlib.ActivityOptionsCompat;
import org.acreetionos.launcher.compatlib.ten.QuickstepCompatFactoryVQ;

@RequiresApi(30)
public class QuickstepCompatFactoryVR extends QuickstepCompatFactoryVQ {

    @NonNull
    @Override
    public ActivityManagerCompat getActivityManagerCompat() {
        return new ActivityManagerCompatVR();
    }

    @NonNull
    @Override
    public ActivityOptionsCompat getActivityOptionsCompat() {
        return new ActivityOptionsCompatVR();
    }
}
