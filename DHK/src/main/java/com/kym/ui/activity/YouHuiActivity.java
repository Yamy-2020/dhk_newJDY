package com.kym.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.kym.ui.BackDialog;
import com.kym.ui.BackDialog3;
import com.kym.ui.LianxiActivity_new;
import com.kym.ui.PayResult;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.YouHuiShengJiAdapter;
import com.kym.ui.adapter.YouHuiTuiGuangAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.GouMaiQuanYi;
import com.kym.ui.info.YouHuiShengJiResponse;
import com.kym.ui.info.YouHuiTuiGuangResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


/**
 * 优惠升级
 *
 * @author sun
 * @date 2019/12/3
 */
public class YouHuiActivity extends BaseActivity implements View.OnClickListener {

    private YouHuiShengJiAdapter adapter;
    private YouHuiTuiGuangAdapter adapter1;
    private BackDialog3 backDialog3;
    private static final int SDK_PAY_FLAG = 1;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_hui);
        getHomeYouHuiShengJi();
        initHead();
        initView();

    }

    private void initHead() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.head_text_title);
        title.setText("购买权益");
    }

    private void initView() {
        LinearLayout tuiguang_shengji = (LinearLayout) findViewById(R.id.tuiguang_shengji);
        tuiguang_shengji.setOnClickListener(this);
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_youhui_shengji);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new YouHuiShengJiAdapter(this, new YouHuiShengJiAdapter.OnYHShengJiClick() {
            @Override
            public void OnYHShengJiClick(final YouHuiShengJiResponse.YHShengJiInfo yhShengJiInfo) {
                backDialog3 = new BackDialog3("确定", "取消", "提示", "升级" + yhShengJiInfo.getName() + "需支付" + yhShengJiInfo.getMoney() + "元",
                        YouHuiActivity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {

                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.textView1:
                                backDialog3.dismiss();
                                zhifubao(yhShengJiInfo.getUpgradeLevel());
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
        });
        mRecyclerView.setAdapter(adapter);

        final RecyclerView mRecyclerView1 = (RecyclerView) findViewById(R.id.rv_youhui_tuiguang);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView1.setLayoutManager(linearLayoutManager1);
        adapter1 = new YouHuiTuiGuangAdapter(this, new YouHuiTuiGuangAdapter.OnYHTuiGuangClick() {
            @Override
            public void OnYHTuiGuangClick(YouHuiTuiGuangResponse.YHTuiGuaangInfo yhTuiGuaangInfo) {

            }
        });
        mRecyclerView1.setAdapter(adapter1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.tuiguang_shengji:
                Intent intent = new Intent(YouHuiActivity.this, LianxiActivity_new.class);
                intent.putExtra("title", "2");
                startActivity(intent);
                break;
        }
    }

    //新政策升级
    private void getHomeYouHuiShengJi() {
//        final DialogUtil dialogUtil = new DialogUtil(this);

        Connect.getInstance().post(YouHuiActivity.this, IService.HOME_YOUHUI, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
//                dialogUtil.dismiss();
                GouMaiQuanYi response = (GouMaiQuanYi) JsonUtils.parse((String) result, GouMaiQuanYi.class);
                if (response.getResult().getCode() == 10000) {
                    String s = response.getData().getCurrent_list().getCurrent_msg();
                    if (s != null /*&& s.size() > 0*/) {
//                        adapter.setData(data);
                    Log.e("瑕疵", "onSuccess: "+s.toString() );
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", YouHuiActivity.this,
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
//                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }





    private void getYouHuiShengJi() {
        final DialogUtil dialogUtil = new DialogUtil(this);

        Connect.getInstance().post(this.getApplicationContext(), IService.YOUHUI_SHENGJI, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YouHuiShengJiResponse response = (YouHuiShengJiResponse) JsonUtils.parse((String) result, YouHuiShengJiResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<YouHuiShengJiResponse.YHShengJiInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", YouHuiActivity.this,
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

    private void getYouHuiGuanli() {
        final DialogUtil dialogUtil = new DialogUtil(this);

        Connect.getInstance().post(this.getApplicationContext(), IService.YOUHUI_TUIGUANG, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YouHuiTuiGuangResponse response = (YouHuiTuiGuangResponse) JsonUtils.parse((String) result, YouHuiTuiGuangResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<YouHuiTuiGuangResponse.YHTuiGuaangInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter1.setData(data);
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", YouHuiActivity.this,
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
                                YouHuiActivity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {

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
                                YouHuiActivity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {

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

    private void zhifubao(String upgradeLevel) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
        HashMap<String, String> params = new HashMap<>();//getPayment_amount()
        params.put("level", SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getLevel());
        params.put("upgradeLevel", upgradeLevel);
        Connect.getInstance().post(this.getApplicationContext(), IService.YONGHUI_PAY, params, new Connect.OnResponseListener() {
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
                                PayTask alipay = new PayTask(YouHuiActivity.this);
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
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (JSONException e) {
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
}
