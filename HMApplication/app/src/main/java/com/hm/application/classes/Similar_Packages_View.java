//
//package com.hm.application.classes;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import com.hm.application.R;
//import com.hm.application.adapter.PackageSectionRecyclerViewAdapter;
//import com.hm.application.adapter.PackageSectionViewPagerAdapter;
//import com.hm.application.model.AppConstants;
//import com.hm.application.utils.CommonFunctions;
//import com.hm.application.utils.DepthPageTransformer;
//import org.json.JSONArray;
//
//
//public class Similar_Packages_View {
//
//    private void toCreateSectionPackage(Context context, String name, JSONArray array) throws Exception, Error {
//        Log.d("Hmapp", " Name of layout : " + name);
//        View view = LayoutInflater.from(context).inflate(R.layout.packages_section_layout, null);
//        if (view != null) {
//            TextView mTvName = view.findViewById(R.id.txtPackageSec);
//            mTvName.setText(CommonFunctions.firstLetterCaps(name));
//            final ViewPager mVp = view.findViewById(R.id.vpPackageSec);
//            mVp.setAdapter(new PackageSectionViewPagerAdapter(context, array, AppConstants.rentout_info, false));
//            mVp.setOffscreenPageLimit(1);
//            mVp.setPadding(5, 0, 5, 0);
//            mLlMain.addView(view);
//
//            Resources r = getResources();
//            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, r.getDisplayMetrics());
//            mVp.setPageMargin((int) (-1 * px));
//            mVp.setPageTransformer(true, new DepthPageTransformer());
//            //            mVp.setPageTransformer(true, new DepthPageTransformer());
//        }
//    }
//
//    private void toCreateRv(Context context,String name, JSONArray array) throws Exception, Error {
//        Log.d("Hmapp", " NAme of layout : " + name);
//        LinearLayout mll = new LinearLayout(context);
//        mll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        mll.setPadding(2, 5, 2, 5);
//        mll.setOrientation(LinearLayout.VERTICAL);
//        mll.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
//
//        TextView mTv = new TextView(context);
//        mTv.setBackgroundColor(ContextCompat.getColor(context, R.color.grey4));
//        mTv.setTextColor(ContextCompat.getColor(context, R.color.grey5));
//        mTv.setTextSize(CommonFunctions.dpToPx(context, 12));
//        mTv.setText(CommonFunctions.firstLetterCaps(name));
//        mTv.setGravity(Gravity.CENTER);
//        mll.addView(mTv);
//
//        RecyclerView rv = new RecyclerView(context);
//        rv.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
//        rv.setLayoutManager(new LinearLayoutManager(context));
//        rv.setAdapter(new PackageSectionRecyclerViewAdapter((context), array, AppConstants.rentout_info));
//        mll.addView(rv);
//
//        // Adding RecyclerView to main layout
//        mLlMain.addView(mll);
//    }
//}
//
//
