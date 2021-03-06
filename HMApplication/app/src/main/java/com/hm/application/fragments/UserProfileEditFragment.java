package com.hm.application.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.common.UserData;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.hm.application.utils.KeyBoard;
import com.hm.application.utils.Utility;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileEditFragment extends Fragment {

    LinearLayout mLlEditUserInfo, mllEditSC;
    TextInputLayout mTilLivesIn, mTilFromPlace, mTilRelationShipStatus, mTilDob, mTilFavTravelQuote, mTilBio;
    TextInputEditText mEdtLivesIn, mEdtFromPlace, mEdtRelationShipStatus, mEdtDob, mEdtFavTravelQuote, mEdtBio;
    Spinner mSprGender;
    ImageView mIvUpeCancel, mIvUpeRight;
    CircleImageView mCivUpeProfile;
    TextView mTvLblIntroduceDone, mTvLblUpeEdit, mTvUpeChangePic;
    Uri picUri;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private OnFragmentInteractionListener mListener;

    public UserProfileEditFragment() {
        // Required empty public constructor
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mListener.toSetTitle(" ", false);
        dataBinding();
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    private void dataBinding() {
        try {
            Log.d("Hamapp", " getar : " + getArguments());

            mCivUpeProfile = getActivity().findViewById(R.id.civUpeProfile);

            if (User.getUser(getContext()).getPicPath() != null) {
                Picasso.with(getContext())
                        .load(AppConstants.URL + User.getUser(getContext()).getPicPath().replaceAll("\\s", "%20"))
                        .into(mCivUpeProfile);
            }

            mIvUpeCancel = getActivity().findViewById(R.id.imgUpeCancel);
            mIvUpeRight = getActivity().findViewById(R.id.imgUpeRight);

            mTvLblUpeEdit = getActivity().findViewById(R.id.txtLblUpeEdit);
            mTvLblUpeEdit.setTypeface(HmFonts.getRobotoBold(getContext()));

            mTvUpeChangePic = getActivity().findViewById(R.id.txtUpeChangePic);
            mTvUpeChangePic.setTypeface(HmFonts.getRobotoBold(getContext()));

//            mllEditSC = getActivity().findViewById(R.id.llEditSubmitCancel);
            mLlEditUserInfo = getActivity().findViewById(R.id.llInfoEdit);
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

            mTilRelationShipStatus = getActivity().findViewById(R.id.mTilRelationshipStatus);
            mTilRelationShipStatus.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilDob = getActivity().findViewById(R.id.mTilDobData);
            mTilDob.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilFavTravelQuote = getActivity().findViewById(R.id.mTilFavTravelQuote);
            mTilFavTravelQuote.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilBio = getActivity().findViewById(R.id.mTilBio);
            mTilBio.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mSprGender = getActivity().findViewById(R.id.sprGenderData);

            mIvUpeRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyBoard.hideKeyboard(getActivity());
                    UserData.toUpdateUserInfoApi(
                            getContext(), mEdtLivesIn.getText().toString().trim(), mEdtFromPlace.getText().toString().trim(), mSprGender.getSelectedItem().toString().trim(),
                            mEdtRelationShipStatus.getText().toString().trim(), mEdtDob.getText().toString().trim(), mEdtFavTravelQuote.getText().toString().trim(), mEdtBio.getText().toString().trim());
                    getActivity().getSupportFragmentManager().beginTransaction().remove(UserProfileEditFragment.this).commit();
                }
            });

            mIvUpeCancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(UserProfileEditFragment.this).commit();
                }
            });

            mEdtDob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyBoard.hideKeyboard(getActivity());
                    Log.d("HmAPp", " Focus 0 : ");
                    CommonFunctions.toOpenDatePicker(getContext(), mEdtDob);
                }
            });

            mEdtRelationShipStatus.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        KeyBoard.hideKeyboard(getActivity());
                        Log.d("HmAPp", " Focus 1 : ");
                        CommonFunctions.toOpenDatePicker(getContext(), mEdtDob);
                    }
                    return false;
                }
            });

            mCivUpeProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                }
            });
            mTvUpeChangePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                }
            });


            mEdtLivesIn.setText(User.getUser(getContext()).getLivesIn());
            mEdtFromPlace.setText(User.getUser(getContext()).getFromDest());
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

        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

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
            Crashlytics.logException(e);

        }
    }

    private void cameraIntent() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
            Uri path = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "tmp_avatar_"
                    + String.valueOf(System.currentTimeMillis())
                    + ".jpg"));

//            String imageFilePath = getContext().getApplicationContext().getPackageName() + ".my.package.name.provider", createImageFile();
            File imageFile = new File(imageFilePath);
            picUri = Uri.fromFile(imageFile); // convert path to Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

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
            Crashlytics.logException(e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE)
                    onSelectFromGalleryResult(data);
                else if (requestCode == REQUEST_CAMERA)
                    onCaptureImageResult(data);
                else if (requestCode == 3) {
                    // get the returned data
                    Bundle extras = data.getExtras();
                    Log.d("hmapp", " crop " + extras);
                    // get the cropped bitmap
                    Bitmap thePic = (Bitmap) extras.get("data");
                    Log.d("hmapp", " crop " + thePic + " ; " + thePic.getByteCount());
                    CommonFunctions.toSaveImages(thePic, "HM_PP", true, getContext(), getActivity());
                    mCivUpeProfile.setImageBitmap(thePic);
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    private void onCaptureImageResult(Intent data) {
        try {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mCivUpeProfile.setImageBitmap(thumbnail);
            CropImage(picUri);
//            CommonFunctions.toSaveImages(thumbnail, "HMC", true, getContext(), getActivity());
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                mCivUpeProfile.setImageBitmap(bm);
                CropImage(data.getData());
//                CommonFunctions.toSaveImages(bm, "HMG", true, getContext(), getActivity());
            } catch (Exception | Error e) {
                e.printStackTrace();
                Crashlytics.logException(e);

            }
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

        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

        void toSetTitle(String title, boolean b);
    }
}
