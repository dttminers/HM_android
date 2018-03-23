package com.hm.application.fragments;

import android.os.AsyncTask;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.adapter.DisplayCommentsAdapter;
import com.hm.application.common.MyPost;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class CommentFragment extends Fragment {

    private RecyclerView mRvCmt;
    private TextView mTvLikesData;
    private EditText mEdtCmt;
    private Button mBtnCmt;
    public boolean reply = false;
    public String commentId = null, timelineId = null;

    public CommentFragment() {
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
            new toDisplayComments().execute();
        } else {
            CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
        }
    }

    private void toBindViews() throws Exception, Error {
        mRvCmt = getActivity().findViewById(R.id.rvComments);
        mTvLikesData = getActivity().findViewById(R.id.txtCmtData);
        mEdtCmt = getActivity().findViewById(R.id.edtCmtPost);
        mBtnCmt = getActivity().findViewById(R.id.btnCmtSend);

        if (getArguments() != null) {
            if (getArguments().getString(AppConstants.TIMELINE_ID) != null) {
                timelineId = getArguments().getString(AppConstants.TIMELINE_ID);
            } else {
                super.onDestroy();
            }
        } else {
            super.onDestroy();
        }

        mBtnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdtCmt.getText().toString().trim().length() > 0) {
                    if (reply) {
                        MyPost.toReplyOnComment(getContext(), commentId, mEdtCmt.getText().toString().trim());
                    } else {
                        MyPost.toCommentOnPost(getContext(), timelineId, mEdtCmt.getText().toString().trim());
                    }
                } else {
                    CommonFunctions.toDisplayToast("Empty", getContext());
                }
            }
        });
    }

    private class toDisplayComments extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_like_share_comment) + "." + getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "Comment Res " + response);
                                                    JSONArray array = new JSONArray(response.trim());
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            mRvCmt.setLayoutManager(new LinearLayoutManager(getContext()));
                                                            mRvCmt.hasFixedSize();
                                                            mRvCmt.setAdapter(new DisplayCommentsAdapter(getContext(), array, CommentFragment.this));
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Comment", getContext());
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Comments", getContext());
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
                                        params.put(getString(R.string.str_action_), getString(R.string.str_fetch_comment_));
                                        params.put(getString(R.string.str_timeline_id_), "22");
                                        return params;
                                    }
                                }
                                , getString(R.string.str_fetch_comment_));
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}