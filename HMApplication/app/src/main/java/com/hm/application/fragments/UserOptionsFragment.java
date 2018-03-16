package com.hm.application.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.activity.UserProfileListActivity;
import com.hm.application.utils.HmFonts;

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
        mflUSerProfile = (FrameLayout) getActivity().findViewById(R.id.flUSerProfile);
        msvUserProfileOptions = (ScrollView) getActivity().findViewById(R.id.svUserProfileOptions);

        //  Linear Layout
        mllMainUp = (LinearLayout) getActivity().findViewById(R.id.llMainUp);
        mrlProfileImage = (LinearLayout) getActivity().findViewById(R.id.rlProfileImage);
        mrlProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replacePage(new UserProfileFeaturesFragment());
            }
        });

        mllUserName = (LinearLayout) getActivity().findViewById(R.id.llUserName);
        mllMyTrips = (LinearLayout) getActivity().findViewById(R.id.llMyTrips);
        mllMyWallet = (LinearLayout) getActivity().findViewById(R.id.llMyWallet);
        mllMyRewards = (LinearLayout) getActivity().findViewById(R.id.llMyRewards);
        mllMyBucketList = (LinearLayout) getActivity().findViewById(R.id.llMyBucketList);
        mllSettings = (LinearLayout) getActivity().findViewById(R.id.llSettings);
        mllLogout = (LinearLayout) getActivity().findViewById(R.id.llLogout);

        // Image View
        mivProfilePicSmall = (ImageView) getActivity().findViewById(R.id.ivProfilePicSmall);
        mivMyTrips = (ImageView) getActivity().findViewById(R.id.ivMyTrips);
        mivMyWallet = (ImageView) getActivity().findViewById(R.id.ivMyWallet);
        mivMyRewards = (ImageView) getActivity().findViewById(R.id.ivMyRewards);
        mivMyBucketList = (ImageView) getActivity().findViewById(R.id.ivMyBucketList);
        mivSettings = (ImageView) getActivity().findViewById(R.id.ivSettings);
        mivLogout = (ImageView) getActivity().findViewById(R.id.ivLogout);

        // TextView
        mtvUserName = (TextView) getActivity().findViewById(R.id.tvUserName);
        mtvUserName.setTypeface(HmFonts.getRobotoBold(getContext()));

        mtvViewProfile = (TextView) getActivity().findViewById(R.id.tvViewProfile);
        mtvViewProfile.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyTrips = (TextView) getActivity().findViewById(R.id.tvMyTrips);
        mtvMyTrips.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyWallet = (TextView) getActivity().findViewById(R.id.tvMyWallet);
        mtvMyWallet.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyWalletValue = (TextView) getActivity().findViewById(R.id.tvMyWalletValue);
        mtvMyWalletValue.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyRewards = (TextView) getActivity().findViewById(R.id.tvMyRewards);
        mtvMyRewards.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyRewardsValue = (TextView) getActivity().findViewById(R.id.tvMyRewardsValue);
        mtvMyRewardsValue.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvMyBucketList = (TextView) getActivity().findViewById(R.id.tvMyBucketList);
        mtvMyBucketList.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvSettings = (TextView) getActivity().findViewById(R.id.tvSettings);
        mtvSettings.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtvLogout = (TextView) getActivity().findViewById(R.id.tvLogout);
        mtvLogout.setTypeface(HmFonts.getRobotoMedium(getContext()));
    }

    public void replacePage(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}