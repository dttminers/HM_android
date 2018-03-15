package com.hm.application.common;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.network.PostObjRequest;
import com.hm.application.network.VolleySingleton;

import org.json.JSONObject;

public class MyPost {

    // toUpdate my Post
    public static void toUpdateMyPost(final Context context, String activity, String tag, String location, String data) {
        try {
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new PostObjRequest(
                                    AppConstants.URL + context.getResources().getString(R.string.str_time_log) + "." + context.getResources().getString(R.string.str_php),
                                    new JSONObject()
                                            .put(context.getResources().getString(R.string.str_action_), "update_post")
                                            .put("uid", 1)
                                            .put("activity", "post")
                                            .put("tag_status", "0")
                                            .put("location_status", "0")
                                            .put("post_data", " In Goa"),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                //{"msg":"Success","post_data":"How are you?"}
                                                if (response != null) {
                                                    if (!response.isNull("msg")){
                                                        if (response.getString("msg").equals("Success")){
                                                            Toast.makeText(context, "Successfully Post", Toast.LENGTH_SHORT).show();
                                                        }
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
                            )
                            , "UpdatePost");
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}

/*action:update_post
uid:1
activity:post
tag_status:0
location_status:0
post_data:How are you?*/
