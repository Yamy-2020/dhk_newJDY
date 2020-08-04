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
import com.kym.ui.info.SignListResponse;

import java.util.ArrayList;
import java.util.List;

public class SignListAdapter extends RecyclerView.Adapter {
    private Context context;
    private SignListAdapter.OnSignInfo listener;
    private ArrayList<SignListResponse.SignInfo> models = new ArrayList<>();
    private int selectPosition;

    public SignListAdapter(Context context, OnSignInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sign_list, parent, false);
        return new SignListAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SignListAdapter.ContentViewHolder) {
            SignListAdapter.ContentViewHolder viewHolder = (SignListAdapter.ContentViewHolder) holder;
            SignListResponse.SignInfo model = models.get(position);
            viewHolder.tvSk_tongdao.setText(model.getNickname());
            if (position == selectPosition){
                viewHolder.tvImg2.setVisibility(View.VISIBLE);
            }else{
                viewHolder.tvImg2.setVisibility(View.GONE);
            }
        }
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    @Override
    public int getItemViewType(int position) {

        return models.size();

    }


    public void setData(List<SignListResponse.SignInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }


    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {
        LinearLayout mListLayout;
        TextView tvSk_tongdao;
        ImageView tvImg2;

        ContentViewHolder(final View view) {
            super(view);
            mListLayout = (LinearLayout)itemView.findViewById(R.id.DeviceList_Layout);
            tvSk_tongdao = (TextView) itemView.findViewById(R.id.tv1);
            tvImg2 = (ImageView) itemView.findViewById(R.id.tvImg2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPosition = getAdapterPosition();
                    listener.signClick(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });


        }
    }

    public interface OnSignInfo {

        void signClick(SignListResponse.SignInfo signInfo);
    }
}
