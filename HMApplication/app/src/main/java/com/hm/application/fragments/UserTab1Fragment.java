package com.hm.application.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hm.application.R;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.classes.UserTimeLinePost;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class UserTab1Fragment extends Fragment {

    private LinearLayout mLlPostMain;
    private JSONArray array;
    private String uid;

    private OnFragmentInteractionListener mListener;

    public UserTab1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        void toSetTitle(String s, boolean b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_tab1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLlPostMain = getActivity().findViewById(R.id.llPostMain);
        if (mLlPostMain.getChildCount() > 0) {
            mLlPostMain.removeAllViews();
        }
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            uid = User.getUser(getContext()).getUid();
            if (CommonFunctions.isOnline(getContext())) {
                Log.d("HmApp", "  agr fetch_timeline " + getArguments());
                if (getArguments() != null) {
                    if (getArguments().getBoolean("other_user")) {
                        Log.d("HmApp", "  agr fetch_timeline" + getArguments().getString("follow_following_fetch"));
                        if (getArguments().getString("fetch_timeline") != null) {
                            toDisplayData(getArguments().getString("fetch_timeline"));
                        } else if (getArguments().getString(AppConstants.F_UID) != null) {
                            uid = getArguments().getString(AppConstants.F_UID);
                            new toGetData().execute();
                        } else {
                            new toGetData().execute();

                        }
                    } else {
                        new toGetData().execute();
                    }
                } else {
                    new toGetData().execute();
                }
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
//    private void checkInternetConnection() {
//        try {
//            if (CommonFunctions.isOnline(getContext())) {
//                if (getArguments() != null) {
//                    Log.d("HmApp", " tab1 fetch_timeline" + getArguments().getString("fetch_timeline"));
//                    if (getArguments().getString("fetch_timeline") != null){
//                        toDisplayData(getArguments().getString("fetch_timeline"));
//                    } else {
//                        new toDisplayInfo().execute();
//                    }
//                } else {
//                    new toDisplayInfo().execute();
//                }
//            } else {
//                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
//            }
//        } catch (Exception | Error e) {
//            e.printStackTrace();
//        }
//    }

    private class toGetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (mLlPostMain.getChildCount() > 0) {
                    mLlPostMain.removeAllViews();
                }
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getString(R.string.str_feed) + getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                toDisplayData(response);

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
                                        params.put(getString(R.string.str_action_), getString(R.string.str_fetch_timeline_));
                                        params.put(getString(R.string.str_uid), uid);

                                        return params;
                                    }
                                }
                                , getString(R.string.str_fetch_timeline_));
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void toDisplayData(String response) {
        try {
            Log.d("HmApp", "fetch_timeline Res " + response);
            array = new JSONArray(response);
            if (array != null) {
                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        if (!array.getJSONObject(i).isNull(getString(R.string.str_activity_small))) {
                            if (array.getJSONObject(i).getString(getString(R.string.str_activity_small)).equals(getString(R.string.str_photo_small))) {
                                UserTimeLinePost.toDisplayNormalPost(array.getJSONObject(i), getContext(), mLlPostMain, i, UserTab1Fragment.this);
                            } else if (array.getJSONObject(i).getString(getString(R.string.str_activity_small)).equals(getString(R.string.str_post_small))) {
                                UserTimeLinePost.toDisplayNormalPost(array.getJSONObject(i), getContext(), mLlPostMain, i, UserTab1Fragment.this);
                            } else if (array.getJSONObject(i).getString(getString(R.string.str_activity_small)).equals(getString(R.string.str_album_small))) {
                                UserTimeLinePost.toDisplayPhotoPost(array.getJSONObject(i), getContext(), mLlPostMain, i, UserTab1Fragment.this);
                            } else {
                                UserTimeLinePost.toDisplayNormalPost(array.getJSONObject(i), getContext(), mLlPostMain, i, UserTab1Fragment.this);
                            }
                        }
                    }
                } else {
                    CommonFunctions.toDisplayToast(getString(R.string.str_no_post_found), getContext());
                }
            } else {
                CommonFunctions.toDisplayToast(getString(R.string.str_no_post_found), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public void toCallSinglePostData(int position, String from) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.BUNDLE, array.getJSONObject(position).toString());
            bundle.putString(AppConstants.FROM, from);
            SinglePostDataFragment singlePostDataFragment = new SinglePostDataFragment();
            singlePostDataFragment.setArguments(bundle);
            ((UserInfoActivity) getContext()).replaceMainHomePage(singlePostDataFragment);

        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}