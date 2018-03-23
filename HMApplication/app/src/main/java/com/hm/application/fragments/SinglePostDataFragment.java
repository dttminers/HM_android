package com.hm.application.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.adapter.SlidingImageAdapter;
import com.hm.application.adapter.UserTab22Adapter;
import com.hm.application.classes.UserTimeLinePost;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SinglePostDataFragment extends Fragment {

    RelativeLayout mrr_header_file;
    ImageView mcircle_img, mIvPost;
    TextView mtxt_label, mtxt_time_ago;
    LinearLayout mllNumber_file;
    TextView mtxtNo_like, mtxtNo_comment, mtxtNo_share;
    LinearLayout mll_footer;
    TextView mtxt_like, mtxt_comment, mtxt_share;
    ViewPager mVp;
    TabLayout mTl;

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

            mrr_header_file = (RelativeLayout) getActivity().findViewById(R.id.rr_header_file);
            mcircle_img = (ImageView) getActivity().findViewById(R.id.circle_img);
            mtxt_label = (TextView) getActivity().findViewById(R.id.txt_label);
            mtxt_time_ago = (TextView) getActivity().findViewById(R.id.txt_time_ago);
            mIvPost = (ImageView) getActivity().findViewById(R.id.image_single);

            mllNumber_file = (LinearLayout) getActivity().findViewById(R.id.llNumber_file);
            mtxtNo_like = (TextView) getActivity().findViewById(R.id.txtNo_like);
            mtxtNo_comment = (TextView) getActivity().findViewById(R.id.txtNo_comment);
            mtxtNo_share = (TextView) getActivity().findViewById(R.id.txtNo_share);

            mll_footer = (LinearLayout) getActivity().findViewById(R.id.ll_footer);
            mtxt_like = (TextView) getActivity().findViewById(R.id.txt_like);
            mtxt_comment = (TextView) getActivity().findViewById(R.id.txt_comment);
            mtxt_share = (TextView) getActivity().findViewById(R.id.txt_share);
            mtxtNo_like.setText("0 " + getContext().getResources().getString(R.string.str_like));
            mtxtNo_comment.setText("0 "+ getContext().getString(R.string.str_comment));
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
                    toDisplayPostData();//.execute();
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
            Log.d("HmAPp", " " + getArguments());
            if (getArguments().getString(AppConstants.BUNDLE) != null) {
                JSONObject obj = new JSONObject(getArguments().getString(AppConstants.BUNDLE));
                Log.d("Hmapp", " obj " + obj);

                if (!obj.isNull(getContext().getString(R.string.str_username_))) {
                    mtxt_label.setText(obj.getString(getContext().getString(R.string.str_username_)));
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
                if (!obj.isNull(getContext().getString(R.string.str_image_url))) {
                    Picasso.with(getContext())
                            .load(AppConstants.URL + obj.getString(getContext().getString(R.string.str_image_url)).replaceAll("\\s", "%20"))
                            .into(mcircle_img);
                }

                if (getArguments().getString(AppConstants.FROM).equals("TAB1")) {
                    if (!obj.isNull(getContext().getString(R.string.str_image_url))) {
                        Picasso.with(getContext())
                                .load(AppConstants.URL + obj.getString(getContext().getString(R.string.str_image_url)).replaceAll("\\s", "%20"))
                                .into(mIvPost);
                        mIvPost.setVisibility(View.VISIBLE);
                    }
                    mVp.setVisibility(View.GONE);
                    mTl.setVisibility(View.GONE);
                } else if (getArguments().getString(AppConstants.FROM).equals("TAB4")) {

                    if (!obj.isNull(getContext().getString(R.string.str_image_url))) {
//                    Picasso.with(context).load(AppConstants.URL + jsonObject.getString("image").replaceAll("\\s", "%20")).into(mImgActPic);
                        mVp.setAdapter(new SlidingImageAdapter(getContext(), obj.getString(getContext().getString(R.string.str_image_url)).split(",")));
                        mTl.setupWithViewPager(mVp);
                        mIvPost.setVisibility(View.GONE);
                        mVp.setVisibility(View.VISIBLE);
                        mTl.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}


