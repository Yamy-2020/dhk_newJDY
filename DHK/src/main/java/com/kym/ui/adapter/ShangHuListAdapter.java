package com.kym.ui.adapter;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.DateUtils;
import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.model.MyChild;
import com.kym.ui.newutilutil.CircleImageView;

import java.util.List;

public class ShangHuListAdapter extends BaseAdapter {
    private String title;
    private String headImg;
    private Activity activity;
    private List<MyChild.DataBean> mList;

    public ShangHuListAdapter(Activity activity, List<MyChild.DataBean> hotgoodsList, String title, String headImg) {

        this.activity = activity;
        this.mList = hotgoodsList;
        this.title = title;
        this.headImg = headImg;

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

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = activity.getLayoutInflater().inflate(R.layout.item_shanghulist, null);
            holder.header = convertView.findViewById(R.id.imageV_fr_dj);
            holder.textv_sr_name = convertView.findViewById(R.id.textV_yg_num);
            holder.textv_yg_dj = convertView.findViewById(R.id.textv_yg_dj);
            holder.textv_sr_phone = convertView.findViewById(R.id.textV_yg_phone);
            holder.textV_yg_zt = convertView.findViewById(R.id.tv_fenrun_status);
            holder.textv_sr_time = convertView.findViewById(R.id.textV_yg_time);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final MyChild.DataBean info = mList.get(position);
        Glide.with(activity).load(headImg).into(holder.header);
        if (null == info.getName() || info.getName().length() == 0) {
            holder.textv_sr_name.setText("未实名");
        } else {
            String str = "";
            for (int i = 1; i < info.getName().length(); i++) {
                str = str + "*";
            }
            holder.textv_sr_name.setText(info.getName().substring(0, 1) + str);
        }
        String dateToString10 = DateUtils.getDateToString10(Long.parseLong(info.getAddtime()));
        holder.textv_sr_time.setText(dateToString10);
        String status = info.getStatus();
        String status_auth = info.getStatus_auth();
        if (!status.equals("1")) {
            holder.textV_yg_zt.setText("冻结");
        } else {
            switch (status_auth) {
                case "1":
                    holder.textV_yg_zt.setText("未完善");
                    break;
                case "2":
                    holder.textV_yg_zt.setText("审核中");
                    break;
                case "3":
                    holder.textV_yg_zt.setText("已通过");
                    break;
                case "4":
                    holder.textV_yg_zt.setText("未通过");
                    break;
                case "-1":
                    holder.textV_yg_zt.setText("冻结");
                    break;
            }
        }
        holder.textv_yg_dj.setText(title);
        holder.textv_sr_phone.setText(info.getMobile().substring(0, 3) +
                " **** " + info.getMobile().substring(7, 11));
        holder.textv_sr_phone.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.showTextToas(activity,"请开启拨打电话权限后重试");
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + info.getMobile()));
                        activity.startActivity(intent);
                    }

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }
}


class Holder {
    CircleImageView header;
    TextView textv_sr_name, textv_sr_phone,
            textv_sr_time, textv_yg_dj, textV_yg_zt;

}
