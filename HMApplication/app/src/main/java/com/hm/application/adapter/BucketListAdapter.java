package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.ViewHolder> {

    private Context context;
    private JSONArray array;

    public BucketListAdapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @Override
    public BucketListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BucketListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.destination_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BucketListAdapter.ViewHolder holder, int position) {
        try {
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_title))) {
                holder.mTvName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_title)));
            } else if (!array.getJSONObject(position).isNull(context.getString(R.string.str_state))) {
                holder.mTvName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_state)));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_package_img_url))) {
                Picasso.with(context)
                        .load(array.getJSONObject(position).getString(context.getString(R.string.str_package_img_url)))
                        .error(R.color.light2)
                        .placeholder(R.color.light)
                        .into(holder.mIvDest);
            } else {
                holder.mIvDest.setBackgroundColor(ContextCompat.getColor(context, R.color.light2));
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvDest;
        private TextView mTvName;

        public ViewHolder(View itemView) {
            super(itemView);

            mIvDest = itemView.findViewById(R.id.imgDest);
            mTvName = itemView.findViewById(R.id.txtName);
            mTvName.setTypeface(HmFonts.getRobotoBold(context));
        }
    }
}

