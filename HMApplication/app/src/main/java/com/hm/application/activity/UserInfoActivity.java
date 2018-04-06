package com.hm.application.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.classes.Tb_PlanTrip_Travellers_Info;
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
import java.io.FileOutputStream;
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
    private TextView mTvLblIntroduceEdit, mTvLivesIn, mTvFromPlace, mTvGender, mTvRelationShipStatus, mTvDob, mTvFavTravelQuote, mTvBio;
    private EditText mEdtPostData;
    private GridLayout mGv;
    private Button mBtnFollow, mBtnPostSubmit;
    private TabItem mTbiUsersFeed, mTbiPhotos, mTbiUsersActivities;
    private TabLayout mTbUsersActivity;
    private LinearLayout mLlDisplayUserInfo;
    private int SELECT_PICTURES = 7, REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ArrayList<Uri> images = new ArrayList<>();
    private boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

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

        dataBinding();
    }

    private void dataBinding() {
        try {
            // Post
            mBtnPostSubmit = findViewById(R.id.btnPostSubmit);
            mEdtPostData = findViewById(R.id.edt_desc_post);
            mIvPostCamera = findViewById(R.id.imgIconCam);
            mIvPostTag = findViewById(R.id.imgIconTag);
            mGv = findViewById(R.id.mGvImages);

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

            mSvUpMain.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (mSvUpMain.getScrollY() > 150) {
                        if (status) {
                            status = false;
                            replaceTabData(new UserTab1Fragment());
                        }
                    }
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
            mTvUserFollowing.setText(getString(R.string.str_following) + User.getUser(UserInfoActivity.this).getFollowing_count());

            mTvUserFollowers = findViewById(R.id.tvUserFollowers);
            mTvUserFollowers.setTypeface(HmFonts.getRobotoMedium(UserInfoActivity.this));
            mTvUserFollowers.setText(getString(R.string.str_followers) + User.getUser(UserInfoActivity.this).getFollowers_count());

            mTvUserName = findViewById(R.id.txtUserName);
            mTvUserName.setTypeface(HmFonts.getRobotoBold(UserInfoActivity.this));
            mTvUserName.setText(CommonFunctions.firstLetterCaps(User.getUser(UserInfoActivity.this).getUsername()));

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

            mTvLblIntroduceEdit = findViewById(R.id.txtLblIntroduceYourSelfEdit);
            mTvLblIntroduceEdit.setTypeface(HmFonts.getRobotoRegular(UserInfoActivity.this));

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


            mTbUsersActivity = findViewById(R.id.tbUsersActivity);


            mTvLblIntroduceEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Tb_PlanTrip_Travellers_Info.toFillUserDetailsInfo(this,);
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
                    if (mEdtPostData.getText().toString().trim().length() > 0) {
                        if (images.size() > 0) {
                            Log.d("Hmapp", " images 1 " + images);
                            if (images.size() > 1) {
                                Log.d("Hmapp", " images2 " + images);
                                MyPost.toUploadAlbum(UserInfoActivity.this, UserInfoActivity.this, mEdtPostData.getText().toString(), images);
                            } else {
                                Log.d("Hmapp", " images 3 " + images.get(0));
                                MyPost.toUploadImage(UserInfoActivity.this, UserInfoActivity.this, mEdtPostData.getText().toString(), images.get(0));
                            }
                        } else {
                            MyPost.toUpdateMyPost(UserInfoActivity.this, "POST", null, null, mEdtPostData.getText().toString().trim());
                        }
                    } else {
                        CommonFunctions.toDisplayToast(" Empty Data ", UserInfoActivity.this);
                    }
                }
            });


