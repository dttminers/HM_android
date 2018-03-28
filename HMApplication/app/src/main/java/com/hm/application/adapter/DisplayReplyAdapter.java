package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.hm.application.classes.Post;
import com.hm.application.common.Comments;
import com.hm.application.common.MyPost;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class DisplayReplyAdapter extends RecyclerView.Adapter<DisplayReplyAdapter.ViewHolder> {
    private Context context;
    private JSONArray array;

    public DisplayReplyAdapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            //[{"id":"12","comment_id":"12","uid":"15","reply":"thanks","timestamp":""}]
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_reply_small))) {
                holder.mTvCuCmt.setText(array.getJSONObject(position).getString(context.getString(R.string.str_reply_small)));
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_username_))) {
                holder.mTvCuName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_username_)));
            }

//            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_timestamp_small))) {
//                holder.mTvCuTime.setText(array.getJSONObject(position).getString(context.getString(R.string.str_timestamp_small)));
//            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_like_count))) {
                holder.mTvCuLike.setText(array.getJSONObject(position).getString(context.getString(R.string.str_like_count)) + " " + context.getString(R.string.str_like));
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_reply_count))) {
                holder.mTvCuReply.setText(array.getJSONObject(position).getString(context.getString(R.string.str_reply_count)) + " " + context.getString(R.string.str_reply));
            } else {
                holder.mTvCuReply.setVisibility(View.GONE);
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_profile_pic))) {
                if (array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)).toLowerCase().contains(context.getString(R.string.str_upload))) {
                    Picasso.with(context).load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)))
                            .error(R.color.light2).placeholder(R.color.light).into(holder.mIvCu);
                } else {
                    Picasso.with(context).load(
                            array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)))
                            .error(R.color.light2).placeholder(R.color.light).into(holder.mIvCu);
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
            mTvCuReply.setVisibility(View.GONE);

            mllReplyComment = itemView.findViewById(R.id.llCfMainReply);
            mllReplyComment.setVisibility(View.VISIBLE);

            mEdtReplyComment = itemView.findViewById(R.id.edtCmtPostReply);
            mEdtReplyComment.setVisibility(View.VISIBLE);

            mBtnReplyComment = itemView.findViewById(R.id.btnCmtSendReply);
            mBtnReplyComment.setVisibility(View.VISIBLE);

            mTvCuReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (!array.getJSONObject(getAdapterPosition()).isNull(context.getString(R.string.str_reply_count))) {
                            if (array.getJSONObject(getAdapterPosition()).getInt(context.getString(R.string.str_reply_count)) > 0) {
                                Post.toDisplayReply(array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_id)), mLlCuReply, context);
                            }
                        }
                        Post.toDisplayReply(array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_id)), mLlCuReply, context);
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                    }
                }
            });

            mTvCuLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Comments.toLikeReply(context, array.getJSONObject(getAdapterPosition()).getString("id"), mTvCuLike);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            mBtnReplyComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        if (mEdtReplyComment.getText().toString().trim().length() > 0) {

//                    if (reply) {
                            MyPost.toReplyOnComment(context, array.getJSONObject(getAdapterPosition()).getString("id"), mEdtReplyComment.getText().toString().trim());
//                        toAddComment(true, mEdtCmt.getText().toString().trim());
//                    } else {
//                MyPost.toCommentOnPost(getContext(), timelineId, mEdtCmt.getText().toString().trim(), mLlAddCmt);
//                toAddComment(false, mEdtCmt.getText().toString().trim());
//                mEdtCmt.setText("");
//                    }
                        } else {
                            CommonFunctions.toDisplayToast("Empty", context);
                        }
                    } catch (Exception | Error e) {
                       e.printStackTrace();
                    }
                }
            });
        }
    }
}