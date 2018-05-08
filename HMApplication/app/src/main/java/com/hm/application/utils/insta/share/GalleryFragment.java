package com.hm.application.utils.insta.share;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.*;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.insta.utils.GridImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private static final String TAG = "hmapp GalleryFragment";

    //constants
    private static final int NUM_GRID_COLUMNS = 3;

    //widgets
    private GridView gridView;
    private ImageView galleryImage;
    //    private ProgressBar mProgressBar;
//    private Spinner directorySpinner;

    //vars
//    private ArrayList<String> directories;
//    private String mAppend = "file:/";
    private String mSelectedImage;
//    private List<String> mApps;
    private ArrayList<String> mMultiSelectImages;

    // View from multi_select_image
    RelativeLayout mRlImages;
    ImageView mIvImages;
    CheckBox mCbImages;
    TextView mTvIDs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        galleryImage = view.findViewById(R.id.galleryImageView);
        gridView = view.findViewById(R.id.gridView);
//        directorySpinner = view.findViewById(R.id.spinnerDirectory);
//        directories = new ArrayList<>();
        mMultiSelectImages = new ArrayList<>();
//        mApps = new ArrayList<>();

        Log.d(TAG, "onCreateView: started.");

//        ImageView shareClose = view.findViewById(R.id.ivCloseShare);
//        shareClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: closing the gallery fragment.");
//                getActivity().finish();
//            }
//        });


        TextView nextScreen = view.findViewById(R.id.tvNext);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to the final share screen.");
                Intent intent = new Intent(getActivity(), NextActivity.class);
                intent.putExtra(getString(R.string.selected_image), mSelectedImage);
                intent.putExtra("list", mMultiSelectImages.toString());
                startActivity(intent);
            }
        });

//        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            ArrayList<String> imgUrls = getImagesPath(getActivity());
            gridView.setAdapter(new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, AppConstants.Append, imgUrls));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
    }

    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);
            Log.d(" HmApp", "PathOfImage = " + PathOfImage);
            listOfAllImages.add(PathOfImage);
        }
        return listOfAllImages;
    }


//
//    public class AppsAdapter extends BaseAdapter {
//        AppsAdapter() {
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            final View item = LayoutInflater.from(getContext()).inflate(R.layout.multi_select_image, null, false);
//            item.setTag(String.valueOf(position));
//            mRlImages = item.findViewById(R.id.rlImage);
//            mIvImages = item.findViewById(R.id.images);
//            mCbImages = item.findViewById(R.id.cb_images);
//            mTvIDs = item.findViewById(R.id.tvId);
//            Log.d("hmapp Gallery", " list " + mApps.get(position));
//            Picasso.with(getContext()).load(mAppend + mApps.get(position)).placeholder(R.color.light).error(R.color.light2).into(mIvImages);
////            mTvIDs.setText(mAppend + mApps.get(position));
//            mTvIDs.setText(String.valueOf(position));
//            mCbImages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        Log.d("HmApp Gallery ", " Selected : checked T: " + mTvIDs.getText());
//                        mMultiSelectImages.add(mTvIDs.getText().toString());
//                    } else {
//                        Log.d("HmApp", " Selected : checked F: " + mTvIDs.getText());
//                        if (mMultiSelectImages.size() > 0) {
//                            mMultiSelectImages.remove(mTvIDs.getText().toString());
//                        }
//                    }
//
//                }
//            });
//
//            mRlImages.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("Hmapp Gallery", " click  :  " + mCbImages.isChecked() + ":" + mTvIDs.getText() + ":" + v.getTag() + ":" + v.getId() + ":" + item.getTag());
//                    View vc = gridView.getChildAt(Integer.parseInt(item.getTag().toString()));
//                    mCbImages = vc.findViewById(R.id.cb_images);
//                    mCbImages.setChecked(!mCbImages.isChecked());
//                }
//            });
//            return item;
//        }
//
//
//        public final int getCount() {
//            Log.d("Hmapp Gallery", " getcount : " + mApps.size());
//            return mApps.size();
//        }
//
//        public final Object getItem(int position) {
//            return mApps.get(position);
//        }
//
//        public final long getItemId(int position) {
//            return position;
//        }
//    }
}