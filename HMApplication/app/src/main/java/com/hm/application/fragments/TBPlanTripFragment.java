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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;

public class TBPlanTripFragment extends Fragment {

    LinearLayout mllMainPt,mllPTDates;
    TextInputLayout mtilPTForm,mtilPTWhereTo,mtilPTStartDate,mtilPTEndDate,mtilPTNoOfTravellers,mtilNoOfRooms,mtilPTBudget,mtilPTPoint,mtilPTTransportPrefered;
    EditText medtPTFrom,medtPTWhereTo,medtPTStartDate,medtPTEndDate,medtPTNoOfTravellers,medtPTNoOfRooms,medtPTBudget,medtPTPoint;
    CheckBox mcheckbox1;
    Spinner msp_tripPlan;
    Button mbtnSubmitTrip;

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
    }

    private void onBindView(){
        mllMainPt = getActivity().findViewById(R.id.llMainPt);
        mllPTDates = getActivity().findViewById(R.id.llPTDates);
        mtilPTForm = getActivity().findViewById(R.id.tilPTForm);
        mtilPTWhereTo = getActivity().findViewById(R.id.tilPTWhereTo);
        mtilPTStartDate = getActivity().findViewById(R.id.tilPTStartDate);
        mtilPTEndDate = getActivity().findViewById(R.id.tilPTEndDate);
        mtilPTNoOfTravellers = getActivity().findViewById(R.id.tilPTNoOfTravellers);
        mtilNoOfRooms = getActivity().findViewById(R.id.tilNoOfRooms);
        mtilPTBudget = getActivity().findViewById(R.id.tilPTBudget);
        mtilPTPoint = getActivity().findViewById(R.id.tilPTPoint);
        mtilPTTransportPrefered = getActivity().findViewById(R.id.tilPTTransportPrefered);

        medtPTFrom = getActivity().findViewById (R.id.edtPTFrom);
        medtPTWhereTo = getActivity().findViewById (R.id.edtPTWhereTo);
        medtPTStartDate = getActivity().findViewById (R.id.edtPTStartDate);
        medtPTEndDate = getActivity().findViewById (R.id.edtPTEndDate);
        medtPTNoOfTravellers = getActivity().findViewById (R.id.edtPTNoOfTravellers);
        medtPTNoOfRooms = getActivity().findViewById (R.id.edtPTNoOfRooms);
        medtPTBudget = getActivity().findViewById (R.id.edtPTBudget);
        medtPTPoint = getActivity().findViewById (R.id.edtPTPoint);

        mcheckbox1 = getActivity().findViewById(R.id.checkbox1);
        msp_tripPlan = getActivity().findViewById(R.id.sp_tripPlan);
        mbtnSubmitTrip = getActivity().findViewById(R.id.btnSubmitTrip);

    }

}