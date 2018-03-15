package com.hm.application.model;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

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
                    user.setId(userJSON.getString("id"));
                }

            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void setUserInfo(Context context) {
        try {
            Editor editor = context.getSharedPreferences(USER_INFO_PREFERS, 0).edit();
            editor.putString(USER_INFO, new JSONObject(new Gson().toJson(User.getUser(context))).toString());
            editor.apply();
            editor.commit();
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}

