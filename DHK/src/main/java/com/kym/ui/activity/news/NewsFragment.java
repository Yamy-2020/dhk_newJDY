package com.kym.ui.activity.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;

import cn.finalteam.loadingview.PagerSlidingTabStrip;


public class NewsFragment extends Fragment {
    private final String[] TITLES = { "交易消息","个人消息","系统消息" };
    private View rootView;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_newsx, null);
            initTttle();
            PagerSlidingTabStrip psts = rootView.findViewById(R.id.tabs);
            ViewPager pager = rootView.findViewById(R.id.pager);
            FragmentManager fm = getChildFragmentManager();
            MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fm);
            pager.setAdapter(myPagerAdapter);
            psts.setViewPager(pager);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initTttle() {
        rootView.findViewById(R.id.head_img_left).setVisibility(View.GONE);
        TextView textV_title = rootView.findViewById(R.id.head_text_title);
        textV_title.setText("消息");
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
