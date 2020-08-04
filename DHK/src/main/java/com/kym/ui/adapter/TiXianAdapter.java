package com.kym.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.model.CashList;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TiXianAdapter extends BaseAdapter {

    private Activity activity;
    private List<CashList.DataBean.CashListBean> mList;

    public TiXianAdapter(Activity activity, List<CashList.DataBean.CashListBean> hotgoodsList) {
        this.activity = activity;
        this.mList = hotgoodsList;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        log.e("****" + mList.size());
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = activity.getLayoutInflater().inflate(R.layout.fenrun_tixiandellist, null);
            holder.textV_tixian_type = convertView.findViewById(R.id.textV_tixian_type);
            holder.textV_tixian_bank = convertView.findViewById(R.id.textV_tixian_bank);
            holder.textV_tixian_time = convertView.findViewById(R.id.textV_tixian_time);
            holder.textV_money = convertView.findViewById(R.id.textV_money);
            holder.imageV_st = convertView.findViewById(R.id.imageV_sta);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        CashList.DataBean.CashListBean info = mList.get(position);
        switch (info.getStatus()) {
            case "3":
                //成功
                holder.imageV_st.setBackgroundResource(R.drawable.ic_wancheng);
                break;
            default:
                holder.imageV_st.setBackgroundResource(R.drawable.ic_jisuan);
                break;
        }
        if (info.getBank_name().equals("支付宝")) {
            holder.textV_tixian_bank.setText(info.getBank_name() + "（" + info.getBank_no() + "）");
        } else {
            String bank_no = info.getBank_no();

            if (bank_no.length() > 1) {
                int n = 4;
                String bank_no_new = bank_no.substring(bank_no.length() - n
                );
                holder.textV_tixian_bank.setText(info.getBank_name() + " (" + bank_no_new
                        + ")");
            } else {
                holder.textV_tixian_bank.setText(info.getBank_name());
            }
        }
        holder.textV_tixian_time.setText(gettime(info.getAddtime()));
        String money = info.getMoney();
        int i = Integer.parseInt(info.getMoney());
        long l = Long.parseLong(money);
        try {
            String s = AmountUtils.changeF2Y(Long.parseLong("" + i));
            holder.textV_money.setText("-" + s + "元");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }


    class Holder {
        private ImageView imageV_st;
        TextView textV_tixian_type, textV_tixian_bank, textV_tixian_time,
                textV_money;

    }

    // 把时间戳转为string类型
    public static String gettime(String string) {
        String time_new = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");
        long cc_time = Long.valueOf(string);
        time_new = simpleDateFormat.format(new Date(cc_time * 1000L));

        return time_new;
    }

}