package com.kym.ui.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by zachary on 2017/11/27.
 */

public class DIsplayUtils {
    /**
     * 获取屏幕宽度像素
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度像素
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * dp 转 px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context,int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
