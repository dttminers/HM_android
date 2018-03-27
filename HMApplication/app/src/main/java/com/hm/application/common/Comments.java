package com.hm.application.common;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Comments {

    public static void toLikeComment(final Context context, final String commentId, final TextView mTvLike) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL
                                            + context.getResources().getString(R.string.str_like_share_comment)
                                            + "." + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                Log.d("HmApp", " update POST " + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    //{"msg":"Success","post_data":"How are you?"}
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").toLowerCase().contains("fail")) {
                                                                mTvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                                                                mTvLike.setText(response.getString("like Count") + " Likes");
                                                            } else if (response.getString("msg").toLowerCase().contains("success")) {
                                                                mTvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
                                                                mTvLike.setText(response.getString("like Count") + " Likes");
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), "like_comment");
                                    params.put("uid", User.getUser(context).getUid());
                                    params.put("comment_id", commentId);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_like_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
        }
    }

    public static void toLikeReply(final Context context, final String replyId, final TextView mTvLike) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL
                                            + context.getResources().getString(R.string.str_like_share_comment)
                                            + "." + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                Log.d("HmApp", " update POST " + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    //{"msg":"Success","post_data":"How are you?"}
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").toLowerCase().contains("fail")) {
                                                                mTvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                                                                mTvLike.setText(response.getString("like Count") + " Likes");
                                                            } else if (response.getString("msg").toLowerCase().contains("success")) {
                                                                mTvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
                                                                mTvLike.setText(response.getString("like Count") + " Likes");
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), "like_reply_comment");
                                    params.put("uid", User.getUser(context).getUid());
                                    params.put("reply_id", replyId);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_like_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
        }
    }
}
