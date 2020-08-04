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
import com.kym.ui.info.YouHuiShengJiResponse;

import java.util.ArrayList;
import java.util.List;

public class YouHuiShengJiAdapter extends RecyclerView.Adapter {
    private Context context;
    private OnYHShengJiClick listener;
    private ArrayList<YouHuiShengJiResponse.YHShengJiInfo> models = new ArrayList<>();

    public YouHuiShengJiAdapter(Context context, OnYHShengJiClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_youhui, parent, false);
        return new YouHuiShengJiAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof YouHuiShengJiAdapter.ContentViewHolder) {
            YouHuiShengJiAdapter.ContentViewHolder viewHolder = (YouHuiShengJiAdapter.ContentViewHolder) holder;
            YouHuiShengJiResponse.YHShengJiInfo model = models.get(position);
            Glide.with(context).load(model.getTop_imgurl()).dontAnimate().into(viewHolder.lv_head_img);
            viewHolder.lv_tv1.setText("升级到"+model.getName());
            viewHolder.lv_tv2.setText("付费"+model.getMoney()+"元即可升级");
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

    public void setData(List<YouHuiShengJiResponse.YHShengJiInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnYHShengJiClick {
        void OnYHShengJiClick(YouHuiShengJiResponse.YHShengJiInfo yhShengJiInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView lv_tv1;
        TextView lv_tv2;
        ImageView lv_head_img;
        public ContentViewHolder(View view) {
            super(view);
            lv_tv1 = (TextView)itemView.findViewById(R.id.lv_tv1);
            lv_tv2 = (TextView)itemView.findViewById(R.id.lv_tv2);
            lv_head_img = (ImageView) itemView.findViewById(R.id.lv_head_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnYHShengJiClick(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
        }
    }
}
