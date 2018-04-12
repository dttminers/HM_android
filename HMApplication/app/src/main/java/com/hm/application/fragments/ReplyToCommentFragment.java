package com.hm.application.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.adapter.DisplayReplyAdapter;
import com.hm.application.common.MyPost;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReplyToCommentFragment extends Fragment {

    private RecyclerView mRv;
    private TextView mTvLikesData;
    private EditText mEdtCmt;
    private Button mBtnCmt;
    private LinearLayout mLlAddCmt;
    private RelativeLayout mllCuCall;
    private ImageView mIvProfilePic;
    public String commentId = null;

    public ReplyToCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            toBindViews();
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void checkInternetConnection() throws Exception, Error {
        if (CommonFunctions.isOnline(getContext())) {
            toDisplayReply();
        } else {
            CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
        }
    }

    @NonNull
    private void toBindViews() throws Exception, Error {
        mRv = getActivity().findViewById(R.id.rvComments);
        mRv.setNestedScrollingEnabled(false);
        mTvLikesData = getActivity().findViewById(R.id.txtCmtData);
        mEdtCmt = getActivity().findViewById(R.id.edtCfPost);
        mBtnCmt = getActivity().findViewById(R.id.btnCfSend);
        mLlAddCmt = getActivity().findViewById(R.id.llAddCmt);
        mllCuCall = getActivity().findViewById(R.id.llCuCall);
        mllCuCall.setVisibility(View.VISIBLE);

        mIvProfilePic = getActivity().findViewById(R.id.imgCf);
        if (User.getUser(getContext()).getPicPath() != null) {
            Picasso.with(getContext())
                    .load(AppConstants.URL + User.getUser(getContext()).getPicPath().replaceAll("\\s", "%20"))
                    .resize(200, 200)
                    .error(R.color.light2)
                    .placeholder(R.color.light)
                    .into(mIvProfilePic);
        }


        if (getArguments() != null) {
            if (getArguments().getString(AppConstants.COMMENT_ID) != null) {
                commentId = getArguments().getString(AppConstants.COMMENT_ID);
                toAddComment(getArguments().getString("Data"));
                checkInternetConnection();
            } else {
                CommonFunctions.toDisplayToast("No Reply", getContext());
            }
        } else {
            CommonFunctions.toDisplayToast("No Reply", getContext());
        }

        mEdtCmt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    toSubmitCommon();
                }
                return false;
            }
        });

        mBtnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSubmitCommon();
            }
        });
    }

    private void toAddComment(String data) {
        try {
            JSONObject obj = new JSONObject(data);
            CircleImageView mIvCu;
            TextView mTvCuName, mTvCuCmt, mTvCuTime, mTvCuLike, mTvCuReply;

            mIvCu = getActivity().findViewById(R.id.imgCu);
            if (!obj.isNull(getContext().getString(R.string.str_profile_pic))) {
                if (obj.getString(getContext().getString(R.string.str_profile_pic)).toLowerCase().contains("upload")) {
                    Picasso.with(getContext()).load(AppConstants.URL + obj.getString(getContext().getString(R.string.str_profile_pic))).placeholder(R.color.light).error(R.color.light2).into(mIvCu);
                } else {
                    Picasso.with(getContext()).load(obj.getString(getContext().getString(R.string.str_profile_pic))).placeholder(R.color.light).error(R.color.light2).into(mIvCu);
                }
            }

            mTvCuName = getActivity().findViewById(R.id.txtCuName);
            mTvCuName.setTypeface(HmFonts.getRobotoBold(getContext()));
            if (!obj.isNull(getContext().getString(R.string.str_username_))) {
                mTvCuName.setText(obj.getString(getContext().getString(R.string.str_username_)));
            }

            mTvCuCmt = getActivity().findViewById(R.id.txtCuCmt);
            mTvCuCmt.setTypeface(HmFonts.getRobotoRegular(getContext()));
            if (!obj.isNull(getContext().getString(R.string.str_comment_small))) {
                mTvCuCmt.setText(obj.getString(getContext().getString(R.string.str_comment_small)));
            }


            mTvCuTime = getActivity().findViewById(R.id.txtCuTime);
            mTvCuTime.setTypeface(HmFonts.getRobotoRegular(getContext()));
            if (!obj.isNull(getContext().getString(R.string.str_time_small))) {
                mTvCuTime.setText(CommonFunctions.toSetDate(obj.getString(getContext().getString(R.string.str_time_small))));
            }

            mTvCuLike = getActivity().findViewById(R.id.txtCuLike);
            mTvCuLike.setTypeface(HmFonts.getRobotoRegular(getContext()));
            if (!obj.isNull(getContext().getString(R.string.str_like_count))) {
                mTvCuLike.setText(obj.getString(getContext().getString(R.string.str_like_count)) + " " + getContext().getString(R.string.str_like));
            }

            mTvCuReply = getActivity().findViewById(R.id.txtCuReply);
            mTvCuReply.setTypeface(HmFonts.getRobotoRegular(getContext()));
            if (!obj.isNull(getContext().getString(R.string.str_reply_count))) {
                mTvCuReply.setText(obj.getString(getContext().getString(R.string.str_reply_count)) + " " + getContext().getString(R.string.str_reply));
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }


    private void toSubmitCommon() {
        try {
            if (mEdtCmt.getText().toString().trim().length() > 0) {
                MyPost.toReplyOnComment(getContext(), commentId, mEdtCmt.getText().toString().trim());
                toDisplayReply();
                mEdtCmt.setText("");
            } else {
                CommonFunctions.toDisplayToast("Empty", getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public void toDisplayReply() {
        try {
            final View view = LayoutInflater.from(getContext()).inflate(R.layout.rv_layout, null, false);
            if (view != null) {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getContext().getString(R.string.str_like_share_comment) + getContext().getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "Reply Res " + response);
                                                    JSONArray array = new JSONArray(response);
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                            params.setMargins(80, 0, 0, 0);
                                                            mRv.setLayoutParams(params);
                                                            mRv.setLayoutManager(new LinearLayoutManager(getContext()));
                                                            mRv.hasFixedSize();
                                                            mRv.setNestedScrollingEnabled(false);
                                                            mRv.setAdapter(new DisplayReplyAdapter(getContext(), array));
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Reply", getContext());
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Reply", getContext());
                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                error.printStackTrace();
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getContext().getString(R.string.str_action_), getContext().getString(R.string.str_fetch_reply_comment_));
                                        params.put(getContext().getString(R.string.str_comment_id), commentId);
                                        return params;
                                    }
                                }
                                , getContext().getString(R.string.str_fetch_reply_comment_));
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}