package com.hm.application.user_data;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.hm.application.utils.KeyBoard;

import org.json.JSONObject;

public class RegistrationFragment extends Fragment {

    private TextInputLayout mTilFullName, mTilUsername, mTilEmail, mTilPassword, mTilMobile, mTilDob, mTilReferralCode;
    private EditText mEdtFullName, mEdtUsername, mEdtPassword, mEdtEmailId, mEdtMobileNo, mEdtDob, mEdtReferralCode;
    private RadioGroup mRgGender;
    private RadioButton mRbMale, mRbFemale, mRbOthers;
    private Button mBtnSubmit;
    private TextView mTxtAlreadyReg, mTxtGenderError, mTxtLblBatReg;
    private String gender;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mTilFullName = getActivity().findViewById(R.id.tilUserFullNameReg);
            mTilFullName.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mTilUsername = getActivity().findViewById(R.id.tilUsernameReg);
            mTilUsername.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mTilEmail = getActivity().findViewById(R.id.tilEmailReg);
            mTilEmail.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mTilPassword = getActivity().findViewById(R.id.tilPasswordReg);
            mTilPassword.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mTilMobile = getActivity().findViewById(R.id.tilMobile);
            mTilMobile.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mTilDob = getActivity().findViewById(R.id.tilDobReg);
            mTilDob.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mTilReferralCode = getActivity().findViewById(R.id.tilReferralCode);
            mTilReferralCode.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mEdtFullName = getActivity().findViewById(R.id.edtUserFullNameReg);
            mEdtFullName.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mEdtUsername = getActivity().findViewById(R.id.edtUserNameReg);
            mEdtUsername.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mEdtEmailId = getActivity().findViewById(R.id.edtEmailReg);
            mEdtEmailId.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mEdtPassword = getActivity().findViewById(R.id.edtPasswordReg);
            mEdtPassword.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mEdtMobileNo = getActivity().findViewById(R.id.edtMobileNo);
            mEdtMobileNo.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mEdtDob = getActivity().findViewById(R.id.edtDobReg);
            mEdtDob.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mEdtReferralCode = getActivity().findViewById(R.id.edtReferralCodeReg);
            mEdtReferralCode.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mRgGender = getActivity().findViewById(R.id.rgGender);
            mRbMale = getActivity().findViewById(R.id.rbMale);
            mRbMale.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mRbFemale = getActivity().findViewById(R.id.rbFemale);
            mRbFemale.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mRbOthers = getActivity().findViewById(R.id.rbOther);
            mRbOthers.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mBtnSubmit = getActivity().findViewById(R.id.btnOtp);
            mBtnSubmit.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTxtAlreadyReg = getActivity().findViewById(R.id.tvAlreadyReg);
            mTxtAlreadyReg.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTxtGenderError = getActivity().findViewById(R.id.tvRgError);
            mTxtGenderError.setTypeface(HmFonts.getRobotoRegular(getContext()));

            mTxtLblBatReg = getActivity().findViewById(R.id.txtLblBatReg);
            mTxtLblBatReg.setTypeface(HmFonts.getRobotoBold(getContext()));

