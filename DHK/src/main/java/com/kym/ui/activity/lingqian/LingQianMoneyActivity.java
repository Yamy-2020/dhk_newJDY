package com.kym.ui.activity.lingqian;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.kym.ui.BackDialog3;
import com.kym.ui.PayResult;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LingQianMoneyActivity extends BaseActivity implements View.OnClickListener {

    private EditText money;
    private LinearLayout btn_queding;
    private Intent intent;
    private SPConfig spConfig;
    private static final int SDK_PAY_FLAG = 1;
    private BackDialog3 backDialog3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ling_qian_money);
        initView();
    }

    public void initView() {
        ImageView imageV_fanhui = (ImageView) findViewById(R.id.head_img_left);
        imageV_fanhui.setVisibility(View.VISIBLE);
        imageV_fanhui.setOnClickListener(this);
        TextView textV_title = (TextView) findViewById(R.id.head_text_title);
        textV_title.setVisibility(View.VISIBLE);
        textV_title.setText("充值金额");
        spConfig = SPConfig.getInstance(this);
        money = (EditText) findViewById(R.id.money);
        btn_queding = (LinearLayout) findViewById(R.id.btn_queding);
        btn_queding.setOnClickListener(this);
        money.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        money.setText(s);
                        money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    money.setText(s);
                    money.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        money.setText(s.subSequence(0, 1));
                        money.setSelection(1);
                        return;
                    }
                }

                if (s.toString().length() < 1) {
                    btn_queding.setBackgroundDrawable(getResources().getDrawable(R.drawable.submit_btn2));
                    btn_queding.setEnabled(false);
                } else {
                    btn_queding.setBackgroundDrawable(getResources().getDrawable(R.drawable.submit_btn));
                    btn_queding.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                LingQianMoneyActivity.this.finish();
                break;
            case R.id.btn_queding:
                if (money.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请先输入充值金额", Toast.LENGTH_SHORT).show();
                }else{
                    getQRcode();
                }
                break;
        }
    }

    private void getQRcode() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();

        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt", getIntent().getStringExtra("encrypt"));
        params.put("money", money.getText().toString());
        Connect.getInstance().post(this.getApplicationContext(), IService.WODE_LINGQIAN_CHONGZHI, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                Log.d("我的零钱充值", "" + result.toString());
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String data = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(data);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        final String orderInfo = obj2.get("data").toString();
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(LingQianMoneyActivity.this);
                                Map<String, String> result = alipay.payV2(orderInfo, true);
                                Log.d("支付返回", orderInfo);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();

                    } else {
                        Toast.makeText(LingQianMoneyActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String message) {
                Log.d("我的零钱充值", "" + message);
                dialogUtil.dismiss();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        backDialog3 = new BackDialog3("确定", "取消", "提示", "支付成功！",
                                LingQianMoneyActivity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {

                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.textView1:
                                        backDialog3.dismiss();
                                        finish();
                                        break;
                                    case R.id.textView2:
                                        backDialog3.dismiss();
                                        finish();
                                        break;
                                }

                            }
                        });
                        backDialog3.setCancelable(false);
                        backDialog3.show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        backDialog3 = new BackDialog3("确定", "取消", "提示", "支付失败！",
                                LingQianMoneyActivity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {

                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.textView1:
                                        backDialog3.dismiss();
                                        break;
                                    case R.id.textView2:
                                        backDialog3.dismiss();
                                        break;
                                }
                            }
                        });
                        backDialog3.setCancelable(false);
                        backDialog3.show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
}
