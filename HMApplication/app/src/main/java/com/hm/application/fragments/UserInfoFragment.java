package com.hm.application.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.common.MyPost;
import com.hm.application.common.UserData;
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

public class UserInfoFragment extends Fragment {

    private NestedScrollView mSvUpMain;
    private LinearLayout mLlUpMain, mLlUserActivities, mllEditSC;
    private RelativeLayout mRlProfileImageData, mRlUserData, mRlUserData2;
    private View mView1;
    private FrameLayout mFlUsersDataContainer;
    private RatingBar mRbUserRatingData;
    private ImageView mIvProfilePic, mIvFlag, mIvShare, mIvPostCamera, mIvPostTag;
    private TextView mTvUserFollowing, mTvUserFollowers, mTvUserName, mTvUserExtraActivities, mTvUsersReferralCode, mTvUsersDescription;
    private TextView mTvLblIntroduceEdit, mTvLblIntroduceDone, mTvLivesIn, mTvFromPlace, mTvGender, mTvRelationShipStatus, mTvDob, mTvFavTravelQuote, mTvBio;
    private TextInputLayout mTilLivesIn, mTilFromPlace, mTilGender, mTilRelationShipStatus, mTilDob, mTilFavTravelQuote, mTilBio;
    private TextInputEditText mEdtLivesIn, mEdtFromPlace, mEdtRelationShipStatus, mEdtDob, mEdtFavTravelQuote, mEdtBio;
    private EditText mEdtPostData;
    private Spinner mSprGender;
    private GridLayout mGv;
    private Button mBtnFollow, mBtnPostSubmit, mBtnEditSubmit, mBtnCancel;
    private TabItem mTbiUsersFeed, mTbiPhotos, mTbiUsersActivities;
    private TabLayout mTbUsersActivity;
    private LinearLayout mLlDisplayUserInfo, mLlEditUserInfo;
    private int SELECT_PICTURES = 7, REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ArrayList<Uri> images = new ArrayList<>();
    private boolean status = true;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataBinding();
    }

    private void dataBinding() {
        try {
            // Post
            mBtnPostSubmit = getActivity().findViewById(R.id.btnPostSubmit);
            mEdtPostData = getActivity().findViewById(R.id.edt_desc_post);
            mIvPostCamera = getActivity().findViewById(R.id.imgIconCam);
            mIvPostTag = getActivity().findViewById(R.id.imgIconTag);
            mGv = getActivity().findViewById(R.id.mGvImages);

            mEdtPostData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEdtPostData.setFocusable(true);
                    mEdtPostData.setFocusableInTouchMode(true);
                    mEdtPostData.requestFocus();
                    KeyBoard.openKeyboard(getActivity());

                }
            });

            mSvUpMain = getActivity().findViewById(R.id.svUpMain);

            mSvUpMain.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (mSvUpMain.getScrollY() > 170) {
                        if (status) {
                            status = false;
                            replaceTabData(new UserTab1Fragment());
                        }
                    }
                }
            });

            mLlUpMain = getActivity().findViewById(R.id.llUpMain);
            mLlUserActivities = getActivity().findViewById(R.id.llUserActivities);
            mllEditSC = getActivity().findViewById(R.id.llEditSubmitCancel);

            mRlProfileImageData = getActivity().findViewById(R.id.rlProfileImageData);
            mRlUserData = getActivity().findViewById(R.id.rlUserData);
            mRlUserData2 = getActivity().findViewById(R.id.rlUserData2);

            mView1 = getActivity().findViewById(R.id.v11);

            mFlUsersDataContainer = getActivity().findViewById(R.id.flUsersDataContainer);

            mRbUserRatingData = getActivity().findViewById(R.id.rbUserRatingData);

            mIvProfilePic = getActivity().findViewById(R.id.imgProfilePic);
            mIvFlag = getActivity().findViewById(R.id.ivFlag);
            mIvShare = getActivity().findViewById(R.id.ivShare);

            mTvUserFollowing = getActivity().findViewById(R.id.tvUserFollowing);
            mTvUserFollowing.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mTvUserFollowing.setText(getString(R.string.str_following) + User.getUser(getContext()).getFollowing_count());

            mTvUserFollowers = getActivity().findViewById(R.id.tvUserFollowers);
            mTvUserFollowers.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mTvUserFollowers.setText(getString(R.string.str_followers) + User.getUser(getContext()).getFollowers_count());

            mTvUserName = getActivity().findViewById(R.id.txtUserName);
            mTvUserName.setTypeface(HmFonts.getRobotoBold(getContext()));
            mTvUserName.setText(CommonFunctions.firstLetterCaps(User.getUser(getContext()).getUsername()));

            mTvUserExtraActivities = getActivity().findViewById(R.id.txtUserExtraActivities);
            mTvUserExtraActivities.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mTvUsersReferralCode = getActivity().findViewById(R.id.txtUsersReferralCode);
            mTvUsersReferralCode.setTypeface(HmFonts.getRobotoBold(getContext()));

            mTvUserName = getActivity().findViewById(R.id.txtUserName);
            mTvUserName.setTypeface(HmFonts.getRobotoBold(getContext()));

            mTvUserExtraActivities = getActivity().findViewById(R.id.txtUserExtraActivities);
            mTvUserExtraActivities.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mTvUsersReferralCode = getActivity().findViewById(R.id.txtUsersReferralCode);
            mTvUsersReferralCode.setTypeface(HmFonts.getRobotoBold(getContext()));

            mTvUsersDescription = getActivity().findViewById(R.id.txtUsersDescription);
            mTvUsersDescription.setTypeface(HmFonts.getRobotoBold(getContext()));

            mBtnFollow = getActivity().findViewById(R.id.btnFollow);
            mTvUserFollowing.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mTbiUsersFeed = getActivity().findViewById(R.id.tbiUsersFeed);
            mTbiPhotos = getActivity().findViewById(R.id.tbiPhotos);
            mTbiUsersActivities = getActivity().findViewById(R.id.tbiUsersActivities);

            mLlDisplayUserInfo = getActivity().findViewById(R.id.llInfoDisplay);
            mLlEditUserInfo = getActivity().findViewById(R.id.llInfoEdit);

            mTvLblIntroduceEdit = getActivity().findViewById(R.id.txtLblIntroduceYourSelfEdit);
            mTvLblIntroduceEdit.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTvLivesIn = getActivity().findViewById(R.id.txtLivesIn);
            mTvLivesIn.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTvFromPlace = getActivity().findViewById(R.id.txtFromPlace);
            mTvFromPlace.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTvGender = getActivity().findViewById(R.id.txtGender);
            mTvGender.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTvRelationShipStatus = getActivity().findViewById(R.id.txtRelationshipStatus);
            mTvRelationShipStatus.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTvDob = getActivity().findViewById(R.id.txtDobData);
            mTvDob.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTvFavTravelQuote = getActivity().findViewById(R.id.txtFavTravelQuote);
            mTvFavTravelQuote.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTvBio = getActivity().findViewById(R.id.txtBio);
            mTvBio.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mEdtLivesIn = getActivity().findViewById(R.id.edtLivesIn);
            mEdtLivesIn.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mEdtFromPlace = getActivity().findViewById(R.id.edtFromPlace);
            mEdtFromPlace.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mEdtRelationShipStatus = getActivity().findViewById(R.id.edtRelationshipStatus);
            mEdtRelationShipStatus.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mEdtDob = getActivity().findViewById(R.id.edtDobData);
            mEdtDob.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mEdtFavTravelQuote = getActivity().findViewById(R.id.edtFavTravelQuote);
            mEdtFavTravelQuote.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mEdtBio = getActivity().findViewById(R.id.edtBio);
            mEdtBio.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilLivesIn = getActivity().findViewById(R.id.mTilLivesIn);
            mTilLivesIn.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilFromPlace = getActivity().findViewById(R.id.mTilFromPlace);
            mTilFromPlace.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilGender = getActivity().findViewById(R.id.mTilGenderData);
            mTilGender.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilRelationShipStatus = getActivity().findViewById(R.id.mTilRelationshipStatus);
            mTilRelationShipStatus.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilDob = getActivity().findViewById(R.id.mTilDobData);
            mTilDob.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilFavTravelQuote = getActivity().findViewById(R.id.mTilFavTravelQuote);
            mTilFavTravelQuote.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilBio = getActivity().findViewById(R.id.mTilBio);
            mTilBio.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mSprGender = getActivity().findViewById(R.id.sprGenderData);

            mTbUsersActivity = getActivity().findViewById(R.id.tbUsersActivity);

            mBtnEditSubmit = getActivity().findViewById(R.id.btnSubmitEdit);
            mBtnEditSubmit.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mBtnCancel = getActivity().findViewById(R.id.btnCancelEdit);
            mBtnCancel.setTypeface(HmFonts.getRobotoRegular(getContext()));


            mTvLblIntroduceEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toShowEditUserInfo();
                }
            });

            mBtnEditSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toUpdateUserInfoApi();
                }
            });

            if (User.getUser(getContext()).getPicPath() != null) {
                Picasso.with(getContext()).load(AppConstants.URL + User.getUser(getContext()).getPicPath().replaceAll("\\s", "%20")).into(mIvProfilePic);
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
                                MyPost.toUploadAlbum(getContext(), getActivity(), mEdtPostData.getText().toString(), images);
                            } else {
                                Log.d("Hmapp", " images 3 " + images.get(0));
                                MyPost.toUploadImage(getContext(), getActivity(), mEdtPostData.getText().toString(), images.get(0));
                            }
                        } else {
                            MyPost.toUpdateMyPost(getContext(), "POST", null, null, mEdtPostData.getText().toString().trim());
                        }
                    } else {
                        CommonFunctions.toDisplayToast(" Empty Data ", getContext());
                    }
                }
            });

            mEdtDob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyBoard.hideKeyboard(getActivity());
