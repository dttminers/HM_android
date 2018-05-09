package com.hm.application.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetArrayRequest extends JsonRequest<JSONArray> {

    public GetArrayRequest(String url, JSONObject jsonObject, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, jsonObject.toString(), listener, errorListener);
        Log.d("HM_URL", " Get Array Req: " + url + " : " + jsonObject.toString());
    }

    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.d("HM_URL", " GET Array Resp: " + response.statusCode + " : " + response.networkTimeMs);
            return Response.success(new JSONArray(new String(response.data, HttpHeaderParser.parseCharset(response.headers))), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

            return null;
        }
    }
}
