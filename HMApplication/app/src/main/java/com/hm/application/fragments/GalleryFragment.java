package com.hm.application.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.activity.NextActivity;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.insta.utils.SquareImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GridView mGridView;
    private ImageView mIvSelectImage;
    private TextView mTvNextScreen;
    private ArrayList<String> mMultiSelectImages, mAllImages;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(R.layout.fragment_gallery, container, false);
            mIvSelectImage = view.findViewById(R.id.galleryImageView);
            mGridView = view.findViewById(R.id.gridViewGallery);
            mMultiSelectImages = new ArrayList<>();
            mAllImages = new ArrayList<>();
            mTvNextScreen = view.findViewById(R.id.tvNext);
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mAllImages = getImagesPath(getActivity());

            int gridWidth = getResources().getDisplayMetrics().widthPixels;
            int imageWidth = gridWidth / 3;
            mGridView.setColumnWidth(imageWidth);

            mTvNextScreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMultiSelectImages.size() == 0) {
                        CommonFunctions.toDisplayToast("Please select at least one image", getContext());
                    } else {
                        if (mMultiSelectImages.size() > 1) {
                            CommonFunctions.toDisplayToast("You've selected Total " + mMultiSelectImages.size() + " image(s).", getContext());
                        }
                        Intent intent = new Intent(getActivity(), NextActivity.class);
//                        intent.putExtra(getString(R.string.selected_image), mSelectedImage);
                        intent.putExtra("list", mMultiSelectImages);
                        startActivity(intent);
                    }
                }
            });

            if (mAllImages != null && mAllImages.size() > 0) {
                mGridView.setAdapter(new GridImageAdapter(getContext(), R.layout.layout_grid_imageview, mAllImages));
                setImage(mAllImages.get(0), mIvSelectImage, AppConstants.Append);
            } else {
                CommonFunctions.toDisplayToast(" No Images ", getContext());
            }

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setImage(mAllImages.get(position), mIvSelectImage, AppConstants.Append);
                }
            });
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(PathOfImage);
        }
        return listOfAllImages;
    }

    private void setImage(String imgURL, ImageView image, String append) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        imageLoader.displayImage(append + imgURL, image, new ImageLoadingListener() {
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
    }


    public class GridImageAdapter extends ArrayAdapter<String> {

        private Context context;
        private int layoutResource;
        private ArrayList<String> imgURLs;

        public GridImageAdapter(Context context, int layoutResource, ArrayList<String> imgURLs) {
            super(context, layoutResource, imgURLs);
            this.context = context;
            this.layoutResource = layoutResource;
            this.imgURLs = imgURLs;
        }

        private class ViewHolder {
            SquareImageView image;
            CheckBox checkbox;
            RelativeLayout relativeLayout;
            int id;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View row, @NonNull ViewGroup parent) {
            final ViewHolder holder;
            View convertView = row;
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(layoutResource, parent, false);
                holder = new ViewHolder();
                holder.image = convertView.findViewById(R.id.gridImageView);
                holder.checkbox = convertView.findViewById(R.id.itemCheckBox);
                holder.relativeLayout = convertView.findViewById(R.id.rl1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.checkbox.setId(position);
            holder.relativeLayout.setId(position);
            holder.image.setId(position);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if (holder.checkbox.isChecked()) {
                        holder.checkbox.setChecked(false);
                        if (mMultiSelectImages != null) {
                            mMultiSelectImages.remove(getItem(id));
                        }
                    } else {
                        holder.checkbox.setChecked(true);
                        if (mMultiSelectImages != null) {
                            mMultiSelectImages.add(getItem(id));
                        }
                        setImage(getItem(id), mIvSelectImage, AppConstants.Append);
                    }
                }
            });
            holder.checkbox.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    toSetView(v);
                }
            });
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
            holder.checkbox.setChecked(mMultiSelectImages.contains(getItem(position)));
            return convertView;
        }

        private void toSetView(View v) {
            CheckBox cb = (CheckBox) v;
            int id = cb.getId();
            if (mMultiSelectImages.contains(getItem(id))) {
                cb.setChecked(false);
                if (mMultiSelectImages != null) {
                    mMultiSelectImages.remove(getItem(id));
                }
            } else {
                cb.setChecked(true);
                if (mMultiSelectImages != null) {
                    mMultiSelectImages.add(getItem(id));
                }
                setImage(getItem(id), mIvSelectImage, AppConstants.Append);
            }
        }
    }
}