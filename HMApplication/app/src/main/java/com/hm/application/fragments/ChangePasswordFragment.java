package com.hm.application.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.activity.AccountSettingsActivity;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.hm.application.utils.KeyBoard;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordFragment extends Fragment {

    LinearLayout mLlChangePwdMain, mLlChangePwd, mLlChangePwdEdit;
    TextInputLayout mTilCurrentPwd, mTilNewPwd, mTilConfirmPwd;
    TextInputEditText mEdtCurrentPwd, mEdtNewPwd, mEdtConfirmPwd;
    ImageView mIvChangePwdCancel;
    TextView mTvLblChangePwd;
    Button mBtnSubmit;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataBinding();
    }

    private void dataBinding() {
        try {
            mIvChangePwdCancel = getActivity().findViewById(R.id.imgChangePwdCancel);

            mBtnSubmit = getActivity().findViewById(R.id.btnSubmit);

            mTvLblChangePwd = getActivity().findViewById(R.id.txtLblChangePwd);
            mTvLblChangePwd.setTypeface(HmFonts.getRobotoBold(getContext()));

            mLlChangePwdMain = getActivity().findViewById(R.id.llChangePwdMain);
            mLlChangePwd = getActivity().findViewById(R.id.llChangePwd);
            mLlChangePwdEdit = getActivity().findViewById(R.id.llChangePwdEdit);

            mEdtCurrentPwd = getActivity().findViewById(R.id.edtCurrentPwd);
            mEdtCurrentPwd.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mEdtNewPwd = getActivity().findViewById(R.id.edtNewPwd);
            mEdtNewPwd.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mEdtConfirmPwd = getActivity().findViewById(R.id.edtConfirmPwd);
            mEdtConfirmPwd.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilCurrentPwd = getActivity().findViewById(R.id.mTilCurrentPwd);
            mTilCurrentPwd.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilNewPwd = getActivity().findViewById(R.id.mTilNewPwd);
            mTilNewPwd.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTilConfirmPwd = getActivity().findViewById(R.id.mTilConfirmPwd);
            mTilConfirmPwd.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mIvChangePwdCancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(ChangePasswordFragment.this).commit();
                }
            });

            mBtnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyBoard.hideKeyboard(getActivity());
                    toSetNewPassword();
                }
            });

            mEdtCurrentPwd.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (isValidCurrentPassword()) {
                            mEdtNewPwd.requestFocus();
                            return true;
                        } else {
                            mEdtCurrentPwd.requestFocus();
                            return false;
                        }
                    } else {
                        return false;
                    }
                }

            });

            mEdtNewPwd.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (isValidNewPassword()) {
                            mEdtConfirmPwd.requestFocus();
                            return true;
                        } else {
                            mEdtNewPwd.requestFocus();
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

            mEdtConfirmPwd.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (isValidCnfPassword()) {
                            mBtnSubmit.requestFocus();
                            return true;
                        } else {
                            mEdtConfirmPwd.requestFocus();
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

            mEdtCurrentPwd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    isValidCurrentPassword();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    isValidCurrentPassword();
                }
            });

            mEdtNewPwd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    isValidNewPassword();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    isValidNewPassword();
                }
            });

            mEdtConfirmPwd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    isValidCnfPassword();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    isValidCnfPassword();
                }
            });

        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

        }
    }

    public boolean isValidCurrentPassword() {
        if (mEdtCurrentPwd.getText().toString().trim().length() == 0) {
            mTilCurrentPwd.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtCurrentPwd.getText().toString().trim().length() < 8) {
            mTilCurrentPwd.setError(getString(R.string.str_error_minimum_8));
            return false;
        } else if (mEdtCurrentPwd.getText().toString().trim().length() > 15) {
            mTilCurrentPwd.setError(getString(R.string.str_error_maximum_15));
            return false;
        } else if (CommonFunctions.isValidPassword(mEdtCurrentPwd.getText().toString().trim())) {
            mTilCurrentPwd.setError(getString(R.string.str_error_pswd));
            return false;
        } else {
            mTilCurrentPwd.setError(null);
            return true;
        }
    }

    public boolean isValidNewPassword() {
        if (mEdtNewPwd.getText().toString().trim().length() == 0) {
            mTilNewPwd.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtNewPwd.getText().toString().trim().length() < 8) {
            mTilNewPwd.setError(getString(R.string.str_error_minimum_8));
            return false;
        } else if (mEdtNewPwd.getText().toString().trim().length() > 15) {
            mTilNewPwd.setError(getString(R.string.str_error_maximum_15));
            return false;
        } else if (CommonFunctions.isValidPassword(mEdtNewPwd.getText().toString().trim())) {
            mTilNewPwd.setError(getString(R.string.str_error_pswd));
            return false;
        } else {
            mTilNewPwd.setError(null);
            return true;
        }
    }

    public boolean isValidCnfPassword() {
        if (mEdtConfirmPwd.getText().toString().trim().length() == 0) {
            mTilConfirmPwd.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtConfirmPwd.getText().toString().trim().length() < 8) {
            mTilConfirmPwd.setError(getString(R.string.str_error_minimum_8));
            return false;
        } else if (mEdtConfirmPwd.getText().toString().trim().length() > 15) {
            mTilConfirmPwd.setError(getString(R.string.str_error_maximum_15));
            return false;
        } else if (CommonFunctions.isValidPassword(mEdtConfirmPwd.getText().toString().trim())) {
            mTilConfirmPwd.setError(getString(R.string.str_error_pswd));
            return false;
        } else {
            mTilConfirmPwd.setError(null);
            return true;
        }
    }

    public void toSetNewPassword(){
        String oldPwd = mEdtCurrentPwd.getText().toString().trim();
        String newPwd = mEdtNewPwd.getText().toString().trim();
        String cnfPwd = mEdtConfirmPwd.getText().toString().trim();

        if (!oldPwd.equals(newPwd)){
            if (newPwd.equals(cnfPwd)){
                toChangePassword(getContext());
            }else {
                mTilConfirmPwd.setError("Invalid password");
                mTilNewPwd.setError("Invalid Password");
                CommonFunctions.toDisplayToast("Password Don't Match !",getContext());
            }
        }else {
            mTilCurrentPwd.setError("Invalid Password");
            mTilNewPwd.setError("Invalid Password");
            CommonFunctions.toDisplayToast("Current Password is Incorrect!",getContext());
        }
    }

    public  void toChangePassword(final Context context) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL + context.getResources().getString(R.string.str_register_login) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                CommonFunctions.toCloseLoader();
                                                Log.d("HmApp", " Change Password: " + res);
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        Log.d("HmApp", "Change Password123:" + response);
                                                        if (!response.isNull("status")) {
                                                            if (response.getInt("status") == 0) {
                                                                CommonFunctions.toDisplayToast("Unable to Change Password", context);
                                                            } else if (response.getInt("status") == 1) {
                                                                CommonFunctions.toDisplayToast("Password Change Successfully", context);
                                                                getActivity().getSupportFragmentManager().beginTransaction().remove(ChangePasswordFragment.this).commit();
                                                            }
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("Unable to Change Password", context);
                                                    }
                                                } else {
                                                    CommonFunctions.toDisplayToast("Unable to Change Password", context);
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                Crashlytics.logException(e);
                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toCloseLoader();
                                            CommonFunctions.toDisplayToast("Unable to  Change Password", context);
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_change_password_));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getResources().getString(R.string.str_old_password),mEdtCurrentPwd.getText().toString().trim());
                                    params.put(context.getResources().getString(R.string.str_new_password_), mEdtNewPwd.getText().toString().trim());
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_change_password_));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
            CommonFunctions.toCloseLoader();
        }
    }
}
