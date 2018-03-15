package com.hm.application.model;

import android.content.Context;

public class User {

    private static transient User user;
    private transient Context context;
    private String email;
    private String fcmToken;
    private String id;
    private String dob;
    private String mobile;
    private String username;
    private int notificationCount = 0;
    private String picPath;

    public User(Context context) {
        this.context = context;
    }

    public static User getUser(Context context) {
        if (user == null) {
            user = new User(context);
        }
        return user;
    }

    public void setUser(User user) {
        user = user;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicPath() {
        return this.picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return this.dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getNotificationCount() {
        return this.notificationCount;
    }

    public void setNotificationCount(int cartCount) {
        this.notificationCount = cartCount;
    }

    public String getFcmToken() {
        return this.fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

}