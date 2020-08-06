package com.kym.ui.activity.bpbro_untils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.kym.ui.activity.SecondActivity;

/**
 * @author sun
 * @date 2019/12/30 3:36 PM
 */
public class bpbro_untils {
    /**
     * 重启app
     */
    public static void restartApp(Context context) {
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 900, restartIntent); // 1秒钟后重启应用

        SecondActivity.activity.finish();
//            BpbroTabActivity.activity.finish();

        ExitActivityUtil.getInstance().exit();
        System.exit(0);
    }
}
