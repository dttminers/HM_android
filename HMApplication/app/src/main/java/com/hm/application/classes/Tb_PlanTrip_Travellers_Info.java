package com.hm.application.classes;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.hm.application.R;

public class Tb_PlanTrip_Travellers_Info {

    public static void toFillTravellersInfo(final Context context){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (inflater != null) {
                dialogView = inflater.inflate(R.layout.fragment_number_of_traveller, null);

                builder.setView(dialogView);

                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        }catch (Exception | Error e){
            e.printStackTrace();
        }
    }

    public static void toFillTravellersRoomInfo(final Context context){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (inflater != null) {
                dialogView = inflater.inflate(R.layout.fragment_number_of_room, null);

                builder.setView(dialogView);

                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        }catch (Exception | Error e){
            e.printStackTrace();
        }
    }
}
