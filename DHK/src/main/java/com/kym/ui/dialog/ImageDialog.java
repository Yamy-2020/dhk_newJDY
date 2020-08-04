package com.kym.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kym.ui.R;

public class ImageDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private DialogClickListener cl;
    private LinearLayout close;
    private ImageView dc_img;

    public interface DialogClickListener {
        void onClick(View view);
    }

    public ImageDialog(Context context, int theme, DialogClickListener cl) {
        super(context, theme);
        this.context = context;
        this.cl = cl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.imagedalog);
        initUI();
        initValue();
    }

    private void initUI() {
        close = findViewById(R.id.close);
        close.setOnClickListener(this);
        dc_img = findViewById(R.id.dc_img);
        dc_img.setOnClickListener(this);
        Glide.with(getContext()).load(R.drawable.kf11).diskCacheStrategy(DiskCacheStrategy.ALL).into(dc_img);
    }

    private void initValue() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        cl.onClick(v);
    }

}
