package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kym.ui.R;
import com.kym.ui.info.TuiGuangSuCaiResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunmiaolong on 2018/7/29.
 */

public class TuiGuangImgAdapter extends RecyclerView.Adapter {
    private Context context;
    private TuiGuangImgAdapter.OnKuaiJieInfo listener;
    private ArrayList<TuiGuangSuCaiResponse.TuiGuangInfo> models = new ArrayList<>();

    public TuiGuangImgAdapter(Context context, TuiGuangImgAdapter.OnKuaiJieInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tuiguang_img, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TuiGuangImgAdapter.ContentViewHolder) {
            TuiGuangImgAdapter.ContentViewHolder viewHolder = (TuiGuangImgAdapter.ContentViewHolder) holder;
            TuiGuangSuCaiResponse.TuiGuangInfo model = models.get(position);
            Glide.with(context).load(model.getShareImg()).diskCacheStrategy(DiskCacheStrategy.RESULT).placeholder(R.drawable.tgt_def).dontAnimate().into(viewHolder.img);
            viewHolder.img_text.setText(model.getName());
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

    public void setData(List<TuiGuangSuCaiResponse.TuiGuangInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {
        private ImageView img;
        private TextView img_text;

        ContentViewHolder(final View view) {
            super(view);
            img =  itemView.findViewById(R.id.img);
            img_text = itemView.findViewById(R.id.img_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kuaijieClick(models.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnKuaiJieInfo {

        void kuaijieClick(TuiGuangSuCaiResponse.TuiGuangInfo tuiGuangInfo);
    }
}
