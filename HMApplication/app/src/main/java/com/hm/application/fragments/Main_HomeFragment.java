package com.hm.application.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.PostObjRequest;
import com.hm.application.network.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main_HomeFragment extends Fragment {


    public Main_HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_home, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void replacePage(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(fragment.getClass().getName())
                .replace(R.id.flHomeContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}