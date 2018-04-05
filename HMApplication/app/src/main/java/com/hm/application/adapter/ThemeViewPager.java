package com.hm.application.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

public class ThemeViewPager extends PagerAdapter {

    private Context context;
    private JSONArray array;

    private ImageView mIvTheme;
    private TextView mTvName, mTvData;

    public ThemeViewPager(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @Override
    public int getCount() {
        return array != null ? array.length() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.theme_layout, container, false);
        try {
            mIvTheme = itemView.findViewById(R.id.imgThemePic);

            mTvName = itemView.findViewById(R.id.txtThemeName);
            mTvName.setTypeface(HmFonts.getRobotoBold(context));

            mTvData = itemView.findViewById(R.id.txtThemeNameData);
            mTvData.setTypeface(HmFonts.getRobotoBold(context));
            mTvData.setVisibility(View.GONE);

            Log.d("HmApp", " item obj : " + position + " : " + array.getJSONObject(position));
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_title))) {
                mTvName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_title)));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                Picasso.with(context)
                        .load(array.getJSONObject(position).getString(context.getString(R.string.str_image_url)))
                        .into(mIvTheme);
            } else {
                mIvTheme.setBackgroundColor(ContextCompat.getColor(context, R.color.light2));
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
        container.addView(itemView);
        return itemView;
    }


    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
        return (0.80f);
    }
}
