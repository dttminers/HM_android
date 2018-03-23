package com.hm.application.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;

public class TBPlanTripFragment extends Fragment {

    TextInputLayout mtilPTNoOfTravellers,mtilNoOfRooms;
    EditText medtPTNoOfTravellers,medtPTNoOfRooms;

    public TBPlanTripFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tbplan_trip, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        medtPTNoOfTravellers = getActivity().findViewById(R.id.edtPTNoOfTravellers);
        mtilPTNoOfTravellers = getActivity().findViewById(R.id.tilPTNoOfTravellers);
    }

}