package com.kym.ui.newutilutil;

import java.util.List;

import com.kym.ui.R;
import com.kym.ui.newutil.CityAdapter;
import com.kym.ui.newutil.StringList;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CityDialog extends Dialog implements android.view.View.OnClickListener, OnItemClickListener {
	private ListView lv;
	private String title;
	private Context context;
	private TextView tv_title;
	private ClickListener c_l;
	private List<StringList> list;
	private ItemClickListener t_l;
	private CityAdapter cityAdapter;
	private RelativeLayout btn_cancel;
	private Window window;
	private WindowManager.LayoutParams lp;
	private DisplayMetrics dm;

	public interface ClickListener {
		public void onClick(View view);
	}

	public interface ItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id);
	}

	public CityDialog(List<StringList> list, String title, Context context, int theme, ClickListener c_l,
			ItemClickListener t_l) {
		super(context, theme);
		this.c_l = c_l;
		this.t_l = t_l;
		this.list = list;
		this.title = title;
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_city);
		initUI();
		initData();
		initValues();
	}

	private void initUI() {
		lv = (ListView) findViewById(R.id.listView1);
		cityAdapter = new CityAdapter(list, context);
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_cancel = (RelativeLayout) findViewById(R.id.btn_cancel);
	}

	private void initData() {
		tv_title.setText(title);
		lv.setAdapter(cityAdapter);
		lv.setOnItemClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	@SuppressLint("RtlHardcoded")
	private void initValues() {
		window = getWindow();
		lp = window.getAttributes();
		dm = context.getResources().getDisplayMetrics();
		lp.width = dm.widthPixels;
		lp.gravity = Gravity.CENTER;
		window.setAttributes(lp);
	}

	@Override
	public void onClick(View v) {
		c_l.onClick(v);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		t_l.onItemClick(parent, view, position, id);
	}

}
