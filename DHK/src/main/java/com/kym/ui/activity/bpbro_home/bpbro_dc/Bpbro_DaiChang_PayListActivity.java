package com.kym.ui.activity.bpbro_home.bpbro_dc;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.BpbroPaylistAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.PayListResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


public class Bpbro_DaiChang_PayListActivity extends BaseActivity implements View.OnClickListener {

    private BpbroPaylistAdapter zdAdapter;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpbro__dai_chang__pay_list);
        initHead();
        getList();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("权益记录");
        RecyclerView mRecyclerView = findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        zdAdapter = new BpbroPaylistAdapter(this, new BpbroPaylistAdapter.OnZDClick() {

            @Override
            public void OnZDClick(PayListResponse.ZDInfo zdInfo) {

            }
        });
        mRecyclerView.setAdapter(zdAdapter);
    }

    /**
     * 购买记录
     */
    private void getList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.GOUMAILIST, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                PayListResponse response = (PayListResponse) JsonUtils.parse((String) result, PayListResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<PayListResponse.ZDInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        zdAdapter.setData(data);
                    } else {
                        zdAdapter.clearData();
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", Bpbro_DaiChang_PayListActivity.this,
                            R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            restartApp(getApplicationContext());
                            backDialog.dismiss();
                        }
                    });
                    backDialog.setCancelable(false);
                    backDialog.show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }
}
