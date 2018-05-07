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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.activity.SinglePostDataActivity;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

public class UserTab23Adapter extends RecyclerView.Adapter<UserTab23Adapter.ViewHolder> {

    private Context context;
    private JSONArray array;

    public UserTab23Adapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @Override
    public UserTab23Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new UserTab23Adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.album_layout, parent, false));
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.place_nb_layout_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserTab23Adapter.ViewHolder holder, int position) {
        try {
            if (position != array.length()) {
                if (!array.getJSONObject(position).isNull(context.getString(R.string.str_caption))) {
                    holder.mTxtAlbumName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_caption)));
                }

                if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image))) {
                    Picasso.with(context)
                            .load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_image)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .into(holder.mImgAlbumPic);
                } else {
                    holder.mImgAlbumPic.setBackgroundColor(ContextCompat.getColor(context, R.color.light2));
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

        private ImageView mImgAlbumPic;
        //        private LinearLayout mLlAlbumPic;
        private TextView mTxtAlbumName;
        private RelativeLayout mRlAlbum;

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

                    }
                }
            });

        }
    }
}

/*
*  mTxtAlbumName = itemView.findViewById(R.id.txtAlbumName);
            mTxtAlbumName.setVisibility(View.VISIBLE);
            mImgAlbumPic = itemView.findViewById(R.id.imgAlbumPic);
            mLlAlbumPic = itemView.findViewById(R.id.llAlbum);
* */




