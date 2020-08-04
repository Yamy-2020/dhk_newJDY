package com.kym.ui.newutil;

import java.util.List;

import com.kym.ui.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint({ "ViewHolder", "InflateParams" })
public class CityAdapter extends BaseAdapter {
	private List<StringList> data;
	private Context context;

	public CityAdapter(List<StringList> data, Context context) {
		this.data = data;
		this.context = context;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_city, null);
		TextView findViewById = view.findViewById(R.id.textView1);
		findViewById.setText(data.get(position).getS());
		return view;
	}

}
