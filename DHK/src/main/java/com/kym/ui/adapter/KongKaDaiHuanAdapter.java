package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.info.KongKaDaiInfo;
import com.kym.ui.newutilutil.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class KongKaDaiHuanAdapter extends RecyclerView.Adapter {
    private Context context;
    private KongKaDaiHuanAdapter.OnKongKaInfo listener;
    private ArrayList<KongKaDaiInfo.KongKaInfo> models = new ArrayList<>();

    public KongKaDaiHuanAdapter(Context context, KongKaDaiHuanAdapter.OnKongKaInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plan_record, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof KongKaDaiHuanAdapter.ContentViewHolder) {
            KongKaDaiHuanAdapter.ContentViewHolder viewHolder = (KongKaDaiHuanAdapter.ContentViewHolder) holder;
            KongKaDaiInfo.KongKaInfo model = models.get(position);
            Glide.with(context).load(model.getLogo_url()).dontAnimate().into(viewHolder.dc_logo);
            viewHolder.dc_bank.setText(model.getBank_name()+" (尾号"+model.getAfterafew().substring(model.getAfterafew().length()-4)+")");
            viewHolder.dc_money.setText((Double.parseDouble(model.getPayment_total_money()) / 100) + "元");
            viewHolder.dc_hknum.setText(model.getPayment_total_number());
            viewHolder.dc_xfnum.setText(model.getConsumpt_total_number());
            viewHolder.dc_fee.setText((Double.parseDouble(model.getPoundage_total_fee()) / 100) + "元");
            if (model.getStatus().equals("1") || model.getStatus().equals("2")) {
                viewHolder.dc_tip.setText("还款状态：执行中");
                viewHolder.dc_tip.setTextColor(context.getResources().getColor(R.color.share));
                viewHolder.dc_img.setImageResource(R.drawable.daiyue);
            } else if (model.getStatus().equals("4")) {
                viewHolder.dc_tip.setText("还款状态：执行成功");
                viewHolder.dc_tip.setTextColor(context.getResources().getColor(R.color.blue_public));
                viewHolder.dc_img.setImageResource(R.drawable.shouyue);
            } else if (model.getStatus().equals("5")) {
                viewHolder.dc_tip.setText("还款状态：执行失败");
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
        TextView dc_bank,dc_money,dc_hknum,dc_xfnum,dc_fee,dc_tip,zd_plan;
        ImageView dc_img;
        ContentViewHolder(final View view) {
            super(view);
            dc_logo = (CircleImageView) itemView.findViewById(R.id.dc_logo);
            dc_bank = (TextView) itemView.findViewById(R.id.dc_bank);
            dc_money = (TextView) itemView.findViewById(R.id.dc_money);
            dc_hknum = (TextView) itemView.findViewById(R.id.dc_hknum);
            dc_xfnum = (TextView) itemView.findViewById(R.id.dc_xfnum);
            dc_fee = (TextView) itemView.findViewById(R.id.dc_fee);
            dc_tip = (TextView) itemView.findViewById(R.id.dc_tip);
            dc_img = (ImageView) itemView.findViewById(R.id.dc_img);
            zd_plan = (TextView) itemView.findViewById(R.id.zd_plan);
            itemView.setOnClickListener(new View.OnClickListener() {
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
