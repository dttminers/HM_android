package com.hm.application.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.activity.UserOptionsActivity;
import com.hm.application.common.MyPost;
import com.hm.application.common.UserData;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.network.VolleyMultipartRequest;
import com.hm.application.user_data.LoginActivity;

public class CommonFunctions {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private static AlertDialog dialog;
    private static String date;

    public static boolean isValidEmail(String email) {
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches();
    }

    public static String firstLetterCaps(String myString) {

        return myString.substring(0, 1).toUpperCase() + myString.substring(1);
    }

    public static boolean isValidPassword(String str) {
        //Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character:
        return Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}").matcher(str).matches();
    }

    public static int toGetRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
    }


    public static int dpToPx(Context context, int dp) {
        return Math.round(((float) dp) * getPixelScaleFactor(context));
    }

    public static int pxToDp(Context context, int px) {
        return Math.round(((float) px) / getPixelScaleFactor(context));
    }

    private static float getPixelScaleFactor(Context context) {
        return context.getResources().getDisplayMetrics().xdpi / 160.0f;
    }

    // Custom method to get the screen width in pixels
    public static int getScreenWidthInDPs(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        //Display dimensions in pixels
        display.getSize(size);
        int width = size.x;
        //int height = size.y;
        return getDPsFromPixels(width, context);
    }

    // Method for converting pixels value to dps
    private static int getDPsFromPixels(int pixels, Context context) {
        Resources r = context.getResources();
        int dps = Math.round(pixels / (r.getDisplayMetrics().densityDpi / 160f));
        return dps;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    //    private static String toDate(String date){
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = fmt.parse(date);
//
//        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
//        return fmtOut.format(date);
//    }

    public static String toSetDate(String date) {
        try {
//            return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date));
//            return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault()).parse(date));
            return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault()).parse(date));
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            return null;
        }
    }

    public static String toSetStartDate(int date) {
        try {
            Calendar c = Calendar.getInstance();
//            c.add(5, date);
            return new SimpleDateFormat("d", Locale.getDefault()).format(c.getTime());
        } catch (Exception | Error e) {
            return null;
        }
    }

    public static String toSetEndDate(int date) {
        try {
            Calendar c = Calendar.getInstance();
//            c.add(5, date);
            return new SimpleDateFormat("d MMM", Locale.getDefault()).format(c.getTime());
        } catch (Exception | Error e) {
            FirebaseCrash.report(e);
            return null;
        }
    }

    private static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getTimeAgo(Date date) {
        long time = date.getTime();
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = currentDate().getTime();
        if (time > now || time <= 0) {
            return "in the future";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "moments ago";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 60 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 2 * HOUR_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static void toShareData(Context context, String title, String body, String timelineId) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(sharingIntent, "Share"));
        MyPost.toSharePost(context, timelineId);
    }

    public static Uri getLocalBitmapUri(ImageView imageView, Activity activity) {
        Uri bmpUri = null;
        try {
            if (!(imageView.getDrawable() instanceof BitmapDrawable)) {
                return null;
            }
            Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "" + System.currentTimeMillis() + ".png");
            if (!Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
            return bmpUri;
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            return bmpUri;
        }
    }

    public static Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            return null;
        }
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceUniqueID(Activity activity) {
        return Settings.Secure.getString(activity.getContentResolver(), "android_id");
    }


    // Online Connection checking Code.................
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return !(connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnected());
    }

    public static void toCallLoader(Context context, String msg) {
        try {
            Builder alertDialogBuilder = new Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                View view = inflater.inflate(R.layout.custom_loader, null, false);
                alertDialogBuilder.setView(view);
                alertDialogBuilder.setTitle(null);
                alertDialogBuilder.setCancelable(false);
                dialog = alertDialogBuilder.create();
                dialog.setCancelable(false);
                dialog.show();
                TextView tvMsg = view.findViewById(R.id.txtLoaderMsg);
                tvMsg.setText(msg);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    public static void toCloseLoader(Context context) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    public static void toCallPop(String msg, Context context) {
        try {
            Builder builder = new Builder(context);
            builder.setMessage(msg).setCancelable(false).setPositiveButton("OK", null);
            AlertDialog alert = builder.create();
            if (alert.isShowing()) {
                alert.dismiss();
            } else {
                alert.show();
            }
        } catch (Exception e) {

            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    public static void toDisplayToast(String msg, Context context) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    public static String toOpenDatePicker(Context context, final EditText mEdtDate) {
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Log.d("HmApp", " Date : " + year + " : " + month + " : " + dayOfMonth);
                    mEdtDate.setText(dayOfMonth + "/" + ((month + 1) != 10 && (month + 1) != 11 && (month + 1) != 12 ? "0" + (month + 1) : "" + (month + 1)) + "/" + year);
                }
            },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            dialog.show();
            return date;
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            return "";
        }
    }

    public static byte[] readBytes(Uri uri, Activity activity) {
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            return byteBuffer.toByteArray();
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            return null;
        }
    }

    public static RoundedBitmapDrawable createRoundedBitmapDrawableWithBorder(Context context, Bitmap bitmap) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int borderWidthHalf = 10;

        int bitmapRadius = Math.min(bitmapWidth, bitmapHeight) / 2;

        int bitmapSquareWidth = Math.min(bitmapWidth, bitmapHeight);
        int newBitmapSquareWidth = bitmapSquareWidth + borderWidthHalf;

        Bitmap roundedBitmap = Bitmap.createBitmap(newBitmapSquareWidth, newBitmapSquareWidth, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(roundedBitmap);
        canvas.drawColor(Color.GRAY);

        int x = borderWidthHalf + bitmapSquareWidth - bitmapWidth;
        int y = borderWidthHalf + bitmapSquareWidth - bitmapHeight;
        canvas.drawBitmap(bitmap, x, y, null);

        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidthHalf * 2);
        borderPaint.setColor(Color.WHITE);

        canvas.drawCircle(canvas.getWidth() / 2, canvas.getWidth() / 2, newBitmapSquareWidth / 2, borderPaint);

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), roundedBitmap);
        roundedBitmapDrawable.setCornerRadius(bitmapRadius);
        roundedBitmapDrawable.setAntiAlias(true);

        return roundedBitmapDrawable;
    }

    public static RoundedBitmapDrawable createRoundedBitmapImageDrawableWithBorder(Context context, Bitmap bitmap) {
        int bitmapWidthImage = bitmap.getWidth();
        int bitmapHeightImage = bitmap.getHeight();
        int borderWidthHalfImage = 4;

        int bitmapRadiusImage = Math.min(bitmapWidthImage, bitmapHeightImage) / 2;
        int bitmapSquareWidthImage = Math.min(bitmapWidthImage, bitmapHeightImage);
        int newBitmapSquareWidthImage = bitmapSquareWidthImage + borderWidthHalfImage;

        Bitmap roundedImageBitmap = Bitmap.createBitmap(newBitmapSquareWidthImage, newBitmapSquareWidthImage, Bitmap.Config.ARGB_8888);
        Canvas mcanvas = new Canvas(roundedImageBitmap);
        mcanvas.drawColor(Color.RED);
        int i = borderWidthHalfImage + bitmapSquareWidthImage - bitmapWidthImage;
        int j = borderWidthHalfImage + bitmapSquareWidthImage - bitmapHeightImage;

        mcanvas.drawBitmap(bitmap, i, j, null);

        Paint borderImagePaint = new Paint();
        borderImagePaint.setStyle(Paint.Style.STROKE);
        borderImagePaint.setStrokeWidth(borderWidthHalfImage);
        borderImagePaint.setColor(Color.WHITE);
        mcanvas.drawCircle(mcanvas.getWidth() / 2, mcanvas.getWidth() / 2, newBitmapSquareWidthImage / 2, borderImagePaint);

        RoundedBitmapDrawable roundedImageBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), roundedImageBitmap);
        roundedImageBitmapDrawable.setCornerRadius(bitmapRadiusImage);
        roundedImageBitmapDrawable.setAntiAlias(true);
        return roundedImageBitmapDrawable;
    }

    public static Bitmap addWhiteBorder(Bitmap bmp, int borderSize) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

    public static Bitmap cropToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width) ? height - (height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0) ? 0 : cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0) ? 0 : cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }

    public static void CircularIvBorderShadow(ImageView mImageView, Bitmap srcBitmap, Context context) {
        Paint paint = new Paint();

        int srcBitmapWidth = srcBitmap.getWidth();
        int srcBitmapHeight = srcBitmap.getHeight();

        int borderWidth = 25;
        int shadowWidth = 10;

        int dstBitmapWidth = Math.min(srcBitmapWidth, srcBitmapHeight) + borderWidth * 2;
        Bitmap dstBitmap = Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(dstBitmap);
        canvas.drawColor(Color.LTGRAY);
        canvas.drawBitmap(srcBitmap, (dstBitmapWidth - srcBitmapWidth) / 2, (dstBitmapWidth - srcBitmapHeight) / 2, null);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth * 2);
        paint.setColor(Color.TRANSPARENT);

        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 2, paint);

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(shadowWidth);

        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 2, paint);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), dstBitmap);
        roundedBitmapDrawable.setCircular(true);
        roundedBitmapDrawable.setAntiAlias(true);

        mImageView.setImageDrawable(roundedBitmapDrawable);
    }

    public static void toReleaseMemory() {
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().gc();
        System.gc();
    }

    public static byte[] getFileDataFromDrawable(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private boolean appInstalledOrNot(String uri, Activity activity) {
        PackageManager pm = activity.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }

        return false;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (Exception | Error e) {
            FirebaseCrash.report(e);
            return false;
        }
    }

    public static void toLogout(Context context) {
        try {
            User.getUser(context).setUid(null);
            User.getUser(context).setName(null);
            User.getUser(context).setUsername(null);
            User.getUser(context).setEmail(null);
            User.getUser(context).setMobile(null);
            User.getUser(context).setDob(null);
            User.getUser(context).setPicPath(null);
            User.getUser(context).setLivesIn(null);
            User.getUser(context).setReferralCode(null);
//            User.getUser(context).setFcmToken(null);
            User.getUser(context).setNotificationCount(0);

            AppDataStorage.setUserInfo(context);
            context.startActivity(new Intent(context, LoginActivity.class).putExtra(AppConstants.USERDATA, AppConstants.LOGIN).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));

        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    public static File toSaveImages(Bitmap bm, String name, boolean b, Context context, Activity activity) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File dir = new File(Environment.getExternalStorageDirectory() + "/Profile");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.d("HmApp", " Path : " + dir + " : " + dir.exists());

            File f = new File(dir,
                    name + Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath() + " : " + f.getName() + ": " + f.getCanonicalPath() + f.exists());
            if (b) {
                UserData.toUploadProfilePic(context, new VolleyMultipartRequest.DataPart(User.getUser(context).getUid() + "p_" + CommonFunctions.getDeviceUniqueID(activity)
                        + "_" + f.getName(), CommonFunctions.readBytes(Uri.fromFile(f), activity), "image/jpeg"));
            }
            return f;
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            return null;
        }
    }

    private static Bitmap createSquaredBitmap(Bitmap srcBmp) {
        int dim = Math.max(srcBmp.getWidth(), srcBmp.getHeight());
        Bitmap dstBmp = Bitmap.createBitmap(dim, dim, Config.ARGB_8888);

        Canvas canvas = new Canvas(dstBmp);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(srcBmp, (dim - srcBmp.getWidth()) / 2, (dim - srcBmp.getHeight()) / 2, null);

        return dstBmp;
    }

     public static  void replaceMainHomePage(Fragment fragment, FragmentActivity activity)  throws Exception, Error{
        Log.d("Hmapp", " agr replaceTabData 1 bundle  " + fragment.getArguments());
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flUserHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}