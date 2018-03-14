package com.hm.application.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.WindowDecorActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.utils.HmFonts;

public class UserProfileFeaturesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    ScrollView msvUpMain;
    LinearLayout mllUpMain,mllUserActivities;
    RelativeLayout mrlProfileImageData,mrlUserData,mrlUserData2;
    View mv11;
    FrameLayout mflUsersDataContainer;
    RatingBar mrbUserRatingData;
    ImageView mimgProfilePic,mivFlag,mivShare;
    TextView mtvUserFollowing,mtvUserFollowers,mtxtUserName,mtxtUserExtraActivities,mtxtUsersReferralCode,mtxtUsersDescription;
    Button mbtnFollow;
    TabItem mtbiUsersFeed,mtbiPhotos,mtbiUsersActivities;
    TabLayout mtbUsersActivity;

    public UserProfileFeaturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            databinding();
        return inflater.inflate(R.layout.fragment_user_profile_features, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private  void  databinding(){

        msvUpMain = (ScrollView) getActivity().findViewById(R.id.svUpMain);

        mllUpMain = (LinearLayout)getActivity().findViewById(R.id.llUpMain);
        mllUserActivities = (LinearLayout)getActivity().findViewById(R.id.llUserActivities);

        mrlProfileImageData = (RelativeLayout)getActivity().findViewById(R.id.rlProfileImageData);
        mrlUserData = (RelativeLayout)getActivity().findViewById(R.id.rlUserData);
        mrlUserData2 = (RelativeLayout)getActivity().findViewById(R.id.rlUserData2);

        mv11 = (View) getActivity().findViewById(R.id.v11);

        mflUsersDataContainer= (FrameLayout)getActivity().findViewById(R.id.flUsersDataContainer);

        mrbUserRatingData = (RatingBar)getActivity().findViewById(R.id.rbUserRatingData);

        mimgProfilePic = (ImageView) getActivity().findViewById(R.id.imgProfilePic);
        mivFlag = (ImageView)getActivity().findViewById(R.id.ivFlag);
        mivShare = (ImageView) getActivity().findViewById(R.id.ivShare);

        mtvUserFollowing = (TextView) getActivity().findViewById(R.id.tvUserFollowing);
        mtvUserFollowing.setTypeface(HmFonts.getRobotoMedium(getContext()));
        mtvUserFollowers = (TextView) getActivity().findViewById(R.id.tvUserFollowers);
        mtvUserFollowers.setTypeface(HmFonts.getRobotoMedium(getContext()));
        mtxtUserName = (TextView) getActivity().findViewById(R.id.txtUserName);
        mtxtUserName.setTypeface(HmFonts.getRobotoBold(getContext()));
        mtxtUserExtraActivities = (TextView) getActivity().findViewById(R.id.txtUserExtraActivities);
        mtxtUserExtraActivities.setTypeface(HmFonts.getRobotoMedium(getContext()));
        mtxtUsersReferralCode = (TextView) getActivity().findViewById(R.id.txtUsersReferralCode);
        mtxtUsersReferralCode.setTypeface(HmFonts.getRobotoBold(getContext()));
        mtxtUsersDescription = (TextView) getActivity().findViewById(R.id.txtUsersDescription);
        mtxtUsersDescription.setTypeface(HmFonts.getRobotoBold(getContext()));

        mbtnFollow = (Button)getActivity().findViewById(R.id.btnFollow);
        mtvUserFollowing.setTypeface(HmFonts.getRobotoMedium(getContext()));

        mtbiUsersFeed = (TabItem)getActivity().findViewById(R.id.tbiUsersFeed);
        mtbiPhotos = (TabItem)getActivity().findViewById(R.id.tbiPhotos);
        mtbiUsersActivities = (TabItem)getActivity().findViewById(R.id.tbiUsersActivities);

        mtbUsersActivity = (TabLayout)getActivity().findViewById(R.id.tbUsersActivity);

















    }
}