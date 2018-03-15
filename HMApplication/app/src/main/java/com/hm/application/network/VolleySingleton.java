package com.hm.application.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private Context mContext;
    private ImageLoader mImageLoader;
    private LruBitmapCache mLruBitmapCache;

    private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        VolleySingleton volleySingleton;
        synchronized (VolleySingleton.class) {
            if (mInstance == null) {
                mInstance = new VolleySingleton(context);
            }
            volleySingleton = mInstance;
        }
        return volleySingleton;
    }

    private LruBitmapCache getLruBitmapCache() {
        if (this.mLruBitmapCache == null) {
            this.mLruBitmapCache = new LruBitmapCache();
        }
        return this.mLruBitmapCache;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (this.mImageLoader == null) {
            getLruBitmapCache();
            this.mImageLoader = new ImageLoader(this.mRequestQueue, this.mLruBitmapCache);
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        try {
            Log.d("HM_URL", " tag : " + tag + ":" + req.getBodyContentType() + " : " + req.getBody() + req.getHeaders());
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
}