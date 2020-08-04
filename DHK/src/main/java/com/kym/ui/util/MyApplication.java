package com.kym.ui.util;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.paradigm.botkit.BotKitClient;
import com.squareup.leakcanary.LeakCanary;
import com.kym.ui.activity.fee_kf.HistoryDataManager;
import com.kym.ui.comeActivity;
import com.kym.ui.getui.DemoIntentService;
import com.kym.ui.getui.DemoPushService;


public class MyApplication extends Application {

    private static DemoHandler handler;
    public static comeActivity demoActivity;
    public static StringBuilder payloadData = new StringBuilder();
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        //客服配置
        String accessKey = HistoryDataManager.getInstance().getRecentAccessKey(this);
        BotKitClient.getInstance().enableDebugLog();
        BotKitClient.getInstance().init(this, accessKey);
        instance = this;

        // DemoPushService 为【步骤2.6】自定义的推送服务
        com.igexin.sdk.PushManager.getInstance().initialize(getApplicationContext(), DemoPushService.class);
        // DemoIntentService 为第三方自定义的推送服务事件接收类
        com.igexin.sdk.PushManager.getInstance().registerPushIntentService(getApplicationContext(), DemoIntentService.class);


        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setPlayerFactory(IjkPlayerFactory.create(this))
                .build());

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        if (handler == null) {
            handler = new DemoHandler();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {// 设置字体大小固定
        if (newConfig.fontScale != 1)
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    public static void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    public static class DemoHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (demoActivity != null) {
                        payloadData.append((String) msg.obj);
                        payloadData.append("\n");
                    }
                    break;
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
