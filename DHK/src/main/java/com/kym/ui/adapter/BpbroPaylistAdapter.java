package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.PayListResponse;

import java.util.ArrayList;
import java.util.List;

public class BpbroPaylistAdapter extends RecyclerView.Adapter {
    private Context context;
    private BpbroPaylistAdapter.OnZDClick listener;
    private ArrayList<PayListResponse.ZDInfo> models = new ArrayList<>();

    public BpbroPaylistAdapter(Context context, BpbroPaylistAdapter.OnZDClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pay_list, parent, false);
        return new BpbroPaylistAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BpbroPaylistAdapter.ContentViewHolder) {
            BpbroPaylistAdapter.ContentViewHolder viewHolder = (BpbroPaylistAdapter.ContentViewHolder) holder;
            PayListResponse.ZDInfo model = models.get(position);
            viewHolder.money.setText("购买贷偿权益" + model.getUpfee() + "元/年");
            viewHolder.time.setText("购买日期" + model.getAddtime());
            viewHolder.dq.setText("到期日期" + model.getStoptime());
            viewHolder.status.setText(model.getStatus());
            if (model.getStatus().equals("使用中")) {
                viewHolder.status.setTextColor(0xFFFF6666);
            } else {
                viewHolder.status.setTextColor(0xFF34A350);
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

    public void setData(List<PayListResponse.ZDInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnZDClick {
        void OnZDClick(PayListResponse.ZDInfo zdInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView money;
        TextView time;
        TextView status, dq;

        public ContentViewHolder(View view) {
            super(view);
            money = (TextView) itemView.findViewById(R.id.zd_money);
            time = (TextView) itemView.findViewById(R.id.zd_time);
            status = (TextView) itemView.findViewById(R.id.zd_status);
            dq = (TextView) itemView.findViewById(R.id.dq_time);
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
