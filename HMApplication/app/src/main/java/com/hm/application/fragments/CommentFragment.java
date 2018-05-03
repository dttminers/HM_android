package com.hm.application.fragments;

import android.content.Context;
import android.os.AsyncTask;
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
import com.google.firebase.crash.FirebaseCrash;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentFragment extends Fragment {

    private RecyclerView mRvCmt;
    private TextView mTvLikesData;
    private Button mBtnCmt;
    private LinearLayout mLlAddCmt, mllAddReply;
    private ImageView mIvProfilePic;
    private String timelineId = null, commentId = null;
    private EditText mEdtCmt;
    private TextView mTvCuReply;


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
            FirebaseCrash.report(e);
        }
    }

    private void checkInternetConnection() throws Error {
        if (CommonFunctions.isOnline(getContext())) {
            new toDisplayComments().execute();
        } else {
            CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
        }
    }

    private void toBindViews() throws Error {
        mRvCmt = getActivity().findViewById(R.id.rvComments);
        mRvCmt.setNestedScrollingEnabled(false);
        mTvLikesData = getActivity().findViewById(R.id.txtCmtData);
        mEdtCmt = getActivity().findViewById(R.id.edtCfPost);
        mBtnCmt = getActivity().findViewById(R.id.btnCfSend);
        mLlAddCmt = getActivity().findViewById(R.id.llAddCmt);

        if (getArguments() != null) {
            Log.d("HmApp", "Comment fragment getArguments: " + getArguments());
            if (getArguments().getString(AppConstants.TIMELINE_ID) != null) {
                Log.d("HmApp", "Comment fragment TIMELINE_ID: " + getArguments().getString(AppConstants.TIMELINE_ID));
                timelineId = getArguments().getString(AppConstants.TIMELINE_ID);
                Log.d("HmApp", "Comment fragment timelineId: " + timelineId);
            } else {
                CommonFunctions.toDisplayToast("No Commentssssssss", getContext());
            }
        } else {
            CommonFunctions.toDisplayToast("No Comment", getContext());
        }

        mEdtCmt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    toSetDataSending();
                }
                return false;
            }
        });

        mBtnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSetDataSending();
            }
        });

        mIvProfilePic = getActivity().findViewById(R.id.imgCf);
        if (User.getUser(getContext()).getPicPath() != null) {
            Picasso.with(getContext())
                    .load(AppConstants.URL + User.getUser(getContext()).getPicPath().replaceAll("\\s", "%20"))
                    .resize(200, 200)
                    .error(R.color.light2)
                    .placeholder(R.color.light)
                    .into(mIvProfilePic);
        }

    }

    private void toSetDataSending() {
        try {
            if (commentId != null) {
                toSubmitReply();
            } else {
                toSubmitComment();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void toSubmitReply() {
        try {
            if (mEdtCmt.getText().toString().trim().length() > 0) {
                MyPost.toReplyOnComment(getContext(), commentId, mEdtCmt.getText().toString().trim(), mTvCuReply);
                if (mllAddReply != null) {
                    toAddReply(mEdtCmt.getText().toString().trim(), mllAddReply);
                }
                mEdtCmt.setText("");
            } else {
                CommonFunctions.toDisplayToast("Empty", getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void toSubmitComment() {
        try {
            if (mEdtCmt.getText().toString().trim().length() > 0) {
                MyPost.toCommentOnPost(getContext(), timelineId, mEdtCmt.getText().toString().trim(), mLlAddCmt);
                toAddComment(mEdtCmt.getText().toString().trim(), mLlAddCmt);
                mEdtCmt.setText("");
            } else {
                CommonFunctions.toDisplayToast("Empty", getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void toAddComment(String data, LinearLayout mLlAddCmt) {
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
                mTvCuName.setTypeface(HmFonts.getRobotoRegular(context));
                mTvCuName.setText(User.getUser(context).getUsername());

                mTvCuCmt = itemView.findViewById(R.id.txtCuCmt);
                mTvCuCmt.setTypeface(HmFonts.getRobotoRegular(context));
                mTvCuCmt.setText(data);

                mTvCuTime = itemView.findViewById(R.id.txtCuTime);
                mTvCuTime.setTypeface(HmFonts.getRobotoRegular(context));

                mTvCuLike = itemView.findViewById(R.id.txtCuLike);
                mTvCuLike.setTypeface(HmFonts.getRobotoRegular(context));
                mTvCuLike.setText("0 " + context.getString(R.string.str_like));

                mTvCuReply = itemView.findViewById(R.id.txtCuReply);
                mTvCuReply.setTypeface(HmFonts.getRobotoRegular(context));

                mLlAddCmt.addView(itemView);
                new toDisplayComments().execute();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void toAddReply(String data, LinearLayout mLlAddCmt) {
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
                mTvCuName.setTypeface(HmFonts.getRobotoRegular(context));
                mTvCuName.setText(User.getUser(context).getUsername());

                mTvCuCmt = itemView.findViewById(R.id.txtCuCmt);
                mTvCuCmt.setTypeface(HmFonts.getRobotoRegular(context));
                mTvCuCmt.setText(data);

                mTvCuTime = itemView.findViewById(R.id.txtCuTime);
                mTvCuTime.setTypeface(HmFonts.getRobotoRegular(context));

                mTvCuLike = itemView.findViewById(R.id.txtCuLike);
                mTvCuLike.setTypeface(HmFonts.getRobotoRegular(context));
                mTvCuLike.setText("0 " + context.getString(R.string.str_like));

                mTvCuReply = itemView.findViewById(R.id.txtCuReply);
                mTvCuReply.setTypeface(HmFonts.getRobotoRegular(context));

                mLlAddCmt.addView(itemView);
                new toDisplayComments().execute();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    public void setReply(TextView mTvReply, String cmtId, String cmtUserName) {
        commentId = cmtId;
        mTvCuReply = mTvReply;
        mEdtCmt.setHint("Reply to " + cmtUserName);
        mEdtCmt.requestFocus();
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
                                                    Log.d("HmApp", "Comment Res: " + response);
                                                    JSONArray array = new JSONArray(response.trim());
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
                                                        mRvCmt.setAdapter(new DisplayCommentsAdapter(getContext(), array, CommentFragment.this));
                                                        mRvCmt.smoothScrollToPosition(array.length() - 1);

                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Comment", getContext());
                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                    FirebaseCrash.report(e);
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
//                                        params.put(getString(R.string.str_timeline_id_), "102");
                                        Log.d("hmapp", " comment fragment_timeline Api:" + timelineId);
                                        return params;
                                    }
                                }
                                , getString(R.string.str_fetch_comment_));
            } catch (Exception | Error e) {
                e.printStackTrace();
                FirebaseCrash.report(e);
            }
            return null;
        }
    }
}