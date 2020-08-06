package com.kym.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.ZDResponse;

import java.util.ArrayList;
import java.util.List;

public class ZDAdapter extends RecyclerView.Adapter {
    private Context context;
    private ZDAdapter.OnZDClick listener;
    private ArrayList<ZDResponse.ZDInfo> models = new ArrayList<>();

    public ZDAdapter(Context context, ZDAdapter.OnZDClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_zd, parent, false);
        return new ZDAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ZDAdapter.ContentViewHolder) {
            ZDAdapter.ContentViewHolder viewHolder = (ZDAdapter.ContentViewHolder) holder;
            ZDResponse.ZDInfo model = models.get(position);
            viewHolder.money.setText(model.getBank_name()+"(" +model.getBank_no().substring(model.getBank_no().length() - 4)+")"+"-"+model.getNickname() + "-" + model.getMoney() + "元");
            viewHolder.time.setText(model.getCreatetime());
            viewHolder.status.setText(model.getStatus());
            if (model.getStatus().equals("未支付")) {
                viewHolder.status.setTextColor(0xFF34A350);

            }else {
                viewHolder.status.setTextColor(0xFFD9BC84);
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

    public void setData(List<ZDResponse.ZDInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnZDClick {
        void OnZDClick(ZDResponse.ZDInfo zdInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView money;
        TextView time;
        TextView status;

        public ContentViewHolder(View view) {
            super(view);
            money = (TextView) itemView.findViewById(R.id.zd_money);
            time = (TextView) itemView.findViewById(R.id.zd_time);
            status = (TextView) itemView.findViewById(R.id.zd_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnZDClick(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
        }
    }
}
