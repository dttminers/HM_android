package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.utils.HmFonts;

import org.json.JSONArray;


public class PackageSectionAdapter extends PagerAdapter {

    private Context context;
    private JSONArray data;

    public PackageSectionAdapter(Context ctx, JSONArray array) {
        context = ctx;
        data = array;
        Log.d("HmApp", " vp data  : " + data);

    }

    @Override
    public int getCount() {
        return data != null ? data.length() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View item = LayoutInflater.from(context).inflate(R.layout.place_info_item_layout, container, false);

        RelativeLayout mRlTravel1, mRlTravel2, mRlTravel3;
        ImageView mIvTravelPic;
        TextView mTxtTravelLoc, mTxtTravelPrice, mTxtTravelTitle;
        RatingBar mRbTravel;

        try {
            mRlTravel1 = item.findViewById(R.id.rlTravel1);
            mRlTravel2 = item.findViewById(R.id.rlTravel2);
            mRlTravel3 = item.findViewById(R.id.rlTravel3);
            mIvTravelPic = item.findViewById(R.id.imgTravel);
            mTxtTravelLoc = item.findViewById(R.id.txtPlace);
            mTxtTravelPrice = item.findViewById(R.id.txtPrice);
            mTxtTravelTitle = item.findViewById(R.id.txtTagName);
            mRbTravel = item.findViewById(R.id.rating_br);

            mTxtTravelTitle.setText(data.getJSONObject(position).getString("title"));
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
        container.addView(item);
//        container.getChildAt(1).setSelected(true);
        return item;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
//        return super.getPageWidth(position);
        return (0.45f);
    }
}
