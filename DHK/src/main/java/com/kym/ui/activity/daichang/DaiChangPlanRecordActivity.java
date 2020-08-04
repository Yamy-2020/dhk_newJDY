package com.kym.ui.activity.daichang;

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

public class DaiChangPlanRecordActivity extends FragmentActivity implements View.OnClickListener {

    private final String[] TITLES = {"全部计划", "执行中", "执行成功", "执行失败"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_chang_plan_record);
        StatusBarUtils.setStatusBarLightMode(getWindow());
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_color), 0);
        initView();
        initHead();
    }

    private void initView() {
        PagerSlidingTabStrip psts = findViewById(R.id.tabs);
        ViewPager pager = findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        DaiChangPlanRecordActivity.MyPagerAdapter myPagerAdapter = new DaiChangPlanRecordActivity.MyPagerAdapter(fm);
        pager.setAdapter(myPagerAdapter);
        psts.setViewPager(pager);
    }

    private void initHead() {
        TextView title = (TextView) findViewById(R.id.head_text_title);
        title.setText("贷偿账单");
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
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
                    return new DaiChangPlanRecord1Fragment(DaiChangPlanRecordActivity.this);
                case 1:
                    return new DaiChangPlanRecord2Fragment(DaiChangPlanRecordActivity.this);
                case 2:
                    return new DaiChangPlanRecord3Fragment(DaiChangPlanRecordActivity.this);
                case 3:
                    return new DaiChangPlanRecord4Fragment(DaiChangPlanRecordActivity.this);
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
