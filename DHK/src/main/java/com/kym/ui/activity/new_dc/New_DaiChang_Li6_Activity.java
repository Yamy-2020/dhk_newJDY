package com.kym.ui.activity.new_dc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;

import cn.finalteam.loadingview.PagerSlidingTabStrip;

public class New_DaiChang_Li6_Activity extends FragmentActivity implements View.OnClickListener {

    private final String[] TITLES = {"全部借款", "已申请", "使用中", "已逾期", "已还款"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_chang_plan_record);
        initView();
        initHead();
    }

    private void initView() {
        PagerSlidingTabStrip psts = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        New_DaiChang_Li6_Activity.MyPagerAdapter myPagerAdapter = new New_DaiChang_Li6_Activity.MyPagerAdapter(fm);
        pager.setAdapter(myPagerAdapter);
        psts.setViewPager(pager);
    }

    private void initHead() {
        TextView title = (TextView) findViewById(R.id.head_text_title);
        title.setText("还款");
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
                    return new New_DaiChang_Li6_1Fragment(New_DaiChang_Li6_Activity.this);
                case 1:
                    return new New_DaiChang_Li6_2Fragment(New_DaiChang_Li6_Activity.this);
                case 2:
                    return new New_DaiChang_Li6_3Fragment(New_DaiChang_Li6_Activity.this);
                case 3:
                    return new New_DaiChang_Li6_4Fragment(New_DaiChang_Li6_Activity.this);
                case 4:
                    return new New_DaiChang_Li6_5Fragment(New_DaiChang_Li6_Activity.this);
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
