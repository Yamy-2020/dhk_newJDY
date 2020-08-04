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
import com.kym.ui.info.MoXieResponse;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoXieAdapter extends RecyclerView.Adapter {

    private Context context;
    private MoXieAdapter.OnMoxie listener;
    private ArrayList<MoXieResponse.moxieInfo> models = new ArrayList<>();

    public MoXieAdapter(Context context, MoXieAdapter.OnMoxie listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_moxie_list, parent, false);
        return new MoXieAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MoXieAdapter.ContentViewHolder) {
            MoXieAdapter.ContentViewHolder viewHolder = (MoXieAdapter.ContentViewHolder) holder;
            MoXieResponse.moxieInfo model = models.get(position);
            if (model.getCard_num().equals("0")) {
                viewHolder.bank_name.setText(model.getBank_name());
                viewHolder.name_on_card.setText(model.getName_on_card());
            } else {
                viewHolder.bank_name.setText(model.getBank_name() + "(" + model.getCard_num() + ")");
                viewHolder.name_on_card.setText(model.getName_on_card() + " | 额度" + model.getCredit_limit());
            }


            if (model.getBill_way().equals("email")) {
                viewHolder.zd_plan.setText("邮箱账单");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//格式
                try {
                    Date date = format.parse(model.getBill_date());//账单日
                    Date date1 = format.parse(model.getPayment_due_date());//还款日
                    Date curDate = new Date(System.currentTimeMillis());

                    if (curDate.getTime() > date.getTime() && curDate.getTime() <= date1.getTime()) {
                        viewHolder.zd_date.setText("本期还款日 " + model.getPayment_due_date());
                        viewHolder.zd_ok.setVisibility(View.INVISIBLE);
                        viewHolder.zd_money.setVisibility(View.VISIBLE);
                        viewHolder.zd_tip.setVisibility(View.VISIBLE);
                        viewHolder.zd_money.setText("￥" + model.getCurrent_bill_amt());
                        viewHolder.zd_tip.setText("账单金额");
                    } else {
                        //账单日
                        String[] strs = model.getBill_date().split("-");
                        int year = 0;
                        int month = 0;
                        for (int i = 1; i < 13; i++) {
                            if (Integer.parseInt(strs[1]) == i) {
                                if (i == 12) {
                                    month = 1;
                                    year = Integer.parseInt(strs[0]) + 1;
                                } else {
                                    month = i + 1;
                                    year = Integer.parseInt(strs[0]);
                                }
                            }
                        }
                        Date date3 = format.parse(year + "-" + month + "-" + strs[2]);//账单日
//                        long lt = date3.getTime() + 4 * 24 * 3600 * 1000;
//                        Date date4 = new Date(lt);
                        String res = format.format(date3);
                        viewHolder.zd_date.setText(res + "可查询账单");
                        viewHolder.zd_tip.setVisibility(View.INVISIBLE);
                        viewHolder.zd_money.setVisibility(View.INVISIBLE);
                        viewHolder.zd_ok.setVisibility(View.VISIBLE);
                        viewHolder.zd_ok.setText("建议网银导入");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else if (model.getBill_way().equals("bank")) {
                viewHolder.zd_plan.setText("网银账单");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//格式
                try {
                    Date date1 = format.parse(model.getPayment_due_date());//还款日
                    Date curDate = new Date(System.currentTimeMillis());
                    if (curDate.getTime() > date1.getTime()) {
                        viewHolder.zd_ok.setVisibility(View.INVISIBLE);
                        viewHolder.zd_money.setVisibility(View.INVISIBLE);
                        viewHolder.zd_tip.setVisibility(View.INVISIBLE);
                        viewHolder.zd_ok.setVisibility(View.VISIBLE);
                        viewHolder.zd_ok.setText("暂无消费账单");
                        viewHolder.zd_date.setVisibility(View.INVISIBLE);
                    } else {
                        if (model.getBill_type().equals("DONE")) {
                            if (Math.abs(Double.parseDouble(model.getCurrent_bill_paid_amt())) >= Double.parseDouble(model.getCurrent_bill_amt())) {
                                viewHolder.zd_tip.setVisibility(View.INVISIBLE);
                                viewHolder.zd_money.setVisibility(View.INVISIBLE);
                                viewHolder.zd_ok.setVisibility(View.VISIBLE);
                                viewHolder.zd_ok.setText("账单已还清");
                                String[] strs = model.getBill_date().split("-");
                                int year = 0;
                                int month = 0;
                                for (int i = 1; i < 13; i++) {
                                    if (Integer.parseInt(strs[1]) == i) {
                                        if (i == 12) {
                                            month = 1;
                                            year = Integer.parseInt(strs[0]) + 1;
                                        } else {
                                            month = i + 1;
                                            year = Integer.parseInt(strs[0]);
                                        }
                                    }
                                }
                                Date date3 = format.parse(year + "-" + month + "-" + strs[2]);//账单日
//                                long lt = date3.getTime() + 4 * 24 * 3600 * 1000;
//                                Date date4 = new Date(lt);
                                String res = format.format(date3);
                                viewHolder.zd_date.setText(res + "可查询账单");
                            } else {
                                viewHolder.zd_date.setText("本期还款日 " + model.getPayment_due_date());
                                viewHolder.zd_ok.setVisibility(View.INVISIBLE);
                                viewHolder.zd_money.setVisibility(View.VISIBLE);
                                viewHolder.zd_tip.setVisibility(View.VISIBLE);
                                viewHolder.zd_ok.setVisibility(View.INVISIBLE);
                                DecimalFormat df = new DecimalFormat("0.00");
                                double ll = Double.parseDouble(model.getCurrent_bill_amt()) +
                                        Double.parseDouble(model.getCurrent_bill_paid_amt());
                                viewHolder.zd_money.setText("￥" + df.format(ll));
                                viewHolder.zd_tip.setText("剩余应还");
                            }
                        } else if (model.getBill_type().equals("UNDONE")) {
                            viewHolder.zd_date.setVisibility(View.VISIBLE);
                            viewHolder.zd_money.setVisibility(View.INVISIBLE);
                            viewHolder.zd_tip.setVisibility(View.INVISIBLE);
                            viewHolder.zd_ok.setVisibility(View.VISIBLE);
                            viewHolder.zd_ok.setText("账单未生成");
                            viewHolder.zd_date.setText(model.getBill_date() + "可查询账单");
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Glide.with(context).load(model.getBg_img()).dontAnimate().into(viewHolder.bg_img);
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

    public void setData(List<MoXieResponse.moxieInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnMoxie {
        void moxie(MoXieResponse.moxieInfo moxie_info);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView bank_name, name_on_card, zd_money, zd_tip, zd_date, zd_ok, zd_plan;
        private ImageView bg_img;

        public ContentViewHolder(View view) {
            super(view);
            bank_name = (TextView) itemView.findViewById(R.id.bank_name);
            name_on_card = (TextView) itemView.findViewById(R.id.name_on_card);
            zd_money = (TextView) itemView.findViewById(R.id.zd_money);
            zd_tip = (TextView) itemView.findViewById(R.id.zd_tip);
            zd_date = (TextView) itemView.findViewById(R.id.zd_date);
            zd_ok = (TextView) itemView.findViewById(R.id.zd_ok);
            zd_plan = (TextView) itemView.findViewById(R.id.zd_plan);
            bg_img = (ImageView) itemView.findViewById(R.id.bg_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.moxie(models.get(getAdapterPosition()));
                }
            });
        }
    }
}
