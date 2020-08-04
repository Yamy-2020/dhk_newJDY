package com.kym.ui.activity.bpbro_home.bpbro_yk;

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
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.info.RcodeInfo;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;

/**
 * 养卡确认签约
 *
 * @author sun
 * @date 2019/12/3
 */

public class XF_QRsignActivity extends BaseActivity implements View.OnClickListener {
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
        tv.setText("养卡通道签约");
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
                } else if (getIntent().getStringExtra("ID").equals("1")) {
                    KFT_QR_sign();
                } else if (getIntent().getStringExtra("ID").equals("3")) {
                    JFT_QR_sign(IService.XF_JFT_QUEREN_SIGN);
                } else if (getIntent().getStringExtra("ID").equals("4")) {
                    JFT_QR_sign(IService.XF_JQJFT_QUEREN_SIGN);
                } else if (getIntent().getStringExtra("ID").equals("5")) {
                    JFT_QR_sign(IService.XF_JDJQJFT_QUEREN_SIGN);
                } else if (getIntent().getStringExtra("ID").equals("6")) {
                    SXY_QR_sign();
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
            tipView("1", getIntent().getStringExtra("tip") + ",请勿退出签约页面");
        }
    }

    /**
     * 快付通确认签约
     */
    private void KFT_QR_sign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("smsSeq", getIntent().getStringExtra("smsSeq"));
        params.put("orderid", getIntent().getStringExtra("orderid"));
        params.put("authCode", etVerifyCode.getText().toString());
        Connect.getInstance().post(getApplicationContext(), IService.XF_QUEREN_SIGN, params, new Connect.OnResponseListener() {
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

    /**
     * 首信易确认签约
     */
    private void SXY_QR_sign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", bankInfo.getCardid());
        params.put("orderid", getIntent().getStringExtra("orderid"));
        params.put("authCode", etVerifyCode.getText().toString());
        Connect.getInstance().post(getApplicationContext(), IService.SXY_YANGKA_QUERENQIANYUE, params, new Connect.OnResponseListener() {
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

    /**
     * 快付通确认签约
     */
    private void JFT_QR_sign(String api) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("smsSeq", getIntent().getStringExtra("smsSeq"));
        params.put("orderid", getIntent().getStringExtra("orderid"));
        params.put("authCode", etVerifyCode.getText().toString());
        params.put("uid", SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getUid());
        Connect.getInstance().post(getApplicationContext(), api, params, new Connect.OnResponseListener() {
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
        backDialog = new BackDialog("", msg, "确定", XF_QRsignActivity.this,
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
