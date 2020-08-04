package com.kym.ui.util;

import com.kym.ui.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class DialogUtil implements OnClickListener {

    private Dialog dialog;
    private View view;
    private Window window;
    private LinearLayout content_ll;
    private TextView title_tv;
    private OnCancleAndConfirmListener listener;
    private RelativeLayout single_rl, confirm_and_cancle_rl;

    /**
     * @param context
     * @param title    dialog标题
     * @param flag     1 只有确定按钮 2有确定取消按钮
     * @param listener 确定取消按钮的监听器
     */
    public DialogUtil(Context context, String title, int flag,
                      OnCancleAndConfirmListener listener) {
        super();
        this.listener = listener;
        dialog = new Dialog(context, R.style.DialgoStyle);
        dialog.setCancelable(false);
        window = dialog.getWindow();
        view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_model, null);
        content_ll = view.findViewById(R.id.content_ll);
        title_tv = view.findViewById(R.id.title_tv);
        title_tv.setText(title);

        single_rl = view.findViewById(R.id.single_rl);
        confirm_and_cancle_rl = view
                .findViewById(R.id.confirm_and_cancle_rl);
        if (flag == 1) {
            single_rl.setVisibility(View.VISIBLE);
            confirm_and_cancle_rl.setVisibility(View.GONE);
        } else if (flag == 2) {
            single_rl.setVisibility(View.GONE);
            confirm_and_cancle_rl.setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.cancle_tv).setOnClickListener(this);
        view.findViewById(R.id.confirm_tv).setOnClickListener(this);
        view.findViewById(R.id.confirm1_tv).setOnClickListener(this);
        window.setContentView(view);
        dialog.show();
    }

    public DialogUtil(Context context, String title, String title2, String title3, int flag,
                      OnCancleAndConfirmListener listener) {
        super();
        this.listener = listener;
        dialog = new Dialog(context, R.style.DialgoStyle);
        dialog.setCancelable(false);
        window = dialog.getWindow();
        view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_model3, null);
        content_ll = view.findViewById(R.id.content_ll);
        title_tv = view.findViewById(R.id.title_tv);
        title_tv.setText(title);

        single_rl = view.findViewById(R.id.single_rl);
        confirm_and_cancle_rl = view
                .findViewById(R.id.confirm_and_cancle_rl);
        if (flag == 1) {
            single_rl.setVisibility(View.VISIBLE);
            confirm_and_cancle_rl.setVisibility(View.GONE);
        } else if (flag == 2) {
            single_rl.setVisibility(View.GONE);
            confirm_and_cancle_rl.setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.cancle_tv).setOnClickListener(this);
        view.findViewById(R.id.confirm_tv).setOnClickListener(this);
        view.findViewById(R.id.confirm1_tv).setOnClickListener(this);
        window.setContentView(view);
        dialog.show();
    }

    public DialogUtil(Context context, String title, String title2, int flag,
                      OnCancleAndConfirmListener listener) {
        super();
        this.listener = listener;
        dialog = new Dialog(context, R.style.DialgoStyle);
        dialog.setCancelable(false);
        window = dialog.getWindow();
        view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_model2, null);
        content_ll = view.findViewById(R.id.content_ll);
        title_tv = view.findViewById(R.id.title_tv);
        title_tv.setText(title);

        single_rl = view.findViewById(R.id.single_rl);
        confirm_and_cancle_rl = view
                .findViewById(R.id.confirm_and_cancle_rl);
        if (flag == 1) {
            single_rl.setVisibility(View.VISIBLE);
            confirm_and_cancle_rl.setVisibility(View.GONE);
        } else if (flag == 2) {
            single_rl.setVisibility(View.GONE);
            confirm_and_cancle_rl.setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.cancle_tv).setOnClickListener(this);
        view.findViewById(R.id.confirm_tv).setOnClickListener(this);
        view.findViewById(R.id.confirm1_tv).setOnClickListener(this);
        window.setContentView(view);
        dialog.show();
    }


    /**
     * 圆形进度条
     *
     * @param context
     */
    public DialogUtil(Context context) {
        super();
        dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.loading_01);
        Animation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setDuration(1000);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        imageView.startAnimation(rotateAnimation);
        window.setContentView(imageView);


    }

    public DialogUtil(Context context1,Context context2) {
        super();
        dialog = new AlertDialog.Builder(context1).create();
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(context1);
        imageView.setImageResource(R.drawable.loading_01);
        TextView text = new TextView(context2);
        text.setText("更新时间较长，请耐心等待...");
        Animation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setDuration(1000);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        imageView.startAnimation(rotateAnimation);
        window.setContentView(imageView);
        window.setContentView(text);


    }

    /**
     * 圆形进度条
     *
     * @param context
     */
    public DialogUtil(Context context, String title, String title2) {
        super();
        dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.login99);
        imageView.setVisibility(View.VISIBLE);

        Animation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setDuration(1000);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        imageView.startAnimation(rotateAnimation);
        window.setContentView(imageView);


    }


    public DialogUtil(Context context, View view) {
        super();
        dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.show();
        window.setContentView(view);


    }

    /**
     * 设置dialog中间内容
     *
     * @param content
     */
    public void setContent(View content) {
        content_ll.addView(content);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        title_tv.setText(toString());
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }

    public interface OnCancleAndConfirmListener {
        void cancle();

        void confirm();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle_tv:
                dialog.dismiss();
                listener.cancle();
                break;

            case R.id.confirm1_tv:
            case R.id.confirm_tv:
                dialog.dismiss();
                listener.confirm();
                break;

            default:
                break;
        }
    }

}
