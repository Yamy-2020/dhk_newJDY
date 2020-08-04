package com.kym.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.model.UserUpGrade;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.log;
import com.zzss.jindy.appconfig.Clone;

import java.util.List;

import static com.zzss.jindy.appconfig.Clone.OMID;


public class LevelAdapter extends BaseAdapter {

    private Activity activity;
    private List<UserUpGrade.DataBean.LevelBean> mList;
    private UserUpGrade.DataBean data;

    public LevelAdapter(Activity activity, List<UserUpGrade.DataBean.LevelBean> mList, UserUpGrade.DataBean data) {
        activity.getWindowManager().getDefaultDisplay();
        this.activity = activity;
        this.mList = mList;
        this.data = data;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = activity.getLayoutInflater().inflate(R.layout.item_level, null);
            holder.lv_tv1 = convertView.findViewById(R.id.lv_tv1);
            holder.lv_tv2 = convertView.findViewById(R.id.lv_tv2);
            holder.lv_tv3 = convertView.findViewById(R.id.lv_tv3);
            holder.up = convertView.findViewById(R.id.up);
            holder.lv_head_img = convertView.findViewById(R.id.lv_head_img);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();

        }
        UserUpGrade.DataBean.LevelBean dataBean1 = mList.get(position);
        if (OMID.equals("IDC5EAD0TUM2QHK3") ||
                OMID.equals("ZD4CTNB7DSLXS9B8") ||
                OMID.equals("3STWH8VK0S27SW31") ||
                OMID.equals("ROWH57OIXPQ0R198")) {
            if (position == 0) {
                holder.up.setVisibility(View.GONE);
//                holder.lv_tv1.setText(dataBean1.getName() + "级别免费使用");
//                holder.lv_tv2.setText("");
//                holder.lv_tv2.setVisibility(View.GONE);
//                holder.lv_tv3.setText("无需推荐或者费用");
            } else if (position == 1) {
                holder.lv_tv1.setText("升级到" + dataBean1.getName());
                holder.lv_tv2.setText("推荐" + dataBean1.getNum() + "人免费升级");
            } else if (position == 2) {
                holder.lv_tv1.setText("升级到" + dataBean1.getName());
                holder.lv_tv2.setText("推荐" + dataBean1.getNum() + "人免费升级");
                log.e("getFee_clerk_manager:" + data.getFee_clerk_manager());
                log.e("getFee_clerk_boss:" + data.getFee_clerk_boss());
                log.e("getFee_manager_boss:" + data.getFee_manager_boss());
                try {
                    holder.lv_tv3.setText("付费" + AmountUtils.changeF2Y(data.getFee_clerk_boss()) + "元即刻升级");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (OMID.equals("VIB0T31Q2L7DNDK5")) {
            //花螺
            if (position == 0) {
                holder.lv_tv1.setText(dataBean1.getName());
                holder.lv_tv2.setText("个人交易累计交易额10万以下");
                holder.lv_tv3.setVisibility(View.GONE);
            } else if (position == 1) {
                holder.lv_tv1.setText(dataBean1.getName());
                holder.lv_tv2.setText("个人交易累计交易额10万以上");
                holder.lv_tv3.setVisibility(View.GONE);
            } else if (position == 2) {
                holder.lv_tv1.setText(dataBean1.getName());
                holder.lv_tv2.setText("个人交易累计交易额50万以上");
                try {
                    holder.lv_tv3.setText("付费" + AmountUtils.changeF2Y(data.getFee_clerk_boss()) + "元即刻升级");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (OMID.equals("1H1AJD6SLKVADDM6")) {
            holder.lv_tv1.setText("升级到" + dataBean1.getName());
            try {
                if (position == 0) {
                    holder.lv_tv2.setText("分享" + dataBean1.getNum() + "个有效用户免费升级");
                    holder.lv_tv3.setText("付费" + AmountUtils.changeF2Y(data.getFee_manager_boss()) + "元即刻升级");
                } else if (position == 1) {
                    holder.lv_tv2.setText("分享" + dataBean1.getNum() + "个VIP用户免费升级");
                    holder.lv_tv3.setText("付费" + AmountUtils.changeF2Y(data.getFee_clerk_boss()) + "元即刻升级");
                } else {
                    holder.lv_tv2.setText("分享" + dataBean1.getNum() + "个高级VIP用户免费升级");
                    holder.lv_tv3.setText("付费" + AmountUtils.changeF2Y(data.getFee_super_vip()) + "元即刻升级");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (position == 0) {
//                holder.lv_tv1.setText(dataBean1.getName() + "级别免费使用");
//                holder.lv_tv2.setText("");
//                holder.lv_tv2.setVisibility(View.GONE);
//                holder.lv_tv3.setText("无需推荐或者费用");
                holder.up.setVisibility(View.GONE);
            } else {
                holder.lv_tv1.setText("升级到" + dataBean1.getName());
                holder.lv_tv2.setText("推荐" + dataBean1.getNum() + "人免费升级");
                try {
                    holder.lv_tv3.setText("付费" + AmountUtils.changeF2Y(data.getFee_clerk_boss()) + "元即刻升级");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Glide.with(activity).load(dataBean1.getTop_imgurl()).dontAnimate().placeholder(R.drawable.default_image).into(holder.lv_head_img);
        return convertView;
    }

    class Holder {
        TextView lv_tv1;
        TextView lv_tv2;
        TextView lv_tv3;
        ImageView lv_head_img;
        LinearLayout up;

    }


}