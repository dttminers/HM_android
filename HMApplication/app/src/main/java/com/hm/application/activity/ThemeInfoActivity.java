package com.hm.application.activity;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
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

import com.google.gson.JsonObject;
import com.hm.application.R;
import com.hm.application.adapter.PackageSectionViewPagerAdapter;
import com.hm.application.adapter.TbDestinationsAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.DepthPageTransformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThemeInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapseToolbar;
    private LinearLayout mLlMain;
    private NestedScrollView mNsvScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_info);

        collapseToolbar = findViewById(R.id.ctlThemeInfo);
        collapseToolbar.setTitle(getString(R.string.app_name));
        collapseToolbar.setExpandedTitleTextAppearance(R.style.ExAppBar);
        collapseToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        mLlMain = findViewById(R.id.llThemeInfoView);
        mNsvScroll = findViewById(R.id.nsvThemeInfoView);
        if (mLlMain.getChildCount() > 0) {
            mLlMain.removeAllViews();
        }
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(ThemeInfoActivity.this)) {
                new ThemeInfoActivity.toShowThemeInfo().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), this);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    private class toShowThemeInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(ThemeInfoActivity.this)
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_themes_info) + getString(R.string.str_php),
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject obj = new JSONObject(response.trim());
                                                    if (obj != null) {
                                                        if (!obj.isNull("package")) {
                                                            if (!obj.getJSONObject("package").isNull(getString(R.string.str_land_package))) {
                                                                if (obj.getJSONObject("package").getJSONArray(getString(R.string.str_land_package)).length() > 0) {
                                                                    toCreateSectionPackage("Land Packages", obj.getJSONObject("package").getJSONArray(getString(R.string.str_land_package)));
                                                                }
                                                            }
                                                            if (!obj.getJSONObject("package").isNull(getString(R.string.str_season_special))) {
                                                                if (obj.getJSONObject("package").getJSONArray(getString(R.string.str_season_special)).length() > 0) {
                                                                    toCreateSectionPackage("Season Special", obj.getJSONObject("package").getJSONArray(getString(R.string.str_season_special)));
                                                                }
                                                            }
                                                            if (!obj.getJSONObject("package").isNull(getString(R.string.str_social_plan))) {
                                                                if (obj.getJSONObject("package").getJSONArray(getString(R.string.str_social_plan)).length() > 0) {
                                                                    toCreateSectionPackage("Social Plan", obj.getJSONObject("package").getJSONArray(getString(R.string.str_social_plan)));
                                                                }
                                                            }
                                                            if (!obj.getJSONObject("package").isNull(getString(R.string.str_luxury_tours))) {
                                                                if (obj.getJSONObject("package").getJSONArray(getString(R.string.str_luxury_tours)).length() > 0) {
                                                                    toCreateSectionPackage("Luxury Tours", obj.getJSONObject("package").getJSONArray(getString(R.string.str_luxury_tours)));
                                                                }
                                                            }
                                                            if (!obj.getJSONObject("package").isNull(getString(R.string.str_weekend_gateways))) {
                                                                if (obj.getJSONObject("package").getJSONArray(getString(R.string.str_weekend_gateways)).length() > 0) {
                                                                    toCreateSectionPackage("WEEKEND GETAWAYS", obj.getJSONObject("package").getJSONArray(getString(R.string.str_weekend_gateways)));
                                                                }
                                                            }
                                                        }
                                                        if (!obj.isNull("rentout")) {
                                                            if (!obj.getJSONObject("rentout").isNull(getString(R.string.str_featured_rentout))) {
                                                                if (obj.getJSONObject("rentout").getJSONArray(getString(R.string.str_featured_rentout)).length() > 0) {
                                                                    toCreateRv("FEATURED RENTOUTS", obj.getJSONObject("rentout").getJSONArray(getString(R.string.str_featured_rentout)));
                                                                }
                                                            }
                                                            if (!obj.getJSONObject("rentout").isNull(getString(R.string.str_normal_rentout))) {
                                                                if (obj.getJSONObject("rentout").getJSONArray(getString(R.string.str_normal_rentout)).length() > 0) {
                                                                    toCreateRv("ALL RENTOUTS", obj.getJSONObject("rentout").getJSONArray(getString(R.string.str_normal_rentout)));
                                                                }
                                                            }
                                                        }
                                                        if (!obj.isNull("activity")) {
                                                            if (!obj.getJSONObject("activity").isNull(getString(R.string.str_featured_activity))) {
                                                                if (obj.getJSONObject("activity").getJSONArray(getString(R.string.str_featured_activity)).length() > 0) {
                                                                    toCreateRv("FEATURED ACTIVITIES", obj.getJSONObject("activity").getJSONArray(getString(R.string.str_featured_activity)));
                                                                }
                                                            }
                                                            if (!obj.getJSONObject("activity").isNull(getString(R.string.str_normal_activity))) {
                                                                if (obj.getJSONObject("activity").getJSONArray(getString(R.string.str_normal_activity)).length() > 0) {
                                                                    toCreateRv("ALL ACTIVITIES", obj.getJSONObject("activity").getJSONArray(getString(R.string.str_normal_activity)));
                                                                }
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
                                        params.put(getString(R.string.str_action_), getString(R.string.str_themes_info));
                                        params.put(getString(R.string.str_theme_id), "1");
                                        return params;
                                    }
                                }
                                , getString(R.string.str_themes_info));
            } catch (Exception | Error e) {
                e.printStackTrace();

            }
            return null;
        }
    }

    private void toCreateSectionPackage(String name, JSONArray array) throws Error {
        Log.d("Hmapp", " Name of layout : " + name);
        View view = LayoutInflater.from(ThemeInfoActivity.this).inflate(R.layout.packages_section_layout, null);
        if (view != null) {
            TextView mTvName = view.findViewById(R.id.txtPackageSec);
            mTvName.setText(CommonFunctions.firstLetterCaps(name));
            final ViewPager mVp = view.findViewById(R.id.vpPackageSec);
            mVp.setAdapter(new PackageSectionViewPagerAdapter(this, array, AppConstants.destination_info, false));
            mVp.setOffscreenPageLimit(1);
            mVp.setPadding(5, 0, 5, 0);
            mLlMain.addView(view);

            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, r.getDisplayMetrics());
            mVp.setPageMargin((int) (-1 * px));
            mVp.setPageTransformer(true, new DepthPageTransformer());
            //            mVp.setPageTransformer(true, new DepthPageTransformer());
        }
    }

    private void toCreateRv(String name, JSONArray array) throws Error {

        LinearLayout mll = new LinearLayout(ThemeInfoActivity.this);
        mll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mll.setPadding(2, 5, 2, 5);
        mll.setOrientation(LinearLayout.VERTICAL);
        mll.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        TextView mTv = new TextView(ThemeInfoActivity.this);
        mTv.setBackgroundColor(ContextCompat.getColor(this, R.color.grey4));
        mTv.setTextColor(ContextCompat.getColor(this, R.color.grey5));
        mTv.setTextSize(CommonFunctions.dpToPx(this, 12));
        mTv.setText(CommonFunctions.firstLetterCaps(name));
        mTv.setGravity(Gravity.CENTER);
        mTv.setVisibility(View.GONE);
//        mll.addView(mTv);

        RecyclerView rv = new RecyclerView(this);
        rv.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.hasFixedSize();
        rv.setAdapter(new TbDestinationsAdapter(this, array));
        mll.addView(rv);

        // Adding RecyclerView to main layout
        mLlMain.addView(mll);
    }
}
