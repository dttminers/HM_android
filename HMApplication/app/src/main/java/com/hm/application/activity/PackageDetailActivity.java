package com.hm.application.activity;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.fragments.TBTravelBibleFragment;

public class PackageDetailActivity extends AppCompatActivity {

    private LinearLayout mLlPkgDetailMain, mLlPkgDetail;
    private NestedScrollView mNsvPkgDetail;
    private RelativeLayout mRlPkgDetail;
    private ImageView mIvPkgDetail;
    private TextView mTvPdDays, mTvPdAmt, mTvPdName, mTvPDLocation, mTvPdLevel, mTvPdActivityId, mTvPdContact, mTvLblFacilities,
            mTvPdFacilities, mTvLblItineraries, mTvLblOtherDetails, mTvLblPolicies, mTvViewMoreCmt;
    private Button mBtnBookNow;
    private RatingBar rbPkgDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
        DataBinding();
    }

    private void DataBinding() {

        mLlPkgDetailMain = (LinearLayout) findViewById(R.id.llPkgDetailMain);
        mLlPkgDetail = (LinearLayout) findViewById(R.id.llPkgDetail);

        mNsvPkgDetail = (NestedScrollView) findViewById(R.id.nsvPackageDetail);

        mRlPkgDetail = (RelativeLayout) findViewById(R.id.rlPkgDetail);

        mIvPkgDetail = (ImageView) findViewById(R.id.imgPkgDetail);



        mTvPdDays = (TextView) findViewById(R.id.txtPdDays);
        mTvPdAmt = (TextView) findViewById(R.id.txtPdAmt);
        mTvPdName = (TextView) findViewById(R.id.txtPdName);
        mTvPDLocation = (TextView) findViewById(R.id.txtPDLocation);
        mTvPdLevel = (TextView) findViewById(R.id.txtPdLevel);
        mTvPdActivityId = (TextView) findViewById(R.id.txtPdActivityId);
        mTvPdContact = (TextView) findViewById(R.id.txtPdContact);
        mTvLblFacilities = (TextView) findViewById(R.id.txtLblFacilities);
        mTvPdFacilities = (TextView) findViewById(R.id.txtPdFacilities);
        mTvLblItineraries = (TextView) findViewById(R.id.txtLblItineraries);
    }
}
