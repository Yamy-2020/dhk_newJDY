package com.kym.ui.activity.huankuan;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.RepaymentPlanDetailActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.NewRepaymentAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.dialog.CalendarViewDialog;
import com.kym.ui.dialog.NoticeDialog;
import com.kym.ui.info.AddBillResponse;
import com.kym.ui.info.RepaymentPlanResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 制定还款计划
 * Created by zachary on 2018/1/30.
 */

public class NewRepaymentPlanActivity extends FragmentActivity implements View.OnClickListener {
    private final int TYPE_SMART = 1;
    private final int TYPE_CUSTOM = 2;
    private final int TYPE_OVER_DATE = -2;
    private final int TYPE_UNCREATE = -3;
    private EditText etRepaymentAmount;
    private TextView tvRepaymentMode;
    private TextView tvRepaymentDate;
    private EditText etRepaymentCount;
    private TextView tvPreview;
    private ConstraintLayout previewView;
    private PopupWindow popWindow;
    private LinearLayout llDate;
    private int statementDate;//账单日
    private int dueDate;//还款日
    private long startMillis;
    private int area;
    private int type = TYPE_SMART;
    private List<String> mSelectData;
    private CalendarViewDialog dateDialog;
    private NewRepaymentAdapter adapter;
    private HashMap<String, String> previewParams = new HashMap<>();//保存预览接口的参数
    private NoticeDialog dialog;
    private BackDialog backDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_repayment_plan);
        initView();
        calculateDueAndStatement();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.right_tv://提交
                if (paramsEquals()) {
                    showNoticeDialog();
                }
                break;
            case R.id.tv_new_repayment_mode://切换模式
                showModePopup();
                break;
            case R.id.tv_new_repayment_date://切换时间
                showDateDialog();
                break;
            case R.id.tv_new_repayment_preview://预览
                if (canPreView()) {
                    preView();
                }
                break;
            case R.id.cancel:
                popWindow.dismiss();
                break;
            case R.id.photograph://智能模式
                llDate.setVisibility(View.GONE);
                tvRepaymentMode.setText("智能模式");
                type = TYPE_SMART;
                popWindow.dismiss();
                break;
            case R.id.albums://自定义模式
                llDate.setVisibility(View.VISIBLE);
                tvRepaymentMode.setText("自定义模式");
                if (type == TYPE_SMART) {
                    mSelectData = null;
                    tvRepaymentDate.setText("");
                    etRepaymentCount.setText("");
                    previewView.setVisibility(View.INVISIBLE);
                }
                type = TYPE_CUSTOM;
                popWindow.dismiss();
                showDateDialog();
                break;
            default:
                break;
        }
    }

    private void showNoticeDialog() {
        if (dialog == null) {
            dialog = new NoticeDialog();
            dialog.setConfiemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirm();
                }
            });
        }
        dialog.show(getSupportFragmentManager(), "notice");
    }

    private int maxnum;
    private int dayNum;

    private void calculateDueAndStatement() {
        Intent intent = getIntent();
        maxnum = Integer.parseInt(intent.getStringExtra("NMaxNum"));
        dayNum = Integer.parseInt(intent.getStringExtra("NDayNum"));
        statementDate = Integer.parseInt(intent.getStringExtra("NBills"));
        dueDate = Integer.parseInt(intent.getStringExtra("NRepayment"));
        if (dueDate != -1 && statementDate != -1) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);//当天
            if (dueDate > statementDate) {//账单日还款日在同一个月
                if (day < statementDate) { // 1 - statementDate
                    area = TYPE_UNCREATE;
                } else if (day <= dueDate - 2) { // statementDate - dueDate-2
                    if (day == statementDate || calendar.get(Calendar.HOUR_OF_DAY) > 19) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                    }
                    area = dueDate - 1 - calendar.get(Calendar.DAY_OF_MONTH);
                    startMillis = calendar.getTime().getTime();
                } else if (day < dueDate) { // dueDate-2 - dueDate
                    area = TYPE_OVER_DATE;
                } else { // dueDate - 31
                    area = TYPE_UNCREATE;
                }
            } else {//账单日还款日不在同一个月
                if (day <= dueDate - 2) { // 1 - dueDate-2
                    if (calendar.get(Calendar.HOUR_OF_DAY) > 19) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                    }
                    startMillis = calendar.getTime().getTime();
                    area = dueDate - 1 - calendar.get(Calendar.DAY_OF_MONTH);
                } else if (day < dueDate) {//dueDate-2 - dueDate
                    area = TYPE_OVER_DATE;
                } else if (day < statementDate) { // dueDate - statementDate
                    area = TYPE_UNCREATE;
                } else { // statementDate - 31
                    int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (day == statementDate || calendar.get(Calendar.HOUR_OF_DAY) > 19) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                    }
                    startMillis = calendar.getTime().getTime();
                    area = dayCount - day + dueDate - 2;
                }
            }
        } else {
            tipView("1", "未传入账单日和还款日");
        }
    }

    private void showDateDialog() {
        if (dueDate != -1 && statementDate != -1) {
            if (area == TYPE_UNCREATE) {
                tipView("1", "账单未生成");
            } else if (area == TYPE_OVER_DATE) {
                tipView("1", "超过可规划划款时间");
            } else {
                dateDialog = new CalendarViewDialog(this, R.style.Theme_Dialog_Scale,
                        startMillis, area, new CalendarViewDialog.OnDateSelectConfirmListener() {
                    @Override
                    public void confirm(List<String> selectDate) {
                        mSelectData = selectDate;
                        dateDialog.dismiss();
                        if (selectDate != null && selectDate.size() > 0) {
                            Collections.sort(mSelectData, new SortByDate());
                            StringBuilder builder = new StringBuilder();
                            for (String date : mSelectData) {
                                builder.append(date.substring(date.length() - 2, date.length())).append(",");
                            }
                            tvRepaymentDate.setText(builder.substring(0, builder.length() - 1));
                        }
                    }
                });
                if (mSelectData != null) {
                    dateDialog.setSelectData(mSelectData);
                }
                dateDialog.show();
            }
        }
    }

    private void initView() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        TextView tvRight = (TextView) findViewById(R.id.right_tv);
        etRepaymentAmount = (EditText) findViewById(R.id.et_new_repayment_amount);
        tvRepaymentMode = (TextView) findViewById(R.id.tv_new_repayment_mode);
        llDate = (LinearLayout) findViewById(R.id.ll_new_repayment_date);
        tvRepaymentDate = (TextView) findViewById(R.id.tv_new_repayment_date);
        etRepaymentCount = (EditText) findViewById(R.id.et_new_repayment_count);
        tvPreview = (TextView) findViewById(R.id.tv_new_repayment_preview);
        previewView = (ConstraintLayout) findViewById(R.id.view_new_repayment_preview);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_new_repayment);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("提交");
        tvRight.setTextColor(getResources().getColor(R.color.blue_21));
        tvTitle.setText("新增计划");
        tvRight.setOnClickListener(this);
        tvRepaymentMode.setOnClickListener(this);
        tvRepaymentDate.setOnClickListener(this);
        tvPreview.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        adapter = new NewRepaymentAdapter(this);
        mRecyclerView.setAdapter(adapter);
        etRepaymentAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    String temp = s.toString();
                    if (temp.startsWith("00")) {
                        s.delete(0, 1);
                        return;
                    }
                    if (temp.startsWith(".")) {
                        s.replace(0, 1, "0.");
                        return;
                    }
                    int index = temp.lastIndexOf(".");
                    if (index > -1 && temp.length() - index - 1 > 2) {
                        s.delete(s.length() - 1, s.length());
                        return;
                    }
                    double inputAmount = Double.parseDouble(s.toString());
                    if (inputAmount > 50000) {
                        s.replace(0, s.length(), "50000");
                    }
                }
            }
        });
        etRepaymentCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (area == TYPE_UNCREATE) {
                        tipView("1", "账单未生成");
                    } else if (area == TYPE_OVER_DATE) {
                        tipView("1", "超过可规划划款时间");
                    }
                }
            }
        });
        etRepaymentCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {//TODO
                if (s != null && s.length() > 0) {
                    if (s.toString().startsWith("0")) {
                        s.delete(0, 1);
                        return;
                    }
                    if (type == TYPE_CUSTOM) {
                        int inputCount = Integer.parseInt(s.toString());
                        if (mSelectData == null) {
                            s.delete(0, s.length());
                        } else if (mSelectData.size() * dayNum < inputCount) {
                            tipView("1", "最大笔数不能超过" + (mSelectData.size() * dayNum));
                            s.replace(0, s.length(), String.valueOf(mSelectData.size() * dayNum));
                        }
                    } else {
                        int inputCount = Integer.parseInt(s.toString());
                        if (area > 0) {
                            Log.e("inputCount:" + inputCount, "area:" + area);
                            if (inputCount > maxnum) {
                                tipView("1", "最大笔数不能超过" + maxnum);
                                s.replace(0, s.length(), maxnum + "");
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean canPreView() {
        if (TextUtils.isEmpty(etRepaymentAmount.getText().toString())) {
            tipView("1", "请正确填写还款金额");
            return false;
        }
        if (TextUtils.isEmpty(etRepaymentCount.getText().toString())) {
            tipView("1", "请正确填写拆分笔数");
            return false;
        }
        if (type == TYPE_CUSTOM && mSelectData == null) {
            tipView("1", "请选择还款日期");
            return false;
        }
        return true;
    }

    private void showModePopup() {
        if (popWindow == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.pop_select_photo, null);
            initModeView(view);
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            popWindow.setContentView(view);
            popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
            popWindow.setFocusable(true);
            popWindow.setOutsideTouchable(true);
            popWindow.setBackgroundDrawable(new BitmapDrawable());
            popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        popWindow.showAtLocation(previewView, Gravity.CENTER, 0, 0);
    }

    private void initModeView(View view) {
        view.findViewById(R.id.cancel).setOnClickListener(this);
        TextView first = (TextView) view.findViewById(R.id.photograph);
        TextView second = (TextView) view.findViewById(R.id.albums);
        first.setText("智能模式");
        first.setTextColor(0xFF333333);
        first.setOnClickListener(this);
        second.setText("自定义模式");
        second.setTextColor(0xFF333333);
        second.setOnClickListener(this);
    }

    private boolean paramsEquals() {
        if (previewParams.size() > 0) {
            String repayMoney = previewParams.get("repay_money");
            if (!TextUtils.equals(repayMoney, String.valueOf(Double.parseDouble(etRepaymentAmount.getText().toString()) * 100))) {
                tipView("1", "您修改了金额，请重新预览");
                return false;
            }
            if (!TextUtils.equals(previewParams.get("modeltype"), String.valueOf(type))) {
                tipView("1", "您修改了还款模式，请重新预览");
                return false;
            } else if (type == TYPE_CUSTOM) {
                if (!TextUtils.equals(previewParams.get("paymenttime"), tvRepaymentDate.getText().toString())) {
                    tipView("1", "您修改了还款日期，请重新预览");
                    return false;
                }
            }
            if (!TextUtils.equals(previewParams.get("repay_number"), etRepaymentCount.getText().toString())) {
                tipView("1", "您修改了还款笔数，请重新预览");
                return false;
            }
            return true;
        } else {
            tipView("1", "请先进行预览");
            return false;
        }
    }

    /**
     * 获取预览计划
     */
    private void preView() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
        tvPreview.requestFocus();
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("NCardId"));
        params.put("repay_money", String.valueOf(Double.parseDouble(etRepaymentAmount.getText().toString()) * 100));
        params.put("modeltype", String.valueOf(type));
        params.put("repay_number", etRepaymentCount.getText().toString());
        if (type == TYPE_CUSTOM) {
            params.put("paymenttime", tvRepaymentDate.getText().toString());
        } else {
            params.put("paymenttime", "");
        }
        Connect.getInstance().post(getApplicationContext(), IService.BILLPREVIEW, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                previewView.setVisibility(View.VISIBLE);
                RepaymentPlanResponse response = (RepaymentPlanResponse) JsonUtils.parse((String) result, RepaymentPlanResponse.class);
                if (response.getResult().getCode() == 10000) {
                    tvPreview.setText("重新计划");
                    previewParams.clear();
                    previewParams.put("cardid", getIntent().getStringExtra("NCardId"));
                    previewParams.put("repay_money", String.valueOf(Double.parseDouble(etRepaymentAmount.getText().toString()) * 100));
                    previewParams.put("modeltype", String.valueOf(type));
                    previewParams.put("repay_number", etRepaymentCount.getText().toString());
                    previewParams.put("paymenttime", tvRepaymentDate.getText().toString());
                    List<RepaymentPlanResponse.RepaymentPlanInfo> data = response.getData();
                    Collections.sort(data, new SortByString());
                    adapter.setData(data);
                } else {
                    ToastUtil.showTextToas(getApplicationContext(),response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(),message);
            }
        });
    }

    /**
     * 提交计划
     */
    private void confirm() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("NCardId"));
        Connect.getInstance().post(getApplicationContext(), IService.ADD_CARD_BILL, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                AddBillResponse response = (AddBillResponse) JsonUtils.parse((String) result, AddBillResponse.class);
                if (response.getResult().getCode() == 10000) {
                    Intent intent = new Intent(NewRepaymentPlanActivity.this, RepaymentPlanDetailActivity.class);
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
                    intent.putExtra("billId", response.getData().getBill_id());
                    startActivity(intent);
                    sendBroadcast(new Intent().setAction("refresh"));
                    finish();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(),response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(),message);
            }
        });
    }

    private long getStringToDate1(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    private class SortByDate implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            int leftInt = Integer.parseInt(lhs);
            int rightInt = Integer.parseInt(rhs);
            return leftInt > rightInt ? 1 : -1;
        }
    }

    private class SortByString implements Comparator<RepaymentPlanResponse.RepaymentPlanInfo> {
        @Override
        public int compare(RepaymentPlanResponse.RepaymentPlanInfo lhs, RepaymentPlanResponse.RepaymentPlanInfo rhs) {
            long lhsStamp = getStringToDate1(lhs.getTime());
            long rhsStamp = getStringToDate1(rhs.getTime());
            return lhsStamp >= rhsStamp ? 1 : -1;
        }
    }

    public void tipView(final String flag, String msg) {
        if (backDialog == null) {
            backDialog = new BackDialog("", msg, "确定", NewRepaymentPlanActivity.this,
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