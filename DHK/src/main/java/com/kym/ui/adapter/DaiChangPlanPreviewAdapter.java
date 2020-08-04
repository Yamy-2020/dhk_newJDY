package com.kym.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.DaiChangPalnPreviewInfo;

import java.util.List;

public class DaiChangPlanPreviewAdapter extends BaseAdapter {

    private Activity activity;
    private List<DaiChangPalnPreviewInfo.DataBean.ListBean> mList;

    public DaiChangPlanPreviewAdapter(Activity activity, List<DaiChangPalnPreviewInfo.DataBean.ListBean> hotgoodsList) {
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
        final DaiChangPlanPreviewAdapter.Holder holder;
        if (convertView == null) {
            holder = new DaiChangPlanPreviewAdapter.Holder();
            convertView = activity.getLayoutInflater().inflate(R.layout.item_plan_preview, null);
            holder.dc_money = convertView.findViewById(R.id.dc_money);
            holder.dc_num = convertView.findViewById(R.id.dc_num);
            holder.dc_type = convertView.findViewById(R.id.dc_type);
            holder.dc_img = convertView.findViewById(R.id.dc_img);
            convertView.setTag(holder);
        } else {
            holder = (DaiChangPlanPreviewAdapter.Holder) convertView.getTag();
        }

        DaiChangPalnPreviewInfo.DataBean.ListBean info = mList.get(position);


        double money = Double.parseDouble(info.getMoney()) / 100;

        holder.dc_money.setText("¥ " + money);
        if (info.getType().equals("1")) {
            holder.dc_num.setText("还款时间 " + info.getTime());
            holder.dc_type.setText("还款金额");
            holder.dc_img.setImageResource(R.drawable.dc_hk);
        } else if (info.getType().equals("2")) {
            holder.dc_num.setText("消费时间 " + info.getTime());
            holder.dc_type.setText("消费金额");
            holder.dc_img.setImageResource(R.drawable.dc_xf);
        }

        return convertView;
    }

    class Holder {
        TextView dc_num, dc_money, dc_type;
        ImageView dc_img;

    }

}
