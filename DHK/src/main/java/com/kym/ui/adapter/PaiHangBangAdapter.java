package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.info.PaiHangBangResponse;
import com.kym.ui.newutilutil.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class PaiHangBangAdapter extends RecyclerView.Adapter {
    private Context context;
    private PaiHangBangAdapter.OnZDClick listener;
    private ArrayList<PaiHangBangResponse.PaiHangBangInfo> models = new ArrayList<>();

    public PaiHangBangAdapter(Context context, PaiHangBangAdapter.OnZDClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phb, parent, false);
        return new PaiHangBangAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PaiHangBangAdapter.ContentViewHolder) {
            PaiHangBangAdapter.ContentViewHolder viewHolder = (PaiHangBangAdapter.ContentViewHolder) holder;
            PaiHangBangResponse.PaiHangBangInfo model = models.get(position);
            viewHolder.text1.setText(model.getGrade());
            Glide.with(context).load(model.getHeaderimg()).error(R.drawable.icon)
                    .dontAnimate().into(viewHolder.img1);
            if(model.getName().equals("")){
                viewHolder.text2.setText("***");
            } else {
                viewHolder.text2.setText(model.getName());
            }
            viewHolder.text3.setText(model.getVolume());
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

    public void setData(List<PaiHangBangResponse.PaiHangBangInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnZDClick {
        void OnZDClick(PaiHangBangResponse.PaiHangBangInfo zdInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        TextView text3;
        CircleImageView img1;

        public ContentViewHolder(View view) {
            super(view);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);
            img1 = itemView.findViewById(R.id.imageV_dj_log);
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
