package com.kym.ui.activity.bpbro_home.bpbro_yk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.bpbro_home.bpbro_sk.KuaiJieDetailActivity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.XFplanlistAdapter;
import com.kym.ui.adapter.ZDAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.KongKaDaiInfo;
import com.kym.ui.info.ZDResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


/**
 * 智收明细
 *
 * @author sun
 * @date 2019/12/3
 */

public class XFplaninfoActivity extends BaseActivity implements View.OnClickListener {
    private ZDAdapter zdAdapter;
    private BackDialog backDialog;
    private XFplanlistAdapter adapter;
    private int XF = 2;
    private LinearLayout zanwu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_xfplaninfo);
        setContentView(R.layout.activity_zd);
        initHead();
//        initView();
        getZdList();
    }


    /**
     * 账单明细列表
     */
    private void getZdList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.ZD_LIST1, null, new Connect.OnResponseListener() {
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
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", XFplaninfoActivity.this,
                            R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(XFplaninfoActivity.this, LoginActivity.class));

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
    private void initHead() {
     /*   findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = findViewById(R.id.head_text_title);*/
        TextView title = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        title.setText("智收明细");
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        zdAdapter = new ZDAdapter(this, new ZDAdapter.OnZDClick() {
            @Override
            public void OnZDClick(ZDResponse.ZDInfo zdInfo) {

            }
        });
        mRecyclerView.setAdapter(zdAdapter);
    }

/*    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getStringExtra("type").equals("all")) {
            getAllPlanList();
        } else {
            getPlanList();
        }
    }*/

/*
    private void initView() {
        RecyclerView mRecyclerView = findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new XFplanlistAdapter(getApplicationContext(), new XFplanlistAdapter.OnKongKaInfo() {
            @Override
            public void kongkaClick(KongKaDaiInfo.KongKaInfo kongKaInfo) {
                Intent intent = new Intent(getApplicationContext(), XFplanlistdetailActivity.class);
                intent.putExtra("bill_id", kongKaInfo.getBill_id());
                startActivityForResult(intent, XF);
            }
        });
        mRecyclerView.setAdapter(adapter);
        zanwu = findViewById(R.id.zanwu);
    }
*/

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XF) {
            getAllPlanList();
        }
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }

/*    *//**
     * 获取消费计划列表
     *//*
    private void getPlanList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("p", "1");
        paramx.put("cardid", getIntent().getStringExtra("NCardId"));
        Connect.getInstance().post(getApplicationContext(), IService.XF_PLAN_LIST, paramx, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                KongKaDaiInfo response = (KongKaDaiInfo) JsonUtils.parse((String) result, KongKaDaiInfo.class);
                dialogUtil.dismiss();
                if (response.getResult().getCode() == 10000) {
                    List<KongKaDaiInfo.KongKaInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                        zanwu.setVisibility(View.GONE);
                    } else {
                        zanwu.setVisibility(View.VISIBLE);
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
    }*/

    /**
     * 获取消费计划列表
     */
    /*
    private void getAllPlanList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("p", "1");
        Connect.getInstance().post(getApplicationContext(), IService.XF_PLAN_LIST, paramx, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                KongKaDaiInfo response = (KongKaDaiInfo) JsonUtils.parse((String) result, KongKaDaiInfo.class);
                dialogUtil.dismiss();
                if (response.getResult().getCode() == 10000) {
                    List<KongKaDaiInfo.KongKaInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                        zanwu.setVisibility(View.GONE);
                    } else {
                        zanwu.setVisibility(View.VISIBLE);
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
}*/
}
