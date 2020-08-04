package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.littlejie.circleprogress.WaveProgress;
import com.kym.ui.R;
import com.kym.ui.info.KongKaDaiInfo;
import com.kym.ui.newutilutil.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class HKplanlistAdapter extends RecyclerView.Adapter {
    private Context context;
    private HKplanlistAdapter.OnKongKaInfo listener;
    private ArrayList<KongKaDaiInfo.KongKaInfo> models = new ArrayList<>();
    private final static int[] COLORS = new int[]{0xFF659cf7, 0xFF82d2f9};


    public HKplanlistAdapter(Context context, HKplanlistAdapter.OnKongKaInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hk_plan_record, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HKplanlistAdapter.ContentViewHolder) {
            HKplanlistAdapter.ContentViewHolder viewHolder = (HKplanlistAdapter.ContentViewHolder) holder;
            KongKaDaiInfo.KongKaInfo model = models.get(position);
            Glide.with(context).load(model.getLogo_url()).dontAnimate().into(viewHolder.bank_logo);
            viewHolder.bank_name.setText(model.getBank_name() + " (尾号" + model.getAfterafew().substring(model.getAfterafew().length() - 4) + ")");
            viewHolder.hk_money.setText((Double.parseDouble(model.getPayment_total_money()) / 100) + "元");
            viewHolder.yh_money.setText(model.getPayment_money() + "元");
            viewHolder.wh_money.setText(model.getSurplus_money() + "元");
            viewHolder.hk_date.setText(model.getSurplus_day() + "天");
            viewHolder.plan_date.setText(model.getSurplus_planday() + "天");
            viewHolder.mWaveProgress.setGradientColors(COLORS);
            viewHolder.mWaveProgress.setValue((Float.parseFloat(model.getConsumpt_money()) * 100 / Float.parseFloat(model.getPayment_total_money())) * 100);
            viewHolder.hk_fee.setText((Double.parseDouble(model.getPoundage_total_fee()) / 100) + "元");
            if (model.getStatus().equals("1") || model.getStatus().equals("2")) {
                viewHolder.hk_tip.setText("还款状态：执行中");
                viewHolder.hk_tip.setTextColor(context.getResources().getColor(R.color.share));
                viewHolder.hk_img.setImageResource(R.drawable.daiyue);
            } else if (model.getStatus().equals("4")) {
                viewHolder.hk_tip.setText("还款状态：执行成功");
                viewHolder.hk_tip.setTextColor(context.getResources().getColor(R.color.blue_public));
                viewHolder.hk_img.setImageResource(R.drawable.shouyue);
            } else if (model.getStatus().equals("5")) {
                viewHolder.hk_tip.setText("还款状态：执行失败");
                viewHolder.hk_tip.setTextColor(context.getResources().getColor(R.color.delete));
                viewHolder.hk_img.setImageResource(R.drawable.weiyue);
            }

            if (model.getIs_show().equals("0")) {
                viewHolder.re_plan.setVisibility(View.GONE);
            } else {
                viewHolder.re_plan.setVisibility(View.VISIBLE);
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

    public void setData(List<KongKaDaiInfo.KongKaInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {
        CircleImageView bank_logo;
        TextView bank_name, hk_money, yh_money, wh_money, hk_fee, hk_date, plan_date, zd_plan, re_plan, hk_tip;
        ImageView hk_img;
        private WaveProgress mWaveProgress;

        ContentViewHolder(final View view) {
            super(view);
            bank_logo = itemView.findViewById(R.id.bank_logo);
            bank_name = itemView.findViewById(R.id.bank_name);
            hk_money = itemView.findViewById(R.id.hk_money);
            yh_money = itemView.findViewById(R.id.yh_money);
            wh_money = itemView.findViewById(R.id.wh_money);
            hk_fee = itemView.findViewById(R.id.hk_fee);
            hk_date = itemView.findViewById(R.id.hk_date);
            plan_date = itemView.findViewById(R.id.plan_date);
            zd_plan = itemView.findViewById(R.id.zd_plan);
            re_plan = itemView.findViewById(R.id.re_plan);
            hk_tip = itemView.findViewById(R.id.hk_tip);
            hk_img = itemView.findViewById(R.id.hk_img);
            mWaveProgress = itemView.findViewById(R.id.wave_progress_bar);

            zd_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kongkaClick(models.get(getAdapterPosition()), "0");
                }
            });

            re_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kongkaClick(models.get(getAdapterPosition()), "1");
                }
            });
        }
    }

    public interface OnKongKaInfo {

        void kongkaClick(KongKaDaiInfo.KongKaInfo kongKaInfo, String type);
    }
}
