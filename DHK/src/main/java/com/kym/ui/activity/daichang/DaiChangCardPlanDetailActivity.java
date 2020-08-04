package com.kym.ui.activity.daichang;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.DaiChangCardAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.DaiChangCardInfo;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

public class DaiChangCardPlanDetailActivity extends BaseActivity implements View.OnClickListener {

    private DaiChangCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_chang_card_plan_detail);
        initView();
        initHead();
        getPreview();
    }

    private void initHead() {
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
        tvTitle.setText("贷偿计划详情");
    }

    private void initView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DaiChangCardAdapter(this, new DaiChangCardAdapter.OnDaiChangCardInfo() {
            @Override
            public void daichangClick(DaiChangCardInfo.DaiChangCardData dachangcardInfo) {

            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 获取空卡代高汇通预览计划
     */
    private void getPreview() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("bill_id", getIntent().getStringExtra("bill_id"));
        Connect.getInstance().post(getApplicationContext(), IService.DC_GET_DETAILS, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                DaiChangCardInfo response = (DaiChangCardInfo) JsonUtils.parse((String) result, DaiChangCardInfo.class);
                dialogUtil.dismiss();
                android.util.Log.d("空卡代还计划列表", result.toString());
                if (response.getResult().getCode() == 10000) {
                    List<DaiChangCardInfo.DaiChangCardData> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                    } else {
                        adapter.clearData();
                    }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }
}
