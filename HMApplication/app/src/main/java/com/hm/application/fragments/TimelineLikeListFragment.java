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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.adapter.TimelineLikeListAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class TimelineLikeListFragment extends Fragment {

    //    private TextView mTvLblFeaturedDest;
    public String timelineId = null;

    public TimelineLikeListFragment() {
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

//        mTvLblFeaturedDest = getActivity().findViewById(R.id.txtLblFeaturedDest);
//        mTvLblFeaturedDest.setVisibility(View.GONE);

        if (getArguments() != null) {
            if (getArguments().getString(AppConstants.TIMELINE_ID) != null) {
                timelineId = getArguments().getString(AppConstants.TIMELINE_ID);
            }
        }
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new TimelineLikeListFragment.toGetTimelineLike().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

        }
    }

    private class toGetTimelineLike extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        "http://vnoi.in/hmapi/display_like_details.php",
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "FriendReq Res " + response);
                                                    JSONArray array = new JSONArray(response);
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            RecyclerView mRv = getActivity().findViewById(R.id.mRvFriendRequest);
                                                            mRv.setLayoutManager(new LinearLayoutManager(getContext()));
                                                            mRv.hasFixedSize();
//                                                            mRv.setAdapter(new TimelineLikeListAdapter());
                                                            mRv.setAdapter(new TimelineLikeListAdapter(getContext(), array));
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Data ", getContext());
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Data ", getContext());
                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace(); Crashlytics.logException(e);
                                                    CommonFunctions.toDisplayToast("No Data", getContext());

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
                                        params.put(getString(R.string.str_action_), "display_like_details");
                                        params.put(getString(R.string.str_uid), User.getUser(getContext()).getUid());
                                        params.put("timeline_id", timelineId);
                                        return params;
                                    }
                                }
                                , "display_like_details");
            } catch (Exception | Error e) {
                e.printStackTrace(); Crashlytics.logException(e);
                CommonFunctions.toDisplayToast("No Data", getContext());

            }
            return null;
        }
    }
}