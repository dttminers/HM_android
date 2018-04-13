package com.hm.application.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.services.SmsListener;

public class SmsReceiver extends BroadcastReceiver {
    private static SmsListener mListener;

    public void onReceive(Context context, Intent intent) {
        try {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            if (pdus != null) {
                for (Object obj : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
                    if (smsMessage.getDisplayOriginatingAddress().contains("SNSWAP")) {
                        String messageBody = smsMessage.getMessageBody();
                        if (messageBody != null) {
                            mListener.messageReceived(messageBody);
                        } else {
                            FirebaseCrash.log("SMSReceiver:1 " + smsMessage.getDisplayMessageBody() + "::" + smsMessage.getEmailBody() + "::" + smsMessage.getEmailFrom() + "::" + smsMessage.getPseudoSubject() + "::" + smsMessage.getMessageBody() + "::" + smsMessage.getStatus() + "::" + smsMessage.getServiceCenterAddress() + "::" + smsMessage.getOriginatingAddress() + "::" + smsMessage.getDisplayOriginatingAddress());
                        }
                    } else {
                        FirebaseCrash.log("SMSReceiver:2 " + smsMessage.getDisplayMessageBody() + "::" + smsMessage.getEmailBody() + "::" + smsMessage.getEmailFrom() + "::" + smsMessage.getPseudoSubject() + "::" + smsMessage.getMessageBody() + "::" + smsMessage.getStatus() + "::" + smsMessage.getServiceCenterAddress() + "::" + smsMessage.getOriginatingAddress() + "::" + smsMessage.getDisplayOriginatingAddress());
                    }
                }
                return;
            }
            FirebaseCrash.log("SMSReceiver:3 " + intent.getExtras());
            return;
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
