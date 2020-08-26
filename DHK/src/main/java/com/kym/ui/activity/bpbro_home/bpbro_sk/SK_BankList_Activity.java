package com.kym.ui.activity.bpbro_home.bpbro_sk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.KuaiJieCardListAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.hualuo.HL_BK_WebActivity;
import com.kym.ui.info.KuaiJieCardList;
import com.kym.ui.util.Connect;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


/**
 * 收款信用卡列表
 *
 * @author sun
 * @date 2019/12/3
 */
public class SK_BankList_Activity extends BaseActivity implements View.OnClickListener {

    private KuaiJieCardListAdapter adapter;
    private RelativeLayout lay_foot_add;
    private PromptDialog promptDialog;
    private final int FANHUICODE = 2;
    private BackDialog backDialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sk__bank_list);
        initHead();
        initView();
        getSK_bank_list();
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tvTitle.setText("选择收款信用卡");
    }

    private void initView() {
        final RecyclerView mRecyclerView = findViewById(R.id.rv_bank_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new KuaiJieCardListAdapter(this, new KuaiJieCardListAdapter.OnKuaiJieCardList() {
            @Override
            public void kj_card_list(KuaiJieCardList.KuaiJieCardListInfo kuaiJieCardListInfo) {
                //测试的时候注释掉的打开,下面的注释掉
//                intent = new Intent(getApplicationContext(), SK_TongDao_Activity.class);
//                intent.putExtra("data", kuaiJieCardListInfo);
//                startActivity(intent);
                intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                intent.putExtra("WEB_TITLE", "信用卡收款");
                intent.putExtra("WEB_URL", Clone.HOST+"Bpbro_Sun/view/bpbro_sk/bpbro_sk_pass.html?bank_name=" + kuaiJieCardListInfo.getBank_name() +
                        "&bank_no=" + kuaiJieCardListInfo.getBank_no() + "&logo_url=" + kuaiJieCardListInfo.getLogo_url() + "&cardid=" + kuaiJieCardListInfo.getCardid() +
                        "&omid=" + Clone.OMID + "&token=" + LoginActivity.accessToken_xin + "&channel=" + kuaiJieCardListInfo.getChannel());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);
        lay_foot_add = findViewById(R.id.lay_foot_add);
        lay_foot_add.setOnClickListener(this);
        lay_foot_add.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.lay_foot_add:
                intent = new Intent(getApplicationContext(), NewAddCreditCardActivity.class);
                startActivityForResult(intent, FANHUICODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FANHUICODE) {
            getSK_bank_list();
        }
    }

    /**
     * 获取收款信用卡列表
     */
    private void getSK_bank_list() {
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("加载中");
        Connect.getInstance().post(getApplicationContext(), IService.KUAIJIE_CREATE_LIST, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                promptDialog.dismissImmediately();
                KuaiJieCardList response = (KuaiJieCardList) JsonUtils.parse((String) result, KuaiJieCardList.class);
                if (response.getResult().getCode() == 10000) {
                    List<KuaiJieCardList.KuaiJieCardListInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                    } else {
                        adapter.clearData();
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", SK_BankList_Activity.this,
                            R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(SK_BankList_Activity.this, LoginActivity.class));

//                            restartApp(getApplicationContext());
                            backDialog.dismiss();
                        }
                    });
                    backDialog.setCancelable(false);
                    backDialog.show();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
                lay_foot_add.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String message) {
                promptDialog.dismissImmediately();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }
}
