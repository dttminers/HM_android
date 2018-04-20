package com.hm.application.newtry.Share;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.utils.CommonFunctions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import com.hm.application.R;
import com.hm.application.newtry.Utils.FilePaths;
import com.hm.application.newtry.Utils.FileSearch;
import com.hm.application.newtry.Utils.GridImageAdapter;
import com.squareup.picasso.Picasso;

public class GalleryFragment extends Fragment {
    private static final String TAG = "hmapp GalleryFragment";

    //constants
    private static final int NUM_GRID_COLUMNS = 3;

    //widgets
    private GridView gridView;
    private ImageView galleryImage;
//    private ProgressBar mProgressBar;
    private Spinner directorySpinner;

    //vars
    private ArrayList<String> directories;
    private String mAppend = "file:/";
    private String mSelectedImage;
    private List<String> mApps;
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
        directorySpinner = view.findViewById(R.id.spinnerDirectory);
//        mProgressBar = view.findViewById(R.id.progressBar);
//        mProgressBar.setVisibility(View.GONE);
        directories = new ArrayList<>();
        mMultiSelectImages = new ArrayList<>();
        mApps = new ArrayList<>();

        Log.d(TAG, "onCreateView: started.");

        ImageView shareClose = view.findViewById(R.id.ivCloseShare);
        shareClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing the gallery fragment.");
                getActivity().finish();
            }
        });


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

        init();
        return view;
    }


    private void init() {
        FilePaths filePaths = new FilePaths();

        //check for other folders indide "/storage/emulated/0/pictures"
        if (FileSearch.getDirectoryPaths(filePaths.MAIN) != null) {
            directories = FileSearch.getDirectoryPaths(filePaths.MAIN);
        }
//        if (FileSearch.getDirectoryPaths(filePaths.PICTURES) != null) {
//            directories = FileSearch.getDirectoryPaths(filePaths.PICTURES);
//        }
//        if (FileSearch.getDirectoryPaths(filePaths.CAMERA) != null) {
//            directories = FileSearch.getDirectoryPaths(filePaths.CAMERA);
//        }
//        directories.add(filePaths.CAMERA);

        ArrayList<String> directoryNames = new ArrayList<>();
        for (int i = 0; i < directories.size(); i++) {
            Log.d(TAG, "init: directory: " + directories.get(i));
            int index = directories.get(i).lastIndexOf("/");
            String string = directories.get(i).substring(index);
            Log.d("hmapp Gallery", " directoryNames : " + string);
            directoryNames.add(string);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, directoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        directorySpinner.setAdapter(adapter);

        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected: " + directories.get(position));

                //setup our image grid for the directory chosen
                setupGridView(directories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//    private void setupGridView(String selectedDirectory) {
//        Log.d(TAG, "setupGridView: directory chosen: " + selectedDirectory);
//        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDirectory);
//        Log.d("hmapp", " img urls  " + imgURLs );
//
//        mApps = imgURLs;
//        //set the grid column width
//        int gridWidth = getResources().getDisplayMetrics().widthPixels;
//        int imageWidth = gridWidth / NUM_GRID_COLUMNS;
//        gridView.setColumnWidth(imageWidth);
//
//        gridView.setAdapter(new AppsAdapter());
//
//        //use the grid adapter to adapter the images to gridview
//        GridImageAdapter adapter = new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, mAppend, imgURLs);
//        gridView.setAdapter(adapter);
//
//        //set the first image to be displayed when the activity fragment view is inflated
//        try {
//            if (imgURLs.size() > 0) {
//                setImage(imgURLs.get(0), galleryImage, mAppend);
//                mSelectedImage = imgURLs.get(0);
//            } else {
//                CommonFunctions.toDisplayToast("No Images", getContext());
//            }
//        } catch (ArrayIndexOutOfBoundsException e) {
//            Log.e(TAG, "setupGridView: ArrayIndexOutOfBoundsException: " + e.getMessage());
//        }
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onItemClick: selected an image: " + imgURLs.get(position));
//
//                setImage(imgURLs.get(position), galleryImage, mAppend);
//                mSelectedImage = imgURLs.get(position);
//            }
//        });
//
//        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//    }

    private void setupGridView(String selectedDirectory){
        Log.d(TAG, "setupGridView: directory chosen: " + selectedDirectory);
        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDirectory);

        //set the grid column width
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);

        //use the grid adapter to adapter the images to gridview
        GridImageAdapter adapter = new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, mAppend, imgURLs);
        gridView.setAdapter(adapter);

        //set the first image to be displayed when the activity fragment view is inflated
        try{
            setImage(imgURLs.get(0), galleryImage, mAppend);
            mSelectedImage = imgURLs.get(0);
        }catch (ArrayIndexOutOfBoundsException e){
            Log.e(TAG, "setupGridView: ArrayIndexOutOfBoundsException: " +e.getMessage() );
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected an image: " + imgURLs.get(position));

                setImage(imgURLs.get(position), galleryImage, mAppend);
                mSelectedImage = imgURLs.get(position);
            }
        });

    }

    private void setImage(String imgURL, ImageView image, String append) {
        Log.d(TAG, "setImage: setting image");

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        imageLoader.displayImage(append + imgURL, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
//                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
//                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public class AppsAdapter extends BaseAdapter {
        AppsAdapter() {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final View item = LayoutInflater.from(getContext()).inflate(R.layout.multi_select_image, null, false);
            item.setTag(String.valueOf(position));
            mRlImages = item.findViewById(R.id.rlImage);
            mIvImages = item.findViewById(R.id.images);
            mCbImages = item.findViewById(R.id.cb_images);
            mTvIDs = item.findViewById(R.id.tvId);
            Log.d("hmapp Gallery", " list " + mApps.get(position));
            Picasso.with(getContext()).load(mAppend + mApps.get(position)).placeholder(R.color.light).error(R.color.light2).into(mIvImages);
//            mTvIDs.setText(mAppend + mApps.get(position));
            mTvIDs.setText(String.valueOf(position));
            mCbImages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Log.d("HmApp Gallery ", " Selected : checked T: " + mTvIDs.getText());
                        mMultiSelectImages.add(mTvIDs.getText().toString());
                    } else {
                        Log.d("HmApp", " Selected : checked F: " + mTvIDs.getText());
                        if (mMultiSelectImages.size() > 0) {
                            mMultiSelectImages.remove(mTvIDs.getText().toString());
                        }
                    }

                }
            });

            mRlImages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Hmapp Gallery", " click  :  " + mCbImages.isChecked() + ":" + mTvIDs.getText() + ":" + v.getTag() + ":" + v.getId() + ":" + item.getTag());
                    View vc = gridView.getChildAt(Integer.parseInt(item.getTag().toString()));
                    mCbImages = vc.findViewById(R.id.cb_images);
                    mCbImages.setChecked(!mCbImages.isChecked());
                }
            });
            return item;
        }


        public final int getCount() {
            Log.d("Hmapp Gallery", " getcount : " + mApps.size());
            return mApps.size();
        }

        public final Object getItem(int position) {
            return mApps.get(position);
        }

        public final long getItemId(int position) {
            return position;
        }
    }
}