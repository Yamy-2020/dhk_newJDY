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

public class BackDialog3 extends Dialog implements android.view.View.OnClickListener {
    private Context context;
    private Dialog3ClickListener cl;
    private TextView tv1, tvv1;
    private TextView tv2;
    private String string;
    private TextView tv_tip;
    private String tip;
    private String next, cancel;

    public interface Dialog3ClickListener {
        void onClick(View view);
    }

    public BackDialog3(String next, String cancel, String tip, String string, Context context, int theme, Dialog3ClickListener cl) {
        super(context, theme);
        this.context = context;
        this.cl = cl;
        this.string = string;
        this.tip = tip;
        this.next = next;
        this.cancel = cancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_back2);
        initUI();
        initValue();
        initData();
    }

    private void initUI() {
        tv1 = findViewById(R.id.textView1);
        tvv1 = findViewById(R.id.textView2);
        tv2 = findViewById(R.id.textview2);
        tv_tip = findViewById(R.id.tv_tip);
    }

    private void initData() {
        tv1.setOnClickListener(this);
        tv1.setText(next);
        tvv1.setOnClickListener(this);
        tvv1.setText(cancel);
        tv2.setText(string);
        if (tip.equals("")) {
            tv_tip.setVisibility(View.GONE);
        } else {
            tv_tip.setText(tip);
        }
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
