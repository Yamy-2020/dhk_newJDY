package com.kym.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.kym.ui.info.GouMaiQuanYi;
import java.util.List;

public class GouMaiAdapter extends RecyclerView.Adapter {
    private final Context context;

    private final List<GouMaiQuanYi.DataBean.PaymentListBean> payment_list;

    public GouMaiAdapter(Context context, List<GouMaiQuanYi.DataBean.PaymentListBean> payment_list) {
        this.context = context;
        this.payment_list = payment_list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);


        return new ItemHolder(inflater.inflate(R.layout.ke_tang_rv1, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        ItemHolder itemHolder = (ItemHolder) viewHolder;

        itemHolder.mName.setText(payment_list.get(position).getName());
        itemHolder.mPaymentAmount.setText("¥  " + payment_list.get(position).getPayment_amount());
        itemHolder.mUpgradeNumber.setText(payment_list.get(position).getUpgrade_number());
        itemHolder.mLf.setText(payment_list.get(position).getRate_list().getSk().getLf());
        itemHolder.mLf1.setText(payment_list.get(position).getRate_list().getYk().getLf());
        itemHolder.mLf2.setText(payment_list.get(position).getRate_list().getHk().getLf());
        itemHolder.mLookOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListenter != null) {
                    mItemClickListenter.onItemClickListenter2(position, v);
                }
            }
        });

        itemHolder.mShengji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListenter != null) {
                    mItemClickListenter.onItemClickListenter(position);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return payment_list.size();
    }


    public OnItemClickListenter mItemClickListenter;


    public interface OnItemClickListenter {
        void onItemClickListenter(int position);

        void onItemClickListenter2(int position, View view);

    }

    public void setmOnItemClickListenter(OnItemClickListenter mOnItemClickListenter) {
        this.mItemClickListenter = mOnItemClickListenter;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mPaymentAmount;
        private TextView mLf;
        private TextView mLf1;
        private TextView mLf2;
        private TextView mUpgradeNumber;
        private Button mShengji;
        private TextView mLookOver;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.name);
            mPaymentAmount = itemView.findViewById(R.id.payment_amount);
            mLf = itemView.findViewById(R.id.lf);
            mLf1 = itemView.findViewById(R.id.lf1);
            mLf2 = itemView.findViewById(R.id.lf2);
            mUpgradeNumber = itemView.findViewById(R.id.upgrade_number);
            mShengji = itemView.findViewById(R.id.shengji);
            mLookOver = itemView.findViewById(R.id.look_over);
        }
    }
}
