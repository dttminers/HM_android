package com.hm.application.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hm.application.R;

public class DestinationInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapseToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_info);
        collapseToolbar = findViewById(R.id.ctl);
        collapseToolbar.setTitle(getString(R.string.app_name));
        collapseToolbar.setExpandedTitleTextAppearance(R.style.ExAppBar);
        collapseToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
    }
}
