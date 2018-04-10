package com.hm.application.classes;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.common.UserData;
import com.hm.application.model.User;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.hm.application.utils.KeyBoard;

public class Common_Alert_box {

    static AlertDialog alert11 = null;

    public static void toFillTravellersInfo(final Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (inflater != null) {
                dialogView = inflater.inflate(R.layout.fragment_number_of_traveller, null);

                builder.setView(dialogView);

                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void toFillTravellersRoomInfo(final Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (inflater != null) {
                dialogView = inflater.inflate(R.layout.fragment_number_of_room, null);

                builder.setView(dialogView);

                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void toFillUserDetailsInfo(final Context context, final Activity activity) {
        try {
            LinearLayout mLlEditUserInfo, mllEditSC;
            final TextInputLayout mTilLivesIn, mTilFromPlace, mTilGender, mTilRelationShipStatus, mTilDob, mTilFavTravelQuote, mTilBio;
            final TextInputEditText mEdtLivesIn, mEdtFromPlace, mEdtRelationShipStatus, mEdtDob, mEdtFavTravelQuote, mEdtBio;
            Button mBtnEditSubmit, mBtnCancel;
            final Spinner mSprGender;
            TextView mTvLblIntroduceDone;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Introduce Yourself");

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (inflater != null) {
                dialogView = inflater.inflate(R.layout.user_info_edit_detail, null);

                mllEditSC = dialogView.findViewById(R.id.llEditSubmitCancel);
                mLlEditUserInfo = dialogView.findViewById(R.id.llInfoEdit);
                mEdtLivesIn = dialogView.findViewById(R.id.edtLivesIn);
                mEdtLivesIn.setTypeface(HmFonts.getRobotoRegular(context));

                mEdtFromPlace = dialogView.findViewById(R.id.edtFromPlace);
                mEdtFromPlace.setTypeface(HmFonts.getRobotoRegular(context));

                mEdtRelationShipStatus = dialogView.findViewById(R.id.edtRelationshipStatus);
                mEdtRelationShipStatus.setTypeface(HmFonts.getRobotoRegular(context));

                mEdtDob = dialogView.findViewById(R.id.edtDobData);
                mEdtDob.setTypeface(HmFonts.getRobotoRegular(context));
                mEdtDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        try {
                            if (v.getId() == mEdtDob.getId()) {
                                Log.d("HmApp", " Focus " + v.getId());
                                KeyBoard.hideKeyboard(activity);
                                CommonFunctions.toOpenDatePicker(context, mEdtDob);
                            }
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });

                mEdtFavTravelQuote = dialogView.findViewById(R.id.edtFavTravelQuote);
                mEdtFavTravelQuote.setTypeface(HmFonts.getRobotoRegular(context));

                mEdtBio = dialogView.findViewById(R.id.edtBio);
                mEdtBio.setTypeface(HmFonts.getRobotoRegular(context));

                mTilLivesIn = dialogView.findViewById(R.id.mTilLivesIn);
                mTilLivesIn.setTypeface(HmFonts.getRobotoRegular(context));

                mTilFromPlace = dialogView.findViewById(R.id.mTilFromPlace);
                mTilFromPlace.setTypeface(HmFonts.getRobotoRegular(context));

                mTilGender = dialogView.findViewById(R.id.mTilGenderData);
                mTilGender.setTypeface(HmFonts.getRobotoRegular(context));

                mTilRelationShipStatus = dialogView.findViewById(R.id.mTilRelationshipStatus);
                mTilRelationShipStatus.setTypeface(HmFonts.getRobotoRegular(context));

                mTilDob = dialogView.findViewById(R.id.mTilDobData);
                mTilDob.setTypeface(HmFonts.getRobotoRegular(context));

                mTilFavTravelQuote = dialogView.findViewById(R.id.mTilFavTravelQuote);
                mTilFavTravelQuote.setTypeface(HmFonts.getRobotoRegular(context));

                mTilBio = dialogView.findViewById(R.id.mTilBio);
                mTilBio.setTypeface(HmFonts.getRobotoRegular(context));

                mSprGender = dialogView.findViewById(R.id.sprGenderData);
                mBtnEditSubmit = dialogView.findViewById(R.id.btnSubmitEdit);
                mBtnEditSubmit.setTypeface(HmFonts.getRobotoRegular(context));

                mBtnCancel = dialogView.findViewById(R.id.btnCancelEdit);
                mBtnCancel.setTypeface(HmFonts.getRobotoRegular(context));

                mBtnEditSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyBoard.hideKeyboard(activity);
                        UserData.toUpdateUserInfoApi(alert11, context, mEdtLivesIn.getText().toString().trim(), mEdtFromPlace.getText().toString().trim(), mSprGender.getSelectedItem().toString().trim(),
                                mEdtRelationShipStatus.getText().toString().trim(), mEdtDob.getText().toString().trim(), mEdtFavTravelQuote.getText().toString().trim(), mEdtBio.getText().toString().trim());
                    }
                });

                mEdtDob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyBoard.hideKeyboard(activity);
//                    Log.d("HmAPp", " DatePicker : " + CommonFunctions.toOpenDatePicker(context));
                        CommonFunctions.toOpenDatePicker(context, mEdtDob);
                    }
                });

                mEdtDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            if (v.getId() == mEdtDob.getId()) {
                                CommonFunctions.toOpenDatePicker(context, mEdtDob);
                            } else {
                                CommonFunctions.toDisplayToast(" no view found " + v.getId(), context);
                            }
                        } else {
                            CommonFunctions.toDisplayToast(" no Focus found " + v.getId(), context);
                        }
                    }
                });

                mEdtLivesIn.setText(User.getUser(context).getLivesIn());
                mEdtFromPlace.setText(User.getUser(context).getFromDest());
                mEdtRelationShipStatus.setText(User.getUser(context).getRelationStatus());
                mEdtDob.setText(User.getUser(context).getDob());
                mEdtFavTravelQuote.setText(User.getUser(context).getFavQuote());
                mEdtBio.setText(User.getUser(context).getBio());
                if (User.getUser(context).getGender().toLowerCase().contains("f")) {
                    mSprGender.setSelection(1);
                } else if (User.getUser(context).getGender().toLowerCase().contains("o")) {
                    mSprGender.setSelection(2);
                } else {
                    mSprGender.setSelection(0);
                }

                builder.setView(dialogView);
                alert11 = builder.create();


                mBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert11.dismiss();
                    }
                });
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(alert11.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                alert11.show();
                alert11.getWindow().setAttributes(lp);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}
