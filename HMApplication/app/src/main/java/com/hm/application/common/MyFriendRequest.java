package com.hm.application.common;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.network.PostObjRequest;
import com.hm.application.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SIS-002 on 15-03-2018.
 */

public class MyFriendRequest {

    public static void toFriendRequest(final Context context, String activity, String tag, String location, String data){
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(new PostObjRequest(AppConstants.URL + context.getResources().getString(R.string.str_follow_data) + "." + context.getResources().getString(R.string.str_php),
                            new JSONObject()
                                    .put(context.getResources().getString(R.string.str_action_), "follow_data")
                                    .put("uid", 33)
                                    .put("friend_id", 1),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                //{"status":0,"msg":"Follow request sent"}
                                                //{"msg":"Success","post_data":"How are you?"}
                                                if (response != null) {
                                                    if (!response.isNull("status")){
                                                        if (response.getString("status").equals("Success")){
                                                            Toast.makeText(context, "Successfully Send", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Unable to Send", Toast.LENGTH_SHORT).show();
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
                            )
                            , "follow_data");
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}


