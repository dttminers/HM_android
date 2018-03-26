package com.hm.application.common;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.fragments.UserProfileFeaturesFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.network.VolleyMultipartRequest;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserData {

    public static void toGetUserData(final Context context, boolean status) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VolleySingleton.getInstance(context)
                            .addToRequestQueue(
                                    new StringRequest(Request.Method.POST,
                                            AppConstants.URL + context.getResources().getString(R.string.str_register_login) + "." + context.getResources().getString(R.string.str_php),
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String res) {
                                                    try {
                                                        /*
                                                        * {"id":"20",  "profile_type":"", "profile_pic":"uploads\/20\/profile_pics\/21-03-2018 18:30:57 PM_202879ad42dec8375e.jpg",          * */
                                                        if (res != null) {
                                                            JSONObject response = new JSONObject(res.trim());
                                                            if (!response.isNull(context.getResources().getString(R.string.str_result_status))) {
                                                                if (response.getInt(context.getResources().getString(R.string.str_result_status)) == 1) {
                                                                    Log.d("Hmapp", " profile " + User.getUser(context).getUid());
                                                                    User.getUser(context).setUid(AppDataStorage.getUserId(context));
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_full_name_))) {
                                                                        User.getUser(context).setName(response.getString(context.getResources().getString(R.string.str_full_name_)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_username_))) {

                                                                        User.getUser(context).setUsername(response.getString(context.getResources().getString(R.string.str_username_)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_email_))) {
                                                                        User.getUser(context).setEmail(response.getString(context.getResources().getString(R.string.str_email_)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_contact_no_))) {
                                                                        User.getUser(context).setMobile(response.getString(context.getResources().getString(R.string.str_contact_no_)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_dob_))) {
                                                                        User.getUser(context).setDob(response.getString(context.getResources().getString(R.string.str_dob_)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_referral_code_))) {
                                                                        User.getUser(context).setReferralCode(response.getString(context.getResources().getString(R.string.str_referral_code_)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_lives_in))) {
                                                                        User.getUser(context).setLivesIn(response.getString(context.getResources().getString(R.string.str_lives_in)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_gender))) {
                                                                        User.getUser(context).setGender(response.getString(context.getResources().getString(R.string.str_gender)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_from_des))) {
                                                                        User.getUser(context).setGender(response.getString(context.getResources().getString(R.string.str_gender)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_relationship_status))) {
                                                                        User.getUser(context).setGender(response.getString(context.getResources().getString(R.string.str_gender)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_fav_quote))) {
                                                                        User.getUser(context).setGender(response.getString(context.getResources().getString(R.string.str_gender)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_bio))) {
                                                                        User.getUser(context).setGender(response.getString(context.getResources().getString(R.string.str_gender)));
                                                                    }
                                                                    if (!response.isNull(context.getResources().getString(R.string.str_profile_pic))) {
                                                                        User.getUser(context).setPicPath(response.getString(context.getString(R.string.str_profile_pic)));
                                                                    }

                                                                    AppDataStorage.setUserInfo(context);
                                                                    AppDataStorage.getUserInfo(context);

                                                                }
                                                            }
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
                                            params.put(context.getResources().getString(R.string.str_action_), context.getResources().getString(R.string.str_user_info_display));
                                            params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                                            return params;
                                        }
                                    }
                                    , context.getResources().getString(R.string.str_user_info_display));
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void packages_info(final Context context) {
        try {
            VolleySingleton.getInstance(context).addToRequestQueue(new StringRequest(
                    Request.Method.POST,
                    "http://vnoi.in/hmapi/packages_info.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("HMAPP ", " RESPONSE " + response);

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
                    params.put(context.getResources().getString(R.string.str_action_), "packages_info");
                    return params;
                }
            }, " ll");

        } catch (Exception | Error e) {

        }
    }

    public static void toUploadAlbum(final ArrayList<Uri> images, final Context context, final FragmentActivity activity, final String caption) {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                AppConstants.URL + context.getResources().getString(R.string.str_time_log) + "." + context.getResources().getString(R.string.str_php),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try {
                            Log.d("HmApp", " pic resultResponse " + resultResponse);
                            if (resultResponse != null) {
                                JSONObject result = new JSONObject(resultResponse.trim());
                                if (!result.isNull("status")) {
                                    if (result.getInt("status") == 1) {
                                        CommonFunctions.toDisplayToast("Updated Successfully", context);
                                        if (!result.isNull("image_path")) {
//
                                        }
                                    } else {
                                        CommonFunctions.toDisplayToast("Failed to upload album ", context);
                                    }
                                } else {
                                    CommonFunctions.toDisplayToast("Failed to upload album ", context);
                                }
                            } else {
                                CommonFunctions.toDisplayToast("Failed to upload album ", context);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.d("HmApp", "Error Status" + status);
                        Log.d("HmApp", "Error Message" + message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("HmPhoto", "Error" + errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(context.getResources().getString(R.string.str_action_), context.getResources().getString(R.string.str_upload_album));
                params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                params.put(context.getResources().getString(R.string.str_upload), String.valueOf(images.size()));
                params.put(context.getResources().getString(R.string.str_caption), caption);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    // file name could found file base or direct access from real path
                    // for now just get bitmap data from ImageView
//                params.put(getString(R.string.str_pic), new DataPart("20" + CommonFunctions.getDeviceUniqueID(getActivity()) + ".jpg", CommonFunctions.getFileDataFromDrawable(getContext(), mIvProfilePic.getDrawable()), "image/jpeg"));
                    for (int i = 0; i < images.size(); i++) {
                        params.put("" + i, new DataPart(i + "_.jpg", CommonFunctions.readBytes(images.get(i), activity), "image/jpeg"));
                    }
                    Log.d("HmAPp", " Params album : " + params);
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest, context.getResources().getString(R.string.str_upload_album));
    }
}