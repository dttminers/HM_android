package com.hm.application.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.utils.CommonNotification;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("HmApp", " onMessageReceived 1: " + remoteMessage.getMessageType() + " : " + remoteMessage.getTo() + " : " + remoteMessage.getMessageId());
        Log.d("HmApp", " onMessageReceived 2: " + remoteMessage.getFrom() + " : " + remoteMessage.getNotification() + " : " + remoteMessage.getSentTime());
        Log.d("HmApp", " onMessageReceived 3: " + remoteMessage.getTtl() + " : " + remoteMessage.getCollapseKey() + " : " + remoteMessage.describeContents());
        Log.d("HmApp", " onMessageReceived 4: " + remoteMessage.getData() + " : " + remoteMessage.getNotification() + " :" + remoteMessage.getData().toString());
        Log.d("HmApp", " onMessageReceived 5: " + remoteMessage.getNotification().getBody() + " : " + remoteMessage.getNotification().getTitle() + ":" + remoteMessage.getNotification().getTag());
        CommonNotification
//                .toSetImageNotification(
                .toSetNormalNotification(
                        this,
                        remoteMessage.getNotification().getTitle(),
                        remoteMessage.getNotification().getBody(),
                        new Intent(this, MainHomeActivity.class), 0);
    }
}
