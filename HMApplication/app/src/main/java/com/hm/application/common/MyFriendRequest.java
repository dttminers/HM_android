package com.hm.application.common;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crashlytics.android.Crashlytics;
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
            CommonFunctions.toCallLoader(context, "Loading");
            btn1.setEnabled(true);
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_follow_data) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //response status 0 = public , 1= private, 2 = error;
                                                Log.d("HmApp", "toFollowFriendRequest res :" + res.trim());
                                                if (res != null && res.trim().length()>0){
                                                    CommonFunctions.toCloseLoader();
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        CommonFunctions.toCloseLoader();
                                                        if (!response.isNull(context.getString(R.string.str_status))) {
                                                            if (response.getInt(context.getString(R.string.str_status)) == 0) {
                                                                btn1.setText(CommonFunctions.firstLetterCaps(context.getString(R.string.str_following_small)));
                                                                btn1.setTextColor(ContextCompat.getColor(context, R.color.grey5));
                                                                btn1.setBackground(ContextCompat.getDrawable(context, R.drawable.round_border_white));
                                                                btn1.setEnabled(true);
                                                                CommonFunctions.toCloseLoader();
                                                            } else if (response.getInt(context.getString(R.string.str_status)) == 1) {
                                                                btn1.setText(CommonFunctions.firstLetterCaps(context.getString(R.string.str_requested)));
                                                                btn1.setEnabled(true);
                                                                btn1.setTextColor(ContextCompat.getColor(context, R.color.grey5));
                                                                btn1.setBackground(ContextCompat.getDrawable(context, R.drawable.round_border_white));
                                                                CommonFunctions.toCloseLoader();
                                                            } else {
                                                                CommonFunctions.toCloseLoader();
                                                            }
                                                            UserData.toGetUserData(context, false);
                                                        } else {
                                                            CommonFunctions.toCloseLoader();
                                                        }
                                                    } else {
                                                        CommonFunctions.toCloseLoader();
                                                    }
                                                } else {
                                                    CommonFunctions.toCloseLoader();
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                Crashlytics.logException(e);

                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toCloseLoader();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_follow_data));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getString(R.string.str_friend_id), id);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_follow_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
            CommonFunctions.toCloseLoader();
        }
    }

    // to Accept follow request to friend...............
    public static void toAcceptFriendRequest(final Context context, final String id, final Button btnConfirm, final Button btnIgnore) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            btnIgnore.setEnabled(true);
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_follow_data) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //response status 0 = public , 1= private, 2 = error;
                                                //{context.getString(R.string.str_status):1,"msg":"Follow request accepted"}
                                                Log.d("HmApp", "toAcceptFriendRequest res " + res.trim());
                                                if (res != null && res.trim().length()>0){
                                                    JSONObject response = new JSONObject(res.trim());
                                                    CommonFunctions.toCloseLoader();
                                                    if (response != null) {
                                                        if (!response.isNull(context.getString(R.string.str_status))) {
                                                            if (response.getInt(context.getString(R.string.str_status)) == 0) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText(context.getString(R.string.str_friend));
                                                                btnIgnore.setEnabled(true);
                                                                btnIgnore.setTextColor(ContextCompat.getColor(context, R.color.grey5));
                                                                btnIgnore.setBackground(ContextCompat.getDrawable(context, R.drawable.round_border_white));
                                                                btnIgnore.setPadding(10, 0, 10, 0);
                                                                CommonFunctions.toCloseLoader();
                                                            } else if (response.getInt(context.getString(R.string.str_status)) == 1) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText(context.getString(R.string.str_friend));
                                                                btnIgnore.setPadding(10, 0, 10, 0);
                                                                btnIgnore.setEnabled(true);
                                                                btnIgnore.setTextColor(ContextCompat.getColor(context, R.color.grey5));
                                                                btnIgnore.setBackground(ContextCompat.getDrawable(context, R.drawable.round_border_white));
                                                                CommonFunctions.toCloseLoader();
                                                            } else {
                                                                CommonFunctions.toCloseLoader();
                                                            }
                                                            UserData.toGetUserData(context, false);
                                                        } else {
                                                            CommonFunctions.toCloseLoader();
                                                        }
                                                    } else {
                                                        CommonFunctions.toCloseLoader();
                                                    }
                                                } else {
                                                    CommonFunctions.toCloseLoader();
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                Crashlytics.logException(e);

                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toCloseLoader();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_follow_accept_data));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());// My Id
                                    params.put(context.getString(R.string.str_friend_id), id);// Friend's Id
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_follow_accept_data));
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);

            CommonFunctions.toCloseLoader();
        }
    }

    // to Delete Already Followed Request
    public static void toDeleteFollowFriendRequest(final Context context, final String id, final Button btnConfirm, final Button btnIgnore) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            btnIgnore.setEnabled(true);
            btnConfirm.setEnabled(true);
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_unfollow_data) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //response status 0 = public , 1= private, 2 = error;
                                                Log.d("HmApp", "toDeleteFollowFriendRequest res " + res.trim());
                                                if (res != null && res.trim().length()>0){
                                                    JSONObject response = new JSONObject(res.trim());
                                                    CommonFunctions.toCloseLoader();
                                                    if (response != null) {
                                                        if (!response.isNull(context.getString(R.string.str_status))) {
                                                            if (response.getInt(context.getString(R.string.str_status)) == 0) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText(CommonFunctions.firstLetterCaps(context.getString(R.string.str_requested)));
                                                                btnIgnore.setPadding(10, 0, 10, 0);
                                                                btnIgnore.setEnabled(true);
                                                                btnIgnore.setTextColor(ContextCompat.getColor(context, R.color.grey5));
                                                                btnIgnore.setBackground(ContextCompat.getDrawable(context, R.drawable.round_border_white));
                                                                CommonFunctions.toCloseLoader();
                                                            } else if (response.getInt(context.getString(R.string.str_status)) == 1) {
                                                                btnConfirm.setVisibility(View.GONE);
                                                                btnIgnore.setText(CommonFunctions.firstLetterCaps(context.getString(R.string.str_follow)));
                                                                btnIgnore.setPadding(10, 0, 10, 0);
                                                                btnIgnore.setEnabled(true);
                                                                btnIgnore.setTextColor(ContextCompat.getColor(context, R.color.grey5));
                                                                btnIgnore.setBackground(ContextCompat.getDrawable(context, R.drawable.round_border_white));
                                                                CommonFunctions.toCloseLoader();
                                                            } else {
                                                                CommonFunctions.toCloseLoader();
                                                            }
                                                            UserData.toGetUserData(context, false);
                                                        } else {
                                                            CommonFunctions.toCloseLoader();
                                                        }
                                                    } else {
                                                        CommonFunctions.toCloseLoader();
                                                    }
                                                } else {
                                                    CommonFunctions.toCloseLoader();
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                Crashlytics.logException(e);

                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toCloseLoader();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_delete_send_request));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getString(R.string.str_friend_id), id);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_unfollow_data));
        } catch (Exception | Error e) {
            btnIgnore.setEnabled(true);
            btnConfirm.setEnabled(true);
            e.printStackTrace();
            Crashlytics.logException(e);

            CommonFunctions.toCloseLoader();
        }
    }

    // to UnFriend Already Friend
    public static void toUnFriendRequest(final Context context, final String id, final Button btnUnFollow) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            btnUnFollow.setEnabled(true);
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_unfollow_data) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                btnUnFollow.setEnabled(true);
                                                //response status 0 = public , 1= private, 2 = error;
                                                Log.d("HmApp", "follow data" + res.trim());
                                                if (res != null && res.trim().length()>0){
                                                    JSONObject response = new JSONObject(res.trim());
                                                    CommonFunctions.toCloseLoader();
                                                    Log.d("HmApp", "follow data " + response);
                                                    if (response != null) {
                                                        if (!response.isNull(context.getString(R.string.str_status))) {
                                                            if (response.getInt(context.getString(R.string.str_status)) == 1) {
                                                                btnUnFollow.setText(context.getString(R.string.str_follow));
                                                                btnUnFollow.setPadding(10, 0, 10, 0);
                                                                btnUnFollow.setEnabled(true);
                                                                btnUnFollow.setTextColor(ContextCompat.getColor(context, R.color.white));
                                                                btnUnFollow.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner_black_border));
                                                                CommonFunctions.toCloseLoader();
                                                            } else {
                                                                CommonFunctions.toCloseLoader();
                                                            }
                                                        } else {
                                                            CommonFunctions.toCloseLoader();
                                                        }
                                                        UserData.toGetUserData(context, false);
                                                    } else {
                                                        CommonFunctions.toCloseLoader();
                                                    }
                                                } else {
                                                    CommonFunctions.toCloseLoader();
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                Crashlytics.logException(e);

                                                CommonFunctions.toCloseLoader();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toCloseLoader();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_unfollow_data));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getString(R.string.str_friend_id), id);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_unfollow_data));
        } catch (Exception | Error e) {
            btnUnFollow.setEnabled(true);
            e.printStackTrace();
            Crashlytics.logException(e);

            CommonFunctions.toCloseLoader();
        }
    }
}