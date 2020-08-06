package com.kym.ui.appconfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kym.ui.info.LoginInfo_old;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.util.JsonUtils;

import java.lang.reflect.Type;
import java.util.HashMap;

public class SPConfig {

    private static SPConfig instance;
    private SharedPreferences sp;
    private Context context;

    private SPConfig(Context context) {
        super();
        this.context = context;
    }

    public static SPConfig getInstance(Context context) {
        if (instance == null) {
            synchronized (SPConfig.class) {
                if (instance == null) {
                    synchronized (SPConfig.class) {
                        instance = new SPConfig(context);
                    }
                }
            }
        }
        return instance;
    }

    /**
     * 设置是否记住密码
     *
     * @param isKeepPassword
     * @return
     */
    public void setKeepPassword(boolean isKeepPassword) {
        sp = context.getSharedPreferences(Constant.IS_KEEP_PASSWORD, 0);
        sp.edit().putBoolean(Constant.IS_KEEP_PASSWORD, isKeepPassword).commit();
    }

    /**
     * 获得是否记住密码
     */
    public boolean getKeepPassword() {
        sp = context.getSharedPreferences(Constant.IS_KEEP_PASSWORD, 0);
        return sp.getBoolean(Constant.IS_KEEP_PASSWORD, false);
    }

    /**
     * 设置账号信息
     *
     * @param username 用户手机号
     * @param password 密码
     */
    public void setAccountInfo(String username, String password) {
        sp = context.getSharedPreferences(Constant.ACCOUNT_INFO, 0);
        Editor editor = sp.edit();
        editor.putString(Constant.USERNAME, username);
        editor.putString(Constant.PASSWORD, password);
        editor.commit();
    }

    /**
     * 获得账号信息
     *
     * @return
     */
    public HashMap<String, String> getAccountInfo() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        sp = context.getSharedPreferences(Constant.ACCOUNT_INFO, 0);
        hashMap.put(Constant.USERNAME, sp.getString(Constant.USERNAME, ""));
        hashMap.put(Constant.PASSWORD, sp.getString(Constant.PASSWORD, ""));
        return hashMap;
    }

    public void saveUserAllInfo(NewUserResponse.DataBean userInfo) {
        String json = JsonUtils.toJson(userInfo);
        sp = context.getSharedPreferences(Constant.USER_ALL_INFO, 0);
        sp.edit().putString(Constant.USER_ALL_INFO, json).commit();
    }

    /**
     * 获取用户全部信息(新接口)
     */
    public NewUserResponse.DataBean getUserAllInfoNew() {
        sp = context.getSharedPreferences(Constant.USER_ALL_INFO, 0);
        String json = sp.getString(Constant.USER_ALL_INFO, "");
        if (!TextUtils.isEmpty(json)) {
            return (NewUserResponse.DataBean) JsonUtils.parse(json, NewUserResponse.DataBean.class);
        } else return null;
    }

    public void setPush(boolean ispush) {
        sp = context.getSharedPreferences(Constant.IS_Push, 0);
        sp.edit().putBoolean(Constant.IS_Push, ispush).commit();
    }

    public boolean getPush() {
        sp = context.getSharedPreferences(Constant.IS_Push, 0);
        return sp.getBoolean(Constant.IS_Push, false);
    }

    /**
     * 保存用户信息审核状态
     */
    public void setUserInfoStatus(int status) {
        sp = context.getSharedPreferences(Constant.USER_INFO, 0);
        sp.edit().putInt("status", status).commit();
    }

    /**
     * @return 返回用户信息审核状态(-1表示未缓存)
     */
    public int getUserInfoStatus() {
        sp = context.getSharedPreferences(Constant.USER_INFO, 0);

        return sp.getInt("status", -1);
    }

    /**
     * 保存用户信息审核步骤
     */
    public void setUserInfoPercent(int status) {
        sp = context.getSharedPreferences(Constant.USER_INFO, 0);
        sp.edit().putInt("percent", status).commit();
    }

    /**
     * @return 返回用户信息审核步骤(0 - 未进行认证)
     */
    public int getUserInfoPercent() {
        sp = context.getSharedPreferences(Constant.USER_INFO, 0);
        return sp.getInt("percent", -1);
    }

    public LoginInfo_old getUserInfo_new() {
        sp = context.getSharedPreferences(Constant.USER_INFO, 0);
        LoginInfo_old loginInfo = null;
        Type type = new TypeToken<LoginInfo_old>() {
        }.getType();
        Gson gson = new Gson();
        String jsonString = sp.getString(Constant.USER_INFO, "");
        if (!"".equals(jsonString)) {
            loginInfo = gson.fromJson(jsonString, type);
        }
        return loginInfo;
    }

}
