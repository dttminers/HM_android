package com.hm.application.common;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleyMultipartRequest;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPost {
    /*
    action:upload_post
    uid:1
    activity:post
    tag_status:0
    location_status:0
    post_data:How are you?*/
    // toUpdate my Post
    public static void toUpdateMyPost(final Context context, final String activity, final String tag_status, final String location_status, final String post_data) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            Log.d("HmApp", " toUpdateMyPost :  " + activity + " : " + tag_status + " : " + location_status + " : " + post_data);
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL + context.getResources().getString(R.string.str_time_log) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                Log.d("HmApp", " update POST " + res.trim());
                                                if (res != null) {
                                                    CommonFunctions.toCloseLoader();
                                                    JSONObject response = new JSONObject(res.trim());
                                                    //{"msg":"Success","post_data":"How are you?"}
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").equals("Success")) {
                                                                CommonFunctions.toDisplayToast("Successfully Post", context);
//                                                                ((UserInfoActivity) context).toSetData();
                                                            }
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("Unable to Post", context);
                                                    }
                                                } else {
                                                    CommonFunctions.toCloseLoader();
                                                    CommonFunctions.toDisplayToast("Unable to Post", context);
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            CommonFunctions.toCloseLoader();
                                            error.printStackTrace();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_upload_post));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
