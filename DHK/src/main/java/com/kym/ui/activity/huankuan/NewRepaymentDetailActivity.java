package com.kym.ui.activity.huankuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.RepaymentPlanDetailActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.NewRepaymentDetailAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.BillDetailResponse;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DIsplayUtils;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import widget.CustomPopWindow;

/**
 * 还款明细
 * Created by zachary on 2018/2/9.
 */

public class NewRepaymentDetailActivity extends BaseActivity implements View.OnClickListener {

    private String billId;
    private String status;//页面状态 1-已确认 2-未确认
    private NewRepaymentDetailAdapter adapter;
    private boolean jump2Home;// 首页传参，判断是否跳转回首页
    private CustomPopWindow deletePopWindow;
    private CustomPopWindow confirmPopWindow;
    private TextView tvConfirm;
    private TextView tvRight;
    private TextView tv_stop_plan;
    private BackDialog backDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_repayment_detail);

        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        billId = intent.getStringExtra("billId");
        //   从首页跳转到此页面的传参
        jump2Home = intent.getBooleanExtra("jump2Home", false);

        initView();
        initData();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (deletePopWindow != null && deletePopWindow.isShowing()) {
            return false;
        }
        if (confirmPopWindow != null && confirmPopWindow.isShowing()) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.tv_plan_detail_confirm:
                Intent intent = new Intent(this, RepaymentPlanDetailActivity.class);
                intent.putExtra("NCardId", getIntent().getStringExtra("NCardId"));
                intent.putExtra("NBankName", getIntent().getStringExtra("NBankName"));
                intent.putExtra("NBankNo", getIntent().getStringExtra("NBankNo"));
                intent.putExtra("NMobile", getIntent().getStringExtra("NMobile"));
                intent.putExtra("NBills", getIntent().getStringExtra("NBills"));
                intent.putExtra("NRepayment", getIntent().getStringExtra("NRepayment"));
                intent.putExtra("NImageUrl", getIntent().getStringExtra("NImageUrl"));
                intent.putExtra("NLogoUrl", getIntent().getStringExtra("NLogoUrl"));
                intent.putExtra("NIsPlan", getIntent().getStringExtra("NIsPlan"));
                intent.putExtra("NCarshNumber", getIntent().getStringExtra("NCarshNumber"));
                intent.putExtra("NRepayNumber", getIntent().getStringExtra("NRepayNumber"));
                intent.putExtra("NDayNum", getIntent().getStringExtra("NDayNum"));
                intent.putExtra("NMaxNum", getIntent().getStringExtra("NMaxNum"));
                intent.putExtra("NIsUse", getIntent().getStringExtra("NIsUse"));
                intent.putExtra("billId", billId);
                intent.putExtra("jump2Home", jump2Home);
                startActivity(intent);
                break;
            case R.id.right_tv:
                String str = tvRight.getText().toString();
                if (TextUtils.equals(str, "删除计划")) {
                    showDeleteDialog();
                } else if (TextUtils.equals(str, "继续执行")) {
                    // 继续执行
                    showConfirmDialog();
                }
                break;
            case R.id.tv_delete_item_confirm:
                String str1 = tvRight.getText().toString();
                if (TextUtils.equals(str1, "删除计划")) {
                    deletePlan();
                } else {
                    // 继续执行
                    repaymentAgain();
                }
                break;
            case R.id.tv_delete_item_cancel:
                if (deletePopWindow != null) {
                    deletePopWindow.dismiss();
                } else if (confirmPopWindow != null) {
                    confirmPopWindow.dismiss();
                }
                break;
            case R.id.tv_stop_plan:
                stopPlan();
                break;
            default:
                break;
        }
    }

    private void initView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_plan_detail);
        TextView title = (TextView) findViewById(R.id.head_text_title);
        tvRight = (TextView) findViewById(R.id.right_tv);
        tvRight.setOnClickListener(this);
        tvConfirm = (TextView) findViewById(R.id.tv_plan_detail_confirm);
        tvConfirm.setOnClickListener(this);
        findViewById(R.id.head_img_left).setOnClickListener(this);

        title.setText("还款明细");
        tvRight.setText("删除计划");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider_10dp));
        mRecyclerView.addItemDecoration(decoration);
        adapter = new NewRepaymentDetailAdapter(this);
        mRecyclerView.setAdapter(adapter);

        if (TextUtils.equals(status, "1")) {
            tvRight.setVisibility(View.INVISIBLE);
            tvConfirm.setVisibility(View.GONE);
        } else {
            tvRight.setVisibility(View.VISIBLE);
            tvConfirm.setVisibility(View.VISIBLE);
        }

        tv_stop_plan = (TextView) findViewById(R.id.tv_stop_plan);
        tv_stop_plan.setOnClickListener(this);
    }

    private void showDeleteDialog() {
        int width = DIsplayUtils.getScreenWidth(this) - DIsplayUtils.dp2px(this, 48);

        deletePopWindow = new CustomPopWindow.Builder(this).setTranslucent(true)
                .size(width, ViewGroup.LayoutParams.WRAP_CONTENT).setContentView(initDeleteView())
                .setFocusable(false).setOutsideFocusable(false).setCancelable(false).build();
        deletePopWindow.showAtLocation(tvConfirm, Gravity.CENTER);
    }

    private View initDeleteView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_delete_item, null);
        TextView title = (TextView) view.findViewById(R.id.tv_delete_item_content);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_delete_item_cancel);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_delete_item_confirm);
        title.setText("是否确认删除此还款计划？");
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        return view;
    }

    private void showConfirmDialog() {
        int width = DIsplayUtils.getScreenWidth(this) - DIsplayUtils.dp2px(this, 48);

        confirmPopWindow = new CustomPopWindow.Builder(this).setTranslucent(true)
                .size(width, ViewGroup.LayoutParams.WRAP_CONTENT).setContentView(initConfirmView())
                .setFocusable(false).setOutsideFocusable(false).setCancelable(false).build();
        confirmPopWindow.showAtLocation(tvConfirm, Gravity.CENTER);
    }

    private View initConfirmView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_delete_item, null);
        TextView title = (TextView) view.findViewById(R.id.tv_delete_item_content);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_delete_item_cancel);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_delete_item_confirm);
        title.setText("是否继续执行？");
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        return view;
    }

    /**
     * 获取计划详情
     */
    private void initData() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("bill_id", billId);
        Connect.getInstance().post(getApplicationContext(), IService.GET_BILLINFO, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                BillDetailResponse response = (BillDetailResponse) JsonUtils.parse((String) result, BillDetailResponse.class);
                if (response.getResult().getCode() == 10000) {
                    BillDetailResponse.BillDetailInfo data = response.getData();
                    List<BillDetailResponse.BillPlanInfo> plans = data.getPlan();
                    Collections.sort(plans, new SortByStamp());
                    data.setPlan(plans);
                    adapter.setData(data);
                    if (TextUtils.equals(data.getPaystatus(), "2")) {
                        tvRight.setVisibility(View.VISIBLE);
                        tvRight.setTextColor(getResources().getColor(R.color.blue_21));
                        tvRight.setText("继续执行");
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

    /**
     * 删除计划
     */
    private void deletePlan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("bill_id", billId);
        Connect.getInstance().post(getApplicationContext(), IService.DELETE_PLAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                deletePopWindow.dismiss();
                YanzhengInfo_old info = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (info.getResult().getCode() == 10000) {
                    Intent intent = new Intent();
                    intent.setAction("refresh");
                    sendBroadcast(intent);
                    tipView("2", "删除成功");
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), info.getResult().getMsg());
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
     * 继续执行
     */
    private void repaymentAgain() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("bill_id", billId);
        Connect.getInstance().post(getApplicationContext(), IService.REPAYMENT_AGAIN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                confirmPopWindow.dismiss();
                YanzhengInfo_old info = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (info.getResult().getCode() == 10000) {
                    initData();
                    Intent intent = new Intent();
                    intent.setAction("refresh");
                    sendBroadcast(intent);
                    ToastUtil.showTextToas(getApplicationContext(),"继续执行成功");
                } else {
                    ToastUtil.showTextToas(getApplicationContext(),info.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                confirmPopWindow.dismiss();
                ToastUtil.showTextToas(getApplicationContext(),message);
            }
        });
    }

    /**
     * 删除计划
     */
    private void stopPlan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("bill_id", billId);
        Connect.getInstance().post(getApplicationContext(), IService.STOP_PlAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old info = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (info.getResult().getCode() == 10000) {
                    tipView("2", info.getResult().getMsg());
                } else {
                    ToastUtil.showTextToas(getApplicationContext(),info.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(),message);
            }
        });
    }

    private class SortByStamp implements Comparator<BillDetailResponse.BillPlanInfo> {

        @Override
        public int compare(BillDetailResponse.BillPlanInfo lhs, BillDetailResponse.BillPlanInfo rhs) {
            long rhsDate = rhs.getAddtime();
            long lhsDate = lhs.getAddtime();
            return lhsDate >= rhsDate ? 1 : -1;
        }
    }

    public void tipView(final String flag, String msg) {
        if (backDialog == null) {
            backDialog = new BackDialog("", msg, "确定", NewRepaymentDetailActivity.this,
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
