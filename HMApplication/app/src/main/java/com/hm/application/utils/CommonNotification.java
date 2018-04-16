package com.hm.application.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.JsonReader;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.JsonObject;
import com.hm.application.R;
import com.hm.application.model.AppConstants;

import org.json.JSONObject;

import java.util.Random;

public class CommonNotification {
    static String description = "HighMountain Notification";

    private static void toSetNormalNotification(Context context, String title, String data, Intent intent, int messageCount) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel ch = null;
        if (VERSION.SDK_INT >= 26) {
            ch = new NotificationChannel("App", "HighMountain", notificationManager != null ? NotificationManager.IMPORTANCE_HIGH : notificationManager.getImportance());
            ch.setDescription(description);
            ch.setShowBadge(false);
        }
        Builder notificationBuilder = new Builder(context, ch != null ? "App" : "HighMountainApp")
                .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(2))
                .setDefaults(-1)
                .setPriority(1)
                .setNumber(messageCount)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        if (title != null) {
            notificationBuilder.setContentTitle(title);
        } else {
            notificationBuilder.setContentTitle(context.getResources().getString(R.string.app_name));
        }
        if (data != null) {
            notificationBuilder.setContentText(data);
        } else {
            notificationBuilder.setContentText(context.getResources().getString(R.string.lbl_str_notification_welcome));
        }
        if (notificationManager != null) {
            notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
        }
    }

    public static void toSetImageNotification(Context context, String title, String body, /*Bitmap bitmap, */Intent intent, int messageCount) {
        try {

            JSONObject obj = new JSONObject(body);
            String data = obj.getString("sender_username");

            Bitmap bitmap = CommonFunctions.getBitmapFromUrl(AppConstants.URL + obj.getString("image_url"));
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel ch = null;
            if (VERSION.SDK_INT >= 26) {
                ch = new NotificationChannel("App", "HighMountain", notificationManager != null ? NotificationManager.IMPORTANCE_HIGH : notificationManager.getImportance());
//            ch.setDescription("HighMountain Notification");
                ch.setLightColor(-16711936);
                ch.enableLights(true);
                ch.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                ch.setDescription(description);
                ch.setShowBadge(false);
                notificationManager.createNotificationChannel(ch);
            }
            BigPictureStyle imageNotify = new BigPictureStyle();
            Builder notificationBuilder = new Builder(context, ch != null ? "App" : "HighMountainApp");
            if (title != null) {
                notificationBuilder.setContentTitle(title);
                imageNotify.setBigContentTitle(title);
            } else {
                notificationBuilder.setContentTitle(context.getResources().getString(R.string.app_name));
                imageNotify.setBigContentTitle(context.getResources().getString(R.string.app_name));
            }
            if (data != null) {
                notificationBuilder.setContentText(data);
                imageNotify.setSummaryText(data);
            } else {
                notificationBuilder.setContentText(context.getResources().getString(R.string.lbl_str_notification_welcome));
                imageNotify.setSummaryText(context.getResources().getString(R.string.lbl_str_notification_welcome));
            }
            if (bitmap != null) {
                imageNotify.bigLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round));
                imageNotify.bigPicture(bitmap);
            }
            notificationBuilder
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setDefaults(-1)
                    .setPriority(1)
                    .setSound(RingtoneManager.getDefaultUri(2))
                    .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .setChannelId("App")
                    .setNumber(messageCount)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                    .setStyle(imageNotify);
            Log.d("Hmapp", " notification :   " + notificationManager);
            if (notificationManager != null) {
                notificationManager.notify(new Random().nextInt(), notificationBuilder.build());

            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    protected void showSingleNotification(Context context, String title, String data, Bitmap bitmap, Intent intent, int messageCount) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel ch = null;
        if (VERSION.SDK_INT >= 26) {
            ch = new NotificationChannel("App", "HighMountain", notificationManager != null ? NotificationManager.IMPORTANCE_HIGH : notificationManager.getImportance());
            ch.setDescription(description);
            ch.setShowBadge(false);
        }
        Builder builder = new Builder(context, ch != null ? "App" : "HighMountainApp");
        builder.setContentTitle(title)
                .setContentText(data)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setGroupSummary(false)
                .setNumber(messageCount)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setGroup("group");
        if (notificationManager != null) {
            notificationManager.notify(new Random().nextInt(), builder.build());
        }
    }

    private void showGroupSummaryNotification(Context context, String title, String data, Bitmap bitmap, Intent intent, int messageCount) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel ch = null;
        if (VERSION.SDK_INT >= 26) {
            ch = new NotificationChannel("App", "HighMountain", notificationManager != null ? NotificationManager.IMPORTANCE_HIGH : notificationManager.getImportance());
            ch.setDescription(description);
            ch.setShowBadge(false);
        }
        Builder builder = new Builder(context, ch != null ? "App" : "HighMountainApp");
        builder.setContentTitle("Dummy content title")
                .setContentText("Dummy content text")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Line 1")
                        .addLine("Line 2")
                        .setSummaryText("Inbox summary text")
                        .setBigContentTitle("Big content title"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCategory(Notification.CATEGORY_EVENT)
                .setNumber(messageCount)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setGroupSummary(true)
                .setGroup("group");
        if (notificationManager != null) {
            notificationManager.notify(new Random().nextInt(), builder.build());
        }
    }

    protected void showBigTextStyleNotification(Context context, String title, String data, Bitmap bitmap, Intent intent, int messageCount) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel ch = null;
        if (VERSION.SDK_INT >= 26) {
            ch = new NotificationChannel("App", "HighMountain", notificationManager != null ? NotificationManager.IMPORTANCE_HIGH : notificationManager.getImportance());
        }

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        bigText.setBigContentTitle("Big Text Notification");
        bigText.setSummaryText("By: Author of Lorem ipsum");

        Builder builder = new Builder(context, ch != null ? "App" : "HighMountainApp");
        builder.setContentTitle(title)
                .setContentText(data)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(bigText)
                .setNumber(messageCount)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setGroupSummary(false)
                .setGroup("group");
        if (notificationManager != null) {
            notificationManager.notify(new Random().nextInt(), builder.build());
        }
    }

    protected void showBigPictureStyleNotification(Context context, String title, String data, Bitmap bitmap, Intent intent, int messageCount) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel ch = null;
        if (VERSION.SDK_INT >= 26) {
            ch = new NotificationChannel("App", "HighMountain", notificationManager != null ? NotificationManager.IMPORTANCE_HIGH : notificationManager.getImportance());
            ch.setDescription(description);
            ch.setShowBadge(false);
        }

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();

        bigPictureStyle.bigPicture(
                BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ic_launcher_background)).build();

        Builder builder = new Builder(context, ch != null ? "App" : "HighMountainApp");
        builder.setContentTitle(title)
                .setContentText(data)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(bigPictureStyle)
                .setNumber(messageCount)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setGroupSummary(false)
                .setGroup("group");
        if (notificationManager != null) {
            notificationManager.notify(new Random().nextInt(), builder.build());
        }
    }

    protected void showMessagingStyleNotification(Context context, String title, String data, Bitmap bitmap, Intent intent, int messageCount) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel ch = null;
        if (VERSION.SDK_INT >= 26) {
            ch = new NotificationChannel("App", "HighMountain", notificationManager != null ? NotificationManager.IMPORTANCE_HIGH : notificationManager.getImportance());
        }
        long timestamp1 = 2147483647, timestamp2 = 2147483637, timestamp3 = 2147483627, timestamp4 = 2147483617;
        Notification notification = new Builder(context, ch != null ? "App" : "HighMountainApp")
                .setStyle(new NotificationCompat.MessagingStyle("Me")
                        .setConversationTitle("Team lunch")
                        .addMessage("Hi", timestamp1, null) // Pass in null for user.
                        .addMessage("What's up?", timestamp2, "Coworker")
                        .addMessage("Not much", timestamp3, null)
                        .addMessage("How about lunch?", timestamp4, "Coworker"))
                .build();
    }

    public static void showCustomNotification(Context context, String title, String data, Bitmap bitmap, Intent intent, int messageCount) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel ch = null;
        if (VERSION.SDK_INT >= 26) {
            ch = new NotificationChannel("App", "HighMountain", notificationManager != null ? NotificationManager.IMPORTANCE_HIGH : notificationManager.getImportance());
            ch.setDescription(description);
            ch.setShowBadge(false);
        }

        // Get the layouts to use in the custom notification
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.album_layout);
//                R.layout.notification_small);
        RemoteViews notificationLayoutExpanded = new RemoteViews(context.getPackageName(), R.layout.theme_layout);
//                R.layout.notification_large);

        // Apply the layouts to the notification
        Notification customNotification = new NotificationCompat.Builder(context, ch != null ? "App" : "HighMountainApp")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .setNumber(messageCount)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .build();
    }
}
