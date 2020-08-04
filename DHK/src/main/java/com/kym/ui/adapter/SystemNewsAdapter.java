package com.kym.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.model.SySMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SystemNewsAdapter extends BaseAdapter {

	private Activity activity;
	private List<SySMessage.DataBean> mList;

	public SystemNewsAdapter(Activity activity, List<SySMessage.DataBean> hotgoodsList) {

		activity.getWindowManager().getDefaultDisplay();
		this.activity = activity;
		this.mList = hotgoodsList;

	}

	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		}
		return mList.size();

	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = activity.getLayoutInflater().inflate(R.layout.item_systemnews, null);
			holder.textv_xt_time = convertView.findViewById(R.id.textV_time);
			holder.textV_title = convertView.findViewById(R.id.textV_title);
			holder.textv_xt_message = convertView.findViewById(R.id.textV_message);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		SySMessage.DataBean info = mList.get(position);
		holder.textv_xt_time.setText(gettime(info.getAddtime()));
		holder.textv_xt_message.setText(info.getContent());
		holder.textV_title.setText(info.getTitle());
		return convertView;
	}

	class Holder {
		TextView textv_xt_time;
		TextView textv_xt_message;
		TextView textV_title;

	}

	public static String gettime(String string) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd     HH:mm");
		long cc_time = Long.valueOf(string);

		return simpleDateFormat.format(new Date(cc_time * 1000L));
	}

}