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
import com.kym.ui.model.UserMyMerchant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Sj_oneAdapter extends BaseAdapter {

    private Activity activity;
    private List<UserMyMerchant.DataBean.ListBean> mList;


    public Sj_oneAdapter(Activity activity, List<UserMyMerchant.DataBean.ListBean> hotgoodsList) {

        this.activity = activity;
        this.mList = hotgoodsList;

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
        //return 5;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = activity.getLayoutInflater().inflate(
                    R.layout.sjlist_one, null);
            holder.textV_mincheng = convertView
                    .findViewById(R.id.textV_mincheng);
            holder.textV_zhijie_num = convertView
                    .findViewById(R.id.textV_zhijie_num);
            holder.textV_jianjie_num = convertView
                    .findViewById(R.id.textV_jianjie_num);
            holder.imageView = convertView.findViewById(R.id.imageV_dj_xin);


            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        UserMyMerchant.DataBean.ListBean info = mList.get(position);
        int e = info.getNum1()+info.getNum2();
        holder.textV_mincheng.setText(info.getName() + "");
        holder.textV_zhijie_num.setText("累计" + e + " 人");
        holder.textV_jianjie_num.setText("间接  " + info.getNum2() + " 人");

        Glide.with(activity).load(info.getHead_img()).placeholder(R.drawable.image_home).error(R.drawable.image_home).dontAnimate()
                .into(holder.imageView);



        return convertView;
    }

    class Holder {
        private ImageView imageView, imageV_zhifutype;
        TextView textV_mincheng, textV_zhijie_num, textV_jianjie_num;

    }

    public static String gettime(String string) {
        String time_new;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd     HH:mm");
        long cc_time = Long.valueOf(string);
        time_new = simpleDateFormat.format(new Date(cc_time * 1000L));

        return time_new;
    }

}