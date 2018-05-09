package com.hm.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.hm.application.R;

import java.util.ArrayList;

public class GridImageViewAdapter extends ArrayAdapter<String> {
    public GridImageViewAdapter(Context context, ArrayList<String> imgURLs) {
        super(context, R.layout.layout_grid_imageview, imgURLs);
        // R.layout.layout_grid_imageview
    }

}
