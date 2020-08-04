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

public class BackDialog extends Dialog implements android.view.View.OnClickListener {
    private Context context;
    private DialogClickListener cl;
    private TextView tv1, tv2, title1;
    private String string, title, btn;




    public interface DialogClickListener {
        void onClick(View view);
    }

    public BackDialog(String title, String string, String btn, Context context, int theme, DialogClickListener cl) {
        super(context, theme);
        this.context = context;
        this.cl = cl;
        this.string = string;
        this.title = title;
        this.btn = btn;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.backdalog);
        initUI();
        initValue();
        initData();
    }

    private void initUI() {
        title1 = (TextView) findViewById(R.id.title);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textview2);
    }

    private void initData() {
        tv1.setOnClickListener(this);
        tv2.setText(string);
        title1.setText(title);
        tv1.setText(btn);
        if (title.equals("")){
            title1.setVisibility(View.GONE);
        }
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
