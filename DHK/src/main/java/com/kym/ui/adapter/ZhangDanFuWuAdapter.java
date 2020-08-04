package com.kym.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.ZhangDanFuWuResponse;

import java.util.ArrayList;
import java.util.List;

public class ZhangDanFuWuAdapter extends RecyclerView.Adapter {
    private Context context;
    private ZhangDanFuWuAdapter.OnZhangDanFuWuClick listener;
    private ArrayList<ZhangDanFuWuResponse.ZhangDanFuWuInfo> models = new ArrayList<>();
    private int selectPosition;

    public ZhangDanFuWuAdapter(Context context, ZhangDanFuWuAdapter.OnZhangDanFuWuClick listener) {
        this.context = context;
        this.listener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_zhangdanfuwu, parent, false);
        return new ZhangDanFuWuAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ZhangDanFuWuAdapter.ContentViewHolder) {
            ZhangDanFuWuAdapter.ContentViewHolder viewHolder = (ZhangDanFuWuAdapter.ContentViewHolder) holder;
            ZhangDanFuWuResponse.ZhangDanFuWuInfo model = models.get(position);
            viewHolder.text_desc.setText(model.getTc_desc());
            viewHolder.text_name.setText(model.getTc_name());
            viewHolder.text_price.setText("¥ " + model.getPrice());
            viewHolder.price.setText(model.getOriginal_price() + "元");
            viewHolder.price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.text_num.setText(model.getNum() + "次");
            if (position == selectPosition){
                viewHolder.li.setBackgroundResource(R.drawable.btn_shape_zdtc);
            }else{
                viewHolder.li.setBackgroundResource(R.drawable.btn_shape_white);
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

    public void setData(List<ZhangDanFuWuResponse.ZhangDanFuWuInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnZhangDanFuWuClick {
        void OnZhangDanFuWuClick(ZhangDanFuWuResponse.ZhangDanFuWuInfo zhangdanfuwuInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView text_desc,text_name,text_price,price,text_num;
        LinearLayout li;
        public ContentViewHolder(View view) {
            super(view);
            text_desc = (TextView)itemView.findViewById(R.id.text_desc);
            text_name = (TextView)itemView.findViewById(R.id.text_name);
            text_price = (TextView)itemView.findViewById(R.id.text_price);
            price = (TextView)itemView.findViewById(R.id.price);
            text_num = (TextView)itemView.findViewById(R.id.text_num);
            li = (LinearLayout)itemView.findViewById(R.id.li);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPosition = getAdapterPosition();
                    listener.OnZhangDanFuWuClick(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
        }
    }
}
