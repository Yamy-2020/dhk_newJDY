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

public class ShowDialog extends Dialog implements View.OnClickListener {
	private Context context;
	private String tv1;
	private String tv2;
	private String tv3;
	private ExitDialogListener listener;

	public interface ExitDialogListener {
		void onClick(View view);
	}

	public ShowDialog(Context context,String t1,String t2,String t3, int theme, ExitDialogListener listener) {
		super(context, theme);
		this.context = context;
		this.listener = listener;
		this.tv1=t1;
		this.tv2=t2;
		this.tv3=t3;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_show);
		initUI();
		initValues();
	}

	@SuppressLint("SetTextI18n")
	private void initUI() {
		TextView t1 = (TextView) findViewById(R.id.textView1);
		TextView t2 = (TextView) findViewById(R.id.textView2);
		TextView t3 = (TextView) findViewById(R.id.textView3);

		TextView t4 = (TextView) findViewById(R.id.textView4);
		TextView t5 = (TextView) findViewById(R.id.textView5);

		t1.setText("姓  名："+tv1);
		t2.setText("身份证："+tv2);
		t3.setText("请核对信息是否正确，确保资金安全到帐"+tv3);

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
