package com.hm.application.common;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyFriendRequest {

    // to sent follow request to friend...............
    public static void toFollowFriendRequest(final Context context, final String id, final Button btn1) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_follow_data) +  context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //response status 0 = public , 1= private, 2 = error;
                                                Log.d("HmApp", "follow data" + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    Log.d("HmApp", "follow data " + response);
                                                    //{"msg":"Success","post_data":"How are you?"}
                                                    if (response != null) {
                                                        if (!response.isNull("status")) {
                                                            if (response.getInt("status") == 0) {
                                                                btn1.setText("Following");
                                                                btn1.setEnabled(false);
                                                            } else if (response.getInt("status") == 1) {
                                                                btn1.setText("Requested");
                                                                btn1.setEnabled(false);
                                                            } else if (response.getInt("status") == 2) {

                                                            }
                                                        }
                                                    } else {
//                                                        Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
//                                                    Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
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
//                                            Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_follow_data));
                                    params.put("uid", User.getUser(context).getUid());//User.getUser(context).getUid());
                                    params.put("friend_id", id);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_follow_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    // to Accept follow request to friend...............
    public static void toAcceptFriendRequest(final Context context, final String id, final Button btnConfirm, final Button btnIgnore) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_follow_data) +  context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //response status 0 = public , 1= private, 2 = error;
                                                //{"status":1,"msg":"Follow request accepted"}
                                                Log.d("HmApp", "follow Accept data" + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    Log.d("HmApp", "follow Accept data " + response);
                                                    if (response != null) {
                                                        if (!response.isNull("status")) {
                                                            if (response.getInt("status") == 0) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText("Friend");
                                                                btnIgnore.setEnabled(false);
                                                                btnIgnore.setPadding(10, 0, 10, 0);
//                                                                Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 1) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText("Friend");
                                                                btnIgnore.setPadding(10, 0, 10, 0);
                                                                btnIgnore.setEnabled(false);
//                                                                Toast.makeText(context, "Follow Request sent", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 2) {
//                                                                Toast.makeText(context, "Unable To Follow", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
//                                                        Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
//                                                    Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
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
//                                            Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_follow_accept_data));
                                    params.put("uid", User.getUser(context).getUid());// My Id
                                    params.put("friend_id", id);// Friend's Id
                                    Log.d("HmApp", " Friend Request Accepted" + params);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_follow_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    // to Delete Already Followed Request
    public static void toDeleteFollowFriendRequest(final Context context, final String id, final Button btnConfirm, final Button btnIgnore) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_unfollow_data) +  context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //response status 0 = public , 1= private, 2 = error;
                                                Log.d("HmApp", "follow delete  data" + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    Log.d("HmApp", "follow delete data " + response);
                                                    if (response != null) {
                                                        if (!response.isNull("status")) {
                                                            if (response.getInt("status") == 0) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText("Ignored");
                                                                btnIgnore.setPadding(10, 0, 10, 0);
                                                                btnIgnore.setEnabled(false);
//                                                                Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 1) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText("Ignored");
                                                                btnIgnore.setPadding(10, 0, 10, 0);
                                                                btnIgnore.setEnabled(false);
//                                                                Toast.makeText(context, "Follow Request sent", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 2) {
//                                                                Toast.makeText(context, "Unable To Follow", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
//                                                        Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
//                                                    Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
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
//                                            Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_unfollow_data));
                                    params.put("uid", User.getUser(context).getUid());
                                    params.put("friend_id", id);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_unfollow_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    // to UnFriend Already Friend
    public static void toUnFriendRequest(final Context context, final String id, final Button btnUnFollow) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_unfollow_data) +  context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //response status 0 = public , 1= private, 2 = error;
                                                Log.d("HmApp", "follow data" + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    Log.d("HmApp", "follow data " + response);
                                                    //{"msg":"Success","post_data":"How are you?"}
                                                    if (response != null) {
                                                        if (!response.isNull("status")) {
                                                            if (response.getInt("status") == 0) {
                                                                btnUnFollow.setText("UnFollow");
                                                                btnUnFollow.setPadding(10, 0, 10, 0);
                                                                btnUnFollow.setEnabled(false);
                                                                CommonFunctions.toDisplayToast("Failed", context);
//                                                                Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 1) {
                                                                btnUnFollow.setText("Follow");
                                                                btnUnFollow.setPadding(10, 0, 10, 0);
                                                                btnUnFollow.setEnabled(false);
//                                                                Toast.makeText(context, "UnFollowed Successfully", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 2) {
//                                                                Toast.makeText(context, "Unable To unfollow", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
//                                                        Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
//                                                    Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
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
//                                            Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_unfollow_data));
                                    params.put("uid", User.getUser(context).getUid());
                                    params.put("friend_id", id);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_follow_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

}