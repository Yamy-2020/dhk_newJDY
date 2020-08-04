package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.DateUtils;
import com.kym.ui.R;
import com.kym.ui.newutilutil.BonusOrderDatum;

import java.util.List;

/**
 * Created by zachary on 2018/1/22.
 */

public class CreditCardOrderAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<BonusOrderDatum> bonusOrderlist;

    public CreditCardOrderAdapter(Context context, List<BonusOrderDatum> bonusOrderlist) {
        this.context = context;
        this.bonusOrderlist=bonusOrderlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_credit_card_order, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentViewHolder viewHolder = (ContentViewHolder) holder;

        BonusOrderDatum bonusOrderDatum = bonusOrderlist.get(position);
        viewHolder.tvName.setText(bonusOrderDatum.getName());
        viewHolder.tvMobile.setText(bonusOrderDatum.getMobile());
        String addTime = bonusOrderDatum.getAddTime();
        long l = Long.parseLong(addTime);
        String dateToString9 = DateUtils.getDateToString9(l*1000);
        viewHolder.tvDate.setText(dateToString9);
        viewHolder.tvBank.setText(bonusOrderDatum.getBankName());
        if (bonusOrderDatum.getType().equals("2")) {
            viewHolder.tvBank.setSelected(true);
            viewHolder.tvStatus.setSelected(true);
            viewHolder.tvStatus.setText("成功");
        } else if (bonusOrderDatum.getType().equals("3")){
            viewHolder.tvBank.setSelected(false);
            viewHolder.tvStatus.setSelected(false);
            viewHolder.tvStatus.setText("不通过");
        }else if (bonusOrderDatum.getType().equals("1")){
            viewHolder.tvBank.setSelected(false);
            viewHolder.tvStatus.setSelected(false);
            viewHolder.tvStatus.setText("申请中");
        }
    }

    @Override
    public int getItemCount() {
        return bonusOrderlist.size();
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {

        private final TextView tvName;
        private final TextView tvMobile;
        private final TextView tvDate;
        private final TextView tvStatus;
        private final TextView tvBank;

        public ContentViewHolder(View view) {
            super(view);
            tvName = (TextView) itemView.findViewById(R.id.tv_order_name);
            tvMobile = (TextView) itemView.findViewById(R.id.tv_order_mobile);
            tvDate = (TextView) itemView.findViewById(R.id.tv_order_date);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_order_status);
            tvBank = (TextView) itemView.findViewById(R.id.tv_order_bank_name);
        }
    }
}
