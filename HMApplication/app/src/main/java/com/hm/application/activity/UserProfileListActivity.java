package com.hm.application.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.model.User;
import com.hm.application.utils.HmFonts;

public class UserProfileListActivity extends AppCompatActivity {

    private FrameLayout mflUSerProfile;
    private ScrollView msvUserProfileOptions;
    private LinearLayout mllMainUp, mrlProfileImage, mllUserName, mllMyTrips, mllMyWallet, mllMyRewards, mllMyBucketList, mllSettings, mllLogout;
    private ImageView mivProfilePicSmall, mivMyTrips, mivMyWallet, mivMyRewards, mivMyBucketList, mivSettings, mivLogout;
    private TextView mtvUserName, mtvViewProfile, mtvMyTrips, mtvMyWallet, mtvMyWalletValue, mtvMyRewards, mtvMyRewardsValue, mtvMyBucketList, mtvSettings, mtvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }

        mflUSerProfile = findViewById(R.id.flUSerProfile);
        msvUserProfileOptions = findViewById(R.id.svUserProfileOptions);

        //  Linear Layout
        mllMainUp = findViewById(R.id.llMainUp);
        mrlProfileImage = findViewById(R.id.rlProfileImage);
        mllUserName = findViewById(R.id.llUserName);
        mllMyTrips = findViewById(R.id.llMyTrips);
        mllMyWallet = findViewById(R.id.llMyWallet);
        mllMyRewards = findViewById(R.id.llMyRewards);
        mllMyBucketList = findViewById(R.id.llMyBucketList);
        mllSettings = findViewById(R.id.llSettings);
        mllLogout = findViewById(R.id.llLogout);

        // Image View
        mivProfilePicSmall = findViewById(R.id.ivProfilePicSmall);
        mivMyTrips = findViewById(R.id.ivMyTrips);
        mivMyWallet = findViewById(R.id.ivMyWallet);
        mivMyRewards = findViewById(R.id.ivMyRewards);
        mivMyBucketList = findViewById(R.id.ivMyBucketList);
        mivSettings = findViewById(R.id.ivSettings);
        mivLogout = findViewById(R.id.ivLogout);

        // TextView
        mtvUserName = findViewById(R.id.tvUserName);
        mtvUserName.setTypeface(HmFonts.getRobotoBold(UserProfileListActivity.this));

        mtvViewProfile = findViewById(R.id.tvViewProfile);
        mtvViewProfile.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyTrips = findViewById(R.id.tvMyTrips);
        mtvMyTrips.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyWallet = findViewById(R.id.tvMyWallet);
        mtvMyWallet.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyWalletValue = findViewById(R.id.tvMyWalletValue);
        mtvMyWalletValue.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyRewards = findViewById(R.id.tvMyRewards);
        mtvMyRewards.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyRewardsValue = findViewById(R.id.tvMyRewardsValue);
        mtvMyRewardsValue.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyBucketList = findViewById(R.id.tvMyBucketList);
        mtvMyBucketList.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvSettings = findViewById(R.id.tvSettings);
        mtvSettings.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvLogout = findViewById(R.id.tvLogout);
        mtvLogout.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));


    }
}