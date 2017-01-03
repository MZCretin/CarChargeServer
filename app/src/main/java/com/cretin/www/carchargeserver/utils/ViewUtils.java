package com.cretin.www.carchargeserver.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.cretin.www.carchargeserver.BaseApplication;
import com.cretin.www.carchargeserver.app.LocalStorageKeys;

import java.lang.reflect.Field;

/**
 * Created by cretin on 16/8/8.
 * 功能1：测量状态栏的高度
 */
public class ViewUtils {
    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeights() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int statusBarHeight = KV.get(LocalStorageKeys.APP_STATUS_HEIGHT, 0);
        if (statusBarHeight == 0)
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = BaseApplication.getContext().getResources().getDimensionPixelSize(x);
                KV.put(LocalStorageKeys.APP_STATUS_HEIGHT, statusBarHeight);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        return statusBarHeight;
    }

//    public void setTextView(TextView tv){
//        Spannable WordtoSpan = new SpannableString("大字小字");
//        WordtoSpan.setSpan(new AbsoluteSizeSpan(20), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        WordtoSpan.setSpan(new AbsoluteSizeSpan(14), 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        remoteViews.setCharSequence(R.id.text11, "setText", WordtoSpan);
//        ComponentName com = new ComponentName("com.jftt.widget", "com.jftt.widget.MyWidgetProvider");
//        appWidgetManager.updateAppWidget(com, remoteViews);
//    }

    //获取屏幕高度
    public static int getWindowHeight(Context context) {
        int height = KV.get(LocalStorageKeys.APP_WINDOW_HEIGHT, 0);
        if (height != 0) {
            return height;
        }
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;//宽度
        height = dm.heightPixels;//高度
        KV.put(LocalStorageKeys.APP_WINDOW_WIDTH, width);
        KV.put(LocalStorageKeys.APP_WINDOW_HEIGHT, height);
        return height;
    }

    //获取屏幕宽度
    public static int getWindowWidth(Context context) {
        int width = KV.get(LocalStorageKeys.APP_WINDOW_WIDTH, 0);
        if (width != 0) {
            return width;
        }
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;//宽度
        int height = dm.heightPixels;//高度
        KV.put(LocalStorageKeys.APP_WINDOW_WIDTH, width);
        KV.put(LocalStorageKeys.APP_WINDOW_HEIGHT, height);
        return width;
    }
}
