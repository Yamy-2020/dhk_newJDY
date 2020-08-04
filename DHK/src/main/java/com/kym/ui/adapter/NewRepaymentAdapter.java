package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.RepaymentPlanResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zachary on 2018/1/30.
 */

public class NewRepaymentAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<RepaymentPlanResponse.RepaymentPlanInfo> models = new ArrayList<>();

    public NewRepaymentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_repayment, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentViewHolder viewHolder = (ContentViewHolder) holder;
        RepaymentPlanResponse.RepaymentPlanInfo model = models.get(position);
        viewHolder.tvDate.setText(model.getTime());
        viewHolder.tvAmount.setText(String.valueOf(model.getMoney() / 100));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setData(List<RepaymentPlanResponse.RepaymentPlanInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {

        private final TextView tvAmount;
        private final TextView tvDate;

        ContentViewHolder(View view) {
            super(view);
            tvDate = (TextView) itemView.findViewById(R.id.tv_item_repayment_preview_date);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_item_repayment_preview_mount);
        }
    }
}