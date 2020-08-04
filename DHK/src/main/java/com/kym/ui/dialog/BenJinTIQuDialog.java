package com.kym.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kym.ui.R;

/**
 * 充值本金弹窗
 */
public class BenJinTIQuDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private DialogClickListener cl;
    private LinearLayout btn, close;
    private EditText money;

    public interface DialogClickListener {
        void onClick(View view);
    }

    public BenJinTIQuDialog(Context context, int theme, DialogClickListener cl) {
        super(context, theme);
        this.context = context;
        this.cl = cl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.benjinczdalog);
        initUI();
        initValue();
    }

    private void initUI() {
        close = findViewById(R.id.close);
        close.setOnClickListener(this);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
        money = findViewById(R.id.money);
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
