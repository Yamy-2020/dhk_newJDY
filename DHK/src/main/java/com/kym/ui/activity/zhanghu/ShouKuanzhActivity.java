package com.kym.ui.activity.zhanghu;

import android.content.Intent;
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
 * 收款账户
 *
 * @author sun
 * @date 2019/12/3
 */

public class ShouKuanzhActivity extends BaseActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_sao_mazh);
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
                    int todayMoney = response.getData().getTodayreceiveMoney();
                    int yesterdayMoney = response.getData().getYesterdayreceiveMoney();
                    int total_money = response.getData().getTotalreceive_money();
                    try {
                        splitter_sum = AmountUtils.changeF2Y(Long.parseLong("" + total_money));
                        remain_sum = AmountUtils.changeF2Y(Long.parseLong("" + balance));
                        float parseFloat = Float.parseFloat(remain_sum);
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
        TextView textview1 = findViewById(R.id.textView1);
        textview1.setText("收款收益余额");
        ImageView imageV_fanhui = (ImageView) findViewById(R.id.head_img_left);
        imageV_fanhui.setVisibility(View.VISIBLE);
        imageV_fanhui.setOnClickListener(this);
        TextView imageV_fanhui1 = (TextView) findViewById(R.id.head_img_right);
        imageV_fanhui1.setText("收款明细");
        imageV_fanhui1.setVisibility(View.VISIBLE);
        imageV_fanhui1.setOnClickListener(this);
        TextView textV_title = (TextView) findViewById(R.id.head_text_title);
        textV_title.setVisibility(View.VISIBLE);
        textV_title.setText("收款账户");
        textV_yuer = (RiseNumberTextView) findViewById(R.id.textV_yuer);
        textV_ljfr = (TextView) findViewById(R.id.textV_ljfr);
        tv_yes = (TextView) findViewById(R.id.textV_yester_sp);
        tv_tod = (TextView) findViewById(R.id.textV_lj_fenrun_day);
        findViewById(R.id.lay_yue_tx).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fenruntixian, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_right:
                Intent intent = new Intent(this, NewDetailActivity.class);
                intent.putExtra("type", 6);
                startActivity(intent);
                break;
            case R.id.head_img_left:
                finish();
                break;
            case R.id.lay_yue_tx:
                Intent intent5 = new Intent(getApplicationContext(), ShouKuantxActivity.class);
                intent5.putExtra("yue", remain_sum);
                startActivity(intent5);
                break;
            default:
                break;
        }
    }
}
