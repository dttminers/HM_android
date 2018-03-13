package com.hm.application.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class PostArrayRequest extends JsonRequest<JSONArray> {

    public PostArrayRequest(String url, JSONObject jsonObject, Listener<JSONArray> listener, ErrorListener errorListener) {
        super(Method.POST, url, jsonObject.toString(), listener, errorListener);
        Log.d("HM_URL", " POST Obj Req: " + url + " : " + jsonObject.toString());
    }

    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.d("HM_URL", " POST Obj Resp: " + response.statusCode + " : " + response.networkTimeMs);
            return Response.success(new JSONArray(new String(response.data, HttpHeaderParser.parseCharset(response.headers))), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception | Error e) {
            e.printStackTrace();
            return null;
        }
    }
}