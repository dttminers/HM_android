package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.fragments.UserTab1Fragment;
import com.hm.application.model.AppConstants;
import com.squareup.picasso.Picasso;

public class SlidingImageAdapter extends PagerAdapter {

    private String[] images;
    private String pos;
    private Context context;
    private TabLayout tabLayout;
    private UserTab1Fragment userTab1Fragment;

    public SlidingImageAdapter(Context ctx, String[] img, String position, UserTab1Fragment userTab1Frg, TabLayout mTl) {
        context = ctx;
        images = img;
        pos = position;
        tabLayout = mTl;
        userTab1Fragment = userTab1Frg;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, final int position) {
        try {
            Log.d("Hmapp", "images " + images[position]);
            View inflate = LayoutInflater.from(context).inflate(R.layout.single_image_view, view, false);
            ImageView imageView = inflate.findViewById(R.id.image_single);
            Picasso.with(context)
                    .load(AppConstants.URL + images[position].trim().replace("\\s", "%20"))
                    .into(imageView);
            view.addView(inflate, 0);

//            imageView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if (userTab1Fragment != null) {
////                        userTab1Fragment.toCallSinglePostData(Integer.parseInt(pos), "Multiple");
////                    }
////                }
////            });

            if (images.length > 1) {
                tabLayout.setVisibility(View.VISIBLE);
            } else {
                tabLayout.setVisibility(View.GONE);
            }
            return inflate;
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
        return null;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}