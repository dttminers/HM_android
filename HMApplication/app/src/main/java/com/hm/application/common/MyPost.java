package com.hm.application.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.PostObjRequest;
import com.hm.application.network.VolleySingleton;

import org.json.JSONObject;

import java.lang.reflect.Method;
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
                                    params.put("uid", User.getUser(context).getId());
                                    params.put("activity", activity);
                                    params.put("tag_status", tag_status);
                                    params.put("location_status", location_status);
                                    params.put("post_data", post_data);
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getString(R.string.str_header), context.getString(R.string.str_header_type));
                                    return super.getHeaders();
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
    public static void toLikeUnlikePost(final Context context, final String timelineId) {
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
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_like_data));
                                    params.put("uid", User.getUser(context).getId());
                                    params.put("timeline_id", timelineId);
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getString(R.string.str_header), context.getString(R.string.str_header_type));
                                    return super.getHeaders();
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
    uid:20
    timeline_id:1
    */
    public static void toCommentOnPost(final Context context, final String timelineId) {
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
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_comment_data));
                                    params.put("uid", User.getUser(context).getId());
                                    params.put("timeline_id", timelineId);
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getString(R.string.str_header), context.getString(R.string.str_header_type));
                                    return super.getHeaders();
                                }
                            }
                            , context.getString(R.string.str_comment_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
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
                                    params.put("uid", User.getUser(context).getId());
                                    params.put("timeline_id", timelineId);
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getString(R.string.str_header), context.getString(R.string.str_header_type));
                                    return super.getHeaders();
                                }
                            }
                            , context.getString(R.string.str_shared_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to Post", Toast.LENGTH_SHORT).show();
        }
    }

}