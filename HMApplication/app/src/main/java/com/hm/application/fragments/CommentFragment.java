package com.hm.application.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.hm.application.adapter.DisplayCommentsAdapter;
import com.hm.application.common.MyPost;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentFragment extends Fragment {

    private RecyclerView mRvCmt;
    private TextView mTvLikesData;
    private EditText mEdtCmt;
    private Button mBtnCmt;
    private LinearLayout mLlAddCmt;
    private RelativeLayout mllCuCall;
    public String timelineId = null;

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
        mRvCmt.setNestedScrollingEnabled(false);
        mTvLikesData = getActivity().findViewById(R.id.txtCmtData);
        mEdtCmt = getActivity().findViewById(R.id.edtCmtPost);
        mBtnCmt = getActivity().findViewById(R.id.btnCmtSend);
        mLlAddCmt = getActivity().findViewById(R.id.llAddCmt);
        mllCuCall = getActivity().findViewById(R.id.llCuCall);
        mllCuCall.setVisibility(View.GONE);

        if (getArguments() != null) {
            if (getArguments().getString(AppConstants.TIMELINE_ID) != null) {
                timelineId = getArguments().getString(AppConstants.TIMELINE_ID);
            } else {
                CommonFunctions.toDisplayToast("No Comment", getContext());
            }
        } else {
            CommonFunctions.toDisplayToast("No Comment", getContext());
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

    private void toSubmitCommon() {
        try {
            if (mEdtCmt.getText().toString().trim().length() > 0) {
                MyPost.toCommentOnPost(getContext(), timelineId, mEdtCmt.getText().toString().trim(), mLlAddCmt);
                toAddComment(mEdtCmt.getText().toString().trim());
                mEdtCmt.setText("");
            } else {
                CommonFunctions.toDisplayToast("Empty", getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toAddComment(String data) {
        try {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.comment_user, null);
            if (itemView != null) {
                Context context = getContext();
                CircleImageView mIvCu;
                TextView mTvCuName, mTvCuCmt, mTvCuTime, mTvCuLike, mTvCuReply;

                mIvCu = itemView.findViewById(R.id.imgCu);
                Picasso.with(context).load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20"))
                        .error(R.color.light2)
                        .placeholder(R.color.light)
                        .into(mIvCu);

                mTvCuName = itemView.findViewById(R.id.txtCuName);
                mTvCuName.setTypeface(HmFonts.getRobotoBold(context));
                mTvCuName.setText(User.getUser(context).getUsername());

                mTvCuCmt = itemView.findViewById(R.id.txtCuCmt);
                mTvCuCmt.setTypeface(HmFonts.getRobotoRegular(context));
                mTvCuCmt.setText(data);

                mTvCuTime = itemView.findViewById(R.id.txtCuTime);
                mTvCuTime.setTypeface(HmFonts.getRobotoRegular(context));

                mTvCuLike = itemView.findViewById(R.id.txtCuLike);
                mTvCuLike.setTypeface(HmFonts.getRobotoMedium(context));
                mTvCuLike.setText("0 " + context.getString(R.string.str_like));

                mTvCuReply = itemView.findViewById(R.id.txtCuReply);
                mTvCuReply.setTypeface(HmFonts.getRobotoMedium(context));

                mLlAddCmt.addView(itemView);
                new toDisplayComments().execute();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class toDisplayComments extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_like_share_comment) + getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "Comment Res " + response);
                                                    JSONArray array = new JSONArray(response.trim());
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            if (mLlAddCmt.getChildCount() > 0) {
                                                                mLlAddCmt.removeAllViews();
                                                            }
                                                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
//                                                            llm.setReverseLayout(true);
                                                            llm.setStackFromEnd(true);
                                                            mRvCmt.setLayoutManager(llm);
                                                            mRvCmt.hasFixedSize();
                                                            mRvCmt.setNestedScrollingEnabled(false);
                                                            mRvCmt.setAdapter(new DisplayCommentsAdapter(getContext(), array, getActivity()));
                                                            mRvCmt.smoothScrollToPosition(array.length() - 1);

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
                                        Map<String, String> params = new HashMap<>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_fetch_comment_));
                                        params.put(getString(R.string.str_timeline_id_), timelineId);
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