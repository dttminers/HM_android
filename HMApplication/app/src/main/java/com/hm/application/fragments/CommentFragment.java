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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        mLlAddCmt = getActivity().findViewById(R.id.llAddCmt);

        Log.d("HmApp ", " Comment " + getArguments());
        if (getArguments() != null) {
            if (getArguments().getString(AppConstants.TIMELINE_ID) != null) {
                timelineId = getArguments().getString(AppConstants.TIMELINE_ID);
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
                    if (reply) {
                        MyPost.toReplyOnComment(getContext(), commentId, mEdtCmt.getText().toString().trim(), mLlAddCmt);
                        toAddComment(true, mEdtCmt.getText().toString().trim());
                    } else {
                        MyPost.toCommentOnPost(getContext(), timelineId, mEdtCmt.getText().toString().trim(), mLlAddCmt);
                        toAddComment(false, mEdtCmt.getText().toString().trim());
                    }
                } else {
                    CommonFunctions.toDisplayToast("Empty", getContext());
                }
            }
        });
    }

    private void toAddComment(boolean b, String data) {

        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.comment_user, null);
        if (itemView != null) {
            Context context = getContext();
            RelativeLayout mRlCuMain;
            LinearLayout mLlCuData, mLlCuReply;
            CircleImageView mIvCu;
            TextView mTvCuName, mTvCuCmt, mTvCuTime, mTvCuLike, mTvCuReply;
            mRlCuMain = itemView.findViewById(R.id.rrCuMain);
            mLlCuData = itemView.findViewById(R.id.llCuData);
            mLlCuReply = itemView.findViewById(R.id.llCmtReplyData);

            mIvCu = itemView.findViewById(R.id.imgCu);
            Picasso.with(context).load(User.getUser(context).getPicPath().replaceAll("\\s", "%20"))
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
            mTvCuLike.setTypeface(HmFonts.getRobotoRegular(context));
            mTvCuLike.setText("0 " + context.getString(R.string.str_like));

            mTvCuReply = itemView.findViewById(R.id.txtCuReply);
            mTvCuReply.setTypeface(HmFonts.getRobotoRegular(context));

            mTvCuReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    toGetReplyData(getAdapterPosition(), mLlCuReply);
                }
            });
            if (!b) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.llCuData);
                params.addRule(RelativeLayout.RIGHT_OF, R.id.imgCu);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.addRule(RelativeLayout.END_OF, R.id.imgCu);
                }
                mLlCuReply.setLayoutParams(params);
            }
            mLlAddCmt.addView(itemView);
            new toDisplayComments().execute();
        }

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