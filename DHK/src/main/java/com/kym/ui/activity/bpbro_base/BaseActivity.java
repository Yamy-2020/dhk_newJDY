package com.kym.ui.activity.bpbro_base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.kym.ui.activity.bpbro_untils.ExitActivityUtil;
import com.jaeger.library.StatusBarUtil;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_untils.StatusBarUtils;

import butterknife.ButterKnife;

/**
 * Activity基类
 *
 * @author sun
 * @date 2019/11/28
 */

public class BaseActivity extends Activity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        ExitActivityUtil.getInstance().addActivity(this);
        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtils.setStatusBarLightMode(getWindow());
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_color), 0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //强制横屏，不允许横屏旋转
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//强制横屏，允许横屏旋转
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch (NullPointerException e) {
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}
