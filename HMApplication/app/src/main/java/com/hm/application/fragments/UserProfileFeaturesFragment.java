package com.hm.application.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.network.VolleyMultipartRequest;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.hm.application.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
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

    private int GALLERY = 1, CAMERA = 2, SELECT_PICTURES = 7;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private boolean toUpdate = false;
    private String userChoosenTask;

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
        // Post
        mBtnPostSubmit = getActivity().findViewById(R.id.btnPostSubmit);
        mEdtPostData = getActivity().findViewById(R.id.edt_desc_post);
        mIvPostCamera = getActivity().findViewById(R.id.imgIconCam);
        mIvPostTag = getActivity().findViewById(R.id.imgIconTag);
        
        mIvPostCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectImage();
            }
        });

        mSvUpMain = getActivity().findViewById(R.id.svUpMain);

        mLlUpMain = getActivity().findViewById(R.id.llUpMain);
        mLlUserActivities = getActivity().findViewById(R.id.llUserActivities);

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

        mTvUserFollowers = getActivity().findViewById(R.id.tvUserFollowers);
        mTvUserFollowers.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mTvUserName = getActivity().findViewById(R.id.txtUserName);
        mTvUserName.setTypeface(HmFonts.getRobotoBold(getContext()));

        mTvUserExtraActivities = getActivity().findViewById(R.id.txtUserExtraActivities);
        mTvUserExtraActivities.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mTvUsersReferralCode = getActivity().findViewById(R.id.txtUsersReferralCode);
        mTvUsersReferralCode.setTypeface(HmFonts.getRobotoBold(getContext()));

        mTvUserFollowers = getActivity().findViewById(R.id.tvUserFollowers);
        mTvUserFollowers.setTypeface(HmFonts.getRobotoMedium(getContext()));

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
        mTvLblIntroduceDone = getActivity().findViewById(R.id.txtLblIntroduceYourSelfDone);

        mTvLivesIn = getActivity().findViewById(R.id.txtLivesIn);
        mTvFromPlace = getActivity().findViewById(R.id.txtFromPlace);
        mTvGender = getActivity().findViewById(R.id.txtGender);
        mTvRelationShipStatus = getActivity().findViewById(R.id.txtRelationshipStatus);
        mTvDob = getActivity().findViewById(R.id.txtDobData);
        mTvFavTravelQuote = getActivity().findViewById(R.id.txtFavTravelQuote);
        mTvBio = getActivity().findViewById(R.id.txtBio);

        mEdtLivesIn = getActivity().findViewById(R.id.edtLivesIn);
        mEdtFromPlace = getActivity().findViewById(R.id.edtFromPlace);

