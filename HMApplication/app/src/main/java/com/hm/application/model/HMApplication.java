package com.hm.application.model;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;

public class HMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        new MyFirebaseInstanceIDService().onTokenRefresh();
//        FirebaseApp.initializeApp(this);
//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
//        Fabric.with(this, new Crashlytics());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d("HMApplication ", " onLowMemory ");
    }


    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}