package com.kym.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.newutil.BannerBottom;
import com.kym.ui.util.AmountUtils;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private setMyOnItemClickListener mOnItemClickListener = null;
    private List<BannerBottom> data;
    private Context context;
    private BannerBottom info;

    public interface setMyOnItemClickListener {
        void onItemClick(View view, int position);
    }

    public RecyclerAdapter(Context context, List<BannerBottom> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            int tag = (Integer) v.getTag();
            mOnItemClickListener.onItemClick(v, tag);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_qrcode, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if (data.size() == 0) {
            info = data.get(position);
        } else {
            info = data.get(position+1);
        }
        viewHolder.textV_more.setText(info.getName());
        viewHolder.textV_titles.setText(info.getRemark());
        try {
            long parseLong = Long.parseLong(info.getAmount());
            String changeF2Y = AmountUtils.changeF2Y(parseLong);
            viewHolder.textV_price.setText("￥" + changeF2Y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(context).load(info.getImgUrl()).placeholder(R.drawable.default_image)
                .error(R.drawable.default_image).dontAnimate().into(viewHolder.imageV_more);
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (data.size() == 0) {
            return 0;
        } else {
            return data.size()-1;
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textV_more;
        TextView textV_titles;
        TextView textV_price;
        ImageView imageV_more;

        ViewHolder(View view) {
            super(view);
            textV_more = view.findViewById(R.id.tv_title);
            textV_titles = view.findViewById(R.id.tv_titles);
            textV_price = view.findViewById(R.id.tv_price);
            imageV_more = view.findViewById(R.id.imageV_typ_er);
        }
    }

}
