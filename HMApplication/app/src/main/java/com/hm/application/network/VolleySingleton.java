package com.hm.application.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private VolleySingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        // If RequestQueue is null the initialize new RequestQueue
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        // Return RequestQueue
        return mRequestQueue;
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        // If Instance is null then initialize new Instance
        if(mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        // Return MySingleton new Instance
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        try {
            Log.d("HM_URL", " tag : " + tag + ":" + req.getBodyContentType() + " : " + req.getHeaders() + " : " + req.getCacheEntry() + " : " + req.hasHadResponseDelivered() + " : " + req.getMethod() + req.getUrl() + " : " + Arrays.toString(req.getPostBody()));
            getRequestQueue().add(req).setTag(tag);
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public void cancelPendingRequests(Object tag) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.cancelAll(tag);
        }
    }

    public<T> void addToRequestQueue(Request<T> request){
        // Add the specified request to the request queue
        getRequestQueue().add(request);
    }
}