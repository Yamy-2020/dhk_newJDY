package com.kym.ui.activity.bpbro_safe;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.RegisterActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

public class RegisterActivity_x extends BaseActivity {

    @BindView(R.id.head_text_title)
    TextView headTextTitle;
    @BindView(R.id.head_img_left)
    ImageView headImgLeft;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.message)
    EditText message;
    @BindView(R.id.msg_btn)
    TextView msgBtn;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.repassword)
    EditText repassword;
    @BindView(R.id.submit)
    TextView submit;
    private int flag; // 标记是忘记密码还是注册 0注册 1忘记密码 2修改密码
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            msgBtn.setText(String.format("重新获取(%ds)", l / 1000));
        }

        @Override
        public void onFinish() {
            msgBtn.setText("获取验证码");
            msgBtn.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_x);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }

    private void initView() {
        flag = getIntent().getIntExtra("register", 0);
        headTextTitle.setText("修改密码");
    }

    @OnClick({R.id.head_img_left, R.id.msg_btn, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.msg_btn:
                if (TextUtils.isEmpty(mobile.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入注册手机号");
                } else {
                    getIdentifyingCode();
                }
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(mobile.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入注册手机号");
                } else if (TextUtils.isEmpty(message.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入短信验证码");
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入6-18密码");
                } else if (TextUtils.isEmpty(repassword.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请再次输入密码");
                } else {
                    forgetPassword();
                }
                break;
        }
    }

    /**
     * 重置密码
     */
    private void forgetPassword() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile.getText().toString());
        params.put("password", password.getText().toString());
        params.put("auth_code", message.getText().toString());
        Connect.getInstance().post(this, IService.RESET_PASSWORD, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (response.getResult().getCode() == 10000) {
                    if (flag == 1) {
                     /*   ToastUtil.showTextToas(getApplicationContext(), "重置密码成功,即将退出");

                        restartApp(getApplicationContext());*/
                     startActivity(new Intent(RegisterActivity_x.this, LoginActivity.class));

                    } else {
                        startActivity(new Intent(RegisterActivity_x.this, LoginActivity.class));

                      /*  restartApp(getApplicationContext());
                        ToastUtil.showTextToas(getApplicationContext(), "修改密码成功");*/
                    }
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

    /**
     * 获取验证码
     */
    private void getIdentifyingCode() {
        msgBtn.setEnabled(false);
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile.getText().toString());
        params.put("type", "2");
        Connect.getInstance().post(this, IService.AUTHCODE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (response.getResult().getCode() == 10000) {
                    ToastUtil.showTextToas(getApplicationContext(), "验证码已发送");
                    startCountDown();
                } else {
                    msgBtn.setEnabled(false);
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                msgBtn.setEnabled(false);
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 计时器
     */
    private void startCountDown() {
        timer.start();
    }
}