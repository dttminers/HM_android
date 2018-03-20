package com.hm.application.user_data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.network.PostObjRequest;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private Button mBtnLogin;
    private TextInputLayout mTilUserName, mTilPassword;
    private EditText mEdtUserName, mEdtPassword;
    private ScrollView mSvParent;
    private LinearLayout mLlLogin, mLlProfilePic;
    private ImageView mIvProfilePic;
    private TextView mTxtRegister;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
            mBtnLogin.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mTilUserName = getActivity().findViewById(R.id.tilLoginUserName);
            mTilUserName.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mTilPassword = getActivity().findViewById(R.id.tilLoginPassword);
            mTilPassword.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mEdtUserName = getActivity().findViewById(R.id.edtLoginUserName);
            mEdtUserName.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mEdtPassword = getActivity().findViewById(R.id.edtLoginPassword);
            mEdtPassword.setTypeface(HmFonts.getRobotoMedium(getContext()));

            mSvParent = getActivity().findViewById(R.id.svLoginParent);

            mLlLogin = getActivity().findViewById(R.id.llLoginData);
            mLlProfilePic = getActivity().findViewById(R.id.llLoginPic);

            mIvProfilePic = getActivity().findViewById(R.id.ivLoginProfilePic);

            mTxtRegister = getActivity().findViewById(R.id.tvRegister);
            mTxtRegister.setTypeface(HmFonts.getRobotoMedium(getContext()));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, (CommonFunctions.getScreenHeight() / 2), 0, 0);
            mLlLogin.setLayoutParams(params);
            toSetProfilePic();

            mTxtRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toChangeScreen(new RegistrationFragment());
                }
            });

            mBtnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), MainHomeActivity.class));//MainHomeActivity
                    if (isValid() && isValidPassword()) {
//                        Toast.makeText(getContext(), " Successful", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(), " Try Again ", Toast.LENGTH_SHORT).show();
                        toLoginUser();
                    }
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

    private void toLoginUser() {
        try {
            VolleySingleton.getInstance(getContext())
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL + getContext().getResources().getString(R.string.str_register_login) + "." + getContext().getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //{"status":1,"msg":"login  Successfully","id":"20","username":"swapnil","email":"swapnil","contact":"123454"}
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        if (!response.isNull("status")) {
                                                            if (response.getInt("status") == 1) {
                                                                User user = new User(getContext());
                                                                user.setId(response.getString("id"));
                                                                user.setUsername(response.getString("username"));
                                                                user.setEmail(response.getString("email"));
                                                                user.setMobile(response.getString("contact"));
                                                                AppDataStorage.setUserInfo(getContext());
                                                                AppDataStorage.getUserInfo(getContext());
                                                                Log.d("HmApp", " UserName : " + User.getUser(getContext()).getUsername());
//                                                                toChangeScreen(new RegisterOTPFragment());
                                                                getContext().startActivity(new Intent(getContext(), MainHomeActivity.class));
                                                                Toast.makeText(getContext(), "Successfully ", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getContext(), "Unable to Login", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), "Unable to Login", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), "Unable to Login", Toast.LENGTH_SHORT).show();
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
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(getString(R.string.str_username_), mEdtUserName.getText().toString().trim());
                                    params.put(getString(R.string.str_email_), mEdtUserName.getText().toString().trim());
                                    params.put(getString(R.string.str_password_), mEdtPassword.getText().toString().trim());
                                    params.put(getString(R.string.str_action_), getString(R.string.str_login_small));
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(getString(R.string.str_header), getString(R.string.str_header_type));
                                    return super.getHeaders();
                                }
                            }
                            , getString(R.string.str_register_small)
                    );
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class toLoginUser extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new PostObjRequest(
                                        AppConstants.URL + getContext().getResources().getString(R.string.str_api) + "." + getContext().getResources().getString(R.string.str_php),
                                        new JSONObject()
                                                .put(getString(R.string.str_username_), mEdtUserName.getText().toString().trim())
                                                .put(getString(R.string.str_email_), mEdtUserName.getText().toString().trim())
                                                .put(getString(R.string.str_password_), mEdtPassword.getText().toString().trim())
                                                .put(getString(R.string.str_action_), getString(R.string.str_login_small)),
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    //{"status":1,"msg":"login Successfully","id":"20","username":"swapnil","email":"swapnil","contact":"123454"}
                                                    //{"status":0,"msg":"login Failed"}
                                                    if (response != null) {
                                                        if (!response.isNull("status")) {
                                                            if (response.getInt("status") == 1) {


                                                                Toast.makeText(getContext(), " Successfully ", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
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
                                , getString(R.string.str_login_small).toUpperCase());

            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void toChangeScreen(Fragment fragment) {
        try {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentrepalce, fragment)
                    .setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out, R.animator.flip_left_in, R.animator.flip_left_out)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commitAllowingStateLoss();
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}