//                    Log.d("HmAPp", " DatePicker : " + CommonFunctions.toOpenDatePicker(getContext()));
                    CommonFunctions.toOpenDatePicker(getContext(), mEdtDob);
                }
            });

            mEdtDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (v.getId() == mEdtDob.getId()) {
                            CommonFunctions.toOpenDatePicker(getContext(), mEdtDob);
                        } else {
                            CommonFunctions.toDisplayToast(" no view found " + v.getId(), getContext());
                        }
                    } else {
                        CommonFunctions.toDisplayToast(" no Focus found " + v.getId(), getContext());
                    }
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
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    //for Tab
    public void replaceTabData(Fragment fragment) {
        try {
            getActivity().getSupportFragmentManager()
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
            if (CommonFunctions.isOnline(getContext())) {
                toDisplayUserInfo();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
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

        mBtnEditSubmit.setVisibility(View.VISIBLE);
        mBtnCancel.setVisibility(View.VISIBLE);
        mllEditSC.setVisibility(View.VISIBLE);

        mEdtLivesIn.setText(User.getUser(getContext()).getLivesIn());
        mEdtFromPlace.setText(User.getUser(getContext()).getFromDest());
//        mSprGender.setText(User.getUser(getContext()).getGender());
        mEdtRelationShipStatus.setText(User.getUser(getContext()).getRelationStatus());
        mEdtDob.setText(User.getUser(getContext()).getDob());
        mEdtFavTravelQuote.setText(User.getUser(getContext()).getFavQuote());
        mEdtBio.setText(User.getUser(getContext()).getBio());
        if (User.getUser(getContext()).getGender().toLowerCase().contains("f")) {
            mSprGender.setSelection(1);
        } else if (User.getUser(getContext()).getGender().toLowerCase().contains("o")) {
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

        mBtnEditSubmit.setVisibility(View.GONE);
        mBtnCancel.setVisibility(View.GONE);
        mllEditSC.setVisibility(View.GONE);

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
                        Log.d("HmApp", " Image " + count + " : " + images);
                    } else if (data.getData() != null) {
                        String imagePath = data.getData().getPath();
//                        images.add(Uri.fromFile(new File(data.getData().getPath())));
                        Log.d("HmApp", " Image " + imagePath + " :  " + images + ":::" + MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData()));
                        images.add(Uri.fromFile(toSaveImages(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData()), "HMC", false)));
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
            ImageView mIv = new ImageView(getContext());
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
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
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
            File dir = new File(
                    Environment.getExternalStorageDirectory() + "/Profile");
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
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath() + " : " + f.getName() + ": " + f.getCanonicalPath() + f.exists());
            if (b) {
                UserData.toUploadProfilePic(getContext(),
                        new VolleyMultipartRequest.DataPart(
                                User.getUser(getContext()).getUid()
                                        + "p_" + CommonFunctions.getDeviceUniqueID(getActivity())
                                        + "_" + f.getName(),
                                CommonFunctions.readBytes(Uri.fromFile(f), getActivity()), "image/jpeg"));
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
                    CommonFunctions.toDisplayToast("Permission", getContext());
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        try {
            final CharSequence[] items = {"Take Photo", "Choose from Library",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result = Utility.checkPermission(getContext());
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
                    VolleySingleton.getInstance(getContext())
                            .addToRequestQueue(
                                    new StringRequest(Request.Method.POST,

                                            AppConstants.URL + getResources().getString(R.string.str_register_login) + getResources().getString(R.string.str_php),
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String res) {
                                                    try {
                                                        Log.d("HmApp", " update 1 " + res.trim());
                                                        if (res != null) {
                                                            JSONObject response = new JSONObject(res.trim());
                                                            //{"status":1,"msg":"Update Successful"}
                                                            if (response != null) {
                                                                if (!response.isNull("status")) {
                                                                    if (response.getInt("status") == 1) {
                                                                        CommonFunctions.toDisplayToast(getResources().getString(R.string.str_successfully_updated), getContext());
                                                                        toHideEditUserInfo();

                                                                        User.getUser(getContext()).setLivesIn(mEdtLivesIn.getText().toString().trim());
                                                                        User.getUser(getContext()).setFromDest(mEdtFromPlace.getText().toString().trim());
                                                                        User.getUser(getContext()).setGender(mSprGender.getSelectedItem().toString());
                                                                        User.getUser(getContext()).setRelationStatus(mEdtRelationShipStatus.getText().toString().trim());
                                                                        User.getUser(getContext()).setDob(mEdtDob.getText().toString().trim());
                                                                        User.getUser(getContext()).setFavQuote(mEdtFavTravelQuote.getText().toString().trim());
                                                                        User.getUser(getContext()).setBio(mEdtBio.getText().toString().trim());
                                                                        User.getUser(getContext()).setUser(User.getUser(getContext()));

                                                                        AppDataStorage.setUserInfo(getContext());
                                                                        AppDataStorage.getUserInfo(getContext());

                                                                        toDisplayUserInfo();
                                                                    } else {
                                                                        CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), getContext());
                                                                    }
                                                                }
                                                            } else {
                                                                CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), getContext());
                                                            }
                                                        } else {
                                                            CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), getContext());
                                                        }
                                                    } catch (Exception | Error e) {
                                                        e.printStackTrace();
                                                        CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), getContext());
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    error.printStackTrace();
                                                    CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), getContext());
                                                }
                                            }
                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put(getResources().getString(R.string.str_action_), getString(R.string.str_user_info_update));
                                            params.put(getResources().getString(R.string.str_id), User.getUser(getContext()).getUid());
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
                    CommonFunctions.toDisplayToast(getResources().getString(R.string.str_error_unable_to_update), getContext());
                }
            }
        }).start();
    }

    private void toDisplayUserInfo() throws Exception, Error {
        mTvUserName.setText(User.getUser(getContext()).getName());
        mTvUsersReferralCode.setText(getResources().getString(R.string.str_referral_code) + " : " + User.getUser(getContext()).getReferralCode());
        mTvLivesIn.setText(User.getUser(getContext()).getLivesIn());
        mTvFromPlace.setText(User.getUser(getContext()).getFromDest());
        mTvGender.setText(User.getUser(getContext()).getGender());
        mTvRelationShipStatus.setText(User.getUser(getContext()).getRelationStatus());
        mTvDob.setText(User.getUser(getContext()).getDob());
        mTvFavTravelQuote.setText(User.getUser(getContext()).getFavQuote());
        mTvBio.setText(User.getUser(getContext()).getBio());
        toShowDisplayUserInfo();
    }
}
