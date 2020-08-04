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

import org.json.JSONObject;

import java.util.HashMap;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;
import static com.kym.ui.appconfig.IService.DZ_KUAIJIE_PAY;
import static com.kym.ui.appconfig.IService.DZ_KUAIJIE_PAY_SUB;
import static com.kym.ui.appconfig.IService.KFT_MSG;
import static com.kym.ui.appconfig.IService.KFT_PAY;
import static com.kym.ui.appconfig.IService.SHOUXINYI_QUEREN_PAY;
import static com.kym.ui.appconfig.IService.SHOUXINYI_QUEREN_PAY1;
import static com.kym.ui.appconfig.IService.SXY_BANGKA;
import static com.kym.ui.appconfig.IService.TFB_MSG;
import static com.kym.ui.appconfig.IService.TFB_PAY;
import static com.kym.ui.appconfig.IService.TFT_MSG;
import static com.kym.ui.appconfig.IService.TFT_PAY;
import static com.kym.ui.appconfig.IService.XS_KUAIJIE_PAY;
import static com.kym.ui.appconfig.IService.XS_KUAIJIE_PAY_SUB;

/**
 * 确认刷卡
 *
 * @author sun
 * @date 2019/12/3
 */

public class KuaiJieMsgPayActivity extends BaseActivity implements View.OnClickListener {

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
        /**
         * type：1-新生。2-快付通。3-快付通高签。4-天付宝 5-腾付通 6-首信易  7-新生N
         */
        if (getIntent().getStringExtra("ID").equals("1")) {
            getMsg("1", DZ_KUAIJIE_PAY);
        } else if (getIntent().getStringExtra("ID").equals("2")) {
            getMsg("2", KFT_MSG);
        } else if (getIntent().getStringExtra("ID").equals("4")) {
            getMsg("4", TFB_MSG);
        } else if (getIntent().getStringExtra("ID").equals("3")) {
            getMsg("5", TFT_MSG);
        } else if (getIntent().getStringExtra("ID").equals("11")) {
            tipView("1", getIntent().getStringExtra("tip"));
        } else if (getIntent().getStringExtra("ID").equals("14")) {
            getMsg("7", XS_KUAIJIE_PAY);
        } else if (getIntent().getStringExtra("ID").equals("16") || getIntent().getStringExtra("ID").equals("18")) {
            tipView("1", getIntent().getStringExtra("statusMsg"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                if (canConfirm()) {
                    if (getIntent().getStringExtra("ID").equals("1")) {
                        subSign("1", DZ_KUAIJIE_PAY_SUB);
                    } else if (getIntent().getStringExtra("ID").equals("2")) {
                        subSign("2", KFT_PAY);
                    } else if (getIntent().getStringExtra("ID").equals("4")) {
                        subSign("4", TFB_PAY);
                    } else if (getIntent().getStringExtra("ID").equals("3")) {
                        subSign("5", TFT_PAY);
                    } else if (getIntent().getStringExtra("ID").equals("11")) {
                        subSign("6", SXY_BANGKA);
                    } else if (getIntent().getStringExtra("ID").equals("14")) {
                        subSign("7", XS_KUAIJIE_PAY_SUB);
                    } else if (getIntent().getStringExtra("ID").equals("16")) {
                        subSign("8", SHOUXINYI_QUEREN_PAY);
                    } else if (getIntent().getStringExtra("ID").equals("18")) {
                        subSign("8", SHOUXINYI_QUEREN_PAY1);
                    }
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
        title.setText("确认刷卡");
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
     * 收款通道获取验证码
     * type：1-新生。2-快付通。3-快付通高签。4-天付宝。5-腾付通
     */
    public void getMsg(String type, String url_name) {
        final Intent intent = getIntent();
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", intent.getStringExtra("ID"));
        params.put("cardid", intent.getStringExtra("cardid"));
        params.put("money", intent.getStringExtra("money"));
        if (type.equals("1") || type.equals("4")) {
            params.put("citycode", getIntent().getStringExtra("citycode"));
            params.put("mcc", getIntent().getStringExtra("mcc"));
        } else if (type.equals("5") || type.equals("2")) {
            params.put("city", getIntent().getStringExtra("city"));
        }

        Connect.getInstance().post(this, url_name, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        String statusName = obj2.getString("statusName");
                        String statusMsg = obj2.getString("statusMsg");
                        if (statusName.equals("sms_sended")) {
                            orderid = obj2.getString("orderid");
                        }
                        tipView("1", statusMsg);
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", KuaiJieMsgPayActivity.this,
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
                        tipView("2", msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("2", message);
            }
        });
    }

    /**
     * 收款通道提交签约信息
     */
    public void subSign(String type, String url_name) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        if (type.equals("1")) {
            params.put("merOrderId", orderid);
            params.put("smsCode", etVerifyCode.getText().toString());
        } else if (type.equals("6")) {
            params.put("orderId", getIntent().getStringExtra("orderid"));
            params.put("requestId", getIntent().getStringExtra("requestId"));
            params.put("smsCode", etVerifyCode.getText().toString());
            params.put("city", getIntent().getStringExtra("city"));
        } else if (type.equals("8")) {
            params.put("requestId", getIntent().getStringExtra("requestId"));
            params.put("smsCode", etVerifyCode.getText().toString());
        } else {
            params.put("orderid", orderid);
            params.put("smscode", etVerifyCode.getText().toString());
        }
        Connect.getInstance().post(this, url_name, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                Result response = (Result) JsonUtils.parse((String) result, Result.class);
                if (response.getResult().getCode() == 10000) {

                    tipView("2", response.getResult().getMsg());

                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", KuaiJieMsgPayActivity.this,
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
        backDialog = new BackDialog("", msg, "确定", KuaiJieMsgPayActivity.this,
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
