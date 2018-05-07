package com.hm.application.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        Log.d("HmApp", "Refreshed token: " + refreshedToken);
        User.getUser(this).setFcmToken(refreshedToken);
        AppDataStorage.setUserInfo(this);
    }
}