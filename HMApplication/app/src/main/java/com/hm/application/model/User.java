package com.hm.application.model;

import android.content.Context;

public class User {

    private static transient User user;
    private int cartCount;
    private transient Context context;
    private String email;
    private String fcmToken;
    private int following;
    private String id;
    private boolean isSetPic;
    private int loyaltyPoints;
    private String mobile;
    private String name;
    private int notificationCount = 0;
    private int pic;
    private String picPath;
    private String pwd;
    private String token;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getIsSetPic() {
        return this.isSetPic;
    }

    public void setIsSetPic(boolean isSetPic) {
        this.isSetPic = isSetPic;
    }

    public int getPic() {
        return this.pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getCartCount() {
        return this.cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

    public int getNotificationCount() {
        return this.notificationCount;
    }

    public void setNotificationCount(int cartCount) {
        this.notificationCount = cartCount;
    }

    public int getFollowing() {
        return this.following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getLoyaltyPoints() {
        return this.loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getFcmToken() {
        return this.fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
