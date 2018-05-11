package com.hm.application.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.common.MyFriendRequest;
import com.hm.application.fragments.UserFollowersListFragment;
import com.hm.application.fragments.UserFollowingListFragment;
import com.hm.application.fragments.UserProfileEditFragment;
import com.hm.application.fragments.UserTab1Fragment;
import com.hm.application.fragments.UserTab21Fragment;
import com.hm.application.fragments.UserTab2Fragment;
import com.hm.application.fragments.UserTab3Fragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.hm.application.utils.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity implements
        UserTab1Fragment.OnFragmentInteractionListener,
        UserTab2Fragment.OnFragmentInteractionListener,
        UserTab3Fragment.OnFragmentInteractionListener,
        UserFollowersListFragment.OnFragmentInteractionListener,
        UserFollowingListFragment.OnFragmentInteractionListener,
        UserProfileEditFragment.OnFragmentInteractionListener,
        UserTab21Fragment.OnFragmentInteractionListener {

    Uri picUri;
    private NestedScrollView mSvUpMain;
    private LinearLayout mLlUpMain, mLlUserActivities;
    private RelativeLayout mRlProfileImageData, mRlUserData, mRlUserData2, mRlDisplayUserInfo;
    private View mView1;
    private FrameLayout mFlUsersDataContainer;
    private RatingBar mRbUserRatingData;
    private ImageView mIvProfilePic, mIvFlag, mIvShare, mIvEditProfile;
    private TextView mTvUserPosts, mTvUserFollowing, mTvUserFollowers, mTvUserName, mTvUserExtraActivities, mTvUsersReferralCode, mTvUsersDescription;
    private TextView mTvLivesIn, mTvFromPlace, mTvGender, mTvRelationShipStatus, mTvDob, mTvFavTravelQuote, mTvBio;
    private Button mBtnFollow;
    private int SELECT_PICTURES = 7, REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bundle bundle;
    private ArrayList<Uri> images = new ArrayList<>();
    private String f_uid;
    private UserTab2Fragment uTab2;
    private UserFollowingListFragment following;
    private UserFollowersListFragment followers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        toSetTitle(User.getUser(UserInfoActivity.this).getUsername(), false);
        dataBinding();
        checkInternetConnection();
        allClickListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        toSetTitle(User.getUser(UserInfoActivity.this).getUsername(), false);
        checkInternetConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toSetTitle(User.getUser(UserInfoActivity.this).getUsername(), false);
        checkInternetConnection();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkInternetConnection();
        toSetTitle(User.getUser(UserInfoActivity.this).getUsername(), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_info_menu, menu);
        MenuItem moreOption = menu.findItem(R.id.menu_moreOption);
        if (f_uid != null) {
            moreOption.setVisible(false);
        } else {
            moreOption.setVisible(true);
        }
        return true;
    }

    private void dataBinding() {
        try {
            uTab2 = new UserTab2Fragment();
            following = new UserFollowingListFragment();
            followers = new UserFollowersListFragment();

            mSvUpMain = findViewById(R.id.svUpMain);

            mLlUpMain = findViewById(R.id.llUpMain);

            mRlProfileImageData = findViewById(R.id.rlProfileImageData);
            mRlUserData = findViewById(R.id.rlUserData);
            mRlUserData2 = findViewById(R.id.rlUserData2);

            mFlUsersDataContainer = findViewById(R.id.flUsersDataContainer);

            mRbUserRatingData = findViewById(R.id.rbUserRatingData);
            LayerDrawable star = (LayerDrawable) mRbUserRatingData.getProgressDrawable();
            star.getDrawable(2).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.red, null), PorterDuff.Mode.SRC_ATOP);
            star.getDrawable(0).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.transparent_white1, null), PorterDuff.Mode.SRC_ATOP);
            star.getDrawable(1).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.red, null), PorterDuff.Mode.SRC_ATOP);

            mIvProfilePic = findViewById(R.id.imgProfilePic);
            mIvFlag = findViewById(R.id.ivFlag);
            mIvShare = findViewById(R.id.ivShare);

            mTvUserPosts = findViewById(R.id.tvUserPost);
            mTvUserPosts.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvUserFollowing = findViewById(R.id.tvUserFollowing);
            mTvUserFollowing.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvUserFollowers = findViewById(R.id.tvUserFollowers);
            mTvUserFollowers.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvUserName = findViewById(R.id.txtUserName);
            mTvUserName.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));

            mTvUserExtraActivities = findViewById(R.id.txtUserExtraActivities);
            mTvUserExtraActivities.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvUsersReferralCode = findViewById(R.id.txtUsersReferralCode);
            mTvUsersReferralCode.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));

            mTvUserName = findViewById(R.id.txtUserName);
            mTvUserName.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));

            mTvUserExtraActivities = findViewById(R.id.txtUserExtraActivities);
            mTvUserExtraActivities.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvUsersReferralCode = findViewById(R.id.txtUsersReferralCode);
            mTvUsersReferralCode.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));

            mTvUsersDescription = findViewById(R.id.txtUsersDescription);
            mTvUsersDescription.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));

            mBtnFollow = findViewById(R.id.btnFollow);
            mBtnFollow.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mRlDisplayUserInfo = findViewById(R.id.rlInfoDisplay);

            mIvEditProfile = findViewById(R.id.imgEditProfile);

            mTvLivesIn = findViewById(R.id.txtLivesIn);
            mTvLivesIn.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvFromPlace = findViewById(R.id.txtFromPlace);
            mTvFromPlace.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvGender = findViewById(R.id.txtGender);
            mTvGender.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvRelationShipStatus = findViewById(R.id.txtRelationshipStatus);
            mTvRelationShipStatus.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvDob = findViewById(R.id.txtDobData);
            mTvDob.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvFavTravelQuote = findViewById(R.id.txtFavTravelQuote);
            mTvFavTravelQuote.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

            mTvBio = findViewById(R.id.txtBio);
            mTvBio.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
    }

    public void replaceMainHomePage(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flUserHomeContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    //for Tab
    public void replaceTabData(Fragment fragment) {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flUsersDataContainer, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commitAllowingStateLoss();
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(UserInfoActivity.this)) {
                if (getIntent().getExtras() != null) {
                    if (getIntent().getStringExtra(AppConstants.F_UID) != null) {
                        f_uid = getIntent().getStringExtra(AppConstants.F_UID);
                        new toDisplayOtherUserInfo().execute();
                    } else {
                        toDisplayUserInfo();
                    }
                } else {
                    toDisplayUserInfo();
                }
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), UserInfoActivity.this);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
    }

    private void allClickListener() {
        mIvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceMainHomePage(new UserProfileEditFragment());
            }
        });

        mTvUserFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceMainHomePage(followers);
            }
        });

        mTvUserFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceMainHomePage(following);
            }
        });

        mBtnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mBtnFollow.setEnabled(false);
                    if (mBtnFollow.getText().toString().trim().toLowerCase().equals(getString(R.string.str_requested).toLowerCase())) {
                        MyFriendRequest.toDeleteFollowFriendRequest(UserInfoActivity.this, f_uid, mBtnFollow, mBtnFollow);
                    } else if (mBtnFollow.getText().toString().trim().toLowerCase().equals(getString(R.string.str_following_small).toLowerCase())) {
                        MyFriendRequest.toUnFriendRequest(UserInfoActivity.this, f_uid, mBtnFollow);
                    } else {
                        MyFriendRequest.toFollowFriendRequest(UserInfoActivity.this, f_uid, mBtnFollow);
                    }
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);
                }
            }
        });

    }

    public void toDisplayUserInfo() throws Error {
        mBtnFollow.setVisibility(View.GONE);
        mTvUserName.setText(CommonFunctions.firstLetterCaps(User.getUser(UserInfoActivity.this).getUsername()));
        mTvUsersReferralCode.setText(getResources().getString(R.string.str_referral_code) + " : " + User.getUser(UserInfoActivity.this).getReferralCode());
        mTvUserFollowing.setText(getString(R.string.str_following) + User.getUser(UserInfoActivity.this).getFollowing_count());
        mTvUserFollowers.setText(getString(R.string.str_followers) + User.getUser(UserInfoActivity.this).getFollowers_count());
        mTvUserPosts.setText(CommonFunctions.firstLetterCaps(getString(R.string.str_posts)) + User.getUser(UserInfoActivity.this).getPost_count());

        mTvLivesIn.setText(User.getUser(UserInfoActivity.this).getLivesIn());
        mTvFromPlace.setText(User.getUser(UserInfoActivity.this).getFromDest());
        mTvGender.setText(User.getUser(UserInfoActivity.this).getGender());
        mTvRelationShipStatus.setText(User.getUser(UserInfoActivity.this).getRelationStatus());
        mTvDob.setText(User.getUser(UserInfoActivity.this).getDob());
        mTvFavTravelQuote.setText(User.getUser(UserInfoActivity.this).getFavQuote());
        mTvBio.setText(User.getUser(UserInfoActivity.this).getBio());

        toSetUserProfilePic();
        toSetData();
        toSetTitle(User.getUser(UserInfoActivity.this).getUsername(), false);
    }

    private void toSetUserProfilePic() {
        if (User.getUser(UserInfoActivity.this).getPicPath() != null) {
            Picasso.with(UserInfoActivity.this).load(AppConstants.URL + User.getUser(UserInfoActivity.this).getPicPath().replaceAll("\\s", "%20")).into(mIvProfilePic);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public void toSetTitle(String title, boolean status) {
        Log.d("hmapp", " tosettitle" + title + " : " + status);
        if (status) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_back_black_24dp));
                if (title != null) {
                    if (title.length() > 0) {
                        getSupportActionBar().setTitle(CommonFunctions.firstLetterCaps(title));
                    } else {
                        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                    }
                } else {
                    getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                }
            }
        }
    }

    private void toSetData() {
        replaceTabData(uTab2);
    }

    private void toHidePost() throws Error {
        mIvEditProfile.setEnabled(false);
        mIvEditProfile.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_moreOption:
                startActivity(new Intent(UserInfoActivity.this, AccountSettingsActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public class toDisplayOtherUserInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(UserInfoActivity.this)
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_other_user_profile_info) + getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String res) {
                                                try {
                                                    if (res != null && res.length() > 0) {
                                                        toHidePost();
                                                        bundle = new Bundle();
                                                        bundle.putBoolean("other_user", true);
                                                        bundle.putString(AppConstants.F_UID, f_uid);
                                                        JSONObject obj = new JSONObject(res.trim());
                                                        if (obj != null) {

                                                            if (!obj.isNull("full_name")) {
                                                                if (obj.getString("full_name").length() > 0) {
                                                                    mTvUserName.setText(CommonFunctions.firstLetterCaps(obj.getString("full_name")));
                                                                    toSetTitle(CommonFunctions.firstLetterCaps(obj.getString("full_name")), false);
                                                                    bundle.putString("name", obj.getString("full_name"));
                                                                }
                                                            }

                                                            if (!obj.isNull("profile_pic")) {
                                                                if (obj.getString("profile_pic").contains("uploads/")) {
                                                                    Picasso.with(UserInfoActivity.this)
                                                                            .load(AppConstants.URL + obj.getString("profile_pic").replaceAll("\\s", "%20"))
                                                                            .into(mIvProfilePic);
                                                                } else {
                                                                    Picasso.with(UserInfoActivity.this)
                                                                            .load(obj.getString("profile_pic").replaceAll("\\s", "%20"))
                                                                            .into(mIvProfilePic);
                                                                }
                                                            }

                                                            if (!obj.isNull("referral_code")) {
                                                                if (obj.getString("referral_code").length() > 0) {
                                                                    mTvUsersReferralCode.setText(getResources().getString(R.string.str_referral_code) + " : " + CommonFunctions.firstLetterCaps(obj.getString("referral_code")));
                                                                }
                                                            }
                                                            if (!obj.isNull("following_count")) {
                                                                if (obj.getString("following_count").length() > 0) {
                                                                    mTvUserFollowing.setText(getString(R.string.str_following) + CommonFunctions.firstLetterCaps(obj.getString("following_count")));
                                                                }
                                                            }
                                                            if (!obj.isNull("followers_count")) {
                                                                if (obj.getString("followers_count").length() > 0) {
                                                                    mTvUserFollowers.setText(getString(R.string.str_followers) + CommonFunctions.firstLetterCaps(obj.getString("followers_count")));
                                                                }
                                                            }
                                                            if (!obj.isNull("post_count")) {
                                                                if (obj.getString("post_count").length() > 0) {
                                                                    mTvUserPosts.setText(CommonFunctions.firstLetterCaps(getString(R.string.str_posts)) + CommonFunctions.firstLetterCaps(obj.getString("post_count")));
                                                                }
                                                            }

                                                            if (!obj.isNull(getString(R.string.str_lives_in))) {
                                                                if (obj.getString(getString(R.string.str_lives_in)).length() > 0) {
                                                                    mTvLivesIn.setText(CommonFunctions.firstLetterCaps(obj.getString(getString(R.string.str_lives_in))));
                                                                }
                                                            }
                                                            if (!obj.isNull(getString(R.string.str_from_des))) {
                                                                if (obj.getString(getString(R.string.str_from_des)).length() > 0) {
                                                                    mTvFromPlace.setText(CommonFunctions.firstLetterCaps(obj.getString(getString(R.string.str_from_des))));
                                                                }
                                                            }
                                                            if (!obj.isNull(getString(R.string.str_gender))) {
                                                                if (obj.getString(getString(R.string.str_gender)).length() > 0) {
                                                                    mTvGender.setText(CommonFunctions.firstLetterCaps(obj.getString(getString(R.string.str_gender))));
                                                                }
                                                            }
                                                            if (!obj.isNull(getString(R.string.str_relationship_status))) {
                                                                if (obj.getString(getString(R.string.str_relationship_status)).length() > 0) {
                                                                    mTvRelationShipStatus.setText(CommonFunctions.firstLetterCaps(obj.getString(getString(R.string.str_relationship_status))));
                                                                }
                                                            }
                                                            if (!obj.isNull(getString(R.string.str_dob))) {
                                                                if (obj.getString(getString(R.string.str_dob)).length() > 0) {
                                                                    mTvDob.setText(CommonFunctions.firstLetterCaps(obj.getString(getString(R.string.str_dob))));
                                                                }
                                                            }
                                                            if (!obj.isNull(getString(R.string.str_bio))) {
                                                                if (obj.getString(getString(R.string.str_bio)).length() > 0) {
                                                                    mTvBio.setText(CommonFunctions.firstLetterCaps(obj.getString(getString(R.string.str_bio))));
                                                                }
                                                            }
                                                            if (!obj.isNull(getString(R.string.str_fav_quote))) {
                                                                if (obj.getString(getString(R.string.str_fav_quote)).length() > 0) {
                                                                    mTvFavTravelQuote.setText(CommonFunctions.firstLetterCaps(obj.getString(getString(R.string.str_fav_quote))));
                                                                }
                                                            } else {
                                                                CommonFunctions.toDisplayToast("No Data Found", UserInfoActivity.this);
                                                            }

                                                            if (!obj.isNull("status")) {
                                                                // private
                                                                if (obj.getInt("status") == 1) {
                                                                    if (!obj.isNull("isFriend")) {
                                                                        // not Friend
                                                                        if (obj.getInt("isFriend") == 0) {
                                                                            mTvUserFollowers.setEnabled(false);
                                                                            mTvUserFollowing.setEnabled(false);
                                                                            mBtnFollow.setText("Follow");
                                                                            mFlUsersDataContainer.setVisibility(View.GONE);
                                                                        } else {
                                                                            // is Friend
                                                                            mTvUserFollowers.setEnabled(true);
                                                                            mTvUserFollowing.setEnabled(true);
                                                                            mBtnFollow.setText("Following");
                                                                            mFlUsersDataContainer.setVisibility(View.VISIBLE);
                                                                            toSetoOtherData(obj);
                                                                        }
                                                                    } else {
                                                                        // null Data
                                                                        mTvUserFollowers.setEnabled(false);
                                                                        mTvUserFollowing.setEnabled(false);
                                                                        mBtnFollow.setText("Follow");
                                                                        mFlUsersDataContainer.setVisibility(View.GONE);

                                                                    }
                                                                } else {
                                                                    // public
                                                                    if (!obj.isNull("isFriend")) {
                                                                        // not Friend
                                                                        if (obj.getInt("isFriend") == 0) {
                                                                            mTvUserFollowers.setEnabled(true);
                                                                            mTvUserFollowing.setEnabled(true);
                                                                            mBtnFollow.setText("Follow");
                                                                            mFlUsersDataContainer.setVisibility(View.VISIBLE);
                                                                            toSetoOtherData(obj);

                                                                        } else {
                                                                            // is Friend
                                                                            mTvUserFollowers.setEnabled(true);
                                                                            mTvUserFollowing.setEnabled(true);
                                                                            mBtnFollow.setText("Following");
                                                                            mFlUsersDataContainer.setVisibility(View.VISIBLE);
                                                                            toSetoOtherData(obj);

                                                                        }
                                                                    } else {
                                                                        // nullData
                                                                        mTvUserFollowers.setEnabled(false);
                                                                        mTvUserFollowing.setEnabled(false);
                                                                        mBtnFollow.setText("Follow");
                                                                        mFlUsersDataContainer.setVisibility(View.GONE);
                                                                        toSetoOtherData(obj);

                                                                    }
                                                                }
                                                            } else {
                                                                mTvUserFollowers.setEnabled(false);
                                                                mTvUserFollowing.setEnabled(false);
                                                                mBtnFollow.setText("Follow");
                                                                mFlUsersDataContainer.setVisibility(View.GONE);
                                                                toSetoOtherData(obj);
                                                            }
                                                            uTab2.setArguments(bundle);
                                                            followers.setArguments(bundle);
                                                            following.setArguments(bundle);
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Data Found", UserInfoActivity.this);
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Data Found", UserInfoActivity.this);
                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                    Crashlytics.logException(e);
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                error.printStackTrace();
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_user_info));
                                        params.put(getString(R.string.str_uid), User.getUser(UserInfoActivity.this).getUid());
                                        params.put(getString(R.string.str_friend_id), f_uid);
                                        return params;
                                    }
                                }
                                , getString(R.string.str_user_info));
            } catch (Exception | Error e) {
                e.printStackTrace();
                Crashlytics.logException(e);

            }
            return null;
        }

        private void toSetoOtherData(JSONObject obj) throws Exception, Error {
            if (!obj.isNull("fetch_photos")) {
                if (!obj.getString("fetch_photos").equals(null)) {
                    bundle.putString("fetch_photos", obj.getString("fetch_photos"));
                    if (!obj.isNull("fetch_albums")) {
                        if (!obj.getString("fetch_albums").equals(null)) {
                            bundle.putString("fetch_albums", obj.getString("fetch_albums"));
                        }
                    }
                }
            }

            if (!obj.isNull("fetch_timeline")) {
                if (!obj.getString("fetch_timeline").equals(null)) {
                    bundle.putString("fetch_timeline", obj.getString("fetch_timeline"));
                }
            }

            if (!obj.isNull("follow_following_fetch")) {
                if (!obj.getString("follow_following_fetch").equals(null)) {
                    bundle.putString("follow_following_fetch", obj.getString("follow_following_fetch"));

                }
            }

            if (!obj.isNull("follow_follower_fetch")) {
                if (!obj.getString("follow_follower_fetch").equals(null)) {
                    bundle.putString("follow_follower_fetch", obj.getString("follow_follower_fetch"));
                    Log.d("Hmapp", " agr fetch_timeline bundle  " + followers.getArguments());
                }
            }
            Log.d("hmApp", " replace bundle " + bundle.toString());
            uTab2.setArguments(bundle);
            Log.d("Hmapp", " replace bundle " + uTab2.getArguments());
            toSetData();
        }
    }
}