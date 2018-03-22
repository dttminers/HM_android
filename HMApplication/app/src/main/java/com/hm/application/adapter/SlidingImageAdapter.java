package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hm.application.R;
import com.squareup.picasso.Picasso;

public class SlidingImageAdapter extends PagerAdapter {

    private String[] images;
    private Context context;

    public SlidingImageAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
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
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        try {
            View imageLayout = LayoutInflater.from(context).inflate(R.layout.single_imageview, view, false);
            ImageView imageView = imageLayout
                    .findViewById(R.id.image_single);
            Picasso.with(context)
                    .load(images[position].trim().replace("\\s", "%20"))
                    .into(imageView);
            view.addView(imageLayout, 0);
            return imageLayout;
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}