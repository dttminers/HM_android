package com.hm.application.classes;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.adapter.DisplayReplyAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class Post {

    public static void toDisplayReply(final String id, LinearLayout mLlCuReply, final Context context) {
        try {
            if (mLlCuReply.getChildCount() > 0) {
                mLlCuReply.removeAllViews();
            }
            final View view = LayoutInflater.from(context).inflate(R.layout.rv_layout, null, false);
            if (view != null) {
                VolleySingleton.getInstance(context)
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + context.getString(R.string.str_like_share_comment) + "." + context.getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "Reply Res " + response);
                                                    JSONArray array = new JSONArray(response);
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            RecyclerView mRv = view.findViewById(R.id.mRvCommon);
                                                            mRv.setLayoutManager(new LinearLayoutManager(context));
                                                            mRv.hasFixedSize();
                                                            mRv.setNestedScrollingEnabled(false);
                                                            mRv.setAdapter(new DisplayReplyAdapter(context, array));
                                                        } else {
                                                            CommonFunctions.toDisplayToast("No Reply", context);
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("No Reply", context);
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
                                        params.put(context.getString(R.string.str_action_), context.getString(R.string.str_fetch_reply_comment_));
                                        params.put(context.getString(R.string.str_comment_id), id);
                                        return params;
                                    }
                                }
                                , context.getString(R.string.str_fetch_reply_comment_));
                mLlCuReply.addView(view);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}