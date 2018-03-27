package com.hm.application.utils;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


public class Permission {

    static Permission permissionsAndroid;

    public static Permission getInstance() {
        if (permissionsAndroid == null)
            permissionsAndroid = new Permission();
        return permissionsAndroid;
    }

    private Permission() {
    }

    // Request Code for request Permissions Must be between 0 to 255.
    //Write External Storage Permission.
    public static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 100;

    public boolean checkWriteExternalStoragePermission(Activity activity) {
        return boolValue(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    public void requestForWriteExternalStoragePermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, "Allow Write External Storage Permission to use this functionality.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }
    //Write External Storage Permission.


    //Record Audio Permission.
    public static final int RECORD_AUDIO_PERMISSION_REQUEST_CODE = 101;

    public boolean checkRecordAudioPermission(Activity activity) {
        return boolValue(ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO));
    }

    public void requestForRecordAudioPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(activity, "Allow Record Audio Permission to use this functionality.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
        }
    }
    //Record Audio Permission.

    // Camera Permission
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 102;

    public boolean checkCameraPermission(Activity activity) {
        return boolValue(ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA));
    }

    public void requestForCameraPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Toast.makeText(activity, "Allow Camera Permission to use this functionality.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);

        }
    }
    // Camera Permission

    // Location Permission
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 103;

    public boolean checkLocationPermission(Activity activity) {
        return boolValue(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION));
    }

    public void requestForLocationPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            Toast.makeText(activity, "Allow Location Permission to use this functionality.", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    // Location Permission

    // function to return true or false based on the permission result
    private boolean boolValue(int value) {
        return value == PackageManager.PERMISSION_GRANTED;
    }
}