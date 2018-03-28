package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.common.MyFriendRequest;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFollowingListAdapter extends RecyclerView.Adapter<com.hm.application.adapter.UserFollowingListAdapter.ViewHolder> {
    private Context context;
    private JSONArray array;

    public UserFollowingListAdapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @NonNull
    @Override
    public com.hm.application.adapter.UserFollowingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new com.hm.application.adapter.UserFollowingListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.friend_request_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull com.hm.application.adapter.UserFollowingListAdapter.ViewHolder holder, int position) {
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
                mBtnIgnore.setText(R.string.str_UnFollow);
                mBtnIgnore.setTypeface(HmFonts.getRobotoBold(context));

                mTvName = itemView.findViewById(R.id.txt_friend_name);
                mTvName.setTypeface(HmFonts.getRobotoBold(context));

                mTvData = itemView.findViewById(R.id.txt_friend_data);
                mTvData.setTypeface(HmFonts.getRobotoRegular(context));

                mBtnIgnore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MyFriendRequest.toUnFriendRequest(context, array.getJSONObject(getAdapterPosition()).getString("uid"), mBtnIgnore);
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