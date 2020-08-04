package com.kym.ui.activity.lingqian;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.LingQianAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.LingQianResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;


import java.util.List;

public class LingQianListActivity extends BaseActivity implements View.OnClickListener {

    private LingQianAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ling_qian_list);
        initHead();
        initView();
        getAllList();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("退款明细");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }

    private void initView() {
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.lq_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new LingQianAdapter(this, new LingQianAdapter.OnZDClick() {

            @Override
            public void OnZDClick(LingQianResponse.LingQianInfo zdInfo) {

            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 获取用户的所有信用卡
     */
    private void getAllList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.WODE_LINGQIAN_MINGXI, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                LingQianResponse response = (LingQianResponse) JsonUtils.parse((String) result, LingQianResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<LingQianResponse.LingQianInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                    } else {
                        adapter.clearData();
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

}
