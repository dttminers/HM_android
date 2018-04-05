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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.theme_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_title))) {
                holder.mTvName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_title)));
            }
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_theme_image))) {
                Picasso.with(context)
                        .load(array.getJSONObject(position).getString(context.getString(R.string.str_theme_image)))
                        .into(holder.mIvTheme);
            } else {
                holder.mIvTheme.setBackgroundColor(ContextCompat.getColor(context, R.color.light2));
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

        private ImageView mIvTheme;
        private TextView mTvName, mTvData;

        ViewHolder(View itemView) {
            super(itemView);
            mIvTheme = itemView.findViewById(R.id.imgThemePic);

            mTvName = itemView.findViewById(R.id.txtThemeName);
            mTvName.setTypeface(HmFonts.getRobotoBold(context));
            mTvName.setVisibility(View.VISIBLE);

            mTvData = itemView.findViewById(R.id.txtThemeNameData);
            mTvData.setTypeface(HmFonts.getRobotoBold(context));
        }
    }
}