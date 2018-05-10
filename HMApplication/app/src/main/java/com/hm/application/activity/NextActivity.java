package com.hm.application.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
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
    private ArrayList<String> images = new ArrayList<>();
    private ImageView mIvPic, mIvBackArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_next);
            mCaption = findViewById(R.id.caption);

            mIvBackArrow = findViewById(R.id.ivBackArrow);
            mIvPic = findViewById(R.id.imageShare);
            mTvShare = findViewById(R.id.tvShare);
            mGvPics = findViewById(R.id.gridViewPics);

            if (getIntent() != null) {
                Log.d("hmapp", " Intent : " + getIntent().getExtras() + getIntent().getStringArrayListExtra("list"));
//                if (getIntent().hasExtra(getString(R.string.selected_image))) {
                if (getIntent().hasExtra("list")) {
                    images.addAll(getIntent().getStringArrayListExtra("list"));
                    UniversalImageLoader.setImage(images.get(0), mIvPic, null, AppConstants.Append);
//                    if (getIntent().hasExtra("list")) {
//                        images = getIntent().getStringArrayListExtra("list");
                    if (images != null && images.size() > 1) {
                        //set the grid column width
                        int gridWidth = getResources().getDisplayMetrics().widthPixels;
                        int imageWidth = gridWidth / 3;
                        mGvPics.setColumnWidth(imageWidth);
                        UniversalImageLoader.setImage(images.get(0), mIvPic, null, AppConstants.Append);
                        mGvPics.setAdapter(new GridImagesAdapter(NextActivity.this, R.layout.layout_grid_imageview, images));
                    }
//                    }
                } else if (getIntent().hasExtra(getString(R.string.selected_bitmap))) {
                    bitmap = getIntent().getParcelableExtra(getString(R.string.selected_bitmap));
                    mIvPic.setImageBitmap(bitmap);
                    images.add(0, CommonFunctions.getLocalBitmapFilePath(mIvPic));
                }
            }

            mTvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                if (mCaption.getText().toString().trim().length() > 0) {
//                    if (images != null) {
//                        if (images.size() > 1) {
//                            MyPost.toUploadAlbum(NextActivity.this, NextActivity.this, mCaption.getText().toString(), images);
//                        } else {
//                            MyPost.toUploadImage(NextActivity.this, NextActivity.this, mCaption.getText().toString(), images.get(0));
//                        }
//                    } else if (bitmap != null) {
//                        MyPost.toUploadImage(NextActivity.this, NextActivity.this, mCaption.getText().toString(), CommonFunctions.getLocalBitmapFilePath(mIvPic));
//                        } else {
//                            MyPost.toUpdateMyPost(NextActivity.this, "post", null, null, mCaption.getText().toString().trim());
//                    }

                        if (images != null) {
                            Log.d("hmapp", " size : " + images.size() + " : " + images);
                            if (images.size() > 1) {
                                MyPost.toUploadAlbum(NextActivity.this, NextActivity.this, mCaption.getText().toString(), images);
                            } else {
                                MyPost.toUploadImage(NextActivity.this, NextActivity.this, mCaption.getText().toString(), images.get(0));
                            }
                        } else {
                            CommonFunctions.toDisplayToast(" Sorry, No Image Selected", NextActivity.this);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 1000);

                        }


                        mCaption.setText("");
//                } else {
//                    CommonFunctions.toDisplayToast(" Empty Data ", NextActivity.this);
//                }
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                        Crashlytics.logException(e);
                    }
                }
            });
            mIvBackArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
    }

//    private void setImage() {
//        intent = getIntent();
//        if (intent.hasExtra(getString(R.string.selected_image))) {
//            imgUrl = intent.getStringExtra(getString(R.string.selected_image));
//            UniversalImageLoader.setImage(imgUrl, image, null, AppConstants.Append);
//        } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
//            bitmap = intent.getParcelableExtra(getString(R.string.selected_bitmap));
//            image.setImageBitmap(bitmap);
//        }
//    }
}