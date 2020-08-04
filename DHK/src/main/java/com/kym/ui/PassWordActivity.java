package com.kym.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thl.fingerlib.FingerprintIdentify;
import com.kym.ui.activity.bpbro_base.BaseActivity;

/**
 * 手势指纹
 *
 * @author sun
 * @date 2019/12/3
 */

public class PassWordActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ImageView img1, img2;
    private LinearLayout li2;
    private String isPwd, isFinger;
    private Intent intent;
    private FingerprintIdentify mFingerprintIdentify;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_word);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        mFingerprintIdentify = new FingerprintIdentify(this, null);
        initHead();
        intiView();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("手势指纹设置");
    }

    private void intiView() {
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        li2 = (LinearLayout) findViewById(R.id.li2);
        li2.setOnClickListener(this);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        isPwd = pref.getString("isPwd", "");
        isFinger = pref.getString("isFinger", "");
        if (isPwd.equals("NO")) {
            img1.setImageResource(R.drawable.pwd_off);
            li2.setVisibility(View.GONE);
        } else if (isPwd.equals("YES")) {
            img1.setImageResource(R.drawable.pwd_on);
            li2.setVisibility(View.VISIBLE);
        }

        if (isFinger.equals("NO")) {
            img2.setImageResource(R.drawable.pwd_off);
        } else if (isFinger.equals("YES")) {
            img2.setImageResource(R.drawable.pwd_on);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.img1:
                if (isPwd.equals("NO")) {
                    startActivity(new Intent(PassWordActivity.this, PatternActivity.class));
                    finish();
                } else if (isPwd.equals("YES")) {
                    intent = new Intent(PassWordActivity.this, PatterLoginActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("title", "验证手势密码");
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.li2:
                intent = new Intent(PassWordActivity.this, PatterLoginActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("title", "验证手势密码");
                startActivity(intent);
                finish();
                break;
            case R.id.img2:
                if (mFingerprintIdentify.isHardwareEnable()) {
                    if (!mFingerprintIdentify.isRegisteredFingerprint()) {
                        tipView("1", "请先录入指纹");
                    } else {
                        if (pref.getString("isFinger", "").equals("NO")) {
                            editor = pref.edit();
                            editor.putString("isFinger", "YES");
                            editor.putString("patternPwd", ""); //验证手势密码
                            editor.putString("isPwd", "NO");
                            editor.apply();
                            img2.setImageResource(R.drawable.pwd_on);
                            img1.setImageResource(R.drawable.pwd_off);
                            tipView("1", "指纹开启成功");
                        } else {
                            editor = pref.edit();
                            editor.putString("isFinger", "NO");
                            editor.putString("patternPwd", ""); //验证手势密码
                            editor.putString("isPwd", "NO");
                            editor.apply();
                            img2.setImageResource(R.drawable.pwd_off);
                            tipView("1", "指纹关闭成功");
                        }
                    }
                } else {
                    tipView("1", "手机硬件不支持");
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFingerprintIdentify != null) {
            mFingerprintIdentify.cancelIdentify();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFingerprintIdentify != null) {
            mFingerprintIdentify.resumeIdentify();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFingerprintIdentify != null) {
            mFingerprintIdentify.cancelIdentify();
        }
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", PassWordActivity.this,
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
