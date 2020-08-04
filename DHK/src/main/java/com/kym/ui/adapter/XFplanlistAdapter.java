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

public class XFplanlistAdapter extends RecyclerView.Adapter {
    private Context context;
    private XFplanlistAdapter.OnKongKaInfo listener;
    private ArrayList<KongKaDaiInfo.KongKaInfo> models = new ArrayList<>();
    private final static int[] COLORS = new int[]{0xFF659cf7, 0xFF82d2f9};

    public XFplanlistAdapter(Context context, XFplanlistAdapter.OnKongKaInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xf_plan_record, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof XFplanlistAdapter.ContentViewHolder) {
            XFplanlistAdapter.ContentViewHolder viewHolder = (XFplanlistAdapter.ContentViewHolder) holder;
            KongKaDaiInfo.KongKaInfo model = models.get(position);
            Glide.with(context).load(model.getLogo_url()).dontAnimate().into(viewHolder.dc_logo);
            viewHolder.bank_name.setText("到账储蓄卡：" + model.getSettlmentBankName() + "(" + model.getSettlmentBankCard().substring(model.getSettlmentBankCard().length() - 4) + ")");
            viewHolder.dc_bank.setText(model.getBank_name() + "(" + model.getAfterafew().substring(model.getAfterafew().length() - 4) + ")");
            viewHolder.dc_money.setText((Double.parseDouble(model.getPayment_total_money()) / 100) + "元");
            viewHolder.dc_xfnum.setText(model.getConsumpt_number());
            viewHolder.dc_hknum.setText(model.getConsumpt_total_number());
            viewHolder.mWaveProgress.setGradientColors(COLORS);
            viewHolder.mWaveProgress.setValue((Float.parseFloat(model.getConsumpt_money()) * 100 / Float.parseFloat(model.getPayment_total_money())) * 100);
            viewHolder.xfnum.setText(model.getPayment_money());
            viewHolder.hknum.setText(model.getConsumpt_money());
            viewHolder.dc_fee.setText((Double.parseDouble(model.getPoundage_total_fee()) / 100) + "元");
            if (model.getStatus().equals("1") || model.getStatus().equals("2")) {
                viewHolder.dc_tip.setText("执行中");
                viewHolder.dc_tip.setTextColor(context.getResources().getColor(R.color.share));
                viewHolder.dc_img.setImageResource(R.drawable.daiyue);
            } else if (model.getStatus().equals("4")) {
                viewHolder.dc_tip.setText("执行成功");
                viewHolder.dc_tip.setTextColor(context.getResources().getColor(R.color.blue_public));
                viewHolder.dc_img.setImageResource(R.drawable.shouyue);
            } else if (model.getStatus().equals("5")) {
                viewHolder.dc_tip.setText("执行失败");
                viewHolder.dc_tip.setTextColor(context.getResources().getColor(R.color.delete));
                viewHolder.dc_img.setImageResource(R.drawable.weiyue);
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
        CircleImageView dc_logo;
        TextView dc_bank, dc_money, dc_hknum, dc_xfnum, dc_fee, dc_tip, zd_plan, hknum, xfnum, bank_name;
        ImageView dc_img;
        private WaveProgress mWaveProgress;

        ContentViewHolder(final View view) {
            super(view);
            dc_logo = itemView.findViewById(R.id.dc_logo);
            dc_bank = itemView.findViewById(R.id.dc_bank);
            dc_money = itemView.findViewById(R.id.dc_money);
            dc_hknum = itemView.findViewById(R.id.dc_hknum);
            dc_xfnum = itemView.findViewById(R.id.dc_xfnum);
            dc_fee = itemView.findViewById(R.id.dc_fee);
            dc_tip = itemView.findViewById(R.id.dc_tip);
            dc_img = itemView.findViewById(R.id.dc_img);
            zd_plan = itemView.findViewById(R.id.zd_plan);
            hknum = itemView.findViewById(R.id.hknum);
            xfnum = itemView.findViewById(R.id.xfnum);
            bank_name = itemView.findViewById(R.id.bank_name);
            mWaveProgress = itemView.findViewById(R.id.wave_progress_bar);

            zd_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kongkaClick(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });


        }
    }

    public interface OnKongKaInfo {

        void kongkaClick(KongKaDaiInfo.KongKaInfo kongKaInfo);
    }
}
