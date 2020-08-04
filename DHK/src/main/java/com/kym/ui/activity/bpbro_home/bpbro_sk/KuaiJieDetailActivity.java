package com.kym.ui.activity.bpbro_home.bpbro_sk;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.ZDAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.ZDResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


/**
 * 收款明细
 * @author sun
 * @date 2019/12/3
 */

public class KuaiJieDetailActivity extends BaseActivity implements View.OnClickListener {

    private ZDAdapter zdAdapter;
    private BackDialog backDialog;

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
        tv.setText("收款明细");
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
     * 账单明细列表
     */
    private void getZdList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.ZD_LIST11, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                ZDResponse response = (ZDResponse) JsonUtils.parse((String) result, ZDResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<ZDResponse.ZDInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        zdAdapter.setData(data);
                    } else {
                        zdAdapter.clearData();
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", KuaiJieDetailActivity.this,
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
