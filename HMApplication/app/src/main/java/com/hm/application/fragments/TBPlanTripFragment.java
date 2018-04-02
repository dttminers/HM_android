package com.hm.application.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.hm.application.R;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.KeyBoard;

public class TBPlanTripFragment extends Fragment {

    LinearLayout mllMainPt,mllPTDates;
    TextInputLayout mTilPTForm,mTilPTWhereTo,mTilPTStartDate,mTilPTEndDate, mTilPTNoOfTravellers,mTilNoOfRooms,mTilPTBudget,mTilPTPoint,mTilPTTransportPrefered;
    EditText mEdtPTFrom,mEdtPTWhereTo,mEdtPTStartDate,mEdtPTEndDate,mEdtPTNoOfTravellers,mEdtPTNoOfRooms,mEdtPTBudget,mEdtPTPoint;
    CheckBox mCbCdn;
    Spinner mSpTripPlan;
    Button mBtnSubmitTrip;

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
        mTilPTForm = getActivity().findViewById(R.id.tilPTForm);
        mTilPTWhereTo = getActivity().findViewById(R.id.tilPTWhereTo);
        mTilPTStartDate = getActivity().findViewById(R.id.tilPTStartDate);
        mTilPTEndDate = getActivity().findViewById(R.id.tilPTEndDate);
        mTilPTNoOfTravellers = getActivity().findViewById(R.id.tilPTNoOfTravellers);
        mTilNoOfRooms = getActivity().findViewById(R.id.tilNoOfRooms);
        mTilPTBudget = getActivity().findViewById(R.id.tilPTBudget);
        mTilPTPoint = getActivity().findViewById(R.id.tilPTPoint);
        mTilPTTransportPrefered = getActivity().findViewById(R.id.tilPTTransportPrefered);

        mEdtPTFrom = getActivity().findViewById (R.id.edtPTFrom);
        mEdtPTWhereTo = getActivity().findViewById (R.id.edtPTWhereTo);
        mEdtPTStartDate = getActivity().findViewById (R.id.edtPTStartDate);
        mEdtPTEndDate = getActivity().findViewById (R.id.edtPTEndDate);
        mEdtPTNoOfTravellers = getActivity().findViewById (R.id.edtPTNoOfTravellers);
        mEdtPTNoOfRooms = getActivity().findViewById (R.id.edtPTNoOfRooms);
        mEdtPTBudget = getActivity().findViewById (R.id.edtPTBudget);
        mEdtPTPoint = getActivity().findViewById (R.id.edtPTPoint);

        mCbCdn = getActivity().findViewById(R.id.checkbox1);
        mSpTripPlan = getActivity().findViewById(R.id.sp_tripPlan);
        mBtnSubmitTrip = getActivity().findViewById(R.id.btnSubmitTrip);

        mEdtPTStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                KeyBoard.hideKeyboard(TBPlanTripFragment.this);
//                    Log.d("HmAPp", " DatePicker : " + CommonFunctions.toOpenDatePicker(UserInfoActivity.this));
                CommonFunctions.toOpenDatePicker(getContext(),mEdtPTStartDate);
            }
        });

    }

}