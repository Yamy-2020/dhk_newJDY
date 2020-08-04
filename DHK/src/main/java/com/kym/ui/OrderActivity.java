package com.kym.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.LoginInfo;
import com.kym.ui.info.RegisterInfo;
import com.kym.ui.info.RequestParam;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 升级付费
 * Created by sunmiaolong on 2019/6/20.
 */

public class OrderActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout btn_order;
    private static final int SDK_PAY_FLAG = 1;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initHead();
        initView();
    }

    private void initHead() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.head_text_title);
        title.setText("付费升级");
    }

    public void initView() {
        btn_order = (LinearLayout) findViewById(R.id.btn_order);
        btn_order.setOnClickListener(this);
        TextView money = (TextView) findViewById(R.id.money);
        int mon = Integer.parseInt(getIntent().getStringExtra("money")) / 100;
        money.setText(mon + "元");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.btn_order:
                zhifubao(IService.UPGRADEORDER);
                break;
        }
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
                        login();
                        backDialog = new BackDialog("", "支付成功", "确定", OrderActivity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(OrderActivity.this, UpGradeActivity.class));
                                finish();
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        tipView("1", "支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private void zhifubao(String url) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
        Connect.getInstance().post(this.getApplicationContext(), url, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
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
                                PayTask alipay = new PayTask(OrderActivity.this);
                                Map<String, String> result = alipay.payV2(orderInfo, true);
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
                        tipView("1", msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1", message);
            }
        });
    }

    private void login() {
        SPConfig spConfig = SPConfig.getInstance(getApplicationContext());
        HashMap<String, String> accountInfo = spConfig.getAccountInfo();
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setMobile(accountInfo.get(Constant.USERNAME));
        registerInfo.setPassword(accountInfo.get(Constant.PASSWORD));
        registerInfo.setCode("");
        registerInfo.setAccessToken(LoginActivity.accessToken_xin);
        registerInfo.setIs_openposition(String.valueOf(2));
        registerInfo.setLatitude("");
        registerInfo.setLongitude("");
        RequestParam param = new RequestParam(IService.LOGIN, registerInfo, this, Constant.LOGIN);
        Connect.getInstance().httpUtil(param, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                LoginInfo loginData = (LoginInfo) result;
                int code = loginData.getResult().getCode();
                if (code == 10000) {
                    SPConfig spConfig = SPConfig.getInstance(getApplicationContext());
                    LoginInfo.LoginChildInfo data = loginData.getData();
                    spConfig.setUserInfoStatus(data.getStatus_auth());
                    spConfig.setUserInfoPercent(data.getStatus_perfect());
                } else {
                    tipView("1", loginData.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                tipView("1", message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", OrderActivity.this,
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
