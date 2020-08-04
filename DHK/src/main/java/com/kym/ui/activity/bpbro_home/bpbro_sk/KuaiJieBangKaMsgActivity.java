package com.kym.ui.activity.bpbro_home.bpbro_sk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.info.KuaiJieCardList;
import com.kym.ui.model.Result;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;
import static com.kym.ui.appconfig.IService.SHOUXINYI_QUEREN_SIGN;

/**
 * 绑卡验证码
 *
 * @author sun
 * @date 2019/12/3
 */

public class KuaiJieBangKaMsgActivity extends BaseActivity implements View.OnClickListener {

    private EditText etVerifyCode;
    private KuaiJieCardList.KuaiJieCardListInfo kuaiJieInfo;
    private String orderid;
    private BackDialog backDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dongzai_qianyue);
        kuaiJieInfo = (KuaiJieCardList.KuaiJieCardListInfo) getIntent().getSerializableExtra("data");
        initHead();
        initUI();
        tipView("1", getIntent().getStringExtra("statusMsg"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                if (canConfirm()) {
                    subSign();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initHead() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = findViewById(R.id.head_text_title);
        title.setText("确认绑卡");
    }

    private void initUI() {
        etVerifyCode = findViewById(R.id.et_new_add_verify_code);
        CircleImageView ivBankLogo = findViewById(R.id.iv_bank_sign);
        TextView tvBankName = findViewById(R.id.tv_bank_sign_card_name);
        TextView tvBankNumber = findViewById(R.id.tv_bank_sign_card_number);
        findViewById(R.id.change_card).setOnClickListener(this);
        Glide.with(this).load(kuaiJieInfo.getLogo_url()).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(kuaiJieInfo.getBank_name());
        tvBankNumber.setText(kuaiJieInfo.getBank_no().substring(0, 4) + " **** **** " + kuaiJieInfo.getBank_no().substring(kuaiJieInfo.getBank_no().length() - 4, kuaiJieInfo.getBank_no().length()));
    }

    private boolean canConfirm() {
        if (TextUtils.isEmpty(etVerifyCode.getText().toString())) {
            tipView("1", "验证码不能为空");
            return false;
        }
        return true;
    }

    /**
     * 收款通道提交签约信息
     */
    public void subSign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("requestId", getIntent().getStringExtra("requestId"));
        params.put("smsCode", etVerifyCode.getText().toString());
        Connect.getInstance().post(this, SHOUXINYI_QUEREN_SIGN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                Result response = (Result) JsonUtils.parse((String) result, Result.class);
                if (response.getResult().getCode() == 10000) {
                    Intent intent = new Intent(getApplicationContext(), KuaiJieTFTShanghuActivity.class);
                    intent.putExtra("data", kuaiJieInfo);
                    intent.putExtra("ID", getIntent().getStringExtra("ID"));
                    intent.putExtra("cardid", getIntent().getStringExtra("cardid"));
                    intent.putExtra("money", getIntent().getStringExtra("money"));
                    startActivity(intent);
                    finish();
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", KuaiJieBangKaMsgActivity.this,
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
                    tipView("2", response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("2", message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", KuaiJieBangKaMsgActivity.this,
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
