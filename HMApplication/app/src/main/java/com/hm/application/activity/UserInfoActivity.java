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
import android.graphics.drawable.ColorDrawable;
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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.common.MyFriendRequest;
import com.hm.application.fragments.UserFollowersListFragment;
import com.hm.application.fragments.UserFollowingListFragment;
import com.hm.application.fragments.UserProfileEditFragment;
import com.hm.application.fragments.UserTab1Fragment;
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
        UserProfileEditFragment.OnFragmentInteractionListener {

    private NestedScrollView mSvUpMain;
    private LinearLayout mLlUpMain, mLlUserActivities;
    private RelativeLayout mRlProfileImageData, mRlUserData, mRlUserData2, mRlDisplayUserInfo;
    private View mView1;
    private FrameLayout mFlUsersDataContainer;
    private RatingBar mRbUserRatingData;
    private ImageView mIvProfilePic, mIvFlag, mIvShare, mIvEditProfile;//, mIvPostCamera, mIvPostTag;
    private TextView mTvUserPosts,mTvUserFollowing, mTvUserFollowers, mTvUserName, mTvUserExtraActivities, mTvUsersReferralCode, mTvUsersDescription;
    private TextView mTvLivesIn, mTvFromPlace, mTvGender, mTvRelationShipStatus, mTvDob, mTvFavTravelQuote, mTvBio;
    //    private EditText mEdtPostData;
//    private GridLayout mGv;
    private Button mBtnFollow;//, mBtnPostSubmit;
    //    private TabItem mTbiUsersFeed, mTbiPhotos, mTbiUsersActivities;
//    private TabLayout mTbUsersActivity;
    private int SELECT_PICTURES = 7, REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private Bundle bundle;

    private ArrayList<Uri> images = new ArrayList<>();
    private String f_uid;
    //    private UserTab1Fragment uTab1;
    private UserTab2Fragment uTab2;
    //    private UserTab3Fragment uTab3;
    private UserFollowingListFragment following;
    private UserFollowersListFragment followers;

    Uri picUri;

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
        Log.d("hmapp", " UserInfo OnRestart");
        toSetTitle(User.getUser(UserInfoActivity.this).getUsername(), false);
//        toSetUserProfilePic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("hmapp", " UserInfo OnResume");
        toSetTitle(User.getUser(UserInfoActivity.this).getUsername(), false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("hmapp", " UserInfo OnStart");
        toSetTitle(User.getUser(UserInfoActivity.this).getUsername(), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_info_menu, menu);
        return true;
    }


//    public void toSetData() {
//        Log.d("Hmapp", " tab " + mTbUsersActivity.getSelectedTabPosition());
//        replaceTabData(uTab1);
//        mTbUsersActivity.getTabAt(0).select();
//        mTbUsersActivity.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Log.d("HmaPP", " TAB " + tab.getPosition());
//                switch (tab.getPosition()) {
//                    case 0:
//                        replaceTabData(uTab1);
//                        break;
//                    case 1:
//                        replaceTabData(uTab2);
//                        break;
//                    case 2:
//                        replaceTabData(uTab3);
//                        break;
//                    default:
//                        replaceTabData(uTab1);
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//                    case 0:
//                        replaceTabData(uTab1);
//                        break;
//                    case 1:
//                        replaceTabData(uTab2);
//                        break;
//                    case 2:
//                        replaceTabData(uTab3);
//                        break;
//                    default:
//                        replaceTabData(uTab1);
//                        break;
//                }
//            }
//        });
//    }

    private void dataBinding() {
        try {
//            uTab1 = new UserTab1Fragment();
            uTab2 = new UserTab2Fragment();
//            uTab3 = new UserTab3Fragment();
            following = new UserFollowingListFragment();
            followers = new UserFollowersListFragment();

            // Post
//            mBtnPostSubmit = findViewById(R.id.btnPostSubmit);
//            mEdtPostData = findViewById(R.id.edt_desc_post);
//            mIvPostCamera = findViewById(R.id.imgIconCam);
//            mIvPostTag = findViewById(R.id.imgIconTag);
//            mGv = findViewById(R.id.mGvImages);

            mSvUpMain = findViewById(R.id.svUpMain);

            mLlUpMain = findViewById(R.id.llUpMain);
//            mLlUserActivities = findViewById(R.id.llUserActivities);

            mRlProfileImageData = findViewById(R.id.rlProfileImageData);
//            Log.d("HmAPp", "Screen Width : " + CommonFunctions.getScreenWidth());
//            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(CommonFunctions.getScreenWidth(), CommonFunctions.getScreenWidth());
//            mRlProfileImageData.setLayoutParams(p);
            mRlUserData = findViewById(R.id.rlUserData);
            mRlUserData2 = findViewById(R.id.rlUserData2);

//            mView1 = findViewById(R.id.v11);

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

//            mTbiUsersFeed = findViewById(R.id.tbiUsersFeed);
//            mTbiPhotos = findViewById(R.id.tbiPhotos);
//            mTbiUsersActivities = findViewById(R.id.tbiUsersActivities);

            mRlDisplayUserInfo = findViewById(R.id.rlInfoDisplay);

            mIvEditProfile = findViewById(R.id.imgEditProfile);
//            mIvEditProfile.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

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

//            mTbUsersActivity = findViewById(R.id.tbUsersActivity);

        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    public void replaceMainHomePage(Fragment fragment) {
        Log.d("Hmapp", " agr replaceTabData 1 bundle  " + fragment.getArguments());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flUserHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    //for Tab
    public void replaceTabData(Fragment fragment) {
        try {
            Log.d("Hmapp", " agr replaceTabData 2 bundle  " + fragment.getArguments());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flUsersDataContainer, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
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
            FirebaseCrash.report(e);
        }
    }

    private void allClickListener() {
        mIvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Common_Alert_box.toFillUserDetailsInfo(UserInfoActivity.this, UserInfoActivity.this);
                replaceMainHomePage(new UserProfileEditFragment());
            }
        });

//        mIvProfilePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectImage();
//            }
//        });

//        mIvPostCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                multiSelectImage();
//            }
//        });
//
//        mIvPostTag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

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

//        mBtnPostSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mEdtPostData.getText().toString().trim().length() > 0) {
//                    if (images.size() > 0) {
//                        if (images.size() > 1) {
//                            MyPost.toUploadAlbum(UserInfoActivity.this, UserInfoActivity.this, mEdtPostData.getText().toString(), images);
//                        } else {
//                            MyPost.toUploadImage(UserInfoActivity.this, UserInfoActivity.this, mEdtPostData.getText().toString(), images.get(0));
//                        }
//                    } else {
//                        MyPost.toUpdateMyPost(UserInfoActivity.this, "POST", null, null, mEdtPostData.getText().toString().trim());
//                    }
//                    toSetData();
//                    mEdtPostData.setText("");
//                } else {
//                    CommonFunctions.toDisplayToast(" Empty Data ", UserInfoActivity.this);
//                }
//            }
//        });

        mBtnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mBtnFollow.setEnabled(false);
                    Log.d("Hmapp", " mbtnignore :  " + mBtnFollow.getText() + " : "
                            + (mBtnFollow.getText().toString().trim().equals(getString(R.string.str_requested))) + " : "
                            + (mBtnFollow.getText().toString().trim().equals(getString(R.string.str_following_small)))
                    );
                    if (mBtnFollow.getText().toString().trim().toLowerCase().equals(getString(R.string.str_requested).toLowerCase())) {
                        MyFriendRequest.toDeleteFollowFriendRequest(UserInfoActivity.this, f_uid, mBtnFollow, mBtnFollow);
                    } else if (mBtnFollow.getText().toString().trim().toLowerCase().equals(getString(R.string.str_following_small).toLowerCase())) {
                        MyFriendRequest.toUnFriendRequest(UserInfoActivity.this, f_uid, mBtnFollow);
                    } else {
                        MyFriendRequest.toFollowFriendRequest(UserInfoActivity.this, f_uid, mBtnFollow);
                    }
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    FirebaseCrash.report(e);
                }
            }
        });

    }

    private void multiSelectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Log.d("hmapp", " Result : " + requestCode + ":" + resultCode + ":" + data);
            if (requestCode == SELECT_PICTURES) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            Log.d("hmapp", " imageuri " + imageUri);
