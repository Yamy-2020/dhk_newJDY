package com.kym.ui.getui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.igexin.sdk.PushManager;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.util.MyApplication;


/**
 * PushManager 为对外接口, 所有调用均是通过 PushManager.getInstance() 日志过滤器输入 PushManager, 在接口调用失败会有对应 Error 级别
 * log 提示.
 */
public class GetuiSdkDemoActivity extends BaseActivity {

    private static final String TAG = "GetuiSdkDemo";
    private static final int REQUEST_PERMISSION = 0;

    // DemoPushService.class 自定义服务名称, 核心服务
    private Class userPushService = DemoPushService.class;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        MyApplication.demoActivity = this;

        Log.d(TAG, "initializing sdk...");

        PackageManager pkgManager = getPackageManager();

        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            requestPermission();
        } else {
            PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
        }
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                REQUEST_PERMISSION);
    }

    @Override
    public void onDestroy() {
        Log.d("GetuiSdkDemo", "onDestroy()");
        MyApplication.payloadData.delete(0, MyApplication.payloadData.length());
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.d("GetuiSdkDemo", "onStop()");
        super.onStop();
    }

}
