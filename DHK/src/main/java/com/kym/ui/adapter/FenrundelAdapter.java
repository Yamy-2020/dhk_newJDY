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
import com.kym.ui.model.SplitterList;
import com.kym.ui.util.AmountUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FenrundelAdapter extends BaseAdapter {

    private Activity activity;
    private List<SplitterList.DataBean> mList;


    public FenrundelAdapter(Activity activity, List<SplitterList.DataBean> hotgoodsList) {
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
            convertView = activity.getLayoutInflater().inflate(R.layout.item_fenrundel, null);
            holder.textV_name_fr = convertView.findViewById(R.id.textV_name_fr);
            holder.textV_num_ph = convertView.findViewById(R.id.textV_num_ph);
            holder.textV_fr_time = convertView.findViewById(R.id.textV_fr_time);
            holder.textV_money_fr = convertView.findViewById(R.id.textV_money_fr);
            holder.textV_fr_type = convertView.findViewById(R.id.textV_fr_type);
            holder.textV_dengji_fr = convertView.findViewById(R.id.textV_dengji_fr);
            holder.imageView = convertView.findViewById(R.id.imageV_fr_dj);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final SplitterList.DataBean info;
        info = mList.get(position);
        String f_name = info.getF_name();
        CharSequence charSequence = f_name.subSequence(0, 1);
        String s = charSequence.toString();
        holder.textV_name_fr.setText(s+"**");
//        holder.textV_num_ph.setText(info.getF_mobile());
        try {
            holder.textV_money_fr.setText(AmountUtils.changeF2Y(Long.parseLong("" + info.getSplitter())) + "元");
        } catch (Exception e) {
            e.printStackTrace();
        }
            holder.textV_fr_type.setText(info.getTypename());

        holder.textV_dengji_fr.setText(info.getF_level_name());
        holder.textV_fr_time.setText(gettime(info.getAddtime()));
        Glide.with(activity).load(info.getHead_img()).placeholder(R.drawable.default_image)
                .error(R.drawable.default_image).dontAnimate().into(holder.imageView);
//        holder.textV_num_ph.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:" + info.getF_mobile()));
//                activity.startActivity(intent);
//            }
//        });
        return convertView;
    }

    class Holder {
        private ImageView imageView;
        TextView textV_name_fr, textV_num_ph, textV_fr_time, textV_money_fr, textV_fr_type, textV_dengji_fr;
    }

    // 把时间戳转为string类型
    public static String gettime(String string) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long cc_time = Long.valueOf(string);

        return simpleDateFormat.format(new Date(cc_time * 1000L));
    }

}