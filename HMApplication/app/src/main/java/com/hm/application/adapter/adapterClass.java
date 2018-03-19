package com.hm.application.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hm.application.R;

import org.json.JSONArray;

public class adapterClass extends RecyclerView.Adapter<adapterClass.ViewHolder> {

    private Context context;
    private JSONArray data;

    public adapterClass(Context context, JSONArray data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.destination_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.mtxtName.setText(data.getJSONObject(position).getString("Name"));
            Glide.with(context).load("http://vnoi.in/hm/uploads/destination/1logo.jpg" +""+ data.getJSONObject(position).getString("image_url")).into(holder.mimgDest);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mimgDest;
        TextView mtxtName;

        public ViewHolder(View itemView) {
            super(itemView);

            mimgDest = itemView.findViewById(R.id.imgDest);
            mtxtName = itemView.findViewById(R.id.txtName);

        }
    }
}
