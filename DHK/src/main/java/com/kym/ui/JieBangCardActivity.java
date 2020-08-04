package com.kym.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.adapter.JieBangAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.JieBangListResponse;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 信用卡解绑
 *
 * @author sun
 * @date 2019/12/3
 */

public class JieBangCardActivity extends BaseActivity implements View.OnClickListener {

    private JieBangAdapter adapter;
    private String id, type;
    private TextView change_card;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_bang_card);
        initHead();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCardSign();
    }

    private void initHead() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.head_text_title);
        title.setText("信用卡解绑");
    }

    private void initView() {
        CircleImageView iv_bank_sign = (CircleImageView) findViewById(R.id.iv_bank_sign);
        TextView tv_bank_sign_card_name = (TextView) findViewById(R.id.tv_bank_sign_card_name);
        TextView tv_bank_sign_card_number = (TextView) findViewById(R.id.tv_bank_sign_card_number);
        Glide.with(this).load(getIntent().getStringExtra("NLogoUrl")).dontAnimate().into(iv_bank_sign);
        tv_bank_sign_card_name.setText(getIntent().getStringExtra("NBankName"));
        tv_bank_sign_card_number.setText(getIntent().getStringExtra("NBankNo"));
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new JieBangAdapter(this, new JieBangAdapter.OnCardSignInfo() {


            @Override
            public void cardSignClick(JieBangListResponse.jiebangInfo jbInfo) {
                id = jbInfo.getId();
                type = jbInfo.getType();
            }
        });
        mRecyclerView.setAdapter(adapter);
        change_card = (TextView) findViewById(R.id.change_card);
        change_card.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                if (change_card.getText().toString().equals("删除信用卡")) {
                    delCard();
                } else {
                    getCardList();
                }
                break;
        }

    }

    /**
     * 获取银行卡签约通道
     */
    private void getCardSign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("NCardId"));
        Connect.getInstance().post(getApplicationContext(), IService.CARD_JIEBANG_LIST, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                JieBangListResponse response = (JieBangListResponse) JsonUtils.parse((String) result, JieBangListResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<JieBangListResponse.jiebangInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        id = data.get(0).getId();
                        type = data.get(0).getType();
                        adapter.setData(data);
                    } else {
                        adapter.clearData();
                        change_card.setText("删除信用卡");
                    }
                } else {
                    tipView("1", response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1", message);
            }
        });
    }

    /**
     * 确认解绑信用卡
     */
    private void getCardList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("NCardId"));
        params.put("id", id);
        params.put("type", type);
        Connect.getInstance().post(getApplicationContext(), IService.CARD_JIEBANG, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                JieBangListResponse response = (JieBangListResponse) JsonUtils.parse((String) result, JieBangListResponse.class);
                if (response.getResult().getCode() == 10000) {
                    tipView("2", response.getResult().getMsg());
                } else {
                    tipView("1", response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1", message);
            }
        });
    }

    /**
     * 删除信用卡
     */
    private void delCard() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("NCardId"));
        Connect.getInstance().post(getApplicationContext(), IService.CARD_DEL, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                JieBangListResponse response = (JieBangListResponse) JsonUtils.parse((String) result, JieBangListResponse.class);
                if (response.getResult().getCode() == 10000) {
                    tipView("2", response.getResult().getMsg());
                } else {
                    tipView("1", response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1", message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        if (backDialog == null) {
            backDialog = new BackDialog("", msg, "确定", JieBangCardActivity.this,
                    R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                @Override
                public void onClick(View view) {
                    if (!flag.equals("1")) {
                        finish();
                    }
                    backDialog.dismiss();
                }
            });
            backDialog.setCancelable(false);
            backDialog.show();
        }
    }
}
