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
import com.hm.application.network.PostObjRequest;
import com.hm.application.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyFriendRequest {

    // to follow request friend...............
    public static void toFriendRequest(final Context context) {
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
                                    params.put("uid", "33");
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
}












