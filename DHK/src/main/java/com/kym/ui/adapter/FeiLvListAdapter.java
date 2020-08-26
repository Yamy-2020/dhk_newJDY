package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.bean.FeiLv_MyBean;
import com.kym.ui.info.FeilvResponse;
import com.kym.ui.util.AmountUtils;

import java.util.ArrayList;
import java.util.List;

public class FeiLvListAdapter extends RecyclerView.Adapter {

    private Context context;
    private FeiLvListAdapter.OnKuaiJieInfo listener;
    private ArrayList<FeiLv_MyBean.DataBean.RateListBean> models ;

    public FeiLvListAdapter(Context context, ArrayList<FeiLv_MyBean.DataBean.RateListBean> arrayList) {
        this.context = context;
        this.models = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_feilv_list, parent, false);
        return new FeiLvListAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FeiLvListAdapter.ContentViewHolder) {
            FeiLvListAdapter.ContentViewHolder viewHolder = (FeiLvListAdapter.ContentViewHolder) holder;
            FeiLv_MyBean.DataBean.RateListBean bean = models.get(position);
            viewHolder.name.setText(bean.getName());
//            long l = Long.parseLong(bean.getRate());
            try {
                //AmountUtils.changeF2Y(l)
                viewHolder.rate.setText(AmountUtils.round(bean.getRate()*100)+"%");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Integer integer = Integer.parseInt(bean.getFee());
            viewHolder.fee.setText(integer/100 + "元/笔");
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


    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {
        TextView name, fee, rate;

        ContentViewHolder(final View view) {
            super(view);
            name = (TextView) itemView.findViewById(R.id.name);
            fee = (TextView) itemView.findViewById(R.id.fee);
            rate = (TextView) itemView.findViewById(R.id.rate);

        }
    }

    public interface OnKuaiJieInfo {

        void kuaijieClick(FeiLv_MyBean.DataBean.RateListBean kuaiJieInfo);
    }
}