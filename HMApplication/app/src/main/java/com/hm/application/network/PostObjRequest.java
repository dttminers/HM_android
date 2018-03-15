package com.hm.application.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

public class PostObjRequest extends JsonRequest<JSONObject> {

    public PostObjRequest(String url, JSONObject jsonObject, Listener<JSONObject> listener, ErrorListener errorListener) {
        super(Method.POST, url, jsonObject.toString(), listener, errorListener);
        Log.d("HM_URL", " POST Obj Req: " + url + " : " + jsonObject.toString());
    }

    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.d("HM_URL", " POST Obj Resp: " + response.statusCode + " : " + response.networkTimeMs + " : "+ new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
            return Response.success(new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers))), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception | Error e) {
            e.printStackTrace();
            return null;
        }
    }
}