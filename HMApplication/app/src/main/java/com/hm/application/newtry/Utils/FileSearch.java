package com.hm.application.newtry.Utils;

import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class FileSearch {

    public static ArrayList<String> getDirectoryPaths(String directory) {
        ArrayList<String> pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listfiles = file.listFiles();
        for (int i = 0; i < listfiles.length; i++) {
            Log.d("hmapp", " isdirectory " + listfiles[i]);
            if (listfiles[i].isDirectory()&& file.listFiles(new ImageFileFilter()).length > 0) {
                pathArray.add(listfiles[i].getAbsolutePath());
            }
        }
        return pathArray;
    }

    /**
     * Search a directory and return a list of all **files** contained inside
     *
     * @param directory
     * @return
     */
    public static ArrayList<String> getFilePaths(String directory) {
        Log.d("hmapp", " getfilepaths : " + directory);
        ArrayList<String> pathArray = new ArrayList<>();
        File file = new File(directory);
        Log.d("hmaPp", "listFiles : " + Arrays.toString(file.listFiles())) ;

        File[] listfiles = file.listFiles();
        for (int i = 0; i < listfiles.length; i++) {
            if (listfiles[i].isFile()) {
                Log.d("hmapp", " getabsolutepath "+ listfiles[i].getAbsolutePath());
                pathArray.add(listfiles[i].getAbsolutePath());
            }
        }
        return pathArray;
    }

    /**
     * Checks the file to see if it has a compatible extension.
     */
    private static boolean isImageFile(String filePath) {
        if (filePath.endsWith(".jpg") || filePath.endsWith(".png"))
        // Add other formats as desired
        {
            return true;
        }
        return false;
    }

    /**
     * This can be used to filter files.
     */
    private static class ImageFileFilter implements FileFilter {

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            else if (isImageFile(file.getAbsolutePath())) {
                return true;
            }
            return false;
        }
    }

}
