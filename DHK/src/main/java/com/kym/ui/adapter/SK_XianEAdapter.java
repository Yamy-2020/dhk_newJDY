package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.info.KuaiJieResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunmiaolong on 2018/7/29.
 * .
 */

public class SK_XianEAdapter extends RecyclerView.Adapter {
    private Context context;
    private SK_XianEAdapter.OnKuaiJieInfo listener;
    private ArrayList<KuaiJieResponse.KuaiJieInfo> models = new ArrayList<>();
    private int selectPosition;
    private BackDialog backDialog;

    public SK_XianEAdapter(Context context, SK_XianEAdapter.OnKuaiJieInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sk_xiane, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SK_XianEAdapter.ContentViewHolder) {
            SK_XianEAdapter.ContentViewHolder viewHolder = (SK_XianEAdapter.ContentViewHolder) holder;
            KuaiJieResponse.KuaiJieInfo model = models.get(position);

            SpannableString spannableString;
            if (model.getIs_land().equals("1")){
                spannableString = new SpannableString(model.getName()+"(落地)");
            } else {
                spannableString = new SpannableString(model.getName()+"(线上)");
            }

            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.blue_2e));
            spannableString.setSpan(colorSpan, spannableString.length()-4, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            viewHolder.tvSk_tongdao.setText(spannableString);
            viewHolder.tvSk_danbi.setText("单笔限额(元)"+model.getSingle_limit());
            viewHolder.tvSk_danri.setText("单日限额(元)"+model.getDay_limit());
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

    public void setData(List<KuaiJieResponse.KuaiJieInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {

        TextView tvSk_tongdao;
        TextView tvSk_danbi;
        TextView tvSk_danri;

        ContentViewHolder(final View view) {
            super(view);
            tvSk_tongdao = (TextView) itemView.findViewById(R.id.tv1);
            tvSk_danbi = (TextView) itemView.findViewById(R.id.danbixiane);
            tvSk_danri = (TextView) itemView.findViewById(R.id.dangrixiane);

        }
    }

    public interface OnKuaiJieInfo {

        void kuaijieClick(KuaiJieResponse.KuaiJieInfo kuaiJieInfo);
    }
}
