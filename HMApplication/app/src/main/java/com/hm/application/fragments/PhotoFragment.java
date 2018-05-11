package com.hm.application.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.activity.NextActivity;
import com.hm.application.model.User;

import java.io.File;

public class PhotoFragment extends Fragment {

    //constant
    private static final int CAMERA_REQUEST_CODE = 5;

    public boolean isVisible = false;

    private OnFragmentInteractionListener mListener;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toSetCamera();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

        boolean checkPermission(String permissions);

        void verifyPermissions(String[] permissions);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            toShowCamera();

        } else {
            isVisible = false;
        }
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == ChoosePhoto.SELECT_PICTURE_CAMERA) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                choosePhoto.showAlertDialog();
//        }
//    }

    private void toShowCamera() {
        if (mListener.checkPermission(Manifest.permission.CAMERA)) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
//            Intent pickImageIntent = new Intent("com.android.camera.action.CROP");
//            File fileImagePath = new File(
//                    Environment.getExternalStorageDirectory().getAbsolutePath()
//                    Environment.getExternalStorageState()
//                    Environment.DIRECTORY_DOWNLOADS
//                            + "/HmPhoto" + System.currentTimeMillis() + User.getUser(getContext()).getUid() + ".jpg");
//            Uri contentUri = Uri.fromFile(fileImagePath);
////            Uri contentUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", fileImagePath);
            Uri contentUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "tmp_avatar_"
                    + String.valueOf(System.currentTimeMillis())
                    + ".jpg"));
//            pickImageIntent.setDataAndType(contentUri, "image/*");
//            pickImageIntent.putExtra("crop", "true");
//            pickImageIntent.putExtra("aspectX", 1);
//            pickImageIntent.putExtra("aspectY", 1);
//            pickImageIntent.putExtra("outputX", 500);
//            pickImageIntent.putExtra("outputY", 500);
//            pickImageIntent.putExtra("return-data", true);
//            startActivityForResult(pickImageIntent, CAMERA_REQUEST_CODE);
        } else {
            mListener.verifyPermissions(new String[]{Manifest.permission.CAMERA});
        }
    }

    public void toSetCamera() {
        if (isVisible) {
//            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
//            isVisible = false;
            toShowCamera();
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        toSetCamera();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        toSetCamera();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (data != null) {
                Bitmap bitmap;
                bitmap = (Bitmap) data.getExtras().get("data");
                try {
                    Intent intent = new Intent(getActivity(), NextActivity.class);
                    intent.putExtra(getString(R.string.selected_bitmap), bitmap);
                    startActivity(intent);
                } catch (Exception | Error e) {
                    isVisible = false;
                    e.printStackTrace();
                    Crashlytics.logException(e);
                }
            }
        } else {
            getContext().startActivity(new Intent());
        }
    }
}