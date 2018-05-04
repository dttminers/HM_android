package com.hm.application.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.hm.application.R;
import com.hm.application.adapter.UserTab24Adapter;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserTab24Fragment extends Fragment {

    String uid;
    private OnFragmentInteractionListener mListener;

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

    private int GALLERY = 1, CAMERA = 2, SELECT_PICTURES = 7;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    public UserTab24Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_tab24, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        try {
            uid = User.getUser(getContext()).getUid();
            if (CommonFunctions.isOnline(getContext())) {
                Log.d("HmApp", "  agr fetch_albums 1 " + getArguments());
                if (getArguments() != null) {
                    if (getArguments().getBoolean("other_user2")) {
                        Log.d("HmApp", "  agr fetch_albums" + getArguments().getString("fetch_albums2"));
                        if (getArguments().getString("fetch_albums2") != null) {
                            toDisplayData(getArguments().getString("fetch_albums2"));
//                        } else if (getArguments().getString(AppConstants.F_UID) != null) {
//                            uid = getArguments().getString(AppConstants.F_UID);
//                            new toGetData().execute();
//                        } else {
//                            new toGetData().execute();
                        }
                    } else {
                        new toGetData().execute();
                    }
                } else {
                    new toGetData().execute();
                }
            } else {
                CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    private class toGetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                VolleySingleton.getInstance(getContext())
                        .addToRequestQueue(
                                new StringRequest(Request.Method.POST,
                                        "http://vnoi.in/hmapi/feed.php",
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                toDisplayData(response);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                error.printStackTrace();
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(getString(R.string.str_action_), getString(R.string.str_fetch_albums_));
                                        params.put(getString(R.string.str_uid), uid);
                                        return params;
                                    }
                                }
                                , getString(R.string.str_fetch_albums_));
            } catch (Exception | Error e) {
                e.printStackTrace();

            }
            return null;
        }
    }

    private void toDisplayData(String response) {
        try {
            JSONArray array = new JSONArray(response);
            if (array != null) {
                if (array.length() > 0) {
                    RecyclerView mRv = getActivity().findViewById(R.id.rvUSerTab24);
                    mRv.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    mRv.hasFixedSize();
                    mRv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                    mRv.setAdapter(new UserTab24Adapter(getContext(), array, UserTab24Fragment.this));
                    mRv.setNestedScrollingEnabled(false);
                } else {
                    CommonFunctions.toDisplayToast("Ji", getContext());
                }
            } else {
                CommonFunctions.toDisplayToast("di", getContext());
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    public void multiSelectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**These following line is the important one!
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES); //SELECT_PICTURES is simply a global int used to check the calling intent in onActivityResult
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("HmApp", " Result : " + requestCode);

        if (requestCode == SELECT_PICTURES) {
            if (resultCode == Activity.RESULT_OK) {
//                Log.d("HmApp", " Data " + data.getExtras());
//                Log.d("HmApp", " Data " + data.getClipData());
//                Log.d("HmApp", " Data " + data.getType());
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;
                    ArrayList<Uri> images = new ArrayList<>();
                    while (currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        images.add(imageUri);
                        Log.d("HmApp", " Image uri : " + imageUri + ":" + imageUri.toString());
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        currentItem = currentItem + 1;
                    }

                    Log.d("HmApp", " " + count + " : " + images);
//                    UserData.toSendMultiImages(images, getContext(),getActivity(), "");
                } else if (data.getData() != null) {
                    String imagePath = data.getData().getPath();
                    Log.d("HmApp", " " + imagePath
                    );
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }

            }
        }
    }
}