package com.kym.ui.activity.lingqian;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.bpbro_untils.bpbro_zhifubao.AuthResult;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.model.AccessTodebit;
import com.kym.ui.model.AccountBalance;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LingQianTiXianActivity extends BaseActivity implements View.OnClickListener {

    private EditText editT_money;
    private String yue;
    private Dialog dialog_tt;
    private double min_dou;
    private TextView textV_ts;
    private DialogUtil loadDialogUti;
    private TextView textV_bankname;
    private TextView textV_bankno;
    private NewUserResponse.DataBean userAllInfoNew;
    private String bank_name;
    private String amount = "0";
    private DialogUtil dialogUtil;
    private TextView line1, line2, zhifubao, zfbBtn;
    private LinearLayout li1, li2, tab1, tab2;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ling_qian_ti_xian);
        userAllInfoNew = SPConfig.getInstance(LingQianTiXianActivity.this).getUserAllInfoNew();
        initview();
        ACCESSTODEBIT();
        isShouQuan();
    }

    private void ACCESSTODEBIT() {
        dialogUtil = new DialogUtil(LingQianTiXianActivity.this);
        Connect.getInstance().post(LingQianTiXianActivity.this, IService.ACCESSTODEBIT, null, new Connect.OnResponseListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                AccessTodebit response = (AccessTodebit) JsonUtils.parse((String) result, AccessTodebit.class);
                if (response.getResult().getCode() == 10000) {
                    AccessTodebit.DataBean bank = response.getData();
                    String bank_no = bank.getBank_no();
                    String substring = bank_no.substring(bank_no.length() - 4, bank_no.length());
                    bank_name = bank.getBank_name();
                    textV_bankname.setText(bank_name);
                    textV_bankno.setText("尾号" + substring + "储蓄卡");
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

    private void account_cash() {
        String s = editT_money.getText().toString();
        amount = AmountUtils.changeY2F(s);
        HashMap<String, String> param = new HashMap<>();
        param.put("amount", amount);
        loadDialogUti = new DialogUtil(this);
        Connect.getInstance().post(LingQianTiXianActivity.this, IService.WODE_LINGQIAN_TIXIAN, param, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                AccountBalance response = (AccountBalance) JsonUtils.parse((String) result, AccountBalance.class);
                if (response.getResult().getCode() == 10000) {
                    dialog_one(amount);
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
//                try {
//                    JSONObject obj = new JSONObject(result.toString());
//                    String data = obj.get("result").toString();
//                    JSONObject obj1 = new JSONObject(data);
//                    String msg = obj1.get("msg").toString();
//                    ToastUtil.showTextToas(getApplicationContext(), msg);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                loadDialogUti.dismiss();
            }

            @Override
            public void onFailure(String message) {
                loadDialogUti.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    private void initview() {
        Intent intent = getIntent();
        yue = intent.getStringExtra("yue");
        ImageView imageV_fanhui = findViewById(R.id.head_img_left);
        imageV_fanhui.setVisibility(View.VISIBLE);
        imageV_fanhui.setOnClickListener(this);
        TextView textV_title = findViewById(R.id.head_text_title);
        textV_title.setVisibility(View.VISIBLE);
        textV_title.setText("提现");
        textV_ts = findViewById(R.id.textV_tishimsg);
        textV_bankname = findViewById(R.id.textV_bankname);
        textV_bankno = findViewById(R.id.textV_bankno_wei);
        TextView textV_yue = findViewById(R.id.textV_yue);
        TextView textV_ok = findViewById(R.id.zhuan_btn_ok);
        textV_ok.setOnClickListener(this);
        editT_money = findViewById(R.id.editT_money);
        textV_yue.setText(yue);
        zhifubao = findViewById(R.id.zhifubao);
        zfbBtn = findViewById(R.id.zfbBtn);
        zfbBtn.setOnClickListener(this);
        li1 = findViewById(R.id.li1);
        li2 = findViewById(R.id.li2);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        line1 = findViewById(R.id.text_line1);
        line2 = findViewById(R.id.text_line2);
        li1.setVisibility(View.VISIBLE);
        line1.setVisibility(View.VISIBLE);
        textV_ts.setText("提现金额小于100(含)元收取1元,大于100元收取1%的手续费");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.yue_tixian, menu);
        return true;
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void dialog_one(String amount) {
        dialog_tt = new Dialog(LingQianTiXianActivity.this, R.style.MyDialgoStyle_xin_x);
        Window window = dialog_tt.getWindow();
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.addpopwindow_tt, null);
        ImageView imageV_top = view.findViewById(R.id.ige_top);
        TextView textV_name = view.findViewById(R.id.textV_name_t);
        TextView textV_bank = view.findViewById(R.id.textV_bank_t);
        TextView textV_time = view.findViewById(R.id.textV_time_t);
        TextView textV_money = view.findViewById(R.id.textV_money_t);
        Glide.with(LingQianTiXianActivity.this).load(userAllInfoNew.getHeadimage()).placeholder(R.drawable.default_image).error(R.drawable.default_image)
                .dontAnimate().into(imageV_top);
        view.findViewById(R.id.layout_diss).setOnClickListener(this);
        textV_name.setText(userAllInfoNew.getName() + "");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        textV_time.setText(dateNowStr + "");
        int i = Integer.parseInt(amount);
        long l = Long.parseLong(i + "");
        try {
            String s = AmountUtils.changeF2Y(l + "");
            if (Clone.OMID.equals("1H1AJD6SLKVADDM6")) {
                textV_bank.setText("银行卡");
            } else {
                textV_bank.setText("支付宝");
            }
            textV_money.setText(s + " 元");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert window != null;
        window.setContentView(view);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog_tt.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.layout_diss:
                dialog_tt.dismiss();
                finish();
                break;
            case R.id.zhuan_btn_ok:
                String m = editT_money.getText().toString();
                if (Clone.OMID.equals("1H1AJD6SLKVADDM6")) {
                    if (m.equals("")) {
                        ToastUtil.showTextToas(getApplicationContext(), "请输入转出金额");
                    } else {
                        String money = editT_money.getText().toString();
                        double money_dou = Double.parseDouble(money);
                        double money_yue = Double.parseDouble(yue);
                        double b = min_dou;
                        if (money_dou >= b) {
                            if (money_yue >= money_dou) {
                                account_cash();
                            } else {
                                ToastUtil.showTextToas(getApplicationContext(), "提现金额+手续费不能超过账户余额");
                            }
                        } else {
                            ToastUtil.showTextToas(getApplicationContext(), "提现金额不能小于" + min_dou + "元");
                        }
                    }
                } else {
                    if (zfbBtn.getText().toString().equals("立即绑定")) {
                        ToastUtil.showTextToas(getApplicationContext(), "请先绑定支付宝账号");
                    } else if (li1.getVisibility() == View.GONE) {
                        ToastUtil.showTextToas(getApplicationContext(), "请选择支付宝提现");
                    } else if (m.equals("")) {
                        ToastUtil.showTextToas(getApplicationContext(), "请输入转出金额");
                    } else {
                        String money = editT_money.getText().toString();
                        double money_dou = Double.parseDouble(money);
                        double money_yue = Double.parseDouble(yue);
                        double b = min_dou;
                        if (money_dou >= b) {
                            if (money_yue >= money_dou) {
                                account_cash();
                            } else {
                                ToastUtil.showTextToas(getApplicationContext(), "提现金额+手续费不能超过账户余额");
                            }
                        } else {
                            ToastUtil.showTextToas(getApplicationContext(), "提现金额不能小于" + min_dou + "元");
                        }
                    }
                }
                break;
            case R.id.zfbBtn:
                zfbShouQuan();
                break;
            case R.id.tab1:
                li1.setVisibility(View.VISIBLE);
                li2.setVisibility(View.GONE);
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                break;
            case R.id.tab2:
                li2.setVisibility(View.VISIBLE);
                li1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                line1.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void isShouQuan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(this.getApplicationContext(), IService.ZHIFUBAO_IS_SHOUQUAN, null, new Connect.OnResponseListener() {
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
                        String ali_nick_name = obj2.get("ali_nick_name").toString();
                        if (ali_nick_name.equals("no")) {
                            zhifubao.setText("请先绑定支付宝账号");
                            zfbBtn.setText("立即绑定");
                            zfbBtn.setEnabled(true);
                        } else {
                            zhifubao.setText("提现到支付宝：" + ali_nick_name);
                            zfbBtn.setText("已绑定");
                            zfbBtn.setEnabled(false);
                        }
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

    private void zfbShouQuan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(this.getApplicationContext(), IService.ZHIFUBAO_SHOUQUAN, null, new Connect.OnResponseListener() {
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
                        final String orderInfo = obj2.get("auth_link").toString();
                        // 对授权接口的调用需要异步进行。
                        Runnable authRunnable = new Runnable() {
                            @Override
                            public void run() {
                                // 构造AuthTask 对象
                                AuthTask authTask = new AuthTask(LingQianTiXianActivity.this);
                                // 获取授权结果。
                                Map<String, String> result = authTask.authV2(orderInfo, true);

                                // 将授权结果以 Message 的形式传递给 App 的其它部分处理。
                                // 对授权结果的处理逻辑可以参考支付宝 SDK Demo 中的实现。
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        Thread authThread = new Thread(authRunnable);
                        authThread.start();
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        String str[] = authResult.getResult().split("&");
                        String srr1 = null;
                        for (int i = 0; i < str.length; i++) {
                            if (str[i].indexOf("user_id") != -1) {
                                srr1 = str[i].split("=")[1];
                            }
                        }
                        Log.d("授权支付宝", String.valueOf(authResult));
                        zfbUserInfo(srr1, authResult.getAuthCode());
                        ToastUtil.showTextToas(getApplicationContext(), "授权成功");
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), "授权失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void zfbUserInfo(String user_id, String code) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("user_id", user_id);
        paramx.put("code", code);
        Connect.getInstance().post(getApplicationContext(), IService.ZHIFUBAO_USERINFO, paramx, new Connect.OnResponseListener() {
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
                        String orderInfo = obj2.get("alipay_user_info_share_response").toString();
                        JSONObject obj3 = new JSONObject(orderInfo);
                        String nick_name = obj3.getString("nick_name");
                        zhifubao.setText("提现到支付宝：" + nick_name);
                        zfbBtn.setText("已绑定");
                        zfbBtn.setEnabled(false);

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
