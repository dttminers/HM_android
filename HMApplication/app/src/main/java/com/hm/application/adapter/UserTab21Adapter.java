package com.hm.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.activity.SinglePostDataActivity;
import com.hm.application.model.AppConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

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
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                Picasso.with(context)
                        .load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).replaceAll("\\s", "%20"))
                        .error(R.color.light2)
                        .placeholder(R.color.light)
                        .into(holder.mImgActPic);
            } else {
                holder.mImgActPic.setBackgroundColor(ContextCompat.getColor(context, R.color.light2));
            }
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

        }
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImgActPic;
        private TextView mTxtAlbumName;

        ViewHolder(View itemView) {
            super(itemView);
            mTxtAlbumName = itemView.findViewById(R.id.txtAlbumName);
            mTxtAlbumName.setVisibility(View.GONE);
            mImgActPic = itemView.findViewById(R.id.imgAlbumPic);
            mImgActPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        context.startActivity(
                                new Intent(context, SinglePostDataActivity.class)
                                        .putExtra(AppConstants.FROM, "Single")
                                        .putExtra(AppConstants.BUNDLE, array.getJSONObject(getAdapterPosition()).toString()));
                    } catch (Exception | Error e) {
                        e.printStackTrace(); Crashlytics.logException(e);

                    }
                }
            });
        }
    }
}