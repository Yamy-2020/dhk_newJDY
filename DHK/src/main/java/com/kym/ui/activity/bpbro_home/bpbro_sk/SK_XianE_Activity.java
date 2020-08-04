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
import com.kym.ui.adapter.SK_XianEAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.KuaiJieResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


/**
 * 限额列表
 *
 * @author sun
 * @date 2019/12/3
 */

public class SK_XianE_Activity extends BaseActivity implements View.OnClickListener {

    private SK_XianEAdapter adapter;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiane);
        initHead();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUI();
        getShouKuanList();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("限额列表");
    }

    private void initUI() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SK_XianEAdapter(this, new SK_XianEAdapter.OnKuaiJieInfo() {
            @Override
            public void kuaijieClick(KuaiJieResponse.KuaiJieInfo kuaiJieInfo) {

            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 获取快捷收款通道列表
     */
    private void getShouKuanList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("channel", getIntent().getStringExtra("channel"));
        Connect.getInstance().post(getApplicationContext().getApplicationContext(), IService.KUAIJIE_SHOUKUAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                KuaiJieResponse response = (KuaiJieResponse) JsonUtils.parse((String) result, KuaiJieResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<KuaiJieResponse.KuaiJieInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                    } else {
                        adapter.clearData();
                        adapter.notifyDataSetChanged();
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", SK_XianE_Activity.this,
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
