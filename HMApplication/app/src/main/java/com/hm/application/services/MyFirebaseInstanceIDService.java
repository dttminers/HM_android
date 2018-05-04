package com.hm.application.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        User.getUser(this).setFcmToken(FirebaseInstanceId.getInstance().getToken());
//        AppDataStorage.setUserInfo(this);
    }
}