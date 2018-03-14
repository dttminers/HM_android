package com.hm.application.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.hm.application.R;
import com.hm.application.user_data.LoginFragment;

public class HmFonts {
    private static Typeface mTypeRobotoBlack;
    private static Typeface mTypeRobotoBlackItalic;
    private static Typeface mTypeRobotoBold;
    private static Typeface mTypeRobotoBoldItalic;
    private static Typeface mTypeRobotoItalic;
    private static Typeface mTypeRobotoLight;
    private static Typeface mTypeRobotoLightItalic;
    private static Typeface mTypeRobotoMedium;
    private static Typeface mTypeRobotoMediumItalic;
    private static Typeface mTypeRobotoRegular;
    private static Typeface mTypeRobotoThin;
    private static Typeface mTypeRobotoThinItalic;


    public static Typeface getRobotoBlack(Context context) {
        if (mTypeRobotoBlack == null) {
            mTypeRobotoBlack = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_black));
        }
        return mTypeRobotoBlack;
    }

    public static Typeface getRobotoBlackItalic(Context context) {
        if (mTypeRobotoBlackItalic == null) {
            mTypeRobotoBlackItalic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_black_italic));
        }
        return mTypeRobotoBlackItalic;
    }

    public static Typeface getRobotoBold(Context context) {
        if (mTypeRobotoBold == null) {
            mTypeRobotoBold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_bold));
        }
        return mTypeRobotoBold;
    }

    public static Typeface getRobotoBoldItalic(Context context) {
        if (mTypeRobotoBoldItalic == null) {
            mTypeRobotoBoldItalic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_bold_italic));
        }
        return mTypeRobotoBoldItalic;
    }

    public static Typeface getRobotoItalic(Context context) {
        if (mTypeRobotoItalic == null) {
            mTypeRobotoItalic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_italic));
        }
        return mTypeRobotoItalic;
    }

    public static Typeface getRobotoLight(Context context) {
        if (mTypeRobotoLight == null) {
            mTypeRobotoLight = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_light));
        }
        return mTypeRobotoLight;
    }

    public static Typeface getRobotoLightItalic(Context context) {
        if (mTypeRobotoLightItalic == null) {
            mTypeRobotoLightItalic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_light_italic));
        }
        return mTypeRobotoLightItalic;
    }

    public static Typeface getRobotoMedium(Context context) {
        if (mTypeRobotoMedium == null) {
            mTypeRobotoMedium = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_medium));
        }
        return mTypeRobotoLightItalic;
    }


    public static Typeface getRobotoMediumItalic(Context context) {
        if (mTypeRobotoMediumItalic == null) {
            mTypeRobotoMediumItalic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_medium_italic));
        }
        return mTypeRobotoMediumItalic;
    }

    public static Typeface getRobotoRegular(Context context) {
        if (mTypeRobotoRegular == null) {
            mTypeRobotoRegular = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_regular));
        }
        return mTypeRobotoRegular;
    }

    public static Typeface getRobotoThin(Context context) {
        if (mTypeRobotoThin == null) {
            mTypeRobotoThin = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_thin));
        }
        return mTypeRobotoThin;
    }

    public static Typeface getRobotoThinItalic(Context context) {
        if (mTypeRobotoThinItalic == null) {
            mTypeRobotoThinItalic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_thin_italic));
        }
        return mTypeRobotoThinItalic;
    }
}
