package com.kym.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kym.ui.model.ZhiHang;
import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ZhiHang.DataBean> data;

	SearchAdapter(Context context, ArrayList<ZhiHang.DataBean> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder vHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.search_list, null);
			vHolder = new viewHolder();
			vHolder.tv_type = (TextView) convertView.findViewById(R.id.textView1);
			convertView.setTag(vHolder);
		} else {
			vHolder = (viewHolder) convertView.getTag();
		}
		vHolder.tv_type.setText(data.get(position).getBank_sub());
		return convertView;
	}

	class viewHolder {
		TextView tv_type;
	}
}
