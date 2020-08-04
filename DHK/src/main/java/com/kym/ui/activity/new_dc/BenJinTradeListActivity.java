package com.kym.ui.activity.new_dc;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.BenJinTradeListAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.BenJinTradeListResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.List;

/**
 * 本金交易记录
 * Created by Sunmiaolong on 2018/7/29.
 * .
 */

public class BenJinTradeListActivity extends BaseActivity implements View.OnClickListener {

    private BenJinTradeListAdapter zdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zd);
        initHead();
        getZdList();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("本金交易记录");
        RecyclerView mRecyclerView = findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        zdAdapter = new BenJinTradeListAdapter(this, new BenJinTradeListAdapter.OnZDClick() {
            @Override
            public void OnZDClick(BenJinTradeListResponse.ZDInfo zdInfo) {

            }
        });
        mRecyclerView.setAdapter(zdAdapter);
    }

    /**
     * 账单明细列表
     */
    private void getZdList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.BRNJINJILU, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                BenJinTradeListResponse response = (BenJinTradeListResponse) JsonUtils.parse((String) result, BenJinTradeListResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<BenJinTradeListResponse.ZDInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        zdAdapter.setData(data);
                    } else {
                        zdAdapter.clearData();
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(),response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }
}
