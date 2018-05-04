package com.hm.application.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.network.VolleySingleton;
import com.hm.application.model.User;
import com.hm.application.utils.CommonFunctions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TagFragment extends Fragment {

    private MultiAutoCompleteTextView multiAutoCompleteTextView;
    private ArrayList<String> tag_list = new ArrayList<>();

    public TagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            checkInternetConnection();

            // Find the MultiAutoCompleteTextView object.
            multiAutoCompleteTextView = getActivity().findViewById(R.id.multiAutoCompleteTextViewEmail);

            // Set multiAutoCompleteTextView related attribute value in java code.
            multiAutoCompleteTextView.setPadding(15, 15, 15, 15);
            multiAutoCompleteTextView.setBackgroundColor(getResources().getColor(R.color.light));
            multiAutoCompleteTextView.setTextColor(getResources().getColor(R.color.grey3));

            // Get the string array from strings.xml file.
//        String emailArr[] = getResources().getStringArray(R.array.email_array);

//        Button showEmailBtn = (Button)getActivity().findViewById(R.id.buttonShowInputEmail);
            multiAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userInputEmail = multiAutoCompleteTextView.getText().toString();

                    // Create an AlertDialog object.
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

                    // Set prompt message.
                    alertDialog.setMessage(getResources().getString(R.string.str_tag_people) + " " + userInputEmail);

                    // Show the alert dialog.
                    alertDialog.show();
                }
            });
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void checkInternetConnection() throws Error {
        if (CommonFunctions.isOnline(getContext())) {
            toGetTags();
        } else {
            CommonFunctions.toDisplayToast(getResources().getString(R.string.lbl_no_check_internet), getContext());
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void toGetTags(){
        try {
            VolleySingleton.getInstance(getContext())
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST,
                                    AppConstants.URL + getString(R.string.str_tag_list) + getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Log.d("HmApp", "Comment Res " + response);
                                                JSONArray array = new JSONArray(response.trim());
                                                if (array != null) {
                                                    if (array.length() > 0) {
                                                        for (int i = 0; i < array.length(); i++) {
                                                            Log.d("HmApp","data " + array.getJSONObject(i).getString("name") );
                                                            if (!array.getJSONObject(i).isNull("name")) {
                                                                tag_list.add(array.getJSONObject(i).getString("name"));
                                                                Log.d("hmapp", "taglist : " + tag_list.size());
                                                            }
                                                        }
                                                    }
                                                }
                                                // Create a new data adapter object.
                                                Log.d("hmapp", "taglist : " + tag_list);
                                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, tag_list);

                                                // Connect the data source with AutoCompleteTextView through adapter.
                                                multiAutoCompleteTextView.setAdapter(arrayAdapter);

                                                // Must set tokenizer for MultiAutoCompleteTextView object, otherwise it will not take effect.
                                                multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();

                                            }
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
                                    Map<String, String> params = new HashMap<>();
                                    params.put(getString(R.string.str_action_), getString(R.string.str_tag_list));
                                    params.put(getString(R.string.str_uid_), User.getUser(getContext()).getUid());
                                    return params;
                                }
                            }
                            , getString(R.string.str_fetch_comment_));
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }
}
