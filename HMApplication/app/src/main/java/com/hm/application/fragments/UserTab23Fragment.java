package com.hm.application.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.hm.application.adapter.UserTab21Adapter;
import com.hm.application.adapter.UserTab23Adapter;
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
public class UserTab23Fragment extends Fragment {


    public UserTab23Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_tab23, container, false);
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new UserTab23Fragment.toUser23().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class toUser23 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_feed) +  getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "Tag Res " + response);
                                                    JSONArray array = new JSONArray(response.trim());
                                                    if (array != null){
                                                        if (array.length()> 0){
                                                            RecyclerView mRv = getActivity().findViewById(R.id.rvUSerTab23);
                                                            mRv.setLayoutManager(new LinearLayoutManager(getContext()));
                                                            mRv.hasFixedSize();
                                                            mRv.setAdapter(new UserTab23Adapter(getContext(), array));
                                                            mRv.setNestedScrollingEnabled(false);
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Data Found", getContext());
                                                        }
                                                    }
                                                    else {
                                                        CommonFunctions.toDisplayToast("No Data Found", getContext());
                                                    }
                                                } catch (Exception| Error e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("HmApp", "Error " + error.getMessage());
                                            }
                                        }
                                )
                                {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_tag_data));
                                        params.put(getString(R.string.str_uid),"2");
                                        return params;
                                    }
                                }
                                , getString(R.string.str_tag_data));
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}



