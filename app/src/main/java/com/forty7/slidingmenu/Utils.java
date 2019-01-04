package com.forty7.slidingmenu;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 2019.1.4 Forty'7
 * xiaowangboke@vip.qq.com
 */
public class Utils {

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
