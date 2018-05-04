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

import com.hm.application.R;
import com.hm.application.adapter.BucketListAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class BucketListFragment extends Fragment {
    private RecyclerView mRvBucketList;

    public BucketListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bucket_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new BucketListFragment.toGetBucketListInfo().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }


    private class toGetBucketListInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                CommonFunctions.toCallLoader(getContext(), "Loading");
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL +getString(R.string.str_bucketlist)+  getContext().getResources().getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("HmApp", "bucketlist" + response);
                                                try {
                                                    JSONArray array = new JSONArray(response.trim());
                                                    if (array != null) {
                                                        CommonFunctions.toCloseLoader();
                                                        if (array.length() > 0) {
                                                            mRvBucketList = getActivity().findViewById(R.id.rvBucketList);
                                                            mRvBucketList.setLayoutManager(new LinearLayoutManager(getContext()));
                                                            mRvBucketList.hasFixedSize();
                                                            mRvBucketList.setAdapter(new BucketListAdapter(getContext(), array));
                                                            CommonFunctions.toCloseLoader();
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Data Found", getContext());
                                                            CommonFunctions.toCloseLoader();
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Data Found", getContext());
                                                        CommonFunctions.toCloseLoader();
                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                    CommonFunctions.toCloseLoader();

                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                error.printStackTrace();
                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_),getString(R.string.str_fetch_bucketlist));
                                        params.put(getString(R.string.str_uid), "2");
                                        return params;
                                    }
                                }
                                , getContext().getString(R.string.str_fetch_bucketlist));
            } catch (Exception | Error e) {
                e.printStackTrace();
                CommonFunctions.toCloseLoader();

            }
            return null;
        }
    }
}
