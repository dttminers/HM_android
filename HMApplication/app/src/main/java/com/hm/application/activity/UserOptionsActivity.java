package com.hm.application.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.fragments.BucketListFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.user_data.LoginActivity;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

public class UserOptionsActivity extends AppCompatActivity {

    private FrameLayout mflUSerProfile;
    private ScrollView msvUserProfileOptions;
    private LinearLayout mllMainUp, mllProfileImage, mllUserName, mllMyTrips, mllMyWallet, mllMyRewards, mllMyBucketList, mllSettings, mllLogout;
    private ImageView mivProfilePicSmall, mivMyTrips, mivMyWallet, mivMyRewards, mivMyBucketList, mivSettings, mivLogout;
    private TextView mtvUserName, mtvViewProfile, mtvMyTrips, mtvMyWallet, mtvMyWalletValue, mtvMyRewards, mtvMyRewardsValue, mtvMyBucketList, mtvSettings, mtvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_left_dark_pink3_24dp));
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
            Spannable text = new SpannableString(getSupportActionBar().getTitle());
            text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_pink3)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            getSupportActionBar().setTitle(text);
        }
        toBindViews();
    }

    private void toBindViews() {
        mflUSerProfile = findViewById(R.id.flUserProfileOption);
        msvUserProfileOptions = findViewById(R.id.svUserProfileOptions);

        //  Linear Layout
        mllMainUp = findViewById(R.id.llMainUp);
        mllProfileImage = findViewById(R.id.rlProfileImage);
        mllProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserOptionsActivity.this, UserInfoActivity.class));
            }
        });

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
        mtvUserName.setTypeface(HmFonts.getRobotoRegular(UserOptionsActivity.this));
        mtvUserName.setText(CommonFunctions.firstLetterCaps(User.getUser(UserOptionsActivity.this).getUsername()));

        mtvViewProfile = findViewById(R.id.tvViewProfile);
        mtvViewProfile.setTypeface(HmFonts.getRobotoRegular(UserOptionsActivity.this));

        mtvMyTrips = findViewById(R.id.tvMyTrips);
        mtvMyTrips.setTypeface(HmFonts.getRobotoRegular(UserOptionsActivity.this));

        mtvMyWallet = findViewById(R.id.tvMyWallet);
        mtvMyWallet.setTypeface(HmFonts.getRobotoRegular(UserOptionsActivity.this));

        mtvMyWalletValue = findViewById(R.id.tvMyWalletValue);
        mtvMyWalletValue.setTypeface(HmFonts.getRobotoRegular(UserOptionsActivity.this));

        mtvMyRewards = findViewById(R.id.tvMyRewards);
        mtvMyRewards.setTypeface(HmFonts.getRobotoRegular(UserOptionsActivity.this));

        mtvMyRewardsValue = findViewById(R.id.tvMyRewardsValue);
        mtvMyRewardsValue.setTypeface(HmFonts.getRobotoRegular(UserOptionsActivity.this));

        mtvMyBucketList = findViewById(R.id.tvMyBucketList);
        mtvMyBucketList.setTypeface(HmFonts.getRobotoRegular(UserOptionsActivity.this));

        mtvSettings = findViewById(R.id.tvSettings);
        mtvSettings.setTypeface(HmFonts.getRobotoRegular(UserOptionsActivity.this));

        mtvLogout = findViewById(R.id.tvLogout);
        mtvLogout.setTypeface(HmFonts.getRobotoRegular(UserOptionsActivity.this));

        mllMyBucketList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replacePage(new BucketListFragment());

            }
        });

        if (User.getUser(UserOptionsActivity.this).getPicPath() != null) {
            Picasso.with(UserOptionsActivity.this)
                    .load(AppConstants.URL + User.getUser(UserOptionsActivity.this).getPicPath().replaceAll("\\s", "%20")).into(mivProfilePicSmall);
        }

        mllLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonFunctions.toLogout(UserOptionsActivity.this);
            }
        });
    }

    public void replacePage(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flUserProfileOption, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}