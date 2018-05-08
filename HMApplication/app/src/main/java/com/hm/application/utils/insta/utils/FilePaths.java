package com.hm.application.utils.insta.utils;

import android.os.Environment;

public class FilePaths {

    //"storage/emulated/0"
    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR + "/Pictures";
    public String DOWNLOADS = ROOT_DIR + "/Downloads";
    public String MAIN = ROOT_DIR + "/";
    public String DICM_CAMERA = ROOT_DIR + "/DCIM/camera";
    public String CAMERA = ROOT_DIR + "/Camera";
    public String STORIES = ROOT_DIR + "/Stories";

    public String FIREBASE_STORY_STORAGE = "stories/users";
    public String FIREBASE_IMAGE_STORAGE = "photos/users/";

}
