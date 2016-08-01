package com.ajinkyabadve.floatingmenulib.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by Ajinkya on 21/07/2016.
 */
public class Util {
    public static float convertDptoPixcel(Context context, int dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        final float scale = context.getResources().getDisplayMetrics().density;
        return px;
    }
}
