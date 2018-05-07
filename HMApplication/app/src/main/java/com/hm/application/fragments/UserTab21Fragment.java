package com.hm.application.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.hm.application.R;
import com.hm.application.adapter.TbThemeAdapter;
import com.hm.application.adapter.UserTab21Adapter;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab21Fragment extends Fragment {

    String uid;
    private RecyclerView mRv;
    private TextView mtv;
    private OnFragmentInteractionListener mListener;

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
        void toSetTitle(String title, boolean b);
    }

    public UserTab21Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_tab21, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            checkInternetConnection();
            mListener.toSetTitle("My Photos", false);
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void checkInternetConnection() {
        try {
            uid = User.getUser(getContext()).getUid();
            if (CommonFunctions.isOnline(getContext())) {
                Log.d("HmApp", "  agr fetch_photos list 1 " + getArguments());
                if (getArguments() != null) {
                    if (getArguments().getBoolean("other_user2")) {
                        Log.d("HmApp", "  agr fetch_photos list 2" + getArguments().getString("fetch_photos2"));
                        if (getArguments().getString("fetch_photos2") != null) {
                            toDisplayData(getArguments().getString("fetch_photos2"));
//                        } else if (getArguments().getString(AppConstants.F_UID) != null) {
//                            uid = getArguments().getString(AppConstants.F_UID);
//                            new toGetData().execute();
//                        } else {
//                            new toGetData().execute();
                        }
                    } else {
                        new toGetData().execute();
                    }
                } else {
                    new toGetData().execute();
                }
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    private void toDisplayData(String response) {
        try {
            Log.d("HmApp", "fetch_photos Res " + response);
            JSONArray array = new JSONArray(response.trim());
            if (array != null) {
                if (array.length() > 0) {
                    mRv = getActivity().findViewById(R.id.rvUSerTab21);
                    mRv.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    mRv.hasFixedSize();
                    mRv.setAdapter(new UserTab21Adapter(getContext(), array));
                    mRv.setNestedScrollingEnabled(false);
                } else {
                    toDispalyText("No Post");
//                    CommonFunctions.toDisplayToast("Ji", getContext());
                }
            } else {
                toDispalyText("No Post");
//                CommonFunctions.toDisplayToast("di", getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    private void toDispalyText(String s) {
        try {
            mtv.setVisibility(View.VISIBLE);
            mtv.setText(s);
            mRv.setVisibility(View.GONE);
        } catch (Exception | Error e) {

            e.printStackTrace();
        }

    }

    private class toGetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        "http://vnoi.in/hmapi/feed.php",
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                toDisplayData(response);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("HmApp", "Error " + error.getMessage());
                                                toDispalyText("No Post");
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_fetch_photos));
                                        params.put(getString(R.string.str_uid), uid);
                                        return params;
                                    }
                                }
                                , "fetch_photos");
            } catch (Exception | Error e) {
                e.printStackTrace();

            }
            return null;
        }
    }
}
