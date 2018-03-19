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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

public class TbThemeAdapter extends RecyclerView.Adapter<TbThemeAdapter.ViewHolder> {

    private Context context;
    private JSONArray array;

    public TbThemeAdapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.destination_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (!array.getJSONObject(position).isNull("title")) {
                holder.mTvName.setText(array.getJSONObject(position).getString("title"));
            }
            if (!array.getJSONObject(position).isNull("theme_image")) {
                Picasso.with(context)
                        .load(array.getJSONObject(position).getString("theme_image"))
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
        return array == null ? 0 : array.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvDest;
        private TextView mTvName;

        ViewHolder(View itemView) {
            super(itemView);

            mIvDest = itemView.findViewById(R.id.imgDest);
            mTvName = itemView.findViewById(R.id.txtName);

        }
    }
}
