package com.hm.application.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import static android.support.v4.content.ContextCompat.startActivity;

//
//import com.hm.application.Manifest;

public class Common_Alert_box {

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
        final TextView mTvCancel, mTvMobNumber, mTvSendQuery, mTvCallUs, mTvContactUs;
        LinearLayout mLlCallUs, mLlSendQuery;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (inflater != null) {
                dialogView = inflater.inflate(R.layout.contact_us_dialog, null);
                mTvCancel = dialogView.findViewById(R.id.tvCancel);
                mTvCancel.setTypeface(HmFonts.getRobotoBold(context));

                mLlSendQuery = dialogView.findViewById(R.id.llSendQuery);
                mLlCallUs = dialogView.findViewById(R.id.llCallUs);

                mTvSendQuery = dialogView.findViewById(R.id.tvSendQuery);
                mTvSendQuery.setTypeface(HmFonts.getRobotoRegular(context));

                mTvCallUs = dialogView.findViewById(R.id.tvCallUs);
                mTvCallUs.setTypeface(HmFonts.getRobotoRegular(context));

                mTvContactUs = dialogView.findViewById(R.id.tvContactUs);
                mTvContactUs.setTypeface(HmFonts.getRobotoBold(context));

                mTvMobNumber = dialogView.findViewById(R.id.tvMobNumber);
                mTvMobNumber.setTypeface(HmFonts.getRobotoRegular(context));

                builder.setView(dialogView);

                final AlertDialog alert = builder.create();

                mLlCallUs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String number = mTvMobNumber.getText().toString();
                            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                            phoneIntent.setData(Uri.parse("tel:" + number));
                            startActivity(context, phoneIntent, null);

                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context,
                                    "Call failed, please try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mLlSendQuery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"highmountain@gmail.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
                        startActivity(context, intent, null);
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