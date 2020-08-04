package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.DateUtils;
import com.kym.ui.R;
import com.kym.ui.info.BillDetailResponse;
import com.kym.ui.util.AmountUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zachary on 2018/2/9.
 */

public class NewRepaymentDetailAdapter extends RecyclerView.Adapter {

    private final int TYPE_HEADER = 0;
    private final int TYPE_CONTENT = 1;

    private Context context;
    private BillDetailResponse.BillDetailInfo data;
    private ArrayList<BillDetailResponse.BillPlanInfo> models = new ArrayList<>();
    private double perFee;

    public NewRepaymentDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(context).inflate(R.layout.item_repayment_detail_header, parent, false);
                return new HeaderViewHolder(view);
            case TYPE_CONTENT:
                view = LayoutInflater.from(context).inflate(R.layout.item_repayment_detail_content, parent, false);
                return new ContentViewHolder(view);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            if (data == null) {
                viewHolder.tvTotalMoney.setText("******");
            } else {
                String totalMoney = AmountUtils.round((data.getRepay_money() / 100));
                viewHolder.tvTotalMoney.setText(totalMoney);
            }
        } else if (holder instanceof ContentViewHolder) {
            ContentViewHolder viewHolder = (ContentViewHolder) holder;
            BillDetailResponse.BillPlanInfo model = models.get(position - 1);
            viewHolder.tvPercent.setText(String.format("当前进度 %d/%d", position, models.size()));
            viewHolder.tvAmount.setText(AmountUtils.round(model.getPlay_money() / 100));
            viewHolder.tvFee.setText(AmountUtils.round(model.getRepay_fee() / 100 + perFee));
            viewHolder.tvDate.setText(DateUtils.getDateToString8(model.getAddtime() * 1000));
            setStatusText(viewHolder.tvStatus, model.getStatus(), model.getPaystatus());
        }
    }

    @Override
    public int getItemCount() {
        return models.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_CONTENT;
    }

    public void setData(BillDetailResponse.BillDetailInfo data) {
        this.data = data;
        models.clear();
        List<BillDetailResponse.BillPlanInfo> plans = data.getPlan();
        if (plans != null && plans.size() > 0) {
            models.addAll(plans);
            perFee = data.getSummaryisfee() / plans.size() / 100;
        }
        notifyDataSetChanged();
    }

    private void setStatusText(TextView tvStatus, String status, String payStatus) {
        switch (status) {
            case "0"://未还款
                tvStatus.setText("未还款");
                tvStatus.setTextColor(context.getResources().getColor(R.color.blue_21));
                break;
            case "1"://待还款
                tvStatus.setText("待还款");
                tvStatus.setTextColor(context.getResources().getColor(R.color.blue_21));
                break;
            case "2"://还款中
                tvStatus.setText("还款中");
                tvStatus.setTextColor(context.getResources().getColor(R.color.blue_21));
                break;
            case "4"://已完成
                tvStatus.setText("已完成");
                tvStatus.setTextColor(0xFF999999);
                break;
            case "5"://失败
                tvStatus.setText("失败");
                tvStatus.setTextColor(context.getResources().getColor(R.color.red));
                break;
            default:
                break;
        }
        if (TextUtils.equals(payStatus, "2")) {
            tvStatus.setText("扣款失败");
            tvStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {

        private final TextView tvPercent;
        private final TextView tvStatus;
        private final TextView tvAmount;
        private final TextView tvFee;
        private final TextView tvDate;

        ContentViewHolder(View view) {
            super(view);
            tvPercent = (TextView) itemView.findViewById(R.id.tv_repayment_detail_percent);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_repayment_detail_status);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_repayment_detail_amount);
            tvFee = (TextView) itemView.findViewById(R.id.tv_repayment_detail_fee);
            tvDate = (TextView) itemView.findViewById(R.id.tv_repayment_detail_date);
        }
    }

    private class HeaderViewHolder extends RecyclerAdapter.ViewHolder {

        private final TextView tvTotalMoney;

        HeaderViewHolder(View view) {
            super(view);
            tvTotalMoney = (TextView) itemView.findViewById(R.id.tv_repayment_detail_header);
        }
    }
}