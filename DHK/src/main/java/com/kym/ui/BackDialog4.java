package com.kym.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class BackDialog4 extends Dialog implements View.OnClickListener {
	private Context context;
	private Dialog4ClickListener cl;
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private String string;
	private CountDownTimer timer;
	public interface Dialog4ClickListener {
		public void onClick(View view);
	}

	public BackDialog4(CountDownTimer timer, String string, Context context, int theme, Dialog4ClickListener cl) {
		super(context, theme);
		this.context = context;
		this.cl = cl;
		this.string = string;
		this.timer=timer;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.backdalog4);
		initUI();
		initValue();
		initData();
	}

	private void initUI() {
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textview2);
		tv3 = (TextView) findViewById(R.id.textView3);
	}

	private void initData() {
		tv1.setOnClickListener(this);
		tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				timer.cancel();
				timer.onFinish();
                dismiss();
            }
        });


		tv2.setText(string);
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
