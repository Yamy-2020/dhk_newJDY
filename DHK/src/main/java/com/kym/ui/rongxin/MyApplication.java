package com.kym.ui.rongxin;

import android.app.Application;

import com.lzy.okgo.OkGo;


public class MyApplication extends Application {
    	public static final String URL = "http://apitest.juhexinyong.com/apiv58/user/usersDatas/";
//	public static final String SIGN_KEY = "DDCE8F2TR3H1975D0DE69BCE5CF051A1";
//    public static final String URL = "http://api.juhexinyong.com/apiv58/user/usersDatas/";
    public static final String SIGN_KEY = "DD2DF2SDCCS193KJ50D983JHDYR982NM";

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
    }
}
