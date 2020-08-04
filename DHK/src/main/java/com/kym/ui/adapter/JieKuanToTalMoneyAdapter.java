package com.kym.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.new_dc.New_DaiChang_huankuan_Activity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.JieKuanTotalMoneyResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

public class JieKuanToTalMoneyAdapter extends BaseAdapter {

    private Activity activity;
    private List<JieKuanTotalMoneyResponse.JieKuanTotalMoneyInfo> mList;

    public JieKuanToTalMoneyAdapter(Activity activity, List<JieKuanTotalMoneyResponse.JieKuanTotalMoneyInfo> mList) {
        this.activity = activity;
        this.mList = mList;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final JieKuanToTalMoneyAdapter.Holder holder;
        if (convertView == null) {
            holder = new JieKuanToTalMoneyAdapter.Holder();
            convertView = activity.getLayoutInflater().inflate(R.layout.item_jiekuan_totalmoney, null);
            holder.jk_money = convertView.findViewById(R.id.jk_money);
            holder.jk_date = convertView.findViewById(R.id.jk_date);
            holder.hk_money = convertView.findViewById(R.id.hk_money);
            holder.hk_time = convertView.findViewById(R.id.hk_time);
            holder.jk_time = convertView.findViewById(R.id.jk_time);
            holder.yh_lx = convertView.findViewById(R.id.yh_lx);
            holder.yh_bj = convertView.findViewById(R.id.yh_bj);
            holder.lilv = convertView.findViewById(R.id.lilv);
            holder.dc_tip = convertView.findViewById(R.id.dc_tip);
            holder.dc_img = convertView.findViewById(R.id.dc_img);
            holder.zd_plan = convertView.findViewById(R.id.zd_plan);
            holder.zd_plan.setVisibility(View.GONE);
            convertView.setTag(holder);
        } else {
            holder = (JieKuanToTalMoneyAdapter.Holder) convertView.getTag();
        }

        final JieKuanTotalMoneyResponse.JieKuanTotalMoneyInfo info = mList.get(position);
        holder.jk_money.setText(Integer.parseInt(info.getJk_money()) / 100 + "元");
        holder.hk_money.setText(info.getHk_money() + "元");
        if (info.getJk_date().equals("1")) {
            holder.jk_date.setText("30天");
        } else if (info.getJk_date().equals("3")) {
            holder.jk_date.setText("90天");
        } else if (info.getJk_date().equals("6")) {
            holder.jk_date.setText("180天");
        } else if (info.getJk_date().equals("12")) {
            holder.jk_date.setText("360天");
        }
        holder.jk_time.setText("借款时间：" + info.getJk_time());
        holder.hk_time.setText("还款时间：" + info.getHk_time());
        holder.lilv.setText("月利率：" + Float.parseFloat(info.getRixi()) * 100 * 30 + "%");
        Float lixi = Float.parseFloat(info.getHk_money()) - Float.parseFloat(info.getJk_money()) / 100;
        holder.yh_lx.setText(lixi + "元");
        holder.yh_bj.setText(Integer.parseInt(info.getJk_money()) / 100 + "元");
        if (info.getStatus().equals("1")) {
            holder.dc_tip.setText("审核中");
            holder.zd_plan.setText("取消借款");
            holder.zd_plan.setVisibility(View.VISIBLE);
            holder.dc_tip.setTextColor(convertView.getResources().getColor(R.color.share));
            holder.dc_img.setImageResource(R.drawable.daiyue);
        } else if (info.getStatus().equals("2")) {
            holder.dc_tip.setText("被拒绝");
            holder.dc_tip.setTextColor(convertView.getResources().getColor(R.color.delete));
            holder.dc_img.setImageResource(R.drawable.weiyue);
        } else if (info.getStatus().equals("3")) {
            holder.dc_tip.setText("使用中");
            holder.dc_tip.setTextColor(convertView.getResources().getColor(R.color.blue_public));
            holder.dc_img.setImageResource(R.drawable.shouyue);
            holder.zd_plan.setText("立即还款");
            holder.zd_plan.setVisibility(View.VISIBLE);
        } else if (info.getStatus().equals("5")) {
            holder.dc_tip.setText("已逾期");
            holder.zd_plan.setText("已还款");
            holder.zd_plan.setVisibility(View.VISIBLE);
            holder.dc_tip.setTextColor(convertView.getResources().getColor(R.color.delete));
            holder.dc_img.setImageResource(R.drawable.weiyue);
        } else if (info.getStatus().equals("6")) {
            holder.dc_tip.setText("已逾期未还款");
            holder.zd_plan.setText("立即还款");
            holder.zd_plan.setVisibility(View.VISIBLE);
            holder.dc_tip.setTextColor(convertView.getResources().getColor(R.color.delete));
            holder.dc_img.setImageResource(R.drawable.weiyue);
        } else if (info.getStatus().equals("4")) {
            holder.dc_tip.setText("已还款");
            holder.dc_tip.setTextColor(convertView.getResources().getColor(R.color.blue_public));
            holder.dc_img.setImageResource(R.drawable.shouyue);
            if (info.getIs_overdue().equals("1")) {
                holder.zd_plan.setText("逾期还款");
                holder.zd_plan.setVisibility(View.VISIBLE);
            } else {
                holder.zd_plan.setText("正常还款");
                holder.zd_plan.setVisibility(View.VISIBLE);
            }
        } else if (info.getStatus().equals("7")) {
            holder.dc_tip.setText("已取消");
            holder.dc_tip.setTextColor(convertView.getResources().getColor(R.color.delete));
            holder.dc_img.setImageResource(R.drawable.weiyue);
        }

        holder.zd_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.zd_plan.getText().toString().equals("取消借款")) {
                    stopJieKuan(info.getId());
                    notifyDataSetChanged();
                } else if (holder.zd_plan.getText().toString().equals("立即还款")) {
                    Intent intent = new Intent(activity, New_DaiChang_huankuan_Activity.class);
                    intent.putExtra("id", info.getId());
                    intent.putExtra("money", info.getHk_money());
                    intent.putExtra("date", holder.jk_date.getText());
                    intent.putExtra("yuelilv", Float.parseFloat(info.getRixi()) * 100 * 30 + "%");
                    intent.putExtra("benjin", holder.yh_bj.getText());
                    intent.putExtra("lixi", holder.yh_lx.getText());
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        });
        return convertView;
    }

    class Holder {
        private TextView jk_money, hk_money, jk_date, hk_time, jk_time, zd_plan, lilv, yh_lx, yh_bj, dc_tip;
        private ImageView dc_img;
    }

    /**
     * 获取所有空卡代还信用卡
     */
    private void stopJieKuan(String id) {
        final DialogUtil dialogUtil = new DialogUtil(activity);
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("id", id);
        Connect.getInstance().post(activity, IService.STOPJIEKUAN, paramx, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                JieKuanTotalMoneyResponse response = (JieKuanTotalMoneyResponse) JsonUtils.parse((String) result, JieKuanTotalMoneyResponse.class);
                dialogUtil.dismiss();
                if (response.getResult().getCode() == 10000) {
                    ToastUtil.showTextToas(activity, response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(activity, message);
            }
        });
    }
}
