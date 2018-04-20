package com.hm.application.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.adapter.SlidingImageAdapter;
import com.hm.application.common.MyPost;
import com.hm.application.fragments.CommentFragment;
import com.hm.application.fragments.TimelineLikeListFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SinglePostDataActivity extends AppCompatActivity {

    private RelativeLayout mrr_header_file;
    private CircleImageView mcircle_img;
    private TextView mtxt_label, mtxt_time_ago, mtxtSpdPost;

    private LinearLayout mllNumber_file;
    private TextView mtxtNo_like, mtxtNo_comment, mtxtNo_share;

    private LinearLayout mll_footer;
    private TextView mtxt_like, mtxt_comment, mtxt_share;

    private ViewPager mVp;
    private TabLayout mTl;

    private String timelineId = null;
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
            FirebaseCrash.report(e);
        }
    }

    private void toSetTitle() throws Exception, Error {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_back_black_24dp));
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
//            Spannable text = new SpannableString(getSupportActionBar().getTitle());
//            text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_pink3)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//            getSupportActionBar().setTitle(text);
        }
    }

    private void bindViews() throws Exception, Error {
        mrr_header_file = findViewById(R.id.rr_header_file);
        mcircle_img = findViewById(R.id.circle_img);
        mtxt_label = findViewById(R.id.txt_label);
        mtxt_time_ago = findViewById(R.id.txt_time_ago);
        mtxtSpdPost = findViewById(R.id.txtSpdPost);

        mllNumber_file = findViewById(R.id.llNumber_file);
        mtxtNo_like = findViewById(R.id.txtNo_like);
        mtxtNo_comment = findViewById(R.id.txtNo_comment);
        mtxtNo_share = findViewById(R.id.txtNo_share);

        mll_footer = findViewById(R.id.ll_footer);

        mtxt_like = findViewById(R.id.txt_like);
        mtxt_comment = findViewById(R.id.txt_comment);
        mtxt_share = findViewById(R.id.txt_share);

        mtxtNo_like.setText("0 " + getResources().getString(R.string.str_like));
        mtxtNo_comment.setText("0 " + getString(R.string.str_comment));
        mtxtNo_share.setText("0 " + getResources().getString(R.string.str_share));

        mVp = findViewById(R.id.vpHs2);
        mTl = findViewById(R.id.tlHs2);
    }

    private void toSetData() {
        try {
            if (getIntent() != null) {
                if (getIntent().getStringExtra(AppConstants.BUNDLE) != null) {
                    obj = new JSONObject(getIntent().getStringExtra(AppConstants.BUNDLE));
                    Log.d("HmApp", " SinglePost1 " + obj);
                    toDisplayData(obj);

                } else if (getIntent().getStringExtra(AppConstants.TIMELINE_ID) != null) {
                    timelineId = getIntent().getStringExtra(AppConstants.TIMELINE_ID);
                    if (CommonFunctions.isOnline(SinglePostDataActivity.this)) {
                        toDisplayNotificationData();
                    } else {
                        CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), SinglePostDataActivity.this);
                    }
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }

    }

    private void toCallCommentUi() {
        try {
            Bundle bundle = new Bundle();
            Log.d("hmapp", " comment " + timelineId);
            bundle.putString(AppConstants.TIMELINE_ID, timelineId);
            CommentFragment cm = new CommentFragment();
            cm.setArguments(bundle);
            replaceMainHomePage(cm);
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
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
        if (!obj.isNull(getString(R.string.str_post_small))) {
            mtxtSpdPost.setVisibility(View.VISIBLE);
            mtxtSpdPost.setText(obj.getString(getString(R.string.str_post_small)));
        } else if (!obj.isNull(getString(R.string.str_caption))) {
            mtxtSpdPost.setVisibility(View.VISIBLE);
            mtxtSpdPost.setText(obj.getString(getString(R.string.str_caption)));
        } else if (!obj.isNull(getString(R.string.str_post_data))) {
            mtxtSpdPost.setVisibility(View.VISIBLE);
            mtxtSpdPost.setText(obj.getString(getString(R.string.str_post_data)));
        } else {
            mtxtSpdPost.setVisibility(View.GONE);
            mtxtSpdPost.setText("");
        }

        if (!obj.isNull(getString(R.string.str_time))) {
            mtxt_time_ago.setText(CommonFunctions.toSetDate(obj.getString(getString(R.string.str_time))));
        }
        if (!obj.isNull(getString(R.string.str_like_count))) {
            mtxtNo_like.setText(obj.getString(getString(R.string.str_like_count)) + " " + getResources().getString(R.string.str_like));
        }
        if (!obj.isNull(getString(R.string.str_comment_count))) {
            mtxtNo_comment.setText(obj.getString(getString(R.string.str_comment_count)) + " " + getString(R.string.str_comment));
        }
        if (!obj.isNull(getString(R.string.str_share_count))) {
            mtxtNo_share.setText(obj.getString(getString(R.string.str_share_count)) + " " + getResources().getString(R.string.str_share));
        }
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
                            null
                    )
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
                            null
                    )
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
                    FirebaseCrash.report(e);
                }
            }
        });

        mtxt_share.setOnClickListener(new View.OnClickListener() {
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
                    FirebaseCrash.report(e);
                }
            }
        });

        mtxt_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyPost.toLikeUnlikePost(SinglePostDataActivity.this, obj.getString(getString(R.string.str_timeline_id_)),
                            null, null, mtxt_like, mtxtNo_like);
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    FirebaseCrash.report(e);
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
                    FirebaseCrash.report(e);
                }
            }
        });

        mtxt_comment.setOnClickListener(new View.OnClickListener() {
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
        Bundle bundle = new Bundle();
        Log.d("hmapp", " comment " + obj.getString(getString(R.string.str_timeline_id_)));
        bundle.putString(AppConstants.TIMELINE_ID, obj.getString(getString(R.string.str_timeline_id_)));
        CommentFragment cm = new CommentFragment();
        cm.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flSpdComment, cm)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
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
                                    params.put(getString(R.string.str_action_), getString(R.string.str_notification_post));
                                    params.put(getString(R.string.str_timeline_id_), timelineId);
                                    return params;
                                }
                            }
                            , getString(R.string.str_notification_post));
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }
}
