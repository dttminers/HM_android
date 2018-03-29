package com.hm.application.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.inputmethodservice.Keyboard;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.common.MyPost;
import com.hm.application.common.UserData;
import com.hm.application.fragments.UserFollowersListFragment;
import com.hm.application.fragments.UserFollowingListFragment;
import com.hm.application.fragments.UserTab1Fragment;
import com.hm.application.fragments.UserTab2Fragment;
import com.hm.application.fragments.UserTab3Fragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.network.VolleyMultipartRequest;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.hm.application.utils.KeyBoard;
import com.hm.application.utils.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {
    private NestedScrollView mSvUpMain;
    private LinearLayout mLlUpMain, mLlUserActivities;
    private RelativeLayout mRlProfileImageData, mRlUserData, mRlUserData2;
    private View mView1;
    private FrameLayout mFlUsersDataContainer;
    private RatingBar mRbUserRatingData;
    private ImageView mIvProfilePic, mIvFlag, mIvShare, mIvPostCamera, mIvPostTag;
    private TextView mTvUserFollowing, mTvUserFollowers, mTvUserName, mTvUserExtraActivities, mTvUsersReferralCode, mTvUsersDescription;
    private TextView mTvLblIntroduceEdit, mTvLblIntroduceDone, mTvLivesIn, mTvFromPlace, mTvGender, mTvRelationShipStatus, mTvDob, mTvFavTravelQuote, mTvBio;
    private TextInputLayout mTilLivesIn, mTilFromPlace, mTilGender, mTilRelationShipStatus, mTilDob, mTilFavTravelQuote, mTilBio;
    private EditText mEdtPostData, mEdtLivesIn, mEdtFromPlace, mEdtRelationShipStatus, mEdtDob, mEdtFavTravelQuote, mEdtBio;
    private Spinner mSprGender;
    private Button mBtnFollow, mBtnPostSubmit;
    private TabItem mTbiUsersFeed, mTbiPhotos, mTbiUsersActivities;
    private TabLayout mTbUsersActivity;
    private LinearLayout mLlDisplayUserInfo, mLlEditUserInfo;
    private int SELECT_PICTURES = 7, REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ArrayList<Uri> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        dataBinding();
    }

    private void dataBinding() {
        try {
            // Post
            mBtnPostSubmit = findViewById(R.id.btnPostSubmit);
            mEdtPostData = findViewById(R.id.edt_desc_post);
            mIvPostCamera = findViewById(R.id.imgIconCam);
            mIvPostTag = findViewById(R.id.imgIconTag);

            mEdtPostData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEdtPostData.setFocusable(true);
                    mEdtPostData.setFocusableInTouchMode(true);
                    mEdtPostData.requestFocus();
                    KeyBoard.openKeyboard(UserInfoActivity.this);

                }
            });

            mSvUpMain = findViewById(R.id.svUpMain);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mSvUpMain.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        Log.d("ScrollView", "scrollX_" + scrollX + "_scrollY_" + scrollY + "_oldScrollX_" + oldScrollX + "_oldScrollY_" + oldScrollY);
                        //Do something
                    }
                });
            }

            mSvUpMain.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    int scrollY = mSvUpMain.getScrollY(); // For ScrollView
                    int scrollX = mSvUpMain.getScrollX(); // For HorizontalScrollView
//                    Log.d("HmApp", " Scrollview " + scrollX + " : " + scrollY);
                    // DO SOMETHING WITH THE SCROLL COORDINATES
