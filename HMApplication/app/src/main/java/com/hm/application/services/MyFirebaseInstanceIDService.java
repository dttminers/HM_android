package com.hm.application.services;

//import android.util.Log;
////
////import com.google.firebase.iid.FirebaseInstanceId;
////import com.google.firebase.iid.FirebaseInstanceIdService;
////import com.hm.application.model.AppDataStorage;
////import com.hm.application.model.User;
////
////public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
////
////    @Override
////    public void onTokenRefresh() {
//////        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//////        Log.d("HmApp", "Refreshed token: " + refreshedToken);
//////        User.getUser(this).setFcmToken(refreshedToken);
////        AppDataStorage.setUserInfo(this);
////    }
////}