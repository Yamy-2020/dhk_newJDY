package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.TieResponse;

import java.util.ArrayList;
import java.util.List;

public class TieAdapter extends RecyclerView.Adapter {
    private Context context;
    private TieAdapter.OnZDClick listener;
    private ArrayList<TieResponse.TieInfo> models = new ArrayList<>();

    public TieAdapter(Context context, TieAdapter.OnZDClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tie, parent, false);
        return new TieAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TieAdapter.ContentViewHolder) {
            TieAdapter.ContentViewHolder viewHolder = (TieAdapter.ContentViewHolder) holder;
            TieResponse.TieInfo model = models.get(position);
            viewHolder.money.setText("额度：￥" + model.getBalance());
            viewHolder.time.setText("时间：" + model.getCreate_time());
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

    public void setData(List<TieResponse.TieInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnZDClick {
        void OnZDClick(TieResponse.TieInfo zdInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView money;
        TextView time;

        public ContentViewHolder(View view) {
            super(view);
            money = (TextView) itemView.findViewById(R.id.money);
            time = (TextView) itemView.findViewById(R.id.time);
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
