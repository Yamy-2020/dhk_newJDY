package com.kym.ui.activity.daichang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.kym.ui.BackDialog;
import com.kym.ui.BackDialog3;
import com.kym.ui.PayResult;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.DaiChangPlanPreviewAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.DaiChangPalnPreviewInfo;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sun
 * @date 2020/1/14
 */
public class DaiChangPlanPreviewActivity extends BaseActivity implements View.OnClickListener {
    private List<DaiChangPalnPreviewInfo.DataBean.ListBean> data_dj;
    private ListView listView;
    private TextView change_card;
    private static final int SDK_PAY_FLAG = 1;
    private BackDialog3 backDialog3;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_chang_plan_preview);
        initHead();
        initView();
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
        tvTitle.setText("提交贷偿计划");
    }

    private void initView() {
        listView = findViewById(R.id.listView_sj);
        change_card = findViewById(R.id.change_card);
        change_card.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPreview();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                submitPlan();
                break;
        }
    }

    /**
     * 获取预览计划
     */
    private void getPreview() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("cardid"));
        params.put("money", getIntent().getStringExtra("money"));
        params.put("modeltype", getIntent().getStringExtra("modeltype"));
        Connect.getInstance().post(getApplicationContext(), IService.DC_GHT_PREVIEW, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                DaiChangPalnPreviewInfo response = (DaiChangPalnPreviewInfo) JsonUtils.parse((String) result, DaiChangPalnPreviewInfo.class);
                if (response.getResult().getCode() == 10000) {
                    DaiChangPalnPreviewInfo.DataBean data = response.getData();
                    data_dj = data.getPayconList();
                    DaiChangPlanPreviewAdapter sj_oneAdapter = new DaiChangPlanPreviewAdapter(DaiChangPlanPreviewActivity.this, data_dj);
                    listView.setAdapter(sj_oneAdapter);
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
     * 提交预览计划
     */
    private void submitPlan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("cardid"));
        params.put("code", getIntent().getStringExtra("cityCode"));
        params.put("city", getIntent().getStringExtra("city"));
        Connect.getInstance().post(getApplicationContext(), IService.DC_GHT_SUBMIT, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.getString("msg");
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        final JSONObject obj2 = new JSONObject(data1);
                        final String bill_id = obj2.get("bill_id").toString();
                        backDialog3 = new BackDialog3("支付", "取消", "提示", "制定该计划，需要支付手续费",
                                DaiChangPlanPreviewActivity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.textView1:
                                        backDialog3.dismiss();
                                        payFee(bill_id);
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
                        ToastUtil.showTextToas(getApplicationContext(), msg);
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
                        backDialog = new BackDialog("", "支付成功", "确定", DaiChangPlanPreviewActivity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getApplicationContext(), DaiChangPlanRecordActivity.class));
                                finish();
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        tipView("2", "支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 支付空卡手续费
     */
    private void payFee(String bill_id) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("bill_id", bill_id);
        Connect.getInstance().post(getApplicationContext(), IService.DC_GHT_FEE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        final String orderInfo = obj2.get("data").toString();
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(DaiChangPlanPreviewActivity.this);
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

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", DaiChangPlanPreviewActivity.this,
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
