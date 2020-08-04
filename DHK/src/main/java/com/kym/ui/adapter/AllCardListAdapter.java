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
import com.kym.ui.info.AllCardListResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AllCardListAdapter extends RecyclerView.Adapter {

    private Context context;
    private AllCardListAdapter.OnCardList listener;
    private ArrayList<AllCardListResponse.AllCardListInfo> models = new ArrayList<>();

    public AllCardListAdapter(Context context, AllCardListAdapter.OnCardList listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_bank_list, parent, false);
        return new AllCardListAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AllCardListAdapter.ContentViewHolder) {
            AllCardListAdapter.ContentViewHolder viewHolder = (AllCardListAdapter.ContentViewHolder) holder;
            AllCardListResponse.AllCardListInfo model = models.get(position);
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
                }
            } else {
                if (day <= reapyDate) {
                    int num = reapyDate - day;
                    int month = calendar.get(Calendar.MONTH) + 1;
                    viewHolder.date_tip.setText(num + "天后到期");
                    viewHolder.date.setText("到期还款日：" + month + "." + reapyDate);
                } else if (day >= billDate) {
                    int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    int num = dayCount - day + reapyDate;
                    viewHolder.date_tip.setText(num + "天后到期");
                    if (calendar.get(Calendar.MONTH) == 11) {
                        viewHolder.date.setText("到期还款日：01." + reapyDate);
                    } else {
                        int month = calendar.get(Calendar.MONTH) + 2;
                        viewHolder.date.setText("到期还款日：" + month + "." + reapyDate);
                    }
                } else {
                    if (day < billDate) {
                        int month = calendar.get(Calendar.MONTH) + 1;
                        viewHolder.date_tip.setText(billDate - day + "天后出账");
                        viewHolder.date.setText("预计账单日：" + month + "." + billDate);
                    } else if (day > reapyDate) {
                        int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        int num = dayCount - day + billDate;
                        viewHolder.date_tip.setText(num + "天后出账");
                        if (calendar.get(Calendar.MONTH) == 11) {
                            viewHolder.date.setText("预计账单日：01." + billDate);
                        } else {
                            int month = calendar.get(Calendar.MONTH) + 2;
                            viewHolder.date.setText("预计账单日：" + month + "." + billDate);
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
        return models.size();
    }

    public void setData(List<AllCardListResponse.AllCardListInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnCardList {
        void kj_card_list(AllCardListResponse.AllCardListInfo allCardListInfo, String type);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private final TextView bank_name, date_tip, bank_no, date, money, submit, submit3;
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
            submit3 = itemView.findViewById(R.id.submit3);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kj_card_list(models.get(getAdapterPosition()), "0");
                }
            });
        }
    }
}
