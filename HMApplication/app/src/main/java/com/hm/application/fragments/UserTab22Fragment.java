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
import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.adapter.UserTab22Adapter;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class UserTab22Fragment extends Fragment {

    String uid;

    private OnFragmentInteractionListener mListener;

    public UserTab22Fragment() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_tab22, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            uid = User.getUser(getContext()).getUid();
            if (CommonFunctions.isOnline(getContext())) {
                Log.d("HmApp", "  agr fetch_photos grid 1 " + getArguments());
                if (getArguments() != null) {
                    if (getArguments().getBoolean("other_user2")) {
                        Log.d("HmApp", "  agr fetch_photos grid " + getArguments().getString("fetch_photos2"));
                        if (getArguments().getString("fetch_photos2") != null) {
                            toDisplayData(getArguments().getString("fetch_photos2"));
//                        } else if (getArguments().getString(AppConstants.F_UID) != null) {
//                            uid = getArguments().getString(AppConstants.F_UID);
//                            new toGetData().execute();
//                        } else {
//                            new toGetData().execute();
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
            Crashlytics.logException(e);

        }
    }

    private void toDisplayData(String res) {
        try { if (res != null && res.trim().length()>0){
            Log.d("HmApp", "fetch_photos Res " + res);
            JSONArray array = new JSONArray(res.trim());
            if (array != null) {
                if (array.length() > 0) {
                    RecyclerView mRv = getActivity().findViewById(R.id.rvUSerTab22);
                    mRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRv.hasFixedSize();
                    mRv.setAdapter(new UserTab22Adapter(getContext(), array));
                    mRv.setNestedScrollingEnabled(false);
                } else {
                    CommonFunctions.toDisplayToast("No Data", getContext());
                }
            } else {
                CommonFunctions.toDisplayToast("No Data", getContext());
            }} else {
                CommonFunctions.toDisplayToast("No Data", getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class toGetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
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
                                                Crashlytics.logException(error);
                                                CommonFunctions.toCloseLoader();
                                            }

                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_fetch_photos));
                                        params.put(getString(R.string.str_uid), uid);
                                        return params;
                                    }
                                }
                                , getString(R.string.str_fetch_photos));
            } catch (Exception | Error e) {
                e.printStackTrace(); Crashlytics.logException(e);

            }
            return null;
        }
    }
}