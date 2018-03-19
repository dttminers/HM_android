package com.hm.application.common;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class MyFriendRequest {

    // to sent follow request to friend...............
    public static void toFollowFriendRequest(final Context context) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_follow_data) + "." + context.getResources().getString(R.string.str_php),
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
                                                                Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 1) {
                                                                Toast.makeText(context, "Follow Request sent", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 2) {
                                                                Toast.makeText(context, "Unable To Follow", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_follow_data));
                                    params.put("uid", User.getUser(context).getId());
                                    params.put("friend_id", "1");
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getString(R.string.str_header), context.getString(R.string.str_header_type));
                                    return super.getHeaders();
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
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_follow_data) + "." + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //response status 0 = public , 1= private, 2 = error;
                                                Log.d("HmApp", "follow Accept data" + res.trim());
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    Log.d("HmApp", "follow Accept data " + response);
                                                    //{"msg":"Success","post_data":"How are you?"}
                                                    if (response != null) {
                                                        if (!response.isNull("status")) {
                                                            if (response.getInt("status") == 0) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText("Friend Request Accepted");
                                                                btnIgnore.setEnabled(false);
                                                                Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 1) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText("Friend Request Accepted");
                                                                btnIgnore.setEnabled(false);
                                                                Toast.makeText(context, "Follow Request sent", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 2) {
                                                                Toast.makeText(context, "Unable To Follow", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_follow_accept_data));
                                    params.put("uid", "20");
                                    params.put("friend_id", id);
                                    Log.d("HmApp", " Friend Request Accepted" + params) ;
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getString(R.string.str_header), context.getString(R.string.str_header_type));
                                    return super.getHeaders();
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
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_follow_data) + "." + context.getResources().getString(R.string.str_php),
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
                                                                btnIgnore.setText("Friend Request Delete");
                                                                btnIgnore.setEnabled(false);
                                                                Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 1) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText("Friend Request Delete");
                                                                btnIgnore.setEnabled(false);
                                                                Toast.makeText(context, "Follow Request sent", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 2) {
                                                                Toast.makeText(context, "Unable To Follow", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_follow_data));
                                    params.put("uid", "20");
                                    params.put("friend_id", id);
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getString(R.string.str_header), context.getString(R.string.str_header_type));
                                    return super.getHeaders();
                                }
                            }
                            , context.getString(R.string.str_follow_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    // to UnFriend Already Friend
    public static void toUnFriendRequest(final Context context, final String id) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_follow_data) + "." + context.getResources().getString(R.string.str_php),
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
                                                                Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 1) {
                                                                Toast.makeText(context, "Follow Request sent", Toast.LENGTH_SHORT).show();
                                                            } else if (response.getInt("status") == 2) {
                                                                Toast.makeText(context, "Unable To Follow", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(context, "Unable to sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_follow_data));
                                    params.put("uid", User.getUser(context).getId());
                                    params.put("friend_id", id);
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getString(R.string.str_header), context.getString(R.string.str_header_type));
                                    return super.getHeaders();
                                }
                            }
                            , context.getString(R.string.str_follow_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}