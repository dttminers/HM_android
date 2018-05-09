package com.hm.application.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.common.MyPost;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.insta.utils.GridImagesAdapter;
import com.hm.application.utils.insta.utils.UniversalImageLoader;

import java.util.ArrayList;

public class NextActivity extends AppCompatActivity {
    private EditText mCaption;
    private GridView mGvPics;
    private TextView mTvShare;
    private Bitmap bitmap;
    private Intent intent;
    private ArrayList<String> images = new ArrayList<>();
    private ImageView mIvPic, mIvBackArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        mCaption = findViewById(R.id.caption);

        mIvBackArrow = findViewById(R.id.ivBackArrow);
        mIvPic = findViewById(R.id.imageShare);
        mTvShare = findViewById(R.id.tvShare);
        mGvPics = findViewById(R.id.gridViewPics);


        if (intent.hasExtra(getString(R.string.selected_image))) {
            images.add(0, intent.getStringExtra(getString(R.string.selected_image)));
            UniversalImageLoader.setImage(images.get(0), mIvPic, null, AppConstants.Append);
            if (intent.hasExtra("list")) {
                images = intent.getStringArrayListExtra("list");
                if (images != null && images.size() > 0) {
                    //set the grid column width
                    int gridWidth = getResources().getDisplayMetrics().widthPixels;
                    int imageWidth = gridWidth / 3;
                    mGvPics.setColumnWidth(imageWidth);
                    UniversalImageLoader.setImage(images.get(0), mIvPic, null, AppConstants.Append);
                    mGvPics.setAdapter(new GridImagesAdapter(NextActivity.this, R.layout.layout_grid_imageview, images));
                }
            }
        } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
            bitmap = intent.getParcelableExtra(getString(R.string.selected_bitmap));
        }


        mTvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (mCaption.getText().toString().trim().length() > 0) {
                if (images != null) {
                    if (images.size() > 1) {
                        MyPost.toUploadAlbum(NextActivity.this, NextActivity.this, mCaption.getText().toString(), images);
                    } else {
                        MyPost.toUploadImage(NextActivity.this, NextActivity.this, mCaption.getText().toString(), images.get(0));
                    }
                } else if (bitmap != null) {
                    MyPost.toUploadImage(NextActivity.this, NextActivity.this, mCaption.getText().toString(), CommonFunctions.getLocalBitmapFilePath(mIvPic));
//                        } else {
//                            MyPost.toUpdateMyPost(NextActivity.this, "post", null, null, mCaption.getText().toString().trim());
                }
                mCaption.setText("");
//                } else {
//                    CommonFunctions.toDisplayToast(" Empty Data ", NextActivity.this);
//                }
            }
        });

        mIvBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}