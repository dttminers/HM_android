package com.hm.application.common;

import android.content.Context;
import android.util.Log;
import android.view.View;

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

public class MyBucketList {

    // item Name package, destination, rentout, activity
    public static void toAddITemInBucketList(final Context context, final String itemName, final String itemId) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_bucketlist) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //{context.getString(R.string.str_status):1}
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        if (!response.isNull(context.getString(R.string.str_status))) {
                                                            if (response.getInt(context.getString(R.string.str_status)) == 1) {
                                                                CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_added_to_bucketlist), context);
                                                            } else {
                                                                if (!response.isNull(context.getString(R.string.str_msg_small))) {
                                                                    CommonFunctions.toDisplayToast(CommonFunctions.firstLetterCaps(response.getString(context.getString(R.string.str_msg_small))), context);
                                                                } else {
                                                                    CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                                                }
                                                            }
                                                        } else {
                                                            CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                                    }
                                                } else {
                                                    CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_add_bucketlist));
                                    params.put(context.getResources().getString(R.string.str_item_name), itemName);
                                    params.put(context.getResources().getString(R.string.str_item_id), itemId);
                                    params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_add_bucketlist));
        } catch (Exception | Error e) {
            e.printStackTrace();
            CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
        }
    }

    public static void toRemoveItemFromBucketList(final Context context,  final String itemId) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_bucketlist) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //{context.getString(R.string.str_status):1}
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        if (!response.isNull(context.getString(R.string.str_status))) {
                                                            if (response.getInt(context.getString(R.string.str_status)) == 1) {
                                                                CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_removed_from_bucketlist), context);
                                                            } else {
                                                                if (!response.isNull(context.getString(R.string.str_msg_small))) {
                                                                    CommonFunctions.toDisplayToast(CommonFunctions.firstLetterCaps(response.getString(context.getString(R.string.str_msg_small))), context);
                                                                } else {
                                                                    CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                                                }
                                                            }
                                                        } else {
                                                            CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                                    }
                                                } else {
                                                    CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_remove_bucketlist));
                                    params.put(context.getResources().getString(R.string.str_item_id), itemId);
                                    params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_remove_bucketlist));
        } catch (Exception | Error e) {
            e.printStackTrace();
            CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_add), context);
        }
    }
}
