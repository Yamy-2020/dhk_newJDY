package com.kym.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.HKplanResponse;

import java.util.List;

public class HKprePlanAdapter extends BaseAdapter {

    private Activity activity;
    private List<HKplanResponse.HKplanInfo.ListBean> mList;

    public HKprePlanAdapter(Activity activity, List<HKplanResponse.HKplanInfo.ListBean> hotgoodsList) {
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
        final HKprePlanAdapter.Holder holder;
        if (convertView == null) {
            holder = new HKprePlanAdapter.Holder();
            convertView = activity.getLayoutInflater().inflate(R.layout.item_xfplan_pre, null);
            holder.dc_money = convertView.findViewById(R.id.dc_money);
            holder.dc_sxf = convertView.findViewById(R.id.dc_sxf);
            holder.dc_num = convertView.findViewById(R.id.dc_num);
            holder.dc_type = convertView.findViewById(R.id.dc_type);
            holder.dc_img = convertView.findViewById(R.id.dc_img);
            convertView.setTag(holder);
        } else {
            holder = (HKprePlanAdapter.Holder) convertView.getTag();
        }

        HKplanResponse.HKplanInfo.ListBean info = mList.get(position);

        if (info.getCategory().equals("1")) {
            holder.dc_num.setText(info.getDate());
            holder.dc_type.setText("消费金额");
            holder.dc_img.setImageResource(R.drawable.dc_hk);
            holder.dc_money.setTextColor(0xFF34A350);
            holder.dc_money.setText("-" + info.getMoney() + "元");
            holder.dc_sxf.setText("");
        } else if (info.getCategory().equals("2")) {
            holder.dc_num.setText(info.getDate());
            holder.dc_type.setText("还款金额");
            holder.dc_img.setImageResource(R.drawable.dc_xf);
            holder.dc_sxf.setText("手续费 " + info.getTotalfee());
            holder.dc_money.setTextColor(0xFFD9BC84);
            holder.dc_money.setText("+" + info.getMoney() + "元");
        }
        return convertView;
    }

    class Holder {
        TextView dc_num, dc_sxf, dc_money, dc_type;
        ImageView dc_img;
    }

}
