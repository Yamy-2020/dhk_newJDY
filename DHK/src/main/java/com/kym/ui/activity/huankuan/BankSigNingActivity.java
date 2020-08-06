package com.kym.ui.activity.huankuan;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;

/**
 * 代还信用卡签约-验证码
 * Created by Sunmiaolong on 2018/7/29.
 * .
 */

public class  BankSigNingActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvGetCode;
    private EditText etVerifyCode;
    private String bank_name,bank_logo,bank_no,cardid;
    private BackDialog backDialog;

    private BankListResponse.BankInfo bankInfo;
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            long second = millisUntilFinished / 1000;
            tvGetCode.setText(String.format("重新获取(%d秒)", second));
        }

        @Override
        public void onFinish() {
            tvGetCode.setEnabled(true);
            tvGetCode.setText("获取验证码");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_sig_ning);
        if (getIntent().getStringExtra("turn_type").equals("banks")){
            bankInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("data");
            bank_name = bankInfo.getBank_name();
            bank_logo = bankInfo.getLogo_url();
            bank_no = bankInfo.getBank_no();
            cardid = bankInfo.getCardid();
        } else {
            bank_name = getIntent().getStringExtra("NBankName");
            bank_logo = getIntent().getStringExtra("NLogoUrl");
            bank_no = getIntent().getStringExtra("NBankNo");
            cardid = getIntent().getStringExtra("NCardId");
        }
        initHead();
        initUI();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                if (canConfirm()) {
                    confirm();
                }
                break;
            case R.id.tv_new_add_verify_code:
                getVerifyCode();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }

    private void initHead() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.head_text_title);
        title.setText("信用卡签约");
    }

    private void initUI() {
        tvGetCode = (TextView) findViewById(R.id.tv_new_add_verify_code);//验证码
        etVerifyCode = (EditText) findViewById(R.id.et_new_add_verify_code);
        CircleImageView ivBankLogo = (CircleImageView) findViewById(R.id.iv_bank_sign);
        TextView tvBankName = (TextView) findViewById(R.id.tv_bank_sign_card_name);
        TextView tvBankNumber = (TextView) findViewById(R.id.tv_bank_sign_card_number);
        findViewById(R.id.change_card).setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        Glide.with(this).load(bank_logo).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(bank_name);
        tvBankNumber.setText(bank_no);
    }

    private boolean canConfirm() {
        if (TextUtils.isEmpty(etVerifyCode.getText().toString())) {
            tipView("1","验证码不能为空");
            return false;
        }
        if (etVerifyCode.getText().toString().length() < 6) {
            tipView("1","验证码位数不正确");
            return false;
        }
        return true;
    }

    /**
     * 提交签约信息
     */
    private void confirm() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", cardid);
        params.put("auth_code", etVerifyCode.getText().toString());
        Connect.getInstance().post(this, IService.SIGN_CREDIT_CARD, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old info = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (info.getResult().getCode() == 10000) {
                    setResult(RESULT_OK);
                    tipView("2","签约成功");
                } else {
                    tipView("1",info.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1",message);
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        tvGetCode.setEnabled(false);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", cardid);
        Connect.getInstance().post(getApplicationContext(), IService.GET_SIGN_CODE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                YanzhengInfo_old info = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (info.getResult().getCode() == 10000) {
                    timer.start();
                    tipView("1","验证码已发送");
                    Toast.makeText(BankSigNingActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                } else {
                    tvGetCode.setEnabled(true);
                    tipView("1",info.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                tvGetCode.setEnabled(true);
                tipView("1",message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        if (backDialog == null) {
            backDialog = new BackDialog("", msg, "确定", BankSigNingActivity.this,
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
}
