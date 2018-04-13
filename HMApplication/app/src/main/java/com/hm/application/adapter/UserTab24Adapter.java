package com.hm.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import com.hm.application.fragments.UserTab24Fragment;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

public class UserTab24Adapter extends RecyclerView.Adapter<UserTab24Adapter.ViewHolder> {

    private Context context;
    private JSONArray array;
    private UserTab24Fragment u;

    public UserTab24Adapter(Context ctx, JSONArray data, UserTab24Fragment userTab24Fragment) {
        context = ctx;
        array = data;
        u = userTab24Fragment;
    }

    @NonNull
    @Override
    public UserTab24Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.album_layout, parent, false));
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.place_nb_layout_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserTab24Adapter.ViewHolder holder, int position) {
        try {
//            if (position != array.length()) {
                if (!array.getJSONObject(position).isNull(context.getString(R.string.str_caption))) {
                    holder.mTxtAlbumName.setText(CommonFunctions.firstLetterCaps(array.getJSONObject(position).getString(context.getString(R.string.str_caption))));
                }

                if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                    if (array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).toLowerCase().contains(context.getString(R.string.str_upload))) {
                        Picasso.with(context)
                                .load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                                .into(holder.mImgAlbumPic);
                    } else {
                        Picasso.with(context)
                                .load(array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                                .into(holder.mImgAlbumPic);
                    }
                } else {
                    holder.mImgAlbumPic.setBackgroundColor(ContextCompat.getColor(context, R.color.light2));
                }
//            } else {
//                holder.mImgAlbumPic.setBackgroundColor(ContextCompat.getColor(context, R.color.light));
//                holder.mImgAlbumPic.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_add_circle_outline_black_24dp));
//                holder.mTxtAlbumName.setVisibility(View.GONE);
//            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.length() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLlAlbum;
        private RelativeLayout mRlAlbum;
        private ImageView mImgAlbumPic;
        private TextView mTxtAlbumName;

        ViewHolder(View itemView) {
            super(itemView);

            mRlAlbum = itemView.findViewById(R.id.rl22);
            mImgAlbumPic = itemView.findViewById(R.id.img22);
            mTxtAlbumName = itemView.findViewById(R.id.txt22);
            mTxtAlbumName.setTypeface(HmFonts.getRobotoRegular(context));
            mTxtAlbumName.setVisibility(View.VISIBLE);
            mRlAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    try {
//                        if (getAdapterPosition() != array.length()) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString(AppConstants.BUNDLE, array.getJSONObject(getAdapterPosition()).toString());
//                            bundle.putString(AppConstants.FROM, "Album");
//                            SinglePostDataFragment singlePostDataFragment = new SinglePostDataFragment();
//                            singlePostDataFragment.setArguments(bundle);
//                            ((UserInfoActivity) context).replaceMainHomePage(singlePostDataFragment);
//                        } else {
//                            u.multiSelectImage();
//                        }
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

/*
*  mLlAlbum = itemView.findViewById(R.id.llAlbum);
            mImgAlbumPic = itemView.findViewById(R.id.imgAlbumPic);
            mTxtAlbumName = itemView.findViewById(R.id.txtAlbumName);
            mTxtAlbumName.setTypeface(HmFonts.getRobotoRegular(context));
            mTxtAlbumName.setVisibility(View.VISIBLE);
* */