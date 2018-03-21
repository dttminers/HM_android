package com.hm.application.adapter;

import android.content.Context;
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

import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.zip.Inflater;

/**
 * Created by SIS-004 on 21-03-2018.
 */

public class UserTab22Adapter extends RecyclerView.Adapter<UserTab22Adapter.ViewHolder> {

    private Context context;
    private JSONArray array;

    public UserTab22Adapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }
    @NonNull
    @Override
    public UserTab22Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.tab22_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserTab22Adapter.ViewHolder holder, int position) {
        try {
            if (!array.getJSONObject(position).isNull("image_url")){
                Picasso.with(context)
                        .load(AppConstants.URL+array.getJSONObject(position).getString("image_url").replaceAll("\\s", "%20"))
                        .into(holder.mImgActPic);
            }
            if (!array.getJSONObject(position).isNull("image_url")) {
                Picasso.with(context)
                        .load(AppConstants.URL + array.getJSONObject(position).getString("image_url").replaceAll("\\s", "%20"))
                        .placeholder(R.color.light)
                        .error(R.color.light)
                        .into(holder.mcircle_img);
            }
            if (!array.getJSONObject(position).isNull("caption")){
                holder.mtxt_label.setText(array.getJSONObject(position).getString("caption"));
            }
            if (!array.getJSONObject(position).isNull("like_count")){
                holder.mtxtNo_like.setText(array.getJSONObject(position).getString("like_count")+" "+context.getResources().getString(R.string.str_like));
            }
            if(!array.getJSONObject(position).isNull("comment_count")){
                holder.mtxt_comment.setText(array.getJSONObject(position).getString("comment_count")+" "+context.getResources().getString(R.string.str_comment));
            }
            if (!array.getJSONObject(position).isNull("share_counr")){
                holder.mtxt_share.setText(array.getJSONObject(position).getString("share_counr"));
            }
        }catch (Exception|Error e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return array==null?0:array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mrr_header_file;
        private ImageView mImgActPic,mcircle_img;
        private TextView mtxt_label,mtxt_time_ago;
        private LinearLayout mll_footer,mllNumber_file;
        private TextView mtxt_like, mtxt_comment, mtxt_share;
        private TextView mtxtNo_like, mtxtNo_comment, mtxtNo_share;

        public ViewHolder(View itemView) {
            super(itemView);
            mrr_header_file = itemView.findViewById(R.id.rr_header_file);
            mll_footer = itemView.findViewById(R.id.ll_footer);
            mllNumber_file = itemView.findViewById(R.id.llNumber_file);
            mImgActPic = itemView.findViewById(R.id.image_single);
            mcircle_img = itemView.findViewById(R.id.circle_img);
            mtxt_label = itemView.findViewById(R.id.txt_label);
            mtxt_time_ago = itemView.findViewById(R.id.txt_time_ago);
            mtxt_like = itemView.findViewById(R.id.txt_like);
            mtxt_comment = itemView.findViewById(R.id.txt_comment);
            mtxt_share = itemView.findViewById(R.id.txt_share);
            mtxtNo_like = itemView.findViewById(R.id.txtNo_like);
            mtxtNo_comment = itemView.findViewById(R.id.txtNo_comment);
            mtxtNo_share = itemView.findViewById(R.id.txtNo_share);


        }
        }
    }
