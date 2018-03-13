package com.hm.application.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.hm.application.R;

public class HmFonts {
    private static Typeface mTypeRobotoBlack;
    private static Typeface mTypeArial;
    private static Typeface mTypeArialBold;
    private static Typeface mTypeGothamBold;
    private static Typeface mTypeGothamBook;
    private static Typeface mTypeRobotoRegular;

    public static Typeface getRobotoBlack(Context context) {
        if (mTypeRobotoBlack == null) {
            mTypeRobotoBlack = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lbl_font_roboto_black));
        }
        return mTypeRobotoBlack;
    }

}
