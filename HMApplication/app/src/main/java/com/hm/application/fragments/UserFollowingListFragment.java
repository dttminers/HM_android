package com.hm.application.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.adapter.FriendRequestAdapter;
import com.hm.application.adapter.UserFollowingListAdapter;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserFollowingListFragment extends Fragment {

    public UserFollowingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.rv_layout, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkInternetConnection();

    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new toGetData().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class toGetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(getString(R.string.str_action_), getString(R.string.str_themes_));
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        "http://vnoi.in/hmapi/follow_data.php",
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "FriendReq Res " + response);
                                                    JSONArray array = new JSONArray(response);
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            RecyclerView mRvThemes = getActivity().findViewById(R.id.mRvCommon);
                                                            mRvThemes.setLayoutManager(new LinearLayoutManager(getContext()));
                                                            mRvThemes.hasFixedSize();
                                                            mRvThemes.setAdapter(new UserFollowingListAdapter(getContext(), array));
                                                        }
                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("HmApp", "Theme error " + error.getMessage());
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_follow_follower_fetch_));
                                        params.put(getString(R.string.str_uid), User.getUser(getContext()).getId());
                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_header), getString(R.string.str_header_type));
                                        // params.put("Content-Type","application/form-data");
                                        return super.getHeaders();
                                    }
                                }
                                , "Theme");
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}