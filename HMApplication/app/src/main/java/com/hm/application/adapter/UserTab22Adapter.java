package com.hm.application.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.common.MyPost;
import com.hm.application.fragments.CommentFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserTab22Adapter extends RecyclerView.Adapter<UserTab22Adapter.ViewHolder> {

    private Context context;
    private JSONArray array;

    public UserTab22Adapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @NonNull
    @Override
    public UserTab22Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.tab22_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserTab22Adapter.ViewHolder holder, int position) {
        try {
            Log.d("HmApp", " post item : " + array.getJSONObject(position));
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_username_))) {
                holder.mTxt_label.setText(array.getJSONObject(position).getString(context.getString(R.string.str_username_)));

            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_time))) {
                holder.mTxt_time_ago.setText(array.getJSONObject(position).getString(context.getString(R.string.str_time)));
                holder.mTxt_time_ago.setText(CommonFunctions.toSetDate(array.getJSONObject(position).getString(context.getString(R.string.str_time))));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_like_count))) {
                holder.mTxtNo_like.setText(array.getJSONObject(position).getString(context.getString(R.string.str_like_count)) + " " + context.getResources().getString(R.string.str_like));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_comment_count))) {
                holder.mTxtNo_comment.setText(array.getJSONObject(position).getString(context.getString(R.string.str_comment_count)) + " " + context.getString(R.string.str_comment));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_share_count))) {
                holder.mTxtNo_share.setText(array.getJSONObject(position).getString(context.getString(R.string.str_share_count)) + " " + context.getResources().getString(R.string.str_share));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                Picasso.with(context)
                        .load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).replaceAll("\\s", "%20"))
                        .into(holder.mImgActPic);
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                Picasso.with(context)
                        .load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).replaceAll("\\s", "%20"))
                        .placeholder(R.color.light)
                        .error(R.color.light)
                        .into(holder.mCircle_img);
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
        private RelativeLayout mRrMainFile;
        private ImageView mImgActPic;
        private CircleImageView mCircle_img;
        private TextView mTxt_label, mTxt_time_ago;
        private LinearLayout mLl_footer, mllNumber_file;
        private TextView mTxt_like, mTxt_comment, mTxt_share;
        private TextView mTxtNo_like, mTxtNo_comment, mTxtNo_share;


        public ViewHolder(View itemView) {
            super(itemView);
            mRrMainFile = itemView.findViewById(R.id.rr_header_file);

            mLl_footer = itemView.findViewById(R.id.ll_footer);

            mllNumber_file = itemView.findViewById(R.id.llNumber_file);

            mImgActPic = itemView.findViewById(R.id.image_single);

            mCircle_img = itemView.findViewById(R.id.circle_img);

            mTxt_label = itemView.findViewById(R.id.txt_label);
            mTxt_label.setTypeface(HmFonts.getRobotoRegular(context));

            mTxt_time_ago = itemView.findViewById(R.id.txt_time_ago);
            mTxt_time_ago.setTypeface(HmFonts.getRobotoRegular(context));

            mTxt_like = itemView.findViewById(R.id.txt_like);

            mTxt_comment = itemView.findViewById(R.id.txt_comment);

            mTxt_share = itemView.findViewById(R.id.txt_share);

            mTxtNo_like = itemView.findViewById(R.id.txtNo_like);
            mTxtNo_like.setTypeface(HmFonts.getRobotoRegular(context));

            mTxtNo_comment = itemView.findViewById(R.id.txtNo_comment);
            mTxtNo_comment.setTypeface(HmFonts.getRobotoRegular(context));

            mTxtNo_share = itemView.findViewById(R.id.txtNo_share);
            mTxtNo_like.setText("0 " + context.getResources().getString(R.string.str_like));
            mTxtNo_comment.setText("0 " + context.getString(R.string.str_comment));
            mTxtNo_share.setText("0 " + context.getResources().getString(R.string.str_share));
            mTxtNo_share.setTypeface(HmFonts.getRobotoRegular(context));

            if (User.getUser(context).getPicPath() != null) {
                Picasso.with(context)
                        .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20")).into(mCircle_img);
            }

            mTxt_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MyPost.toLikeUnlikePost(context, array.getJSONObject(getAdapterPosition()).getString("timeline_id"),  mTxt_like, mTxtNo_like);
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                    }

                }
            });

            mTxt_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString("Likes", mTxtNo_like.getText().toString().trim());
                        CommentFragment cm = new CommentFragment();
                        cm.setArguments(bundle);
                        ((MainHomeActivity) context).replacePage(cm);
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                    }
                }
            });

            mTxt_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        CommonFunctions.toShareData(context, context.getString(R.string.app_name), array.getJSONObject(getAdapterPosition()).getString("caption"), array.getJSONObject(getAdapterPosition()).getString("timeline_id"));
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
