package com.hm.application.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.adapter.DisplayReplyAdapter;
import com.hm.application.common.MyPost;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;


public class ReplyToCommentFragment extends Fragment {

    private RecyclerView mRv;
    private TextView mTvLikesData;
    private EditText mEdtCmt;
    private Button mBtnCmt;
    private LinearLayout mLlAddCmt;
    public String commentId = null;

    public ReplyToCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            toBindViews();
            checkInternetConnection();
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

    private void toBindViews() throws Exception, Error {
        mRv = getActivity().findViewById(R.id.rvComments);
        mRv.setNestedScrollingEnabled(false);
        mTvLikesData = getActivity().findViewById(R.id.txtCmtData);
        mEdtCmt = getActivity().findViewById(R.id.edtCmtPost);
        mBtnCmt = getActivity().findViewById(R.id.btnCmtSend);
        mLlAddCmt = getActivity().findViewById(R.id.llAddCmt);

        if (getArguments() != null) {
            if (getArguments().getString(AppConstants.COMMENT_ID) != null) {
                commentId = getArguments().getString(AppConstants.COMMENT_ID);
            } else {
                CommonFunctions.toDisplayToast("No Comment", getContext());
            }
        } else {
            CommonFunctions.toDisplayToast("No Comment", getContext());
        }

        mBtnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdtCmt.getText().toString().trim().length() > 0) {
//                    MyPost.toReplyOnComment(getContext(), commentId, mEdtCmt.getText().toString().trim());
//                    toAddComment(mEdtCmt.getText().toString().trim());
                    mEdtCmt.setText("");
                } else {
                    CommonFunctions.toDisplayToast("Empty", getContext());
                }
            }
        });
    }

    public void toDisplayReply() {
        try {
            final View view = LayoutInflater.from(getContext()).inflate(R.layout.rv_layout, null, false);
            if (view != null) {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getContext().getString(R.string.str_like_share_comment) +  getContext().getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "Reply Res " + response);
                                                    JSONArray array = new JSONArray(response);
                                                    if (array != null) {
                                                        if (array.length() > 0) {
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
