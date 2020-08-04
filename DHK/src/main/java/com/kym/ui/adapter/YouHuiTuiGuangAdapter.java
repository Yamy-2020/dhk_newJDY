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
import com.kym.ui.info.YouHuiTuiGuangResponse;

import java.util.ArrayList;
import java.util.List;

public class YouHuiTuiGuangAdapter extends RecyclerView.Adapter {
    private Context context;
    private OnYHTuiGuangClick listener;
    private ArrayList<YouHuiTuiGuangResponse.YHTuiGuaangInfo> models = new ArrayList<>();

    public YouHuiTuiGuangAdapter(Context context, OnYHTuiGuangClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_youhui, parent, false);
        return new YouHuiTuiGuangAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof YouHuiTuiGuangAdapter.ContentViewHolder) {
            YouHuiTuiGuangAdapter.ContentViewHolder viewHolder = (YouHuiTuiGuangAdapter.ContentViewHolder) holder;
            YouHuiTuiGuangResponse.YHTuiGuaangInfo model = models.get(position);
            Glide.with(context).load(model.getTop_imgurl()).dontAnimate().into(viewHolder.lv_head_img);
            viewHolder.lv_tv1.setText(model.getName());
            viewHolder.lv_tv2.setText("推广"+(model.getTotalReferrals()-model.getDirectPeople()-model.getIndirectPeople())+"人即可升级");
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

    public void setData(List<YouHuiTuiGuangResponse.YHTuiGuaangInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnYHTuiGuangClick {
        void OnYHTuiGuangClick(YouHuiTuiGuangResponse.YHTuiGuaangInfo yhTuiGuaangInfo);
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
                    listener.OnYHTuiGuangClick(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
        }
    }
}
