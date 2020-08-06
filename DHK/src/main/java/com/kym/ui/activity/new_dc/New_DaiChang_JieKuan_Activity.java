package com.kym.ui.activity.new_dc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.BackDialog3;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.daichang.DaiChangBankCardListActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.SelectBankAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.hualuo.HL_BK_WebActivity;
import com.kym.ui.info.BankNameResponse;
import com.kym.ui.info.JieKuanToTalMoney1Response;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.model.AccessTodebit;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import widget.CustomPopWindow;

public class New_DaiChang_JieKuan_Activity extends BaseActivity implements View.OnClickListener {

    private CustomPopWindow bankPopWindow;
    private List<BankNameResponse.BankNameInfo> banks;
    private EditText jk_money;
    private TextView kj_money, date, total_money, look, hk_cxk, hk_xyk, yuelilv, lixi, benjin, xieyi1, xieyi2;
    private ImageView tvimg1, tvimg2;
    private BackDialog3 backDialog4;
    public static String total_Money, date_Time;
    private Dialog getCodeDialog;
    private EditText etCode;
    private Context mContext;
    private TextView tvGetCode, xf_text;
    private CountDownTimer timer;
    private int FANHUI = 3;
    private String name, card, mobile, bank_zh, bank_no, xykbankNo, xykbank_name, bank_name, loanAddDate, loanrepayDate, rixi, str_lixi, str_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__dai_chang__jie_kuan);
        mContext = this;
        initView();
        initHead();
        getCXK();
        geiRiXi();
        name = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getName();
        card = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getIdcard();
        mobile = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getMobile();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("借款");
    }

    private void initView() {
        TextView btn = (TextView) findViewById(R.id.btn);
        jk_money = (EditText) findViewById(R.id.jk_money);
        kj_money = (TextView) findViewById(R.id.kj_money);
        yuelilv = (TextView) findViewById(R.id.yuelilv);
        lixi = (TextView) findViewById(R.id.lixi);
        kj_money.setText("您当前单笔借款最高可用" + getIntent().getStringExtra("money") + "元");
        date = (TextView) findViewById(R.id.date);
        total_money = (TextView) findViewById(R.id.total_money);
        look = findViewById(R.id.look);
        hk_cxk = findViewById(R.id.hk_cxk);
        hk_xyk = findViewById(R.id.hk_xyk);
        benjin = findViewById(R.id.benjin);
        xieyi1 = findViewById(R.id.xieyi1);
        xieyi2 = findViewById(R.id.xieyi2);
        xieyi1.setOnClickListener(this);
        xieyi2.setOnClickListener(this);
        tvimg1 = findViewById(R.id.tvImg1);
        tvimg2 = findViewById(R.id.tvImg2);
        date.setOnClickListener(this);
        look.setOnClickListener(this);
        hk_xyk.setOnClickListener(this);
        tvimg1.setOnClickListener(this);
        tvimg2.setOnClickListener(this);
        btn.setOnClickListener(this);
        jk_money.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        jk_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        jk_money.setText(s);
                        jk_money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().equals(".")) {
                    s = "0" + s;
                    jk_money.setText(s);
                    jk_money.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        jk_money.setText(s.subSequence(0, 1));
                        jk_money.setSelection(1);
                        return;
                    }
                }
                date.setText("");
                date.setHint("请选择期数");
                total_money.setText("");
                total_Money = "";
                lixi.setText("");
                date_Time = "";
                if (!jk_money.getText().toString().equals("")) {
                    benjin.setText(bigDecimalMoney(Float.parseFloat(jk_money.getText().toString())) + "元");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FANHUI) {
                String bankName = data.getStringExtra("bankName");
                xykbankNo = data.getStringExtra("bankNo");
                xykbank_name = data.getStringExtra("bank_name");
                hk_xyk.setText(bankName);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.date:
                date.setEnabled(false);
                getDate();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.look:
                if (jk_money.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入借款金额");
                } else if (jk_money.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择期数");
                } else if (date.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择期数");
                } else {

                }
                break;
            case R.id.hk_xyk:
                Intent intent = new Intent(getApplicationContext(), AllDcCardListActivity.class);
                startActivityForResult(intent, FANHUI);
                break;
            case R.id.xieyi1:
                if (jk_money.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入借款金额");
                } else if (date.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择期数");
                } else if (hk_xyk.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择信用卡");
                } else {
                    intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                    intent.putExtra("WEB_TITLE", "借款协议");
                    intent.putExtra("WEB_URL", "http://www.bpbro.com/h5/views/index.html?bp_userName=" + name +
                            "&bp_idCard=" + card + "&bp_mobile=" + mobile + "&bp_jkmoney=" + jk_money.getText().toString() +
                            "&bp_jktime=" + 30 * (Integer.parseInt(date.getText().toString().split("个")[0])) +
                            "&bp_hkway=储蓄卡&bp_hktime=" + loanrepayDate + "&bp_rililv=" + (Float.parseFloat(rixi) * 100) +
                            "%&bp_skzhanghu=" + xykbank_name + "&bp_skzhanghao=" + xykbankNo + "&bp_userName=" + name + "&bp_time=" + loanAddDate);
                    startActivity(intent);
                    break;
                }
                break;
            case R.id.xieyi2:
                if (jk_money.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入借款金额");
                } else if (date.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择期数");
                } else if (hk_xyk.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择信用卡");
                } else {
                    intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                    intent.putExtra("WEB_TITLE", "自动扣款");
                    intent.putExtra("WEB_URL", "http://www.bpbro.com/h5/views/index2.html?bp_userName=" + name +
                            "&bp_idCard=" + card + "&bp_bank_zh=" + bank_zh + "&bp_bank_name=" + bank_name + "&bp_bank_no=" + bank_no +
                            "&bp_userName=" + name + "&bp_time=" + loanAddDate);
                    startActivity(intent);
                    break;
                }
                break;
            case R.id.tvImg1:
                if (jk_money.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入借款金额");
                } else if (date.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择期数");
                } else if (hk_xyk.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择信用卡");
                } else {
                    tvimg2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tvImg2:
                tvimg2.setVisibility(View.GONE);
                break;
            case R.id.btn:
                if (jk_money.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入借款金额");
                } else if (date.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择期数");
                } else if (hk_xyk.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择信用卡");
                } else if (tvimg2.getVisibility() == View.GONE) {
                    ToastUtil.showTextToas(getApplicationContext(), "请阅读并勾选同意借款等相关协议");
                } else {
//                    backDialog4 = new BackDialog3("确定", "取消", "提示", "借款金额是代还信用卡的垫付资金,确定要制定代还计划吗?",
//                            New_DaiChang_JieKuan_Activity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            switch (view.getId()) {
//                                case R.id.textView1:
//                                    backDialog4.dismiss();
//                                    startActivity(new Intent(getApplicationContext(), DaiChangBankCardListActivity.class));
//                                    finish();
//                                    break;
//                                case R.id.textView2:
//                                    backDialog4.dismiss();
//                                    break;
//                            }
//                        }
//                    });
//                    backDialog4.setCancelable(false);
//                    backDialog4.show();

                    View view1 = LayoutInflater.from(mContext).inflate(R.layout.layout_get_xfcode, null);
                    initGetCodeDialog(view1);
                    showGetCodeDialog(view1);
                }
                break;
        }
    }

    private void initGetCodeDialog(View view) {
        etCode = view.findViewById(R.id.et_get_code);
        tvGetCode = view.findViewById(R.id.tv_get_code);
        xf_text = view.findViewById(R.id.xf_text);
        xf_text.setText("请使用手机尾号" + SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getMobile().substring(7, 11) + "获取验证码");
        TextView tvConfirm = view.findViewById(R.id.tv_get_code_confirm);
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
                backDialog4 = new BackDialog3("确定", "取消", "提示", "借款金额是代还信用卡的垫付资金,确定要制定代还计划吗?",
                        New_DaiChang_JieKuan_Activity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.textView1:
                                backDialog4.dismiss();
                                startActivity(new Intent(getApplicationContext(), DaiChangBankCardListActivity.class));
                                finish();
                                break;
                            case R.id.textView2:
                                backDialog4.dismiss();
                                break;
                        }
                    }
                });
                backDialog4.setCancelable(false);
                backDialog4.show();
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
        timer = new CountDownTimer(60000, 1000) {
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
     * 选择期数
     */
    private void getDate() {
        try {
            JSONObject sun = new JSONObject("{\"result\":{\"code\":10000,\"msg\":\"请求成功\"}," +
                    "\"data\":[{\"name\":\"1个月\"},{\"name\":\"3个月\"},{\"name\":\"6个月\"},{\"name\":\"12个月\"}] }");
            BankNameResponse response = (BankNameResponse) JsonUtils.parse(String.valueOf(sun), BankNameResponse.class);
            banks = response.getData();
            showBankPop();
            date.setEnabled(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showBankPop() {
        if (bankPopWindow == null) {
            View view = initBankView();
            bankPopWindow = new CustomPopWindow.Builder(this).setAnimationStyle(android.R.style.Animation_InputMethod).setFocusable(true)
                    .setOutsideFocusable(true).setContentView(view).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .build();
        }
        bankPopWindow.showAtLocation(date, Gravity.BOTTOM);
    }


    private View initBankView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_select_bank, null);
        RecyclerView recyclerView = view.findViewById(R.id.rv_select_bank);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider_1dp));
        recyclerView.addItemDecoration(decoration);
        SelectBankAdapter adapter = new SelectBankAdapter(this);
        adapter.setData(banks);
        adapter.setListener(new SelectBankAdapter.SelectBankListener() {
            @Override
            public void selectBank(BankNameResponse.BankNameInfo bankInfo) {
                date.setText(bankInfo.getName());
                date.setHint("");
                if (!jk_money.getText().toString().equals("")) {
                    getTotalMoney();
                }
                bankPopWindow.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
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
                    String substring = bank_no.substring(bank_no.length() - 4);
                    hk_cxk.setText(bank.getBank_name() + "储蓄卡" + substring);
                    bank_zh = bank.getBank_sub();
                    bank_name = bank.getBank_name();
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

    private void getTotalMoney() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("amount", jk_money.getText().toString());
        params.put("days", date.getText().toString().split("个")[0]);
        Connect.getInstance().post(this.getApplicationContext(), IService.JIEKUANTOTALMONEY, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                JieKuanToTalMoney1Response response = (JieKuanToTalMoney1Response) JsonUtils.parse((String) result, JieKuanToTalMoney1Response.class);
                if (response.getResult().getCode() == 10000) {
                    JieKuanToTalMoney1Response.JieKuanTotalMoneyInfo bank = response.getData();
                    total_money.setText(bigDecimalMoney(Float.parseFloat(bank.getLoanAllBack())) + "元");
                    lixi.setText(bigDecimalMoney(Float.parseFloat(bank.getLoanInterest())) + "元");
                    str_lixi = bank.getLoanInterest();
                    str_money = total_Money = jk_money.getText().toString();
                    date_Time = date.getText().toString();
                    loanAddDate = bank.getLoanAddDate();
                    loanrepayDate = bank.getLoanrepayDate();
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
     * 获取日息
     */
    private void geiRiXi() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(this, IService.JIEKUANRIXI, null, new Connect.OnResponseListener() {
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
                        rixi = obj2.get("DayInterest").toString();
                        yuelilv.setText((Float.parseFloat(obj2.get("DayInterest").toString()) * 100 * 30) + "%");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

    /**
     * 获取验证码
     */
    private void JieKuan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("amount", jk_money.getText().toString());
        params.put("days", date.getText().toString().split("个")[0]);
        params.put("interest", str_lixi);
        params.put("repayamount", str_money);
        Connect.getInstance().post(this, IService.JIEKUAN, params, new Connect.OnResponseListener() {
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

    public static String bigDecimalMoney(float money) {
        DecimalFormat df = new DecimalFormat(",##0.00");
        return df.format(money);
    }
}
