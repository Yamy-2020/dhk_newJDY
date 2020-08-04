package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.XinYongZuJiResponse;

import java.util.ArrayList;
import java.util.List;

public class XinYongZuJiAdapter extends RecyclerView.Adapter {
    private Context context;
    private XinYongZuJiAdapter.OnXinYongZuJi listener;
    private ArrayList<XinYongZuJiResponse.XinYongZuJiInfo> models = new ArrayList<>();

    public XinYongZuJiAdapter(Context context, XinYongZuJiAdapter.OnXinYongZuJi listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xinyong_list, parent, false);
        return new XinYongZuJiAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof XinYongZuJiAdapter.ContentViewHolder) {
            XinYongZuJiAdapter.ContentViewHolder viewHolder = (XinYongZuJiAdapter.ContentViewHolder) holder;
            XinYongZuJiResponse.XinYongZuJiInfo model = models.get(position);
            viewHolder.time.setText(model.getCreatetime());
            viewHolder.type_name.setText(model.getType_name());
            viewHolder.desc.setText(model.getDesc());
            viewHolder.type.setText(model.getType());
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

    public void setData(List<XinYongZuJiResponse.XinYongZuJiInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnXinYongZuJi {
        void kj_card_list(XinYongZuJiResponse.XinYongZuJiInfo xinYongZuJiInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView time, type_name, desc ,type;

        public ContentViewHolder(View view) {
            super(view);
            time = (TextView) itemView.findViewById(R.id.time);
            type_name = (TextView) itemView.findViewById(R.id.type_name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            type = (TextView) itemView.findViewById(R.id.type);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kj_card_list(models.get(getAdapterPosition()));
                }
            });
        }
    }
}
