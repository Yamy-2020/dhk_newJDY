package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.info.SaoMaPassListResponse;

import java.util.ArrayList;
import java.util.List;

public class SaoMaPassListAdapter extends RecyclerView.Adapter {

    private Context context;
    private SaoMaPassListAdapter.OnCardList listener;
    private ArrayList<SaoMaPassListResponse.PassList> models = new ArrayList<>();

    public SaoMaPassListAdapter(Context context, SaoMaPassListAdapter.OnCardList listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saoma_pass_list, parent, false);
        return new SaoMaPassListAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SaoMaPassListAdapter.ContentViewHolder) {
            SaoMaPassListAdapter.ContentViewHolder viewHolder = (SaoMaPassListAdapter.ContentViewHolder) holder;
            SaoMaPassListResponse.PassList model = models.get(position);
            if (model.getIs_use().equals("1")) {
                Glide.with(context).load(model.getY_log()).dontAnimate().into(viewHolder.li_img);
            } else {
                Glide.with(context).load(model.getN_log()).dontAnimate().into(viewHolder.li_img);
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

    public void setData(List<SaoMaPassListResponse.PassList> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnCardList {
        void kj_card_list(SaoMaPassListResponse.PassList passList, int type);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView li_img;

        ContentViewHolder(View view) {
            super(view);
            li_img = itemView.findViewById(R.id.li_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kj_card_list(models.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
