package com.hm.application.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;


public class VolleySingleton {
    private static VolleySingleton mInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;

    private VolleySingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        try {
            Log.d("HM_URL", " tag 1: " + tag + " : " + req.getUrl());
            Log.d("HM_URL", " tag 2: " + new String(req.getBody(), "UTF-8"));
            req.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) {
                    error.printStackTrace();
                }
            });
            getRequestQueue().add(req).setTag(tag);
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

        }
    }


    public void cancelPendingRequests(Object tag) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.cancelAll(tag);
        }
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}