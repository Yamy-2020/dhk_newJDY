package com.kym.ui.activity.bpbro_home.bpbro_hk;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.info.RcodeInfo;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;

/**
 * 还款信用卡确认签约
 *
 * @author sun
 * @date 2019/12/3
 */

public class HK_QRsignActivity extends BaseActivity implements View.OnClickListener {
    private EditText etVerifyCode;
    private BankListResponse.BankInfo bankInfo;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_zai);
        initHead();
        initUI();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("信用卡确认签约");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                if (TextUtils.isEmpty(etVerifyCode.getText().toString())) {
                    tipView("1", "验证码不能为空");
                } else {
                    KFT_QR_sign();
                }
                break;
        }
    }

    private void initUI() {
        bankInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("bankData");
        etVerifyCode = (EditText) findViewById(R.id.et_new_add_verify_code);
        CircleImageView ivBankLogo = (CircleImageView) findViewById(R.id.iv_bank_sign);
        TextView tvBankName = (TextView) findViewById(R.id.tv_bank_sign_card_name);
        TextView tvBankNumber = (TextView) findViewById(R.id.tv_bank_sign_card_number);
        findViewById(R.id.change_card).setOnClickListener(this);
        Glide.with(this).load(bankInfo.getLogo_url()).placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(bankInfo.getBank_name());
        tvBankNumber.setText(bankInfo.getBank_no().substring(0, 4)
                + " **** **** " + bankInfo.getBank_no().substring(bankInfo.getBank_no().length() - 4, bankInfo.getBank_no().length()));
        if (getIntent().getStringExtra("tip") != null) {
            tipView("1", "验证码已发送,请勿退出页面");
        }
    }

    /**
     * 确认签约
     */
    private void KFT_QR_sign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        if (getIntent().getStringExtra("id").equals("30") || getIntent().getStringExtra("id").equals("31")) {
            params.put("cardid", bankInfo.getCardid());
            params.put("authCode", etVerifyCode.getText().toString());
            params.put("orderid", "568089787656565");
            params.put("id", getIntent().getStringExtra("id"));
        } else {
            if (getIntent().getStringExtra("id").equals("7")) {
                params.put("cardid", getIntent().getStringExtra("smsSeq"));
            } else if (getIntent().getStringExtra("id").equals("13") || getIntent().getStringExtra("id").equals("14") ||
                    getIntent().getStringExtra("id").equals("15") || getIntent().getStringExtra("id").equals("28") ||
                    getIntent().getStringExtra("id").equals("29")) {
                params.put("cardid", bankInfo.getCardid());
            } else {
                params.put("smsSeq", getIntent().getStringExtra("smsSeq"));
            }
            params.put("orderid", getIntent().getStringExtra("orderid"));
            params.put("authCode", etVerifyCode.getText().toString());
            params.put("id", getIntent().getStringExtra("id"));
        }

        Connect.getInstance().post(getApplicationContext(), IService.TONGYI_SIGN_QUEREN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                RcodeInfo response = (RcodeInfo) JsonUtils.parse((String) result, RcodeInfo.class);
                tipView("2", response.getResult().getMsg());
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", HK_QRsignActivity.this,
                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag.equals("1")) {
                    finish();
                }
                backDialog.dismiss();
            }
        });
        backDialog.setCancelable(false);
        backDialog.show();
    }
}
