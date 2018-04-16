package com.hm.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.activity.SinglePostDataActivity;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;
import com.squareup.picasso.Picasso;

public class SlidingImageAdapter extends PagerAdapter {

    private String[] images;
    private String timelineId;
    private Context context;
    private boolean status;

    public SlidingImageAdapter(Context ctx, String[] img, String timeline_id, boolean b) {
        context = ctx;
        images = img;
        timelineId = timeline_id;
        status = b;
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
            Log.d("Hmapp", "images " + images[position]);
            View inflate = LayoutInflater.from(context).inflate(R.layout.single_image_view, view, false);
            ImageView imageView = inflate.findViewById(R.id.image_single);
            Picasso.with(context)
                    .load(AppConstants.URL + images[position].trim().replace("\\s", "%20"))
                    .into(imageView);
            view.addView(inflate, 0);

//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        context.startActivity(
//                                new Intent(context, SinglePostDataActivity.class)
//                                        .putExtra(AppConstants.FROM, "Single")
//                                        .putExtra(AppConstants.BUNDLE, array.getJSONObject(getAdapterPosition()).toString()));
//                    } catch (Exception | Error e) {
//                        e.printStackTrace();
//                        FirebaseCrash.report(e);
//                    }
//                }
//            });
            return inflate;
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