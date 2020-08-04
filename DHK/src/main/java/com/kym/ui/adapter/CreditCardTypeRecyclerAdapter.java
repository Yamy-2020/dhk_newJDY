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
import com.kym.ui.newutil.HandleCardList;

import java.util.List;


public class CreditCardTypeRecyclerAdapter extends RecyclerView.Adapter<CreditCardTypeRecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private setMyOnItemClickListener mOnItemClickListener = null;
    private List<HandleCardList> data;
    private Context context;
    private HandleCardList info;

    public interface setMyOnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CreditCardTypeRecyclerAdapter(Context context, List<HandleCardList> data) {
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

    public void setMyOnItemClickListener(setMyOnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_credit_card_type, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        info = data.get(position);
        viewHolder.tv_card_type.setText(info.getTitle());
        Glide.with(context).load(info.getPicUrl()).placeholder(R.drawable.default_image)
                .error(R.drawable.default_image).dontAnimate().into(viewHolder.im_card_type);
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (data.size() == 0) {
            return 0;
        } else {
            return data.size();
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_card_type;
        ImageView im_card_type;

        ViewHolder(View view) {
            super(view);
            tv_card_type = view.findViewById(R.id.tv_card_type);
            im_card_type = view.findViewById(R.id.im_card_type);
        }
    }
}
