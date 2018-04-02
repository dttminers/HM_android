package com.hm.application.fragments;

import android.content.res.Resources;
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
import com.hm.application.R;
import com.hm.application.adapter.PackageSectionRecyclerViewAdapter;
import com.hm.application.adapter.PackageSectionViewPagerAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.DepthPageTransformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TBRentoutsFragment extends Fragment {

    private LinearLayout mLlMain;
    private NestedScrollView mNsvScroll;
    private ViewPager vp;


    public TBRentoutsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tbrentouts, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLlMain = getActivity().findViewById(R.id.llRentOut);
        mNsvScroll = getActivity().findViewById(R.id.nsvRentOut);
        if (mLlMain.getChildCount() > 0) {
            mLlMain.removeAllViews();
        }
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new toShowRentoutData().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class toShowRentoutData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_rentout) + getString(R.string.str_php),
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {

                                                    JSONObject obj = new JSONObject(response.trim());
                                                    if (obj != null) {
                                                        if (!obj.isNull(getString(R.string.str_featured_rentout))) {
                                                            if (obj.getJSONArray(getString(R.string.str_featured_rentout)).length() > 0) {
                                                                toCreateSectionPackage("FEATURED RENTOUTS", obj.getJSONArray(getString(R.string.str_featured_rentout)));
                                                            }
                                                        }
                                                        if (!obj.isNull(getString(R.string.str_normal_rentout))) {
                                                            if (obj.getJSONArray(getString(R.string.str_normal_rentout)).length() > 0) {
                                                                toCreateRv("ALL RENTOUTS", obj.getJSONArray(getString(R.string.str_normal_rentout)));
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
                                        params.put(getString(R.string.str_action_), getString(R.string.str_section1));
                                        return params;
                                    }
                                }
                                , getString(R.string.str_section1));
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void toCreateSectionPackage(String name, JSONArray array) throws Exception, Error {
        Log.d("Hmapp", " Name of layout : " + name);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.packages_section_layout, null);
        if (view != null) {
            TextView mTvName = view.findViewById(R.id.txtPackageSec);
            mTvName.setText(CommonFunctions.firstLetterCaps(name));
            final ViewPager mVp = view.findViewById(R.id.vpPackageSec);
            mVp.setAdapter(new PackageSectionViewPagerAdapter(getContext(), array, AppConstants.rentout_info));
            mVp.setOffscreenPageLimit(1);
            mVp.setPadding(5, 0, 5, 0);
            mLlMain.addView(view);

            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, r.getDisplayMetrics());
            Log.d("HmApp", " tpage 1 : " + ((int) (-1) * px));
            mVp.setPageMargin((int) (-1 * px));

            mVp.setPageTransformer(true, new DepthPageTransformer() {
                @Override
                public void transformPage(View page, float position) {
                    Log.d("HmApp", " tpage 2 : " + " pos " + position + " : " + mVp.getPageMargin());
                    if (position < -1) {
                        page.setAlpha(0);
                    } else if (position <= 1) {
                        float scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f));
                        Log.d("hmapp", " tpage 3 : " + scaleFactor);
                        page.setScaleX(scaleFactor);
                        page.setScaleY(scaleFactor);
                        page.setAlpha(scaleFactor);
                    } else {
                        page.setAlpha(0);
                    }
                }
            });
//            mVp.setPageTransformer(true, new DepthPageTransformer());
        }
    }

    private void toCreateRv(String name, JSONArray array) throws Exception, Error {
        Log.d("Hmapp", " NAme of layout : " + name);
        LinearLayout mll = new LinearLayout(getContext());
        mll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mll.setPadding(2, 5, 2, 5);
        mll.setOrientation(LinearLayout.VERTICAL);
        mll.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

        TextView mTv = new TextView(getContext());
        mTv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey4));
        mTv.setTextColor(ContextCompat.getColor(getContext(), R.color.grey5));
        mTv.setTextSize(CommonFunctions.dpToPx(getContext(), 12));
        mTv.setText(CommonFunctions.firstLetterCaps(name));
        mTv.setGravity(Gravity.CENTER);
        mll.addView(mTv);

        RecyclerView rv = new RecyclerView(getContext());
        rv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new PackageSectionRecyclerViewAdapter(getContext(), array, AppConstants.rentout_info));
        mll.addView(rv);

        // Adding RecyclerView to main layout
        mLlMain.addView(mll);
    }
}