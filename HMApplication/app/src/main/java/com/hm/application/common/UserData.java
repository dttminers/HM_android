package com.hm.application.common;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.fragments.UserProfileEditFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.network.VolleyMultipartRequest;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserData {

    public static void toGetUserData(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VolleySingleton.getInstance(context)
                            .addToRequestQueue(
                                    new StringRequest(Request.Method.POST,
                                            AppConstants.URL + context.getResources().getString(R.string.str_register_login) + context.getResources().getString(R.string.str_php),
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

                                                                    if (!response.isNull(context.getResources().getString(R.string.str_followers_count))) {
                                                                        User.getUser(context).setFollowers_count(response.getString(context.getResources().getString(R.string.str_followers_count)));
                                                                    }

                                                                    if (!response.isNull(context.getResources().getString(R.string.str_following_count))) {
                                                                        User.getUser(context).setFollowing_count(response.getString(context.getResources().getString(R.string.str_following_count)));
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
                                            params.put("time", "" + System.currentTimeMillis());
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

    public static void toUpdateUserInfoApi(final Context context, final String lives, final String from, final String gender, final String relation, final String dob, final String quote, final String bio) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VolleySingleton.getInstance(context)
                            .addToRequestQueue(
                                    new StringRequest(Request.Method.POST,
                                            AppConstants.URL + context.getString(R.string.str_register_login) + context.getString(R.string.str_php),
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String res) {
                                                    try {

                                                        Log.d("HmApp", " update 1 " + res.trim());
                                                        if (res != null) {
                                                            JSONObject response = new JSONObject(res.trim());
                                                            //{context.getString(R.string.str_status):1,"msg":"Update Successful"}
                                                            if (response != null) {
                                                                if (!response.isNull(context.getString(R.string.str_status))) {
                                                                    if (response.getInt(context.getString(R.string.str_status)) == 1) {
                                                                        CommonFunctions.toDisplayToast(context.getString(R.string.str_successfully_updated), context);
//                                                                        toHideEditUserInfo();

                                                                        User.getUser(context).setLivesIn(lives);
                                                                        User.getUser(context).setFromDest(from);
                                                                        User.getUser(context).setGender(gender);
                                                                        User.getUser(context).setRelationStatus(relation);
                                                                        User.getUser(context).setDob(dob);
                                                                        User.getUser(context).setFavQuote(quote);
                                                                        User.getUser(context).setBio(bio);
                                                                        User.getUser(context).setUser(User.getUser(context));

                                                                        AppDataStorage.setUserInfo(context);
                                                                        AppDataStorage.getUserInfo(context);
//
//                                                                        toDisplayUserInfo();
                                                                        ((UserInfoActivity) context).toDisplayUserInfo();
                                                                    } else {
                                                                        CommonFunctions.toDisplayToast(context.getResources().getString(R.string.str_error_unable_to_update), context);
                                                                    }
                                                                }
                                                            } else {
                                                                CommonFunctions.toDisplayToast(context.getResources().getString(R.string.str_error_unable_to_update), context);
                                                            }
                                                        } else {
                                                            CommonFunctions.toDisplayToast(context.getResources().getString(R.string.str_error_unable_to_update), context);
                                                        }
                                                    } catch (Exception | Error e) {
                                                        e.printStackTrace();

                                                        CommonFunctions.toDisplayToast(context.getResources().getString(R.string.str_error_unable_to_update), context);
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    error.printStackTrace();

                                                    CommonFunctions.toDisplayToast(context.getResources().getString(R.string.str_error_unable_to_update), context);
                                                }
                                            }
                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put(context.getResources().getString(R.string.str_action_),context.getString(R.string.str_user_info_update));
                                            params.put(context.getResources().getString(R.string.str_id), User.getUser(context).getUid());
                                            params.put(context.getResources().getString(R.string.str_lives_in), lives);
                                            params.put(context.getResources().getString(R.string.str_from_place),from);
                                            params.put(context.getResources().getString(R.string.str_gender), gender);
                                            params.put(context.getResources().getString(R.string.str_rel_status),relation);
                                            params.put(context.getResources().getString(R.string.str_fav_quote), quote);
                                            params.put(context.getResources().getString(R.string.str_dob), dob);
                                            params.put(context.getResources().getString(R.string.str_bio), bio);
                                            return params;
                                        }
                                    }
                                    , (context).getString(R.string.str_user_info_update));
                } catch (Exception | Error e) {
                    e.printStackTrace();

                    CommonFunctions.toDisplayToast(context.getString(R.string.str_error_unable_to_update),context);
                }
            }
        }).start();
    }

    public static void toUploadProfilePic(final Context context, final VolleyMultipartRequest.DataPart dataPart) {
        CommonFunctions.toCallLoader(context, "Loading");
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                AppConstants.URL + context.getResources().getString(R.string.str_profile_pic) + context.getResources().getString(R.string.str_php),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try {
                            CommonFunctions.toCloseLoader(context);
                            Log.d("HmApp", " pic resultResponse " + resultResponse);
                            if (resultResponse != null) {
                                JSONObject result = new JSONObject(resultResponse.trim());
                                if (!result.isNull(context.getString(R.string.str_status))) {
                                    if (result.getInt(context.getString(R.string.str_status)) == 1) {
                                        CommonFunctions.toDisplayToast("Updated Successfully", context);
                                        if (!result.isNull("image_path")) {
                                            User.getUser(context).setPicPath(result.getString(context.getString(R.string.str_image_path)));
                                            User.getUser(context).setUser(User.getUser(context));
                                            AppDataStorage.setUserInfo(context);
                                            AppDataStorage.getUserInfo(context);
                                        }
                                    } else {
                                        CommonFunctions.toDisplayToast("Failed to update ", context);
                                    }
                                } else {
                                    CommonFunctions.toDisplayToast("Failed to update ", context);
                                }
                            } else {
                                CommonFunctions.toDisplayToast("Failed to update ", context);
                            }

                        } catch (Exception e) {
                            CommonFunctions.toCloseLoader(context);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonFunctions.toCloseLoader(context);
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString(context.getString(R.string.str_status));
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
                params.put(context.getResources().getString(R.string.str_action_), context.getResources().getString(R.string.str_profile_pic));
                params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put(context.getResources().getString(R.string.str_pic), dataPart);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest, context.getResources().getString(R.string.str_profile_pic));
    }
}