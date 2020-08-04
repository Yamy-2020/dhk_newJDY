package com.kym.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.leon.commons.util.ApplicationUtil;
import com.kym.ui.activity.WelcomeActivity;
import com.kym.ui.util.MyApplication;
import com.kym.ui.when_page.PageActivity;

/**
 * 启动页
 *
 * @author sun
 * @date 2019/12/30
 */
public class comeActivity extends Activity {
    public static final int SHARE_APP_TAG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int versionCode = ApplicationUtil.getLocalVersionCode(this);
        SharedPreferences setting = getSharedPreferences(String.valueOf(SHARE_APP_TAG), 0);
        String vn = setting.getString("versionCode", "11");
        //判断版本号是否一致，一致的话2秒后进入主页面，否则进入引导页
        if (String.valueOf(versionCode).equals(vn)) {
            Intent intent = new Intent(comeActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor edit = setting.edit();
            edit.putString("versionCode", String.valueOf(versionCode));
            edit.commit();
            setting.edit().putBoolean("PAGE", false).commit();
            Intent intent = new Intent(comeActivity.this, PageActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onDestroy() {
        MyApplication.payloadData.delete(0, MyApplication.payloadData.length());
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
