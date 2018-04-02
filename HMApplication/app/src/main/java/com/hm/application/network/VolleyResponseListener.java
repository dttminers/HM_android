package com.hm.application.network;

public interface VolleyResponseListener {
    void onError(String message);

    void onResponse(Object response);
}