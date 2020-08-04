package com.kym.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.kym.ui.shaixun.ShaiXun1Fragment;
import com.kym.ui.shaixun.ShaiXun2Fragment;
import com.kym.ui.shaixun.ShaiXun3Fragment;
import com.kym.ui.shaixun.ShaiXun4Fragment;
import com.kym.ui.shaixun.ShaiXun5Fragment;

import cn.finalteam.loadingview.PagerSlidingTabStrip;

/**
 * 我的商户详情页
 */

public class ShaiXunActivity extends FragmentActivity implements OnClickListener {

    private final String[] TITLES = {"全部", "未实名", "待审核", "未通过", "已实名"};
    private String stringTitle;
    private String lid;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shai_xun);
        Intent intent = getIntent();
        stringTitle = intent.getStringExtra("name");
        lid = intent.getStringExtra("lid");
        imageUrl = intent.getStringExtra("head_img");
        initTttle();
        PagerSlidingTabStrip psts = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fm);
        pager.setAdapter(myPagerAdapter);
        psts.setViewPager(pager);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

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
                    return new ShaiXun1Fragment(ShaiXunActivity.this, lid, stringTitle, imageUrl);
                case 1:
                    return new ShaiXun2Fragment(ShaiXunActivity.this, lid, stringTitle, imageUrl);
                case 2:
                    return new ShaiXun3Fragment(ShaiXunActivity.this, lid, stringTitle, imageUrl);
                case 3:
                    return new ShaiXun4Fragment(ShaiXunActivity.this, lid, stringTitle, imageUrl);
                case 4:
                    return new ShaiXun5Fragment(ShaiXunActivity.this, lid, stringTitle, imageUrl);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }

    @SuppressLint("SetTextI18n")
    private void initTttle() {
        TextView title = (TextView) findViewById(R.id.head_text_title);
        if (null != stringTitle) {
            title.setText(stringTitle + "");
        } else {
            title.setText("员工");
        }
        findViewById(R.id.head_img_left).setOnClickListener(this);
    }

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
