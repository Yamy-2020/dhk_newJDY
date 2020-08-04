package com.kym.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.BillPlanResponse;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.DIsplayUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangph on 2018/1/29.
 * .
 */

public class BankPlanAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BillPlanResponse.BillPlanInfo> models = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Date date = new Date();
    private int dp;

    public BankPlanAdapter(Context context) {
        this.context = context;
        dp = DIsplayUtils.dp2px(context, 6);

    }

    @Override
    public int getCount() {
        return models.size();
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
        final BankPlanAdapter.viewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bank_plan, null);
            holder = new BankPlanAdapter.viewHolder();
            holder.bar = convertView.findViewById(R.id.Progressbar);
            holder.tv2 = convertView.findViewById(R.id.tv2);//还款金额
            holder.tv3 = convertView.findViewById(R.id.tv3);//计划是否确认
            holder.tv5 = convertView.findViewById(R.id.tv5);//计划时间
            holder.tv7 = convertView.findViewById(R.id.tv7);//手续费
            holder.tv9 = convertView.findViewById(R.id.tv9);//还款进度
            convertView.setTag(holder);
        } else {
            holder = (BankPlanAdapter.viewHolder) convertView.getTag();
        }

        BillPlanResponse.BillPlanInfo model = models.get(position);
        holder.tv2.setText(AmountUtils.round(model.getRepay_money() / 100));
        date.setTime(model.getAddtime() * 1000);
        String format = sdf.format(date);
        holder.tv5.setText(format);
        holder.tv7.setText(AmountUtils.round((model.getRepay_fee() + model.getSummaryisfee()) / 100));
        holder.tv9.setText(String.format("%d/%d", model.getCash_number(), model.getRepay_number()));
        holder.bar.setProgress(model.getCash_number() * 100 / model.getRepay_number());
        String status = model.getStatus();
        if (TextUtils.equals(status, "0")) {
            //计划未确认
            holder.tv3.setText("计划未确认");
            holder.tv3.setTextColor(context.getResources().getColor(R.color.gray_a7));
            Drawable drawable = context.getResources().getDrawable(R.drawable.jihua_d);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tv3.setCompoundDrawablePadding(dp);
            holder.tv3.setCompoundDrawables(drawable, null, null, null);
        } else {
            switch (status) {
                case "1":
                    holder.tv3.setText("计划已确认");
                    holder.tv3.setTextColor(context.getResources().getColor(R.color.blue_21));
                    break;
                case "2":
                    holder.tv3.setText("还款中");
                    holder.tv3.setTextColor(context.getResources().getColor(R.color.blue_21));
                    break;
                case "4":
                    holder.tv3.setText("已完成");
                    holder.tv3.setTextColor(0xFF999999);
                    break;
                case "5":
                    holder.tv3.setText("扣款失败");
                    holder.tv3.setTextColor(context.getResources().getColor(R.color.red));
                    break;
                default:
                    holder.tv3.setText("状态未知");
                    holder.tv3.setTextColor(context.getResources().getColor(R.color.blue_21));
                    break;

            }
            holder.tv3.setTextColor(context.getResources().getColor(R.color.blue_21));
            Drawable drawable = context.getResources().getDrawable(R.drawable.jihua_y);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tv3.setCompoundDrawablePadding(dp);
            holder.tv3.setCompoundDrawables(drawable, null, null, null);
        }
        return convertView;
    }

    public void setData(List<BillPlanResponse.BillPlanInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void insertData(List<BillPlanResponse.BillPlanInfo> data) {
        models.addAll(data);
//        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    class viewHolder {
        ProgressBar bar;
        TextView tv2;
        TextView tv3;
        TextView tv5;
        TextView tv7;
        TextView tv9;
    }
}
