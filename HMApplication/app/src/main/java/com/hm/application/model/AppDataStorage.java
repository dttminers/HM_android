package com.hm.application.model;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;

//
import com.google.gson.Gson;
import com.hm.application.R;

import org.json.JSONObject;

public class AppDataStorage {

    private static final String USER_INFO = "USER_INFO";
    private static final String USER_INFO_PREFERS = "USER_INFO_PREFERS";

    public static void getUserInfo(Context context) {
        try {
            String us = context.getSharedPreferences(USER_INFO_PREFERS, 0).getString(USER_INFO, null);
            if (us != null) {
                User user = User.getUser(context);
                JSONObject userJSON = new JSONObject(us);
                // {"dob":"1\/1\/1990","email":"swapnil","gender":"M","livesIn":"NSP","mobile":"123454","name":"Swapnil Jadhav","notificationCount":0,"picPath":"uploads\/20\/profile_pics\/23-03-2018 16:28:24 PM_202879ad42dec8375e.jpg","referralCode":"123","uid":"20","username":"swapnil"}
                Log.d("HmApp", " getUserInfo " + userJSON);
                if (!userJSON.isNull(context.getString(R.string.str_uid))) {
                    user.setUid(userJSON.getString(context.getString(R.string.str_uid)));
                }
                if (!userJSON.isNull("email")) {
                    user.setEmail(userJSON.getString("email"));
                }
                if (!userJSON.isNull("name")) {
                    user.setName(userJSON.getString("name"));
                }

                if (!userJSON.isNull("username")) {
                    user.setUsername(userJSON.getString("username"));
                }
                if (!userJSON.isNull("mobile")) {
                    user.setMobile(userJSON.getString("mobile"));
                }
                if (!userJSON.isNull("dob")) {
                    user.setDob(userJSON.getString("dob"));
                }
                if (!userJSON.isNull("picPath")) {
                    user.setPicPath(userJSON.getString("picPath"));
                }
                if (!userJSON.isNull("livesIn")) {
                    user.setLivesIn(userJSON.getString("livesIn"));
                }
                if (!userJSON.isNull("referralCode")) {
                    user.setReferralCode(userJSON.getString("referralCode"));
                }
                if (!userJSON.isNull("gender")) {
                    user.setGender(userJSON.getString("gender"));
                }
                if (!userJSON.isNull("fromDest")) {
                    user.setFromDest(userJSON.getString("fromDest"));
                }
                if (!userJSON.isNull("relationStatus")) {
                    user.setRelationStatus(userJSON.getString("relationStatus"));
                }
                if (!userJSON.isNull("favQuote")) {
                    user.setFavQuote(userJSON.getString("favQuote"));
                }
                if (!userJSON.isNull("bio")) {
                    user.setBio(userJSON.getString("bio"));
                }
                if (!userJSON.isNull("fcmToken")) {
                    user.setFcmToken(userJSON.getString("fcmToken"));
                }
                if (!userJSON.isNull("notificationCount")) {
                    user.setNotificationCount(userJSON.getInt("notificationCount"));
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    public static void setUserInfo(Context context) {
        try {
            Editor editor = context.getSharedPreferences(USER_INFO_PREFERS, 0).edit();
            Log.d("HmApp", " setUserInfo " + new JSONObject(new Gson().toJson(User.getUser(context))).toString());
            editor.putString(USER_INFO, new JSONObject(new Gson().toJson(User.getUser(context))).toString());
            editor.apply();
            editor.commit();
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }
}