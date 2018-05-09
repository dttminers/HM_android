package com.hm.application.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.classes.Common_Alert_box;
import com.hm.application.fragments.ChangePasswordFragment;
import com.hm.application.fragments.UserProfileEditFragment;
import com.hm.application.fragments.UserTab21Fragment;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;


public class AccountSettingsActivity extends AppCompatActivity {

    private RelativeLayout mRlAccSettingMain, mRlAccSettingTool, mRlAccSettingTb;
    private LinearLayout mLlLblAccount, mLlLblSupport, mLlAccountSetting;
    private Toolbar mProfileToolBar;
    private ImageView mIvBackArrow;
    private TextView mTvLblOption, mTvLblAccount, mTvPhotos, mTvPostLiked, mTvEditProf, mTvCngPassword, mTvBlockUser, mTvPrivateAcc, mTvLblSupport, mTvProblem, mTvHelpCenter, mTvAddAccount, mTvLogout;
    private Switch mSwitchAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsettings);
        try {
            bindViews();
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

        }

    }

    private void bindViews() throws Error {
        mRlAccSettingMain = findViewById(R.id.rlAccSettingMain);
        mRlAccSettingTool = findViewById(R.id.rlAccSettingTool);
        mRlAccSettingTb = findViewById(R.id.rlAccSettingTb);

        mLlLblAccount = findViewById(R.id.llLblAccount);
        mLlLblSupport = findViewById(R.id.llLblSupport);
        mLlAccountSetting = findViewById(R.id.llAccountSetting);

        mIvBackArrow = findViewById(R.id.ivBackArrow);

        mTvLblOption = findViewById(R.id.tvLblOption);
        mTvLblOption.setTypeface(HmFonts.getRobotoBold(AccountSettingsActivity.this));

        mTvPhotos = findViewById(R.id.tvPhotos);
        mTvPhotos.setTypeface(HmFonts.getRobotoRegular(AccountSettingsActivity.this));

        mTvLblAccount = findViewById(R.id.tvLblAccount);
        mTvLblAccount.setTypeface(HmFonts.getRobotoBold(AccountSettingsActivity.this));

        mTvPostLiked = findViewById(R.id.tvPostLiked);
        mTvPostLiked.setTypeface(HmFonts.getRobotoRegular(AccountSettingsActivity.this));

        mTvEditProf = findViewById(R.id.tvEditProf);
        mTvEditProf.setTypeface(HmFonts.getRobotoRegular(AccountSettingsActivity.this));

        mTvCngPassword = findViewById(R.id.tvCngPassword);
        mTvCngPassword.setTypeface(HmFonts.getRobotoRegular(AccountSettingsActivity.this));

        mTvBlockUser = findViewById(R.id.tvBlockUser);
        mTvBlockUser.setTypeface(HmFonts.getRobotoRegular(AccountSettingsActivity.this));

        mTvLblSupport = findViewById(R.id.tvLblSupport);
        mTvLblSupport.setTypeface(HmFonts.getRobotoBold(AccountSettingsActivity.this));

        mTvProblem = findViewById(R.id.tvProblem);
        mTvProblem.setTypeface(HmFonts.getRobotoRegular(AccountSettingsActivity.this));

        mTvHelpCenter = findViewById(R.id.tvHelpCenter);
        mTvHelpCenter.setTypeface(HmFonts.getRobotoRegular(AccountSettingsActivity.this));

        mTvAddAccount = findViewById(R.id.tvAddAccount);
        mTvAddAccount.setTypeface(HmFonts.getRobotoRegular(AccountSettingsActivity.this));
        mTvAddAccount.setVisibility(View.GONE);

        mTvLogout = findViewById(R.id.tvLogout);
        mTvLogout.setTypeface(HmFonts.getRobotoRegular(AccountSettingsActivity.this));

        mSwitchAccount = findViewById(R.id.switchAccount);
        mSwitchAccount.setTypeface(HmFonts.getRobotoBold(AccountSettingsActivity.this));

        mSwitchAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Common_Alert_box.toPrivateAccount(AccountSettingsActivity.this, true);
                } else {
                    Common_Alert_box.toPrivateAccount(AccountSettingsActivity.this, false);
                }
            }
        });

        mTvHelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common_Alert_box.toContactUs(AccountSettingsActivity.this);
            }
        });

        mTvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonFunctions.toLogout(AccountSettingsActivity.this);
            }
        });

        mTvEditProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceMainHomePage(new UserProfileEditFragment());
            }
        });

        mTvPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceMainHomePage(new UserTab21Fragment());
            }
        });

        mIvBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTvCngPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceMainHomePage(new ChangePasswordFragment());
            }
        });
    }

    public void replaceMainHomePage(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rlAccSettingMain, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
















