package com.hm.application.classes;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.adapter.CommentsAdapter;
import com.hm.application.adapter.DisplayReplyAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class Post {

    static RecyclerView mRvCmt;
    static LinearLayout mLlAddCmt;
    static Context context;
    static String timelineId;

    public static void toDisplayComments(String timelineId_, RecyclerView mRvCmt_, Context context_, LinearLayout mLlAddCmt_) {
        timelineId = timelineId_;
        mRvCmt = mRvCmt_;
        mLlAddCmt = mLlAddCmt_;
        context = context_;
        toDisplayComments();
    }

    private static void toDisplayComments() {// extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VolleySingleton.getInstance(context)
                            .addToRequestQueue(
                                    new StringRequest(Request.Method.POST,
                                            AppConstants.URL + context.getString(R.string.str_like_share_comment) + context.getString(R.string.str_php),
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
                                                            LinearLayoutManager llm = new LinearLayoutManager(context);
//                                                            llm.setReverseLayout(true);
//                                                        llm.setStackFromEnd(true);
                                                            mRvCmt.setLayoutManager(llm);
                                                            mRvCmt.hasFixedSize();
                                                            mRvCmt.setVisibility(View.VISIBLE);
                                                            mRvCmt.setNestedScrollingEnabled(false);
                                                            mRvCmt.setAdapter(new CommentsAdapter(context, array));
//                                                        mRvCmt.smoothScrollToPosition(array.length() - 1);

                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Comment", context);
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
                                            params.put(context.getString(R.string.str_action_), context.getString(R.string.str_fetch_comment_));
                                            params.put(context.getString(R.string.str_timeline_id_), timelineId);
//                                        params.put(context.getString(R.string.str_timeline_id_), "102");
                                            Log.d("hmapp", " comment fragment_timeline Api:" + timelineId);
                                            return params;
                                        }
                                    }
                                    , context.getString(R.string.str_fetch_comment_));
                } catch (Exception | Error e) {
                    e.printStackTrace();

                }
            }
        }).start();
//            return null;
//        }
    }

    public static void toDisplayReply(final String id, LinearLayout mLlCuReply, final Context context) {
        try {
            if (mLlCuReply.getChildCount() > 0) {
                mLlCuReply.removeAllViews();
            }
            final View view = LayoutInflater.from(context).inflate(R.layout.rv_layout, null, false);
            if (view != null) {
                VolleySingleton.getInstance(context)
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
                                                            RecyclerView mRv = view.findViewById(R.id.mRvCommon);
                                                            mRv.setLayoutManager(new LinearLayoutManager(context));
                                                            mRv.hasFixedSize();
                                                            mRv.setNestedScrollingEnabled(false);
                                                            mRv.setAdapter(new DisplayReplyAdapter(context, array));
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Reply", context);
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Reply", context);
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

        }
    }
}