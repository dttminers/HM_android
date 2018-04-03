package com.hm.application.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
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
import com.hm.application.adapter.PackageSectionViewPagerAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TBTravelWithUsFragment extends Fragment {

    private LinearLayout mLlMain;
    private NestedScrollView mNsvScroll;
    private ViewPager vp;
    private String action = "section1", TAG = "ScreenScroll";

    /*
     * http://vnoi.in/hmapi/uploads/20/profile_pics/28-03-2018%2013:53:18%20PM_22879ad42dec8375eHMG1522225396696.jpg
     * http://vnoi.in/hmapi/uploads/20/profile_pics/23-03-2018%2016:28:24%20PM_202879ad42dec8375e.jpg
     * http://vnoi.in/hmapi/uploads/20/profile_pics/21-03-2018%2018:30:57%20PM_202879ad42dec8375e.jpg
     * http://vnoi.in/hmapi/uploads/20/profile_pics/21-03-2018%2018:14:34%20PM_202879ad42dec8375e.jpg
     * */

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
        mNsvScroll = getActivity().findViewById(R.id.nsvMain);
        if (mLlMain.getChildCount() > 0) {
            mLlMain.removeAllViews();
        }
        checkInternetConnection();
    }


    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new toGetInfo().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class toGetInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_travel_with_us_) +  getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    //{"today_deals":null,"social_plan":null,"weekend_gateways":null}
                                                    //{"land_packages":null,"offers of the month":null,"season special":null,"luxury tours":null}
                                                    Log.d("HmApp", "travel Res " + response);
                                                    JSONObject obj = new JSONObject(response.trim());
                                                    if (obj != null) {
                                                        if (!obj.isNull(getString(R.string.str_today_deals))) {
                                                            if (obj.getJSONArray(getString(R.string.str_today_deals)).length() > 0) {
                                                                toCreateSectionPackage(getString(R.string.str_today_deals_h), obj.getJSONArray(getString(R.string.str_today_deals)), true);
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_social_plan))) {
                                                            if (obj.getJSONArray(getString(R.string.str_social_plan)).length() > 0) {
                                                                toCreateSectionPackage(getString(R.string.str_social_plan_pkg), obj.getJSONArray(getString(R.string.str_social_plan)), false);
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_weekend_gateways))) {
                                                            if (obj.getJSONArray(getString(R.string.str_weekend_gateways)).length() > 0) {
                                                                toCreateSectionPackage(getString(R.string.str_weekend_gateways_h), obj.getJSONArray(getString(R.string.str_weekend_gateways)), false);
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_land_packages))) {
                                                            if (obj.getJSONArray(getString(R.string.str_land_packages)).length() > 0) {
                                                                toCreateSectionPackage(getString(R.string.str_land_packages_h), obj.getJSONArray(getString(R.string.str_land_packages)), false);
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_offers_of_the_month))) {
                                                            if (obj.getJSONArray(getString(R.string.str_offers_of_the_month)).length() > 0) {
                                                                toCreateSectionPackage(getString(R.string.str_offers_of_the_month_h), obj.getJSONArray(getString(R.string.str_offers_of_the_month)), false);
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_season_special))) {
                                                            if (obj.getJSONArray(getString(R.string.str_season_special)).length() > 0) {
                                                                toCreateSectionPackage(getString(R.string.str_season_special_h), obj.getJSONArray(getString(R.string.str_season_special)), false);
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_luxury_tours))) {
                                                            if (obj.getJSONArray(getString(R.string.str_luxury_tours)).length() > 0) {
                                                                toCreateSectionPackage(getString(R.string.str_luxury_tours_h), obj.getJSONArray(getString(R.string.str_luxury_tours)), false);
                                                            }
                                                        }

                                                        if (action.equals(getString(R.string.str_section1))) {
                                                            action = getString(R.string.str_section2);
                                                            new toGetInfo().execute();
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
                                        params.put(getString(R.string.str_action_), action);
                                        return params;
                                    }
                                }
                                , action);
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void toCreateSectionPackage(String name, JSONArray array, boolean status) throws Exception, Error {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.packages_section_layout, null);
        if (view != null) {
            TextView mTvName = view.findViewById(R.id.txtPackageSec);
            mTvName.setText(CommonFunctions.firstLetterCaps(name));
            ViewPager mVp = view.findViewById(R.id.vpPackageSec);
            mVp.setAdapter(new PackageSectionViewPagerAdapter(getContext(), array, getString(R.string.str_package_info), status));
            mVp.setPageMargin(10);
            mVp.setOffscreenPageLimit(2);
            mVp.setPadding(5, 0, 5, 0);
            mLlMain.addView(view);
        }
    }
}