package com.kym.ui.activity.huankuan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.paradigm.botkit.BotKitClient;
import com.paradigm.botlib.VisitorInfo;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.bpbro_untils.bank_ocr.FileUtil;
import com.kym.ui.activity.fee_kf.ChatActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.SelectBankAdapter;
import com.kym.ui.adapter.SelectDateAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.BankNameResponse;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.newutil.DragFloatActionButton;
import com.kym.ui.util.Connect;
import com.kym.ui.util.EncryptUtils;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
import widget.CustomPopWindow;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;
import static com.zzss.jindy.appconfig.Clone.OMID;

/**
 * 添加信用卡
 *
 * @author sun
 * @date 2019/12/3
 */

public class NewAddCreditCardActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {
    /**
     * 点击识别信用卡
     * 账单日标识,还款日标识
     * 信用卡卡号,有效期,安全码,预留手机号,验证码
     * 开户银行,账单日,还款日,验证码按钮,
     * 获取银行数据
     */
    private static final int REQUEST_CODE_BANKCARD = 111;
    private TextView img_add;
    private final int TYPE_STATEMENT = 0, TYPE_DUE = 1;
    private EditText etCardNumber, etCardValidDate, etCardCVV2, etCardMobile, etVerifyCode, ed;
    private TextView tvCardBank, tvCardStatementDate, tvCardDueDate, tvGetCode;
    private List<BankNameResponse.BankNameInfo> banks;
    private CustomPopWindow datePopWindow;
    private CustomPopWindow bankPopWindow;
    private BackDialog backDialog;
    private PromptDialog promptDialog;
    private DragFloatActionButton circle_button;

