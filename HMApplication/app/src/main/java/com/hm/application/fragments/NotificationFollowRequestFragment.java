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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.adapter.NotificationAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFollowRequestFragment extends Fragment {
    private RecyclerView mRvNfMain;
    private RelativeLayout mRlFollowRequest;
    private TextView mTvNfFollow;

    public NotificationFollowRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow_request, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkInternetConnection();
        mTvNfFollow = getActivity().findViewById(R.id.tvNfFollow);
        mTvNfFollow.setVisibility(View.GONE);
        mRlFollowRequest = getActivity().findViewById(R.id.rlFollowRequest);
        mRlFollowRequest.setVisibility(View.GONE);
    }


    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new NotificationFollowRequestFragment.toGetFollowNotification().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }


    private class toGetFollowNotification extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_notification_uid) + getContext().getResources().getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("HmApp", "notification:" + response);
                                                try {
                                                    JSONArray array = new JSONArray(response.trim());
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            mRvNfMain = getActivity().findViewById(R.id.rvNfMain);
                                                            mRvNfMain.setLayoutManager(new LinearLayoutManager(getContext()));
                                                            mRvNfMain.hasFixedSize();
                                                            mRvNfMain.setAdapter(new NotificationAdapter(getContext(), array));

                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Notification", getContext());
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Notification", getContext());

                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                    FirebaseCrash.report(e);
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                error.printStackTrace();
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_list_notification));
                                        params.put(getString(R.string.str_uid), User.getUser(getContext()).getUid());
                                        return params;
                                    }
                                }
                                , getContext().getString(R.string.str_sent_notification));
            } catch (Exception | Error e) {
                e.printStackTrace();
                CommonFunctions.toCloseLoader();
                FirebaseCrash.report(e);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CommonFunctions.toCallLoader(getContext(), "Loading");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CommonFunctions.toCloseLoader();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            CommonFunctions.toCloseLoader();
        }

    }
}
