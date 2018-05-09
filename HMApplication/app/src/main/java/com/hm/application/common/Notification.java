package com.hm.application.common;

import android.content.Context;
import android.util.Log;

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

//import com.hm.application.utils.insta.videocompressor.Config;

public class Notification {

    public static void toChangeReceiveStatus(final Context context, final String msgId) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_notification_uid)
                                    + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //{"status":1,"msg":"update success"}
                                                Log.d("HmApp", "receive_notification res :" + res.trim());
                                                if (res != null) {
                                                    CommonFunctions.toCloseLoader();
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        CommonFunctions.toCloseLoader();
                                                        if (!response.isNull(context.getString(R.string.str_status))) {
                                                            if (response.getInt(context.getString(R.string.str_status)) == 1) {
                                                                Log.d("Hmapp", "received_notification successfully");
                                                                CommonFunctions.toCloseLoader();
                                                            } else {
                                                                CommonFunctions.toCloseLoader();
                                                            }
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
                                                e.printStackTrace(); Crashlytics.logException(e);

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
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_received_notification));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getString(R.string.str_msg_id), msgId);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_follow_data));
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

            CommonFunctions.toCloseLoader();
        }
    }

    public static void toChangeReadStatus(final Context context, final String msgId) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_notification_uid)
                                    + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //{"status":1,"msg":"update success"}
                                                Log.d("HmApp", "read_notification res :" + res.trim());
                                                if (res != null) {
                                                    CommonFunctions.toCloseLoader();
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        CommonFunctions.toCloseLoader();
                                                        if (!response.isNull(context.getString(R.string.str_status))) {
                                                            if (response.getInt(context.getString(R.string.str_status)) == 1) {
                                                                Log.d("Hmapp", "read_notification successfully");
                                                                CommonFunctions.toCloseLoader();
                                                            } else {
                                                                CommonFunctions.toCloseLoader();
                                                            }
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
                                                e.printStackTrace(); Crashlytics.logException(e);

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
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_read_notification));
                                    params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
                                    params.put(context.getString(R.string.str_msg_id), msgId);
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_follow_data));
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

            CommonFunctions.toCloseLoader();
        }
    }

}
