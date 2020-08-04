package com.kym.ui.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.model.SplitterTotalTime;
import com.kym.ui.util.AmountUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FenRunAdapter extends BaseAdapter {

    private Activity activity;
    private List<SplitterTotalTime.DataBean> mList;


    public FenRunAdapter(Activity activity, List<SplitterTotalTime.DataBean> dataList) {
        this.activity = activity;
        this.mList = dataList;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
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
            convertView = activity.getLayoutInflater().inflate(
                    R.layout.fenruntypelist_three, null);

            holder.textV_name = convertView
                    .findViewById(R.id.textV_type_name);
            holder.textV_money = convertView
                    .findViewById(R.id.textV_type_money);


            holder.imageV_more = convertView
                    .findViewById(R.id.imageV_type);


            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        SplitterTotalTime.DataBean info = mList.get(position);

        holder.textV_name.setText(info.getName());
        try {
            holder.textV_money.setText(AmountUtils.changeF2Y(Long.parseLong("" + info.getSplitter())) + "元");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(activity).load(info.getImg()).placeholder(R.drawable.default_image).error(R.drawable.default_image)
                .dontAnimate().into(holder.imageV_more);


        return convertView;
    }


    class Holder {
        private ImageView imageV_more;

        TextView textV_name, textV_money;

    }

    // 把时间戳转为string类型
    public static String gettime(String string) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");

        return simpleDateFormat.format(new Date(Long.valueOf(string) * 1000L));
    }

    public static FenRunAdapter getAdapter() {
        // TODO Auto-generated method stub
        return null;
    }

}