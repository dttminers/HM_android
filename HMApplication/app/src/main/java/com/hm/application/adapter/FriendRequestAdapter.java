package com.hm.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.common.MyFriendRequest;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {
    private Context context;
    private JSONArray array;

    public FriendRequestAdapter(Context ctx, JSONArray data) {
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
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_name))) {
                holder.mTvName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_name)));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_mutual_friend_count))) {
                holder.mTvData.setText(array.getJSONObject(position).getString(context.getString(R.string.str_mutual_friend_count)) + " " + context.getString(R.string.str_common_friends));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_profile_pic))) {
                if (array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)).toLowerCase().contains("upload")) {
                    Picasso.with(context)
                            .load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_profile_pic)).replaceAll("\\s", "%20"))
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
            e.printStackTrace(); Crashlytics.logException(e);

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
                mBtnConfirm.setTypeface(HmFonts.getRobotoRegular(context));

                mBtnIgnore = itemView.findViewById(R.id.btnFrIgnore);
                mBtnIgnore.setTypeface(HmFonts.getRobotoRegular(context));

                mTvName = itemView.findViewById(R.id.txt_friend_name);
                mTvName.setTypeface(HmFonts.getRobotoRegular(context));

                mTvData = itemView.findViewById(R.id.txt_friend_data);
                mTvData.setTypeface(HmFonts.getRobotoRegular(context));

                mRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            context.startActivity(new Intent(context, UserInfoActivity.class).putExtra(AppConstants.F_UID, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_uid))));
                        } catch (Exception | Error e) {
                            e.printStackTrace(); Crashlytics.logException(e);

                        }
                    }
                });

                mIvProfilePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            context.startActivity(new Intent(context, UserInfoActivity.class).putExtra(AppConstants.F_UID, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_uid))));
                        } catch (Exception | Error e) {
                            e.printStackTrace(); Crashlytics.logException(e);

                        }
                    }
                });

                mBtnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mBtnConfirm.setEnabled(false);
                            MyFriendRequest.toAcceptFriendRequest(context, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_uid)), mBtnConfirm, mBtnIgnore);
                        } catch (Exception | Error e) {
                            e.printStackTrace(); Crashlytics.logException(e);

                        }
                    }
                });

                mBtnIgnore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mBtnIgnore.setEnabled(false);
                            MyFriendRequest.toDeleteFollowFriendRequest(context, array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_uid)), mBtnConfirm, mBtnIgnore);
                        } catch (Exception | Error e) {
                            e.printStackTrace(); Crashlytics.logException(e);

                        }
                    }
                });
            } catch (Exception | Error e) {
                e.printStackTrace(); Crashlytics.logException(e);

            }
        }
    }
}