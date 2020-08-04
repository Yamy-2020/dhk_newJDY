package com.kym.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.newutil.NewDetailDatum;
import com.kym.ui.util.AmountUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewDetailAdapter extends BaseAdapter {
    private Context context;
    private List<NewDetailDatum> data;
    private int request_type;

    public NewDetailAdapter(Context context, List<NewDetailDatum> data, int request_type) {
        this.context = context;
        this.data = data;
        this.request_type = request_type;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder vHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_newdetail, null);
            vHolder = new viewHolder();
            vHolder.tv1 = convertView.findViewById(R.id.textView1);
            vHolder.tv2 = convertView.findViewById(R.id.textView2);

            convertView.setTag(vHolder);
        } else {
            vHolder = (viewHolder) convertView.getTag();
        }
        String addtime = data.get(position).getAddtime();
        if (request_type == 4) {

//			String collectionMoey = data.get(position).getCollectionMoey();
            String collectionMoey = data.get(position).getTotalMoney();
            try {
                int parseInt2 = Integer.parseInt(collectionMoey);
                vHolder.tv2.setText("+¥ " + AmountUtils.changeF2Y((long) parseInt2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request_type == 5) {

            String collectionMoey = data.get(position).getTotal_cash_money();

            try {
                int parseInt2 = Integer.parseInt(collectionMoey);
                vHolder.tv2.setText("+¥ " + AmountUtils.changeF2Y((long) parseInt2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request_type == 6) {

            String collectionMoey = data.get(position).getTotal_receive_money();
            try {
                int parseInt2 = Integer.parseInt(collectionMoey);
                vHolder.tv2.setText("+¥ " + AmountUtils.changeF2Y((long) parseInt2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request_type == 7) {

            String collectionMoey = data.get(position).getTotal_kade_money();
            try {
                int parseInt2 = Integer.parseInt(collectionMoey);
                vHolder.tv2.setText("+¥ " + AmountUtils.changeF2Y((long) parseInt2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  else if (request_type == 8) {

            String collectionMoey = data.get(position).getTotal_update_money();
            try {
                int parseInt2 = Integer.parseInt(collectionMoey);
                vHolder.tv2.setText("+¥ " + AmountUtils.changeF2Y((long) parseInt2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request_type == 9) {

            String collectionMoey = data.get(position).getTotal_active_cash_money();
            try {
                int parseInt2 = Integer.parseInt(collectionMoey);
                vHolder.tv2.setText("+¥ " + AmountUtils.changeF2Y((long) parseInt2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        vHolder.tv1.setText(gettime(addtime));
        return convertView;
    }

    public static String gettime(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long cc_time = Long.valueOf(string);

        return simpleDateFormat.format(new Date(cc_time * 1000L));
    }

    class viewHolder {
        TextView tv1, tv2;
    }
}
