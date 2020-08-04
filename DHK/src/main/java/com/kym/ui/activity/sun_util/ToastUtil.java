package com.kym.ui.activity.sun_util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kym.ui.R;

/**
 * 自定义Toast具体类，统一每个手机的toast
 */

public class ToastUtil {

    /**
     * 显示文本+图片的Toast
     *
     * @param context 上下文
     * @param message 要提示的文本
     */
    public static void showImageToas(Context context, String message) {
        View toastview = LayoutInflater.from(context).inflate(R.layout.toast_image_layout, null);
        TextView text = (TextView) toastview.findViewById(R.id.tv_message);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastview);
        toast.show();
    }

    /**
     * 显示文本的Toast
     *
     * @param context
     * @param message
     */
    public static void showTextToas(Context context, String message) {
        View toastview = LayoutInflater.from(context).inflate(R.layout.toast_text_layout, null);
        TextView text = (TextView) toastview.findViewById(R.id.tv_message);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastview);
        toast.show();
    }
}

