package com.ajinkyabadve.floatingmenulib.util;

import android.content.Context;

/**
 * Created by Ajinkya on 21/07/2016.
 */
public class Util {
    public static int convertDptoPixcel(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }
}
