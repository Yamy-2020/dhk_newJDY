package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.BenJinTradeListResponse;

import java.util.ArrayList;
import java.util.List;

public class BenJinTradeListAdapter extends RecyclerView.Adapter {
    private Context context;
    private BenJinTradeListAdapter.OnZDClick listener;
    private ArrayList<BenJinTradeListResponse.ZDInfo> models = new ArrayList<>();

    public BenJinTradeListAdapter(Context context, BenJinTradeListAdapter.OnZDClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_zd, parent, false);
        return new BenJinTradeListAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BenJinTradeListAdapter.ContentViewHolder) {
            BenJinTradeListAdapter.ContentViewHolder viewHolder = (BenJinTradeListAdapter.ContentViewHolder) holder;
            BenJinTradeListResponse.ZDInfo model = models.get(position);
            viewHolder.money.setText(model.getType_source_name() + model.getType_name() + model.getAmount() + "元");
            viewHolder.time.setText(model.getAddtime());
            if (model.getStatus().equals("成功")) {
                viewHolder.status.setTextColor(0xFF34A350);
            } else {
                viewHolder.status.setTextColor(0xFFFF6666);
            }
            viewHolder.status.setText(model.getStatus());
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

    public void setData(List<BenJinTradeListResponse.ZDInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnZDClick {
        void OnZDClick(BenJinTradeListResponse.ZDInfo zdInfo);
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
