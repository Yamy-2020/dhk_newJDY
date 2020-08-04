package com.kym.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;

public class ShareWenAn_Activity extends BaseActivity {

    private RecyclerView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_wen_an_);
        initView();
    }

    private void initView() {

        mRecycleView = (RecyclerView) findViewById(R.id.rv_bank_list);
//
//        myAdapter = new MyAdapter(this, mData);
//        mRecycleView.setAdapter(myAdapter);//设置适配器


        //设置布局管理器 , 将布局设置成纵向
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(linerLayoutManager);

        //设置分隔线
        //mRecycleView.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST));

        //设置增加或删除条目动画
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

    }


}