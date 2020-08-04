package com.kym.ui.activity.bpbro_home.bpbro_dc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.daichang.DaiChangBankCardListActivity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

public class Bpbro_DiaChang_DX_Activity extends BaseActivity implements View.OnClickListener {

    private Intent intent;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpbro_dc_dx);
        initHead();
        initView();
    }

    private void initHead() {
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView dae = (TextView) findViewById(R.id.dae);
        TextView xiaoe = (TextView) findViewById(R.id.xiaoe);
        dae.setText("贷偿金额为5万-20万");
        xiaoe.setText("贷偿金额为5千-5万");
        tvTitle.setText("贷偿");
    }

    private void initView() {
        LinearLayout sk1 = (LinearLayout) findViewById(R.id.sk1);
        LinearLayout sk2 = (LinearLayout) findViewById(R.id.sk2);
        sk1.setOnClickListener(this);
        sk2.setOnClickListener(this);
        TextView xh = (TextView) findViewById(R.id.xh);
        TextView dh = (TextView) findViewById(R.id.dh);
        xh.setText("小额贷偿(" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getKade_small() + ")");
        dh.setText("大额贷偿(" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getKade_big() + ")");
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
        }
    }

    /**
     * 获取用户绑定信用卡列表
     */
    private void getBindBankList(String type1) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type1);
        Connect.getInstance().post(this.getApplicationContext(), IService.DC_BANK_LIST, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                BankListResponse response = (BankListResponse) JsonUtils.parse((String) result, BankListResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<BankListResponse.BankInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        intent = new Intent(getApplicationContext(), DaiChangBankCardListActivity.class);
                        intent.putExtra("type", type1);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getApplicationContext(), NewAddCreditCardActivity.class);
                        startActivity(intent);
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", Bpbro_DiaChang_DX_Activity.this,
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
}
