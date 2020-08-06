package com.kym.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.util.Connect;
import com.kym.ui.util.Connect.OnResponseListener;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;

/**
 * 忘记密码
 *
 * @author sun
 * @date 2020/1/15
 */

public class RegisterActivity extends BaseActivity implements OnClickListener {

    private EditText username_et;
    private EditText confirmation_et;
    private EditText password_et;
    private TextView get_confirmation_tv;
    private Context mContext;
    private int flag;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mContext = this;
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 初始化
     */
    private void initView() {
        TextView text_title = findViewById(R.id.head_text_title);
        username_et = findViewById(R.id.username_et); // 注册的手机号
        confirmation_et = findViewById(R.id.confirmation_et); // 验证码
        get_confirmation_tv = findViewById(R.id.confirmation_code_tv); // 获取验证码
        get_confirmation_tv.setOnClickListener(this);
        password_et = findViewById(R.id.password_et); // 密码
        findViewById(R.id.next_bt).setOnClickListener(this);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.text_forget_dl).setOnClickListener(this);
        flag = getIntent().getIntExtra("register", 0);
        text_title.setText("忘记密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_bt:
                forgetPassword(); // 忘记密码
                break;
            // 获取验证码
            case R.id.confirmation_code_tv:
                getIdentifyingCode();
                break;
            // 返回
            case R.id.head_img_left:
                finish();
                break;
            // 返回
            case R.id.text_forget_dl:
                finish();
                break;

            default:
                break;
        }
    }

    /**
     * 重置密码
     */
    private void forgetPassword() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", username_et.getText().toString());
        params.put("password", password_et.getText().toString());
        params.put("auth_code", confirmation_et.getText().toString());
        Connect.getInstance().post(this, IService.RESET_PASSWORD, params, new OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (response.getResult().getCode() == 10000) {
                    if (flag == 1) {
                        ToastUtil.showTextToas(mContext, "重置密码成功");
                    } else {
                        ToastUtil.showTextToas(mContext, "修改密码成功");
                    }
                    finish();
                } else {
                    ToastUtil.showTextToas(mContext, response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(mContext, message);
            }
        });
    }


    /**
     * 获取验证码
     */
    private void getIdentifyingCode() {
        get_confirmation_tv.setEnabled(false);
        String phone = username_et.getText().toString();
        if (!TextUtils.isEmpty(phone)) {
            HashMap<String, String> params = new HashMap<>();
            params.put("mobile", phone);
            params.put("type", "2");
            final DialogUtil dialogUtil = new DialogUtil(this);
            Connect.getInstance().post(this, IService.AUTHCODE, params, new OnResponseListener() {
                @Override
                public void onSuccess(Object result) {
                    dialogUtil.dismiss();
                    YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                    if (response.getResult().getCode() == 10000) {
                        ToastUtil.showTextToas(mContext, "验证码已发送");
                        startCountDown();
                    } else {
                        get_confirmation_tv.setEnabled(true);
                        ToastUtil.showTextToas(mContext, response.getResult().getMsg());
                    }
                }

                @Override
                public void onFailure(String message) {
                    dialogUtil.dismiss();
                    get_confirmation_tv.setEnabled(true);
                    ToastUtil.showTextToas(getApplicationContext(), message);
                }
            });
        } else {
            ToastUtil.showTextToas(mContext, "请填写手机号码");
        }
    }

    /**
     * 计时器
     */
    private void startCountDown() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                get_confirmation_tv.setText(String.format("重新获取(%ds)", l / 1000));
            }

            @Override
            public void onFinish() {
                get_confirmation_tv.setText("获取验证码");
                get_confirmation_tv.setEnabled(true);
            }
        };
        timer.start();
    }

}