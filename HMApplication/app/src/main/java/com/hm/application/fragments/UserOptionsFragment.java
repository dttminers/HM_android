package com.hm.application.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.user_data.LoginActivity;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

public class UserOptionsFragment extends Fragment {

    private FrameLayout mflUSerProfile;
    private ScrollView msvUserProfileOptions;
    private LinearLayout mllMainUp, mrlProfileImage, mllUserName, mllMyTrips, mllMyWallet, mllMyRewards, mllMyBucketList, mllSettings, mllLogout;
    private ImageView mivProfilePicSmall, mivMyTrips, mivMyWallet, mivMyRewards, mivMyBucketList, mivSettings, mivLogout;
    private TextView mtvUserName, mtvViewProfile, mtvMyTrips, mtvMyWallet, mtvMyWalletValue, mtvMyRewards, mtvMyRewardsValue, mtvMyBucketList, mtvSettings, mtvLogout;

    public UserOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_options, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toBindViews();
    }

    private void toBindViews() {
        mflUSerProfile = getActivity().findViewById(R.id.flUSerProfile);
        msvUserProfileOptions = getActivity().findViewById(R.id.svUserProfileOptions);

        //  Linear Layout
        mllMainUp = getActivity().findViewById(R.id.llMainUp);
        mrlProfileImage = getActivity().findViewById(R.id.rlProfileImage);
        mrlProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replacePage(new UserInfoFragment());
//                startActivity(new Intent(getContext(), UserInfoActivity.class));
            }
        });

        mllUserName = getActivity().findViewById(R.id.llUserName);
        mllMyTrips = getActivity().findViewById(R.id.llMyTrips);
        mllMyWallet = getActivity().findViewById(R.id.llMyWallet);
        mllMyRewards = getActivity().findViewById(R.id.llMyRewards);
        mllMyBucketList = getActivity().findViewById(R.id.llMyBucketList);
        mllSettings = getActivity().findViewById(R.id.llSettings);
        mllLogout = getActivity().findViewById(R.id.llLogout);

        // Image View
        mivProfilePicSmall = getActivity().findViewById(R.id.ivProfilePicSmall);
        mivMyTrips = getActivity().findViewById(R.id.ivMyTrips);
        mivMyWallet = getActivity().findViewById(R.id.ivMyWallet);
        mivMyRewards = getActivity().findViewById(R.id.ivMyRewards);
        mivMyBucketList = getActivity().findViewById(R.id.ivMyBucketList);
        mivSettings = getActivity().findViewById(R.id.ivSettings);
        mivLogout = getActivity().findViewById(R.id.ivLogout);

        // TextView
        mtvUserName = getActivity().findViewById(R.id.tvUserName);
        mtvUserName.setTypeface(HmFonts.getRobotoMedium(getContext()));
        mtvUserName.setText(User.getUser(getContext()).getUsername());

        mtvViewProfile = getActivity().findViewById(R.id.tvViewProfile);
        mtvViewProfile.setTypeface(HmFonts.getRobotoRegular(getContext()));

        mtvMyTrips = getActivity().findViewById(R.id.tvMyTrips);
        mtvMyTrips.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyWallet = getActivity().findViewById(R.id.tvMyWallet);
        mtvMyWallet.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyWalletValue = getActivity().findViewById(R.id.tvMyWalletValue);
        mtvMyWalletValue.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyRewards = getActivity().findViewById(R.id.tvMyRewards);
        mtvMyRewards.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyRewardsValue = getActivity().findViewById(R.id.tvMyRewardsValue);
        mtvMyRewardsValue.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyBucketList = getActivity().findViewById(R.id.tvMyBucketList);
        mtvMyBucketList.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvSettings = getActivity().findViewById(R.id.tvSettings);
        mtvSettings.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvLogout = getActivity().findViewById(R.id.tvLogout);
        mtvLogout.setTypeface(HmFonts.getRobotoMedium(getContext()));

        if (User.getUser(getContext()).getPicPath() != null) {
            Picasso.with(getContext())
                    .load(AppConstants.URL + User.getUser(getContext()).getPicPath().replaceAll("\\s", "%20")).into(mivProfilePicSmall);
        }

        mllLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    User.getUser(getContext()).setUid(null);
                    User.getUser(getContext()).setName(null);
                    User.getUser(getContext()).setUsername(null);
                    User.getUser(getContext()).setEmail(null);
                    User.getUser(getContext()).setMobile(null);
                    User.getUser(getContext()).setDob(null);
                    User.getUser(getContext()).setPicPath(null);
                    User.getUser(getContext()).setLivesIn(null);
                    User.getUser(getContext()).setReferralCode(null);
                    User.getUser(getContext()).setFcmToken(null);
                    User.getUser(getContext()).setNotificationCount(0);

                    AppDataStorage.setUserInfo(getContext());
                    startActivity(new Intent(getContext(), LoginActivity.class).putExtra(AppConstants.USERDATA, AppConstants.LOGIN));

                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void replacePage(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}