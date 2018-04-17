package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.fragments.CommentFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private JSONArray array;

    public NotificationAdapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.notificationlayout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        try {
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_sender_username))) {
                holder.mTvNfLabel.setText(CommonFunctions.firstLetterCaps(array.getJSONObject(position).getString(context.getString(R.string.str_sender_username))+" "+array.getJSONObject(position).getString(context.getString(R.string.str_title))));
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_comment_))) {
                holder.mTvNfTime.setText(CommonFunctions.firstLetterCaps(array.getJSONObject(position).getString(context.getString(R.string.str_comment_))));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_sender_profile_pic))) {
                if (array.getJSONObject(position).getString(context.getString(R.string.str_sender_profile_pic)).toLowerCase().contains("uploads")) {
                    Picasso.with(context).load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_sender_profile_pic))).placeholder(R.color.light).error(R.color.light2).into(holder.mIvNfUserPic);
                } else {
                    Picasso.with(context).load(array.getJSONObject(position).getString(context.getString(R.string.str_sender_profile_pic))).placeholder(R.color.light).error(R.color.light2).into(holder.mIvNfUserPic);
                }
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                if (array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).toLowerCase().contains("uploads")) {
                    Picasso.with(context).load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_image_url))).placeholder(R.color.light).error(R.color.light2).into(holder.mIvNfPic);
                } else {
                    Picasso.with(context).load(array.getJSONObject(position).getString(context.getString(R.string.str_image_url))).placeholder(R.color.light).error(R.color.light2).into(holder.mIvNfPic);
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mRlNfMain;
        ImageView mIvNfUserPic, mIvNfPic;
        TextView mTvNfTime, mTvNfLabel;

        public ViewHolder(View itemView) {
            super(itemView);

            mRlNfMain = itemView.findViewById(R.id.rlNfMain);
            mIvNfUserPic = itemView.findViewById(R.id.imgNfUserPic);
            mIvNfPic = itemView.findViewById(R.id.imgNfPic);
            mTvNfTime = itemView.findViewById(R.id.txtNfTime);
            mTvNfLabel = itemView.findViewById(R.id.txtNfLabel);
        }
    }
}
