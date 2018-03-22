package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

/**
 * Created by SIS-004 on 21-03-2018.
 */

public class UserTab24Adapter extends RecyclerView.Adapter<UserTab24Adapter.ViewHolder>{

    private Context context;
    private JSONArray array;

    public UserTab24Adapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }
    @NonNull
    @Override
    public UserTab24Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.album_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserTab24Adapter.ViewHolder holder, int position) {
        try {
            if (!array.getJSONObject(position).isNull("image_url")) {
                Picasso.with(context)
                        .load(AppConstants.URL+array.getJSONObject(position).getString("image_url").replaceAll("\\s", "%20"))
                        .into(holder.mImgActPic);
            } else {
                holder.mImgActPic.setBackgroundColor(ContextCompat.getColor(context, R.color.light2));
            }
            if(!array.getJSONObject(position).isNull("caption")){
                holder.mtxtAlbumName.setText(array.getJSONObject(position).getString("caption"));
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return array== null ? 0: array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mllAlbum;
        private ImageView mImgActPic;
        private TextView mtxtAlbumName;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgActPic = itemView.findViewById(R.id.imgAlbumPic);
            mtxtAlbumName = itemView.findViewById(R.id.txtAlbumName);
            mllAlbum = itemView.findViewById(R.id.llAlbum);
        }
    }
}
