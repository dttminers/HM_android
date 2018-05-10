package com.hm.application.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.adapter.SlidingImageAdapter;
import com.hm.application.classes.Post;
import com.hm.application.common.MyPost;
import com.hm.application.fragments.CommentFragment;
import com.hm.application.fragments.TimelineLikeListFragment;
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

public class SinglePostDataActivity extends AppCompatActivity {

    private RelativeLayout mrr_header_file, mRrVpMain;
    private CircleImageView mcircle_img;
    private TextView mtxt_label, mtxt_time_ago, mtxtSpdPost, mTvTimeLineId;
    private LinearLayout mLlSpdMain, mLlMainVpPost;
    private TextView mtxtNo_like, mtxtNo_comment, mTvUserLikeName;
    private FrameLayout mFlSpdHome;
    private NestedScrollView mNsvSpdPost;
    private CheckBox mChkPostLiked, mChkLike;

    private LinearLayout mll_footer;
    private ImageView mImgComment, mImgShare;
    private ViewPager mVp;
    private TabLayout mTl;

    // Comments
    private RecyclerView mRvCmt;
    private Button mBtnCmt;
    private LinearLayout mLlAddCmt, mllAddReply;
    private ImageView mIvProfilePic;
    private EditText mEdtCmt;
    private TextView mTvCuReply;


