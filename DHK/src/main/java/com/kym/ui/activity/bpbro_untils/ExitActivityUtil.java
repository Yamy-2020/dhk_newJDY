package com.kym.ui.activity.bpbro_untils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;


/**
 * 该类用于完全退出整个App
 *
 * @author sun
 * @date 2019/12/3 3:15 PM
 */
public class ExitActivityUtil extends Application {
    List<Activity> activityList = new LinkedList();//Activity 容器
    private static ExitActivityUtil instance;

    private ExitActivityUtil() {
    }

    /**
     * 单例，获取唯一的ExitActivity 实例
     */
    public static ExitActivityUtil getInstance() {
        if (null == instance) {
            instance = new ExitActivityUtil();
        }
        return instance;
    }

    /**
     * 添加Activity 到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 遍历所有Activity 并退出
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}