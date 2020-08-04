package com.kym.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.adapter.ZDAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.ZDResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.List;


public class ZDActivity extends BaseActivity implements View.OnClickListener {
    private ZDAdapter zdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zd);
        initHead();
        getZdList();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("门店码账单明细");

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        zdAdapter = new ZDAdapter(this, new ZDAdapter.OnZDClick() {
            @Override
            public void OnZDClick(ZDResponse.ZDInfo zdInfo) {

            }
        });
        mRecyclerView.setAdapter(zdAdapter);
    }
    /**
     * 账单明细
     */
    private void getZdList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.ZD_LIST, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                android.util.Log.d("银行列表",result.toString());
                dialogUtil.dismiss();
                ZDResponse response = (ZDResponse) JsonUtils.parse((String) result, ZDResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<ZDResponse.ZDInfo> data = response.getData();
                    android.util.Log.d("银行列表data",data.toString());
                    if (data != null && data.size() > 0) {
                        zdAdapter.setData(data);
                    } else {
                        zdAdapter.clearData();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.getResult().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
