package com.kym.ui.activity.shiming;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.ShaiXunActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.daichang.DaiChangCityActivity;
import com.kym.ui.activity.daichang.DaiChangRongHuiWebActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.fragment.ShouYi_Show_Fragment;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.info.RequestParam;
import com.kym.ui.info.UploadFile;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.model.Result;
import com.kym.ui.shaixun.ShaiXun1Fragment;
import com.kym.ui.shaixun.ShaiXun2Fragment;
import com.kym.ui.shaixun.ShaiXun3Fragment;
import com.kym.ui.shaixun.ShaiXun4Fragment;
import com.kym.ui.shaixun.ShaiXun5Fragment;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.FileCache;
import com.kym.ui.util.JsonUtils;
import com.kym.ui.util.PerMisson;
import com.kym.ui.util.UpLoadImage;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cn.finalteam.loadingview.PagerSlidingTabStrip;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


/**
 * 易宝证件照
 *
 * @author sun
 * @date 2020/1/14
 */

public class ZhengJianActivity extends FragmentActivity implements View.OnClickListener {
//    private final String[] TITLES = {"收款收益", "还款收益", "智收收益", "升级"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_feng_xiang);
//        PagerSlidingTabStrip psts = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        TextView tv = findViewById(R.id.head_text_title);
//        findViewById(R.id.head_img_left).setOnClickListener(this);
//        tv.setText("分润账户");
//        FragmentManager fm = getSupportFragmentManager();

      /*  ZhengJianActivity.MyPagerAdapter myPagerAdapter = new ZhengJianActivity.MyPagerAdapter(fm);
        pager.setAdapter(myPagerAdapter);
        psts.setViewPager(pager);*/
    }
   /* public class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ShouYi_Show_Fragment(ZhengJianActivity.this);
                case 1:
                    return new ShouYi_Show_Fragment(ZhengJianActivity.this);
                case 2:
                    return new ShouYi_Show_Fragment(ZhengJianActivity.this);
                case 3:
                    return new ShouYi_Show_Fragment(ZhengJianActivity.this);
                case 4:
                    return new ShouYi_Show_Fragment(ZhengJianActivity.this);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;

            default:
                break;
        }
    }

}