//                            CropImage(imageUri);
                            images.add(imageUri);
                            currentItem = currentItem + 1;
                        }
                    } else if (data.getData() != null) {
                        String imagePath = data.getData().getPath();
//                        CropImage(Uri.fromFile(new File(imagePath)));
                        images.add(Uri.fromFile(CommonFunctions.toSaveImages(MediaStore.Images.Media.getBitmap(UserInfoActivity.this.getContentResolver(), data.getData()), "HMC", false, UserInfoActivity.this, UserInfoActivity.this)));
                    }
                }
            }

            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE) {
                    onSelectFromGalleryResult(data);
                } else if (requestCode == REQUEST_CAMERA) {
                    onCaptureImageResult(data);
                } else if (requestCode == 3) {
                    // get the returned data
                    Bundle extras = data.getExtras();
                    Log.d("hmapp", " crop " + extras);
                    // get the cropped bitmap
                    Bitmap thePic = (Bitmap) extras.get("data");
                    Log.d("hmapp", " crop " + thePic + " ; " + thePic.getByteCount());
//                    CommonFunctions.toSaveImages(thePic, "HM_PP", true, UserInfoActivity.this, UserInfoActivity.this);
//                    images.add(Uri.fromFile(CommonFunctions.toSaveImages(MediaStore.Images.Media.getBitmap(UserInfoActivity.this.getContentResolver(), data.getData()), "HM_", false, UserInfoActivity.this, UserInfoActivity.this)));
                    // CropImage();
                    mIvProfilePic.setImageBitmap(thePic);
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void toCreateImagesOFPostView(Uri imageUri) {
        try {
            Log.d("Hmapp ", " image uri imv " + imageUri);
            Bitmap myImg = BitmapFactory.decodeFile(imageUri.getPath());
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(), matrix, true);
            ImageView mIv = new ImageView(UserInfoActivity.this);
            mIv.setImageBitmap(rotated);
//            mGv.addView(mIv);
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void onCaptureImageResult(Intent data) {
        try {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mIvProfilePic.setImageBitmap(thumbnail);
            CommonFunctions.toSaveImages(thumbnail, "HMC", true, UserInfoActivity.this, UserInfoActivity.this);
//            CropImage(picUri);
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(UserInfoActivity.this.getContentResolver(), data.getData());
                mIvProfilePic.setImageBitmap(bm);
                CommonFunctions.toSaveImages(bm, "HMG", true, UserInfoActivity.this, UserInfoActivity.this);
//                CropImage(data.getData());
            } catch (Exception | Error e) {
                e.printStackTrace();
                FirebaseCrash.report(e);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String userChoosenTask = null;
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    CommonFunctions.toDisplayToast("Permission", UserInfoActivity.this);
                    //code for deny
                }
                break;
        }
    }

    private void CropImage(Uri picUri) {
        try {
            Log.d("Hmapp", " Crop image " + picUri);
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, 3);
        } catch (ActivityNotFoundException e) {
//            Toast.makeText(this, "Your device is not supportting the crop action", Toast.LENGTH_SHORT);
            FirebaseCrash.report(e);
        }
    }

    private void selectImage() {
        try {
            final CharSequence[] items = {"Take Photo", "Choose from Library",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result = Utility.checkPermission(UserInfoActivity.this);
                    String userChoosenTask;
                    if (items[item].equals("Take Photo")) {
                        userChoosenTask = "Take Photo";
                        if (result)
                            cameraIntent();
                    } else if (items[item].equals("Choose from Library")) {
                        userChoosenTask = "Choose from Library";
                        if (result)
                            galleryIntent();
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void cameraIntent() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
            File imageFile = new File(imageFilePath);
            picUri = Uri.fromFile(imageFile); // convert path to Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void galleryIntent() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    public void toDisplayUserInfo() throws Error {
        mBtnFollow.setVisibility(View.GONE);
        mTvUserName.setText(CommonFunctions.firstLetterCaps(User.getUser(UserInfoActivity.this).getUsername()));
        mTvUsersReferralCode.setText(getResources().getString(R.string.str_referral_code) + " : " + User.getUser(UserInfoActivity.this).getReferralCode());
        mTvUserFollowing.setText(getString(R.string.str_following) + User.getUser(UserInfoActivity.this).getFollowing_count());
        mTvUserFollowers.setText(getString(R.string.str_followers) + User.getUser(UserInfoActivity.this).getFollowers_count());
        mTvUserPosts.setText(CommonFunctions.firstLetterCaps(getString(R.string.str_posts))+ User.getUser(UserInfoActivity.this).getPost_count());

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
//                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
//                Spannable text = new SpannableString(getSupportActionBar().getTitle());
//                text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_pink3)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                getSupportActionBar().setTitle(text);
            }
        }
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
                                            public void onResponse(String response) {
                                                try {
                                                    toHidePost();
                                                    bundle = new Bundle();
                                                    bundle.putBoolean("other_user", true);
                                                    bundle.putString(AppConstants.F_UID, f_uid);

                                                    Log.d("HmApp", "fetch_photos Res " + response);
                                                    JSONObject obj = new JSONObject(response.trim());
                                                    if (obj != null) {

                                                        Log.d("HmApp", " lives in " + obj.getString(getString(R.string.str_lives_in)));
                                                        if (!obj.isNull("full_name")) {
                                                            if (obj.getString("full_name").length() > 0) {
                                                                mTvUserName.setText(CommonFunctions.firstLetterCaps(obj.getString("full_name")));
                                                               toSetTitle( CommonFunctions.firstLetterCaps(obj.getString("full_name")), false);
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
//                                                                        mTbUsersActivity.setVisibility(View.GONE);
                                                                        mFlUsersDataContainer.setVisibility(View.GONE);
                                                                    } else {
                                                                        // is Friend
                                                                        mTvUserFollowers.setEnabled(true);
                                                                        mTvUserFollowing.setEnabled(true);
                                                                        mBtnFollow.setText("Following");
//                                                                        mTbUsersActivity.setVisibility(View.VISIBLE);
                                                                        mFlUsersDataContainer.setVisibility(View.VISIBLE);
                                                                        toSetoOtherData(obj);
                                                                    }
                                                                } else {
                                                                    // null Data
                                                                    mTvUserFollowers.setEnabled(false);
                                                                    mTvUserFollowing.setEnabled(false);
                                                                    mBtnFollow.setText("Follow");
//                                                                    mTbUsersActivity.setVisibility(View.GONE);
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
//                                                                        mTbUsersActivity.setVisibility(View.VISIBLE);
                                                                        mFlUsersDataContainer.setVisibility(View.VISIBLE);
                                                                        toSetoOtherData(obj);

                                                                    } else {
                                                                        // is Friend
                                                                        mTvUserFollowers.setEnabled(true);
                                                                        mTvUserFollowing.setEnabled(true);
                                                                        mBtnFollow.setText("Following");
//                                                                        mTbUsersActivity.setVisibility(View.VISIBLE);
                                                                        mFlUsersDataContainer.setVisibility(View.VISIBLE);
                                                                        toSetoOtherData(obj);

                                                                    }
                                                                } else {
                                                                    // nullData
                                                                    mTvUserFollowers.setEnabled(false);
                                                                    mTvUserFollowing.setEnabled(false);
                                                                    mBtnFollow.setText("Follow");
//                                                                    mTbUsersActivity.setVisibility(View.GONE);
                                                                    mFlUsersDataContainer.setVisibility(View.GONE);
                                                                    toSetoOtherData(obj);

                                                                }
                                                            }
                                                        } else {
                                                            mTvUserFollowers.setEnabled(false);
                                                            mTvUserFollowing.setEnabled(false);
                                                            mBtnFollow.setText("Follow");
//                                                            mTbUsersActivity.setVisibility(View.GONE);
                                                            mFlUsersDataContainer.setVisibility(View.GONE);
                                                            toSetoOtherData(obj);
                                                        }
//                                                        uTab1.setArguments(bundle);
                                                        uTab2.setArguments(bundle);
                                                        followers.setArguments(bundle);
                                                        following.setArguments(bundle);
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Data Found", UserInfoActivity.this);
                                                    }


                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                    FirebaseCrash.report(e);
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
                FirebaseCrash.report(e);
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
//                    replaceTabData(uTab1);
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

    private void toSetData() {
        replaceTabData(uTab2);
    }

    private void toHidePost() throws Error {
//        mBtnPostSubmit.setVisibility(View.GONE);
//        mEdtPostData.setVisibility(View.GONE);
//        mIvPostCamera.setVisibility(View.GONE);
//        mIvPostTag.setVisibility(View.GONE);
//        mGv.setVisibility(View.GONE);
        mIvEditProfile.setEnabled(false);
        mIvEditProfile.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Log.d("HmApp", "User onBackStackChanged 1 : " + getSupportFragmentManager().getBackStackEntryCount() + " : " + getSupportFragmentManager().getFragments());
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            Log.d("HmApp", "User kl");
            getFragmentManager().popBackStack();
        } else {
            Log.d("hmapp", " UserInfo backpress");
            toSetTitle(User.getUser(UserInfoActivity.this).getUsername(), false);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_logout:
                CommonFunctions.toLogout(UserInfoActivity.this);
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
}