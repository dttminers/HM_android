package com.hm.application.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.PostObjRequest;
import com.hm.application.network.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main_HomeFragment extends Fragment {


    public Main_HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_home, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        new toLoginUser().execute();

    }

    private class toLoginUser extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
//                JSONObject obj = new JSONObject();
//                obj.put("data",
//                        new JSONObject()
//                                .put(getString(R.string.str_uid), "20")
////                                                .put(getString(R.string.str_email_), mEdtUserName.getText().toString().trim())
////                                                .put(getString(R.string.str_password_), mEdtPassword.getText().toString().trim())
//                                .put(getString(R.string.str_action_), "fetch_photos"));
//                new JSONObject()
//                        .put("action", "fetch_photos")
//                        .put(getString(R.string.str_uid), "20"),
                Map<String, String> params = new HashMap<String, String>();
                params.put(getString(R.string.str_action_), getString(R.string.str_fetch_photos));
                params.put(getString(R.string.str_uid), User.getUser(getContext()).getUid());

                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new PostObjRequest(
                                        AppConstants.URL + getContext().getResources().getString(R.string.str_feed) + "." + getContext().getResources().getString(R.string.str_php),
                                        new JSONObject(params.toString()),
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

    public void replacePage(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(fragment.getClass().getName())
                .replace(R.id.flHomeContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}