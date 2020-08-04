package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.BenJinZhangHu;

import java.util.ArrayList;
import java.util.List;

public class BenJinZhangHuAdapter extends RecyclerView.Adapter {
    private Context context;
    private BenJinZhangHuAdapter.OnKuaiJieCardList listener;
    private ArrayList<BenJinZhangHu.BenJInZhangHuInfo> models = new ArrayList<>();
    private int selectPosition;

    public BenJinZhangHuAdapter(Context context, BenJinZhangHuAdapter.OnKuaiJieCardList listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_benjinzhanghu, parent, false);
        return new BenJinZhangHuAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BenJinZhangHuAdapter.ContentViewHolder) {
            BenJinZhangHuAdapter.ContentViewHolder viewHolder = (BenJinZhangHuAdapter.ContentViewHolder) holder;
            BenJinZhangHu.BenJInZhangHuInfo model = models.get(position);
            viewHolder.dc_text.setText(model.getName());
            viewHolder.dc_money.setText(model.getMoney());
            if (position == selectPosition) {
                viewHolder.dc_li.setBackgroundResource(R.drawable.label_bg_no);
                viewHolder.dc_text.setTextColor(context.getResources().getColor(R.color.blue_2e));
                viewHolder.dc_money.setTextColor(context.getResources().getColor(R.color.blue_2e));
            } else {
                viewHolder.dc_li.setBackgroundResource(R.drawable.label_bg);
                viewHolder.dc_text.setTextColor(context.getResources().getColor(R.color.black_33));
                viewHolder.dc_money.setTextColor(context.getResources().getColor(R.color.black_33));
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

    public void setData(List<BenJinZhangHu.BenJInZhangHuInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnKuaiJieCardList {

        void kj_card_list(BenJinZhangHu.BenJInZhangHuInfo kuaiJieCardListInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView dc_text, dc_money;
        private LinearLayout dc_li;


        ContentViewHolder(View view) {
            super(view);
            dc_text = (TextView) itemView.findViewById(R.id.dc_text);
            dc_money = (TextView) itemView.findViewById(R.id.dc_money);
            dc_li = (LinearLayout) itemView.findViewById(R.id.dc_li);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPosition = getAdapterPosition();
                    listener.kj_card_list(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
        }
    }
}
