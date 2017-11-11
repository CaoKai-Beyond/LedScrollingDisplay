package jmscapplications.com.ledscrollindisplay.custom_views;


import android.app.Application;

import com.ckmobile.led.BuildConfig;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;

public class CustomAppClass extends Application {
    public void onCreate() {
        super.onCreate();
        //init Fabric
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(BuildConfig.DEBUG)
                .build();
        Fabric.with(fabric);
    }
}
