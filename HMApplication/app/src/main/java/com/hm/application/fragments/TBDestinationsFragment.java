package com.hm.application.fragments;


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
import com.hm.application.R;
import com.hm.application.adapter.adapterClass;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

public class TBDestinationsFragment extends Fragment {


    public TBDestinationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tbdestinations, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.Destination);

        VolleySingleton.getInstance(getContext())
                .addToRequestQueue(
                        new StringRequest(Request.Method.POST,

                                AppConstants.URL + getContext().getResources().getString(R.string.str_destination) + "." + getContext().getResources().getString(R.string.str_php),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.d("Response", response.toString());

                                            JSONArray jsonArray = response.getJsonArray;
                                            Log.d("Response", " array " + response.getJSONArray("result"));

                                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                            recyclerView.setHasFixedSize(true);
                                            recyclerView.setAdapter(new adapterClass(getContext(), response.getJSONArray("result")));

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }));

    }
}