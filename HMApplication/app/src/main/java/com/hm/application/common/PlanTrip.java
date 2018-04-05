package com.hm.application.common;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlanTrip {

    public static void toUpdateMyPost(final Context context) {
        try {
            CommonFunctions.toCallLoader(context, "Loading");
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(
                                    Request.Method.POST,
                                    AppConstants.URL + "plan_trip" + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                Log.d("HmApp", " plantrip " + res.trim());
//                                                if (res != null) {
                                                    CommonFunctions.toCloseLoader(context);
//                                                    JSONObject response = new JSONObject(res.trim());
//                                                    //{"msg":"Success","post_data":"How are you?"}
//                                                    if (response != null) {
//                                                        if (!response.isNull("msg")) {
//                                                            if (response.getString("msg").equals("Success")) {
//                                                                CommonFunctions.toDisplayToast("Successfully Post", context);
//                                                            }
//                                                        }
//                                                    } else {
//                                                        CommonFunctions.toDisplayToast("Unable to Post", context);
//                                                    }
//                                                } else {
//                                                    CommonFunctions.toCloseLoader(context);
//                                                    CommonFunctions.toDisplayToast("Unable to Post", context);
//                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                CommonFunctions.toCloseLoader(context);
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            CommonFunctions.toCloseLoader(context);
                                            error.printStackTrace();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    try {

                                        JSONArray array = new JSONArray();
                                        Map<String, String> params = new HashMap<String, String>();
//                                        params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_upload_post));
//                                        params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
//                                        ArrayList<String> data = new ArrayList<>();
//                                        ArrayList<ArrayList<String>> data1 = new ArrayList<>();
//                                        ArrayList<ArrayList<ArrayList<String>>> data2 = new ArrayList<>();
//
//                                        data.add("ll ");
//                                        data1.add(data);
//                                        Map<String, String> p = new HashMap<String, String>();
//                                        for (int i = 0; i < 4; i++) {
//                                            p.put("name["+i+"]", " aks " + i);
//                                            p.put("named["+i+"]", " akshi " + i);
//                                            data1.add(data);
//                                            data2.add(data1);
//                                            params.put("adult_data["+i+"]", p.toString());
//                                        }
//                                        Log.d("Hmapp", " plantrip" + params);
//                                        params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_upload_post));
//                                        params.put(context.getString(R.string.str_uid), User.getUser(context).getUid());
                                        for (int i = 0; i < 4; i++) {
                                            array.put(new JSONObject(new Gson().toJson(User.getUser(context))));
//                                            array.put(new JSONObject(new Gson().toJson(User.getUser(context))));
                                            params.put("adult_data["+i+"]", array.get(i).toString());
                                        }
                                        Log.d("Hmapp", " trip" + params);
                                        return params;
                                    } catch (Exception| Error e){
                                        e.printStackTrace();
                                    }
                                    return null;
                                }
                            }
                            , context.getString(R.string.str_upload_post));
        } catch (Exception | Error e) {
            e.printStackTrace();
            CommonFunctions.toCloseLoader(context);
        }
    }

}
