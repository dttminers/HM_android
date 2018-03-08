package com.hm.application.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyBoard {
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService("input_method");
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void openKeyboard(Activity activity) {
        ((InputMethodManager) activity.getSystemService("input_method")).toggleSoftInput(2, 0);
    }

    public static void forceHideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService("input_method");
        if (imm.isActive()) {
            imm.toggleSoftInput(1, 0);
        }
    }
}
