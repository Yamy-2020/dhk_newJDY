package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.CardSignResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2018/1/29.
 * .
 */

public class CardSignAdapter extends RecyclerView.Adapter {
    private Context context;
    private CardSignAdapter.OnCardSignInfo listener;
    private ArrayList<CardSignResponse.CardSignInfo> models = new ArrayList<>();
    private int selectPosition;

    public CardSignAdapter(Context context, CardSignAdapter.OnCardSignInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_sign, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CardSignAdapter.ContentViewHolder) {
            CardSignAdapter.ContentViewHolder viewHolder = (CardSignAdapter.ContentViewHolder) holder;
            CardSignResponse.CardSignInfo model = models.get(position);
            viewHolder.sign_name.setText(model.getNickname());
            viewHolder.sign_img_yes.setVisibility(View.GONE);
            if (position == selectPosition) {
                viewHolder.sign_img_yes.setVisibility(View.VISIBLE);
            } else {
                viewHolder.sign_img_yes.setVisibility(View.GONE);
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

    public void setData(List<CardSignResponse.CardSignInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnCardSignInfo {
        void cardSignClick(CardSignResponse.CardSignInfo cardSignInfo);
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {
        TextView sign_name;
        ImageView sign_img_yes;
        LinearLayout tongdao;

        ContentViewHolder(final View view) {
            super(view);
            tongdao = (LinearLayout) itemView.findViewById(R.id.tongdao);
            sign_name = (TextView) itemView.findViewById(R.id.sign_name);
            sign_img_yes = (ImageView) itemView.findViewById(R.id.sign_img_yes);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPosition = getAdapterPosition();
                    listener.cardSignClick(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
        }
    }

}