//        mEdtGender = getActivity().findViewById(R.id.edtGenderData);

        mEdtRelationShipStatus = getActivity().findViewById(R.id.edtRelationshipStatus);
        mEdtDob = getActivity().findViewById(R.id.edtDobData);
        mEdtFavTravelQuote = getActivity().findViewById(R.id.edtFavTravelQuote);
        mEdtBio = getActivity().findViewById(R.id.edtBio);

        mTilLivesIn = getActivity().findViewById(R.id.mTilLivesIn);
        mTilFromPlace = getActivity().findViewById(R.id.mTilFromPlace);
        mTilGender = getActivity().findViewById(R.id.mTilGenderData);
        mTilRelationShipStatus = getActivity().findViewById(R.id.mTilRelationshipStatus);
        mTilDob = getActivity().findViewById(R.id.mTilDobData);
        mTilFavTravelQuote = getActivity().findViewById(R.id.mTilFavTravelQuote);
        mTilBio = getActivity().findViewById(R.id.mTilBio);

        mSprGender = getActivity().findViewById(R.id.sprGenderData);

        mTbUsersActivity = (TabLayout) getActivity().findViewById(R.id.tbUsersActivity);

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

        mIvProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        toGetUserInfo();
    }

    private void multiSelectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**These following line is the important one!
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES); //SELECT_PICTURES is simply a global int used to check the calling intent in onActivityResult
    }

    private void toShowDisplayUserInfo() {
        toUpdate = false;
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
        toUpdate = true;
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
//        mEdtGender.setVisibility(View.VISIBLE);

        mEdtRelationShipStatus.setVisibility(View.VISIBLE);
        mEdtDob.setVisibility(View.VISIBLE);
        mEdtFavTravelQuote.setVisibility(View.VISIBLE);
        mEdtBio.setVisibility(View.VISIBLE);
        mSprGender.setVisibility(View.VISIBLE);

    }

    private void toHideEditUserInfo() {
        toUpdate = false;
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
//        mEdtGender.setVisibility(View.GONE);

        mEdtRelationShipStatus.setVisibility(View.GONE);
        mEdtDob.setVisibility(View.GONE);
        mEdtFavTravelQuote.setVisibility(View.GONE);
        mEdtBio.setVisibility(View.GONE);
        mSprGender.setVisibility(View.GONE);
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("HMApp", "onActivityResult" + requestCode + ":" + resultCode + ":" + data);
////        if (resultCode == RESULT_CANCELED) {
////            return;
////        }
//        if (requestCode == GALLERY) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
//                    String path = saveImage(bitmap);
//                    Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
//                    mIvProfilePic.setImageBitmap(bitmap);
//
//                } catch (Exception | Error e) {
//                    e.printStackTrace();
//                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } else if (requestCode == CAMERA) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            mIvShare.setImageBitmap(thumbnail);
//            saveImage(thumbnail);
//            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("HmApp", " Result : " + requestCode);

        if(requestCode == SELECT_PICTURES) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;
                    while(currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        currentItem = currentItem + 1;
                    }
                    Log.d("HmApp", " " + count);
                } else if(data.getData() != null) {
                    String imagePath = data.getData().getPath();
                    Log.d("HmApp", " " + imagePath);
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
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mIvProfilePic.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                saveImage(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mIvProfilePic.setImageBitmap(bm);
    }

    public String saveImage(Bitmap myBitmap) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File wallpaperDirectory = new File(
                    Environment.getExternalStorageDirectory()
//                    Environment.DIRECTORY_DOWNLOADS
                            + "/Profile");
            // have the object build the directory structure, if needed.
            if (!wallpaperDirectory.exists()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Files.createFile(wallpaperDirectory.toPath());
                }
//                wallpaperDirectory.mkdirs();
            }
