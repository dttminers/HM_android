package com.hm.application.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.classes.Post;
import com.hm.application.common.MyPost;
import com.hm.application.fragments.ReplyToCommentFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayCommentsAdapter extends RecyclerView.Adapter<DisplayCommentsAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private JSONArray array;

    public DisplayCommentsAdapter(Context ctx, JSONArray data, FragmentActivity act) {
        context = ctx;
        array = data;
        activity = act;
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

        private RelativeLayout mRlCuMain;
        private LinearLayout mLlCuData, mLlCuReply, mllReplyComment;
        private ImageView mIvCu;
        private EditText mEdtReplyComment;
        private Button mBtnReplyComment;
        private TextView mTvCuName, mTvCuCmt, mTvCuTime, mTvCuLike, mTvCuReply;

        ViewHolder(View itemView) {
            super(itemView);
            mRlCuMain = itemView.findViewById(R.id.rrCuMain);
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

            mllReplyComment = itemView.findViewById(R.id.llCfMainReply);
            mllReplyComment.setVisibility(View.GONE);

            mEdtReplyComment = itemView.findViewById(R.id.edtCmtPostReply);
            mEdtReplyComment.setVisibility(View.GONE);

            mBtnReplyComment = itemView.findViewById(R.id.btnCmtSendReply);
            mBtnReplyComment.setVisibility(View.GONE);

            mBtnReplyComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        activity.getWindow().setSoftInputMode(16);
                        if (mEdtReplyComment.getText().toString().trim().length() > 0) {
                            MyPost.toReplyOnComment(context, array.getJSONObject(getAdapterPosition()).getString("id"), mEdtReplyComment.getText().toString().trim());
                            toAddComment(mEdtReplyComment.getText().toString().trim(), array.getJSONObject(getAdapterPosition()).getString("id"));
                            mEdtReplyComment.setText("");
                        } else {
                            CommonFunctions.toDisplayToast("Empty", context);
                        }
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                    }
                }
            });

            mTvCuLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MyPost.toLikeComment(context, array.getJSONObject(getAdapterPosition()).getString("id"), mTvCuLike);
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                    }
                }
            });

            mTvCuReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    toGetReplyData(getAdapterPosition(), mLlCuReply);
                    ((MainHomeActivity) context).replacePage(new ReplyToCommentFragment());
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
        }

        private void toGetReplyData(int position, LinearLayout mLlCuReply) {
            try {
                if (!array.getJSONObject(position).isNull(context.getString(R.string.str_reply_count))) {
                    if (array.getJSONObject(position).getInt(context.getString(R.string.str_reply_count)) > 0) {
                        Post.toDisplayReply(array.getJSONObject(position).getString(context.getString(R.string.str_id)), mLlCuReply, context);
                    } else {
                        mllReplyComment.setVisibility(View.VISIBLE);
                        mEdtReplyComment.setVisibility(View.VISIBLE);
                        mBtnReplyComment.setVisibility(View.VISIBLE);
                    }
                } else {
                    mllReplyComment.setVisibility(View.VISIBLE);
                    mEdtReplyComment.setVisibility(View.VISIBLE);
                    mBtnReplyComment.setVisibility(View.VISIBLE);
                }
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
        }

        private void toAddComment(String data, String commentId) {
            try {
                View itemView = LayoutInflater.from(context).inflate(R.layout.comment_user, null);
                if (itemView != null) {
                    CircleImageView mIvCu;
                    TextView mTvCuName, mTvCuCmt, mTvCuTime, mTvCuLike, mTvCuReply;

                    mIvCu = itemView.findViewById(R.id.imgCu);
                    Picasso.with(context).load(AppConstants.URL+User.getUser(context).getPicPath().replaceAll("\\s", "%20"))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .into(mIvCu);

                    mTvCuName = itemView.findViewById(R.id.txtCuName);
                    mTvCuName.setTypeface(HmFonts.getRobotoBold(context));
                    mTvCuName.setText(User.getUser(context).getUsername());

                    mTvCuCmt = itemView.findViewById(R.id.txtCuCmt);
                    mTvCuCmt.setTypeface(HmFonts.getRobotoRegular(context));
                    mTvCuCmt.setText(data);

                    mTvCuTime = itemView.findViewById(R.id.txtCuTime);
                    mTvCuTime.setTypeface(HmFonts.getRobotoRegular(context));

                    mTvCuLike = itemView.findViewById(R.id.txtCuLike);
                    mTvCuLike.setTypeface(HmFonts.getRobotoRegular(context));
                    mTvCuLike.setText("0 " + context.getString(R.string.str_like));

                    mTvCuReply = itemView.findViewById(R.id.txtCuReply);
                    mTvCuReply.setTypeface(HmFonts.getRobotoRegular(context));

                    mLlCuReply.addView(itemView);
                    Post.toDisplayReply(commentId, mLlCuReply, context);
                }
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
        }
    }
}