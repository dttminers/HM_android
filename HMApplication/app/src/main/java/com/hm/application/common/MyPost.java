package com.hm.application.common;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.fragments.CommentFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.network.VolleyMultipartRequest;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyPost {

    /*
    action:update_post
    uid:1
    activity:post
    tag_status:0
    location_status:0
    post_data:How are you?*/

    // toUpdate my Post
    public static void toUpdateMyPost(final Context context, final String activity, final String tag_status, final String location_status, final String post_data) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL + context.getResources().getString(R.string.str_time_log) + "." + context.getResources().getString(R.string.str_php),
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
                                                            if (response.getString("msg").equals("Success")) {
                                                                Toast.makeText(context, "Successfully Post", Toast.LENGTH_SHORT).show();
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
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_update_post));
                                    params.put("uid", User.getUser(context).getUid());
                                    params.put("activity", activity);
                                    params.put("tag_status", tag_status);
                                    params.put("location_status", location_status);
                                    if (post_data != null) {
                                        params.put("post_data", post_data);
                                    } else {
                                        params.put("post_data", "POST");
                                    }
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_update_post));
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    /*
    action:like_data
    uid:20
    timeline_id:1
    */
    public static void toLikeUnlikePost(final Context context, final String timelineId, final LinearLayout mLlPostMain, final Object tag, final TextView mTxt_like, final TextView mTxtNo_like) {
        try {
            Log.d("HmAPp", " toLikeUnlikePost : " + mLlPostMain.getRootView() + " : " + mLlPostMain.getChildCount() + ":" + tag);
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
                                                            if (response.getString("msg").contains("decrease")) {
                                                                Toast.makeText(context, "unliked", Toast.LENGTH_SHORT).show();

                                                                if (tag != null) {
                                                                    View v = mLlPostMain.getChildAt(Integer.parseInt(tag.toString()));

                                                                    TextView mTvNo = v.findViewById(R.id.txtNo_like);
                                                                    mTvNo.setText(response.getString("like Count") + " Likes");

                                                                    TextView mTv = v.findViewById(R.id.txt_like);
                                                                    mTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                                                                } else {
                                                                    mTxtNo_like.setText(response.getString("like Count") + " Likes");
                                                                    mTxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                                                                }

                                                            } else if (response.getString("msg").contains("increases")) {
                                                                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
                                                                if (tag != null) {
//
                                                                    View v = mLlPostMain.getChildAt(Integer.parseInt(tag.toString()));
                                                                    TextView mtvNo = v.findViewById(R.id.txtNo_like);
                                                                    mtvNo.setText(response.getString("like Count") + " Likes");

                                                                    TextView mTv = v.findViewById(R.id.txt_like);
                                                                    mTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
                                                                } else {
                                                                    mTxtNo_like.setText(response.getString("like Count") + " Likes");
                                                                    mTxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                                                                }

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
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_like_data));
                                    params.put("uid", User.getUser(context).getUid());
                                    params.put("timeline_id", timelineId);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_like_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    action:comment_data
uid:41
timeline_id:22
comment:Goa was awesome
    */
    public static void toCommentOnPost(final Context context, final String timelineId, final String commentData, final LinearLayout mLlAddCmt) {
        try {
            Log.d("HMAPP", " toCommentOnPost : " + timelineId + ":" + commentData);
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_like_share_comment) + "." + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                Log.d("HmApp", " comment on post " + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").toLowerCase().contains("success")) {
//                                                                Toast.makeText(context, "Commented", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(context, "Failed to Comment", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_comment_data));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getString(R.string.str_timeline_id_), timelineId);
                                    params.put(context.getString(R.string.str_comment_small), commentData);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_comment_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    action:share_data
    uid:20
    timeline_id:1
    */
    public static void toSharePost(final Context context, final String timelineId) {
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
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").contains("decrease")) {
                                                                Toast.makeText(context, "unliked", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getString("msg").contains("increasese")) {
                                                                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
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
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_shared_data));
                                    params.put("uid", User.getUser(context).getUid());
                                    params.put("timeline_id", timelineId);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_shared_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    comment_id:12
    action:fetch_reply_comment
    */
    public static void toReplyOnComment(final Context context, final String commentId, final String data) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_like_share_comment) + "." + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                Log.d("HmApp", " comment on post " + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").toLowerCase().contains("success")) {
//                                                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//                                                                Post.toDisplayReply(response.getString("comment_id"), );
                                                            } else {
                                                                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_comment_reply_data));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getString(R.string.str_comment_id), commentId);
                                    params.put(context.getString(R.string.str_reply_small), data);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_comment_reply_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }
    }


    public static void toUploadProfilePic(final Context context, final VolleyMultipartRequest.DataPart dataPart) {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                AppConstants.URL + context.getResources().getString(R.string.str_profile_pic) + "." + context.getResources().getString(R.string.str_php),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try {
                            Log.d("HmApp", " pic resultResponse " + resultResponse);
                            if (resultResponse != null) {
                                JSONObject result = new JSONObject(resultResponse.trim());
                                if (!result.isNull("status")) {
                                    if (result.getInt("status") == 1) {
                                        CommonFunctions.toDisplayToast("Updated Successfully", context);
                                        if (!result.isNull("image_path")) {
                                            User.getUser(context).setPicPath(result.getString(context.getString(R.string.str_image_path)));
                                            User.getUser(context).setUser(User.getUser(context));
                                            AppDataStorage.setUserInfo(context);
                                            AppDataStorage.getUserInfo(context);
                                        }
                                    } else {
                                        CommonFunctions.toDisplayToast("Failed to update ", context);
                                    }
                                } else {
                                    CommonFunctions.toDisplayToast("Failed to update ", context);
                                }
                            } else {
                                CommonFunctions.toDisplayToast("Failed to update ", context);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.d("HmApp", "Error Status" + status);
                        Log.d("HmApp", "Error Message" + message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("HmPhoto", "Error" + errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(context.getResources().getString(R.string.str_action_), context.getResources().getString(R.string.str_profile_pic));
                params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put(context.getResources().getString(R.string.str_pic), dataPart);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest, context.getResources().getString(R.string.str_profile_pic));
    }
}