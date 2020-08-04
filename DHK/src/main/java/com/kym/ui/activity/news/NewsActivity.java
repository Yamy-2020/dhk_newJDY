package com.kym.ui.activity.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_untils.StatusBarUtils;

import cn.finalteam.loadingview.PagerSlidingTabStrip;

/**
 * 我的消息
 *
 * @author sun
 * @date 2019/12/3
 */

public class NewsActivity extends FragmentActivity {
    private final String[] TITLES = {"交易消息", "个人消息", "系统消息"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        StatusBarUtils.setStatusBarLightMode(getWindow());
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_color), 0);
        initTttle();
        PagerSlidingTabStrip psts = findViewById(R.id.tabs);
        ViewPager pager = findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fm);
        pager.setAdapter(myPagerAdapter);
        psts.setViewPager(pager);
    }

    private void initTttle() {
        findViewById(R.id.head_img_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView textV_title = findViewById(R.id.head_text_title);
        textV_title.setText("我的消息");
        findViewById(R.id.gone).setVisibility(View.GONE);
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
                    return new TradeNewsFragment();
                case 1:
                    return new PersonNewsFragment();
                case 2:
                    return new SystemNewsFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }
}

