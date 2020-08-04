package com.kym.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kym.ui.fragment.NewHomeTopFragment;
import com.kym.ui.info.BankListResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zachary on 2018/2/1.
 */

public class NewHomeTopAdapter extends FragmentPagerAdapter {

    private ArrayList<BankListResponse.BankInfo> models = new ArrayList<>();

    public NewHomeTopAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewHomeTopFragment.newInstance(models.get(position));
    }

    @Override
    public int getCount() {
        return models.size();
    }

    public void setData(List<BankListResponse.BankInfo> plans) {
        models.clear();
        models.addAll(plans);
        notifyDataSetChanged();
    }
}