//            replaceTabData(new UserTab1Fragment());

            mTbUsersActivity.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Log.d("HmaPP", " TAB " + tab.getPosition());
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
                .replace(R.id.flUserHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    //for Tab
    public void replaceTabData(Fragment fragment) {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flUsersDataContainer, fragment)
                    .addToBackStack(fragment.getClass().getName())
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

//    private void toShowEditUserInfo() {
//        toHideDisplayUSerInfo();
//        mLlEditUserInfo.setVisibility(View.VISIBLE);
//        mTvLblIntroduceDone.setVisibility(View.VISIBLE);
//
//        mTilLivesIn.setVisibility(View.VISIBLE);
//        mTilFromPlace.setVisibility(View.VISIBLE);
//        mTilGender.setVisibility(View.VISIBLE);
//        mTilRelationShipStatus.setVisibility(View.VISIBLE);
//        mTilDob.setVisibility(View.VISIBLE);
//        mTilFavTravelQuote.setVisibility(View.VISIBLE);
//        mTilBio.setVisibility(View.VISIBLE);
//
//        mEdtLivesIn.setVisibility(View.VISIBLE);
//        mEdtFromPlace.setVisibility(View.VISIBLE);
//
//        mEdtRelationShipStatus.setVisibility(View.VISIBLE);
//        mEdtDob.setVisibility(View.VISIBLE);
//        mEdtFavTravelQuote.setVisibility(View.VISIBLE);
//        mEdtBio.setVisibility(View.VISIBLE);
//
//        mSprGender.setVisibility(View.VISIBLE);
//
//        mBtnEditSubmit.setVisibility(View.VISIBLE);
//        mBtnCancel.setVisibility(View.VISIBLE);
//        mllEditSC.setVisibility(View.VISIBLE);
//
//
//}
//
//    private void toHideEditUserInfo() {
//        mLlEditUserInfo.setVisibility(View.GONE);
//        mTvLblIntroduceDone.setVisibility(View.GONE);
//
//        mTilLivesIn.setVisibility(View.GONE);
//        mTilFromPlace.setVisibility(View.GONE);
//        mTilGender.setVisibility(View.GONE);
//        mTilRelationShipStatus.setVisibility(View.GONE);
//        mTilDob.setVisibility(View.GONE);
//        mTilFavTravelQuote.setVisibility(View.GONE);
//        mTilBio.setVisibility(View.GONE);
//
//        mEdtLivesIn.setVisibility(View.GONE);
//        mEdtFromPlace.setVisibility(View.GONE);
//
//        mEdtRelationShipStatus.setVisibility(View.GONE);
//        mEdtDob.setVisibility(View.GONE);
//        mEdtFavTravelQuote.setVisibility(View.GONE);
//        mEdtBio.setVisibility(View.GONE);
//        mSprGender.setVisibility(View.GONE);
//
//        mBtnEditSubmit.setVisibility(View.GONE);
//        mBtnCancel.setVisibility(View.GONE);
//        mllEditSC.setVisibility(View.GONE);
//
//    }

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
            if (requestCode == SELECT_PICTURES) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            images.add(imageUri);
                            Log.d("HmApp", " Image uri : " + imageUri + ":" + imageUri.toString());
                            currentItem = currentItem + 1;
                        }
                        Log.d("HmApp", " Image " + count + " : " + images);
                    } else if (data.getData() != null) {
                        String imagePath = data.getData().getPath();
                        Log.d("HmApp", " Image " + imagePath + " :  " + images + ":::" + MediaStore.Images.Media.getBitmap(UserInfoActivity.this.getContentResolver(), data.getData()));
                        images.add(Uri.fromFile(toSaveImages(MediaStore.Images.Media.getBitmap(UserInfoActivity.this.getContentResolver(), data.getData()), "HMC", false)));
                    }
                }
            }

            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE)
                    onSelectFromGalleryResult(data);
                else if (requestCode == REQUEST_CAMERA)
                    onCaptureImageResult(data);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
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
            mGv.addView(mIv);
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void onCaptureImageResult(Intent data) {
        try {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mIvProfilePic.setImageBitmap(thumbnail);
            toSaveImages(thumbnail, "HMC", true);
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(UserInfoActivity.this.getContentResolver(), data.getData());
                mIvProfilePic.setImageBitmap(bm);
                toSaveImages(bm, "HMG", true);
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
        }
    }

    private File toSaveImages(Bitmap bm, String name, boolean b) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File dir = new File(Environment.getExternalStorageDirectory() + "/Profile");
            // have the object build the directory structure, if needed.
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.d("HmApp", " Path : " + dir + " : " + dir.exists());

            File f = new File(dir,
                    name + Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(UserInfoActivity.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath() + " : " + f.getName() + ": " + f.getCanonicalPath() + f.exists());
            if (b) {
                UserData.toUploadProfilePic(UserInfoActivity.this, new VolleyMultipartRequest.DataPart(User.getUser(UserInfoActivity.this).getUid() + "p_" + CommonFunctions.getDeviceUniqueID(UserInfoActivity.this) + "_" + f.getName(), CommonFunctions.readBytes(Uri.fromFile(f), UserInfoActivity.this), "image/jpeg"));
            }
            return f;
        } catch (Exception | Error e) {
            e.printStackTrace();
            return null;
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
        Log.d("HmApp", "User onBackPress : " + getFragmentManager().getBackStackEntryCount());
        Log.d("HmApp", "User onBackStackChanged 1 : " + getSupportFragmentManager().getBackStackEntryCount() + " : " + getSupportFragmentManager().getFragments());
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            Log.d("HmApp", "User kl");
            getFragmentManager().popBackStack();
        } else {
            Log.d("HmApp", "User kj");
//            popBackStack();
            super.onBackPressed();
//            finish();
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

    @Override
    protected void onPause() {
        super.onPause();
//        AppConstants.USER_PROFILE_PAGE_CHANGE = mTbUsersActivity.getSelectedTabPosition();
    }
}