package com.kym.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.kym.ui.R;

public class BenJinWenTiDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private DialogClickListener cl;
    private LinearLayout close;

    public interface DialogClickListener {
        void onClick(View view);
    }

    public BenJinWenTiDialog(Context context, int theme, DialogClickListener cl) {
        super(context, theme);
        this.context = context;
        this.cl = cl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.benjinwenti);
        initUI();
        initValue();
    }

    private void initUI() {
        close = findViewById(R.id.close);
        close.setOnClickListener(this);
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
