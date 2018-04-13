package com.hm.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.activity.SinglePostDataActivity;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.common.MyPost;
import com.hm.application.fragments.CommentFragment;
import com.hm.application.fragments.ReplyToCommentFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

public class DisplayCommentsAdapter extends RecyclerView.Adapter<DisplayCommentsAdapter.ViewHolder> {
    private Context context;
    private JSONArray array;
    private  CommentFragment cf;

    public DisplayCommentsAdapter(Context ctx, JSONArray data, CommentFragment act) {
        context = ctx;
        array = data;
        cf = act;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_comment_small))) {
                holder.mTvCuCmt.setText(array.getJSONObject(position).getString(context.getString(R.string.str_comment_small)));
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_username_))) {
                holder.mTvCuName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_username_)));
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_time_small))) {
                holder.mTvCuTime.setText(CommonFunctions.toSetDate(array.getJSONObject(position).getString(context.getString(R.string.str_time_small))));
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_like_count))) {
                holder.mTvCuLike.setText(array.getJSONObject(position).getString(context.getString(R.string.str_like_count)) + " " + context.getString(R.string.str_like));
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_reply_count))) {
                holder.mTvCuReply.setText(array.getJSONObject(position).getString(context.getString(R.string.str_reply_count)) + " " + context.getString(R.string.str_reply));
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_profile_pic))) {
                if (array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)).toLowerCase().contains("upload")) {
                    Picasso.with(context).load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic))).placeholder(R.color.light).error(R.color.light2).into(holder.mIvCu);
                } else {
                    Picasso.with(context).load(array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic))).placeholder(R.color.light).error(R.color.light2).into(holder.mIvCu);
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mRlCuMain, mRlCuInner;
        private LinearLayout mLlCuData, mLlCuReply;
        private ImageView mIvCu;
        private TextView mTvCuName, mTvCuCmt, mTvCuTime, mTvCuLike, mTvCuReply;

        ViewHolder(View itemView) {
            super(itemView);
            mRlCuMain = itemView.findViewById(R.id.rlCuMain);
            mRlCuInner = itemView.findViewById(R.id.rlCuInner);
            mLlCuData = itemView.findViewById(R.id.llCuData);
            mLlCuReply = itemView.findViewById(R.id.llCmtReplyData);

            mIvCu = itemView.findViewById(R.id.imgCu);

            mTvCuName = itemView.findViewById(R.id.txtCuName);
            mTvCuName.setTypeface(HmFonts.getRobotoRegular(context));

            mTvCuCmt = itemView.findViewById(R.id.txtCuCmt);
            mTvCuCmt.setTypeface(HmFonts.getRobotoRegular(context));

            mTvCuTime = itemView.findViewById(R.id.txtCuTime);
            mTvCuTime.setTypeface(HmFonts.getRobotoRegular(context));

            mTvCuLike = itemView.findViewById(R.id.txtCuLike);
            mTvCuLike.setTypeface(HmFonts.getRobotoRegular(context));

            mTvCuReply = itemView.findViewById(R.id.txtCuReply);
            mTvCuReply.setTypeface(HmFonts.getRobotoRegular(context));


            mTvCuLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MyPost.toLikeComment(context, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_id)), mTvCuLike);
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                        FirebaseCrash.report(e);
                    }
                }
            });

            mTvCuReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.COMMENT_ID, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_id)));
                        bundle.putString("Data", array.getJSONObject(getAdapterPosition()).toString());
                        ReplyToCommentFragment reply = new ReplyToCommentFragment();
                        reply.setArguments(bundle);
                        ((UserInfoActivity) context).replaceMainHomePage(reply);

                    } catch (Exception | Error e) {
                        e.printStackTrace();
                        FirebaseCrash.report(e);
                    }
                }
            });

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.llCuData);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.imgCu);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)

            {
                params.addRule(RelativeLayout.END_OF, R.id.imgCu);
            }
            mLlCuReply.setLayoutParams(params);
            mLlCuReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        cf.setReply(array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_id)), array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_username_)));
                    } catch (Exception| Error e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}