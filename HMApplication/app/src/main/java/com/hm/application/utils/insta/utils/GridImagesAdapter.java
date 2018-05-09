package com.hm.application.utils.insta.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class GridImagesAdapter extends ArrayAdapter<String> {

    private Context context;
    private int layoutResource;
    private ArrayList<String> imgURLs;

    public GridImagesAdapter(Context context, int layoutResource, ArrayList<String> imgURLs) {
        super(context, layoutResource, imgURLs);
        this.context = context;
        this.layoutResource = layoutResource;
        this.imgURLs = imgURLs;
    }

    private static class ViewHolder {
        SquareImageView image;
        CheckBox checkBox;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View row, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View convertView = row;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.gridImageView);
            holder.checkBox = convertView.findViewById(R.id.itemCheckBox);
            holder.checkBox.setVisibility(View.GONE);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        imageLoader.displayImage(AppConstants.Append + getItem(position), holder.image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
        return convertView;
    }
}