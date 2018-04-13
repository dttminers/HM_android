package com.hm.application.user_data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.utils.HmFonts;

public class RegisterOTPFragment extends Fragment {

    private Button mBtnResend, mBtnSubmit;
    private EditText mEdtOTP1, mEdtOTP2, mEdtOTP3, mEdtOTP4;
    private TextView mTvLblVerifyNumber, mTvLblMobileNumber, mTvTimer,mTvOTPError;

    private OnFragmentInteractionListener mListener;

    public RegisterOTPFragment() {
        // Required empty public constructor
    }

    public static RegisterOTPFragment newInstance(String param1, String param2) {
        return new RegisterOTPFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_otp, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onBindViews();
    }

    private void onBindViews() {

        mBtnResend = getActivity().findViewById(R.id.btnResend);
        mBtnResend.setTypeface(HmFonts.getRobotoRegular(getContext()));
        mBtnSubmit = getActivity().findViewById(R.id.btnSubmit);
        mBtnSubmit.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mEdtOTP1 = getActivity().findViewById(R.id.edtOTP1);
        mEdtOTP1.setTypeface(HmFonts.getRobotoRegular(getContext()));
        mEdtOTP2 = getActivity().findViewById(R.id.edtOTP2);
        mEdtOTP2.setTypeface(HmFonts.getRobotoRegular(getContext()));
        mEdtOTP3 = getActivity().findViewById(R.id.edtOTP3);
        mEdtOTP3.setTypeface(HmFonts.getRobotoRegular(getContext()));
        mEdtOTP4 = getActivity().findViewById(R.id.edtOTP4);
        mEdtOTP4.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mTvLblVerifyNumber = getActivity().findViewById(R.id.tvVerifyNumber);
        mTvLblVerifyNumber.setTypeface(HmFonts.getRobotoRegular(getContext()));
        mTvLblMobileNumber = getActivity().findViewById(R.id.tvMobileNumber);
        mTvLblMobileNumber.setTypeface(HmFonts.getRobotoRegular(getContext()));
        mTvTimer = getActivity().findViewById(R.id.tvOTPTimer);
        mTvTimer.setTypeface(HmFonts.getRobotoRegular(getContext()));
        mTvOTPError =getActivity().findViewById(R.id.tvOTP_error);
        mTvOTPError.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), MainHomeActivity.class));
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public boolean isOTPValid() {
        return getOTP().equals("5896");
    }

    private String getOTP() {
        return mEdtOTP1.getText().toString().trim() +
                mEdtOTP2.getText().toString().trim() +
                mEdtOTP3.getText().toString().trim() +
                mEdtOTP4.getText().toString().trim();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
