package com.kym.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CXKDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private ExitDialogListener listener;

    public interface ExitDialogListener {
        void onClick(View view);
    }

    public CXKDialog(Context context, int theme, ExitDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_cxk);
        initUI();
        initValues();
    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        TextView t4 = findViewById(R.id.textView4);
        TextView t5 = findViewById(R.id.textView5);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
    }

    public void onClick(View v) {
        listener.onClick(v);
    }

    @SuppressLint("RtlHardcoded")
    private void initValues() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }
}
