package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.info.BankNameResponse;
import com.kym.ui.util.DIsplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zachary on 2018/1/31.
 */

public class SelectBankAdapter extends RecyclerView.Adapter {
    private int height;
    private Context context;
    private SelectBankListener listener;
    private ArrayList<BankNameResponse.BankNameInfo> models = new ArrayList<>();

    public SelectBankAdapter(Context context) {
        this.context = context;
        height = DIsplayUtils.dp2px(context, 40);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(context);
        textView.setTextColor(0xFF333333);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        return new ContentViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentViewHolder viewHolder = (ContentViewHolder) holder;
        BankNameResponse.BankNameInfo model = models.get(position);
        viewHolder.textView.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setListener(SelectBankListener listener) {
        this.listener = listener;
    }

    public void setData(List<BankNameResponse.BankNameInfo> banks) {
        models.clear();
        models.addAll(banks);
    }

    public interface SelectBankListener {
        void selectBank(BankNameResponse.BankNameInfo bankInfo);
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {

        private final TextView textView;

        public ContentViewHolder(View view) {
            super(view);
            textView = (TextView) this.itemView;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.selectBank(models.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
