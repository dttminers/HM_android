package com.hm.application.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import com.hm.application.adapter.PackageSectionAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TBTravelWithUsFragment extends Fragment {

    LinearLayout mLlMain;

    public TBTravelWithUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tbtravel_with_us, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLlMain = getActivity().findViewById(R.id.llTbMain);
        if (mLlMain.getChildCount() > 0) {
            mLlMain.removeAllViews();
        }
        checkInternetConnection();
    }


    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new TravelPackage().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class TravelPackage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL +getString(R.string.str_travel_with_us_) + "." + getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "travel Res " + response);
                                                    JSONObject obj = new JSONObject(response.trim());
                                                    if (obj != null) {
                                                        if (!obj.isNull("today_deals")){
                                                            if (obj.getJSONArray("today_deals").length() > 0) {
                                                                toCreateSectionPackage(" Today's Deal", obj.getJSONArray("today_deals"));
                                                            }
                                                        }
                                                        if(!obj.isNull("social_plan")) {
                                                            if (obj.getJSONArray("social_plan").length() > 0) {
                                                                toCreateSectionPackage(" Social Plan Package ", obj.getJSONArray("social_plan"));
                                                            }
                                                        }
                                                        if(!obj.isNull("weekend_gateways")) {
                                                            if (obj.getJSONArray("weekend_gateways").length() > 0) {
                                                                toCreateSectionPackage("Weekend Gateways ", obj.getJSONArray("weekend_gateways"));
                                                            }
                                                        }
                                                        if(!obj.isNull("land_packages")) {
                                                            if (obj.getJSONArray("land_packages").length() > 0) {
                                                                toCreateSectionPackage("Land Packages ", obj.getJSONArray("land_packages"));
                                                            }
                                                        }
                                                        if(!obj.isNull("weekend_gateways")) {
                                                            if (obj.getJSONArray("weekend_gateways").length() > 0) {
                                                                toCreateSectionPackage("Weekend Gateways ", obj.getJSONArray("weekend_gateways"));
                                                            }
                                                        }
                                                        if(!obj.isNull("weekend_gateways")) {
                                                            if (obj.getJSONArray("weekend_gateways").length() > 0) {
                                                                toCreateSectionPackage("Weekend Gateways ", obj.getJSONArray("weekend_gateways"));
                                                            }
                                                        }
                                                        if(!obj.isNull("weekend_gateways")) {
                                                            if (obj.getJSONArray("weekend_gateways").length() > 0) {
                                                                toCreateSectionPackage("Weekend Gateways ", obj.getJSONArray("weekend_gateways"));
                                                            }
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

    private void toCreateSectionPackage(String name, JSONArray array) throws Exception, Error {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.packages_section_layout, null);
        if (view != null) {
            TextView mTvName = view.findViewById(R.id.txtPackageSec);
            mTvName.setText(name);
            ViewPager mVp = view.findViewById(R.id.vpPackageSec);
            mVp.setAdapter(new PackageSectionAdapter(getContext(), array));
//            mVp.setCurrentItem(2);
            mVp.setPageMargin(10);
            mVp.setOffscreenPageLimit(2);
            mLlMain.addView(view);
//            mVp.getChildAt(2).setSelected(true);
        }
    }
}