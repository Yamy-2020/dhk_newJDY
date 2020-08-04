package com.kym.ui.activity.bpbro_home.bpbro_hk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.HKplanlistAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.ChongZhiPlan;
import com.kym.ui.info.HKplanResponse;
import com.kym.ui.info.KongKaDaiInfo;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


/**
 * 还款计划列表
 *
 * @author sun
 * @date 2019/12/3
 */

public class HK_Sb_planinfoActivity extends BaseActivity {

    @BindView(R.id.head_text_title)
    TextView headTextTitle;
    @BindView(R.id.head_img_left)
    ImageView headImgLeft;
    @BindView(R.id.rv_bank_list)
    RecyclerView rvBankList;
    @BindView(R.id.zanwu)
    LinearLayout zanwu;
    private HKplanlistAdapter adapter;
    private int XF = 2;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfplaninfo);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getStringExtra("type").equals("all")) {
            getAllPlanList();
        }
    }

    private void initView() {
        headTextTitle.setText("失败还款计划列表");
        rvBankList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new HKplanlistAdapter(getApplicationContext(), new HKplanlistAdapter.OnKongKaInfo() {
            @Override
            public void kongkaClick(KongKaDaiInfo.KongKaInfo kongKaInfo, String type) {
                if (type.equals("0")) {
                    /**
                     * 计划详情
                     */
                    Intent intent = new Intent(getApplicationContext(), HK_planlistdetailActivity.class);
                    intent.putExtra("bill_id", kongKaInfo.getBill_id());
                    startActivityForResult(intent, XF);
                } else {
                    /**
                     * 重置计划
                     */
                    czPlan(kongKaInfo);
                }
            }
        });
        rvBankList.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XF) {
            getAllPlanList();
        }
    }

    /**
     * 获取消费计划列表
     */
    private void getAllPlanList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("p", "1");
        paramx.put("type", "5");
        Connect.getInstance().post(getApplicationContext(), IService.HK_LIST, paramx, new Connect.OnResponseListener() {
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
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", HK_Sb_planinfoActivity.this,
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

    @OnClick(R.id.head_img_left)
    public void onViewClicked() {
        finish();
    }

    /**
     * 重置计划
     */
    private void czPlan(KongKaDaiInfo.KongKaInfo kongKaInfo) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("bill_id", kongKaInfo.getBill_id());
        Connect.getInstance().post(getApplicationContext(), IService.CHONGZHIHKPLAN, paramx, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                ChongZhiPlan response = (ChongZhiPlan) JsonUtils.parse((String) result, ChongZhiPlan.class);
                dialogUtil.dismiss();
                if (response.getResult().getCode() == 10000) {
                    getPrePlan(response, kongKaInfo.getAfterafew());
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", HK_Sb_planinfoActivity.this,
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

    /**
     * 获取预览计划
     */
    private void getPrePlan(ChongZhiPlan response1, String cardno) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", response1.getData().getCardid());
        params.put("money", response1.getData().getMoney());
        params.put("type", response1.getData().getType());
        params.put("number", response1.getData().getNumber());
        params.put("area", response1.getData().getArea());
        params.put("date", response1.getData().getDate());
        params.put("mode", response1.getData().getMode());
        params.put("cardsurplus", response1.getData().getCardsurplus());
        params.put("reset_bill_id", response1.getData().getReset_bill_id());
        Connect.getInstance().post(getApplicationContext(), IService.NEW_HK_PLAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                HKplanResponse response = (HKplanResponse) JsonUtils.parse((String) result, HKplanResponse.class);
                if (response.getData() != null) {
                    Intent intent = new Intent(getApplicationContext(), HK_prePlanActivity.class);
                    intent.putExtra("cardid", response1.getData().getCardid());
                    intent.putExtra("money", response1.getData().getMoney());
                    intent.putExtra("type", response1.getData().getType());
                    intent.putExtra("number", response1.getData().getNumber());
                    intent.putExtra("area", response1.getData().getArea());
                    intent.putExtra("date", response1.getData().getDate());
                    intent.putExtra("mode", response1.getData().getMode());
                    intent.putExtra("cardsurplus", response1.getData().getCardsurplus());
                    intent.putExtra("cradno", cardno);
                    intent.putExtra("reset_bill_id", response1.getData().getReset_bill_id());
                    startActivity(intent);
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
}
