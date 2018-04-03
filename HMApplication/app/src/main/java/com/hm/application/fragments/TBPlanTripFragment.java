package com.hm.application.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.classes.Tb_PlanTrip_Travellers_Info;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.hm.application.utils.KeyBoard;

import java.util.regex.Pattern;

public class TBPlanTripFragment extends Fragment {

    LinearLayout mllMainPt, mllPTDates;
    TextInputLayout mTilPTForm, mTilPTWhereTo, mTilPTStartDate, mTilPTEndDate, mTilPTNoOfTravellers, mTilNoOfRooms, mTilPTBudget, mTilPTPoint, mTilPTTransportPrefered;
    EditText mEdtPTFrom, mEdtPTWhereTo, mEdtPTStartDate, mEdtPTEndDate, mEdtPTNoOfTravellers, mEdtPTNoOfRooms, mEdtPTBudget, mEdtPTPoint;
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
        onBindView();
//        toValidateAll();

    }

    public boolean toValidateAll() {
        ValidateFrom();
        ValidateWhere();
        ValidateBudget();
        ValidatePTMind();
        ValidateStartDate();
        ValidateEndDate();
        return ValidateFrom() && ValidateWhere() && ValidateBudget() && ValidatePTMind() && ValidateStartDate() && ValidateEndDate();

    }

    private void onBindView() {
        mllMainPt = getActivity().findViewById(R.id.llMainPt);
        mllPTDates = getActivity().findViewById(R.id.llPTDates);

        mTilPTForm = getActivity().findViewById(R.id.tilPTForm);
        mTilPTForm.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mTilPTWhereTo = getActivity().findViewById(R.id.tilPTWhereTo);
        mTilPTWhereTo.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mTilPTStartDate = getActivity().findViewById(R.id.tilPTStartDate);
        mTilPTStartDate.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mTilPTEndDate = getActivity().findViewById(R.id.tilPTEndDate);
        mTilPTEndDate.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mTilPTNoOfTravellers = getActivity().findViewById(R.id.tilPTNoOfTravellers);
        mTilPTNoOfTravellers.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mTilNoOfRooms = getActivity().findViewById(R.id.tilNoOfRooms);
        mTilNoOfRooms.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mTilPTBudget = getActivity().findViewById(R.id.tilPTBudget);
        mTilPTBudget.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mTilPTPoint = getActivity().findViewById(R.id.tilPTPoint);
        mTilPTPoint.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mTilPTTransportPrefered = getActivity().findViewById(R.id.tilPTTransportPrefered);
        mTilPTTransportPrefered.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mEdtPTFrom = getActivity().findViewById(R.id.edtPTFrom);
        mEdtPTFrom.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mEdtPTWhereTo = getActivity().findViewById(R.id.edtPTWhereTo);
        mEdtPTWhereTo.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mEdtPTStartDate = getActivity().findViewById(R.id.edtPTStartDate);
        mEdtPTStartDate.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mEdtPTEndDate = getActivity().findViewById(R.id.edtPTEndDate);
        mEdtPTEndDate.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mEdtPTNoOfTravellers = getActivity().findViewById(R.id.edtPTNoOfTravellers);
        mEdtPTNoOfTravellers.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mEdtPTNoOfRooms = getActivity().findViewById(R.id.edtPTNoOfRooms);
        mEdtPTNoOfRooms.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mEdtPTBudget = getActivity().findViewById(R.id.edtPTBudget);
        mEdtPTBudget.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mEdtPTPoint = getActivity().findViewById(R.id.edtPTPoint);
        mEdtPTPoint.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mCbCdn = getActivity().findViewById(R.id.checkbox1);
        mCbCdn.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mSpTripPlan = getActivity().findViewById(R.id.sp_tripPlan);

        mBtnSubmitTrip = getActivity().findViewById(R.id.btnSubmitTrip);
        mBtnSubmitTrip.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mEdtPTStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoard.hideKeyboard(getActivity());
                CommonFunctions.toOpenDatePicker(getContext(), mEdtPTStartDate);
            }
        });

        mEdtPTEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoard.hideKeyboard(getActivity());
                CommonFunctions.toOpenDatePicker(getContext(), mEdtPTEndDate);
            }
        });

        mEdtPTNoOfTravellers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tb_PlanTrip_Travellers_Info.toFillTravellersInfo(getContext());
            }
        });

        mEdtPTNoOfRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tb_PlanTrip_Travellers_Info.toFillTravellersRoomInfo(getContext());
            }
        });

        mEdtPTFrom.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (ValidateFrom()) {
                        mEdtPTFrom.requestFocus();
                        return true;
                    } else {
                        mEdtPTFrom.requestFocus();
                        return false;
                    }
                } else {
                    return false;
                }
            }
        });

        mEdtPTWhereTo.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (ValidateWhere()) {
                        mEdtPTWhereTo.requestFocus();
                        return true;
                    } else {
                        mEdtPTWhereTo.requestFocus();
                        return false;
                    }

                } else {
                    return false;
                }
            }
        });

        mEdtPTBudget.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (ValidateBudget()) {
                        mEdtPTBudget.requestFocus();
                        return true;
                    } else {
                        mEdtPTBudget.requestFocus();
                        return false;
                    }
                } else {
                    return false;
                }

            }
        });

        mEdtPTPoint.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (ValidatePTMind()) {
                        mEdtPTPoint.requestFocus();
                        return true;
                    } else {
                        mEdtPTPoint.requestFocus();
                        return false;
                    }
                } else {
                    return false;
                }
            }
        });

        mEdtPTStartDate.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (ValidateStartDate()) {
                        mEdtPTStartDate.requestFocus();
                        return true;
                    } else {
                        mEdtPTStartDate.requestFocus();
                        return false;
                    }
                } else {
                    return false;
                }
            }
        });

        mEdtPTEndDate.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (ValidateEndDate()) {
                        mEdtPTEndDate.requestFocus();
                        return true;
                    } else {
                        mEdtPTEndDate.requestFocus();
                        return false;
                    }
                } else {
                    return false;
                }
            }
        });

        mEdtPTFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ValidateFrom();
            }

            @Override
            public void afterTextChanged(Editable s) {
                ValidateFrom();
            }
        });

        mEdtPTWhereTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ValidateWhere();
            }

            @Override
            public void afterTextChanged(Editable s) {
                ValidateWhere();
            }
        });

        mEdtPTBudget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ValidateBudget();
            }

            @Override
            public void afterTextChanged(Editable s) {
                ValidateBudget();
            }
        });

        mEdtPTPoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ValidatePTMind();
            }

            @Override
            public void afterTextChanged(Editable s) {
                ValidatePTMind();
            }
        });

        mEdtPTStartDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ValidateStartDate();
            }

            @Override
            public void afterTextChanged(Editable s) {
                ValidateStartDate();
            }
        });

        mEdtPTEndDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ValidateEndDate();
            }

            @Override
            public void afterTextChanged(Editable s) {
                ValidateEndDate();
            }
        });
    }


    public boolean ValidateFrom() {
        if (mEdtPTFrom.getText().toString().trim().length() == 0) {
            mTilPTForm.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtPTFrom.getText().toString().trim().length() < 3) {
            mTilPTForm.setError(getString(R.string.str_error_minimun_3));
            return false;
        } else if (mEdtPTFrom.getText().toString().trim().length() > 20) {
            mTilPTForm.setError(getString(R.string.str_error_max_20));
            return false;
        } else {
            mTilPTForm.setError(null);
            return true;
        }
    }

    public boolean ValidateWhere() {
        if (mEdtPTWhereTo.getText().toString().trim().length() == 0) {
            mTilPTWhereTo.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtPTWhereTo.getText().toString().trim().length() < 3) {
            mTilPTWhereTo.setError(getString(R.string.str_error_minimun_3));
            return false;
        } else if (mEdtPTWhereTo.getText().toString().trim().length() > 20) {
            mTilPTWhereTo.setError(getString(R.string.str_error_max_20));
            return false;
        } else {
            mTilPTWhereTo.setError(null);
            return true;
        }
    }

    public boolean ValidateBudget() {
        if (mEdtPTBudget.getText().toString().trim().length() == 0) {
            mTilPTBudget.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtPTBudget.getText().toString().trim().length() < 3) {
            mTilPTBudget.setError(getString(R.string.str_error_minimun_3));
            return false;
        } else if (!Pattern.compile("^[0-9]*$").matcher(mEdtPTBudget.getText().toString().trim()).matches()) {
            mTilPTBudget.setError(getString(R.string.str_error_valid_mobile_no));
            return false;
        } else {
            mTilPTBudget.setError(null);
            return true;
        }
    }

    public boolean ValidatePTMind() {
        if (mEdtPTPoint.getText().toString().trim().length() == 0) {
            mTilPTPoint.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (mEdtPTWhereTo.getText().toString().trim().length() < 3) {
            mTilPTPoint.setError(getString(R.string.str_error_minimun_3));
            return false;
        } else if (mEdtPTWhereTo.getText().toString().trim().length() > 20) {
            mTilPTPoint.setError(getString(R.string.str_error_max_20));
            return false;
        } else {
            mTilPTPoint.setError(null);
            return true;
        }
    }

    public boolean ValidateStartDate() {
        if (mEdtPTStartDate.getText().toString().length() == 0) {
            mTilPTStartDate.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (!Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d{2}$").matcher(mEdtPTStartDate.getText().toString().trim()).matches()) {
            mTilPTStartDate.setError(getString(R.string.str_err));
            return false;
        } else {
            mTilPTStartDate.setError(null);
            return true;
        }
    }

    public boolean ValidateEndDate() {
        if (mEdtPTEndDate.getText().toString().length() == 0) {
            mTilPTEndDate.setError(getString(R.string.str_field_cant_be_empty));
            return false;
        } else if (!Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d{2}$").matcher(mEdtPTEndDate.getText().toString().trim()).matches()) {
            mTilPTEndDate.setError(getString(R.string.str_err));
            return false;
        } else {
            mTilPTEndDate.setError(null);
            return true;
        }
    }
}