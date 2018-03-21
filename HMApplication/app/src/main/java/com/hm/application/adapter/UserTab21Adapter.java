package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

/**
 * Created by Developer on 20-03-2018.
 */

public class UserTab21Adapter extends RecyclerView.Adapter<UserTab21Adapter.ViewHolder> {

    private Context context;
    private JSONArray array;

    public UserTab21Adapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.album_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (!array.getJSONObject(position).isNull("image_url")) {
                Picasso.with(context)
                        .load(AppConstants.URL+array.getJSONObject(position).getString("image_url").replaceAll("\\s", "%20"))
                        .into(holder.mImgActPic);
            } else {
                holder.mImgActPic.setBackgroundColor(ContextCompat.getColor(context, R.color.light2));
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImgActPic;

        ViewHolder(View itemView) {
            super(itemView);

            mImgActPic = itemView.findViewById(R.id.imgAlbumPic);


        }
    }
}