    private String timelineId = null, commentId = null;
    private JSONObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post_data);
        try {
            toSetTitle();
            bindViews();
            toSetData();
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    private void toSetTitle() throws Error {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_back_black_24dp));
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        }
    }

    private void bindViews() throws Error {
        mNsvSpdPost = findViewById(R.id.nsvSpdPost);
        mFlSpdHome = findViewById(R.id.flSpdHome);

        mLlSpdMain = findViewById(R.id.llSpdMain);
        mLlMainVpPost = findViewById(R.id.llMainVpPost);

        mrr_header_file = findViewById(R.id.rrHeaderMain);
        mRrVpMain = findViewById(R.id.rrVpMain);

        mcircle_img = findViewById(R.id.circle_img);
        mtxt_label = findViewById(R.id.txt_label);

        mtxt_time_ago = findViewById(R.id.txt_time_ago);
        mtxt_time_ago.setTypeface(HmFonts.getRobotoRegular(SinglePostDataActivity.this));

        mtxtSpdPost = findViewById(R.id.txtSpdPost);
        mtxtSpdPost.setTypeface(HmFonts.getRobotoRegular(SinglePostDataActivity.this));

        mTvTimeLineId = findViewById(R.id.tvTimelineId);

        mChkPostLiked = findViewById(R.id.chkPostLiked);
        mChkLike = findViewById(R.id.chkLike);

        mtxtNo_like = findViewById(R.id.txtNo_like);
        mtxtNo_comment = findViewById(R.id.txtNo_comment);

//        mTvUserLikeName = findViewById(R.id.tvUserLikeName);

        mll_footer = findViewById(R.id.llSdpFooter);

        mImgComment = findViewById(R.id.imgComment);
        mImgShare = findViewById(R.id.imgShare);

        mVp = findViewById(R.id.vpMainPost);
        mTl = findViewById(R.id.tlMainPost);


        mLlAddCmt = findViewById(R.id.llAddCmtSPD);
        mRvCmt = findViewById(R.id.rvCommentsSPD);
        mRvCmt.setNestedScrollingEnabled(false);
        mEdtCmt = findViewById(R.id.edtCfPost);
        mBtnCmt = findViewById(R.id.btnCfSend);
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
                Log.d("hmapp", " comment submit spd: " + mEdtCmt.getText());
                toSetDataSending();
            }
        });

        mIvProfilePic = findViewById(R.id.imgCf);
        if (User.getUser(this).getPicPath() != null) {
            Picasso.with(this)
                    .load(AppConstants.URL + User.getUser(this).getPicPath().replaceAll("\\s", "%20"))
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
            Crashlytics.logException(e);

        }
    }

    private void toSubmitReply() {
        try {
            if (mEdtCmt.getText().toString().trim().length() > 0) {
                MyPost.toReplyOnComment(this, commentId, mEdtCmt.getText().toString().trim(), mTvCuReply);
                if (mllAddReply != null) {
                    toAddReply(mEdtCmt.getText().toString().trim(), mllAddReply);
                }
                mEdtCmt.setText("");
            } else {
                CommonFunctions.toDisplayToast("Empty", this);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    private void toSubmitComment() {
        try {
            if (mEdtCmt.getText().toString().trim().length() > 0) {
                MyPost.toCommentOnPost(this, timelineId, mEdtCmt.getText().toString().trim(), mLlAddCmt);
                toAddComment(mEdtCmt.getText().toString().trim(), mLlAddCmt);
                mEdtCmt.setText("");
            } else {
                CommonFunctions.toDisplayToast("Empty", this);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    private void toAddComment(String data, LinearLayout mLlAddCmt) {
        try {
            View itemView = LayoutInflater.from(this).inflate(R.layout.comment_user, null);
            if (itemView != null) {
                Context context = this;
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
//                new toDisplayComments().execute();
//                toDisplayComments();
                Post.toDisplayComments(timelineId, mRvCmt, this, mLlAddCmt);
                mLlAddCmt.requestFocus();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    private void toAddReply(String data, LinearLayout mLlAddCmt) {
        try {
            View itemView = LayoutInflater.from(this).inflate(R.layout.comment_user, null);
            if (itemView != null) {
                Context context = this;
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
//                new toDisplayComments().execute();
//                toDisplayComments();
                mLlAddCmt.requestFocus();
                Post.toDisplayComments(timelineId, mRvCmt, this, mLlAddCmt);
                mLlAddCmt.requestFocus();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    private void toSetData() {
        try {
            if (getIntent() != null) {
                Log.d("HmApp", " singlePostData : " + getIntent());
                if (getIntent().getStringExtra(AppConstants.BUNDLE) != null) {
                    obj = new JSONObject(getIntent().getStringExtra(AppConstants.BUNDLE));
                    if (getIntent().getStringExtra(AppConstants.TIMELINE_ID) != null) {
                        timelineId = getIntent().getStringExtra(AppConstants.TIMELINE_ID);
                    } else {
                        timelineId = obj.getString("timeline_id");
                    }
                    Log.d("HmApp", " SinglePost Timeline 1 " + timelineId);
                    toDisplayData(obj);
                } else if (getIntent().getStringExtra(AppConstants.TIMELINE_ID) != null) {
                    timelineId = getIntent().getStringExtra(AppConstants.TIMELINE_ID);
                    Log.d("HmApp", " SinglePost Timeline 2 " + timelineId);
                    if (CommonFunctions.isOnline(SinglePostDataActivity.this)) {
                        toDisplayNotificationData();
                    } else {
                        CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), SinglePostDataActivity.this);
                    }
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    private void toCallCommentUi() {
        try {
            Bundle bundle = new Bundle();
            Log.d("hmapp", " Spd Timeline1" + timelineId);
            bundle.putString(AppConstants.TIMELINE_ID, obj.getString(getString(R.string.str_timeline_id_)));
            Log.d("hmapp", " Spd Timeline2 " + timelineId);
            CommentFragment cm = new CommentFragment();
            cm.setArguments(bundle);
            replaceMainHomePage(cm);
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }

    public void replaceMainHomePage(Fragment fragment) {
        Log.d("Hmapp", " agr replaceTabData 1 bundle  " + fragment.getArguments());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flSpdHome, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toDisplayData(final JSONObject obj) throws Exception, Error {
        Log.d("hmapp", " data " + obj);
        if (!obj.isNull(getString(R.string.str_username_))) {
            mtxt_label.setText(CommonFunctions.firstLetterCaps(obj.getString(getString(R.string.str_username_))));
        } else {
            mtxt_label.setText(CommonFunctions.firstLetterCaps(User.getUser(SinglePostDataActivity.this).getUsername()));
        }
        if (!obj.isNull(getString(R.string.str_post_small)) && obj.getString(getString(R.string.str_post_small)).trim().length() > 0) {
            mtxtSpdPost.setVisibility(View.VISIBLE);
            mtxtSpdPost.setText(obj.getString(getString(R.string.str_post_small)));
        } else if (!obj.isNull(getString(R.string.str_caption)) && obj.getString(getString(R.string.str_caption)).trim().length() > 0) {
            mtxtSpdPost.setVisibility(View.VISIBLE);
            mtxtSpdPost.setText(obj.getString(getString(R.string.str_caption)));
        } else if (!obj.isNull(getString(R.string.str_post_data)) && obj.getString(getString(R.string.str_post_data)).trim().length() > 0) {
            mtxtSpdPost.setVisibility(View.VISIBLE);
            mtxtSpdPost.setText(obj.getString(getString(R.string.str_post_data)));
        } else {
            mtxtSpdPost.setVisibility(View.GONE);
        }

        if (!obj.isNull(getString(R.string.str_time))) {
            mtxt_time_ago.setText(CommonFunctions.toSetDate(obj.getString(getString(R.string.str_time))));
        }
        if (!obj.isNull(getString(R.string.str_timeline_id_))) {
            mTvTimeLineId.setText(obj.getString(getString(R.string.str_timeline_id_)));
        }
//        if (!obj.isNull(getString(R.string.str_timeline_id_))) {
//            mTvUserLikeName.setText(obj.getString(getString(R.string.str_friend_like)));
//        }
        if (!obj.isNull(getString(R.string.str_like_count)))
            if (!obj.isNull(getString(R.string.str_friend_like))) {
                mtxtNo_like.setText(obj.getString(getString(R.string.str_friend_like)) + " and " + obj.getString(getString(R.string.str_like_count)) + " others");
            }
        if (!obj.isNull(getString(R.string.str_comment_count))) {
            mtxtNo_comment.setText(obj.getString(getString(R.string.str_comment_count)) + " " + getString(R.string.str_comment));
        }
//        if (!obj.isNull(getString(R.string.str_share_count))) {
//            mtxtNo_share.setText(obj.getString(getString(R.string.str_share_count)) + " " + getResources().getString(R.string.str_share));
//        }
        if (User.getUser(SinglePostDataActivity.this).getPicPath() != null) {
            Picasso.with(SinglePostDataActivity.this)
                    .load(AppConstants.URL + User.getUser(SinglePostDataActivity.this).getPicPath().replaceAll("\\s", "%20"))
                    .error(R.color.light2)
                    .placeholder(R.color.light)
                    .resize(100, 100)
                    .into(mcircle_img);
        }

        if (!obj.isNull(getString(R.string.str_image_url))) {
            mVp.setAdapter(
                    new SlidingImageAdapter(
                            SinglePostDataActivity.this,
                            obj.getString(getString(R.string.str_image_url)).split(","),
                            null,
                            null,
                            mTl)
            );
            if (obj.getString(getString(R.string.str_image_url)).split(",").length > 1) {
                mTl.setupWithViewPager(mVp);
            } else {
                mTl.setVisibility(View.GONE);
            }
        } else if (!obj.isNull(getString(R.string.str_image))) {
            mVp.setAdapter(
                    new SlidingImageAdapter(
                            SinglePostDataActivity.this,
                            obj.getString(getString(R.string.str_image)).split(","),
                            null,
                            null,
                            mTl)
            );
            mTl.setupWithViewPager(mVp);
        } else {
            mVp.setVisibility(View.GONE);
            mTl.setVisibility(View.GONE);
        }

        mrr_header_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(
                            new Intent(SinglePostDataActivity.this, UserInfoActivity.class)
                                    .putExtra(AppConstants.F_UID, obj.getString("Uid")));
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);

                }
            }
        });

        mImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CommonFunctions
                            .toShareData(SinglePostDataActivity.this,
                                    getString(R.string.app_name),
                                    obj.getString(getString(R.string.str_caption)),
                                    obj.getString(getString(R.string.str_timeline_id_)), null);
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);

                }
            }
        });

        mChkLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyPost.toLikeUnlikePost(SinglePostDataActivity.this, obj.getString(getString(R.string.str_timeline_id_)), null, null, mChkPostLiked, mChkLike);
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);

                }
            }
        });

        mtxtNo_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.TIMELINE_ID, obj.getString(getString(R.string.str_timeline_id_)));
                    TimelineLikeListFragment time = new TimelineLikeListFragment();
                    time.setArguments(bundle);
                    replaceMainHomePage(time);
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);

                }
            }
        });

        mImgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCallCommentUi();
            }
        });

        mtxtNo_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCallCommentUi();
            }
        });
        // toDisplay comments Below
//        Bundle bundle = new Bundle();
//        bundle.putString(AppConstants.TIMELINE_ID, timelineId);
//        CommentFragment cm = new CommentFragment();
//        cm.setArguments(bundle);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.flSpdComment, cm)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .commit();
        Post.toDisplayComments(timelineId, mRvCmt, this, mLlAddCmt);
    }

    public void toDisplayNotificationData() {
        try {
            VolleySingleton.getInstance(SinglePostDataActivity.this)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST,
                                    AppConstants.URL + getString(R.string.str_notification_uid) + getString(R.string.str_php),
                                    new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Log.d("HmApp", "notification:" + response);
                                                if (response != null) {
                                                    JSONArray array = new JSONArray(response.trim());
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            if (!array.isNull(0)) {
                                                                obj = array.getJSONObject(0);
                                                                toDisplayData(obj);
                                                            }
                                                        }

                                                    }
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                Crashlytics.logException(e);

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
                                    params.put(getString(R.string.str_action_), getString(R.string.str_notification_post));
                                    params.put(getString(R.string.str_timeline_id_), timelineId);
                                    Log.d("HmApp", "Notification timeline: " + timelineId);
                                    return params;
                                }
                            }
                            , getString(R.string.str_notification_post));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }
}