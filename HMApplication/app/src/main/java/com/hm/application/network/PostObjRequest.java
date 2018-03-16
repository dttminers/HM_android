package com.hm.application.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class PostObjRequest extends JsonRequest<JSONObject> {

    public PostObjRequest(String url, JSONObject jsonObject, Listener<JSONObject> listener, ErrorListener errorListener) {
        super(Method.POST, url, jsonObject.toString(), listener, errorListener);
        Log.d("HM_URL", " POST Obj Req: " + url + " : " + jsonObject.toString());
    }

    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.d("HM_URL", " POST Obj Resp: " + response.statusCode + " : " + response.networkTimeMs +": " + response.headers + " : " + response.allHeaders);
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            Log.d("Hmurl", "string "+ jsonString);

            JSONObject result = null;

            if (jsonString != null && jsonString.length() > 0)
                result = new JSONObject(jsonString);
//            Log.d("HM_URL", " POST Obj Resp: "+ " : "+ result+":" + response +":"+ response.statusCode + " : " + response.networkTimeMs +": " + response.headers + " : " + response.allHeaders);
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.d("Hmurl", "exce 1"+e.getMessage());
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            Log.d("Hmurl", "exce 1"+je.getMessage());
            return Response.error(new ParseError(je));
        }
//        try {
//            Log.d("HM_URL", " POST Obj Resp: " +  response.statusCode + " : " + response.networkTimeMs +": " + response.headers + " : " + response.allHeaders);//+ " : "+ Response.success(new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers))), HttpHeaderParser.parseCacheHeaders(response)).toString());
//            Log.d("HM_URL", " POST Obj Resp: " + parseNetworkResponse(response).toString());//+ " : "+ Response.success(new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers))), HttpHeaderParser.parseCacheHeaders(response)).toString());
//            return Response.success(new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers))), HttpHeaderParser.parseCacheHeaders(response));
//        } catch (Exception | Error e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}