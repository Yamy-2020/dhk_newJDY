package com.kym.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.activity.daichang.DaiChangCardPlanDetailActivity;
import com.kym.ui.info.DaiChangRecord;
import com.kym.ui.newutilutil.CircleImageView;

import java.util.List;

public class DaiChangRecordAdapter extends BaseAdapter {
    private Activity activity;
    private List<DaiChangRecord.DataBean> mList;

    public DaiChangRecordAdapter(Activity activity, List<DaiChangRecord.DataBean> hotgoodsList) {
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
            convertView = activity.getLayoutInflater().inflate(R.layout.item_plan_record, null);
            holder.dc_logo = convertView.findViewById(R.id.dc_logo);
            holder.dc_bank = convertView.findViewById(R.id.dc_bank);
            holder.dc_money = convertView.findViewById(R.id.dc_money);
            holder.dc_hknum = convertView.findViewById(R.id.dc_hknum);
            holder.dc_xfnum = convertView.findViewById(R.id.dc_xfnum);
            holder.dc_fee = convertView.findViewById(R.id.dc_fee);
            holder.dc_tip = convertView.findViewById(R.id.dc_tip);
            holder.dc_img = convertView.findViewById(R.id.dc_img);
            holder.zd_plan = convertView.findViewById(R.id.zd_plan);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final DaiChangRecord.DataBean info = mList.get(position);
        Glide.with(activity).load(info.getLogo_url()).into(holder.dc_logo);
        holder.dc_bank.setText(info.getBank_name() + " (尾号" + info.getAfterafew().substring(info.getAfterafew().length() - 4) + ")");
        holder.dc_money.setText((Double.parseDouble(info.getPayment_total_money()) / 100) + "元");
        holder.dc_hknum.setText(info.getPayment_total_number());
        holder.dc_xfnum.setText(info.getConsumpt_total_number());
        holder.dc_fee.setText((Double.parseDouble(info.getPoundage_total_fee()) / 100) + "元");
        if (info.getStatus().equals("1") || info.getStatus().equals("2")) {
            holder.dc_tip.setText("还款状态：执行中");
            holder.dc_tip.setTextColor(convertView.getResources().getColor(R.color.share));
            holder.dc_img.setImageResource(R.drawable.daiyue);
        } else if (info.getStatus().equals("4")) {
            holder.dc_tip.setText("还款状态：执行成功");
            holder.dc_tip.setTextColor(convertView.getResources().getColor(R.color.blue_public));
            holder.dc_img.setImageResource(R.drawable.shouyue);
        } else if (info.getStatus().equals("5")) {
            holder.dc_tip.setText("还款状态：执行失败");
            holder.dc_tip.setTextColor(convertView.getResources().getColor(R.color.delete));
            holder.dc_img.setImageResource(R.drawable.weiyue);
        }

        holder.zd_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DaiChangCardPlanDetailActivity.class);
                intent.putExtra("bill_id", info.getBill_id());
                activity.startActivity(intent);
            }
        });
        return convertView;
    }

    class Holder {
        private CircleImageView dc_logo;
        private TextView dc_bank, dc_money, dc_hknum, dc_xfnum, dc_fee, dc_tip, zd_plan;
        private ImageView dc_img;

    }
}
