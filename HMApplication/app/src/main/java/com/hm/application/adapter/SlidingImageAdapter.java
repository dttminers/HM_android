package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;
import com.squareup.picasso.Picasso;

public class SlidingImageAdapter extends PagerAdapter {

    private String[] images;
    private Context context;

    public SlidingImageAdapter(Context ctx, String[] img) {
        context = ctx;
        images = img;
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
            Log.d("Hmapp",  "images "  + images[position] );
            View imageLayout = LayoutInflater.from(context).inflate(R.layout.single_image_view, view, false);
            ImageView imageView = imageLayout
                    .findViewById(R.id.image_single);
            Picasso.with(context)
                    .load(AppConstants.URL + images[position].trim().replace("\\s", "%20"))
                    .into(imageView);
            view.addView(imageLayout, 0);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CommonFunctions.toDisplayToast("Click ", context);
                }
            });
            return imageLayout;
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
        return null;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}