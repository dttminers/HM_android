package com.hm.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.hm.application.fragments.TimelineLikeListFragment;
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
                holder.mTvPostTitle.setText(CommonFunctions.firstLetterCaps(array.getJSONObject(position).getString(context.getString(R.string.str_username_))));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_time))) {
//                holder.mTvPostTime.setText(array.getJSONObject(position).getString(context.getString(R.string.str_time)));
                holder.mTvPostTime.setText(CommonFunctions.toSetDate(array.getJSONObject(position).getString(context.getString(R.string.str_time))));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_caption))) {
                holder.mTvPostData.setText(array.getJSONObject(position).getString(context.getString(R.string.str_caption)));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_like_count))) {
                holder.mTvLikeCount.setText(array.getJSONObject(position).getString(context.getString(R.string.str_like_count)) + " " + context.getResources().getString(R.string.str_like));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_comment_count))) {
                holder.mTvCommentCount.setText(array.getJSONObject(position).getString(context.getString(R.string.str_comment_count)) + " " + context.getString(R.string.str_comment));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_share_count))) {
                holder.mTvShareCount.setText(array.getJSONObject(position).getString(context.getString(R.string.str_share_count)) + " " + context.getResources().getString(R.string.str_share));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                Picasso.with(context)
                        .load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).replaceAll("\\s", "%20"))
                        .placeholder(R.color.light)
                        .error(R.color.light)
                        .into(holder.mImgActPic);
            }
            if (User.getUser(context).getPicPath() != null) {
                Picasso.with(context)
                        .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20"))
                        .placeholder(R.color.light)
                        .error(R.color.light)
                        .into(holder.mCivPostPic);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRlMainHeaderFile, mRlNumberFile;
        private ImageView mImgActPic;
        private CircleImageView mCivPostPic;
        private TextView mTvPostTitle, mTvPostTime, mTvPostData;
        private LinearLayout mLlFooter, mLlFooterFile, mllMain;
        private TextView mTvLikeLbl, mTvCommentLbl, mTvShareLbl;
        private TextView mTvLikeCount, mTvCommentCount, mTvShareCount;


        public ViewHolder(View itemView) {
            super(itemView);
            mRlMainHeaderFile = itemView.findViewById(R.id.rr_header_file);

            mllMain = itemView.findViewById(R.id.llTab22Main);

            mLlFooter = itemView.findViewById(R.id.ll_footer);

            mLlFooterFile = itemView.findViewById(R.id.llNumber_file);
            mRlNumberFile = itemView.findViewById(R.id.rlNumber_file);

            mImgActPic = itemView.findViewById(R.id.image_single);

            mCivPostPic = itemView.findViewById(R.id.circle_img);

            mTvPostTitle = itemView.findViewById(R.id.txt_label);
            mTvPostTitle.setTypeface(HmFonts.getRobotoRegular(context));

            mTvPostTime = itemView.findViewById(R.id.txt_time_ago);
            mTvPostTime.setTypeface(HmFonts.getRobotoRegular(context));
            mTvPostTime.setVisibility(View.VISIBLE);

            mTvPostData = itemView.findViewById(R.id.txtPostData22);
            mTvPostData.setTypeface(HmFonts.getRobotoRegular(context));

            mTvLikeLbl = itemView.findViewById(R.id.txt_like);

            mTvCommentLbl = itemView.findViewById(R.id.txt_comment);

            mTvShareLbl = itemView.findViewById(R.id.txt_share);

            mTvLikeCount = itemView.findViewById(R.id.txtNo_like);
            mTvLikeCount.setTypeface(HmFonts.getRobotoRegular(context));

            mTvCommentCount = itemView.findViewById(R.id.txtNo_comment);
            mTvCommentCount.setTypeface(HmFonts.getRobotoRegular(context));

            mTvShareCount = itemView.findViewById(R.id.txtNo_share);
            mTvLikeCount.setText("0 " + context.getResources().getString(R.string.str_like));
            mTvCommentCount.setText("0 " + context.getString(R.string.str_comment));
            mTvShareCount.setText("0 " + context.getResources().getString(R.string.str_share));
            mTvShareCount.setTypeface(HmFonts.getRobotoRegular(context));

            if (User.getUser(context).getPicPath() != null) {
                Picasso.with(context)
                        .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20")).into(mCivPostPic);
            }

//            if (!array.getJSONObject().isNull(context.getString(R.string.str_is_liked))) {
//                if (array.getJSONObject(context.getString(R.string.str_is_liked)).toLowerCase().equals(context.getString(R.string.str_true))) {
//                    mTvLikeLbl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
//                } else {
//                    mTvLikeLbl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
//                }
//            } else {
//                mTvLikeLbl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
//            }

            mTvLikeCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.TIMELINE_ID, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_timeline_id_)));
                        TimelineLikeListFragment time = new TimelineLikeListFragment();
                        time.setArguments(bundle);
                        ((UserInfoActivity) context).replaceMainHomePage(time);
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                        FirebaseCrash.report(e);
                    }
                }
            });

            mTvLikeLbl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MyPost.toLikeUnlikePost(context, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_timeline_id_)), null, null, mTvLikeLbl, mTvLikeCount);
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                        FirebaseCrash.report(e);
                    }

                }
            });

            mTvCommentLbl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.TIMELINE_ID, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_timeline_id_)));
                        CommentFragment cm = new CommentFragment();
                        cm.setArguments(bundle);
                        ((UserInfoActivity) context).replaceMainHomePage(cm);
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                        FirebaseCrash.report(e);
                    }
                }
            });

            mTvShareLbl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        CommonFunctions.toShareData(context, context.getString(R.string.app_name), array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_caption)),
                                array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_timeline_id_)), null);
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                        FirebaseCrash.report(e);
                    }
                }
            });

            mllMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    try {
//                        Bundle bundle = new Bundle();
//                        bundle.putString(AppConstants.BUNDLE, array.getJSONObject(getAdapterPosition()).toString());
//                        bundle.putString(AppConstants.FROM, "Single");
//                        SinglePostDataFragment singlePostDataFragment = new SinglePostDataFragment();
//                        singlePostDataFragment.setArguments(bundle);
//                        ((UserInfoActivity) context).replaceMainHomePage(singlePostDataFragment);
//                    } catch (Exception | Error e) {
//                        e.printStackTrace();
//                    }
                    try {
                        context.startActivity(
                                new Intent(context, SinglePostDataActivity.class)
                                        .putExtra(AppConstants.FROM, "Single")
                                        .putExtra(AppConstants.BUNDLE, array.getJSONObject(getAdapterPosition()).toString()));
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                        FirebaseCrash.report(e);
                    }
                }
            });
        }
    }
}
