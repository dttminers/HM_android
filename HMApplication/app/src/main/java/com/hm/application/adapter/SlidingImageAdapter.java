package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hm.application.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlidingImageAdapter extends PagerAdapter {

    private String[] images;
    private LayoutInflater inflater;
    private Context context;

    public SlidingImageAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.single_imageview, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.image_single);
        Log.d("HmAPP", " images : " + position + " : " + images[position]);

//        imageView.setImageResource(images[position]);
        Picasso.with(context)
                .load(images[position].trim().replace("\\s", "%20"))
                .into(imageView);


        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