//                    if (scrollY > 170) {
//                        replaceTabData(new UserTab1Fragment());
//                    }
                }
            });

            mLlUpMain = findViewById(R.id.llUpMain);
            mLlUserActivities = findViewById(R.id.llUserActivities);

            mRlProfileImageData = findViewById(R.id.rlProfileImageData);
            mRlUserData = findViewById(R.id.rlUserData);
            mRlUserData2 = findViewById(R.id.rlUserData2);

            mView1 = findViewById(R.id.v11);

            mFlUsersDataContainer = findViewById(R.id.flUsersDataContainer);

            mRbUserRatingData = findViewById(R.id.rbUserRatingData);

            mIvProfilePic = findViewById(R.id.imgProfilePic);
            mIvFlag = findViewById(R.id.ivFlag);
            mIvShare = findViewById(R.id.ivShare);

            mTvUserFollowing = findViewById(R.id.tvUserFollowing);
            mTvUserFollowing.setTypeface(HmFonts.getRobotoMedium(UserInfoActivity.this));
            mTvUserFollowing.setText(getString(R.string.str_following) + User.getUser(UserInfoActivity.this).getFollowers_count());

            mTvUserFollowers = findViewById(R.id.tvUserFollowers);
            mTvUserFollowers.setTypeface(HmFonts.getRobotoMedium(UserInfoActivity.this));
            mTvUserFollowers.setText(getString(R.string.str_followers) + User.getUser(UserInfoActivity.this).getFollowing_count());

            mTvUserName = findViewById(R.id.txtUserName);
            mTvUserName.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));
            mTvUserName.setText(User.getUser(UserInfoActivity.this).getUsername());

            mTvUserExtraActivities = findViewById(R.id.txtUserExtraActivities);
            mTvUserExtraActivities.setTypeface(HmFonts.getRobotoMedium(UserInfoActivity.this));

            mTvUsersReferralCode = findViewById(R.id.txtUsersReferralCode);
            mTvUsersReferralCode.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));

            mTvUserName = findViewById(R.id.txtUserName);
            mTvUserName.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));

            mTvUserExtraActivities = findViewById(R.id.txtUserExtraActivities);
            mTvUserExtraActivities.setTypeface(HmFonts.getRobotoMedium(UserInfoActivity.this));

            mTvUsersReferralCode = findViewById(R.id.txtUsersReferralCode);
            mTvUsersReferralCode.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));

            mTvUsersDescription = findViewById(R.id.txtUsersDescription);
            mTvUsersDescription.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));

            mBtnFollow = findViewById(R.id.btnFollow);
            mTvUserFollowing.setTypeface(HmFonts.getRobotoMedium(UserInfoActivity.this));

            mTbiUsersFeed = findViewById(R.id.tbiUsersFeed);
            mTbiPhotos = findViewById(R.id.tbiPhotos);
            mTbiUsersActivities = findViewById(R.id.tbiUsersActivities);

            mLlDisplayUserInfo = findViewById(R.id.llInfoDisplay);
            mLlEditUserInfo = findViewById(R.id.llInfoEdit);

            mTvLblIntroduceEdit = findViewById(R.id.txtLblIntroduceYourSelfEdit);
            mTvLblIntroduceDone = findViewById(R.id.txtLblIntroduceYourSelfDone);

            mTvLivesIn = findViewById(R.id.txtLivesIn);
            mTvFromPlace = findViewById(R.id.txtFromPlace);
            mTvGender = findViewById(R.id.txtGender);
            mTvRelationShipStatus = findViewById(R.id.txtRelationshipStatus);
            mTvDob = findViewById(R.id.txtDobData);
            mTvFavTravelQuote = findViewById(R.id.txtFavTravelQuote);
            mTvBio = findViewById(R.id.txtBio);

            mEdtLivesIn = findViewById(R.id.edtLivesIn);
            mEdtFromPlace = findViewById(R.id.edtFromPlace);
            mEdtRelationShipStatus = findViewById(R.id.edtRelationshipStatus);
            mEdtDob = findViewById(R.id.edtDobData);
            mEdtFavTravelQuote = findViewById(R.id.edtFavTravelQuote);
            mEdtBio = findViewById(R.id.edtBio);

            mTilLivesIn = findViewById(R.id.mTilLivesIn);
            mTilFromPlace = findViewById(R.id.mTilFromPlace);
            mTilGender = findViewById(R.id.mTilGenderData);
            mTilRelationShipStatus = findViewById(R.id.mTilRelationshipStatus);
            mTilDob = findViewById(R.id.mTilDobData);
            mTilFavTravelQuote = findViewById(R.id.mTilFavTravelQuote);
            mTilBio = findViewById(R.id.mTilBio);

            mSprGender = findViewById(R.id.sprGenderData);

            mTbUsersActivity = findViewById(R.id.tbUsersActivity);

            mTvLblIntroduceEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toShowEditUserInfo();
                }
            });

            mTvLblIntroduceDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toUpdateUserInfoApi();
                }
            });

            if (User.getUser(UserInfoActivity.this).getPicPath() != null) {
                Picasso.with(UserInfoActivity.this).load(AppConstants.URL + User.getUser(UserInfoActivity.this).getPicPath().replaceAll("\\s", "%20")).into(mIvProfilePic);
            }

            mIvProfilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                }
            });

            mIvPostCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    multiSelectImage();
                }
            });

            mIvPostTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            mTvUserFollowers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceMainHomePage(new UserFollowersListFragment());
                }
            });

            mTvUserFollowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceMainHomePage(new UserFollowingListFragment());
                }
            });

            mBtnPostSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (images.size() > 0) {
                        if (images.size() > 1) {
                            MyPost.toUploadAlbum(UserInfoActivity.this, UserInfoActivity.this, mEdtPostData.getText().toString(), images);
                        } else {
                            MyPost.toUploadImage(UserInfoActivity.this, UserInfoActivity.this, mEdtPostData.getText().toString(), images);
                        }
                    } else {
                        MyPost.toUpdateMyPost(UserInfoActivity.this, "POST", null, null, mEdtPostData.getText().toString().trim());
                    }
                }
            });

            mEdtDob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyBoard.hideKeyboard(UserInfoActivity.this);
