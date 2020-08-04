package com.kym.ui.activity.tgsucai;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.adapter.TuiGuangTextAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.TuiGuangSuCaiResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.List;

/**
 * 推广文案
 *
 * @author sun
 * @date 2019/12/3
 */
public class TuiGuangWenAnActivity extends BaseActivity implements View.OnClickListener {

    private TuiGuangTextAdapter adapter;
    private LinearLayout zanwu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang_su_cai);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getText();
    }

    private void initView() {
        zanwu = (LinearLayout) findViewById(R.id.zanwu);
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        tvTitle.setText("推广文案");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TuiGuangTextAdapter(this, new TuiGuangTextAdapter.OnKuaiJieInfo() {

            @Override
            public void kuaijieClick(TuiGuangSuCaiResponse.TuiGuangInfo tuiGuangInfo) {

            }

        });
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }

    private void getText() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.TUIGUANGWENAN_SUCAI, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                TuiGuangSuCaiResponse response = (TuiGuangSuCaiResponse) JsonUtils.parse((String) result, TuiGuangSuCaiResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<TuiGuangSuCaiResponse.TuiGuangInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                    } else {
                        adapter.clearData();
                    }
                    zanwu.setVisibility(View.GONE);
                } else if (response.getResult().getCode() == 99999) {
                    zanwu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
            }
        });
    }

}
