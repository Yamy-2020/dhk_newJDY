package com.kym.ui.activity.zhanghu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.AccountBalance;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

/**
 * 分润账户
 *
 * @author sun
 * @date 2019/12/3
 */

public class ZhangHuActivity extends BaseActivity implements OnClickListener {

    private TextView tv2, fx_money, sj_money, total_zh_money1/*,hd_money*/;
    private TextView tv4;
    private TextView tv5;
    private DialogUtil loadDialogUti;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhang_hu);
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("分润账户");
        findViewById(R.id.zong).setOnClickListener(this);
        findViewById(R.id.l1).setOnClickListener(this);
        LinearLayout v12=findViewById(R.id.v12);
        v12.setOnClickListener(this);
        v12.setVisibility(View.GONE);
//        findViewById(R.id.v12).setOnClickListener(this);
        findViewById(R.id.l3).setOnClickListener(this);
        findViewById(R.id.l4).setOnClickListener(this);
        findViewById(R.id.l5).setOnClickListener(this);
        TextView sj_name = findViewById(R.id.sj_name);
/*        if (OMID.equals("1H1AJD6SLKVADDM6")) {
            findViewById(R.id.l4).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.l4).setVisibility(View.GONE);
        }

        if (OMID.equals("VIB0T31Q2L7DNDK5")) {
            findViewById(R.id.l4).setVisibility(View.VISIBLE);
//            findViewById(R.id.v12).setVisibility(View.GONE);
            findViewById(R.id.l5).setVisibility(View.GONE);
            sj_name.setText("办卡分润");
        } else {
//            findViewById(R.id.v12).setVisibility(View.VISIBLE);
            findViewById(R.id.l5).setVisibility(View.VISIBLE);
            sj_name.setText("升级账户");
        }*/
        tv2 = findViewById(R.id.textView2);
        tv4 = findViewById(R.id.textView4);
        tv5 = findViewById(R.id.textView5);
        fx_money = findViewById(R.id.fx_money);
        sj_money = findViewById(R.id.sj_money);
//        hd_money = findViewById(R.id.hd_money);
        total_zh_money1 = findViewById(R.id.total_zh_money);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getdata();
    }

    private void getdata() {
        loadDialogUti = new DialogUtil(this);
        Connect.getInstance().post(ZhangHuActivity.this, IService.GET_ACCOUNT_BALANCE, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                AccountBalance response = (AccountBalance) JsonUtils.parse((String) result, AccountBalance.class);
                if (response.getResult().getCode() == 10000) {
                    int total_money = response.getData().getTotal_money();
                    int totalback_money = response.getData().getTotalback_money();
                    int totalreceive_money = response.getData().getTotalreceive_money();
                    int totalkade_money = response.getData().getTotalkade_money();
                    int totalsj_money = response.getData().getTotalupgrade_money();
                    int totalhd_money = response.getData().getTotalactivity_money();
                    int balance = response.getData().getTotalback_money();
                    int total_zh_money = total_money + totalreceive_money + totalkade_money + totalsj_money + totalhd_money+balance;
                    try {
                        String s_total_money = AmountUtils.changeF2Y(Long.parseLong("" + total_money));
                        String s_totalback_money = AmountUtils.changeF2Y(Long.parseLong("" + totalback_money));
                        String s_totalreceive_money = AmountUtils.changeF2Y(Long.parseLong("" + totalreceive_money));
                        String s_totalkade_money = AmountUtils.changeF2Y(Long.parseLong("" + totalkade_money));
                        String s_totalsj_money = AmountUtils.changeF2Y(Long.parseLong("" + totalsj_money));
                        String s_totalhd_money = AmountUtils.changeF2Y(Long.parseLong("" + totalhd_money));
                        String s_total_zh_money = AmountUtils.changeF2Y(Long.parseLong("" + total_zh_money));
                        tv2.setText(s_total_money + "元");
                        tv4.setText(s_totalkade_money + "元");
                        tv5.setText(s_totalreceive_money + "元");
                        fx_money.setText(s_totalback_money + "元");
                        sj_money.setText(s_totalsj_money + "元");
//                        hd_money.setText(s_totalhd_money + "元");
                        total_zh_money1.setText(s_total_zh_money + "元");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", ZhangHuActivity.this,
                            R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            restartApp(getApplicationContext());
                            backDialog.dismiss();
                        }
                    });
                    backDialog.setCancelable(false);
                    backDialog.show();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
                loadDialogUti.dismiss();
            }

            @Override
            public void onFailure(String message) {
                loadDialogUti.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zong:
                startActivity(new Intent(this, TotalzhActivity.class));
                break;
            case R.id.l1:
                startActivity(new Intent(this, XinYongKaActivity.class));
                break;/*
            case R.id.v12:
                startActivity(new Intent(this, DaiChangzhActivity.class));
            break;*/
            case R.id.l3:
                startActivity(new Intent(this, ShouKuanzhActivity.class));
                break;
            case R.id.l4:
                startActivity(new Intent(this, FanXianzhActivity.class));
                break;
            case R.id.l5:
                startActivity(new Intent(this, ShengJiZhActivity.class));
                break;
            case R.id.head_img_left:
                finish();
                break;
            default:
                break;
        }

    }
}
