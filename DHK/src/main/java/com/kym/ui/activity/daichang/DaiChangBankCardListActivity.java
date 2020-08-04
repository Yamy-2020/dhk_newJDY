package com.kym.ui.activity.daichang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.NewBankListAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.hualuo.HL_BK_WebActivity;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 贷偿信用卡列表
 *
 * @author sun
 * @date 2019/12/3
 */

public class DaiChangBankCardListActivity extends Activity implements View.OnClickListener {
    private NewBankListAdapter adapter;
    public static Activity activity;
    private final int FANHUICODE = 2;
    private Intent intent;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_chang_bank_card_list);
        initHead();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCardList();
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        if (getIntent().getStringExtra("type").equals("X")) {
            tvTitle.setText("选择信用卡(小额)");
        } else {
            tvTitle.setText("选择信用卡(大额)");
        }
    }

    private void initView() {
        findViewById(R.id.lay_foot_add).setOnClickListener(this);
        final RecyclerView mRecyclerView = findViewById(R.id.rv_bank_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new NewBankListAdapter(this, new NewBankListAdapter.OnBankCardClickListener() {
            @Override
            public void cardClick(BankListResponse.BankInfo bankInfo) {
                if (bankInfo.getIs_sign().equals("1")) {
                    /**
                     * 签约
                     */
                    if (bankInfo.getChannel().indexOf(",") == -1) {
                        if (bankInfo.getChannel().equals("4")) {
                            flag = true;
                        }
                    } else {
                        for (int i = 0; i < bankInfo.getChannel().split(",").length; i++) {
                            if (bankInfo.getChannel().split(",")[i].equals("4")) {
                                flag = true;
                                break;
                            }
                        }
                    }

                    if (flag) {
                        intent = new Intent(getApplicationContext(), DaiChang_QianYue_Activity.class);
                        intent.putExtra("bankInfo", bankInfo);
                        startActivity(intent);
                        finish();
                    } else {
                        intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                        intent.putExtra("WEB_TITLE", "信用卡签约");
                        intent.putExtra("WEB_URL", "https://www.bpbro.com/Bpbro_Sun/view/bpbro_dc/bpbro_dc_sign_pass.html?bank_name=" + bankInfo.getBank_name() +
                                "&bank_no=" + bankInfo.getBank_no() + "&logo_url=" + bankInfo.getLogo_url() + "&cardid=" + bankInfo.getCardid() +
                                "&omid=" + Clone.OMID + "&token=" + LoginActivity.accessToken_xin);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    if (bankInfo.getIs_plan().equals("1")) {
                        startActivity(new Intent(getApplicationContext(), DaiChangPlanRecordActivity.class));
                    } else {
                        if (bankInfo.getIs_allow().equals("1")) {
//                            calculateDueAndStatement(bankInfo.getBills(), bankInfo.getRepayment(), bankInfo);
                            /**
                             * 制定计划
                             */
                            intent = new Intent(getApplicationContext(), DaiChangPlanActivity.class);
                            intent.putExtra("bankInfo", bankInfo);
                            intent.putExtra("type", "X");
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.showTextToas(getApplicationContext(), bankInfo.getAllow_message());
                        }
                    }
                }
            }
        }, "HK");
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FANHUICODE) {
            getCardList();
        }
    }

    private void calculateDueAndStatement(int statementDate, int dueDate, BankListResponse.BankInfo bankInfo) {
        if (dueDate != -1 && statementDate != -1) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);//当天
            if (dueDate > statementDate) {
                /**
                 * 同一个月
                 */
                if (day <= statementDate || day >= dueDate) {
                    /**
                     * 账单未生成
                     */
                    ToastUtil.showTextToas(getApplicationContext(), "账单未生成");
                } else {
                    /**
                     * 制定计划
                     */
                    intent = new Intent(getApplicationContext(), DaiChangPlanActivity.class);
                    intent.putExtra("bankInfo", bankInfo);
                    intent.putExtra("type", "X");
                    startActivity(intent);
                    finish();
                }
            } else {
                /**
                 * 不同月
                 */
                if (day <= dueDate || day >= statementDate) {
                    /**
                     * 制定计划
                     */
                    intent = new Intent(getApplicationContext(), DaiChangPlanActivity.class);
                    intent.putExtra("bankInfo", bankInfo);
                    intent.putExtra("type", "X");
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), "账单未生成");
                }
            }
        } else {
            ToastUtil.showTextToas(getApplicationContext(), "未传入账单日和还款日");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.lay_foot_add:
                intent = new Intent(getApplicationContext(), NewAddCreditCardActivity.class);
                startActivityForResult(intent, FANHUICODE);
                break;
        }
    }

    /**
     * 获取空卡代还信用卡
     */
    private void getCardList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("type", getIntent().getStringExtra("type"));
        Connect.getInstance().post(getApplicationContext(), IService.DC_BANK_LIST, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                BankListResponse response = (BankListResponse) JsonUtils.parse((String) result, BankListResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<BankListResponse.BankInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                    } else {
                        adapter.clearData();
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
    }
}
