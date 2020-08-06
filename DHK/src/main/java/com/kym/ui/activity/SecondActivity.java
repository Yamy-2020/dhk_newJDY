package com.kym.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.NewHomeFragment;
import com.kym.ui.R;
import com.kym.ui.ThreeFrement_new;
import com.kym.ui.activity.rongxinfen.RongXinFenFragment;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.KeXinFenBean;
import com.kym.ui.newutil.SharedPreferencesHelper;
import com.kym.ui.util.Connect;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kym.ui.util.MyDataCleanManager.clearAllCache;

/**
 * 控制器
 *
 * @author sun
 * @date 2019/12/4
 */

public class SecondActivity extends FragmentActivity {
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    @SuppressLint("StaticFieldLeak")
    public static ImageView imageView2;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;

    private SharedPreferencesHelper sp;
    private FragmentManager fm;
    private NewHomeFragment homeFragment; //首页s
    private RongXinFenFragment rongXinFenFragment; //课堂或者可信分
//    private New_DaiChang_Fragment daichangFragment; //贷偿
    private ThreeFrement_new mineFragment; //我的
    private refreshBroadcastReceiver receiver;//刷新的广播
    private long exitTime = 0;
    private int is_shop;
    private KeTangFragment keTangFragment;
    private String[] tabTitles;
    private int[] bitmap;

    @Override
    protected void onResume() {
        super.onResume();
        if (sp.getBoolean("jump2market")) {
            llTab.getChildAt(1).performClick();
            sp.remove("jump2market");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()是获取当前时间，返回的是毫秒
            {
                ToastUtil.showTextToas(getApplicationContext(), "再按一次退出" + Clone.APP_NAME);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ifshop();
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        activity = this;
        sp = new SharedPreferencesHelper(SecondActivity.this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void initView() {
        fm = getSupportFragmentManager();
        addTab3();
        llTab.getChildAt(0).performClick();
        clearAllCache(getApplicationContext());
    }

    private void addTab3() {
        LayoutInflater inflater = getLayoutInflater();


        if (is_shop==0){
            tabTitles = new String[]{"首页", "可信分", "我的"};
            bitmap = new int[]{R.drawable.icon_shoukuan_selector,
                    R.drawable.icon_rongxinfen_selector,
                    R.drawable.icon_tongji_selector};
        }else if(is_shop==1){

            tabTitles = new String[]{"首页", "小课堂", "我的"};
            bitmap = new int[]{R.drawable.icon_shoukuan_selector,
                    R.drawable.icon_ketang_selector,
                    R.drawable.icon_tongji_selector};
        }
        for (int i = 0; i < 3; i++) {
            @SuppressLint("InflateParams") final View tab = inflater.inflate(R.layout.tm_tab_btn, null);
            ImageView imageView = tab.findViewById(R.id.tab_icon);
            imageView2 = tab.findViewById(R.id.tab_icon_hint);
            TextView textView = tab.findViewById(R.id.tab_icon_titles);

            Log.e("22222222222222", "addTab3: 11111111"+is_shop );

            if (is_shop==0){
                textView.setText(tabTitles[i]);
                imageView.setBackgroundResource(bitmap[i]);
            }else if(is_shop==1){
                textView.setText(tabTitles[i]);
                imageView.setBackgroundResource(bitmap[i]);
            }

            final int finalI = i;
            tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = fm.beginTransaction();
                    switch (finalI) {
                        case 0:
                            resetFragment(transaction);
                            if (homeFragment == null) {
                                homeFragment = new NewHomeFragment();
                                transaction.add(R.id.container_second, homeFragment).commit();
                            } else {
                                transaction.show(homeFragment).commit();
                            }
                            resetTab();
                            tab.setSelected(true);
                            break;
                 /*       case 1:
                            resetFragment(transaction);
                            if (daichangFragment == null) {
                                daichangFragment = new New_DaiChang_Fragment();
                                transaction.add(R.id.container_second, daichangFragment).commit();
                            } else {
                                transaction.show(daichangFragment).commit();
                            }
                            resetTab();
                            tab.setSelected(true);
                            break;*/
                        case 1:
                            resetFragment(transaction);

                            if (is_shop==0){

                                if (rongXinFenFragment == null) {
                                    rongXinFenFragment = new RongXinFenFragment();
                                    transaction.add(R.id.container_second, rongXinFenFragment).commit();
                                } else {
                                    transaction.show(rongXinFenFragment).commit();
                                }
                                resetTab();
                                tab.setSelected(true);
                            }else if (is_shop==1){
                                if (keTangFragment == null) {
                                keTangFragment = new KeTangFragment();
                                transaction.add(R.id.container_second, keTangFragment).commit();
                            } else {
                            transaction.show(keTangFragment).commit();
                        }
                                resetTab();
                                tab.setSelected(true);
                            }

                            resetTab();
                            tab.setSelected(true);
                            break;
                        case 2:
                            resetFragment(transaction);
                            if (mineFragment == null) {
                                mineFragment = new ThreeFrement_new();
                                transaction.add(R.id.container_second, mineFragment).commit();
                            } else {
                                transaction.show(mineFragment).commit();
                            }
                            resetTab();
                            tab.setSelected(true);
                            break;
                    }
                }
            });
            llTab.addView(tab, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        }
    }


    Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //do something,refresh UI;
                    initView();
                    registerBroadCast();

                    break;
                default:
                    break;
            }
        }

    };

    protected void ifshop() {
        HashMap<String, String> params = new HashMap<>();
        params.put("version",LoginActivity.VERSION);
        params.put("terminal","android");
        Connect.getInstance().post(this, IService.KEXINFEN_SHOP, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                KeXinFenBean shop = (KeXinFenBean) JsonUtils.parse((String) result, KeXinFenBean.class);
                if (shop.getResult().getCode() == 10000) {
                    is_shop = shop.getData().getShop();
                    Log.e("is_shop ==== ", "onSuccess: " + is_shop);
                    mHandler.sendEmptyMessage(0);



                } /*else {
                    ToastUtil.showTextToas(getContext(), "请联系客服");
                }*/
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
    private void resetTab() {
        for (int i = 0; i < llTab.getChildCount(); i++) {
            llTab.getChildAt(i).setSelected(false);
        }
    }

    private void resetFragment(FragmentTransaction transaction) {
        @SuppressLint("RestrictedApi")
        List<Fragment> fragments = fm.getFragments();
        for (Fragment fragment : fragments) {
            transaction.hide(fragment);
        }
    }

    private void registerBroadCast() {
        receiver = new refreshBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("refresh");
        registerReceiver(receiver, filter);
    }

    private class refreshBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(Clone.APP_NAME, "广播通知刷新");
        }
    }
}