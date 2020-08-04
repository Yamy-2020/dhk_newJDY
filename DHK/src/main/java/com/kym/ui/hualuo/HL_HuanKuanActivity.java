package com.kym.ui.hualuo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.kym.ui.activity.bpbro_home.bpbro_hk.HK_BankList_Activity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;
import static com.zzss.jindy.appconfig.Clone.OMID;

public class HL_HuanKuanActivity extends BaseActivity implements View.OnClickListener {

    private Intent intent;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hl__huan_kuan);
        initHead();
        initView();
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView dae = findViewById(R.id.dae);
        TextView xiaoe = findViewById(R.id.xiaoe);
        dae.setText("支持单卡单笔1万以下,单日最高执行还款4笔");
        xiaoe.setText("单笔一千，单日四千，总额五万");

            tvTitle.setText("还款");
    }

    private void initView() {
        LinearLayout sk1 = findViewById(R.id.sk1);
        LinearLayout sk2 = findViewById(R.id.sk2);
        LinearLayout sk3 = findViewById(R.id.sk3);
        sk1.setOnClickListener(this);
        sk2.setOnClickListener(this);
        sk3.setOnClickListener(this);
        if (OMID.equals("1H1AJD6SLKVADDM6")) {
            sk3.setVisibility(View.VISIBLE);
            TextView youxiang = findViewById(R.id.youxiang);
            youxiang.setText("新小额还款(" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getYx_fee() + ")");
        }
        TextView xh = findViewById(R.id.xh);
        TextView dh = findViewById(R.id.dh);
        xh.setText("小额还款(" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getRep_small() + ")");
        dh.setText("大额还款(" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getRep_big() + ")");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.sk1:
                getBindBankList("X");
                break;
            case R.id.sk2:
                getBindBankList("D");
                break;
            case R.id.sk3:
                getBindBankList("Y");
                break;
        }
    }

    /**
     * 获取用户绑定信用卡列表
     */
    private void getBindBankList(String type1) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type1);
        Connect.getInstance().post(this.getApplicationContext(), IService.NEWXINYONGKA, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                BankListResponse response = (BankListResponse) JsonUtils.parse((String) result, BankListResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<BankListResponse.BankInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        intent = new Intent(getApplicationContext(), HK_BankList_Activity.class);
                        intent.putExtra("data", response);
                        intent.putExtra("type", type1);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getApplicationContext(), NewAddCreditCardActivity.class);
                        startActivity(intent);
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", HL_HuanKuanActivity.this,
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
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    public void tipView(String msg) {
        backDialog = new BackDialog("", msg, "确定", HL_HuanKuanActivity.this,
                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
            @Override
            public void onClick(View view) {
                backDialog.dismiss();
            }
        });
        backDialog.setCancelable(false);
        backDialog.show();
    }
}
