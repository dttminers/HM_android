package com.hm.application.newtry.Share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.common.MyPost;
import com.hm.application.newtry.Utils.UniversalImageLoader;
import com.hm.application.utils.CommonFunctions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NextActivity extends AppCompatActivity {

    private static final String TAG = "NextActivity";

    //widgets
    private EditText mCaption;

    //vars
    //vars
    private String mAppend = "file:/";
    private int imageCount = 0;
    private String imgUrl;
    private Bitmap bitmap;
    private Intent intent;
    private ArrayList<Uri> images = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        mCaption = findViewById(R.id.caption);

        ImageView backArrow = findViewById(R.id.ivBackArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing the activity");
                finish();
            }
        });


        TextView share = findViewById(R.id.tvShare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to the final share screen.");
                String caption = mCaption.getText().toString();
                Log.d("hmapp", " list " + intent.getStringExtra("list"));

                if (intent.hasExtra(getString(R.string.selected_image))) {
                    imgUrl = intent.getStringExtra(getString(R.string.selected_image));
//                    images = intent.getStringExtra("list");

                } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
                    bitmap = intent.getParcelableExtra(getString(R.string.selected_bitmap));
                }

                if (mCaption.getText().toString().trim().length() > 0) {
                    if (images.size() > 0) {
                        if (images.size() > 1) {
                            MyPost.toUploadAlbum(NextActivity.this, NextActivity.this, mCaption.getText().toString(), images);
                        } else {
                            MyPost.toUploadImage(NextActivity.this, NextActivity.this, mCaption.getText().toString(), images.get(0));
                        }
                    } else {
                        MyPost.toUpdateMyPost(NextActivity.this, "post", null, null, mCaption.getText().toString().trim());
                    }
                    mCaption.setText("");
                } else {
                    CommonFunctions.toDisplayToast(" Empty Data ", NextActivity.this);
                }
            }
        });

        setImage();
    }


    private void setImage() {
        intent = getIntent();
        ImageView image = findViewById(R.id.imageShare);

        if (intent.hasExtra(getString(R.string.selected_image))) {
            imgUrl = intent.getStringExtra(getString(R.string.selected_image));
            Log.d(TAG, "setImage: got new image url: " + imgUrl);
            UniversalImageLoader.setImage(imgUrl, image, null, mAppend);
        } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
            bitmap = intent.getParcelableExtra(getString(R.string.selected_bitmap));
            Log.d(TAG, "setImage: got new bitmap");
            image.setImageBitmap(bitmap);
        }
    }
}