//                    Log.d("HmAPp", " DatePicker : " + CommonFunctions.toOpenDatePicker(UserInfoActivity.this));
                    CommonFunctions.toOpenDatePicker(UserInfoActivity.this, mEdtDob);
                }
            });

            replaceTabData(new UserTab1Fragment());

            mTbUsersActivity.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        case 0:
                            replaceTabData(new UserTab1Fragment());
                            break;
                        case 1:
                            replaceTabData(new UserTab2Fragment());
                            break;
                        case 2:
                            replaceTabData(new UserTab3Fragment());
                            break;
                        default:
                            replaceTabData(new UserTab1Fragment());
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            checkInternetConnection();
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public void replaceMainHomePage(Fragment fragment) {
        Log.d("HmApp", " user fragment " + fragment.getTag() + " : " + fragment.getId() + ": " + fragment.getClass().getName());
        getSupportFragmentManager()
                .beginTransaction()
//                .replace(R.id.flHomeContainer, fragment)
                .add(R.id.flUserHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                Log.d("HmApp", "User onBackStackChanged : " + getSupportFragmentManager().getBackStackEntryCount() + " : " + getSupportFragmentManager().getFragments());
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    startActivity(new Intent(UserInfoActivity.this, MainHomeActivity.class));
                }
            }
        });
    }

    //for Tab
    public void replaceTabData(Fragment fragment) {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flUsersDataContainer, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(UserInfoActivity.this)) {
                toDisplayUserInfo();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), UserInfoActivity.this);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toShowDisplayUserInfo() {
        mLlDisplayUserInfo.setVisibility(View.VISIBLE);
        mTvLblIntroduceEdit.setVisibility(View.VISIBLE);

        mTvLivesIn.setVisibility(View.VISIBLE);
        mTvFromPlace.setVisibility(View.VISIBLE);
        mTvGender.setVisibility(View.VISIBLE);
        mTvRelationShipStatus.setVisibility(View.VISIBLE);
        mTvDob.setVisibility(View.VISIBLE);
        mTvFavTravelQuote.setVisibility(View.VISIBLE);
        mTvBio.setVisibility(View.VISIBLE);
    }

    private void toHideDisplayUSerInfo() {
        mLlDisplayUserInfo.setVisibility(View.GONE);
        mTvLblIntroduceEdit.setVisibility(View.GONE);

        mTvLivesIn.setVisibility(View.GONE);
        mTvFromPlace.setVisibility(View.GONE);
        mTvGender.setVisibility(View.GONE);
        mTvRelationShipStatus.setVisibility(View.GONE);
        mTvDob.setVisibility(View.GONE);
        mTvFavTravelQuote.setVisibility(View.GONE);
        mTvBio.setVisibility(View.GONE);
    }

    private void toShowEditUserInfo() {
        toHideDisplayUSerInfo();
        mLlEditUserInfo.setVisibility(View.VISIBLE);
        mTvLblIntroduceDone.setVisibility(View.VISIBLE);

        mTilLivesIn.setVisibility(View.VISIBLE);
        mTilFromPlace.setVisibility(View.VISIBLE);
        mTilGender.setVisibility(View.VISIBLE);
        mTilRelationShipStatus.setVisibility(View.VISIBLE);
        mTilDob.setVisibility(View.VISIBLE);
        mTilFavTravelQuote.setVisibility(View.VISIBLE);
        mTilBio.setVisibility(View.VISIBLE);

        mEdtLivesIn.setVisibility(View.VISIBLE);
        mEdtFromPlace.setVisibility(View.VISIBLE);

        mEdtRelationShipStatus.setVisibility(View.VISIBLE);
        mEdtDob.setVisibility(View.VISIBLE);
        mEdtFavTravelQuote.setVisibility(View.VISIBLE);
        mEdtBio.setVisibility(View.VISIBLE);
        mSprGender.setVisibility(View.VISIBLE);

        mEdtLivesIn.setText(User.getUser(UserInfoActivity.this).getLivesIn());
        mEdtFromPlace.setText(User.getUser(UserInfoActivity.this).getFromDest());
//        mSprGender.setText(User.getUser(UserInfoActivity.this).getGender());
        mEdtRelationShipStatus.setText(User.getUser(UserInfoActivity.this).getRelationStatus());
        mEdtDob.setText(User.getUser(UserInfoActivity.this).getDob());
        mEdtFavTravelQuote.setText(User.getUser(UserInfoActivity.this).getFavQuote());
        mEdtBio.setText(User.getUser(UserInfoActivity.this).getBio());
        if (User.getUser(UserInfoActivity.this).getGender().toLowerCase().contains("f")) {
            mSprGender.setSelection(1);
        } else if (User.getUser(UserInfoActivity.this).getGender().toLowerCase().contains("o")) {
            mSprGender.setSelection(2);
        } else {
            mSprGender.setSelection(0);
        }
    }

    private void toHideEditUserInfo() {
        mLlEditUserInfo.setVisibility(View.GONE);
        mTvLblIntroduceDone.setVisibility(View.GONE);

        mTilLivesIn.setVisibility(View.GONE);
        mTilFromPlace.setVisibility(View.GONE);
        mTilGender.setVisibility(View.GONE);
        mTilRelationShipStatus.setVisibility(View.GONE);
        mTilDob.setVisibility(View.GONE);
        mTilFavTravelQuote.setVisibility(View.GONE);
        mTilBio.setVisibility(View.GONE);

        mEdtLivesIn.setVisibility(View.GONE);
        mEdtFromPlace.setVisibility(View.GONE);

        mEdtRelationShipStatus.setVisibility(View.GONE);
        mEdtDob.setVisibility(View.GONE);
        mEdtFavTravelQuote.setVisibility(View.GONE);
        mEdtBio.setVisibility(View.GONE);
        mSprGender.setVisibility(View.GONE);
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
        Log.d("HmApp", " Result : " + requestCode);

        if (requestCode == SELECT_PICTURES) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;
                    while (currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        images.add(imageUri);
                        Log.d("HmApp", " Image uri : " + imageUri + ":" + imageUri.toString());
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        currentItem = currentItem + 1;
                    }

                    Log.d("HmApp", " " + count + " : " + images);
                } else if (data.getData() != null) {
                    String imagePath = data.getData().getPath();
                    images.add(Uri.fromFile(new File(data.getData().getPath())));
                    Log.d("HmApp", " " + imagePath + " :  " + images);
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        try {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File dir = new File(
                    Environment.getExternalStorageDirectory()
                            + "/Profile");
            // have the object build the directory structure, if needed.
            if (!dir.exists()) {
                dir.mkdirs();
            }
//            File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            File file = new File(dir, "HMC" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                file.createNewFile();
                fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();
                UserData.toUploadProfilePic(UserInfoActivity.this,
                        new VolleyMultipartRequest.DataPart(User.getUser(UserInfoActivity.this).getUid() + CommonFunctions.getDeviceUniqueID(UserInfoActivity.this) + file.getName(),
                                CommonFunctions.readBytes(Uri.fromFile(file), UserInfoActivity.this), "image/jpeg"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mIvProfilePic.setImageBitmap(thumbnail);
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(UserInfoActivity.this.getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File dir = new File(
                        Environment.getExternalStorageDirectory()
                                + "/Profile");
                // have the object build the directory structure, if needed.
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                Log.d("HmApp", " Path : " + dir + " : " + dir.exists());

                File f = new File(dir,
                        "HMG" + Calendar.getInstance().getTimeInMillis() + ".jpg");
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(UserInfoActivity.this,
                        new String[]{f.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::--->" + f.getAbsolutePath() + " : " + f.getName() + ": " + f.getCanonicalPath() + f.exists());

                UserData.toUploadProfilePic(UserInfoActivity.this,
                        new VolleyMultipartRequest.DataPart(
                                User.getUser(UserInfoActivity.this).getUid()
                                        + "p_" + CommonFunctions.getDeviceUniqueID(UserInfoActivity.this)
                                        + "_" + f.getName(),
                                CommonFunctions.readBytes(Uri.fromFile(f), UserInfoActivity.this), "image/jpeg"));
//                return f.getAbsolutePath();
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
        }
        mIvProfilePic.setImageBitmap(bm);
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
        }
    }

    private void cameraIntent() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception | Error e) {
            e.printStackTrace();
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
        }
    }

    private void toUpdateUserInfoApi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VolleySingleton.getInstance(UserInfoActivity.this)
                            .addToRequestQueue(
                                    new StringRequest(Request.Method.POST,

                                            AppConstants.URL + getResources().getString(R.string.str_register_login) + "." + getResources().getString(R.string.str_php),
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String res) {
                                                    try {
                                                        Log.d("HmApp", " update 1 " + res.trim());
                                                        if (res != null) {
                                                            JSONObject response = new JSONObject(res.trim());
//                                                Log.d("HmApp", " update 2 " + response);
                                                            //{"status":1,"msg":"Update Successful"}
                                                            if (response != null) {
                                                                if (!response.isNull("status")) {
                                                                    if (response.getInt("status") == 1) {
                                                                        CommonFunctions.toDisplayToast(getResources().getString(R.string.str_successfully_updated), UserInfoActivity.this);
                                                                        toHideEditUserInfo();

                                                                        User.getUser(UserInfoActivity.this).setLivesIn(mEdtLivesIn.getText().toString().trim());
                                                                        User.getUser(UserInfoActivity.this).setFromDest(mEdtFromPlace.getText().toString().trim());
                                                                        User.getUser(UserInfoActivity.this).setGender(mSprGender.getSelectedItem().toString());
                                                                        User.getUser(UserInfoActivity.this).setRelationStatus(mEdtRelationShipStatus.getText().toString().trim());
                                                                        User.getUser(UserInfoActivity.this).setDob(mEdtDob.getText().toString().trim());
                                                                        User.getUser(UserInfoActivity.this).setFavQuote(mEdtFavTravelQuote.getText().toString().trim());
                                                                        User.getUser(UserInfoActivity.this).setBio(mEdtBio.getText().toString().trim());
                                                                        User.getUser(UserInfoActivity.this).setUser(User.getUser(UserInfoActivity.this));
                                                                        AppDataStorage.setUserInfo(UserInfoActivity.this);
                                                                        AppDataStorage.getUserInfo(UserInfoActivity.this);
                                                                        toDisplayUserInfo();
                                                                    } else {
                                                                        CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), UserInfoActivity.this);
                                                                    }
                                                                }
                                                            } else {
                                                                CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), UserInfoActivity.this);
                                                            }
                                                        } else {
                                                            CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), UserInfoActivity.this);
                                                        }
                                                    } catch (Exception | Error e) {
                                                        e.printStackTrace();
                                                        CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), UserInfoActivity.this);
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    error.printStackTrace();
                                                    CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), UserInfoActivity.this);
                                                }
                                            }
                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put(getResources().getString(R.string.str_action_), getString(R.string.str_user_info_update));
                                            params.put(getResources().getString(R.string.str_id), User.getUser(UserInfoActivity.this).getUid());
                                            params.put(getResources().getString(R.string.str_lives_in), mEdtLivesIn.getText().toString().trim());
                                            params.put(getResources().getString(R.string.str_from_place), mEdtFromPlace.getText().toString().trim());
                                            params.put(getResources().getString(R.string.str_gender), mSprGender.getSelectedItem().toString());
                                            params.put(getResources().getString(R.string.str_rel_status), mEdtRelationShipStatus.getText().toString().trim());
                                            params.put(getResources().getString(R.string.str_fav_quote), mEdtFavTravelQuote.getText().toString().trim());
                                            params.put(getResources().getString(R.string.str_dob), mEdtDob.getText().toString().trim());
                                            params.put(getResources().getString(R.string.str_bio), mEdtBio.getText().toString().trim());
                                            Log.d("HM_URL", " update_params " + params);
                                            return params;
                                        }
                                    }
                                    , getResources().getString(R.string.str_user_info_update));
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), UserInfoActivity.this);
                }
            }
        }).start();
    }

    private void toDisplayUserInfo() throws Exception, Error {
        mTvUserName.setText(User.getUser(UserInfoActivity.this).getName());
        mTvUsersReferralCode.setText(getResources().getString(R.string.str_referral_code) + " : " + User.getUser(UserInfoActivity.this).getReferralCode());
        mTvLivesIn.setText(User.getUser(UserInfoActivity.this).getLivesIn());
        mTvFromPlace.setText(User.getUser(UserInfoActivity.this).getFromDest());
        mTvGender.setText(User.getUser(UserInfoActivity.this).getGender());
        mTvRelationShipStatus.setText(User.getUser(UserInfoActivity.this).getRelationStatus());
        mTvDob.setText(User.getUser(UserInfoActivity.this).getDob());
        mTvFavTravelQuote.setText(User.getUser(UserInfoActivity.this).getFavQuote());
        mTvBio.setText(User.getUser(UserInfoActivity.this).getBio());
        toShowDisplayUserInfo();
    }

    @Override
    public void onBackPressed() {
        Log.d("HmApp", "MainHome onBackPress : " + getFragmentManager().getBackStackEntryCount());
        Log.d("HmApp", "MainHome onBackStackChanged 1 : " + getSupportFragmentManager().getBackStackEntryCount() + " : " + getSupportFragmentManager().getFragments());
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            Log.d("HmApp", "MainHome kl");
            getFragmentManager().popBackStack();
        } else {
            Log.d("HmApp", "MainHome kj");
//            popBackStack();
            super.onBackPressed();
//            finish();
        }
    }
}