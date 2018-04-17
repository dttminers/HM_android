package com.hm.application.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.adapter.UserFollowingListAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;


public class UserFollowingListFragment extends Fragment {

    String uid;
    private OnFragmentInteractionListener mListener;

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

    public UserFollowingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.rv_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkInternetConnection();
        mListener.toSetTitle("Following", false);

    }

    private void checkInternetConnection() {
        try {
            uid = User.getUser(getContext()).getUid();
            if (CommonFunctions.isOnline(getContext())) {
                Log.d("HmApp", "  agr follow_following_fetch " + getArguments());
                if (getArguments() != null) {
                    if (getArguments().getBoolean("other_user")) {
                        Log.d("HmApp", "  agr follow_following_fetch" + getArguments().getString("follow_following_fetch"));
                        if (getArguments().getString("follow_following_fetch") != null) {
                            toDisplayData(getArguments().getString("follow_following_fetch"));
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
            FirebaseCrash.report(e);
        }
    }

    private void toDisplayData(String response) {
        try {
            Log.d("HmApp", "follower_fetch Res " + response);
            JSONArray array = new JSONArray(response);
            if (array != null) {
                if (array.length() > 0) {
                    RecyclerView mRv = getActivity().findViewById(R.id.mRvCommon);
                    mRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRv.hasFixedSize();
                    mRv.setNestedScrollingEnabled(false);
                    mRv.setAdapter(new UserFollowingListAdapter(getContext(), array));
                } else {
                    CommonFunctions.toDisplayToast(getString(R.string.str_data_not_found), getContext());
                }
            } else {
                CommonFunctions.toDisplayToast(getString(R.string.str_data_not_found), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private class toGetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        "http://vnoi.in/hmapi/follow_data.php",
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                toDisplayData(response);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("HmApp", "follower_fetch error " + error.getMessage());
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_follow_following_fetch_));
                                        params.put(getString(R.string.str_uid), uid);
                                        Log.d("HmApp", " follow_following_fetch params " + params);
                                        return params;
                                    }
                                }
                                , getString(R.string.str_follow_following_fetch_));
            } catch (Exception | Error e) {
                e.printStackTrace();
                FirebaseCrash.report(e);
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CommonFunctions.toCallLoader(getContext(), "Loading");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CommonFunctions.toCloseLoader();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            CommonFunctions.toCloseLoader();
        }
    }
}