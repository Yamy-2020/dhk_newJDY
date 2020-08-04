package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.DaiChangCardInfo;

import java.util.ArrayList;
import java.util.List;

import static com.kym.ui.DateUtils.getDateToString10;

public class HKCardAdapter extends RecyclerView.Adapter {
    private Context context;
    private HKCardAdapter.OnDaiChangCardInfo listener;
    private ArrayList<DaiChangCardInfo.DaiChangCardData> models = new ArrayList<>();

    public HKCardAdapter(Context context, HKCardAdapter.OnDaiChangCardInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dai_chang_card, parent, false);
        return new HKCardAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HKCardAdapter.ContentViewHolder) {
            HKCardAdapter.ContentViewHolder viewHolder = (HKCardAdapter.ContentViewHolder) holder;
            DaiChangCardInfo.DaiChangCardData model = models.get(position);
            if (model.getType().equals("1")) {
                viewHolder.dc_type.setText("消费金额");
                viewHolder.dc_img.setImageResource(R.drawable.dc_xf);
                viewHolder.dc_money.setText("-" + (Double.parseDouble(model.getAmount()) / 100) + "元");
                viewHolder.dc_money.setTextColor(0xFF34A350);
            } else {
                viewHolder.dc_type.setText("还款金额");
                viewHolder.dc_img.setImageResource(R.drawable.dc_hk);
                viewHolder.dc_money.setText("+" + (Double.parseDouble(model.getAmount()) / 100) + "元");
                viewHolder.dc_money.setTextColor(0xFFFF6666);
            }

            viewHolder.dc_time.setText(getDateToString10(Long.parseLong(model.getTasktime())));
            if (model.getStatus().equals("1")) {
                viewHolder.dc_status.setText("待执行");
                viewHolder.dc_status.setTextColor(context.getResources().getColor(R.color.share));
            } else if (model.getStatus().equals("2") || viewHolder.dc_status.equals("3")) {
                viewHolder.dc_status.setText("执行中");
                viewHolder.dc_status.setTextColor(context.getResources().getColor(R.color.blue_2e));
            } else if (model.getStatus().equals("4")) {
                viewHolder.dc_status.setText("执行成功");
                viewHolder.dc_status.setTextColor(context.getResources().getColor(R.color.blue_public));
            } else if (model.getStatus().equals("5")) {
                viewHolder.dc_status.setText("已终止");
                viewHolder.dc_status.setTextColor(context.getResources().getColor(R.color.delete));
            }
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {

        return models.size();

    }

    public void setData(List<DaiChangCardInfo.DaiChangCardData> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {
        TextView dc_type, dc_money, dc_time, dc_status;
        ImageView dc_img;

        ContentViewHolder(final View view) {
            super(view);
            dc_type = (TextView) itemView.findViewById(R.id.dc_type);
            dc_money = (TextView) itemView.findViewById(R.id.dc_money);
            dc_time = (TextView) itemView.findViewById(R.id.dc_time);
            dc_status = (TextView) itemView.findViewById(R.id.dc_status);
            dc_img = (ImageView) itemView.findViewById(R.id.dc_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.daichangClick(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });


        }
    }

    public interface OnDaiChangCardInfo {

        void daichangClick(DaiChangCardInfo.DaiChangCardData dachangcardInfo);
    }
}
