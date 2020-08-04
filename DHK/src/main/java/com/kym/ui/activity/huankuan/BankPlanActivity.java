package com.kym.ui.activity.huankuan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.BankPlanAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.info.BillPlanResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 代还计划列表
 */

public class BankPlanActivity extends BaseActivity implements View.OnClickListener {

    private final int PAGE_COUNT = 12;
    private PtrClassicFrameLayout mPtrLayout;
    private BankPlanAdapter adapter;
    private List<BillPlanResponse.BillPlanInfo> allhotlist_shouru = new ArrayList<BillPlanResponse.BillPlanInfo>();
    private BankListResponse.BankInfo bankInfo;
    private BillPlanResponse planResponse;
    private TextView tvConfirm;
    private refreshBroadcastReceiver receiver;
    private int nowPage = 1;
    private String NCardId, NBankName, NMobile, NImageUrl,
            NLogoUrl, NIsPlan, NCarshNumber, NRepayNumber, NBankNo;
    private int NIsUse, NDayNum, NMaxNum, NBills, NRepayment;
    private ListView mListView;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_plan);
        Intent intent = getIntent();
        planResponse = (BillPlanResponse) intent.getSerializableExtra("planData");
        if (getIntent().getStringExtra("turn_type").equals("banks")) {
            bankInfo = (BankListResponse.BankInfo) intent.getSerializableExtra("bankData");
            NBankName = bankInfo.getBank_name();
            NImageUrl = bankInfo.getImage_url();
            NBankNo = bankInfo.getBank_no();
            NCardId = bankInfo.getCardid();
            NIsUse = bankInfo.getIsUse();
            NMaxNum = bankInfo.getMaxnum();
            NBills = bankInfo.getBills();
            NRepayment = bankInfo.getRepayment();
            NLogoUrl = bankInfo.getLogo_url();
            NMobile = bankInfo.getMobile();
            NIsPlan = bankInfo.getIs_plan();
            NCarshNumber = bankInfo.getCash_number();
            NRepayNumber = bankInfo.getRepay_number();
            NDayNum = bankInfo.getDayNum();
        } else {
            NBankName = getIntent().getStringExtra("NBankName");
            NImageUrl = getIntent().getStringExtra("NImageUrl");
            NBankNo = getIntent().getStringExtra("NBankNo");
            NCardId = getIntent().getStringExtra("NCardId");
            NIsUse = Integer.parseInt(getIntent().getStringExtra("NIsUse"));
            NMaxNum = Integer.parseInt(getIntent().getStringExtra("NMaxNum"));
            NBills = Integer.parseInt(getIntent().getStringExtra("NBills"));
            NRepayment = Integer.parseInt(getIntent().getStringExtra("NRepayment"));
            NLogoUrl = getIntent().getStringExtra("NLogoUrl");
            NMobile = getIntent().getStringExtra("NMobile");
            NIsPlan = getIntent().getStringExtra("NCardId");
            NCarshNumber = getIntent().getStringExtra("NCarshNumber");
            NRepayNumber = getIntent().getStringExtra("NRepayNumber");
            NDayNum = Integer.parseInt(getIntent().getStringExtra("NDayNum"));
        }
        registerBroadCast();
        initUI();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.add_new_plan:
                if (NIsUse == 1) {
                    if (NMaxNum > 0) {
                        Intent intent = new Intent(BankPlanActivity.this, NewRepaymentPlanActivity.class);
                        intent.putExtra("NCardId", NCardId);
                        intent.putExtra("NBankName", NBankName);
                        intent.putExtra("NBankNo", NBankNo);
                        intent.putExtra("NMobile", NMobile);
                        intent.putExtra("NBills", NBills + "");
                        intent.putExtra("NRepayment", NRepayment + "");
                        intent.putExtra("NImageUrl", NImageUrl);
                        intent.putExtra("NLogoUrl", NLogoUrl);
                        intent.putExtra("NIsPlan", NIsPlan);
                        intent.putExtra("NCarshNumber", NCarshNumber);
                        intent.putExtra("NRepayNumber", NRepayNumber);
                        intent.putExtra("NDayNum", NDayNum + "");
                        intent.putExtra("NMaxNum", NMaxNum + "");
                        intent.putExtra("NIsUse", NIsUse + "");
                        startActivity(intent);
                    } else {
                        tipView("1", "账单未生成，无法添加计划");
                    }
                } else {
                    tipView("1", "制定计划服务时间在9：00 - 18：00");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initData() {
        if (planResponse != null) {
            nowPage = 2;
            BillPlanResponse.BillPlanData data = planResponse.getData();
            tvConfirm.setVisibility(TextUtils.equals(data.getIs_show_button(), "1") ? View.VISIBLE : View.GONE);
            List<BillPlanResponse.BillPlanInfo> plans = data.getBill_list();
            if (plans != null) {
                adapter.setData(plans);
            }
            mPtrLayout.setLoadMoreEnable(plans != null && plans.size() == PAGE_COUNT);
        } else {
            nowPage = 1;
            getPlanList();
        }
    }

    private void initUI() {
        TextView title = (TextView) findViewById(R.id.head_text_title);
        mListView = (ListView) findViewById(R.id.bank_plan_list);
        mPtrLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_bank_plan);
        tvConfirm = (TextView) findViewById(R.id.add_new_plan);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        title.setText("还款计划");
        tvConfirm.setOnClickListener(this);
        mListView.addHeaderView(initHeaderView());
        adapter = new BankPlanAdapter(BankPlanActivity.this);
        mListView.setAdapter(adapter);
        mListView.setDivider(getResources().getDrawable(R.drawable.divider_10dp));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Log.d("sun", allhotlist_shouru.toString());
                    BillPlanResponse.BillPlanInfo planInfo = allhotlist_shouru.get(position - 1);
                    if (TextUtils.equals(planInfo.getStatus(), "0")) {
                        // 计划未确认
                        Intent intent = new Intent(BankPlanActivity.this, NewRepaymentDetailActivity.class);
                        intent.putExtra("billId", planInfo.getBill_id());
                        intent.putExtra("status", "2");
                        intent.putExtra("NCardId", NCardId);
                        intent.putExtra("NBankName", NBankName);
                        intent.putExtra("NBankNo", NBankNo);
                        intent.putExtra("NMobile", NMobile);
                        intent.putExtra("NBills", NBills + "");
                        intent.putExtra("NRepayment", NRepayment + "");
                        intent.putExtra("NImageUrl", NImageUrl);
                        intent.putExtra("NLogoUrl", NLogoUrl);
                        intent.putExtra("NIsPlan", NIsPlan);
                        intent.putExtra("NCarshNumber", NCarshNumber);
                        intent.putExtra("NRepayNumber", NRepayNumber);
                        intent.putExtra("NDayNum", NDayNum + "");
                        intent.putExtra("NMaxNum", NMaxNum + "");
                        intent.putExtra("NIsUse", NIsUse + "");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(BankPlanActivity.this, NewRepaymentDetailActivity.class);
                        intent.putExtra("billId", planInfo.getBill_id());
                        intent.putExtra("status", "1");
                        intent.putExtra("NCardId", NCardId);
                        intent.putExtra("NBankName", NBankName);
                        intent.putExtra("NBankNo", NBankNo);
                        intent.putExtra("NMobile", NMobile);
                        intent.putExtra("NBills", NBills + "");
                        intent.putExtra("NRepayment", NRepayment + "");
                        intent.putExtra("NImageUrl", NImageUrl);
                        intent.putExtra("NLogoUrl", NLogoUrl);
                        intent.putExtra("NIsPlan", NIsPlan);
                        intent.putExtra("NCarshNumber", NCarshNumber);
                        intent.putExtra("NRepayNumber", NRepayNumber);
                        intent.putExtra("NDayNum", NDayNum + "");
                        intent.putExtra("NMaxNum", NMaxNum + "");
                        intent.putExtra("NIsUse", NIsUse + "");
                        startActivity(intent);
                    }
                }
            }
        });
        mPtrLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                nowPage = 1;
                getPlanList();
            }
        });
        mPtrLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                getPlanList();
            }
        });
        mPtrLayout.setLoadMoreEnable(true);
    }

    private View initHeaderView() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.head_bank_plan, null);
        ImageView ivCardBg = (ImageView) headerView.findViewById(R.id.iv_bank_list);
        TextView tvCardName = (TextView) headerView.findViewById(R.id.tv_bank_name);
        TextView tvCardNumber = (TextView) headerView.findViewById(R.id.tv_bank_num);
        TextView tvCardDate = (TextView) headerView.findViewById(R.id.tv_bank_date);

        Glide.with(this).load(NImageUrl).dontAnimate().placeholder(R.drawable.default_image).into(ivCardBg);
        tvCardName.setText(NBankName);
        tvCardNumber.setText(NBankNo.substring(0, 4)
                + " **** **** " + NBankNo.substring(NBankNo.length() - 4, NBankNo.length()));
        tvCardDate.setText(String.format("账单日:%s日   /   还款日:%s日", NBills, NRepayment));
        return headerView;
    }

    private void registerBroadCast() {
        receiver = new refreshBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("refresh");
        registerReceiver(receiver, filter);
    }

    private class refreshBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            nowPage = 1;
            getPlanList();
        }
    }

    /**
     * 获取银行卡还款计划列表
     */
    private void getPlanList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", NCardId);
        params.put("p", String.valueOf(nowPage));
        Connect.getInstance().post(getApplicationContext(), IService.GET_BILLLIST, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                BillPlanResponse response = (BillPlanResponse) JsonUtils.parse((String) result, BillPlanResponse.class);
                if (response.getResult().getCode() == 10000) {
                    planResponse = response;
                    BillPlanResponse.BillPlanData data = planResponse.getData();
                    tvConfirm.setVisibility(TextUtils.equals(data.getIs_show_button(), "1") ? View.VISIBLE : View.GONE);
                    List<BillPlanResponse.BillPlanInfo> plans = data.getBill_list();
                    if (plans != null && plans.size() > 0) {
                        if (nowPage > 1) {
                            adapter.insertData(plans);
                        } else {
                            adapter.setData(plans);
                        }
                        allhotlist_shouru.addAll(plans);
                    } else {
                        if (nowPage == 1) {
                            adapter.clearData();
                            tvConfirm.setVisibility(View.VISIBLE);
                        }
                    }

                    // 刷新状态
                    nowPage++;
                    if (mPtrLayout.isRefreshing()) {
                        mPtrLayout.refreshComplete();
                    }
                    if (mPtrLayout.isLoadingMore()) {
                        mPtrLayout.loadMoreComplete(plans != null && plans.size() < PAGE_COUNT);
                    } else {
                        mPtrLayout.setLoadMoreEnable(plans != null && plans.size() == PAGE_COUNT);
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                    if (mPtrLayout.isRefreshing()) {
                        mPtrLayout.refreshComplete();
                    }
                    if (mPtrLayout.isLoadingMore()) {
                        mPtrLayout.loadMoreComplete(false);
                    } else {
                        mPtrLayout.setLoadMoreEnable(false);
                    }
                }
            }

            @Override
            public void onFailure(String message) {
                // 刷新状态
                if (mPtrLayout.isRefreshing()) {
                    mPtrLayout.refreshComplete();
                }
                if (mPtrLayout.isLoadingMore()) {
                    mPtrLayout.loadMoreComplete(false);
                } else {
                    mPtrLayout.setLoadMoreEnable(false);
                }
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", BankPlanActivity.this,
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