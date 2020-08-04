package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.FeilvResponse;

import java.util.ArrayList;
import java.util.List;

public class FeiLvListAdapter extends RecyclerView.Adapter {

    private Context context;
    private FeiLvListAdapter.OnKuaiJieInfo listener;
    private ArrayList<FeilvResponse.FeiLvInfo> models = new ArrayList<>();

    public FeiLvListAdapter(Context context, FeiLvListAdapter.OnKuaiJieInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_feilv_list, parent, false);
        return new FeiLvListAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FeiLvListAdapter.ContentViewHolder) {
            FeiLvListAdapter.ContentViewHolder viewHolder = (FeiLvListAdapter.ContentViewHolder) holder;
            FeilvResponse.FeiLvInfo model = models.get(position);
            viewHolder.name.setText(model.getName());
            viewHolder.rate.setText(model.getRate() + "%");
            viewHolder.fee.setText(model.getFee() + "元/笔");
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

    public void setData(List<FeilvResponse.FeiLvInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {
        TextView name, fee, rate;

        ContentViewHolder(final View view) {
            super(view);
            name = (TextView) itemView.findViewById(R.id.name);
            fee = (TextView) itemView.findViewById(R.id.fee);
            rate = (TextView) itemView.findViewById(R.id.rate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kuaijieClick(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface OnKuaiJieInfo {

        void kuaijieClick(FeilvResponse.FeiLvInfo kuaiJieInfo);
    }
}