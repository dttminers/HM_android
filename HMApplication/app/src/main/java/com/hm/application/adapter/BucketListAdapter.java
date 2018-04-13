package com.hm.application.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.network.VolleySingleton;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.hm.application.common.MyBucketList.toRemoveItemFromBucketList;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.ViewHolder> {

    private Context context;
    private JSONArray array;

    public BucketListAdapter(Context ctx, JSONArray data) {
        context = ctx;
        array = data;
    }

    @Override
    public BucketListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BucketListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.destination_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BucketListAdapter.ViewHolder holder, int position) {
        try {
            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_title))) {
                holder.mTvName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_title)));
            } else if (!array.getJSONObject(position).isNull(context.getString(R.string.str_state))) {
                holder.mTvName.setText(array.getJSONObject(position).getString(context.getString(R.string.str_state)));
            }

            if (!array.getJSONObject(position).isNull(context.getString(R.string.str_package_img_url))) {
                if (array.getJSONObject(position).getString(context.getString(R.string.str_package_img_url)).contains("http")) {
                    Picasso.with(context)
                            .load(array.getJSONObject(position).getString(context.getString(R.string.str_package_img_url)))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .into(holder.mIvDest);
                } else {
                    Picasso.with(context)
                            .load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_package_img_url)))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .into(holder.mIvDest);
                }
            } else if (!array.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                if (array.getJSONObject(position).getString(context.getString(R.string.str_image_url)).contains("http")) {
                    Picasso.with(context)
                            .load(array.getJSONObject(position).getString(context.getString(R.string.str_image_url)))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .into(holder.mIvDest);
                } else {
                    Picasso.with(context)
                            .load(AppConstants.URL + array.getJSONObject(position).getString(context.getString(R.string.str_image_url)))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .into(holder.mIvDest);
                }
            } else {
                holder.mIvDest.setBackgroundColor(ContextCompat.getColor(context, R.color.light2));
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    @Override
    public int getItemCount() {
        return array != null ? array.length() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvDest, mIvRemoveFromBL;
        private TextView mTvName;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvRemoveFromBL = itemView.findViewById(R.id.ivAddToBL);
            mIvDest = itemView.findViewById(R.id.imgDest);
            mTvName = itemView.findViewById(R.id.txtName);
            mTvName.setTypeface(HmFonts.getRobotoBold(context));

            mIvRemoveFromBL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        toAskOptions(array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_bucket_id_)),
                                array.getJSONObject(getAdapterPosition()).getString(context.getString(R.string.str_title)),
                                getAdapterPosition());
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                        CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_remove), context);
                        FirebaseCrash.report(e);
                    }
                }
            });
        }
    }

    private void toAskOptions(final String bucketId, String itemName, final int pos) throws Exception, Error {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context, R.style.MyAlertDialogTheme);
        builder1.setTitle(context.getString(R.string.str_lbl_remove) + "\n" + CommonFunctions.firstLetterCaps(itemName));
        builder1.setMessage(R.string.str_msg_remove_ask);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                context.getString(R.string.str_lbl_yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        toRemoveItemFromBucketList(context, bucketId, pos);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                context.getString(R.string.str_lbl_no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void toRemoveItemFromBucketList(final Context context, final String itemId, final int pos) {
        try {
            CommonFunctions.toCallLoader(context, "Removing...");
            VolleySingleton.getInstance(context)
                    .addToRequestQueue(
                            new StringRequest(Request.Method.POST, AppConstants.URL + context.getResources().getString(R.string.str_bucketlist) + context.getResources().getString(R.string.str_php),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String res) {
                                            try {
                                                //{context.getString(R.string.str_status):1}
                                                if (res != null) {
                                                    JSONObject response = new JSONObject(res.trim());
                                                    if (response != null) {
                                                        Log.d("HmApp", "onResponse: " + res);
                                                        if (!response.isNull(context.getString(R.string.str_status))) {
                                                            if (response.getInt(context.getString(R.string.str_status)) == 1) {
                                                                CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_removed_from_bucketlist), context);
                                                                toRemoveFromList(pos);
                                                                CommonFunctions.toCloseLoader(context);
                                                            } else {
                                                                CommonFunctions.toCloseLoader(context);
                                                                if (!response.isNull(context.getString(R.string.str_msg_small))) {
                                                                    CommonFunctions.toDisplayToast(CommonFunctions.firstLetterCaps(response.getString(context.getString(R.string.str_msg_small))), context);
                                                                } else {
                                                                    CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_remove), context);
                                                                }
                                                            }
                                                        } else {
                                                            CommonFunctions.toCloseLoader(context);
                                                            CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_remove), context);
                                                        }
                                                    } else {
                                                        CommonFunctions.toCloseLoader(context);
                                                        CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_remove), context);
                                                    }
                                                } else {
                                                    CommonFunctions.toCloseLoader(context);
                                                    CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_remove), context);
                                                }
                                            } catch (Exception | Error e) {
                                                e.printStackTrace();
                                                FirebaseCrash.report(e);
                                                CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_remove), context);
                                                CommonFunctions.toCloseLoader(context);

                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_remove), context);
                                            CommonFunctions.toCloseLoader(context);
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put(context.getResources().getString(R.string.str_action_), context.getString(R.string.str_remove_bucketlist));
                                    params.put(context.getResources().getString(R.string.str_id), itemId);
                                    params.put(context.getResources().getString(R.string.str_uid), User.getUser(context).getUid());
                                    return params;
                                }
                            }
                            , context.getString(R.string.str_remove_bucketlist));
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            CommonFunctions.toDisplayToast(context.getString(R.string.str_msg_failed_to_remove), context);
            CommonFunctions.toCloseLoader(context);

        }
    }

    private void toRemoveFromList(int pos) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                array.remove(pos);
            }
            notifyItemChanged(pos);
            notifyItemRangeRemoved(pos, array.length());
            notifyDataSetChanged();
            notify();
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }
}