package com.hm.application.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;

public class ChangePasswordFragment extends Fragment {

    LinearLayout mLlChangePwdMain, mLlChangePwd, mLlChangePwdEdit;
    TextInputLayout mTilCurrentPwd, mTilNewPwd, mTilConfirmPwd;
    TextInputEditText mEdtCurrentPwd, mEdtNewPwd, mEdtConfirmPwd;
    ImageView mIvChangePwdCancel, mIvChangePwdRight;
    TextView mTvLblChangePwd, mTvForgetPassword;

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
            mIvChangePwdRight = getActivity().findViewById(R.id.imgChangePwdRight);

            mTvLblChangePwd = getActivity().findViewById(R.id.txtLblChangePwd);
            mTvLblChangePwd.setTypeface(HmFonts.getRobotoBold(getContext()));

            mTvForgetPassword = getActivity().findViewById(R.id.tvForgetPassword);
            mTvForgetPassword.setTypeface(HmFonts.getRobotoRegular(getContext()));

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
                            mTvForgetPassword.requestFocus();
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
            mTilCurrentPwd.setError(" Empty ");
            return false;
        } else if (mEdtCurrentPwd.getText().toString().trim().length() < 8) {
            mTilCurrentPwd.setError(" Min 8 letter ");
            return false;
        } else if (mEdtCurrentPwd.getText().toString().trim().length() > 25) {
            mTilCurrentPwd.setError(" Max 25 letter");
            return false;
        } else if (CommonFunctions.isValidPassword(mEdtCurrentPwd.getText().toString().trim())) {
            mTilCurrentPwd.setError(" Password Should contain atleast 1 uppercase letter, 1 lowercase letter, 1 special character, 1 number");
            return false;
        } else {
            mTilCurrentPwd.setError(null);
            return true;
        }
    }

    public boolean isValidNewPassword() {
        if (mEdtNewPwd.getText().toString().trim().length() == 0) {
            mTilNewPwd.setError(" Empty ");
            return false;
        } else if (mEdtNewPwd.getText().toString().trim().length() < 8) {
            mTilNewPwd.setError(" Min 8 letter ");
            return false;
        } else if (mEdtNewPwd.getText().toString().trim().length() > 25) {
            mTilNewPwd.setError(" Max 25 letter");
            return false;
        } else if (CommonFunctions.isValidPassword(mEdtNewPwd.getText().toString().trim())) {
            mTilNewPwd.setError(" Password Should contain atleast 1 uppercase letter, 1 lowercase letter, 1 special character, 1 number");
            return false;
        } else {
            mTilNewPwd.setError(null);
            return true;
        }
    }

    public boolean isValidCnfPassword() {
        if (mEdtConfirmPwd.getText().toString().trim().length() == 0) {
            mTilConfirmPwd.setError(" Empty ");
            return false;
        } else if (mEdtConfirmPwd.getText().toString().trim().length() < 8) {
            mTilConfirmPwd.setError(" Min 8 letter ");
            return false;
        } else if (mEdtConfirmPwd.getText().toString().trim().length() > 25) {
            mTilConfirmPwd.setError(" Max 25 letter");
            return false;
        } else if (CommonFunctions.isValidPassword(mEdtConfirmPwd.getText().toString().trim())) {
            mTilConfirmPwd.setError(" Password Should contain atleast 1 uppercase letter, 1 lowercase letter, 1 special character, 1 number");
            return false;
        } else {
            mTilConfirmPwd.setError(null);
            return true;
        }
    }

}
