package com.hm.application.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.application.R;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;

//
//import com.hm.application.Manifest;

public class Common_Alert_box {

    public static void toFillTravellersInfo(final Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (inflater != null) {
                dialogView = inflater.inflate(R.layout.fragment_number_of_traveller, null);

                builder.setView(dialogView);

                AlertDialog alert = builder.create();
                alert.show();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    public static void toFillTravellersRoomInfo(final Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (inflater != null) {
                dialogView = inflater.inflate(R.layout.fragment_number_of_room, null);

                builder.setView(dialogView);

                AlertDialog alert = builder.create();
                alert.show();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    public static void toPostMoreIcon(final Context context) {
        TextView mTvMcDialogDelete, mTvMcDialogShare;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (inflater != null) {
                dialogView = inflater.inflate(R.layout.more_custom_dialogbox, null);
                mTvMcDialogDelete = dialogView.findViewById(R.id.tvMcDialogDelete);
                mTvMcDialogShare = dialogView.findViewById(R.id.tvMcDialogShare);

                builder.setView(dialogView);

                final AlertDialog alert = builder.create();

                mTvMcDialogShare.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            CommonFunctions
                                    .toShareData(context,
                                            context.getString(R.string.app_name),
                                            "",
                                            "", null);
                        } catch (Exception | Error e) {
                            e.printStackTrace();

                        }
                    }
                });
                mTvMcDialogDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });
                alert.show();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    public static void toContactUs(final Context context) {
        TextView mTvCancel;
        LinearLayout mLlCallUs, mLlSendQuery;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (inflater != null) {
                dialogView = inflater.inflate(R.layout.contact_us_dialog, null);
                mTvCancel = dialogView.findViewById(R.id.tvCancel);
                mLlSendQuery = dialogView.findViewById(R.id.llSendQuery);
                mLlCallUs = dialogView.findViewById(R.id.llCallUs);

                builder.setView(dialogView);

                final AlertDialog alert = builder.create();

                mLlCallUs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }

                });

                mLlSendQuery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }

                });

                mTvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });
                alert.show();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    @SuppressLint("SetTextI18n")
    public static void toPrivateAccount(final Context context, final boolean status) {
        // status = true = private; status = false = public;
        Button mBtnCancel, mBtnOk;
        TextView mTvCustomHeader, mTvCustomMsg;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inflater != null) {
                View dialogView = inflater.inflate(R.layout.custom_dialog_box_private, null);

                mTvCustomHeader = dialogView.findViewById(R.id.tvCustomHeader);
                mTvCustomHeader.setTypeface(HmFonts.getRobotoBold(context));

                mTvCustomMsg = dialogView.findViewById(R.id.tvCustomHeader);
                mTvCustomMsg.setTypeface(HmFonts.getRobotoRegular(context));

                mBtnCancel = dialogView.findViewById(R.id.btnCancelPrivate);
                mBtnCancel.setTypeface(HmFonts.getRobotoRegular(context));

                mBtnOk = dialogView.findViewById(R.id.btnOkPrivate);
                mBtnOk.setTypeface(HmFonts.getRobotoRegular(context));

                builder.setView(dialogView);

                if (status) {
                    mTvCustomMsg.setText(context.getString(R.string.str_private_account_msg));
                    mTvCustomHeader.setText(context.getString(R.string.str_change_to_private_account));
                } else {
                    mTvCustomMsg.setText(context.getString(R.string.str_public_account_msg));
                    mTvCustomHeader.setText(R.string.str_change_to_public);
                }

                final AlertDialog alert = builder.create();

                mBtnOk.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(context, "Account is Private", Toast.LENGTH_SHORT).show();
                        if (status) {
                            CommonFunctions.toDisplayToast(" Now your account is public ", context);
                        } else {
                            CommonFunctions.toDisplayToast(" Now your account is private ", context);
                        }
                        alert.dismiss();
                    }
                });
                mBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
//                        Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    public static void toPublicAccount(final Context context) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Change to Public Account?");

        //Setting message manually and performing action on button click
        builder.setMessage("Anyone will be able to see your photos,videos and stories.You will no longer need to approve followers.");
        //This will not allow to close dialogbox until user selects an option
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(context, "Account is Public", Toast.LENGTH_SHORT).show();
                //builder.finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}