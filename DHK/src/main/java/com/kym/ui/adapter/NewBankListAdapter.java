package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.info.BankListResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 还款信用卡列表
 *
 * @author sun
 * @date 2020/1/12
 */

public class NewBankListAdapter extends RecyclerView.Adapter {

    private final int TYPE_CONTENT = 0;

    private Context context;
    private OnBankCardClickListener listener;
    private ArrayList<BankListResponse.BankInfo> models = new ArrayList<>();
    private String type;

    public NewBankListAdapter(Context context, OnBankCardClickListener listener, String type) {
        this.context = context;
        this.listener = listener;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_CONTENT:
                view = LayoutInflater.from(context).inflate(R.layout.item_bank_list, parent, false);
                return new ContentViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder) {
            ContentViewHolder viewHolder = (ContentViewHolder) holder;
            BankListResponse.BankInfo model = models.get(position);
            viewHolder.bank_name.setText(model.getBank_name());
            viewHolder.bank_no.setText(model.getBank_no().substring(0, 4)
                    + " **** **** " + model.getBank_no().substring(model.getBank_no().length() - 4));

            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);//当天
            int billDate = model.getBills(); //账单日
            int reapyDate = model.getRepayment();//还款日
            if (reapyDate > billDate) {
                if (day >= billDate && day <= reapyDate) {
                    int month = calendar.get(Calendar.MONTH) + 1;
                    viewHolder.date_tip.setText(reapyDate - day + "天后到期");
                    viewHolder.date.setText("到期还款日：" + month + "." + reapyDate);
                    if (type.equals("HK")) {
                        viewHolder.submit.setText("立即还款");
                        viewHolder.submit.setBackgroundResource(R.drawable.bank_list_focus);
                        viewHolder.submit.setEnabled(true);
                    } else if (type.equals("XF")) {
                        viewHolder.submit.setText("立即养卡");
                        viewHolder.submit.setBackgroundResource(R.drawable.bank_list_focus);
                    }
                } else {
                    if (day < billDate) {
                        int month = calendar.get(Calendar.MONTH) + 1;
                        viewHolder.date.setText("预计账单日：" + month + "." + billDate);
                        viewHolder.date_tip.setText(billDate - day + "天后出账");
                    } else {
                        if (calendar.get(Calendar.MONTH) == 11) {
                            viewHolder.date.setText("预计账单日：01." + billDate);
                        } else {
                            int month = calendar.get(Calendar.MONTH) + 2;
                            viewHolder.date.setText("预计账单日：" + month + "." + billDate);
                        }
                        int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        int num = dayCount - day + billDate;
                        viewHolder.date_tip.setText(num + "天后出账");
                    }
                    if (type.equals("HK")) {
                        viewHolder.submit.setText("立即还款");
                        viewHolder.submit.setBackgroundResource(R.drawable.bank_list_blur);
                        viewHolder.submit.setEnabled(false);
                    } else if (type.equals("XF")) {
                        viewHolder.submit.setText("立即养卡");
                        viewHolder.submit.setBackgroundResource(R.drawable.bank_list_focus);
                    }
                }
            } else {
                if (day <= reapyDate) {
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int num = reapyDate - day;
                    viewHolder.date_tip.setText(num + "天后到期");
                    viewHolder.date.setText("到期还款日：" + month + "." + reapyDate);
                    if (type.equals("HK")) {
                        viewHolder.submit.setText("立即还款");
                        viewHolder.submit.setBackgroundResource(R.drawable.bank_list_focus);
                        viewHolder.submit.setEnabled(true);
                    } else if (type.equals("XF")) {
                        viewHolder.submit.setText("立即养卡");
                        viewHolder.submit.setBackgroundResource(R.drawable.bank_list_focus);
                    }
                } else if (day >= billDate) {
                    int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    int num = dayCount - day + reapyDate;
                    int month = calendar.get(Calendar.MONTH) + 2;
                    viewHolder.date_tip.setText(num + "天后到期");
                    if (calendar.get(Calendar.MONTH) == 11) {
                        viewHolder.date.setText("到期还款日：01." + reapyDate);
                    } else {
                        viewHolder.date.setText("到期还款日：" + month + "." + reapyDate);
                    }
                    if (type.equals("HK")) {
                        viewHolder.submit.setText("立即还款");
                        viewHolder.submit.setBackgroundResource(R.drawable.bank_list_focus);
                        viewHolder.submit.setEnabled(true);
                    } else if (type.equals("XF")) {
                        viewHolder.submit.setText("立即养卡");
                        viewHolder.submit.setBackgroundResource(R.drawable.bank_list_focus);
                    }
                } else {
                    if (day < billDate) {
                        int month = calendar.get(Calendar.MONTH) + 1;
                        viewHolder.date_tip.setText(billDate - day + "天后出账");
                        viewHolder.date.setText("预计账单日：" + month + "." + billDate);
                        if (type.equals("HK")) {
                            viewHolder.submit.setText("立即还款");
                            viewHolder.submit.setBackgroundResource(R.drawable.bank_list_blur);
                            viewHolder.submit.setEnabled(false);
                        } else if (type.equals("XF")) {
                            viewHolder.submit.setText("立即养卡");
                            viewHolder.submit.setBackgroundResource(R.drawable.bank_list_focus);
                        }
                    } else if (day > reapyDate) {
                        int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        int num = dayCount - day + billDate;
                        int month = calendar.get(Calendar.MONTH) + 2;
                        viewHolder.date_tip.setText(num + "天后出账");
                        if (calendar.get(Calendar.MONTH) == 11) {
                            viewHolder.date.setText("预计账单日：01." + billDate);
                        } else {
                            viewHolder.date.setText("预计账单日：" + month + "." + billDate);
                        }
                        if (type.equals("HK")) {
                            viewHolder.submit.setText("立即还款");
                            viewHolder.submit.setBackgroundResource(R.drawable.bank_list_blur);
                            viewHolder.submit.setEnabled(false);
                        } else if (type.equals("XF")) {
                            viewHolder.submit.setText("立即养卡");
                            viewHolder.submit.setBackgroundResource(R.drawable.bank_list_focus);
                        }
                    }
                }
            }
            Glide.with(context).load(model.getLogo_url()).dontAnimate().into(viewHolder.bank_logo);
            if (model.getBalance().equals("0")) {
                viewHolder.money.setText("￥" + model.getBalance());
            } else {
                viewHolder.money.setText("￥" + model.getBalance());
            }
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_CONTENT;
    }

    public void setData(List<BankListResponse.BankInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {

        private final TextView bank_name, date_tip, bank_no, date, money, submit;
        private final ImageView bank_logo;

        ContentViewHolder(View view) {
            super(view);
            bank_name = itemView.findViewById(R.id.bank_name);
            date_tip = itemView.findViewById(R.id.date_tip);
            bank_no = itemView.findViewById(R.id.bank_no);
            date = itemView.findViewById(R.id.date);
            money = itemView.findViewById(R.id.money);
            bank_logo = itemView.findViewById(R.id.bank_logo);
            submit = itemView.findViewById(R.id.submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.cardClick(models.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnBankCardClickListener {
        void cardClick(BankListResponse.BankInfo bankInfo);
    }
}
