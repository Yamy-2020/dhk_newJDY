package com.kym.ui.activity.zhanghu;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.AccountBalance;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.kym.ui.util.RiseNumberTextView;

/**
 * 总账户
 *
 * @author sun
 * @date 2019/12/3
 */

public class TotalzhActivity extends BaseActivity implements View.OnClickListener {

    private RiseNumberTextView textV_yuer;
    private TextView textV_ljfr;
    private String remain_sum;
    private TextView tv_yes;
    private TextView tv_tod;
    private DialogUtil loadDialogUti;
    private String splitter_sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_zh);
        initview();
    }

    private void getdata() {
        loadDialogUti = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.GET_ACCOUNT_BALANCE, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                AccountBalance response = (AccountBalance) JsonUtils.parse((String) result, AccountBalance.class);
                if (response.getResult().getCode() == 10000) {
                    int balance = response.getData().getTotalreceive_money();
                    int todayMoney = response.getData().getTodayreceiveMoney() + response.getData().getTodaykadeMoney() +
                            response.getData().getTodayupgradeMoney() + response.getData().getTodayMoney()+response.getData().getTodaybackMoney();

                    int yesterdayMoney = response.getData().getYesterdayreceiveMoney() + response.getData().getYesterdaykadeMoney() +
                            response.getData().getYesterdayMoney() + response.getData().getYesterdayupgradeMoney()+response.getData().getYesterdaybackMoney();

                    int total_money = response.getData().getTotalreceive_money() + response.getData().getTotal_money() +
                            response.getData().getTotalkade_money() + response.getData().getTotalupgrade_money()+response.getData().getTotalback_money();//getTodaybackMoney
                    try {
                        splitter_sum = AmountUtils.changeF2Y(Long.parseLong("" + total_money));
                        remain_sum = AmountUtils.changeF2Y(Long.parseLong("" + balance));

                        float parseFloat = Float.parseFloat(splitter_sum);
                        textV_yuer.withNumber(parseFloat);
                        textV_yuer.setDuration(2000);
                        textV_yuer.start();
                        textV_ljfr.setVisibility(View.INVISIBLE);
                        textV_ljfr.setText(splitter_sum);
                        tv_yes.setText(AmountUtils.changeF2Y(Long.parseLong("" + yesterdayMoney)));
                        tv_tod.setText(AmountUtils.changeF2Y(Long.parseLong("" + todayMoney)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
    protected void onResume() {
        super.onResume();
        getdata();
    }

    private void initview() {
        ImageView imageV_fanhui = (ImageView) findViewById(R.id.head_img_left);
        imageV_fanhui.setVisibility(View.VISIBLE);
        imageV_fanhui.setOnClickListener(this);
        TextView textV_title = (TextView) findViewById(R.id.head_text_title);
        textV_title.setVisibility(View.VISIBLE);
        textV_title.setText("总账户");
        textV_yuer = (RiseNumberTextView) findViewById(R.id.textV_yuer);
        textV_ljfr = (TextView) findViewById(R.id.textV_ljfr);
        tv_yes = (TextView) findViewById(R.id.textV_yester_sp);
        tv_tod = (TextView) findViewById(R.id.textV_lj_fenrun_day);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fenruntixian, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            default:
                break;
        }
    }
}
