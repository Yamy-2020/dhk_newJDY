package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class KuaiJieAdapter extends RecyclerView.Adapter {
    private Context context;
    private KuaiJieAdapter.OnKuaiJieInfo listener;
    private ArrayList<KuaiJieResponse.KuaiJieInfo> models = new ArrayList<>();
    private int selectPosition;
    private BackDialog backDialog;

    public KuaiJieAdapter(Context context, KuaiJieAdapter.OnKuaiJieInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kuai_jie, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof KuaiJieAdapter.ContentViewHolder) {
            KuaiJieAdapter.ContentViewHolder viewHolder = (KuaiJieAdapter.ContentViewHolder) holder;
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
            viewHolder.tvSk_feilv.setText(model.getRate());
            viewHolder.tvSk_shouxufei.setText(model.getFee());
            viewHolder.tvSk_danbi.setText(model.getSingle_limit());
            viewHolder.tvSk_danri.setText(model.getDay_limit());
            viewHolder.tvImg2.setVisibility(View.GONE);
            viewHolder.tv3.setText(model.getBankList().toString());
            if (position == selectPosition){
                viewHolder.tvImg2.setVisibility(View.VISIBLE);
            }else{
                viewHolder.tvImg2.setVisibility(View.GONE);
            }
            if(model.getIs_new().equals("1")){
                viewHolder.tvImg_new.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvImg_new.setVisibility(View.GONE);
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
        LinearLayout mListLayout;
        TextView tvSk_tongdao;
        TextView tvSk_feilv;
        TextView tvSk_shouxufei;
        TextView tvSk_danbi;
        TextView tvSk_danri;
        TextView tv2,tv3;
        ImageView tvImg2,tvImg1,tvImg_new;

        ContentViewHolder(final View view) {
            super(view);
            mListLayout = (LinearLayout)itemView.findViewById(R.id.DeviceList_Layout);
            tvSk_tongdao = (TextView) itemView.findViewById(R.id.tv1);
            tvSk_feilv = (TextView) itemView.findViewById(R.id.feilv);
            tvSk_shouxufei = (TextView) itemView.findViewById(R.id.shouxufei);
            tvSk_danbi = (TextView) itemView.findViewById(R.id.danbixiane);
            tvSk_danri = (TextView) itemView.findViewById(R.id.dangrixiane);
            tvImg1 = (ImageView) itemView.findViewById(R.id.tvImg1);
            tvImg2 = (ImageView) itemView.findViewById(R.id.tvImg2);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            tv3 = (TextView) itemView.findViewById(R.id.tv3);
            tvImg_new = (ImageView) itemView.findViewById(R.id.tvImg_new);
            tv3.setVisibility(View.GONE);
            tvImg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPosition = getAdapterPosition();
                    listener.kuaijieClick(models.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backDialog = new BackDialog("支持银行列表", tv3.getText().toString(), "确定", context,
                            R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            backDialog.dismiss();
                        }
                    });
                    backDialog.setCancelable(false);
                    backDialog.show();
                }
            });

        }
    }

    public interface OnKuaiJieInfo {

        void kuaijieClick(KuaiJieResponse.KuaiJieInfo kuaiJieInfo);
    }
}
