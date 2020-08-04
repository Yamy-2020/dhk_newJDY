package com.kym.ui.activity.rongxinfen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.kym.ui.BackDialog;
import com.kym.ui.NewHomeFragment;
import com.kym.ui.PayResult;
import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.hualuo.HL_HuanKuanActivity;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 信用提升
 *
 * @author sun
 * @date 2019/12/3
 */

public class XinYong_GuanLi_Activity extends Activity implements View.OnClickListener {

    private LinearLayout li1_1, li1_2, li2_1, li2_2, li3_1, li3_2;
    private ImageView img_grxx, img_ykrc, img_sjzx;
    private String li1 = "1", li2 = "1", li3 = "1";
    private View view1, view2, view3;
    private LinearLayout renzheng1, renzheng2, renzheng3, huankuan, zhangdan, shengji;
    private static final int SDK_PAY_FLAG = 1;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_yong_guan_li);
        initHead();
        initView();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("信用提升");
    }

    private void initView() {
        renzheng1 = (LinearLayout) findViewById(R.id.renzheng1);
        renzheng2 = (LinearLayout) findViewById(R.id.renzheng2);
        renzheng3 = (LinearLayout) findViewById(R.id.renzheng3);
        huankuan = (LinearLayout) findViewById(R.id.huankuan);
        zhangdan = (LinearLayout) findViewById(R.id.zhangdan);
        shengji = (LinearLayout) findViewById(R.id.shengji);
        img_grxx = (ImageView) findViewById(R.id.img_grxx);
        img_ykrc = (ImageView) findViewById(R.id.img_ykrc);
        img_sjzx = (ImageView) findViewById(R.id.img_sjzx);
        li1_1 = (LinearLayout) findViewById(R.id.li1_1);
        li1_2 = (LinearLayout) findViewById(R.id.li1_2);
        li2_1 = (LinearLayout) findViewById(R.id.li2_1);
        li2_2 = (LinearLayout) findViewById(R.id.li2_2);
        li3_1 = (LinearLayout) findViewById(R.id.li3_1);
        li3_2 = (LinearLayout) findViewById(R.id.li3_2);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        li1_1.setOnClickListener(this);
        li2_1.setOnClickListener(this);
        li3_1.setOnClickListener(this);
        renzheng1.setOnClickListener(this);
        renzheng2.setOnClickListener(this);
        renzheng3.setOnClickListener(this);
        huankuan.setOnClickListener(this);
        zhangdan.setOnClickListener(this);
        shengji.setOnClickListener(this);
        li1_2.setVisibility(View.GONE);
        li2_2.setVisibility(View.GONE);
        li3_2.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.li1_1:
                if (li1.equals("1")) {
                    li1 = "2";
                    img_grxx.setImageResource(R.drawable.top);
                    li1_2.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.VISIBLE);
                } else if (li1.equals("2")) {
                    li1 = "1";
                    img_grxx.setImageResource(R.drawable.bottom);
                    li1_2.setVisibility(View.GONE);
                    view1.setVisibility(View.GONE);
                }
                break;
            case R.id.li2_1:
                if (li2.equals("1")) {
                    li2 = "2";
                    img_ykrc.setImageResource(R.drawable.top);
                    li2_2.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                } else if (li2.equals("2")) {
                    li2 = "1";
                    img_ykrc.setImageResource(R.drawable.bottom);
                    li2_2.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                }
                break;
            case R.id.li3_1:
                if (li3.equals("1")) {
                    li3 = "2";
                    img_sjzx.setImageResource(R.drawable.top);
                    li3_2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                } else if (li3.equals("2")) {
                    li3 = "1";
                    img_sjzx.setImageResource(R.drawable.bottom);
                    li3_2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                }
                break;
            case R.id.renzheng1:
                tipView("您已认证");
                break;
            case R.id.renzheng2:
                tipView("暂未开放,敬请您期待");
                break;
            case R.id.renzheng3:
                tipView("暂未开放,敬请您期待");
                break;
            case R.id.huankuan:
                startActivity(new Intent(getApplicationContext(), HL_HuanKuanActivity.class));
                break;
            case R.id.zhangdan:
                tipView("请去导入账单试试吧");
                break;
            case R.id.shengji:
                if (SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getLevel().equals("1")) {
                    if (Clone.OMID.equals("X9FN9CEDKB0C9A43")) {
                        //天天智还
                        zhifubao(IService.YONGHUI_PAY);
                    } else {
                        zhifubao(IService.UPGRADEORDER);
                    }
                } else {
                    tipView("更多优惠,请咨询客服");
                }
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
                        backDialog = new BackDialog("", "支付成功", "确定", XinYong_GuanLi_Activity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getApplicationContext(), NewHomeFragment.class));
                                finish();
                                backDialog.dismiss();
                            }
                        });
                    } else {
                        tipView("支付失败");
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
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", SPConfig.getInstance(this.getApplicationContext()).getUserAllInfoNew().getUid());
        if (Clone.OMID.equals("X9FN9CEDKB0C9A43")) {
            params.put("level", SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getLevel());
            params.put("upgradeLevel", "2");
        }
        Connect.getInstance().post(this.getApplicationContext(), url, params, new Connect.OnResponseListener() {
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
                                PayTask alipay = new PayTask(XinYong_GuanLi_Activity.this);
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

    public void tipView(String msg) {
        backDialog = new BackDialog("", msg, "确定", XinYong_GuanLi_Activity.this,
                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
            @Override
            public void onClick(View view) {
                backDialog.dismiss();
            }
        });
        backDialog.setCancelable(false);
        backDialog.show();
    }

}