//            if (!wallpaperDirectory.getParentFile().exists())
//                wallpaperDirectory.getParentFile().mkdirs();
//            if (!wallpaperDirectory.exists())
//                wallpaperDirectory.createNewFile();

            Log.d("HmApp", " Path : " + wallpaperDirectory + " : " + wallpaperDirectory.exists());


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
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
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
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void saveProfileAccount() {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, "", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    Log.d("HMAPP", " resultResponse " + resultResponse);
//                    JSONObject result = new JSONObject(resultResponse);
//                    String status = result.getString("status");
//                    String message = result.getString("message");

                } catch (Exception e) {
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

                        Log.d("HmApp", "Error Status" + status);
                        Log.d("HmApp", "Error Message" + message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("HmPhoto", "Error"+ errorMessage);
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

    private void toUpdateUserInfoApi() {
        try {
            VolleySingleton.getInstance(getContext())
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST,

                                    AppConstants.URL + getContext().getResources().getString(R.string.str_register_login) + "." + getContext().getResources().getString(R.string.str_php),
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
                                                                Toast.makeText(getContext(), getString(R.string.str_successfully_updated), Toast.LENGTH_SHORT).show();
                                                                toHideEditUserInfo();
                                                                toGetUserInfo();
                                                            } else {
                                                                Toast.makeText(getContext(), getString(R.string.failed_to_update), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), getString(R.string.str_error_unable_to_update), Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), getString(R.string.str_error_unable_to_update), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                Toast.makeText(getContext(), getString(R.string.str_error_unable_to_update), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            Toast.makeText(getContext(), getString(R.string.str_error_unable_to_update), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(getContext().getResources().getString(R.string.str_action_), getString(R.string.str_user_info_update));
                                    params.put(getString(R.string.str_id), User.getUser(getContext()).getId());
                                    params.put(getString(R.string.str_lives_in), mEdtLivesIn.getText().toString().trim());
                                    params.put(getString(R.string.str_from_place), mEdtFromPlace.getText().toString().trim());
                                    params.put(getString(R.string.str_gender), mSprGender.getSelectedItem().toString());
                                    params.put(getString(R.string.str_rel_status), mEdtRelationShipStatus.getText().toString().trim());
                                    params.put(getString(R.string.str_dob), mEdtDob.getText().toString().trim());
                                    params.put(getString(R.string.str_bio), mEdtBio.getText().toString().trim());
                                    Log.d("HM_URL", " update_params " + params);
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(getString(R.string.str_header), getString(R.string.str_header_type));
                                    return super.getHeaders();
                                }
                            }
                            , getString(R.string.str_user_info_update));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Toast.makeText(getContext(), getString(R.string.str_error_unable_to_update), Toast.LENGTH_SHORT).show();
        }
    }

    private void toGetUserInfo() {
        try {
            VolleySingleton.getInstance(getContext())
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST,
                                    AppConstants.URL + getContext().getResources().getString(R.string.str_register_login) + "." + getContext().getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (!response.isNull(getString(R.string.str_status))) {
                                                        if (response.getInt(getString(R.string.str_status)) == 1) {
                                                            if (!response.isNull("username")) {
                                                                mTvUserName.setText(response.getString("username").toUpperCase());
                                                                User.getUser(getContext()).setUsername(response.getString("username"));
                                                            }
                                                            if (!response.isNull("email")) {
                                                                User.getUser(getContext()).setEmail(response.getString("email"));
                                                            }
                                                            if (!response.isNull("contact_no")) {
                                                                User.getUser(getContext()).setMobile(response.getString("contact_no"));
                                                            }
                                                            if (!response.isNull("dob")) {
                                                                User.getUser(getContext()).setDob(response.getString("dob"));
                                                                mTvDob.setText(getContext().getResources().getString(R.string.str_dob_data) + " : " + response.getString("dob"));
                                                            }
                                                            if (!response.isNull("referral_code")) {
                                                                mTvUsersReferralCode.setText(getContext().getResources().getString(R.string.str_referral_code) + " : " + response.getString("referral_code"));
                                                                User.getUser(getContext()).setReferralCode(response.getString("referral_code"));
                                                            }
                                                            if (!response.isNull("lives_in")) {
                                                                mTvLivesIn.setText(getContext().getResources().getString(R.string.str_lives_in_data) + " : " + response.getString("lives_in"));
                                                            }
                                                            if (!response.isNull("gender")) {
                                                                mTvGender.setText(getContext().getResources().getString(R.string.str_gender_data) + " : " + response.getString("gender"));
                                                            }
                                                            if (!response.isNull("from_des")) {
                                                                mTvFromPlace.setText(getContext().getResources().getString(R.string.str_from_place_data) + " : " + response.getString("from_des"));
                                                            }
                                                            if (!response.isNull("relationship_status")) {
                                                                mTvRelationShipStatus.setText(getContext().getResources().getString(R.string.str_relationship_status_data) + " : " + response.getString("relationship_status"));
                                                            }
                                                            if (!response.isNull("fav_quote")) {
                                                                mTvFavTravelQuote.setText(getContext().getResources().getString(R.string.str_favourite_travel_quote_data) + " : " + response.getString("fav_quote"));
                                                            }
                                                            if (!response.isNull("bio")) {
                                                                mTvBio.setText(getContext().getResources().getString(R.string.str_bio_data) + " : " + response.getString("bio"));
                                                            }
                                                            AppDataStorage.setUserInfo(getContext());
                                                            AppDataStorage.getUserInfo(getContext());
                                                            Log.d("HmApp", " User get () " + User.getUser(getContext()).getDob());
                                                            toShowDisplayUserInfo();
                                                        }
                                                    }
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
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
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(getString(R.string.str_action_), getString(R.string.str_user_info_display));
                                    params.put(getString(R.string.str_uid), "20");
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(getString(R.string.str_header), getString(R.string.str_header_type));
                                    // params.put("Content-Type","application/form-data");
                                    return super.getHeaders();
                                }
                            }
                            , getString(R.string.str_user_info_display));
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}