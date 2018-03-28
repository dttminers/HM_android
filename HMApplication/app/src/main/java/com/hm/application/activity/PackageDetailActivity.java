package com.hm.application.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PackageDetailActivity extends AppCompatActivity {

    private LinearLayout mLlPkgDetailMain, mLlPkgDetail, mLlDay1Info, mLlDay2Info;
    private TextView mTvToCarryInfo, mTvMealInfo, mTvActivityInfo, mTvGrpSizeInfo, mTvStayInfo, mTvImpPointInfo,
            mTvCncPolicyInfo, mTvRentPolicyInfo, mTvRefPolicyInfo, mTvCfPolicyInfo, mTvPrivacyPolicyInfo, mTvTermCondInfo;
    private NestedScrollView mNsvPkgDetail;
    private RelativeLayout mRlPkgDetail;
    private ImageView mIvPkgDetail;
    private TextView mTvPdDays, mTvPdAmt, mTvPdName, mTvPDLocation, mTvPdLevel, mTvPdActivityId, mTvPdContact, mTvLblFacilities, mTvLblFacilitiesData,
            mTvPdFacilities, mTvLblItineraries, mTvLblOtherDetails, mTvLblPolicies, mTvViewMoreCmt,
            mTvLblDay1, mTvLblDay2, mTvLblToCarry, mTvLblMeal, mTvLblActivity, mTvLblGrpSize, mTvLblStay, mTvLblImpPoint,
            mTvLblCncPolicy, mTvLblRentPolicy, mTvLblRefPolicy, mTvLblCfPolicy, mTvLblPrivacyPolicy, mTvLblTermCond;
    private Button mBtnBookNow;
    private RatingBar mRbPkgDetail;

    // iter item  layout
    private RelativeLayout mRrItinerary;
    private ImageView mIvItemIcon;
    private TextView mTvItemData, mTvItemTimeData;
    private View mV12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);
        try {

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            }

            toBindViews();
            checkInternetConnection();
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toBindViews() throws Error {

        mLlPkgDetailMain = findViewById(R.id.llPkgDetailMain);
        mLlPkgDetail = findViewById(R.id.llPkgDetail);
        mLlDay1Info = findViewById(R.id.llDay1Info);
        mLlDay2Info = findViewById(R.id.llDay2Info);

        mNsvPkgDetail = findViewById(R.id.nsvPackageDetail);
        mRlPkgDetail = findViewById(R.id.rlPkgDetail);
        mIvPkgDetail = findViewById(R.id.imgPkgDetail);

        mTvToCarryInfo = findViewById(R.id.txtToCarryInfo);
        mTvToCarryInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvMealInfo = findViewById(R.id.txtMealInfo);
        mTvMealInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvActivityInfo = findViewById(R.id.txtActivityInfo);
        mTvActivityInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvGrpSizeInfo = findViewById(R.id.txtGrpSizeInfo);
        mTvGrpSizeInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvStayInfo = findViewById(R.id.txtStayInfo);
        mTvStayInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvImpPointInfo = findViewById(R.id.txtImpPointInfo);
        mTvImpPointInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvCncPolicyInfo = findViewById(R.id.txtCncPolicyInfo);
        mTvCncPolicyInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvRentPolicyInfo = findViewById(R.id.txtRentPolicyInfo);
        mTvRentPolicyInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvRefPolicyInfo = findViewById(R.id.txtRefPolicyInfo);
        mTvRefPolicyInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvCfPolicyInfo = findViewById(R.id.txtCfPolicyInfo);
        mTvCfPolicyInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvPrivacyPolicyInfo = findViewById(R.id.txtPrivacyPolicyInfo);
        mTvPrivacyPolicyInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvTermCondInfo = findViewById(R.id.txtTermCondInfo);
        mTvTermCondInfo.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvPdDays = findViewById(R.id.txtPdDays);
        mTvPdDays.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvPdAmt = findViewById(R.id.txtPdAmt);
        mTvPdAmt.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvPdName = findViewById(R.id.txtPdName);
        mTvPdName.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvPDLocation = findViewById(R.id.txtPDLocation);
        mTvPDLocation.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvPdLevel = findViewById(R.id.txtPdLevel);
        mTvPdLevel.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvPdActivityId = findViewById(R.id.txtPdActivityId);
        mTvPdActivityId.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvPdContact = findViewById(R.id.txtPdContact);
        mTvPdContact.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvLblFacilities = findViewById(R.id.txtLblFacilities);
        mTvLblFacilities.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvLblFacilitiesData = findViewById(R.id.txtLblFacilitiesData);
        mTvLblFacilitiesData.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));
        // mTvPdFacilities = (TextView) findViewById(R.id.txtPdFacilities);
        // mTvPdFacilities.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvLblItineraries = findViewById(R.id.txtLblItineraries);
        mTvLblItineraries.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvLblOtherDetails = findViewById(R.id.txtLblOtherDetails);
        mTvLblOtherDetails.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvLblPolicies = findViewById(R.id.txtLblPolicies);
        mTvLblPolicies.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvViewMoreCmt = findViewById(R.id.txtViewMoreCmt);
        mTvViewMoreCmt.setTypeface(HmFonts.getRobotoMedium(PackageDetailActivity.this));

        mTvLblDay1 = findViewById(R.id.txtLblDay1);
        mTvLblDay1.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblDay2 = findViewById(R.id.txtLblDay2);
        mTvLblDay2.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblToCarry = findViewById(R.id.txtLblToCarry);
        mTvLblToCarry.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblMeal = findViewById(R.id.txtLblMeal);
        mTvLblMeal.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblActivity = findViewById(R.id.txtLblActivity);
        mTvLblActivity.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblGrpSize = findViewById(R.id.txtLblGrpSize);
        mTvLblGrpSize.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblStay = findViewById(R.id.txtLblStay);
        mTvLblStay.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblImpPoint = findViewById(R.id.txtLblImpPoint);
        mTvLblImpPoint.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblCncPolicy = findViewById(R.id.txtLblCncPolicy);
        mTvLblCncPolicy.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblRentPolicy = findViewById(R.id.txtLblRentPolicy);
        mTvLblRentPolicy.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblRefPolicy = findViewById(R.id.txtLblRefPolicy);
        mTvLblRefPolicy.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblCfPolicy = findViewById(R.id.txtLblCfPolicy);
        mTvLblCfPolicy.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblPrivacyPolicy = findViewById(R.id.txtLblPrivacyPolicy);
        mTvLblPrivacyPolicy.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        mTvLblTermCond = findViewById(R.id.txtLblTermCond);
        mTvLblTermCond.setTypeface(HmFonts.getRobotoRegular(PackageDetailActivity.this));

        //mBtnBookNow = findViewById(R.id.btnBookNow);
        mRbPkgDetail = findViewById(R.id.rbPd1);
        LayerDrawable star = (LayerDrawable) mRbPkgDetail.getProgressDrawable();
        star.getDrawable(2).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.light_orange2, null), PorterDuff.Mode.SRC_ATOP);
        star.getDrawable(0).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.light2, null), PorterDuff.Mode.SRC_ATOP);
        star.getDrawable(1).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.light_orange2, null), PorterDuff.Mode.SRC_ATOP);

        mTvLblDay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mLlDay1Info.getVisibility() == View.VISIBLE) {
                        mLlDay1Info.setVisibility(View.GONE);
                        mTvLblDay1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
                    } else {
                        mLlDay1Info.setVisibility(View.VISIBLE);
                        mTvLblDay1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collaspe_, 0);
                    }
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
            }
        });

        mTvLblDay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mLlDay2Info.getVisibility() == View.VISIBLE) {
                        mLlDay2Info.setVisibility(View.GONE);
                        mTvLblDay2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
                    } else {
                        mLlDay2Info.setVisibility(View.VISIBLE);
                        mTvLblDay2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collaspe_, 0);
                    }
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(PackageDetailActivity.this)) {
                new toGetDetailInfo().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), PackageDetailActivity.this);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class toGetDetailInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(PackageDetailActivity.this)
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_packages_info) + "." + getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "travel Res " + response);
                                                    if (response != null) {
                                                        if (new JSONObject(response.trim()) != null) {
                                                            JSONObject res = new JSONObject(response.trim());
                                                            JSONObject obj = res.getJSONObject("packages_details");
                                                            Log.d("HmAPp", " response pak detail " + obj.getString("title"));
                                                            if (obj != null) {
                                                                if (!obj.isNull(getString(R.string.str_title))) {
                                                                    mTvPdName.setText(obj.getString(getString(R.string.str_title)));
                                                                }
                                                                if (!obj.isNull(getString(R.string.str_price))) {
                                                                    mTvPdAmt.setText(obj.getString(getString(R.string.str_price)));
                                                                }
                                                                if (!obj.isNull(getString(R.string.str_destination))) {
                                                                    mTvPDLocation.setText(obj.getString(getString(R.string.str_destination)));
                                                                }
                                                                if (!obj.isNull(getString(R.string.str_package_day))) {
                                                                    if (!obj.isNull(getString(R.string.str_package_night))) {
                                                                        mTvPdDays.setText(obj.getString(getString(R.string.str_package_day)) + "D/" + obj.getString(getString(R.string.str_package_night)) + "N");
                                                                    } else {
                                                                        mTvPdDays.setText(obj.getString(getString(R.string.str_package_day)) + "D");
                                                                    }
                                                                } else {
                                                                    if (!obj.isNull(getString(R.string.str_package_night))) {
                                                                        mTvPdDays.setText(obj.getString(getString(R.string.str_package_night)) + "N");
                                                                    } else {
                                                                        mTvPdDays.setText("N.A.");
                                                                    }
                                                                }
                                                                if (!obj.isNull(getString(R.string.str_trek_level))) {
                                                                    mTvPdLevel.setText(getString(R.string.str_level) + obj.getString(getString(R.string.str_trek_level)));
                                                                }
                                                                if (!obj.isNull(getString(R.string.str_trek_level))) {
                                                                    mTvPdActivityId.setText(getString(R.string.str_activity_id) + obj.getString(getString(R.string.str_trek_level)));
                                                                }
                                                                if (!obj.isNull(getString(R.string.str_trek_level))) {
                                                                    mTvPdContact.setText(getString(R.string.str_contact) + obj.getString(getString(R.string.str_trek_level)));
                                                                }
                                                                if (!obj.isNull(getString(R.string.str_facilities_small))) {
                                                                    mTvLblFacilitiesData.setText(obj.getString(getString(R.string.str_facilities_small)));
                                                                }
                                                                if (!obj.isNull(getString(R.string.str_package_img_url))) {
                                                                    Picasso.with(getApplicationContext())
                                                                            .load(obj.getString(getString(R.string.str_package_img_url)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                                                                            .into(mIvPkgDetail);
                                                                }
                                                            } else {
                                                                CommonFunctions.toDisplayToast("Data Not Found", getApplicationContext());
                                                            }
                                                            Log.d("HmApp", " short " + res.getJSONArray("short_iteneraries"));
                                                            if (!res.isNull(getString(R.string.str_short_iteneraries))) {
                                                                if (res.getJSONArray(getString(R.string.str_short_iteneraries)).length() > 0) {
                                                                    toDisplayShortItenerariesData(res.getJSONArray(getString(R.string.str_short_iteneraries)));
                                                                }
                                                            }

                                                            if (!res.isNull(getString(R.string.str_long_iteneraries))) {
                                                                if (res.getJSONArray(getString(R.string.str_long_iteneraries)).length() > 0) {
                                                                    toDisplayLongItenerariesData(res.getJSONArray(getString(R.string.str_long_iteneraries)));
                                                                }

                                                            }
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Data Found", getApplicationContext());
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Data", getApplicationContext());
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
                                        params.put(getString(R.string.str_action_), getString(R.string.str_package_info));
                                        params.put(getString(R.string.str_id), "6");
                                        return params;
                                    }
                                }
                                , getString(R.string.str_package_info));
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void toDisplayShortItenerariesData(JSONArray si) {
        Log.d("HmApp", " si itter " + si);
     /* "short_iteneraries": [
        {
            "id": "1",
            "package_id": "6",
            "head_text": "Day 1",
            "time": "09:00:00",
            "icon": "http://www.vnoi.in/hmapi/uploads/icons8-car-50.png",
            "content": "Auckland"
        }
    ],     * */
        try {
            if (mLlDay1Info.getChildCount() > 0) {
                mLlDay1Info.removeAllViews();
            }
            View view = LayoutInflater.from(PackageDetailActivity.this).inflate(R.layout.itineraries_item_layout, null);
            if (view != null) {
                mRrItinerary = view.findViewById(R.id.rrItinerary);
                mIvItemIcon = view.findViewById(R.id.ivItemIcon);
                mTvItemData = view.findViewById(R.id.tvItemData);
                mTvItemTimeData = view.findViewById(R.id.tvItemTimeData);
                mV12 = findViewById(R.id.v12);

                for (int i = 0; i < si.length(); i++) {
                    Log.d("HmApp", "short_iteneraries " + si);
                    if (!si.getJSONObject(i).isNull(getString(R.string.str_time_small))) {
                        mTvItemTimeData.setText(si.getJSONObject(i).getString(getString(R.string.str_time_small)));
                    }
                    if (!si.getJSONObject(i).isNull(getString(R.string.str_content))) {
                        mTvItemData.setText(si.getJSONObject(i).getString(getString(R.string.str_content)));
                    }
                    if (!si.getJSONObject(i).isNull(getString(R.string.str_icon))) {
                        if (si.getJSONObject(i).getString(getString(R.string.str_icon)).contains(getString(R.string.str_upload))) {
                            Picasso.with(getApplicationContext())
                                    .load(AppConstants.URL + si.getJSONObject(i).getString(getString(R.string.str_icon)).trim().replaceAll("\\s", "%20"))
                                    .error(R.color.light)
                                    .placeholder(R.color.light)
                                    .into(mIvItemIcon);
                        } else {
                            Picasso.with(getApplicationContext())
                                    .load(si.getJSONObject(i).getString(getString(R.string.str_icon)).trim().replaceAll("\\s", "%20"))
                                    .error(R.color.light)
                                    .placeholder(R.color.light)
                                    .into(mIvItemIcon);

                        }
                    }
                    mLlDay1Info.addView(view);
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toDisplayLongItenerariesData(JSONArray si) {
     /* "long_iteneraries": [
        {
            "id": "2",
            "package_id": "6",
            "head_text": "Day 1",
            "title": "Auckland",
            "content": "Auckland"
        }
    ],     * */
        try {
            View view = LayoutInflater.from(PackageDetailActivity.this).inflate(R.layout.itineraries_item_layout, null);
            if (view != null) {
                mRrItinerary = view.findViewById(R.id.rrItinerary);
                mIvItemIcon = view.findViewById(R.id.ivItemIcon);
                mTvItemData = view.findViewById(R.id.tvItemData);
                mTvItemTimeData = view.findViewById(R.id.tvItemTimeData);
                mV12 = findViewById(R.id.v12);

                for (int i = 0; i < si.length(); i++) {
                    if (!si.getJSONObject(i).isNull(getString(R.string.str_title))) {
                        mTvItemTimeData.setText(si.getJSONObject(i).getString(getString(R.string.str_title)));
                    }
                    if (!si.getJSONObject(i).isNull(getString(R.string.str_content))) {
                        mTvItemData.setText(si.getJSONObject(i).getString(getString(R.string.str_content)));
                    }
                    if (!si.getJSONObject(i).isNull(getString(R.string.str_icon))) {
                        if (si.getJSONObject(i).getString(getString(R.string.str_icon)).contains(getString(R.string.str_upload))) {
                            Picasso.with(getApplicationContext())
                                    .load(AppConstants.URL + si.getJSONObject(i).getString(getString(R.string.str_icon)).trim().replaceAll("\\s", "%20"))
                                    .error(R.color.light)
                                    .placeholder(R.color.light)
                                    .into(mIvItemIcon);
                        } else {
                            Picasso.with(getApplicationContext())
                                    .load(si.getJSONObject(i).getString(getString(R.string.str_icon)).trim().replaceAll("\\s", "%20"))
                                    .error(R.color.light)
                                    .placeholder(R.color.light)
                                    .into(mIvItemIcon);

                        }
                    }

                    mLlDay2Info.addView(view);
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}