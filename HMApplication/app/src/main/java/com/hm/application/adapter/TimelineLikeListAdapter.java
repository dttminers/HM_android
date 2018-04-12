package com.hm.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.common.MyFriendRequest;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineLikeListAdapter extends RecyclerView.Adapter<TimelineLikeListAdapter.ViewHolder> {

    private Context context;
    private JSONArray array;

    public TimelineLikeListAdapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.friend_request_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_username_small))) {
                holder.mTvName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_username_small)));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_full_name_small))) {
                holder.mTvData.setText(array.getJSONObject(position).getString(context.getString(R.string.str_full_name_small)));
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_uid_))) {
                if (!array.getJSONObject(position).getString(context.getString(R.string.str_uid_)).equals(User.getUser(context).getUid())) {
                    if (!array.getJSONObject(position).isNull(context.getString(R.string.str_following_small))) {
                        if (array.getJSONObject(position).getInt(context.getString(R.string.str_following_small)) == 1) {
                            holder.mBtnIgnore.setText(CommonFunctions.firstLetterCaps(context.getString(R.string.str_following_small)));
                        } else {
                            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_requested))) {
                                if (array.getJSONObject(position).getInt(context.getString(R.string.str_requested)) == 1) {
                                    holder.mBtnIgnore.setText(context.getString(R.string.str_requested));
                                }
                            } else {
                                holder.mBtnIgnore.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        holder.mBtnIgnore.setVisibility(View.GONE);
                    }
                } else {
                    holder.mBtnIgnore.setVisibility(View.GONE);
                }
            } else {
                holder.mBtnIgnore.setVisibility(View.GONE);
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_profile_pic))) {
                if (array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)).toLowerCase().contains("upload")) {
                    Picasso.with(context)
                            .load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)).replaceAll("\\s", "%20"))
                            .placeholder(R.color.light)
                            .error(R.color.light)
                            .into(holder.mIvProfilePic);
                } else if (array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)).toLowerCase().contains("https")) {
                    Picasso.with(context)
                            .load(array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)).replaceAll("\\s", "%20"))
                            .placeholder(R.color.light)
                            .error(R.color.light)
                            .into(holder.mIvProfilePic);
                } else {
                    Picasso.with(context)
                            .load(array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)).replaceAll("\\s", "%20"))
                            .placeholder(R.color.light)
                            .error(R.color.light)
                            .into(holder.mIvProfilePic);
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
        private CircleImageView mIvProfilePic;
        private Button mBtnIgnore, mBtnConfirm;
        private TextView mTvName, mTvData;
        private RelativeLayout mRl;

        ViewHolder(View itemView) {
            super(itemView);
            try {
                mRl = itemView.findViewById(R.id.rr_friend_req);
                mIvProfilePic = itemView.findViewById(R.id.imgFriendPic);

                mBtnConfirm = itemView.findViewById(R.id.btnFrConfirm);
                mBtnConfirm.setTypeface(HmFonts.getRobotoBold(context));
                mBtnConfirm.setVisibility(View.GONE);

                mBtnIgnore = itemView.findViewById(R.id.btnFrIgnore);
                mBtnIgnore.setText(R.string.str_follow);
                mBtnIgnore.setTypeface(HmFonts.getRobotoBold(context));

                mTvName = itemView.findViewById(R.id.txt_friend_name);
                mTvName.setTypeface(HmFonts.getRobotoBold(context));

                mTvData = itemView.findViewById(R.id.txt_friend_data);
                mTvData.setTypeface(HmFonts.getRobotoRegular(context));

                mTvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            context.startActivity(new Intent(context, UserInfoActivity.class).putExtra(AppConstants.F_UID, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_uid))));
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });

                mBtnIgnore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mBtnIgnore.setEnabled(false);
                            if (mBtnIgnore.getText().toString().trim().toLowerCase().equals(context.getString(R.string.str_requested).toLowerCase())) {
                                MyFriendRequest.toDeleteFollowFriendRequest(context, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_uid)), mBtnConfirm, mBtnIgnore);
                            } else if (mBtnIgnore.getText().toString().trim().toLowerCase().equals(context.getString(R.string.str_following_small).toLowerCase())) {
                                MyFriendRequest.toUnFriendRequest(context, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_uid)), mBtnIgnore);
                            } else {
                                MyFriendRequest.toFollowFriendRequest(context, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_uid)), mBtnIgnore);
                            }
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
        }
    }
}

