package com.hm.application.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hm.application.R;
//import com.hm.application.newtry.Utils.GridImageAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class TBNearByFragment extends Fragment {

    private GridView gridView;

    public TBNearByFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tbnear_by, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridView = getActivity().findViewById(R.id.gridViewnb);
        toCollectImages();
    }

    private void toCollectImages() {
        try {
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;
            //Stores all the images from the gallery in Cursor
            Cursor cursor = getActivity().getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                    null, orderBy);
            //Total number of images
            int count = cursor.getCount();

            //Create an array to store path to all the images
            final String[] arrPath = new String[count];

            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                //Store the path of the image
                arrPath[i] = cursor.getString(dataColumnIndex);
                Log.d("hmapp ", "PATH : " + arrPath[i]);
            }
            // The cursor should be freed up after use with close()
            cursor.close();

            //set the grid column width
            int gridWidth = getResources().getDisplayMetrics().widthPixels;
            int imageWidth = gridWidth/3;
            gridView.setColumnWidth(imageWidth);

//            GridImageAdapter adapter = new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, "file:/", new ArrayList<>(Arrays.asList(arrPath)));
//            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("hmapp", "onItemClick: selected an image: " + arrPath[position]);

//                    setImage(imgURLs.get(position), galleryImage, mAppend);
//                    mSelectedImage = imgURLs.get(position);
                }
            });
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}