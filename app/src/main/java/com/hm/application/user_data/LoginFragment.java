package com.hm.application.user_data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.application.MainActivity;
import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.utils.CommonFunctions;

public class LoginFragment extends Fragment {

    private Button mBtnLogin;
    private TextInputLayout mTilUserName, mTilPassword;
    private EditText mEdtUserName, mEdtPassword;
    private ScrollView mSvParent;
    private LinearLayout mLlLogin, mLlProfilePic;
    private ImageView mIvProfilePic;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toBindViews();
    }

    private void toBindViews() {
        try {
            mBtnLogin = getActivity().findViewById(R.id.btnSignIn);

            mTilUserName = getActivity().findViewById(R.id.tilLoginUserName);
            mTilPassword = getActivity().findViewById(R.id.tilLoginPassword);

            mEdtUserName = getActivity().findViewById(R.id.edtLoginUserName);
            mEdtPassword = getActivity().findViewById(R.id.edtLoginPassword);

            mSvParent = getActivity().findViewById(R.id.svLoginParent);

            mLlLogin = getActivity().findViewById(R.id.llLoginData);
            mLlProfilePic = getActivity().findViewById(R.id.llLoginPic);

            mIvProfilePic = getActivity().findViewById(R.id.ivLoginProfilePic);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, (CommonFunctions.getScreenHeight() / 2), 0, 0);
            mLlLogin.setLayoutParams(params);
            toSetProfilePic();


            mBtnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), MainHomeActivity.class));//MainHomeActivity
//                    if (isValid() && isValidPassword()) {
//                        Toast.makeText(getContext(), " Successful", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(), " Try Again ", Toast.LENGTH_SHORT).show();
//                    }
                }
            });

            mEdtUserName.setOnEditorActionListener(new EditText.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (isValid()) {
                            return true;
                        }
                    }
                    return false;
                }
            });

            mEdtPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (isValidPassword()) {
                            return true;
                        }
                    }
                    return false;
                }
            });

        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toSetProfilePic() {
//        mIvProfilePic.setImageDrawable(CommonFunctions.createRoundedBitmapImageDrawableWithBorder(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.logo_)));
        CommonFunctions.CircularIvBorderShadow(mIvProfilePic, BitmapFactory.decodeResource(getResources(), R.drawable.logo_), getContext());
    }

    public boolean isValid() {
        if (mEdtUserName.getText().toString().trim().length() == 0) {
            mTilUserName.setError(" Empty ");
            return false;
        } else if (mEdtUserName.getText().toString().trim().length() < 3) {
            mTilUserName.setError(" Min 3 letter ");
            return false;
        } else if (mEdtUserName.getText().toString().trim().length() > 25) {
            mTilUserName.setError(" Max 25 letter");
            return false;
        } else {
            mTilUserName.setError(null);
            return true;
        }
    }

    public boolean isValidPassword() {
        if (mEdtPassword.getText().toString().trim().length() == 0) {
            mTilPassword.setError(" Empty ");
            return false;
        } else if (mEdtPassword.getText().toString().trim().length() < 8) {
            mTilPassword.setError(" Min 8 letter ");
            return false;
        } else if (mEdtPassword.getText().toString().trim().length() > 25) {
            mTilPassword.setError(" Max 25 letter");
            return false;
        } else if (CommonFunctions.isValidPassword(mEdtPassword.getText().toString().trim())) {
            mTilPassword.setError(" Password Should contain atleast 1 uppercase letter, 1 lowercase letter, 1 special character, 1 number");
            return false;
        } else {
            mTilPassword.setError(null);
            return true;
        }
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}