package com.hm.application.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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
import com.hm.application.adapter.DisplayReplyAdapter;
import com.hm.application.classes.Post;
import com.hm.application.common.MyPost;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.newtry.models.Comment;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity {

    private LinearLayout mLlAddCmt;
    //
    private ImageView mIvProfilePic;
    private Button mBtnCmt;
    private EditText mEdtCmt;
    //
//    private TextView mTvCuReply;
//    private LinearLayout mllAddReply;
    //
    private String timelineId = "101", commentId = "190";//null;

    ///
    private RelativeLayout mRlCuMain, mRlCuInner;
    private LinearLayout mLlCuData, mLlCuReply;
    private ImageView mIvCu;
    private TextView mTvCuName, mTvCuCmt, mTvCuTime, mTvCuLike, mTvCuReply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        try {
            toBindViews();
            checkInternetConnection();
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toBindViews() throws Exception, Error {

        mLlAddCmt = findViewById(R.id.llAddCmt);

        mIvProfilePic = findViewById(R.id.imgCf);
        mEdtCmt = findViewById(R.id.edtCfPost);
        mBtnCmt = findViewById(R.id.btnCfSend);

        if (User.getUser(CommentActivity.this).getPicPath() != null) {
            Picasso.with(CommentActivity.this)
                    .load(AppConstants.URL + User.getUser(CommentActivity.this).getPicPath().replaceAll("\\s", "%20"))
                    .resize(200, 200)
                    .error(R.color.light2)
                    .placeholder(R.color.light)
                    .into(mIvProfilePic);
        }

        mEdtCmt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d("HmAPp", " cf " + commentId);
                    if (commentId != null) {
                        toSubmitReply();
                    } else {
                        toSubmitComment();
                    }
                }
                return false;
            }
        });

        mBtnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HmAPp", " cf " + commentId);
                if (commentId != null) {
                    toSubmitReply();
                } else {
                    toSubmitComment();
                }
            }
        });


    }

    private void checkInternetConnection() throws Exception, Error {
        if (CommonFunctions.isOnline(CommentActivity.this)) {
            new toDisplayComments().execute();
        } else {
            CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), CommentActivity.this);
        }
    }

    private class toDisplayComments extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(CommentActivity.this)
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
                                                            toDisplayCommentData(array);

                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Comment", CommentActivity.this);
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Comments", CommentActivity.this);
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

    private void toDisplayCommentData(final JSONArray array) throws Exception, Error {
        if (mLlAddCmt.getChildCount() > 0) {
            mLlAddCmt.removeAllViews();
        }
        for (int i = 0; i < array.length(); i++) {
            View itemView = LayoutInflater.from(CommentActivity.this).inflate(R.layout.comment_user, null, false);
            if (itemView != null) {


                // binding
                mRlCuMain = itemView.findViewById(R.id.rlCuMain);
                mRlCuInner = itemView.findViewById(R.id.rlCuInner);
                mLlCuData = itemView.findViewById(R.id.llCuData);
                mLlCuReply = itemView.findViewById(R.id.llCmtReplyData);

                mIvCu = itemView.findViewById(R.id.imgCu);

                mTvCuName = itemView.findViewById(R.id.txtCuName);
                mTvCuName.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

                mTvCuCmt = itemView.findViewById(R.id.txtCuCmt);
                mTvCuCmt.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

                mTvCuTime = itemView.findViewById(R.id.txtCuTime);
                mTvCuTime.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

                mTvCuLike = itemView.findViewById(R.id.txtCuLike);
                mTvCuLike.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

                mTvCuReply = itemView.findViewById(R.id.txtCuReply);
                mTvCuReply.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));


                if (!array.getJSONObject(i).isNull(getString(R.string.str_comment_small))) {
                    mTvCuCmt.setText(array.getJSONObject(i).getString(getString(R.string.str_comment_small)));
                }

                if (!array.getJSONObject(i).isNull(getString(R.string.str_username_))) {
                    mTvCuName.setText(CommonFunctions.firstLetterCaps(array.getJSONObject(i).getString(getString(R.string.str_username_))));
                }

                if (!array.getJSONObject(i).isNull(getString(R.string.str_time_small))) {
                    mTvCuTime.setText(CommonFunctions.toSetDate(array.getJSONObject(i).getString(getString(R.string.str_time_small))));
                }

                if (!array.getJSONObject(i).isNull(getString(R.string.str_like_count))) {
                    mTvCuLike.setText(array.getJSONObject(i).getString(getString(R.string.str_like_count)) + " " + getString(R.string.str_like));
                }

                if (!array.getJSONObject(i).isNull(getString(R.string.str_reply_count))) {
                    mTvCuReply.setText(array.getJSONObject(i).getString(getString(R.string.str_reply_count)) + " " + getString(R.string.str_reply));
                }

                if (!array.getJSONObject(i).isNull(getString(R.string.str_profile_pic))) {
                    if (array.getJSONObject(i).getString(getString(R.string.str_profile_pic)).toLowerCase().contains("upload")) {
                        Picasso.with(CommentActivity.this).load(AppConstants.URL + array.getJSONObject(i).getString(getString(R.string.str_profile_pic))).placeholder(R.color.light).error(R.color.light2).into(mIvCu);
                    } else {
                        Picasso.with(CommentActivity.this).load(array.getJSONObject(i).getString(getString(R.string.str_profile_pic))).placeholder(R.color.light).error(R.color.light2).into(mIvCu);
                    }
                }

                final int finalI = i;
                mRlCuInner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            startActivity(new Intent(CommentActivity.this, UserInfoActivity.class).
                                    putExtra(AppConstants.F_UID, array.getJSONObject(finalI).getString("uid")));
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                            FirebaseCrash.report(e);
                        }
                    }
                });

                mTvCuLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MyPost.toLikeComment(CommentActivity.this, array.getJSONObject(finalI).getString(getString(R.string.str_id)), mTvCuLike);
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                            FirebaseCrash.report(e);
                        }
                    }
                });

                mTvCuReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (!array.getJSONObject(finalI).isNull(getString(R.string.str_reply_count))) {
                                if (array.getJSONObject(finalI).getInt(getString(R.string.str_reply_count)) > 0) {
                                    if (mLlCuReply.getChildCount() == 0) {
                                        toDisplayReply(array.getJSONObject(finalI).getString(getString(R.string.str_id)), mLlCuReply, CommentActivity.this);
                                    } else {
                                        toDisplayReply(array.getJSONObject(finalI).getString(getString(R.string.str_id)), mLlCuReply, CommentActivity.this);
                                        setReply(mTvCuReply, array.getJSONObject(finalI).getString(getString(R.string.str_id)), array.getJSONObject(finalI).getString(getString(R.string.str_username_)));
                                    }
                                } else {
                                    setReply(mTvCuReply, array.getJSONObject(finalI).getString(getString(R.string.str_id)), array.getJSONObject(finalI).getString(getString(R.string.str_username_)));
                                    toDisplayReply(array.getJSONObject(finalI).getString(getString(R.string.str_id)), mLlCuReply, CommentActivity.this);
                                }
                            } else {
                                setReply(mTvCuReply, array.getJSONObject(finalI).getString(getString(R.string.str_id)), array.getJSONObject(finalI).getString(getString(R.string.str_username_)));
                                toDisplayReply(array.getJSONObject(finalI).getString(getString(R.string.str_id)), mLlCuReply, CommentActivity.this);
                            }
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                            FirebaseCrash.report(e);
                        }
                    }
                });

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.llCuData);
                params.addRule(RelativeLayout.RIGHT_OF, R.id.imgCu);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)

                {
                    params.addRule(RelativeLayout.END_OF, R.id.imgCu);
                }
                mLlCuReply.setLayoutParams(params);
                mLlCuReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            setReply(mTvCuReply, array.getJSONObject(finalI).getString(getString(R.string.str_id)), array.getJSONObject(finalI).getString(getString(R.string.str_username_)));
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });

                mLlAddCmt.addView(itemView);
            }
        }
    }

    public void toDisplayReply(final String id, final LinearLayout mLlCuReply, final Context context) {
        try {
            if (mLlCuReply.getChildCount() > 0) {
                mLlCuReply.removeAllViews();
            }
            final View view = LayoutInflater.from(CommentActivity.this).inflate(R.layout.rv_layout, null, false);
            if (view != null) {
                VolleySingleton.getInstance(CommentActivity.this)
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + context.getString(R.string.str_like_share_comment) + context.getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "Reply Res " + response);
                                                    JSONArray array = new JSONArray(response);
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            toDisplayReplyData(array, mLlCuReply);
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Reply", context);
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Reply", context);
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
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(context.getString(R.string.str_action_), context.getString(R.string.str_fetch_reply_comment_));
                                        params.put(context.getString(R.string.str_comment_id), id);
                                        return params;
                                    }
                                }
                                , context.getString(R.string.str_fetch_reply_comment_));
                mLlCuReply.addView(view);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void toDisplayReplyData(JSONArray array, LinearLayout mLlCuReply) throws Exception, Error {
        for (int i = 0; i < array.length(); i++) {
            View itemView = LayoutInflater.from(CommentActivity.this).inflate(R.layout.comment_user, null, false);

            mRlCuMain = itemView.findViewById(R.id.rlCuMain);
            mRlCuInner = itemView.findViewById(R.id.rlCuInner);
            mLlCuData = itemView.findViewById(R.id.llCuData);
            mLlCuReply = itemView.findViewById(R.id.llCmtReplyData);

            mIvCu = itemView.findViewById(R.id.imgCu);

            mTvCuName = itemView.findViewById(R.id.txtCuName);
            mTvCuName.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

            mTvCuCmt = itemView.findViewById(R.id.txtCuCmt);
            mTvCuCmt.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

            mTvCuTime = itemView.findViewById(R.id.txtCuTime);
            mTvCuTime.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

            mTvCuLike = itemView.findViewById(R.id.txtCuLike);
            mTvCuLike.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

            mTvCuReply = itemView.findViewById(R.id.txtCuReply);
            mTvCuReply.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));
            mTvCuReply.setVisibility(View.GONE);


            //[{"id":"12","comment_id":"12","uid":"15","reply":"thanks","timestamp":""}]
            //{"id":"70","comment_id":"107","uid":"2","reply":"ddd","like_count":"0","time":"2018-04-03 06:35:43","username":"akshipta","profile_pic":"uploads\/2\/profile_pics\/_2p_2879ad42dec8375e_HMG1522326768780.jpg"}
            if (!array.getJSONObject(i).isNull(getString(R.string.str_reply_small))) {
                mTvCuCmt.setText(array.getJSONObject(i).getString(getString(R.string.str_reply_small)));
            }

            if (!array.getJSONObject(i).isNull(getString(R.string.str_username_))) {
                mTvCuName.setText(CommonFunctions.firstLetterCaps(array.getJSONObject(i).getString(getString(R.string.str_username_))));
            }

            if (!array.getJSONObject(i).isNull(getString(R.string.str_time_small))) {
                mTvCuTime.setText(CommonFunctions.toSetDate(array.getJSONObject(i).getString(getString(R.string.str_time_small))));
            }

            if (!array.getJSONObject(i).isNull(getString(R.string.str_like_count))) {
                mTvCuLike.setText(array.getJSONObject(i).getString(getString(R.string.str_like_count)) + " " + getString(R.string.str_like));
            }

            if (!array.getJSONObject(i).isNull(getString(R.string.str_reply_count))) {
                mTvCuReply.setText(array.getJSONObject(i).getString(getString(R.string.str_reply_count)) + " " + getString(R.string.str_reply));
            } else {
                mTvCuReply.setVisibility(View.GONE);
            }

            if (!array.getJSONObject(i).isNull(getString(R.string.str_profile_pic))) {
                if (array.getJSONObject(i).getString(getString(R.string.str_profile_pic)).toLowerCase().contains(getString(R.string.str_upload))) {
                    Picasso.with(CommentActivity.this).load(AppConstants.URL + array.getJSONObject(i).getString(getString(R.string.str_profile_pic)))
                            .error(R.color.light2).placeholder(R.color.light).into(mIvCu);
                } else {
                    Picasso.with(CommentActivity.this).load(
                            array.getJSONObject(i).getString(getString(R.string.str_profile_pic)))
                            .error(R.color.light2).placeholder(R.color.light).into(mIvCu);
                }
            }
            mLlCuReply.addView(itemView);
        }
    }

    private void toSubmitComment() {
        try {
            if (mEdtCmt.getText().toString().trim().length() > 0) {
                MyPost.toCommentOnPost(CommentActivity.this, timelineId, mEdtCmt.getText().toString().trim(), mLlAddCmt);
                toAddComment(mEdtCmt.getText().toString().trim(), mLlAddCmt);
                mEdtCmt.setText("");
            } else {
                CommonFunctions.toDisplayToast("Empty", CommentActivity.this);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toSubmitReply() {
        try {
            if (mEdtCmt.getText().toString().trim().length() > 0) {
                MyPost.toReplyOnComment(CommentActivity.this, commentId, mEdtCmt.getText().toString().trim(), mTvCuReply);
//                if (mllAddReply != null) {
//                    toAddReply(mEdtCmt.getText().toString().trim(), mllAddReply);
//                }
                mEdtCmt.setText("");
            } else {
                CommonFunctions.toDisplayToast("Empty", CommentActivity.this);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toAddComment(String data, LinearLayout mLlAddCmt) {
        try {
            View itemView = LayoutInflater.from(CommentActivity.this).inflate(R.layout.comment_user, null);
            if (itemView != null) {
                Context context = CommentActivity.this;
                CircleImageView mIvCu;
                TextView mTvCuName, mTvCuCmt, mTvCuTime, mTvCuLike, mTvCuReply;

                mIvCu = itemView.findViewById(R.id.imgCu);
                Picasso.with(CommentActivity.this).load(AppConstants.URL + User.getUser(CommentActivity.this).getPicPath().replaceAll("\\s", "%20"))
                        .error(R.color.light2)
                        .placeholder(R.color.light)
                        .into(mIvCu);

                mTvCuName = itemView.findViewById(R.id.txtCuName);
                mTvCuName.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));
                mTvCuName.setText(User.getUser(CommentActivity.this).getUsername());

                mTvCuCmt = itemView.findViewById(R.id.txtCuCmt);
                mTvCuCmt.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));
                mTvCuCmt.setText(data);

                mTvCuTime = itemView.findViewById(R.id.txtCuTime);
                mTvCuTime.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

                mTvCuLike = itemView.findViewById(R.id.txtCuLike);
                mTvCuLike.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));
                mTvCuLike.setText("0 " + getString(R.string.str_like));

                mTvCuReply = itemView.findViewById(R.id.txtCuReply);
                mTvCuReply.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

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
            View itemView = LayoutInflater.from(CommentActivity.this).inflate(R.layout.comment_user, null);
            if (itemView != null) {
                Context context = CommentActivity.this;
                CircleImageView mIvCu;
                TextView mTvCuName, mTvCuCmt, mTvCuTime, mTvCuLike, mTvCuReply;

                mIvCu = itemView.findViewById(R.id.imgCu);
                Picasso.with(CommentActivity.this).load(AppConstants.URL + User.getUser(CommentActivity.this).getPicPath().replaceAll("\\s", "%20"))
                        .error(R.color.light2)
                        .placeholder(R.color.light)
                        .into(mIvCu);

                mTvCuName = itemView.findViewById(R.id.txtCuName);
                mTvCuName.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));
                mTvCuName.setText(User.getUser(CommentActivity.this).getUsername());

                mTvCuCmt = itemView.findViewById(R.id.txtCuCmt);
                mTvCuCmt.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));
                mTvCuCmt.setText(data);

                mTvCuTime = itemView.findViewById(R.id.txtCuTime);
                mTvCuTime.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

                mTvCuLike = itemView.findViewById(R.id.txtCuLike);
                mTvCuLike.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));
                mTvCuLike.setText("0 " + getString(R.string.str_like));

                mTvCuReply = itemView.findViewById(R.id.txtCuReply);
                mTvCuReply.setTypeface(HmFonts.getRobotoRegular(CommentActivity.this));

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
}
