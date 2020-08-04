package com.kym.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jianyuyouhun.fingerprintscan.library.FingerprintScanHelper;
import com.jianyuyouhun.fingerprintscan.library.OnAuthResultListener;
import com.kym.ui.activity.bpbro_base.BaseActivity;

public class FingerActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        findViewById(R.id.finger).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finger:
                new FingerprintScanHelper(FingerActivity.this)
                        .startAuth(new OnAuthResultListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(FingerActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onInputPwd(String pwd) {
                                Toast.makeText(FingerActivity.this, "密码" + pwd, Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailed(String msg) {
                                Toast.makeText(FingerActivity.this, "失败" + msg, Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onDeviceNotSupport() {
                                Toast.makeText(FingerActivity.this, "手机设备不支持", Toast.LENGTH_SHORT).show();

                            }
                        }, true, true);
                break;
        }
    }
}
