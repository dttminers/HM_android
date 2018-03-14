package com.hm.application.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.application.R;
import com.hm.application.utils.HmFonts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class UserProfileFeaturesFragment extends Fragment {

    private ScrollView mSvUpMain;
    private LinearLayout mLlUpMain, mLlUserActivities;
    private RelativeLayout mRlProfileImageData, mRlUserData, mRlUserData2;
    private View mView1;
    private FrameLayout mFlUsersDataContainer;
    private RatingBar mRbUserRatingData;
    private ImageView mIvProfilePic, mIvFlag, mIvShare;
    private TextView mtvUserFollowing, mtvUserFollowers, mtxtUserName, mtxtUserExtraActivities, mtxtUsersReferralCode, mtxtUsersDescription;
    private Button mbtnFollow;
    private TabItem mtbiUsersFeed, mtbiPhotos, mtbiUsersActivities;
    private TabLayout mtbUsersActivity;

    private int GALLERY = 1, CAMERA = 2;

    public UserProfileFeaturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile_features, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(" HmApp ", " savedInstanceState " + savedInstanceState);
        dataBinding();
    }

    private void dataBinding() {
        mSvUpMain = (ScrollView) getActivity().findViewById(R.id.svUpMain);

        mLlUpMain = (LinearLayout) getActivity().findViewById(R.id.llUpMain);
        mLlUserActivities = (LinearLayout) getActivity().findViewById(R.id.llUserActivities);

        mRlProfileImageData = (RelativeLayout) getActivity().findViewById(R.id.rlProfileImageData);
        mRlUserData = (RelativeLayout) getActivity().findViewById(R.id.rlUserData);
        mRlUserData2 = (RelativeLayout) getActivity().findViewById(R.id.rlUserData2);

        mView1 = (View) getActivity().findViewById(R.id.v11);

        mFlUsersDataContainer = (FrameLayout) getActivity().findViewById(R.id.flUsersDataContainer);

        mRbUserRatingData = (RatingBar) getActivity().findViewById(R.id.rbUserRatingData);

        mIvProfilePic = (ImageView) getActivity().findViewById(R.id.imgProfilePic);
        mIvFlag = (ImageView) getActivity().findViewById(R.id.ivFlag);
        mIvShare = (ImageView) getActivity().findViewById(R.id.ivShare);

        mtvUserFollowing = (TextView) getActivity().findViewById(R.id.tvUserFollowing);
        mtvUserFollowing.setTypeface(HmFonts.getRobotoMedium(getContext()));
        mtvUserFollowers = (TextView) getActivity().findViewById(R.id.tvUserFollowers);
        mtvUserFollowers.setTypeface(HmFonts.getRobotoMedium(getContext()));
        mtxtUserName = (TextView) getActivity().findViewById(R.id.txtUserName);
        mtxtUserName.setTypeface(HmFonts.getRobotoBold(getContext()));
        mtxtUserExtraActivities = (TextView) getActivity().findViewById(R.id.txtUserExtraActivities);
        mtxtUserExtraActivities.setTypeface(HmFonts.getRobotoMedium(getContext()));
        mtxtUsersReferralCode = (TextView) getActivity().findViewById(R.id.txtUsersReferralCode);
        mtxtUsersReferralCode.setTypeface(HmFonts.getRobotoBold(getContext()));
        mtxtUsersDescription = (TextView) getActivity().findViewById(R.id.txtUsersDescription);
        mtxtUsersDescription.setTypeface(HmFonts.getRobotoBold(getContext()));

        mbtnFollow = (Button) getActivity().findViewById(R.id.btnFollow);
        mtvUserFollowing.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtbiUsersFeed = (TabItem) getActivity().findViewById(R.id.tbiUsersFeed);
        mtbiPhotos = (TabItem) getActivity().findViewById(R.id.tbiPhotos);
        mtbiUsersActivities = (TabItem) getActivity().findViewById(R.id.tbiUsersActivities);

        mtbUsersActivity = (TabLayout) getActivity().findViewById(R.id.tbUsersActivity);
        showPictureDialog();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("HMApp", "onActivityResult" + requestCode + ":" + resultCode + ":" + data.getExtras());
//        if (resultCode == RESULT_CANCELED) {
//            return;
//        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    mIvProfilePic.setImageBitmap(bitmap);

                } catch (Exception | Error e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mIvShare.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File wallpaperDirectory = new File(
                    Environment.getExternalStorageDirectory() + "/Profile/Pictures");
            // have the object build the directory structure, if needed.
            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }


            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
        return "Empty";
    }
}