package com.hm.application.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.adapter.FriendRequestAdapter;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main_FriendRequestFragment extends Fragment {


    public Main_FriendRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_friend_request, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkInternetConnection();

    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
//                new toGetFriendRequest().execute();
                toGetFriendRequest();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    private void toGetFriendRequest() {
//    private class toGetFriendRequest extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CommonFunctions.toCallLoader(getContext(), "Loading");
                    JSONObject obj = new JSONObject();
                    obj.put(getString(R.string.str_action_), getString(R.string.str_themes_));
                    VolleySingleton.getInstance(getContext())
                            .addToRequestQueue(
                                    new StringRequest(Request.Method.POST,
                                            "http://vnoi.in/hmapi/follow_data.php",
                                            new Response.Listener<String>() {

                                                @Override
                                                public void onResponse(String res) {
                                                    CommonFunctions.toCloseLoader();
                                                    try {
                                                        if (res != null && res.length() > 0) {
                                                            Log.d("HmApp", "FriendReq Res " + res);
                                                            JSONArray array = new JSONArray(res);
                                                            if (array != null) {
                                                                if (array.length() > 0) {
                                                                    RecyclerView mRv = getActivity().findViewById(R.id.mRvFriendRequest);
                                                                    mRv.setLayoutManager(new LinearLayoutManager(getContext()));
                                                                    mRv.hasFixedSize();
                                                                    mRv.setAdapter(new FriendRequestAdapter(getContext(), array));
                                                                } else {
                                                                    CommonFunctions.toDisplayToast("No Friend Request ", getContext());
                                                                }
                                                            } else {
                                                                CommonFunctions.toDisplayToast("No Friend Request", getContext());
                                                            }
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Friend Request", getContext());
                                                        }
                                                    } catch (Exception | Error e) {
                                                        e.printStackTrace();
                                                        Crashlytics.logException(e);
                                                        CommonFunctions.toDisplayToast("No Friend Request", getContext());

                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    error.printStackTrace();
                                                    Crashlytics.logException(error);
                                                    CommonFunctions.toCloseLoader();
                                                }
                                            }
                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put(getString(R.string.str_action_), getString(R.string.str_follow_request_fetch_));
                                            params.put(getString(R.string.str_uid), User.getUser(getContext()).getUid());
                                            return params;
                                        }
                                    }
                                    , getString(R.string.str_follow_request_fetch_));
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);
                    CommonFunctions.toCloseLoader();
                    CommonFunctions.toDisplayToast("No Friend Request", getContext());

                }
//            return null;
            }

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            CommonFunctions.toCallLoader(getContext(), "Loading");
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            CommonFunctions.toCloseLoader();
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//            CommonFunctions.toCloseLoader();
//        }
//    }
        }).start();
    }
}