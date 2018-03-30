package com.hm.application.adapter;

import android.content.Context;
import android.os.Bundle;
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
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.fragments.SinglePostDataFragment;
import com.hm.application.fragments.UserTab24Fragment;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

public class UserTab24Adapter extends RecyclerView.Adapter<UserTab24Adapter.ViewHolder> {

    private Context context;
    private JSONArray array;
    private UserTab24Fragment u;

    public UserTab24Adapter(Context ctx, JSONArray data, UserTab24Fragment userTab24Fragment) {
        context = ctx;
        array = data;
        u = userTab24Fragment;
    }

    @NonNull
    @Override
    public UserTab24Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.album_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserTab24Adapter.ViewHolder holder, int position) {
        try {
            if (position != array.length()) {
                if (!array.getJSONObject(position).isNull(context.getString(R.string.str_caption))) {
                    holder.mTxtAlbumName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_caption)));
                }

                if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                    Picasso.with(context)
                            .load(array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                            .into(holder.mImgAlbumPic);
                } else {
                    holder.mImgAlbumPic.setBackgroundColor(ContextCompat.getColor(context, R.color.light2));
                }
            } else {
                holder.mImgAlbumPic.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_add_circle_outline_black_24dp));
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.length() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLlAlbum;
        private ImageView mImgAlbumPic;
        private TextView mTxtAlbumName;

        ViewHolder(View itemView) {
            super(itemView);

            mLlAlbum = itemView.findViewById(R.id.llAlbum);
            mImgAlbumPic = itemView.findViewById(R.id.imgAlbumPic);
            mTxtAlbumName = itemView.findViewById(R.id.txtAlbumName);
            mTxtAlbumName.setTypeface(HmFonts.getRobotoBlack(context));
            mTxtAlbumName.setVisibility(View.VISIBLE);
            mLlAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (getAdapterPosition() != array.length()) {
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConstants.BUNDLE, array.getJSONObject(getAdapterPosition()).toString());
                            bundle.putString(AppConstants.FROM, "TAB4");
                            SinglePostDataFragment singlePostDataFragment = new SinglePostDataFragment();
                            singlePostDataFragment.setArguments(bundle);
                            ((MainHomeActivity) context).replacePage(singlePostDataFragment);
                        } else {
                            u.multiSelectImage();
                        }
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}