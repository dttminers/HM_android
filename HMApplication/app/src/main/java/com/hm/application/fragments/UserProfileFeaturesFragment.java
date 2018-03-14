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

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.hm.application.R;
import com.hm.application.network.VolleyMultipartRequest;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    private void saveProfileAccount() {
        // loading or check internet connection or something...
        // ... then
        String url = "http://www.angga-ari.com/api/something/awesome";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    Log.d("HMAPP", " resultResponse " + resultResponse);
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String message = result.getString("message");

//                    if (status.equals(Constant.REQUEST_SUCCESS)) {
//                        // tell everybody you have succed upload image and post strings
//                        Log.i("Messsage", message);
//                    } else {
//                        Log.i("Unexpected", message);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
//                params.put("name", mNameInput.getText().toString());
//                params.put("location", mLocationInput.getText().toString());
//                params.put("about", mAvatarInput.getText().toString());
//                params.put("contact", mContactInput.getText().toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("avatar", new DataPart("file_avatar.jpg", CommonFunctions.getFileDataFromDrawable(getContext(), mIvProfilePic.getDrawable()), "image/jpeg"));
                params.put("cover", new DataPart("file_cover.jpg", CommonFunctions.getFileDataFromDrawable(getContext(), mIvShare.getDrawable()), "image/jpeg"));

                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(multipartRequest, " Upload Profile Pic");
    }

}