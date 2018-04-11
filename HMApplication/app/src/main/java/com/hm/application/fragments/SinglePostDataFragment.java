package com.hm.application.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.adapter.SlidingImageAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.utils.CommonFunctions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class SinglePostDataFragment extends Fragment {

    private RelativeLayout mrr_header_file;
    private CircleImageView mcircle_img;
    private TextView mtxt_label, mtxt_time_ago;
    private LinearLayout mllNumber_file;
    private TextView mtxtNo_like, mtxtNo_comment, mtxtNo_share;
    private LinearLayout mll_footer;
    private TextView mtxt_like, mtxt_comment, mtxt_share;
    private ViewPager mVp;
    private TabLayout mTl;

    public SinglePostDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_post_data, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onBindViews();
        checkInternetConnection();
    }

    private void onBindViews() {
        try {

            mrr_header_file = getActivity().findViewById(R.id.rr_header_file);
            mcircle_img = getActivity().findViewById(R.id.circle_img);
            mtxt_label = getActivity().findViewById(R.id.txt_label);
            mtxt_time_ago = getActivity().findViewById(R.id.txt_time_ago);

            mllNumber_file = getActivity().findViewById(R.id.llNumber_file);
            mtxtNo_like = getActivity().findViewById(R.id.txtNo_like);
            mtxtNo_comment = getActivity().findViewById(R.id.txtNo_comment);
            mtxtNo_share = getActivity().findViewById(R.id.txtNo_share);

            mll_footer = getActivity().findViewById(R.id.ll_footer);
            mtxt_like = getActivity().findViewById(R.id.txt_like);
            mtxt_comment = getActivity().findViewById(R.id.txt_comment);
            mtxt_share = getActivity().findViewById(R.id.txt_share);
            mtxtNo_like.setText("0 " + getContext().getResources().getString(R.string.str_like));
            mtxtNo_comment.setText("0 " + getContext().getString(R.string.str_comment));
            mtxtNo_share.setText("0 " + getContext().getResources().getString(R.string.str_share));

            mVp = getActivity().findViewById(R.id.vpHs2);
            mTl = getActivity().findViewById(R.id.tlHs2);

            if (User.getUser(getContext()).getPicPath() != null) {
                Picasso.with(getContext())
                        .load(AppConstants.URL + User.getUser(getContext()).getPicPath().replaceAll("\\s", "%20")).into(mcircle_img);

            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                if (getArguments() != null) {
                    toDisplayPostData();
                } else {
                    CommonFunctions.toDisplayToast("Failed", getContext());
                    super.onDestroy();
                }
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toDisplayPostData() {
        try {
            if (getArguments().getString(AppConstants.BUNDLE) != null) {
                Log.d("HmApp", " SinglePost " + getArguments().getString(AppConstants.BUNDLE));
                JSONObject obj = new JSONObject(getArguments().getString(AppConstants.BUNDLE));
                Log.d("HmApp", " SinglePost1 " + obj);
                if (!obj.isNull(getContext().getString(R.string.str_username_))) {
                    Log.d("HmApp", " SinglePost2 " + obj.getString(getContext().getString(R.string.str_username_)));
                    mtxt_label.setText(obj.getString(getContext().getString(R.string.str_username_)));
                } else if (!obj.isNull(getContext().getString(R.string.str_post_small))) {
                    Log.d("HmApp", " SinglePost3 " + obj.getString(getContext().getString(R.string.str_post_small)));
                    mtxt_label.setText(obj.getString(getContext().getString(R.string.str_post_small)));
                } else if (!obj.isNull(getContext().getString(R.string.str_caption))) {
                    Log.d("HmApp", " SinglePost4 " + obj.getString(getContext().getString(R.string.str_caption)));
                    mtxt_label.setText(obj.getString(getContext().getString(R.string.str_caption)));
                } else if (!obj.isNull(getContext().getString(R.string.str_username_))) {
                    Log.d("HmApp", " SinglePost5 " + obj.getString(getContext().getString(R.string.str_username_)));
                    mtxt_label.setText(obj.getString(getContext().getString(R.string.str_username_)));
                } else {
                    Log.d("HmApp", " SinglePost6 " + User.getUser(getContext()).getUsername());
                    mtxt_label.setText(User.getUser(getContext()).getUsername());
                }

                if (!obj.isNull(getContext().getString(R.string.str_time))) {
                    mtxt_time_ago.setText(CommonFunctions.toSetDate(obj.getString(getContext().getString(R.string.str_time))));
                }
                if (!obj.isNull(getContext().getString(R.string.str_like_count))) {
                    mtxtNo_like.setText(obj.getString(getContext().getString(R.string.str_like_count)) + " " + getContext().getResources().getString(R.string.str_like));
                }
                if (!obj.isNull(getContext().getString(R.string.str_comment_count))) {
                    mtxtNo_comment.setText(obj.getString(getContext().getString(R.string.str_comment_count)) + " " + getContext().getString(R.string.str_comment));
                }
                if (!obj.isNull(getContext().getString(R.string.str_share_count))) {
                    mtxtNo_share.setText(obj.getString(getContext().getString(R.string.str_share_count)) + " " + getContext().getResources().getString(R.string.str_share));
                }
                if (User.getUser(getContext()).getPicPath() != null) {
                    Picasso.with(getContext())
                            .load(AppConstants.URL + User.getUser(getContext()).getPicPath().replaceAll("\\s", "%20"))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .resize(100, 100)
                            .into(mcircle_img);
                }

                if (!obj.isNull(getContext().getString(R.string.str_image_url))) {
                    mVp.setAdapter(new SlidingImageAdapter(getContext(), obj.getString(getContext().getString(R.string.str_image_url)).split(",")));
                    if (obj.getString(getContext().getString(R.string.str_image_url)).split(",").length > 1) {
                        mTl.setupWithViewPager(mVp);
                    } else {
                        mTl.setVisibility(View.GONE);
                    }
                } else if (!obj.isNull(getContext().getString(R.string.str_image))) {
                    mVp.setAdapter(new SlidingImageAdapter(getContext(), obj.getString(getContext().getString(R.string.str_image)).split(",")));
                    mTl.setupWithViewPager(mVp);
                } else {
                    mVp.setVisibility(View.GONE);
                    mTl.setVisibility(View.GONE);
                }
            }
        } catch (Exception | Error e) {
            Log.d("HmAPp ", " Error  " + e.getMessage());
            e.printStackTrace();
        }
    }
}