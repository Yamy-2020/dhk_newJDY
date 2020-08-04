package com.kym.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.model.SplitterList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TradeNewsAdapter extends BaseAdapter {

    private Activity activity;
    private List<SplitterList.DataBean> mList;
    private Context context;


    public TradeNewsAdapter(Activity activity, List<SplitterList.DataBean> hotgoodsList) {

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
            convertView = activity.getLayoutInflater().inflate(R.layout.zhangdanlist_one, null);
            holder.textv_jy_money = convertView.findViewById(R.id.textV_message_fr);
            holder.textv_jy_time = convertView.findViewById(R.id.textV_time_fr);
            holder.textv_jy_type = convertView.findViewById(R.id.textV_type_t);
            holder.imageV_zhifutype = convertView.findViewById(R.id.imageV_type);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        SplitterList.DataBean info = mList.get(position);
        holder.textv_jy_type.setText(info.getTypename());
        Glide.with(activity).load(info.getHead_img()).dontAnimate().placeholder(R.drawable.ic_mg_fr).into(holder.imageV_zhifutype);
        if (info.getSplitter().equals("0") || info.getSplitter() == null) {
            holder.textv_jy_money.setText("无款入账");
        } else {
            String from_name = info.getF_name();
            String splitter = info.getSplitter();
            int i = Integer.parseInt(splitter);
            final long l = Long.parseLong(i + "");
            holder.textv_jy_money.setText(info.getText());
            String substring = from_name.substring(0, from_name.length() - 1);
            try {
//                holder.textv_jy_money.setText("恭喜您：" + substring + "*" + "为你贡献了" + AmountUtils.changeF2Y(Long.parseLong("" + info.getSplitter())) + " 元，请继续努力！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        holder.textv_jy_time.setText(gettime(info.getAddtime()));
        return convertView;
    }

    class Holder {
        private ImageView imageV_zhifutype;
        TextView textv_jy_money, textv_jy_time, textv_jy_type;

    }

    // 把时间戳转为string类型
    public static String gettime(String string) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd     HH:mm");
        long cc_time = Long.valueOf(string);
        return simpleDateFormat.format(new Date(cc_time * 1000L));

    }

}