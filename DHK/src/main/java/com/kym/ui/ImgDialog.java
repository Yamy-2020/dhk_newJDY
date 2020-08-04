package com.kym.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ImgDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private Dialog3ClickListener cl;
    private TextView tv1, tvv1;

    public interface Dialog3ClickListener {
        void onClick(View view);
    }

    public ImgDialog(Context context, int theme, Dialog3ClickListener cl) {
        super(context, theme);
        this.context = context;
        this.cl = cl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.img_dialog);
        initUI();
        initValue();
        initData();
    }

    private void initUI() {
        tv1 = findViewById(R.id.textView1);
        tvv1 = findViewById(R.id.textView2);
    }

    private void initData() {
        tv1.setOnClickListener(this);
        tvv1.setOnClickListener(this);
    }

    private void initValue() {
        Window window = getWindow();
        assert window != null;
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
