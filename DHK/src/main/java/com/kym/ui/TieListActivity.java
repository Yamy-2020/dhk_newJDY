package com.kym.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.TieAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.TieResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

/**
 * 提额明细
 *
 * @author sun
 * @date 2019/12/3
 */

public class TieListActivity extends BaseActivity implements View.OnClickListener {

    private TieAdapter tieAdapter;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tie_list);
        initHead();
        getZdList();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("提额明细");
        RecyclerView mRecyclerView = findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tieAdapter = new TieAdapter(this, new TieAdapter.OnZDClick() {

            @Override
            public void OnZDClick(TieResponse.TieInfo zdInfo) {

            }
        });
        mRecyclerView.setAdapter(tieAdapter);
    }

    /**
     * 账单明细列表
     */
    private void getZdList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("NCardId"));
        Connect.getInstance().post(getApplicationContext(), IService.EDUMINGXI, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                TieResponse response = (TieResponse) JsonUtils.parse((String) result, TieResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<TieResponse.TieInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        tieAdapter.setData(data);
                    } else {
                        tieAdapter.clearData();
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", TieListActivity.this,
                            R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(TieListActivity.this, LoginActivity.class));

//                            restartApp(getApplicationContext());
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
