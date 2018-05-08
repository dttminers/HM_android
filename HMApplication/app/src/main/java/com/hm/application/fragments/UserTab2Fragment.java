package com.hm.application.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;

public class UserTab2Fragment extends Fragment {

    Bundle bundle;
    private TabLayout tabLayout;
    private OnFragmentInteractionListener mListener;

    public UserTab2Fragment() {
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
        return inflater.inflate(R.layout.fragment_user_tab2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkInternetConnection();

        tabLayout = getActivity().findViewById(R.id.tbUsersTab2);
        replacePage(new UserTab21Fragment());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replacePage(new UserTab21Fragment());
                        break;
                    case 1:
                        replacePage(new UserTab22Fragment());
                        break;
                    case 2:
                        replacePage(new UserTab23Fragment());
                        break;
                    case 3:
                        replacePage(new UserTab24Fragment());
                        break;
                    default:
                        replacePage(new UserTab21Fragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void checkInternetConnection() {
        try {
            if (CommonFunctions.isOnline(getContext())) {
                Log.d("HmApp", "  agr fetch_timeline 1 " + getArguments());
                if (getArguments() != null) {
                    bundle = new Bundle();
                    Log.d("HmApp", " agr2 " + getArguments().getBoolean("other_user"));
                    bundle.putBoolean("other_user2", getArguments().getBoolean("other_user"));
                    bundle.putString(AppConstants.F_UID, getArguments().getString("F_UID"));
                    bundle.putString("fetch_photos2", getArguments().getString("fetch_photos"));
                    bundle.putString("fetch_albums2", getArguments().getString("fetch_albums"));
                }
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    public void replacePage(Fragment fragment) {
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flUsersTab2Container, fragment)
//                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

        void toSetTitle(String s, boolean b);
    }

}
