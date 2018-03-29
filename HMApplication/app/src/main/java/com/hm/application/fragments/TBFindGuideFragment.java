package com.hm.application.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.adapter.PackageSectionRecyclerViewAdapter;
import com.hm.application.adapter.PackageSectionViewPagerAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TBFindGuideFragment extends Fragment {

    private LinearLayout mLlMain;
    private NestedScrollView mNsvScroll;
    private ViewPager vp;

    public TBFindGuideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tb_find_guide, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLlMain = getActivity().findViewById(R.id.llFindGuide);
        mNsvScroll = getActivity().findViewById(R.id.nsvFindGuide);
        if (mLlMain.getChildCount() > 0) {
            mLlMain.removeAllViews();
        }
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new TBFindGuideFragment.toGetFindGuideDetailInfo().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }


    private class toGetFindGuideDetailInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_activity) + "." + getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "activity" + response);
                                                    JSONObject obj = new JSONObject(response.trim());
                                                    if (obj != null) {
                                                        if (!obj.isNull(getString(R.string.str_featured_activity))) {
                                                            if (obj.getJSONArray(getString(R.string.str_featured_activity)).length() > 0) {
                                                                toCreateSectionPackage("FEATURED ACTIVITIES", obj.getJSONArray(getString(R.string.str_featured_activity)));
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_normal_activity))) {
                                                            if (obj.getJSONArray(getString(R.string.str_normal_activity)).length() > 0) {
                                                                toCreateSectionPackage("THINGS TO DO", obj.getJSONArray(getString(R.string.str_normal_activity)));
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_normal_activity))) {
                                                            if (obj.getJSONArray(getString(R.string.str_normal_activity)).length() > 0) {
                                                                toCreateRv("ALL ACTIVITIES", obj.getJSONArray(getString(R.string.str_normal_activity)));
                                                            }
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
                                                Log.d("HmApp", "Error " + error.getMessage());
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_), "section1");
                                        return params;
                                    }
                                }
                                , "section1");
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private void toCreateRv(String name, JSONArray array) throws Exception, Error {
        Log.d("Hmapp", " NAme of layout : " + name);
        LinearLayout mll = new LinearLayout(getContext());
        mll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mll.setPadding(10, 10, 10, 10);
        mll.setOrientation(LinearLayout.VERTICAL);
        mll.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

        TextView mTv = new TextView(getContext());
        mTv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey4));
        mTv.setTextColor(ContextCompat.getColor(getContext(), R.color.grey5));
        mTv.setTextSize(CommonFunctions.dpToPx(getContext(), 10));
        mTv.setText(name);
        mTv.setGravity(Gravity.CENTER);
        mll.addView(mTv);

        RecyclerView rv = new RecyclerView(getContext());
        rv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new PackageSectionRecyclerViewAdapter(getContext(), array, getString(R.string.str_activity_info)));
        mll.addView(rv);

        // Adding RecyclerView to main layout
        mLlMain.addView(mll);

    }

    private void toCreateSectionPackage(String name, JSONArray array) throws Exception, Error {
        Log.d("Hmapp", " Name of layout : " + name);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.packages_section_layout, null);
        if (view != null) {
            TextView mTvName = view.findViewById(R.id.txtPackageSec);
            mTvName.setText(name);
            ViewPager mVp = view.findViewById(R.id.vpPackageSec);
            mVp.setAdapter(new PackageSectionViewPagerAdapter(getContext(), array,getString(R.string.str_activity_info)));
            mVp.setPageMargin(10);
            mVp.setOffscreenPageLimit(2);
            mVp.setPadding(5, 0, 5, 0);
            mLlMain.addView(view);
        }
    }
}
