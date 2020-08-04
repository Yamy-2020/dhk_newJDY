package com.kym.ui.activity.new_dc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.model.AccessTodebit;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;

public class New_DaiChang_huankuan_Activity extends BaseActivity implements View.OnClickListener {

    private TextView date, hk_cxk;
    private Dialog getCodeDialog;
    private EditText etCode;
    private Context mContext;
    private TextView tvGetCode, xf_text;
    private CountDownTimer timer;
    private String bank_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__dai_chang__huan_kuan);
        mContext = this;
        initView();
        initHead();
        getCXK();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("还款");
    }

    private void initView() {
        TextView btn = (TextView) findViewById(R.id.btn);
        TextView jk_money = (TextView) findViewById(R.id.jk_money);
        jk_money.setText("￥ " + getIntent().getStringExtra("money"));
        TextView yuelilv = (TextView) findViewById(R.id.yuelilv);
        yuelilv.setText(getIntent().getStringExtra("yuelilv"));
        TextView lixi = (TextView) findViewById(R.id.lixi);
        lixi.setText(getIntent().getStringExtra("lixi"));
        date = (TextView) findViewById(R.id.date);
        date.setText(getIntent().getStringExtra("date"));
        TextView benjin = (TextView) findViewById(R.id.benjin);
        benjin.setText(getIntent().getStringExtra("benjin"));
        hk_cxk = (TextView) findViewById(R.id.hk_cxk);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.btn:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.layout_get_xfcode, null);
                initGetCodeDialog(view1);
                showGetCodeDialog(view1);
                break;
        }
    }

    private void initGetCodeDialog(View view) {
        etCode = (EditText) view.findViewById(R.id.et_get_code);
        tvGetCode = (TextView) view.findViewById(R.id.tv_get_code);
        xf_text = (TextView) view.findViewById(R.id.xf_text);
        xf_text.setText("请使用手机尾号" + SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getMobile().substring(7, 11) + "获取验证码");
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_get_code_confirm);
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvGetCode.setEnabled(false);
                getCode();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etCode.getText().toString()) || etCode.getText().toString().length() < 4) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入有效的验证码");
                    return;
                }
                getCodeDialog.dismiss();
                JieKuan();
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getMobile());
        params.put("type", "11");
        Connect.getInstance().post(this, IService.AUTHCODE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (response.getResult().getCode() == 10000) {
                    startCountDown();
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                    getCodeDialog.dismiss();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
                getCodeDialog.dismiss();
            }
        });
    }

    // 开始倒计时
    private void startCountDown() {
        timer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long l) {
                tvGetCode.setText(String.format("重新发送(%d秒)", l / 1000));
            }

            @Override
            public void onFinish() {
                tvGetCode.setText("获取验证码");
                tvGetCode.setEnabled(true);
            }
        };
        timer.start();
    }

    private void showGetCodeDialog(View view) {
        getCodeDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        getCodeDialog.setContentView(view, lp);
        getCodeDialog.setCancelable(true);
        getCodeDialog.setCanceledOnTouchOutside(true);
        getCodeDialog.show();
    }

    /**
     * 获取用户的储蓄卡信息
     */
    private void getCXK() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.ACCESSTODEBIT, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                AccessTodebit response = (AccessTodebit) JsonUtils.parse((String) result, AccessTodebit.class);
                if (response.getResult().getCode() == 10000) {
                    AccessTodebit.DataBean bank = response.getData();
                    bank_no = bank.getBank_no();
                    String substring = bank_no.substring(bank_no.length() - 4, bank_no.length());
                    hk_cxk.setText(bank.getBank_name() + "储蓄卡" + substring);
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

    /**
     * 获取验证码
     */
    private void JieKuan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", getIntent().getStringExtra("id"));
        Connect.getInstance().post(this, IService.HUANKUAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (response.getResult().getCode() == 10000) {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                    finish();
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
