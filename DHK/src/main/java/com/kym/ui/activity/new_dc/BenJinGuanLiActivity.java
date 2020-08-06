package com.kym.ui.activity.new_dc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.kym.ui.PayResult;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.dialog.BenJinWenTiDialog;
import com.kym.ui.dialog.BenJinZCDialog;
import com.kym.ui.util.Connect;
import com.kym.ui.util.RiseNumberTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;

public class BenJinGuanLiActivity extends BaseActivity implements View.OnClickListener {

    private RiseNumberTextView benjin;
    private TextView principal_use, principal_frozen, principal_withdrawn;
    private PromptDialog promptDialog;
    private BenJinZCDialog benJinZCDialog;
    private BenJinWenTiDialog benJinWenTiDialog;
    private static final int SDK_PAY_FLAG = 1;
    private Dialog getCodeDialog, getCodeDialog1;
    private EditText money;
    private Context mContext;
    private ImageView head_img_right;
    private TopRightMenu mTopRightMenu;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ben_jin_guan_li);
        mContext = this;
        initHead();
        initView();
        bj_zh();
    }

    private void initView() {
        benjin = findViewById(R.id.benjin);
        principal_use = findViewById(R.id.principal_use);
        principal_frozen = findViewById(R.id.principal_frozen);
        principal_withdrawn = findViewById(R.id.principal_withdrawn);
        TextView bj_zc = findViewById(R.id.bj_zhuancun);
        TextView bj_cz = findViewById(R.id.bj_chongzhi);
        TextView bj_tq = findViewById(R.id.bj_tiqu);
        ImageView wenti = findViewById(R.id.wenti);
        wenti.setOnClickListener(this);
        bj_zc.setOnClickListener(this);
        bj_cz.setOnClickListener(this);
        bj_tq.setOnClickListener(this);
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("本金管理");
        head_img_right = findViewById(R.id.head_img_right);
        head_img_right.setVisibility(View.VISIBLE);
        head_img_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.bj_zhuancun:
                benJinZCDialog = new BenJinZCDialog(this,
                        R.style.Theme_Dialog_Scale, new BenJinZCDialog.DialogClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.btn:
                                bj_zhuancun();
                                benJinZCDialog.dismiss();
                                break;
                            case R.id.close:
                                benJinZCDialog.dismiss();
                                break;
                        }
                    }
                });
                benJinZCDialog.setCancelable(false);
                benJinZCDialog.show();
                break;
            case R.id.bj_chongzhi:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.benjinczdalog, null);
                initGetCodeDialog(view1);
                showGetCodeDialog(view1);
                break;
            case R.id.bj_tiqu:
                View view = LayoutInflater.from(mContext).inflate(R.layout.benjintqdalog, null);
                initGetCodeDialog1(view);
                showGetCodeDialog1(view);
                break;
            case R.id.head_img_right:
                mTopRightMenu = new TopRightMenu(BenJinGuanLiActivity.this);
                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem("交易记录"));
                mTopRightMenu
                        .setHeight(160)     //默认高度480
                        .showIcon(false) //默认宽度wrap_content
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                if (position == 0) {
                                    intent = new Intent(getApplicationContext(), BenJinTradeListActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .showAsDropDown(head_img_right, -140, 0);
                break;
            case R.id.wenti:
                benJinWenTiDialog = new BenJinWenTiDialog(this,
                        R.style.Theme_Dialog_Scale, new BenJinWenTiDialog.DialogClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.close:
                                benJinWenTiDialog.dismiss();
                                break;
                        }
                    }
                });
                benJinWenTiDialog.setCancelable(false);
                benJinWenTiDialog.show();
                break;
        }
    }

    private void initGetCodeDialog(View view) {
        money = view.findViewById(R.id.money);
        LinearLayout btn = view.findViewById(R.id.btn);
        LinearLayout close = view.findViewById(R.id.close);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(money.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入充值金额");
                    return;
                }
                getCodeDialog.dismiss();
                bj_chongzhi();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCodeDialog.dismiss();
            }
        });
    }

    private void showGetCodeDialog(View view) {
        getCodeDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        getCodeDialog.setContentView(view, lp);
        getCodeDialog.setCancelable(true);
        getCodeDialog.setCanceledOnTouchOutside(false);
        getCodeDialog.show();
    }

    private void initGetCodeDialog1(View view) {
        money = view.findViewById(R.id.money);
        LinearLayout btn = view.findViewById(R.id.btn);
        LinearLayout close = view.findViewById(R.id.close);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(money.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入提取金额");
                    return;
                }
                getCodeDialog1.dismiss();
                bj_tiqu();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCodeDialog1.dismiss();
            }
        });
    }

    private void showGetCodeDialog1(View view) {
        getCodeDialog1 = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        getCodeDialog1.setContentView(view, lp);
        getCodeDialog1.setCancelable(true);
        getCodeDialog1.setCanceledOnTouchOutside(false);
        getCodeDialog1.show();
    }

    /**
     * 本金账户
     */
    private void bj_zh() {
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("加载中");
        Connect.getInstance().post(this, IService.BENJINZHANGHU, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                promptDialog.dismissImmediately();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String data = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(data);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        final String principal = obj2.get("principal").toString();
                        float parseFloat = Float.parseFloat(principal);
                        benjin.withNumber(parseFloat);
                        benjin.setDuration(0);
                        benjin.start();
                        principal_use.setText(obj2.getString("principal_use"));
                        principal_frozen.setText(obj2.getString("principal_frozen"));
                        principal_withdrawn.setText(obj2.getString("principal_withdrawn"));
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                promptDialog.dismissImmediately();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 转存本金
     */
    private void bj_zhuancun() {
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("加载中");
        HashMap<String, String> params = new HashMap<>();
        params.put("type", BenJinZCDialog.type);
        Connect.getInstance().post(this, IService.FENRUNZHUANCUN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                promptDialog.dismissImmediately();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String data = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(data);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    ToastUtil.showTextToas(getApplicationContext(), msg);
                    bj_zh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                promptDialog.dismissImmediately();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 充值本金
     */
    private void bj_chongzhi() {
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("加载中");
        HashMap<String, String> params = new HashMap<>();
        params.put("amount", money.getText().toString());
        Connect.getInstance().post(this, IService.BENJINCHONGZHI, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                promptDialog.dismissImmediately();
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
                                PayTask alipay = new PayTask(BenJinGuanLiActivity.this);
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
                promptDialog.dismissImmediately();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 提取本金
     */
    private void bj_tiqu() {
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("加载中");
        HashMap<String, String> params = new HashMap<>();
        params.put("amount", money.getText().toString());
        Connect.getInstance().post(this, IService.BENJINTIQU, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                promptDialog.dismissImmediately();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String data = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(data);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    ToastUtil.showTextToas(getApplicationContext(), msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                promptDialog.dismissImmediately();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

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
                        ToastUtil.showTextToas(getApplicationContext(), "充值成功");
                        bj_zh();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.showTextToas(getApplicationContext(), "充值失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };
}
