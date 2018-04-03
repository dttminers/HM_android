package com.hm.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.activity.PackageDetailActivity;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class PackageSectionRecyclerViewAdapter extends RecyclerView.Adapter<PackageSectionRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private JSONArray data;
    private String fromTo;


    public PackageSectionRecyclerViewAdapter(Context ctx, JSONArray array, String from) {
        context = ctx;
        data = array;
        fromTo = from;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.place_info_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            if (!data.getJSONObject(position).isNull(context.getString(R.string.str_title))) {
                holder.mTxtTravelTitle.setText(data.getJSONObject(position).getString(context.getString(R.string.str_title)));
            }

            if (!data.getJSONObject(position).isNull(context.getString(R.string.str_price))) {
                holder.mTxtTravelPrice.setText(context.getString(R.string.str_lbl_rs) + " " + data.getJSONObject(position).getString(context.getString(R.string.str_price)));
            }
            if (!data.getJSONObject(position).isNull(context.getString(R.string.str_destination_))) {
                holder.mTxtTravelLoc.setText(data.getJSONObject(position).getString(context.getString(R.string.str_destination_)));
            }
            if (!data.getJSONObject(position).isNull(context.getString(R.string.str_package_img_url))) {
                Log.d("hmapp ", " image 1 " + position + " : " + data.getJSONObject(position).getString(context.getString(R.string.str_package_img_url)).trim().replaceAll("\\s", "%20"));
                Picasso.with(context)
                        .load(data.getJSONObject(position).getString(context.getString(R.string.str_package_img_url)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                        .error(R.color.light2)
                        .placeholder(R.color.light)
                        .resize(300, 250)
                        .into(holder.mIvTravelPic);

            } else if (!data.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                Log.d("hmapp ", " image 2  " + position + " : " + data.getJSONObject(position).getString(context.getString(R.string.str_image_url)).trim().replaceAll("\\s", "%20"));

                Picasso.with(context)
                        .load(data.getJSONObject(position).getString(context.getString(R.string.str_image_url)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                        .error(R.color.light2)
                        .placeholder(R.color.light)
                        .resize(300, 250)
                        .into(holder.mIvTravelPic);
            }

            holder.mRlTravel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PackageSectionRecyclerViewAdapter.this.context, PackageDetailActivity.class);
                    intent.putExtra(AppConstants.DETAIL_TAG, fromTo);
                    PackageSectionRecyclerViewAdapter.this.context.startActivity(intent);

                }
            });
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.length() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRlTravel1, mRlTravel2, mRlTravel3;
        private ImageView mIvTravelPic, mIvAddToBL;
        private TextView mTxtTravelLoc, mTxtTravelPrice, mTxtTravelTitle;
        private RatingBar mRbTravel;

        public ViewHolder(View item) {
            super(item);

            mRlTravel1 = item.findViewById(R.id.rlTravel1);
            mRlTravel2 = item.findViewById(R.id.rlTravel2);
            mRlTravel3 = item.findViewById(R.id.rlTravel3);

            mIvTravelPic = item.findViewById(R.id.imgTravel);
            mIvAddToBL = item.findViewById(R.id.ivAddToBL);

            mTxtTravelLoc = item.findViewById(R.id.txtPlace);
            mTxtTravelLoc.setTypeface(HmFonts.getRobotoRegular(context));

            mTxtTravelPrice = item.findViewById(R.id.txtPrice);
            mTxtTravelPrice.setTypeface(HmFonts.getRobotoRegular(context));

            mTxtTravelTitle = item.findViewById(R.id.txtTagName);
            mTxtTravelTitle.setTypeface(HmFonts.getRobotoRegular(context));

            mRbTravel = item.findViewById(R.id.rating_br);

            LayerDrawable star = (LayerDrawable) mRbTravel.getProgressDrawable();
            star.getDrawable(2).setColorFilter(ResourcesCompat.getColor(context.getResources(), R.color.light_orange2, null), PorterDuff.Mode.SRC_ATOP);
            star.getDrawable(0).setColorFilter(ResourcesCompat.getColor(context.getResources(), R.color.light2, null), PorterDuff.Mode.SRC_ATOP);
            star.getDrawable(1).setColorFilter(ResourcesCompat.getColor(context.getResources(), R.color.light_orange2, null), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
