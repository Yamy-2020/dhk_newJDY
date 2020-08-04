package com.kym.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kym.ui.R;

public class BankSigNingDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private final OnUserConfirmListener listener;


    public BankSigNingDialog(Activity context,OnUserConfirmListener l, int themeDialogScale) {
        super(context, themeDialogScale);
        this.context = context;
        listener = l;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_banksigning);
        findViewById(R.id.lijiqianyue).setOnClickListener(this);
        initValue();
    }

    private void initValue() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.lijiqianyue:
                listener.userConfirm();
                break;
        }
    }

    public interface OnUserConfirmListener{
        void userConfirm();
    }
}