            mEdtFullName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (validateFullName()) {
                            mEdtUsername.requestFocus();
                            return true;
                        } else {
                            mEdtFullName.requestFocus();
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

            mEdtUsername.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (validateUsername()) {
                            mEdtEmailId.requestFocus();
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
                            mEdtPassword.requestFocus();
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
                            mEdtMobileNo.requestFocus();
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
                            mEdtDob.requestFocus();
                            KeyBoard.hideKeyboard(getActivity());
                            Log.d("HmAPp", " Focus 1 : ");
                            CommonFunctions.toOpenDatePicker(getContext(), mEdtDob);
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
                            mEdtReferralCode.requestFocus();
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
                            mRbMale.requestFocus();
//                            generate_otp();
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

            mEdtFullName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validateFullName();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    validateUsername();
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

            mEdtDob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyBoard.hideKeyboard(getActivity());
                    Log.d("HmAPp", " Focus 0 : ");
                    CommonFunctions.toOpenDatePicker(getContext(), mEdtDob);
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
            toRegisterNewUser();
//            new toRegisterUser();
//        } else {
//            Toast.makeText(getContext(), " Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean toValidateAll() {
        validateFullName();
        validateUsername();
        validateEmail();
        validatePassword();
        validateMobile();
        validateDate();
        validateReferral();
        validateGender();
        return validateFullName() && validateUsername() && validateEmail() && validatePassword() && validateMobile() && validateDate()  && validateGender();//&& validateReferral()
    }

    private boolean validateGender() {
        if (mRgGender.getCheckedRadioButtonId() == -1) {
            mTxtGenderError.setText(R.string.str_error_select_gender);
            mTxtGenderError.setVisibility(View.VISIBLE);
            return false;
        } else {
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
        } else if (!Pattern.compile("^[a-zA-Z ]+$").matcher(mEdtUsername.getText().toString().trim()).matches()) {
            mTilUsername.setError(getString(R.string.str_error_invalid_name));
            return false;
        } else {
            mTilUsername.setError(null);
            return true;
        }
    }

    public boolean validateFullName() {
        if (mEdtFullName.getText().toString().trim().length() == 0) {
            mTilFullName.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtFullName.getText().toString().trim().length() < 3) {
            mTilFullName.setError(getString(R.string.str_error_minimun_3));
            return false;
        } else if (mEdtFullName.getText().toString().trim().length() > 20) {
            mTilFullName.setError(getString(R.string.str_error_max_20));
            return false;
        } else if (!Pattern.compile("^[a-zA-Z ]+$").matcher(mEdtFullName.getText().toString().trim()).matches()) {
            mTilFullName.setError(getString(R.string.str_error_invalid_name));
            return false;
        } else {
            mTilFullName.setError(null);
            return true;
        }
    }

    public boolean validateEmail() {
        if (mEdtEmailId.getText().toString().trim().length() == 0) {
            mTilEmail.setError(getContext().getResources().getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (CommonFunctions.isValidEmail(mEdtEmailId.getText().toString().trim())) {
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
        } else if (!Pattern.compile("^([1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d{2}$").matcher(mEdtDob.getText().toString().trim()).matches()) {
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

    private void toRegisterNewUser() {
        try {
            CommonFunctions.toCallLoader(getContext(), "Loading");
            VolleySingleton.getInstance(getContext())
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL + getContext().getResources().getString(R.string.str_register_login) + getContext().getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //{context.getString(R.string.str_status):1,"msg":"Saved Successfully"}
                                                //{context.getString(R.string.str_status):1,"msg":"Saved Successfully","uid":42}
                                                //{context.getString(R.string.str_status):0,"email":"Email already exist","contact":"Mobile No. already exist","username":"Username already exist"}
                                                //{context.getString(R.string.str_status):0,"username":"Username already exist"}
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        CommonFunctions.toCloseLoader();
                                                        if (!response.isNull(getContext().getString(R.string.str_status))) {
                                                            if (response.getInt(getContext().getString(R.string.str_status)) == 1) {
                                                                Log.d("HmApp", " res register  " + response);

                                                                User user = new User(getContext());
                                                                user.setUid(response.getString(getContext().getString(R.string.str_uid)));
                                                                user.setUsername(mEdtUsername.getText().toString().trim());
                                                                user.setEmail(mEdtEmailId.getText().toString().trim());
                                                                user.setDob(mEdtDob.getText().toString().trim());
                                                                user.setMobile(mEdtMobileNo.getText().toString().trim());
                                                                user.setName(mEdtFullName.getText().toString().trim());
                                                                user.setReferralCode(mEdtReferralCode.getText().toString().trim());
                                                                user.setGender(gender);
                                                                user.setUser(user);

                                                                AppDataStorage.setUserInfo(getContext());
                                                                AppDataStorage.getUserInfo(getContext());
                                                                Log.d("HmApp", " UserName : " + User.getUser(getContext()).getUsername());

//                                                                toChangeScreen(new RegisterOTPFragment());
                                                                getContext().startActivity(new Intent(getContext(), MainHomeActivity.class));
                                                                Toast.makeText(getContext(), "Successfully ", Toast.LENGTH_SHORT).show();
                                                                CommonFunctions.toCloseLoader();
                                                            } else {
                                                                if (!response.isNull("email")) {
                                                                    mTilEmail.setError(response.getString("email"));
                                                                    CommonFunctions.toCloseLoader();
                                                                }
                                                                if (!response.isNull("contact")) {
                                                                    mTilMobile.setError(response.getString("contact"));
                                                                    CommonFunctions.toCloseLoader();
                                                                }
                                                                if (!response.isNull("username")) {
                                                                    mTilUsername.setError(response.getString("username"));
                                                                    CommonFunctions.toCloseLoader();
                                                                }
                                                                Toast.makeText(getContext(), "Unable to Register", Toast.LENGTH_SHORT).show();
                                                                CommonFunctions.toCloseLoader();
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), "Unable to Register", Toast.LENGTH_SHORT).show();
                                                        CommonFunctions.toCloseLoader();
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), "Unable to Register", Toast.LENGTH_SHORT).show();
                                                    CommonFunctions.toCloseLoader();
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();

                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    CommonFunctions.toCloseLoader();
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(getString(R.string.str_fullname_), mEdtFullName.getText().toString().trim());
                                    params.put(getString(R.string.str_username_), mEdtUsername.getText().toString().trim());
                                    params.put(getString(R.string.str_email_), mEdtEmailId.getText().toString().trim());
                                    params.put(getString(R.string.str_password_), mEdtPassword.getText().toString().trim());
                                    params.put(getString(R.string.str_contact_no_), mEdtMobileNo.getText().toString().trim());
                                    params.put(getString(R.string.str_dob_), mEdtDob.getText().toString().trim());
                                    params.put(getString(R.string.str_referral_code_), mEdtReferralCode.getText().toString().trim());
                                    params.put(getString(R.string.str_gender_), gender);
                                    params.put(getString(R.string.str_action_), getString(R.string.str_register_small));
                                    params.put(getString(R.string.str_fcm_token), User.getUser(getContext()).getFcmToken());
                                    params.put(getString(R.string.str_device_id), CommonFunctions.getDeviceUniqueID(getActivity()));
                                    return params;
                                }
                            }
                            , getString(R.string.str_register_small)
                    );
        } catch (Exception | Error e) {
            e.printStackTrace();

            CommonFunctions.toCloseLoader();
        }
    }

    private void toChangeScreen(Fragment fragment) {
        try {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentrepalce, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out, R.animator.flip_left_in, R.animator.flip_left_out)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commitAllowingStateLoss();
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }
}