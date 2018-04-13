package com.hm.application.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.adapter.TbDestinationsAdapter;
import com.hm.application.adapter.TbThemeAdapter;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TBDestinationsFragment extends Fragment {

    private NestedScrollView mnsvDestination;
    private LinearLayout mllMain1;
   private RecyclerView mRvDest;
    private TextView mtxtLblFavDest, mtxtLblFeaturedDest;
    private Spinner msprDestinations;

    public TBDestinationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tbdestinations, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onBindView();
        checkInternetConnection();
    }

    private void onBindView() {
        try {
            mnsvDestination = getActivity().findViewById(R.id.nsvDestination);
            mllMain1 = getActivity().findViewById(R.id.llDestination);
            mRvDest = getActivity().findViewById(R.id.mRvTbDestination);
            mRvDest.setNestedScrollingEnabled(false  );
            mtxtLblFavDest = getActivity().findViewById(R.id.txtLblFavDest);
            mtxtLblFavDest.setTypeface(HmFonts.getRobotoRegular(getContext()));
            mtxtLblFeaturedDest = getActivity().findViewById(R.id.txtLblFeaturedDest);
            mtxtLblFeaturedDest.setTypeface(HmFonts.getRobotoRegular(getContext()));
            msprDestinations = getActivity().findViewById(R.id.sprDestinations);

        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                new toGetDestinationInfo().execute();
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }


    private class toGetDestinationInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(getString(R.string.str_action_), getString(R.string.str_destination_));
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        AppConstants.URL + getContext().getResources().getString(R.string.str_destination_) +  getContext().getResources().getString(R.string.str_php),
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONArray array = new JSONArray(response);
                                                    if (array != null) {
                                                        if (array.length() > 0) {
                                                            mRvDest.setLayoutManager(new LinearLayoutManager(getContext()));
                                                            mRvDest.hasFixedSize();
                                                            mRvDest.setAdapter(new TbDestinationsAdapter(getContext(), array));
                                                        } else {
                                                            CommonFunctions.toDisplayToast("Ji", getContext());
                                                        }
                                                    } else {
                                                        CommonFunctions.toDisplayToast("di", getContext());
                                                    }
                                                } catch (Exception | Error e) {
                                                    e.printStackTrace();
                                                    FirebaseCrash.report(e);
                                                }
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
                                        params.put(getString(R.string.str_action_), getString(R.string.str_destination_));
                                        return params;
                                    }
                                }
                                , getContext().getString(R.string.str_destination_));
            } catch (Exception | Error e) {
                e.printStackTrace();
                FirebaseCrash.report(e);
            }
            return null;
        }
    }
}