package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.LingQianResponse;

import java.util.ArrayList;
import java.util.List;

import static com.kym.ui.DateUtils.getDateToString101;

public class LingQianAdapter extends RecyclerView.Adapter {
    private Context context;
    private LingQianAdapter.OnZDClick listener;
    private ArrayList<LingQianResponse.LingQianInfo> models = new ArrayList<>();

    public LingQianAdapter(Context context, LingQianAdapter.OnZDClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_zd, parent, false);
        return new LingQianAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LingQianAdapter.ContentViewHolder) {
            LingQianAdapter.ContentViewHolder viewHolder = (LingQianAdapter.ContentViewHolder) holder;
            LingQianResponse.LingQianInfo model = models.get(position);
            viewHolder.money.setText(model.getMoney() + "元");
            viewHolder.time.setText(getDateToString101(Long.parseLong(model.getAddtime())));
            viewHolder.status.setText(model.getType());
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

    public void setData(List<LingQianResponse.LingQianInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnZDClick {
        void OnZDClick(LingQianResponse.LingQianInfo zdInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView money;
        TextView time;
        TextView status;
        public ContentViewHolder(View view) {
            super(view);
            money = (TextView)itemView.findViewById(R.id.zd_money);
            time = (TextView)itemView.findViewById(R.id.zd_time);
            status = (TextView)itemView.findViewById(R.id.zd_status);
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
