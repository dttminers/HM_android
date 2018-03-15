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

        mflUSerProfile = (FrameLayout) findViewById(R.id.flUSerProfile);
        msvUserProfileOptions = (ScrollView) findViewById(R.id.svUserProfileOptions);

        //  Linear Layout
        mllMainUp = (LinearLayout) findViewById(R.id.llMainUp);
        mrlProfileImage = (LinearLayout) findViewById(R.id.rlProfileImage);
        mllUserName = (LinearLayout) findViewById(R.id.llUserName);
        mllMyTrips = (LinearLayout) findViewById(R.id.llMyTrips);
        mllMyWallet = (LinearLayout) findViewById(R.id.llMyWallet);
        mllMyRewards = (LinearLayout) findViewById(R.id.llMyRewards);
        mllMyBucketList = (LinearLayout) findViewById(R.id.llMyBucketList);
        mllSettings = (LinearLayout) findViewById(R.id.llSettings);
        mllLogout = (LinearLayout) findViewById(R.id.llLogout);

        // Image View
        mivProfilePicSmall = (ImageView) findViewById(R.id.ivProfilePicSmall);
        mivMyTrips = (ImageView) findViewById(R.id.ivMyTrips);
        mivMyWallet = (ImageView) findViewById(R.id.ivMyWallet);
        mivMyRewards = (ImageView) findViewById(R.id.ivMyRewards);
        mivMyBucketList = (ImageView) findViewById(R.id.ivMyBucketList);
        mivSettings = (ImageView) findViewById(R.id.ivSettings);
        mivLogout = (ImageView) findViewById(R.id.ivLogout);

        // TextView
        mtvUserName = (TextView) findViewById(R.id.tvUserName);
        mtvUserName.setTypeface(HmFonts.getRobotoBold(UserProfileListActivity.this));

        mtvViewProfile = (TextView) findViewById(R.id.tvViewProfile);
        mtvViewProfile.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyTrips = (TextView) findViewById(R.id.tvMyTrips);
        mtvMyTrips.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyWallet = (TextView) findViewById(R.id.tvMyWallet);
        mtvMyWallet.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyWalletValue = (TextView) findViewById(R.id.tvMyWalletValue);
        mtvMyWalletValue.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyRewards = (TextView) findViewById(R.id.tvMyRewards);
        mtvMyRewards.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyRewardsValue = (TextView) findViewById(R.id.tvMyRewardsValue);
        mtvMyRewardsValue.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvMyBucketList = (TextView) findViewById(R.id.tvMyBucketList);
        mtvMyBucketList.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvSettings = (TextView) findViewById(R.id.tvSettings);
        mtvSettings.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));

        mtvLogout = (TextView) findViewById(R.id.tvLogout);
        mtvLogout.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));
    }
}