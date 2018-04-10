package com.hm.application.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("HmApp", " onMessageReceived 1: " + remoteMessage.getMessageType() + " : " + remoteMessage.getTo() + " : " + remoteMessage.getMessageId());
        Log.d("HmApp", " onMessageReceived 2: " + remoteMessage.getFrom() + " : " + remoteMessage.getNotification() + " : " + remoteMessage.getSentTime());
        Log.d("HmApp", " onMessageReceived 3: " + remoteMessage.getTtl() + " : " + remoteMessage.getCollapseKey() + " : " + remoteMessage.describeContents());
        Log.d("HmApp", " onMessageReceived 4: " + remoteMessage.getData() + " : " + remoteMessage.getNotification() + " :" + remoteMessage.getNotification().getBody());
        Log.d("HmApp", " onMessageReceived 5: " + remoteMessage.getNotification().getBody() + " : " + remoteMessage.getNotification().getTitle() + ":" + remoteMessage.getNotification().getTag());
    }
}
