package com.hm.application.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.CharsetUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MultipartRequest extends Request<String> {
    private MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    private long fileLength = 0;
    private HttpEntity httpentity;
    private File mFilePart;
    private Listener<String> mListener;
    private MultipartProgressListener multipartProgressListener;
    private String token;

    public MultipartRequest(String url, File file, String token, MultipartProgressListener proListener, ErrorListener errorListener, Listener<String> listener) {
        super(1, url, errorListener);
        this.mListener = listener;
        this.mFilePart = file;
        this.fileLength = file.length();
        this.token = token;
        this.multipartProgressListener = proListener;
        this.entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            this.entity.setCharset(CharsetUtils.get("UTF-8"));
        } catch (Exception | Error e) {
            e.printStackTrace();

            buildMultipartEntity();
            this.httpentity = this.entity.build();
        }
        buildMultipartEntity();
        this.httpentity = this.entity.build();
    }

    private void buildMultipartEntity() {
        this.entity.addTextBody("api_token", this.token);
        this.entity.addPart("profile-picture", new FileBody(this.mFilePart, ContentType.create("image/*"), this.mFilePart.getName()));
    }

    public String getBodyContentType() {
        return this.httpentity.getContentType().getValue();
    }

    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            this.httpentity.writeTo(new CountingOutputStream(bos, this.fileLength, this.multipartProgressListener));
        } catch (Exception e) {
            e.printStackTrace();

            return bos.toByteArray();
        }
        return bos.toByteArray();
    }

    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new String(response.data, "UTF-8"), getCacheEntry());
        } catch (Exception | Error e) {
            e.printStackTrace();

            return Response.success(new String(response.data), getCacheEntry());
        }
    }

    protected void deliverResponse(String response) {
        this.mListener.onResponse(response);
    }

    public interface MultipartProgressListener {
        void transferred(long j, int i);
    }

    private static class CountingOutputStream extends FilterOutputStream {
        private final MultipartProgressListener progListener;
        private long fileLength;
        private long transferred = 0;

        public CountingOutputStream(OutputStream out, long fileLength, MultipartProgressListener listener) {
            super(out);
            this.fileLength = fileLength;
            this.progListener = listener;
        }

        public void write(byte[] b, int off, int len) throws IOException {
            this.out.write(b, off, len);
            if (this.progListener != null) {
                this.transferred += (long) len;
                if (this.fileLength != 0) {
                    this.progListener.transferred(this.transferred, (int) ((this.transferred * 100) / this.fileLength));
                }
            }
        }

        public void write(int b) throws IOException {
            this.out.write(b);
            if (this.progListener != null) {
                this.transferred++;
                if (this.fileLength != 0) {
                    this.progListener.transferred(this.transferred, (int) ((this.transferred * 100) / this.fileLength));
                }
            }
        }
    }
}


/*
public class MultipartRequest extends Request<String> {

    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
    private final Response.Listener<String> mListener;
    private final File mImageFile;
    protected Map<String, String> headers;
    private String mBoundary;
    private Map<String, String> mParams;
    private String mFileFieldName;
    private String mFilename;
    private String mBodyContentType;

    public void setBoundary(String boundary) {
        this.mBoundary = boundary;
    }

    public MultipartRequest(String url, final Map<String, String> params, File imageFile, String filename, String fileFieldName, ErrorListener errorListener, Listener<String> listener) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mImageFile = imageFile;
        mParams = params;
        mFileFieldName = fileFieldName;
        mFilename = filename;

        buildMultipartEntity();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        headers.put("Accept", "application/json");
        headers.put("X-Requested-With", "XMLHTTPRequest");
        headers.put("User-Agent", "KaliMessenger");
        return headers;
    }

    private void buildMultipartEntity() {
        for (Map.Entry<String, String> entry : mParams.entrySet()) {
            mBuilder.addTextBody(entry.getKey(), entry.getValue());
        }
        mBuilder.addBinaryBody(mFileFieldName, mImageFile, ContentType.create("image/jpg"), mFilename);
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    }

    @Override
    public String getBodyContentType() {
        return mBodyContentType;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            HttpEntity entity = mBuilder.build();
            mBodyContentType = entity.getContentType().getValue();
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }

        return bos.toByteArray();
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}*/