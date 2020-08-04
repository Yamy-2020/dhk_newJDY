package com.kym.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShenHeDialog extends Dialog implements android.view.View.OnClickListener {
	private Context context;
	private ShenHeDialogClickListener cl;
	private TextView tv1;
	private TextView tv2;
	private LinearLayout canl;

	public interface ShenHeDialogClickListener {
		public void onClick(View view);
	}

	public ShenHeDialog(Context context, int theme, ShenHeDialogClickListener cl) {
		super(context, theme);
		this.context = context;
		this.cl = cl;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.shenhedialog2);
		initUI();
		initValue();
		initData();
	}

	private void initUI() {
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		canl = (LinearLayout) findViewById(R.id.cancel);
	}

	private void initData() {
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		canl.setOnClickListener(this);
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
