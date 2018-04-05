package com.hm.application.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.google.gson.JsonArray;
import com.hm.application.R;
import com.hm.application.adapter.PackageSectionRecyclerViewAdapter;
import com.hm.application.adapter.PackageSectionViewPagerAdapter;
import com.hm.application.adapter.TbDestinationsAdapter;
import com.hm.application.adapter.ThemeViewPager;
import com.hm.application.fragments.TBRentoutsFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.DepthPageTransformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DestinationInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapseToolbar;
    private LinearLayout mLlMain;
    private NestedScrollView mNsvScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_info);
        collapseToolbar = findViewById(R.id.ctl);
        collapseToolbar.setTitle(getString(R.string.app_name));
        collapseToolbar.setExpandedTitleTextAppearance(R.style.ExAppBar);
        collapseToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        mLlMain = findViewById(R.id.llDstInfoView);
        mNsvScroll = findViewById(R.id.nsvDstInfoView);
        if (mLlMain.getChildCount() > 0) {
            mLlMain.removeAllViews();
        }
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(DestinationInfoActivity.this)) {
                new DestinationInfoActivity.toShowDestinationInfo().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), this);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class toShowDestinationInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(DestinationInfoActivity.this)
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_destination_info) + getString(R.string.str_php),
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {

                                                    JSONObject obj = new JSONObject(response.trim());
                                                    if (obj != null) {
                                                        if (!obj.isNull(getString(R.string.str_activity))) {
                                                            if (obj.getJSONArray(getString(R.string.str_activity)).length() > 0) {
                                                                toCreateViewPager(obj.getJSONArray(getString(R.string.str_activity)));
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_activity))) {
                                                            if (obj.getJSONArray(getString(R.string.str_activity)).length() > 0) {
                                                                toCreateSectionPackage("PLACE TO VISIT", obj.getJSONArray(getString(R.string.str_activity)));
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_activity))) {
                                                            if (obj.getJSONArray(getString(R.string.str_activity)).length() > 0) {
                                                                toCreateSectionPackage("WEEKEND GETAWAYS", obj.getJSONArray(getString(R.string.str_activity)));
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_activity))) {
                                                            if (obj.getJSONArray(getString(R.string.str_activity)).length() > 0) {
                                                                toCreateRv(obj.getJSONArray(getString(R.string.str_activity)));
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
                                        params.put(getString(R.string.str_action_), getString(R.string.str_destination_info));
                                        params.put(getString(R.string.str_dest_id), "3");
                                        return params;
                                    }
                                }
                                , getString(R.string.str_destination_info));
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void toCreateSectionPackage(String name, JSONArray array) throws Exception, Error {
        Log.d("Hmapp", " Name of layout : " + name);
        View view = LayoutInflater.from(DestinationInfoActivity.this).inflate(R.layout.packages_section_layout, null);
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

    private void toCreateRv(JSONArray array) throws Exception, Error {

        LinearLayout mll = new LinearLayout(DestinationInfoActivity.this);
        mll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mll.setPadding(2, 5, 2, 5);
        mll.setOrientation(LinearLayout.VERTICAL);
        mll.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        RecyclerView rv = new RecyclerView(this);
        rv.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.hasFixedSize();
        rv.setAdapter(new TbDestinationsAdapter(this, array));
        mll.addView(rv);

        // Adding RecyclerView to main layout
        mLlMain.addView(mll);
    }

    private void toCreateViewPager(JSONArray array) throws Exception, Error {
        Log.d("HmApp", " toCreateViewPager " + array);
        ViewPager mVp = new ViewPager(DestinationInfoActivity.this);
        mVp.setAdapter(new ThemeViewPager(this, array));
        mVp.setOffscreenPageLimit(2);
        mVp.setPadding(5, 0, 5, 0);
        mLlMain.addView(mVp);
    }
}
