package com.hm.application.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class PostObjRequest extends JsonRequest<JSONObject> {

    public PostObjRequest(String url, JSONObject str, Listener<JSONObject> listener, ErrorListener errorListener) {
        super(Method.POST, url, str.toString().replace("\"", "").replace(":", "="), listener, errorListener);
        Log.d("HM_URL", " POST Obj Req: " + url + " : "
                + str.toString()
                .replace("\"", "")
                .replace(":", "=")
                .replace(",", "&")
                .replace("{", "")
                .replace("}", ""));//jsonObject.toString().replace("\"", "").replace(":","=")
    }

    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.d("HM_URL", " POST Obj Resp: " + response.statusCode + " : " + response.networkTimeMs + ": " + response.headers + " : " + response.allHeaders);
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            Log.d("Hmurl", "string " + jsonString);

            JSONObject result = null;

            if (jsonString != null && jsonString.length() > 0)
                result = new JSONObject(jsonString);
//            Log.d("HM_URL", " POST Obj Resp: "+ " : "+ result+":" + response +":"+ response.statusCode + " : " + response.networkTimeMs +": " + response.headers + " : " + response.allHeaders);
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.d("Hmurl", "exce 1" + e.getMessage());
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            Log.d("Hmurl", "exce 1" + je.getMessage());
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