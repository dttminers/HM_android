package com.hm.application.activity;

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

    FrameLayout mflUSerProfile;
    ScrollView msvUserProfileOptions;
    LinearLayout mllMainUp,mrlProfileImage,mllUserName,mllMyTrips,mllMyWallet,mllMyRewards,mllMyBucketlist,mllSettings,mllLogout;
    ImageView mivProfilePicSmall,mivMyTrips,mivMyWallet,mivMyRewards,mivMyBucketlist,mivSettings,mivLogout;
    TextView mtvUserName,mtvViewProfile,mtvMyTrips,mtvMyWallet,mtvMyWalletValue,mtvMyRewards,mtvMyRewardsValue,mtvMyBucketlist,mtvSettings,mtvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_list);

        mflUSerProfile = (FrameLayout) findViewById(R.id.flUSerProfile);
        msvUserProfileOptions = (ScrollView) findViewById(R.id.svUserProfileOptions);
//                  Linear Layout
        mllMainUp = (LinearLayout)findViewById(R.id.llMainUp);
        mrlProfileImage = (LinearLayout)findViewById(R.id.rlProfileImage);
        mllUserName = (LinearLayout)findViewById(R.id.llUserName);
        mllMyTrips = (LinearLayout)findViewById(R.id.llMyTrips);
        mllMyWallet = (LinearLayout)findViewById(R.id.llMyWallet);
        mllMyRewards = (LinearLayout)findViewById(R.id.llMyRewards);
        mllMyBucketlist = (LinearLayout)findViewById(R.id.llMyBucketlist);
        mllSettings = (LinearLayout)findViewById(R.id.llSettings);
        mllLogout = (LinearLayout)findViewById(R.id.llLogout);
//               Image View
        mivProfilePicSmall = (ImageView) findViewById(R.id.ivProfilePicSmall);
        mivMyTrips = (ImageView) findViewById(R.id.ivMyTrips);
        mivMyWallet = (ImageView) findViewById(R.id.ivMyWallet);
        mivMyRewards = (ImageView) findViewById(R.id.ivMyRewards);
        mivMyBucketlist = (ImageView) findViewById(R.id.ivMyBucketlist);
        mivSettings = (ImageView) findViewById(R.id.ivSettings);
        mivLogout = (ImageView) findViewById(R.id.ivLogout);
//        TextView
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
        mtvMyBucketlist = (TextView) findViewById(R.id.tvMyBucketlist);
        mtvMyBucketlist.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));
        mtvSettings = (TextView) findViewById(R.id.tvSettings);
        mtvSettings.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));
        mtvLogout = (TextView) findViewById(R.id.tvLogout);
        mtvLogout.setTypeface(HmFonts.getRobotoMedium(UserProfileListActivity.this));








    }
}