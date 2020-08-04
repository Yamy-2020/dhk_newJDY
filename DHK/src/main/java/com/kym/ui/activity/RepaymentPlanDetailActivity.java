package com.kym.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.BackDialog;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.huankuan.BankPlanActivity;
import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.BillDetailResponse;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import widget.CustomPopWindow;

/**
 * 确认还款计划
 * Created by zachary on 2018/2/1.
 */

public class RepaymentPlanDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvNeedPayAmount;       //支付金额
    private TextView tvTotalAmount;         //还款总金额
    private TextView tvTotalFee;            //还款服务费
    private TextView tvRepaymentTotalCount; //还款总期数
    private TextView tvPerCount;            //每月拆分笔数
    private TextView tvDueDate;             //每月还款日
    private TextView tvFirstRepayment;      //第一笔还款金额
    private TextView tvFirstFee;            //第一笔还款服务费
    private EditText etCode;                //验证码输入框
    private TextView tvGetCode;             //获取验证码按钮
    private CustomPopWindow popWindow;
    private String billId;
    private SimpleDateFormat sf;
    private boolean jump2Home;
    private BackDialog backDialog;

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            long sec = millisUntilFinished / 1000;
            tvGetCode.setText(String.format("重新获取(%d秒)", sec));
        }

        @Override
        public void onFinish() {
            tvGetCode.setText("获取验证码");
            tvGetCode.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment_plan_detail);
        Intent intent = getIntent();
        billId = intent.getStringExtra("billId");
        // 首页进入传参，判断是否跳转回首页
        jump2Home = intent.getBooleanExtra("jump2Home", false);

        initView();
        initData();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (popWindow != null && popWindow.isShowing()) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_plan_detail_confirm:
                // 确认
                if (canConfirm()) {
                    confirm();
                }
                break;
            case R.id.head_img_left:
                if (jump2Home) {
                    //跳转回首页
                    Intent intent1 = new Intent(this, SecondActivity.class);
                    intent1.putExtra("refresh", false);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                } else {
                    // 跳转回计划列表页
                    Intent intent1 = new Intent(this, BankPlanActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                }
                finish();
                break;
            case R.id.right_tv:
                // 删除计划
                deletePlan();
                break;
            case R.id.tv_plan_detail_verify_code:
                // 获取验证码
                getVerifyCode();
                break;
            case R.id.lijiqianyue:
                popWindow.dismiss();
                // 前往计划列表页
                if (jump2Home) {
                    Intent intent1 = new Intent(this, SecondActivity.class);
                    intent1.putExtra("refresh", true);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                } else {
                    Intent intent = new Intent(RepaymentPlanDetailActivity.this, BankPlanActivity.class);
                    intent.putExtra("turn_type", "bills");
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
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    sendBroadcast(new Intent().setAction("refresh"));
                    sendBroadcast(new Intent().setAction("listRefresh"));
                }
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (popWindow != null && popWindow.isShowing()) {
                return true;
            }
            if (jump2Home) {
                // 跳转回首页
                Intent intent1 = new Intent(this, SecondActivity.class);
                intent1.putExtra("refresh", false);
                intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
            } else {
                // 计划列表页
                Intent intent = new Intent(this, BankPlanActivity.class);
                intent.putExtra("turn_type", "bills");
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
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private String stamp2DayString(long stamp) {
        Date d = new Date(stamp * 1000);
        if (sf == null) {
            sf = new SimpleDateFormat("dd");
        }
        return sf.format(d);
    }

    private void setData(BillDetailResponse.BillDetailInfo data) {
        List<BillDetailResponse.BillPlanInfo> plans = data.getPlan();
        if (plans != null && plans.size() > 0) {
            Collections.sort(plans, new SortByStamp());
            BillDetailResponse.BillPlanInfo planInfo = plans.get(0);
            double totalFee = (data.getRepay_fee() + data.getSummaryisfee()) / 100;
            tvTotalFee.setText(AmountUtils.round(totalFee));
            double needPayMoney = planInfo.getPlay_money() / 100 + totalFee;
//            tvNeedPayAmount.setText(AmountUtils.round(needPayMoney));//TODO
            tvNeedPayAmount.setText(AmountUtils.round(planInfo.getPlay_money() / 100));//TODO  18/6/12 17:00 叶老师需求。
            tvTotalAmount.setText(AmountUtils.round(data.getRepay_money() / 100));
            double perFee = (data.getSummaryisfee() / data.getRepay_number()) / 100;
            tvRepaymentTotalCount.setText(AmountUtils.round(perFee));
            tvPerCount.setText(String.valueOf(data.getRepay_number()));
            String startDay = stamp2DayString(planInfo.getAddtime());
            String endDay = stamp2DayString(plans.get(plans.size() - 1).getAddtime());
            tvDueDate.setText(String.format("%s - %s日", startDay, endDay));
            tvFirstRepayment.setText(AmountUtils.round(planInfo.getPlay_money() / 100));//TODO
            tvFirstFee.setText(AmountUtils.round(planInfo.getRepay_fee() / 100 + +perFee));
        }
    }

    private void initView() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        tvNeedPayAmount = (TextView) findViewById(R.id.tv_plan_detail_amount);
        tvTotalAmount = (TextView) findViewById(R.id.tv_plan_detail_total_amount);
        tvTotalFee = (TextView) findViewById(R.id.tv_plan_detail_total_fee);
        tvRepaymentTotalCount = (TextView) findViewById(R.id.tv_plan_detail_total_count);
        tvPerCount = (TextView) findViewById(R.id.tv_plan_detail_per_count);
        tvDueDate = (TextView) findViewById(R.id.tv_plan_detail_due_date);
        TextView tvConfirm = (TextView) findViewById(R.id.tv_plan_detail_confirm);
        tvFirstRepayment = (TextView) findViewById(R.id.tv_plan_detail_first_repayment);
        tvFirstFee = (TextView) findViewById(R.id.tv_plan_detail_first_fee);
        CircleImageView ivBankLogo = (CircleImageView) findViewById(R.id.civ_repayment_confirm);
        TextView tvBankName = (TextView) findViewById(R.id.tv_repayment_confirm_bank_name);
        TextView tvBankNumber = (TextView) findViewById(R.id.tv_repayment_confirm_bank_number);
        TextView tvBindMobile = (TextView) findViewById(R.id.tv_plan_detail_mobile);
        etCode = (EditText) findViewById(R.id.et_plan_detail_code);
        tvGetCode = (TextView) findViewById(R.id.tv_plan_detail_verify_code);

        tvTitle.setText("确认计划");
        tvConfirm.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        Glide.with(this).load(getIntent().getStringExtra("NLogoUrl")).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(getIntent().getStringExtra("NBankName"));
        tvBankNumber.setText(getIntent().getStringExtra("NBankNo").substring(0, 4)
                + " **** **** " + getIntent().getStringExtra("NBankNo").substring(getIntent().getStringExtra("NBankNo").length() - 4, getIntent().getStringExtra("NBankNo").length()));
        tvBindMobile.setText(getIntent().getStringExtra("NMobile"));
    }

    private boolean canConfirm() {
        String verifyCode = etCode.getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            ToastUtil.showTextToas(getApplicationContext(), "验证码不能为空");
            return false;
        }
        if (verifyCode.length() < 4) {
            ToastUtil.showTextToas(getApplicationContext(), "验证码长度不正确");
            return false;
        }
        return true;
    }

    private void showConfirmDialog() {
        popWindow = new CustomPopWindow.Builder(this).setTranslucent(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).setContentView(initPop())
                .setFocusable(false).setOutsideFocusable(false).setCancelable(false).setAnimationStyle(R.style.anim_popup_rise_down).build();
        popWindow.showAtLocation(tvGetCode, Gravity.BOTTOM);
    }

    private View initPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_banksigning, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_bank_sign);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_bank_sign_content);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_bank_sign_confirm);
        view.findViewById(R.id.lijiqianyue).setOnClickListener(this);
        imageView.setImageResource(R.drawable.push);
        tvContent.setText("计划确定成功,请前往查看");
        tvConfirm.setText("好   的");
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
                    setData(response.getData());
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
                YanzhengInfo_old info = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (info.getResult().getCode() == 10000) {
                    ToastUtil.showTextToas(getApplicationContext(), info.getResult().getMsg());
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
     * 获取验证码
     */
    private void getVerifyCode() {
        tvGetCode.setEnabled(false);
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", getIntent().getStringExtra("NMobile"));
        params.put("type", "3");
        params.put("uid", SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getUid());
        Connect.getInstance().post(this, IService.AUTHCODE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (response.getResult().getCode() == 10000) {
                    ToastUtil.showTextToas(getApplicationContext(), "验证码已发送");
                    timer.start();
                } else {
                    tvGetCode.setEnabled(true);
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                tvGetCode.setEnabled(true);
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 提交支付
     */
    private void confirm() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("bill_id", billId);
        params.put("mobile", getIntent().getStringExtra("NMobile"));
        params.put("auth_code", etCode.getText().toString());
        Connect.getInstance().post(getApplicationContext(), IService.CONFIRM_BILL_PAY, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old info = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (info.getResult().getCode() == 10000) {
                    ToastUtil.showTextToas(getApplicationContext(), "确认成功");
                    showConfirmDialog();
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

    private class SortByStamp implements Comparator<BillDetailResponse.BillPlanInfo> {
        @Override
        public int compare(BillDetailResponse.BillPlanInfo lhs, BillDetailResponse.BillPlanInfo rhs) {
            long rhsDate = rhs.getAddtime();
            long lhsDate = lhs.getAddtime();
            return lhsDate >= rhsDate ? 1 : -1;
        }
    }
}