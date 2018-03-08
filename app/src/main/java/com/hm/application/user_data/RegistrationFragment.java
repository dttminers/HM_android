package com.hm.application.user_data;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.hm.application.R;

public class RegistrationFragment extends Fragment {

    TextInputLayout txtInputUsername, txtInputEmail, txtInputPassword, txtInputMobile, txtInputBirth_date, txtInputReferral;
    EditText username, password, email, mobile, birth_date, referral;
    RadioGroup radioGrp;
    RadioButton radio_male, radio_female, radio_other;
    Button btn_otp;
    TextView txt1, txt2;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtInputUsername = getActivity().findViewById(R.id.txtInputUsername);
        txtInputEmail = getActivity(). findViewById(R.id.txtInputMail);
        txtInputPassword = getActivity(). findViewById(R.id.txtInputPassword);
        txtInputMobile = getActivity().findViewById(R.id.txtInputMobile);
        txtInputBirth_date = getActivity(). findViewById(R.id.txtInputBirth_date);
        txtInputReferral = getActivity(). findViewById(R.id.txtInputReferral);

        username = getActivity(). findViewById(R.id.username);
        email = getActivity(). findViewById(R.id.email);
        password = getActivity(). findViewById(R.id.password);
        mobile = getActivity(). findViewById(R.id.mobile);
        birth_date = getActivity(). findViewById(R.id.birth_date);
        referral = getActivity(). findViewById(R.id.referral);

        radioGrp = getActivity(). findViewById(R.id.radioGrp);
        radio_male = getActivity(). findViewById(R.id.radio_male);
        radio_female = getActivity(). findViewById(R.id.radio_female);
        radio_other = getActivity(). findViewById(R.id.radio_other);

        btn_otp = getActivity(). findViewById(R.id.btn_otp);

        txt1 = getActivity(). findViewById(R.id.txt1);
        txt2 = getActivity(). findViewById(R.id.txt2);

        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generate_otp();

            }
        });
    }

    public void generate_otp(){
        if (validateUsername() || validateEmail() || validatePassword() || validateDate() || validateMobile() || validateReferral()){
            Toast.makeText(getActivity(),"failed",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(),"success",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validateUsername() {
        boolean isCheck = false;

        if (username.getText().toString().trim().length() == 0) {
            isCheck = false;
            username.setError("Empty");
        } else if (username.getText().toString().trim().length() < 3) {
            isCheck = false;
            username.setError("Minimum 3 letter Allow");
        } else if (username.getText().toString().trim().length() > 20) {
            isCheck = false;
            username.setError("Maximum 20 letter Allow");
        } else
            isCheck = true;
        return isCheck;

    }

    public boolean validateEmail() {
        boolean isCheck =false;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Matcher matcherObj = Pattern.compile(ePattern).matcher(email.getText().toString().trim());
        if (email.getText().toString().trim().length() == 0) {
            isCheck = false;
            email.setError("Empty");
        } else  if (matcherObj.matches()) {
            isCheck = true;
        }else {
            isCheck = false;
            email.setError("please enter valid Email");
        }
        return isCheck;
    }

    public boolean validatePassword() {
        boolean isCheck = false;
        if (password.getText().toString().trim().length() == 0) {
            isCheck = false;
            password.setError("Empty");
        } else if (password.getText().toString().trim().length() >= 8 && password.getText().toString().trim().length() <= 15) {
            isCheck = true;
        } else {
            isCheck = false;
            password.setError("please enter valid password..");
        }
        return isCheck;
    }


    public boolean validateMobile() {
        boolean isCheck = false;
        if (mobile.getText().toString().trim().length() == 0) {
            isCheck = false;
            mobile.setError("please enter valid mobile number");
        } else if (mobile.getText().toString().trim().length() == 10) {
            isCheck = true;
        } else {
            isCheck = false;
            mobile.setError("please enter valid mobile Number");
        }
        return isCheck;
    }

    public boolean validateDate() {

        boolean isCheck = false;

        String regEx = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d{2}$";
        Matcher matcherObj = Pattern.compile(regEx).matcher(birth_date.getText().toString().trim());
        if (matcherObj.matches()) {
            isCheck = true;
        } else {
            isCheck = false;
            birth_date.setError("please enter proper date formate");
        }
        return isCheck;
    }

    public boolean validateReferral() {
        boolean isCheck = false;
        if (referral.getText().toString().trim().length() == 0) {
            isCheck = false;
            referral.setError("Empty");
        } else if (referral.getText().toString().trim().length() <= 4 && referral.getText().toString().trim().length() >= 8) {
            isCheck = false;
            password.setError("Please enter valid referral code..");
        } else {
            isCheck = true;
        }
        return isCheck;
    }
}