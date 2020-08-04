package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.util.DIsplayUtils;

/**
 * Created by zachary on 2018/1/31.
 */

public class SelectDateAdapter extends RecyclerView.Adapter {
    private final int height;
    private Context context;
    private OnDateSelectListener listener;

    public SelectDateAdapter(Context context) {
        this.context = context;
        height = DIsplayUtils.dp2px(context, 40);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(context);
        textView.setTextColor(0xFF333333);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(0xFFFFFFFF);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        return new ContentViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentViewHolder viewHolder = (ContentViewHolder) holder;
        viewHolder.textView.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return 31;
    }

    public void setListener(OnDateSelectListener listener) {
        this.listener = listener;
    }

    public interface OnDateSelectListener {
        void dateSelect(int position);
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {

        private final TextView textView;

        ContentViewHolder(View view) {
            super(view);
            textView = (TextView) this.itemView;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.dateSelect(getAdapterPosition() + 1);
                    }
                }
            });
        }
    }
}