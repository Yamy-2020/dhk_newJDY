package com.kym.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.Fenrun_tixianmsg_ListInfo;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Fenrun_tixiandelAdapter1 extends BaseAdapter {

	private Activity activity;
	private List<Fenrun_tixianmsg_ListInfo> mList;

	public Fenrun_tixiandelAdapter1(Activity activity,
									List<Fenrun_tixianmsg_ListInfo> hotgoodsList) {

		this.activity = activity;
		this.mList = hotgoodsList;

	}

	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		}
		log.e("****" + mList.size());
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
			convertView = activity.getLayoutInflater().inflate(R.layout.fenrun_tixiandellist, null);

			holder.textV_tixian_type = convertView.findViewById(R.id.textV_tixian_type);
			holder.textV_tixian_bank = convertView.findViewById(R.id.textV_tixian_bank);
			holder.textV_tixian_time = convertView.findViewById(R.id.textV_tixian_time);
			holder.textV_money = convertView.findViewById(R.id.textV_money);
			holder.imageV_st = convertView.findViewById(R.id.imageV_sta);


			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		final Fenrun_tixianmsg_ListInfo info = mList.get(position);

		if (info.getStatus().equals("3")) {
			// 成功
			holder.imageV_st.setBackgroundResource(R.drawable.ic_wancheng);
		} else if (info.getStatus().equals("1")) {
			// 申请中
			holder.imageV_st.setBackgroundResource(R.drawable.ic_jisuan);
		} else if (info.getStatus().equals("2")) {
			// 处理中
			holder.imageV_st.setBackgroundResource(R.drawable.ic_jisuan);
		} else {
			holder.imageV_st.setVisibility(View.INVISIBLE);
		}

		String bank_no = info.getBank_no();
		int n = 4;
		String bank_no_new = bank_no.substring(bank_no.length() - n);

		holder.textV_tixian_bank.setText(info.getBank() + " (" + bank_no_new + ")");
		holder.textV_tixian_time.setText(gettime(info.getAdd_time()));

		try {
			holder.textV_money.setText("-" + AmountUtils.changeF2Y(info.getAmount()) + "元");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}


	class Holder {
		private ImageView imageV_st;
		TextView textV_tixian_type, textV_tixian_bank, textV_tixian_time, textV_money;

	}

	// 把时间戳转为string类型
	public static String gettime(String string) {
		String time_new;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long cc_time = Long.valueOf(string);
		time_new = simpleDateFormat.format(new Date(cc_time * 1000L));

		return time_new;
	}

}