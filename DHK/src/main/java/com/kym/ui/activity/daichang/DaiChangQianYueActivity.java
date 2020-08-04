package com.kym.ui.activity.daichang;

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
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 信用卡签约-输入短信验证码界面
 */
public class DaiChangQianYueActivity extends BaseActivity implements View.OnClickListener {

    private EditText etVerifyCode;
    private BankListResponse.BankInfo bankInfo;
    private BackDialog backDialog;
    private String smsSeq, tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_chang_qian_yue);
        bankInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("data");
        initHead();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMsgCode();
    }

    private void initHead() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.head_text_title);
        title.setText("信用卡签约");
    }

    private void initView() {
        etVerifyCode = (EditText) findViewById(R.id.et_new_add_verify_code);
        CircleImageView ivBankLogo = (CircleImageView) findViewById(R.id.iv_bank_sign);
        TextView tvBankName = (TextView) findViewById(R.id.tv_bank_sign_card_name);
        TextView tvBankNumber = (TextView) findViewById(R.id.tv_bank_sign_card_number);
        findViewById(R.id.change_card).setOnClickListener(this);
        Glide.with(this).load(bankInfo.getLogo_url()).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(bankInfo.getBank_name());
        tvBankNumber.setText(bankInfo.getBank_no().substring(0, 4)
                + " **** **** " + bankInfo.getBank_no().substring(bankInfo.getBank_no().length() - 4, bankInfo.getBank_no().length()));
    }

    private boolean canConfirm() {
        if (TextUtils.isEmpty(etVerifyCode.getText().toString())) {
            tipView("1", "验证码不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                if (canConfirm()) {
                   getQueRen();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取签约验证码
     */
    private void getMsgCode() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", getIntent().getStringExtra("id"));
        params.put("cardid", bankInfo.getCardid());
        Connect.getInstance().post(getApplicationContext(), IService.ALL_SIGN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data);
                        tips = obj2.getString("tips");
                        smsSeq = obj2.getString("smsSeq");
                        tipView("1",tips);
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(),msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 获取空卡代签约
     */
    private void getQueRen() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("smsSeq", smsSeq);
        params.put("authCode", etVerifyCode.getText().toString());
        params.put("cardid",bankInfo.getCardid());
        params.put("id",getIntent().getStringExtra("id"));
        Connect.getInstance().post(getApplicationContext(), IService.ALL_QUEREN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if(code.equals("10000")){
                        tipView("2", msg);
                    } else {
                        tipView("1", msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(),message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", DaiChangQianYueActivity.this,
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
