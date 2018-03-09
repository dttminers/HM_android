package com.hm.application.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.hm.application.services.SmsListener;

public class SmsReceiver extends BroadcastReceiver {
    private static SmsListener mListener;

    public void onReceive(Context context, Intent intent) {
        Throwable e;
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
//                            FirebaseCrash.log("SMSReceiver:1 " + smsMessage.getDisplayMessageBody() + "::" + smsMessage.getEmailBody() + "::" + smsMessage.getEmailFrom() + "::" + smsMessage.getPseudoSubject() + "::" + smsMessage.getMessageBody() + "::" + smsMessage.getStatus() + "::" + smsMessage.getServiceCenterAddress() + "::" + smsMessage.getOriginatingAddress() + "::" + smsMessage.getDisplayOriginatingAddress());
                        }
                    } else {
//                        FirebaseCrash.log("SMSReceiver:2 " + smsMessage.getDisplayMessageBody() + "::" + smsMessage.getEmailBody() + "::" + smsMessage.getEmailFrom() + "::" + smsMessage.getPseudoSubject() + "::" + smsMessage.getMessageBody() + "::" + smsMessage.getStatus() + "::" + smsMessage.getServiceCenterAddress() + "::" + smsMessage.getOriginatingAddress() + "::" + smsMessage.getDisplayOriginatingAddress());
                    }
                }
                return;
            }
//            FirebaseCrash.log("SMSReceiver:3 " + intent.getExtras());
            return;
        } catch (Exception e2) {
            e = e2;
        } catch (Error e3) {
            e = e3;
        }
//        FirebaseCrash.report(e);
        e.printStackTrace();
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
