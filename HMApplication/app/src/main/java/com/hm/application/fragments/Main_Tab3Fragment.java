package com.hm.application.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.newtry.Home.HomeActivity;
import com.hm.application.newtry.Share.ShareActivity;

public class Main_Tab3Fragment extends Fragment {

    private EditText mEdtPostData;
    private GridLayout mGv;
    private Button mBtnFollow, mBtnPostSubmit;
    private ImageView mIvPostCamera, mIvPostTag;

    private OnFragmentInteractionListener mListener;

    public Main_Tab3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_tab3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Post
        mBtnPostSubmit = getActivity().findViewById(R.id.btnPostSubmit);
        mEdtPostData = getActivity().findViewById(R.id.edt_desc_post);
        mIvPostCamera = getActivity().findViewById(R.id.imgIconCam);
        mIvPostTag = getActivity().findViewById(R.id.imgIconTag);
        mGv = getActivity().findViewById(R.id.mGvImages);
        mBtnPostSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), ShareActivity.class));
                    }
                }
        );

    }

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
}