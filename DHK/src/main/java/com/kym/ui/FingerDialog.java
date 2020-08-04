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

public class FingerDialog extends Dialog implements View.OnClickListener {
	private Context context;
	private Dialog3ClickListener cl;
	private TextView cancel,queding;
	private TextView title;
	private String string;

	public interface Dialog3ClickListener {
		void onClick(View view);
	}

	public FingerDialog(String string, Context context, int theme, Dialog3ClickListener cl) {
		super(context, theme);
		this.context = context;
		this.cl = cl;
		this.string = string;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_finger);
		initUI();
		initValue();
		initData();
	}

	private void initUI() {
		cancel = (TextView) findViewById(R.id.textView2);
		queding = (TextView) findViewById(R.id.textView1);
		title = (TextView) findViewById(R.id.tv_title);
	}

	private void initData() {
		cancel.setOnClickListener(this);
		queding.setOnClickListener(this);
		title.setText(string);
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
