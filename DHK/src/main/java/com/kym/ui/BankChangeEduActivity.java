package com.kym.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;


/**
 * 信用卡修改额度
 *
 * @author sun
 * @date 2019/12/3
 */

public class BankChangeEduActivity extends BaseActivity implements View.OnClickListener {

    private EditText money;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_change_ed);
        initUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.tv_change_bank_confirm:
                // 修改信息
                if (canConfirm()) {
                    confirm();
                }
                break;
            default:
                break;
        }
    }

    private void initUI() {
        TextView title = (TextView) findViewById(R.id.head_text_title);
        TextView tvCardName = (TextView) findViewById(R.id.tv_change_bank_card);
        CircleImageView ivBankLogo = (CircleImageView) findViewById(R.id.iv_change_bank_card);
        TextView tvBankNumber = (TextView) findViewById(R.id.tv_change_bank_card_number);
        money = (EditText) findViewById(R.id.money);
        findViewById(R.id.tv_change_bank_confirm).setOnClickListener(this);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        title.setText("信用卡修改额度");
        tvCardName.setText(getIntent().getStringExtra("NBankName"));
        Glide.with(this).load(getIntent().getStringExtra("NLogoUrl")).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankNumber.setText(getIntent().getStringExtra("NBankNo").substring(0, 4)
                + " **** **** " + getIntent().getStringExtra("NBankNo").substring(getIntent().getStringExtra("NBankNo").length() - 4, getIntent().getStringExtra("NBankNo").length()));
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                int len = s.toString().length();
                if (len > 1 && text.startsWith("0")) {
                    s.replace(0, 1, "");
                }
            }
        });
    }

    private boolean canConfirm() {
        if (TextUtils.isEmpty(money.getText().toString())) {
            tipView("1", "请输入最新额度");
            return false;
        } else if (money.getText().toString().equals("0")) {
            tipView("1", "请输入正确的额度");
            return false;
        }
        return true;
    }

    /**
     * 修改信用卡信息
     */
    private void confirm() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("NCardId"));
        params.put("balance", money.getText().toString());
        Connect.getInstance().post(getApplicationContext(), IService.UPDATEEDU, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old info = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (info.getResult().getCode() == 10000) {
                    tipView("2", "修改成功");
                } else {
                    tipView("1", info.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1", message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", BankChangeEduActivity.this,
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