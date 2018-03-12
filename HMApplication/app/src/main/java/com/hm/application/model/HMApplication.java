package com.hm.application.model;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

public class HMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("HMApplication ", " onCreate ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d("HMApplication ", " onLowMemory ");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("HMApplication ", " onTerminate ");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d("HMApplication ", " onTrimMemory : " + level);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
        Log.d("HMApplication ", " attachBaseContext ");
    }
}