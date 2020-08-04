package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.AllQianYueListResponse;

import java.util.ArrayList;
import java.util.List;

public class AllQianYueListAdapter extends RecyclerView.Adapter {

    private Context context;
    private AllQianYueListAdapter.OnCardList listener;
    private ArrayList<AllQianYueListResponse.AllQianYueListInfo> models = new ArrayList<>();


    public AllQianYueListAdapter(Context context, AllQianYueListAdapter.OnCardList listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_qianyue, parent, false);
        return new AllQianYueListAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AllQianYueListAdapter.ContentViewHolder) {
            AllQianYueListAdapter.ContentViewHolder viewHolder = (AllQianYueListAdapter.ContentViewHolder) holder;
            AllQianYueListResponse.AllQianYueListInfo model = models.get(position);
            viewHolder.bank_name.setText(model.getBank_name() + "(" + model.getBank_no().substring(model.getBank_no().length() - 4) + ")");
            for (int i = 0;i<model.getChannellist().size();i++){
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);       //LayoutInflater inflater1=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LayoutInflater inflater3 = LayoutInflater.from(context);
                View view = inflater3.inflate(R.layout.item_list_qianyue, null);
                TextView text1 = view.findViewById(R.id.text1);
                text1.setText(model.getChannellist().get(i).getNickname()+"("+model.getChannellist().get(i).getType()+")");
                TextView text2 = view.findViewById(R.id.text2);
                if (model.getChannellist().get(i).getIs_sign().equals("0")) {
                    text2.setText("未签约");
                    text2.setTextColor(0xFF34A350);
                }/* else {
                    text2.setText("已签约");
                    text2.setTextColor(0xFFFF6666);
                }*/
                view.setLayoutParams(lp);
                viewHolder.li.addView(view);
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

    public void setData(List<AllQianYueListResponse.AllQianYueListInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnCardList {
        void kj_card_list(AllQianYueListResponse.AllQianYueListInfo allCardListInfo, String type);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView bank_name;
        private LinearLayout li;

        ContentViewHolder(View view) {
            super(view);
            bank_name = itemView.findViewById(R.id.bank_name);
            li = itemView.findViewById(R.id.li);
        }
    }
}