//                                    params.put(context.getString(R.string.str_activity), activity != null ? activity.toLowerCase() : "");
                                    params.put(context.getString(R.string.str_activity), context.getResources().getString(R.string.str_post_small));
                                    params.put(context.getString(R.string.str_tag_status), tag_status != null ? tag_status : "");
                                    params.put(context.getString(R.string.str_location_status), location_status != null ? location_status : "");
                                    params.put(context.getString(R.string.str_post_data), post_data != null ? post_data : "POST_DATA");
                                    Log.d("HmApp", " toUpdateMyPost :  " + params);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_upload_post));
        } catch (Exception | Error e) {
            e.printStackTrace();
            CommonFunctions.toCloseLoader();
        }
    }

    /*action:update_image
    uid:20
    caption:Delhi in winters
    upload_count: count */
    public static void toUploadAlbum(final Context context, final FragmentActivity activity, final String caption, final ArrayList<Uri> images) {
        CommonFunctions.toCallLoader(context, "Loading");
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                AppConstants.URL + context.getResources().getString(R.string.str_time_log) + context.getResources().getString(R.string.str_php),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try {
                            CommonFunctions.toCloseLoader();
                            Log.d("HmApp", " pic resultResponse " + resultResponse);
                            if (resultResponse != null) {
                                JSONObject result = new JSONObject(resultResponse.trim());
                                if (!result.isNull(context.getString(R.string.str_status))) {
                                    CommonFunctions.toCloseLoader();
                                    if (result.getInt(context.getString(R.string.str_status)) == 1) {
                                        CommonFunctions.toCloseLoader();
                                        CommonFunctions.toDisplayToast("Updated Successfully", context);
//                                        ((UserInfoActivity) context).toSetData();
                                        if (!result.isNull("image_path")) {
                                            CommonFunctions.toCloseLoader();
                                        }
                                    } else {
                                        CommonFunctions.toCloseLoader();
                                        CommonFunctions.toDisplayToast("Failed to upload album ", context);
                                    }
                                } else {
                                    CommonFunctions.toCloseLoader();
                                    CommonFunctions.toDisplayToast("Failed to upload album ", context);
                                }
                            } else {
                                CommonFunctions.toCloseLoader();
                                CommonFunctions.toDisplayToast("Failed to upload album ", context);
                            }
                        } catch (Exception e) {
                            CommonFunctions.toCloseLoader();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonFunctions.toCloseLoader();
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
                        String status = response.getString(context.getString(R.string.str_status));
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
                params.put(context.getResources().getString(R.string.str_action_), context.getResources().getString(R.string.str_upload_album));
                params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                params.put(context.getResources().getString(R.string.str_upload_count), String.valueOf(images.size()));
                params.put(context.getResources().getString(R.string.str_caption), caption);
                params.put(context.getResources().getString(R.string.str_activity), context.getResources().getString(R.string.str_album_small));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    for (int i = 0; i < images.size(); i++) {
                        params.put("" + i, new DataPart(i + "_.jpg", CommonFunctions.readBytes(images.get(i), activity), "image/jpeg"));
                    }
                    Log.d("HmAPp", " Params album : " + params);
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest, context.getResources().getString(R.string.str_upload_album));
    }

    /*action:update_image
    uid:20
    caption:Delhi in winters
    image_url */
    public static void toUploadImage(final Context context, final FragmentActivity activity, final String caption, final Uri images) {
        Log.d("HmAPp", " Upload images : " + images);
        CommonFunctions.toCallLoader(context, "Loading");
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                AppConstants.URL + context.getResources().getString(R.string.str_time_log) + context.getResources().getString(R.string.str_php),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try {
                            CommonFunctions.toCloseLoader();
                            Log.d("HmApp", " pic resultResponse " + resultResponse);
                            if (resultResponse != null) {
                                JSONObject result = new JSONObject(resultResponse.trim());
                                if (!result.isNull(context.getString(R.string.str_status))) {
                                    if (result.getInt(context.getString(R.string.str_status)) == 1) {
                                        CommonFunctions.toDisplayToast("Updated Successfully", context);
//                                        ((UserInfoActivity) context).toSetData();
                                        if (!result.isNull("image_path")) {
//
                                        }
                                    } else {
                                        CommonFunctions.toDisplayToast("Failed to upload album ", context);
                                    }
                                } else {
                                    CommonFunctions.toDisplayToast("Failed to upload album ", context);
                                }
                            } else {
                                CommonFunctions.toDisplayToast("Failed to upload album ", context);
                            }

                        } catch (Exception e) {
                            CommonFunctions.toCloseLoader();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonFunctions.toCloseLoader();
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
                        String status = response.getString(context.getString(R.string.str_status));
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
                params.put(context.getResources().getString(R.string.str_action_), context.getResources().getString(R.string.str_upload_image));
                params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                params.put(context.getResources().getString(R.string.str_caption), caption);
                params.put(context.getResources().getString(R.string.str_activity), context.getResources().getString(R.string.str_photo_small));

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    params.put(context.getResources().getString(R.string.str_image_url),
                            new VolleyMultipartRequest.DataPart(
                                    User.getUser(context).getUid()
                                            + "p_" + CommonFunctions.getDeviceUniqueID(activity)
                                            + "_" + caption + ".jpg",
                                    CommonFunctions.readBytes(images, activity), "image/jpeg"));
                    Log.d("HmAPp", " params_upload_image : " + params);
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest, context.getResources().getString(R.string.str_upload_album));
    }

    /*
    action:like_data
    uid:20
    timeline_id:1
    */
    public static void toLikeUnlikePost(final Context context, final String timelineId, final LinearLayout mLlPostMain, final Object tag, final TextView mTxt_like, final TextView mTxtNo_like) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            Log.d("HmAPp", " toLikeUnlikePost : " + timelineId + " : " + tag);
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL
                                            + context.getResources().getString(R.string.str_like_share_comment)
                                            + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                CommonFunctions.toCloseLoader();
                                                Log.d("HmApp", " update POST " + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    //{"msg":"Success","post_data":"How are you?"}
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").contains("decrease")) {
                                                                CommonFunctions.toDisplayToast("unliked", context);

                                                                if (tag != null) {
                                                                    View v = mLlPostMain.getChildAt(Integer.parseInt(tag.toString()));

                                                                    TextView mTvNo = v.findViewById(R.id.txtNo_like);
                                                                    mTvNo.setText(response.getString("like Count") + " Likes");

                                                                    TextView mTv = v.findViewById(R.id.txt_like);
                                                                    mTv.setTextColor(ContextCompat.getColor(context, R.color.black));
                                                                    mTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                                                                } else {
                                                                    mTxtNo_like.setText(response.getString("like Count"));
                                                                    mTxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                                                                    mTxt_like.setTextColor(ContextCompat.getColor(context, R.color.black));
                                                                }

                                                            } else if (response.getString("msg").contains("increases")) {
                                                                CommonFunctions.toDisplayToast("Liked", context);
                                                                if (tag != null) {
//
                                                                    View v = mLlPostMain.getChildAt(Integer.parseInt(tag.toString()));
                                                                    TextView mtvNo = v.findViewById(R.id.txtNo_like);
                                                                    mtvNo.setText(response.getString("like Count") + " Likes");

                                                                    TextView mTv = v.findViewById(R.id.txt_like);
                                                                    mTv.setTextColor(ContextCompat.getColor(context, R.color.blue));
                                                                    mTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
                                                                } else {
                                                                    mTxtNo_like.setText(response.getString("like Count"));
                                                                    mTxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
                                                                    mTxt_like.setTextColor(ContextCompat.getColor(context, R.color.blue));
                                                                }

                                                            }
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("Unable to Post", context);
                                                    }
                                                } else {
                                                    CommonFunctions.toDisplayToast("Unable to Post", context);
                                                }
                                            } catch (Exception | Error e) {
                                                CommonFunctions.toCloseLoader();
                                                e.printStackTrace();
                                                FirebaseCrash.report(e);
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toDisplayToast("Unable to Post", context);
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_like_data));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getString(R.string.str_timeline_id_), timelineId);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_like_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            CommonFunctions.toCloseLoader();
            CommonFunctions.toDisplayToast("Unable to Post", context);
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
            CommonFunctions.toCallLoader(context, "Loading");
            Log.d("HMAPP", " toCommentOnPost : " + timelineId + ":" + commentData);
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_like_share_comment) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                CommonFunctions.toCloseLoader();
                                                Log.d("HmApp", " comment on post " + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").toLowerCase().contains("success")) {
                                                                CommonFunctions.toDisplayToast( "Commented", context);
                                                            } else {
                                                                CommonFunctions.toDisplayToast("Failed to Comment", context);
                                                            }
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("failed", context);
                                                    }
                                                } else {
                                                    CommonFunctions.toDisplayToast("failed", context);
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                FirebaseCrash.report(e);
                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toCloseLoader();
                                            CommonFunctions.toDisplayToast("failed", context);
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
            FirebaseCrash.report(e);
            CommonFunctions.toCloseLoader();
            CommonFunctions.toDisplayToast("failed", context);
        }
    }

    /*
    comment_id:12
    action:fetch_reply_comment*/
    public static void toReplyOnComment(final Context context, final String commentId, final String data, final TextView mTvCuReply) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_like_share_comment) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                CommonFunctions.toCloseLoader();
                                                Log.d("HmApp", " comment on post " + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").toLowerCase().contains("success")) {
//                                                                CommonFunctions.toDisplayToast( "Success", context);
//                                                                Post.toDisplayReply(response.getString("comment_id"), );
                                                            } else {
                                                                CommonFunctions.toDisplayToast("failed", context);
                                                            }
                                                        }

                                                        if (!response.isNull(context.getString(R.string.str_total_reply))){
                                                            if (mTvCuReply != null) {
                                                                mTvCuReply.setText(response.getString(context.getString(R.string.str_total_reply)) + " " + context.getString(R.string.str_reply));
                                                            }
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("failed", context);
                                                    }
                                                } else {
                                                    CommonFunctions.toDisplayToast("failed", context);
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                FirebaseCrash.report(e);
                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toCloseLoader();
                                            CommonFunctions.toDisplayToast(error.getMessage(), context);
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
            FirebaseCrash.report(e);
            CommonFunctions.toCloseLoader();
            CommonFunctions.toDisplayToast(e.getMessage(), context);
        }
    }

    /*comment_id:38
    action:like_comment
    uid:20*/
    public static void toLikeComment(final Context context, final String commentId, final TextView mTvLike) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL + context.getResources().getString(R.string.str_like_share_comment) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                CommonFunctions.toCloseLoader();
                                                Log.d("HmApp", " update POST " + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    //{"msg":"Success","post_data":"How are you?"}
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").toLowerCase().contains("fail")) {
                                                                mTvLike.setText(response.getString("like Count") + " Likes");
                                                            } else if (response.getString("msg").toLowerCase().contains("success")) {
                                                                mTvLike.setText(response.getString("like Count") + " Likes");
                                                            }
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("Unable to Like", context);
                                                    }
                                                } else {
                                                    CommonFunctions.toDisplayToast("Unable to Like", context);
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                FirebaseCrash.report(e);
                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toCloseLoader();
                                            CommonFunctions.toDisplayToast(error.getMessage(), context);
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_like_comment));
                                    params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getResources().getString(R.string.str_comment_id), commentId);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_like_comment));
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            CommonFunctions.toCloseLoader();
            CommonFunctions.toDisplayToast(e.getMessage(), context);
        }
    }

    /*reply_id:12
    action:like_reply_comment
    uid:20*/
    public static void toLikeReply(final Context context, final String replyId, final TextView mTvLike) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL
                                            + context.getResources().getString(R.string.str_like_share_comment)
                                            + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                CommonFunctions.toCloseLoader();
                                                Log.d("HmApp", " update POST " + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        CommonFunctions.toCloseLoader();
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").toLowerCase().contains("fail")) {
                                                                mTvLike.setText(response.getString("like Count") + " Likes");
                                                            } else if (response.getString("msg").toLowerCase().contains("success")) {
                                                                mTvLike.setText(response.getString("like Count") + " Likes");
                                                            }
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("Unable to Like", context);
                                                    }
                                                } else {
                                                    CommonFunctions.toDisplayToast("Unable to Like", context);
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                FirebaseCrash.report(e);
                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toCloseLoader();
                                            CommonFunctions.toDisplayToast(error.getMessage(), context);
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_like_reply_comment));
                                    params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getString(R.string.str_reply_id), replyId);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_like_reply_comment));
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            CommonFunctions.toCloseLoader();
            CommonFunctions.toDisplayToast(e.getMessage(), context);
        }
    }

    /* action:share_data
    uid:20
    timeline_id:1*/
    public static void toSharePost(final Context context, final String timelineId) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL
                                            + context.getResources().getString(R.string.str_like_share_comment)
                                            + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                CommonFunctions.toCloseLoader();
                                                Log.d("HmApp", " update POST " + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        if (!response.isNull("msg")) {
                                                            if (response.getString("msg").contains("decrease")) {
                                                                CommonFunctions.toDisplayToast("unliked", context);
                                                            } else if (response.getString("msg").contains("increasese")) {
                                                                CommonFunctions.toDisplayToast("Liked", context);
                                                            }
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("Unable to Share", context);
                                                    }
                                                } else {
                                                    CommonFunctions.toDisplayToast("Unable to Share", context);
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                FirebaseCrash.report(e);
                                                CommonFunctions.toCloseLoader();

                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toCloseLoader();
                                            CommonFunctions.toDisplayToast("Unable to Share", context);
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_shared_data));
                                    params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getResources().getString(R.string.str_timeline_id_), timelineId);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_shared_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            CommonFunctions.toCloseLoader();
            CommonFunctions.toDisplayToast(e.getMessage(), context);
        }
    }
}