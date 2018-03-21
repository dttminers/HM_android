package com.hm.application.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.vision.text.Line;
import com.hm.application.R;
import com.hm.application.adapter.TbThemeAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserTab1Fragment extends Fragment {

    private LinearLayout mLlPostMain;

    public UserTab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_tab1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLlPostMain = getActivity().findViewById(R.id.llPostMain);
        if (mLlPostMain.getChildCount()> 0){
            mLlPostMain.removeAllViews();
        }
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new toDisplayInfo().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private class toDisplayInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (mLlPostMain.getChildCount()> 0){
                    mLlPostMain.removeAllViews();
                }
                JSONObject obj = new JSONObject();
                obj.put(getString(R.string.str_action_), getString(R.string.str_themes_));
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_feed) + "." + getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("HmApp", "fetch_timeline Res " + response);
                                                    JSONArray array = new JSONArray(response);
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            for (int i = 0; i < array.length(); i++) {
                                                                if (!array.getJSONObject(i).isNull("activity")) {
                                                                    if (array.getJSONObject(i).getString("activity").equals("photo")) {
                                                                        toDisplayNormalPost(array.getJSONObject(i));
                                                                    } else if (array.getJSONObject(i).getString("activity").equals("post")) {
                                                                        toDisplayNormalPost(array.getJSONObject(i));
                                                                    } else if (array.getJSONObject(i).getString("activity").equals("album")) {
                                                                        toDisplayAlbumPost(array.getJSONObject(i));
                                                                    } else {
                                                                        toDisplayNormalPost(array.getJSONObject(i));
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            CommonFunctions.toDisplayToast("Ji", getContext());
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("di", getContext());
                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("HmApp", "Post error " + error.getMessage());
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_fetch_timeline_));
                                        params.put(getString(R.string.str_uid), User.getUser(getContext()).getUid());
                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_header), getString(R.string.str_header_type));
                                        // params.put("Content-Type","application/form-data");
                                        return super.getHeaders();
                                    }
                                }
                                , getString(R.string.str_fetch_timeline_));
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void toDisplayAlbumPost(JSONObject jsonObject) {

    }

    private void toDisplayNormalPost(JSONObject jsonObject) {
        try {
            /*{
                "activity": "post",
                    "id": "2",
                    "timeline_id": "25",
                    "post": "Iron Man",
                    "image": null,
                    "like_count": "0",
                    "comment_count": "0",
                    "share_count": "0",
                    "time": "20-03-2018 16:02 PM"
            },*/
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                View itemView = inflater.inflate(R.layout.tab22_list, null, false);

                RelativeLayout mrr_header_file;
                ImageView mImgActPic,mcircle_img;
                TextView mtxt_label,mtxt_time_ago;
                LinearLayout mll_footer,mllNumber_file;
                TextView mtxt_like, mtxt_comment, mtxt_share;
                TextView mtxtNo_like, mtxtNo_comment, mtxtNo_share;

                mrr_header_file = itemView.findViewById(R.id.rr_header_file);
                mll_footer = itemView.findViewById(R.id.ll_footer);
                mllNumber_file = itemView.findViewById(R.id.llNumber_file);
                mImgActPic = itemView.findViewById(R.id.image_single);
                mcircle_img = itemView.findViewById(R.id.circle_img);
                mtxt_label = itemView.findViewById(R.id.txt_label);
                mtxt_time_ago = itemView.findViewById(R.id.txt_time_ago);
                mtxt_like = itemView.findViewById(R.id.txt_like);
                mtxt_comment = itemView.findViewById(R.id.txt_comment);
                mtxt_share = itemView.findViewById(R.id.txt_share);
                mtxtNo_like = itemView.findViewById(R.id.txtNo_like);
                mtxtNo_comment = itemView.findViewById(R.id.txtNo_comment);
                mtxtNo_share = itemView.findViewById(R.id.txtNo_share);

                if (!jsonObject.isNull("post")) {
                    mtxt_label.setText(jsonObject.getString("post"));
                }
                if (!jsonObject.isNull("like_count")) {
                    mtxtNo_like.setText(jsonObject.getString("like_count"));
                }
                if (!jsonObject.isNull("comment_count")) {
                    mtxtNo_comment.setText(jsonObject.getString("comment_count"));
                }
                if (!jsonObject.isNull("share_count")) {
                    mtxtNo_share.setText(jsonObject.getString("share_count"));
                }
                if (!jsonObject.isNull("image")) {
                    Picasso.with(getContext()).load(AppConstants.URL + jsonObject.getString("image").replaceAll("\\s", "%20")).into(mImgActPic);
                }
                mLlPostMain.addView(itemView);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toDisplayPhotoPost(JSONObject jsonObject) {
    }
}
