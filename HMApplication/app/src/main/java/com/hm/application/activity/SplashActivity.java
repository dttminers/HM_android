package com.hm.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.user_data.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.d("HmApp", " Splash 1" + User.getUser(SplashActivity.this).getUid() + ":" + User.getUser(SplashActivity.this).getUsername());
//                AppDataStorage.getUserInfo(SplashActivity.this);
//                Log.d("HmApp", " Splash 2" + User.getUser(SplashActivity.this).getUid() + ":" + User.getUser(SplashActivity.this).getUsername());
////                if (User.getUser(SplashActivity.this).getUsername() != null) {
//                if(AppDataStorage.getUserId(SplashActivity.this)!= null){
//                    startActivity(new Intent(SplashActivity.this, MainHomeActivity.class));
//                } else {
//                    startActivity(new Intent(SplashActivity.this, LoginActivity.class).putExtra(AppConstants.USERDATA, AppConstants.LOGIN));
//                }
                AppDataStorage.setUserId(SplashActivity.this, "20");
                startActivity(new Intent(SplashActivity.this, MainHomeActivity.class));
                finish();
            }
        }, 2000);
    }
}