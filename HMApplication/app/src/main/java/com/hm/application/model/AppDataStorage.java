package com.hm.application.model;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

public class AppDataStorage {

    private static final String USER_ID = "USER_ID";
    private static final String USER_ID_PREFERS = "USER_ID_PREFERS";

    private static final String USER_INFO = "USER_INFO";
    private static final String USER_INFO_PREFERS = "USER_INFO_PREFERS";

    public static void getUserInfo(Context context) {
        try {
            String us = context.getSharedPreferences(USER_INFO_PREFERS, 0).getString(USER_INFO, null);
            if (us != null) {
                User user = User.getUser(context);
                JSONObject userJSON = new JSONObject(us);
                Log.d("HmApp", " getUserInfo " + userJSON);
                if (!userJSON.isNull("username")) {
                    user.setUsername(userJSON.getString("username"));
                }
                if (!userJSON.isNull("email")) {
                    user.setEmail(userJSON.getString("email"));
                }
                if (!userJSON.isNull("mobile")) {
                    user.setMobile(userJSON.getString("mobile"));
                }
                if (!userJSON.isNull("id")) {
                    user.setUid(userJSON.getString("id"));
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

    public static void setUserId(Context context, String uid) {
        try {
            Editor editor = context.getSharedPreferences(USER_ID_PREFERS, 0).edit();
            Log.d("HmApp", " setUserInfo " + new JSONObject(new Gson().toJson(User.getUser(context))).toString());
            editor.putString(USER_ID, uid);
            editor.apply();
            editor.commit();
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static String getUserId(Context context) {
        try {
            String us = context.getSharedPreferences(USER_ID_PREFERS, 0).getString(USER_ID, null);
            if (us != null) {
                return us;
            } else {
                return null;
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            return null;
        }
    }
}

