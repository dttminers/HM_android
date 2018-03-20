package com.hm.application.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hm.application.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab2Fragment extends Fragment {

    private TabLayout tabLayout;

    public UserTab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_tab2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

    public void replacePage(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flUsersTab2Container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

}
