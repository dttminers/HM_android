package com.hm.application.user_data;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.network.PostObjRequest;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;

import org.json.JSONObject;

public class RegistrationFragment extends Fragment {

    private TextInputLayout mTilUsername, mTilEmail, mTilPassword, mTilMobile, mTilDob, mTilReferralCode;
    private EditText mEdtUsername, mEdtPassword, mEdtEmailId, mEdtMobileNo, mEdtDob, mEdtReferralCode;
    private RadioGroup mRgGender;
    private RadioButton mRbMale, mRbFemale, mRbOthers;
    private Button mBtnSubmit;
    private TextView mTxtAlreadyReg, mTxtGenderError,mTxtLblBatReg;
    private String gender;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mTilUsername = getActivity().findViewById(R.id.tilUsernameReg);
            mTilUsername.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mTilEmail = getActivity().findViewById(R.id.tilEmailReg);
            mTilEmail.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mTilPassword = getActivity().findViewById(R.id.tilPasswordReg);
            mTilPassword.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mTilMobile = getActivity().findViewById(R.id.tilMobile);
            mTilMobile.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mTilDob = getActivity().findViewById(R.id.tilDobReg);
            mTilDob.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mTilReferralCode = getActivity().findViewById(R.id.tilReferralCode);
            mTilReferralCode.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mEdtUsername = getActivity().findViewById(R.id.edtUserNameReg);
            mEdtUsername.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mEdtEmailId = getActivity().findViewById(R.id.edtEmailReg);
            mEdtEmailId.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mEdtPassword = getActivity().findViewById(R.id.edtPasswordReg);
            mEdtPassword.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mEdtMobileNo = getActivity().findViewById(R.id.edtMobileNo);
            mEdtMobileNo.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mEdtDob = getActivity().findViewById(R.id.edtDobReg);
            mEdtDob.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mEdtReferralCode = getActivity().findViewById(R.id.edtReferralCodeReg);
            mEdtReferralCode.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mRgGender = getActivity().findViewById(R.id.rgGender);
            mRbMale = getActivity().findViewById(R.id.rbMale);
            mRbMale.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mRbFemale = getActivity().findViewById(R.id.rbFemale);
            mRbFemale.setTypeface(HmFonts.getRobotoMedium(getContext()));
            mRbOthers = getActivity().findViewById(R.id.rbOther);
            mRbOthers.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mBtnSubmit = getActivity().findViewById(R.id.btnOtp);
            mBtnSubmit.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mTxtAlreadyReg = getActivity().findViewById(R.id.tvAlreadyReg);
            mTxtAlreadyReg.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mTxtGenderError = getActivity().findViewById(R.id.tvRgError);
            mTxtGenderError.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mTxtLblBatReg = getActivity().findViewById(R.id.txtLblBatReg);
            mTxtLblBatReg.setTypeface(HmFonts.getRobotoBold(getContext()));

            mEdtUsername.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (validateUsername()) {
                            return true;
                        } else {
                            mEdtUsername.requestFocus();
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

            mEdtEmailId.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (validateEmail()) {
                            return true;
                        } else {
                            mEdtEmailId.requestFocus();
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

            mEdtPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (validatePassword()) {
                            return true;
                        } else {
                            mEdtPassword.requestFocus();
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

            mEdtMobileNo.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (validateMobile()) {
                            return true;
                        } else {
                            mEdtMobileNo.requestFocus();
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

            mEdtDob.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (validateDate()) {
                            return true;
                        } else {
                            mEdtDob.requestFocus();
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

            mEdtReferralCode.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (validateReferral()) {
                            return true;
                        } else {
                            mEdtReferralCode.requestFocus();
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

            mEdtUsername.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validateUsername();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    validateUsername();
                }
            });

            mEdtEmailId.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validateEmail();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    validateEmail();
                }
            });

            mEdtPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validatePassword();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    validatePassword();
                }
            });

            mEdtMobileNo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validateMobile();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    validateMobile();
                }
            });

            mEdtDob.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validateDate();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    validateDate();
                }
            });

            mEdtReferralCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validateReferral();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    validateReferral();
                }
            });

            mBtnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generate_otp();
                }
            });

            mTxtAlreadyReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toChangeScreen(new LoginFragment());
                }
            });

        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public void generate_otp() {
        if (toValidateAll()) {
            new toRegisterUser();
        } else {
            Toast.makeText(getContext(), " Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean toValidateAll() {
        validateUsername();
        validateEmail();
        validatePassword();
        validateMobile();
        validateDate();
        validateReferral();
        validateGender();
        return validateUsername() && validateEmail() && validatePassword() && validateMobile() && validateDate() && validateReferral() && validateGender();
    }

    private boolean validateGender() {
        if (mRgGender.getCheckedRadioButtonId() == -1) {
            mTxtGenderError.setText(R.string.str_error_select_gender);
            mTxtGenderError.setVisibility(View.VISIBLE);
            return false;
        } else {
            Log.d("Hm", " Gender : " + mRgGender.getCheckedRadioButtonId()
                    + " : " + (mRgGender.getCheckedRadioButtonId() == mRbMale.getId())
                    + " : " + (mRgGender.getCheckedRadioButtonId() == mRbFemale.getId())
                    + " : " + (mRgGender.getCheckedRadioButtonId() == mRbOthers.getId())
            );
            if (mRgGender.getCheckedRadioButtonId() == mRbMale.getId()) {
                gender = "Male";
            } else if (mRgGender.getCheckedRadioButtonId() == mRbFemale.getId()) {
                gender = "Female";
            } else {
                gender = "Others";
            }
            return true;
        }
    }

    public boolean validateUsername() {
        if (mEdtUsername.getText().toString().trim().length() == 0) {
            mTilUsername.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtUsername.getText().toString().trim().length() < 3) {
            mTilUsername.setError(getString(R.string.str_error_minimun_3));
            return false;
        } else if (mEdtUsername.getText().toString().trim().length() > 20) {
            mTilUsername.setError(getString(R.string.str_error_max_20));
            return false;
        } else {
            mTilUsername.setError(null);
            return true;
        }
    }

    public boolean validateEmail() {
        if (mEdtEmailId.getText().toString().trim().length() == 0) {
            mTilEmail.setError(getContext().getResources().getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$").matcher(mEdtEmailId.getText().toString().trim()).matches()) {
            mTilEmail.setError(null);
            return true;
        } else {
            mTilEmail.setError(getString(R.string.str_error_valid_email));
            return false;
        }
    }

    public boolean validatePassword() {
        if (mEdtPassword.getText().toString().trim().length() == 0) {
            mTilPassword.setError(getContext().getResources().getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtPassword.getText().toString().trim().length() < 8) {
            mTilPassword.setError(getString(R.string.str_error_minimum_8));
            return false;
        } else if (mEdtPassword.getText().toString().trim().length() > 25) {
            mTilPassword.setError(getString(R.string.str_error_maximum_25));
            return false;
        } else if (CommonFunctions.isValidPassword(mEdtPassword.getText().toString().trim())) {
            mTilPassword.setError(getString(R.string.str_error_pswd));
            return false;
        } else {
            mTilPassword.setError(null);
            return true;
        }
    }

    public boolean validateMobile() {
        if (mEdtMobileNo.getText().toString().trim().length() == 0) {
            mTilMobile.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtMobileNo.getText().toString().trim().length() != 10) {
            mTilMobile.setError(getString(R.string.str_error_valid_mobile_no));
            return false;
        } else if (!Pattern.compile("^[0-9]*$").matcher(mEdtMobileNo.getText().toString().trim()).matches()) {
            mTilMobile.setError(getString(R.string.str_error_valid_mobile_no));
            return false;
        } else {
            mTilMobile.setError(null);
            return true;
        }
    }

    public boolean validateDate() {
        if (mEdtDob.getText().toString().length() == 0) {
            mTilDob.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (!Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d{2}$").matcher(mEdtDob.getText().toString().trim()).matches()) {
            mTilDob.setError(getString(R.string.str_err));
            return false;
        } else {
            mTilDob.setError(null);
            return true;
        }
    }

    public boolean validateReferral() {
        if (mEdtReferralCode.getText().toString().trim().length() == 0) {
            mTilReferralCode.setError(getContext().getResources().getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtReferralCode.getText().toString().trim().length() <= 4 && mEdtReferralCode.getText().toString().trim().length() >= 8) {
            mTilReferralCode.setError(getString(R.string.str_error_valid_referral_code));
            return false;
        } else {
            mTilReferralCode.setError(null);
            return true;
        }
    }

    private class toRegisterUser extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new PostObjRequest(
                                        AppConstants.URL,
                                        new JSONObject()
                                                .put(getString(R.string.str_username_), mEdtUsername.getText().toString().trim())
                                                .put(getString(R.string.str_email_), mEdtEmailId.getText().toString().trim())
                                                .put(getString(R.string.str_password_), mEdtPassword.getText().toString().trim())
                                                .put(getString(R.string.str_contact_no_), mEdtMobileNo.getText().toString().trim())
                                                .put(getString(R.string.str_dob_), mEdtDob.getText().toString().trim())
                                                .put(getString(R.string.str_referral_code_), mEdtReferralCode.getText().toString().trim())
                                                .put(getString(R.string.str_gender_), gender)
                                                .put(getString(R.string.str_action_), getString(R.string.str_register_small)),
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    if (response != null) {
                                                        if (!response.isNull("status")) {
                                                            if (response.getInt("status") == 1) {
                                                                toChangeScreen(new RegisterOTPFragment());
                                                                Toast.makeText(getContext(), " Successfully ", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getContext(), "Unable to Register", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), "Unable to Register", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                }
                                )
                                , " Register");

            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void toChangeScreen(Fragment fragment) {
        try {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentrepalce, fragment)
                    .setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out, R.animator.flip_left_in, R.animator.flip_left_out)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commitAllowingStateLoss();
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }


}