    private CountDownTimer timer = new CountDownTimer(300000, 1000) {
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_credit);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        hideKeyBroad(v);
        if (hasFocus) {
            onClick(v);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.tv_new_add_verify_code:
                String mobile = etCardMobile.getText().toString().replace(" ", "");
                if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
                    ToastUtil.showTextToas(getApplicationContext(), "手机号格式不正确");
                } else {
                    getVerifyCode(mobile);
                }
                break;
            case R.id.tv_add_new_credit_confirm:
                if (canConfirm()) {
                    confirm();
                }
                break;
            case R.id.tv_new_add_statement_date:
                tvCardStatementDate.requestFocus();
                showDatePop(TYPE_STATEMENT);
                break;
            case R.id.tv_new_add_due_date:
                showDatePop(TYPE_DUE);
                break;
            case R.id.tv_new_add_bank:
                if (banks == null) {
                    tvCardBank.setEnabled(false);
                    getBankList();
                } else {
                    showBankPop();
                }
                break;
            case R.id.img_add:
                Intent intent = new Intent(NewAddCreditCardActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, REQUEST_CODE_BANKCARD);
                break;
            case R.id.circle_button:
                VisitorInfo info = new VisitorInfo();
                info.nickName = Clone.APP_NAME + "_" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getName() + "_" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getUid();
                info.userName = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getName();
                info.phone = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getMobile();
                BotKitClient.getInstance().setVisitor(info);
                BotKitClient.getInstance().setPortraitUser(getResources().getDrawable(R.drawable.icon));
                BotKitClient.getInstance().setPortraitRobot(getResources().getDrawable(R.drawable.tianjia));
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                break;
            default:
                break;

        }
    }

    private void initView() {
        circle_button = findViewById(R.id.circle_button);
        circle_button.setOnClickListener(this);
        if (OMID.equals("E1TDVFFY8JX3RY62")) {
            circle_button.setVisibility(View.GONE);
        }
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView tvTitle = findViewById(R.id.head_text_title);
        etCardNumber = findViewById(R.id.et_new_add_number);
        tvCardBank = findViewById(R.id.tv_new_add_bank);
        etCardValidDate = findViewById(R.id.et_new_add_valid_date);
        etCardCVV2 = findViewById(R.id.et_new_add_cvv2);
        tvCardStatementDate = findViewById(R.id.tv_new_add_statement_date);
        tvCardDueDate = findViewById(R.id.tv_new_add_due_date);
        etCardMobile = findViewById(R.id.et_new_add_mobile);
        etVerifyCode = findViewById(R.id.et_new_add_verify_code);
        ed = findViewById(R.id.ed);
        tvGetCode = findViewById(R.id.tv_new_add_verify_code);
        TextView tvConfirm = findViewById(R.id.tv_add_new_credit_confirm);
        img_add = findViewById(R.id.img_add);
        img_add.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvCardStatementDate.setOnClickListener(this);
        tvCardDueDate.setOnClickListener(this);
        tvCardBank.setOnClickListener(this);
        tvCardStatementDate.setOnFocusChangeListener(this);
        tvCardBank.setOnFocusChangeListener(this);
        tvCardDueDate.setOnFocusChangeListener(this);
        tvGetCode.setOnFocusChangeListener(this);
        etCardNumber.setOnFocusChangeListener(this);
        tvTitle.setText("添加信用卡");
    }

    private void hideKeyBroad(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    private void getVerifyCode(String mobile) {
        RequestCode(mobile);
    }

    private boolean canConfirm() {
        if (TextUtils.isEmpty(etCardNumber.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "信用卡卡号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvCardBank.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "开户银行不能为空");
            return false;
        }
        String validateDate = etCardValidDate.getText().toString();
        if (TextUtils.isEmpty(validateDate)) {
            ToastUtil.showTextToas(getApplicationContext(), "信用卡有效期限不能为空");
            return false;
        } else {
            if (validateDate.length() < 4) {
                ToastUtil.showTextToas(getApplicationContext(), "信用卡有效期限位数不正确");
                return false;
            } else {
                int month = Integer.parseInt(validateDate.substring(0, 2));
                int year = Integer.parseInt(validateDate.substring(2, 4));
                if (month > 12) {
                    ToastUtil.showTextToas(getApplicationContext(), "信用卡有效期限月份不正确");
                    return false;
                }
                if (year < month) {
                    ToastUtil.showTextToas(getApplicationContext(), "信用卡有效期限年月位置不正确");
                    return false;
                }
            }
        }
        if (TextUtils.isEmpty(tvCardStatementDate.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "信用卡账单日不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tvCardDueDate.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "信用卡还款日不能为空");
            return false;
        }
        if (TextUtils.isEmpty(ed.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "信用卡额度不能为空");
            return false;
        }
        if (TextUtils.isEmpty(etCardCVV2.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "信用卡CVV2码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(etCardMobile.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "预留手机号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(etVerifyCode.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "验证码不能为空");
            return false;
        }
        return true;
    }

    private void showDatePop(int type) {
        View view = initDateView(type);
        datePopWindow = new CustomPopWindow.Builder(this).setAnimationStyle(android.R.style.Animation_InputMethod).setFocusable(true)
                .setOutsideFocusable(true).setContentView(view).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .build();
        datePopWindow.showAtLocation(tvGetCode, Gravity.BOTTOM);
    }

    private View initDateView(final int type) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_select_date, null);
        RecyclerView recyclerView = view.findViewById(R.id.rv_select_date);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider_1dp));
        recyclerView.addItemDecoration(decoration);
        SelectDateAdapter adapter = new SelectDateAdapter(this);
        adapter.setListener(new SelectDateAdapter.OnDateSelectListener() {
            @Override
            public void dateSelect(int position) {
                if (type == TYPE_STATEMENT) {
                    tvCardStatementDate.setText(String.format("%d日", position));
                    if (position < 12) {
                        tvCardDueDate.setText(String.format("%d日", position + 20));
                    } else {
                        tvCardDueDate.setText(String.format("%d日", position - 11));
                    }
                } else {
                    tvCardDueDate.setText(String.format("%d日", position));
                }
                datePopWindow.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void startCountDown() {
        timer.start();
    }

    private void showBankPop() {
        if (bankPopWindow == null) {
            View view = initBankView();
            bankPopWindow = new CustomPopWindow.Builder(this).setFocusable(true).setOutsideFocusable(true)
                    .setContentView(view).setTranslucent(false).size(tvCardBank.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT)
                    .build();
        }
        bankPopWindow.showAsDropDown(tvCardBank, 0, 5);
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
                tvCardBank.setText(bankInfo.getName());
                bankPopWindow.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    /**
     * 获取银行列表
     */
    private void getBankList() {
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("加载中");
        Connect.getInstance().post(this, IService.GET_BANKNAME, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                promptDialog.dismissImmediately();
                tvCardBank.setEnabled(true);
                BankNameResponse response = (BankNameResponse) JsonUtils.parse((String) result, BankNameResponse.class);
                if (response.getResult().getCode() == 10000) {
                    banks = response.getData();
                    showBankPop();
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", NewAddCreditCardActivity.this,
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
                promptDialog.dismissImmediately();
                tvCardBank.setEnabled(true);
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 获取验证码
     */
    private void RequestCode(String mobile) {
        tvGetCode.setEnabled(false);
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("type", "5");
        params.put("uid", SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getUid());
        Connect.getInstance().post(this, IService.AUTHCODE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (response.getResult().getCode() == 10000) {
                    ToastUtil.showTextToas(getApplicationContext(), "验证码已发送");
                    startCountDown();
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", NewAddCreditCardActivity.this,
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
                    tvGetCode.setEnabled(true);
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                tvGetCode.setEnabled(true);
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 添加信用卡 - 提交
     */
    private void confirm() {
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("加载中");
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", etCardMobile.getText().toString().replace(" ", ""));
        params.put("bank_name", tvCardBank.getText().toString());
        params.put("balance", ed.getText().toString());
        String billsString = tvCardStatementDate.getText().toString();
        if (billsString.length() < 3) {
            billsString = "0" + billsString;
        }
        params.put("bills", billsString.substring(0, billsString.length() - 1));
        String repaymentString = tvCardDueDate.getText().toString();
        if (repaymentString.length() < 3) {
            repaymentString = "0" + repaymentString;
        }
        params.put("repayment", repaymentString.substring(0, billsString.length() - 1));
        params.put("auth_code", etVerifyCode.getText().toString());
        String secretKey = OMID + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getUid();
        Log.e(Clone.APP_NAME, "secretKey = " + secretKey);
        try {
            String cardNumber = EncryptUtils.des3(secretKey, etCardNumber.getText().toString().replace(" ", ""));
            params.put("bank_no", cardNumber);
            Log.d("cvv2fdfdf", etCardCVV2.getText().toString());
            String safeCode = EncryptUtils.des3(secretKey, etCardCVV2.getText().toString());
            params.put("safecode", safeCode);
            String dateStr = etCardValidDate.getText().toString();
            String validDate = EncryptUtils.des3(secretKey, dateStr.substring(2, 4) + dateStr.substring(0, 2));
            params.put("validityperiod", validDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Connect.getInstance().post(this, IService.CREDITCARD_ADD, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                promptDialog.dismissImmediately();
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (response.getResult().getCode() == 10000) {
                    ToastUtil.showTextToas(getApplicationContext(), "卡片添加成功");
                    finish();
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", NewAddCreditCardActivity.this,
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
                promptDialog.dismissImmediately();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，银行卡识别

        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {

            BankCardParams param = new BankCardParams();
            param.setImageFile(new File(FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath()));
            OCR.getInstance(this).recognizeBankCard(param, new OnResultListener<BankCardResult>() {

                @Override
                public void onResult(BankCardResult bankCardResult) {
                    if (bankCardResult.getBankCardType().toString().equals("Credit")) {
                        etCardNumber.setText(bankCardResult.getBankCardNumber());
//                        tvCardBank.setText(bankCardResult.getBankName());
                    } else if (bankCardResult.getBankCardType().toString().equals("Debit")) {
                        ToastUtil.showTextToas(getApplicationContext(), "请绑定信用卡");
                        etCardNumber.setText("");
                        tvCardBank.setText("");
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), "未识别出你的卡片,请手动输入或更换卡片");
                        etCardNumber.setText("");
                        tvCardBank.setText("");
                    }
                }

                @Override
                public void onError(OCRError error) {
                    ToastUtil.showTextToas(getApplicationContext(), error.getMessage());
                }
            });
        }
    }
}