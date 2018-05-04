package com.hm.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hm.application.R;
import com.hm.application.activity.SinglePostDataActivity;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.common.Notification;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
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
                Spannable text = new SpannableString(CommonFunctions.firstLetterCaps(array.getJSONObject(position).getString(context.getString(R.string.str_sender_username))));
                text.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.black)), 0, text.length(), 0);
                if (!array.getJSONObject(position).isNull(context.getString(R.string.str_title))) {
                    if (!array.getJSONObject(position).isNull(context.getString(R.string.str_comment_))) {
                        holder.mTvNfLabel.setText(new SpannableStringBuilder().append(text).append(" ").append(array.getJSONObject(position).getString(context.getString(R.string.str_title)))
                                .append(":").append(array.getJSONObject(position).getString(context.getString(R.string.str_comment_))));
                    } else if (!array.getJSONObject(position).isNull(context.getString(R.string.str_post_data))) {
                        holder.mTvNfLabel.setText(new SpannableStringBuilder().append(text).append(" ").append(array.getJSONObject(position).getString(context.getString(R.string.str_title)))
                                .append(":").append(array.getJSONObject(position).getString(context.getString(R.string.str_post_data))));
                    } else if (!array.getJSONObject(position).isNull(context.getString(R.string.str_reply).toLowerCase())) {
                        holder.mTvNfLabel.setText(new SpannableStringBuilder().append(text).append(" ").append(array.getJSONObject(position).getString(context.getString(R.string.str_title)))
                                .append(":").append(array.getJSONObject(position).getString(context.getString(R.string.str_reply).toLowerCase())));
                    } else {
                        holder.mTvNfLabel.setText(new SpannableStringBuilder().append(text).append(" ").append(array.getJSONObject(position).getString(context.getString(R.string.str_title))));
                    }
                } else {
                    holder.mTvNfLabel.setVisibility(View.GONE);
                }
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_time))) {
                holder.mTvNfTime.setText(CommonFunctions.firstLetterCaps(array.getJSONObject(position).getString(context.getString(R.string.str_time))));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_sender_profile_pic))) {
                if (array.getJSONObject(position).getString(context.getString(R.string.str_sender_profile_pic)).toLowerCase().contains("uploads")) {
                    Picasso.with(context).load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_sender_profile_pic)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                            .placeholder(R.color.light)
                            .error(R.color.light2)
                            .into(holder.mIvNfUserPic);
                } else {
                    Picasso.with(context).load(array.getJSONObject(position).getString(context.getString(R.string.str_sender_profile_pic)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                            .placeholder(R.color.light)
                            .error(R.color.light2)
                            .into(holder.mIvNfUserPic);
                }
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                if (array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).toLowerCase().contains("uploads")) {
                    Picasso.with(context).load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                            .placeholder(R.color.light)
                            .error(R.color.light2)
                            .into(holder.mIvNfPic);
                } else {
                    Picasso.with(context).load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                            .placeholder(R.color.light)
                            .error(R.color.light2)
                            .into(holder.mIvNfPic);
                }
            } else {
                holder.mIvNfPic.setVisibility(View.GONE);
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
            mTvNfTime.setTypeface(HmFonts.getRobotoRegular(context));

            mTvNfLabel = itemView.findViewById(R.id.txtNfLabel);
            mTvNfLabel.setTypeface(HmFonts.getRobotoRegular(context));

            mRlNfMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Notification.toChangeReadStatus(context, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_msg_id)));
                    } catch (Exception | Error e) {
                        e.printStackTrace();

                    }
                }
            });

            mTvNfLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        context.startActivity(new Intent(context, UserInfoActivity.class)
                                .putExtra(AppConstants.F_UID, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_sender_uid_))));
                    } catch (Exception | Error e) {
                        e.printStackTrace();

                    }
                }
            });

            mIvNfUserPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        context.startActivity(new Intent(context, UserInfoActivity.class)
                                .putExtra(AppConstants.F_UID, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_sender_uid_))));
                    } catch (Exception | Error e) {
                        e.printStackTrace();

                    }
                }
            });

            mIvNfPic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    goToSinglePost(getAdapterPosition());
                }
            });

            mTvNfTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToSinglePost(getAdapterPosition());
                }
            });
        }
    }

    private void goToSinglePost(int adapterPosition) {
        try {
            context.startActivity(
                    new Intent(context, SinglePostDataActivity.class)
                            .putExtra(AppConstants.FROM, "Single")
                            .putExtra(AppConstants.TIMELINE_ID, array.getJSONObject(adapterPosition).getString(context.getString(R.string.str_timeline_id_))));
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }
}