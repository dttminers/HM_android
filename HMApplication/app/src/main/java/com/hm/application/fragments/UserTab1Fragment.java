package com.hm.application.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;
import com.hm.application.R;
import com.hm.application.adapter.GridAdapter;
import com.hm.application.adapter.SlidingImageAdapter;
import com.hm.application.classes.UserTimeLinePost;
import com.hm.application.model.AppConstants;
import com.hm.application.model.DemoItem;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTab1Fragment extends Fragment {

    private LinearLayout mLlPostMain;

    public UserTab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_tab1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLlPostMain = getActivity().findViewById(R.id.llPostMain);
        if (mLlPostMain.getChildCount() > 0) {
            mLlPostMain.removeAllViews();
        }
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new toDisplayInfo().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class toDisplayInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (mLlPostMain.getChildCount() > 0) {
                    mLlPostMain.removeAllViews();
                }
                JSONObject obj = new JSONObject();
                obj.put(getString(R.string.str_action_), getString(R.string.str_themes_));
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_feed) + "." + getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "fetch_timeline Res " + response);
                                                    JSONArray array = new JSONArray(response);
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            for (int i = 0; i < array.length(); i++) {
                                                                if (!array.getJSONObject(i).isNull("activity")) {
                                                                    if (array.getJSONObject(i).getString("activity").equals("photo")) {
                                                                        UserTimeLinePost.toDisplayNormalPost(array.getJSONObject(i), getContext(), mLlPostMain);
                                                                    } else if (array.getJSONObject(i).getString("activity").equals("post")) {
                                                                        UserTimeLinePost.toDisplayMultiImagePost(array.getJSONObject(i), getContext(), mLlPostMain);
                                                                    } else if (array.getJSONObject(i).getString("activity").equals("album")) {
                                                                        UserTimeLinePost.toDisplayPhotoPost(array.getJSONObject(i), getContext(), mLlPostMain);
                                                                    } else {
                                                                        UserTimeLinePost.toDisplayNormalPost(array.getJSONObject(i), getContext(), mLlPostMain);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            CommonFunctions.toDisplayToast("Ji", getContext());
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("di", getContext());
                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("HmApp", "Post error " + error.getMessage());
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_fetch_timeline_));
                                        params.put(getString(R.string.str_uid), User.getUser(getContext()).getUid());
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
                                , getString(R.string.str_fetch_timeline_));
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
