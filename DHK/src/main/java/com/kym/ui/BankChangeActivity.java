package com.kym.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.adapter.SelectDateAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import widget.CustomPopWindow;

/**
 * 修改账单日还款日
 *
 * @author sun
 * @date 2019/12/3
 */

public class BankChangeActivity extends BaseActivity implements View.OnClickListener {

    private final int TYPE_BILL = 0;
    private final int TYPE_REPAYMENT = 1;
    private TextView tvBills;
    private TextView tvRepayment;
    private CustomPopWindow datePopWindow;
    private BackDialog backDialog;
    private ImageView head_img_right;
    private TopRightMenu mTopRightMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_change);
        initUI();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (datePopWindow != null && datePopWindow.isShowing()) {
                datePopWindow.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.tv_change_bank_confirm:
                // 修改信息
                if (canConfirm()) {
                    confirm();
                }
                break;
            case R.id.tv_change_bank_bills:
                // 账单日选择
                showDatePop(TYPE_BILL);
                break;
            case R.id.tv_change_bank_repay:
                // 还款日
                showDatePop(TYPE_REPAYMENT);
                break;
            case R.id.head_img_right:
                mTopRightMenu = new TopRightMenu(BankChangeActivity.this);
                List<MenuItem> menuItems = new ArrayList<>();
//                menuItems.add(new MenuItem("解绑"));
                menuItems.add(new MenuItem("修改额度"));
                menuItems.add(new MenuItem("提额明细"));
                mTopRightMenu
                        .setHeight(320)     //默认高度480  ,三个是450
                        .showIcon(false) //默认宽度wrap_content
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                if (position == 0) {
                                 /*   Intent intent = new Intent(getApplicationContext(), JieBangCardActivity.class);
                                    intent.putExtra("NCardId", getIntent().getStringExtra("NCardId"));
                                    intent.putExtra("NBankName", getIntent().getStringExtra("NBankName"));
                                    intent.putExtra("NBankNo", getIntent().getStringExtra("NBankNo"));
                                    intent.putExtra("NLogoUrl", getIntent().getStringExtra("NLogoUrl"));
                                    startActivity(intent);*/

                                    Intent intent = new Intent(getApplicationContext(), BankChangeEduActivity.class);
                                    intent.putExtra("NCardId", getIntent().getStringExtra("NCardId"));
                                    intent.putExtra("NBankName", getIntent().getStringExtra("NBankName"));
                                    intent.putExtra("NBankNo", getIntent().getStringExtra("NBankNo"));
                                    intent.putExtra("NLogoUrl", getIntent().getStringExtra("NLogoUrl"));
                                    startActivity(intent);
                                } else if (position == 1) {
                                    Intent intent = new Intent(getApplicationContext(), TieListActivity.class);
                                    intent.putExtra("NCardId", getIntent().getStringExtra("NCardId"));
                                    startActivity(intent);
                                }/*else {

                                }*/
                            }
                        })
                        .showAsDropDown(head_img_right, -140, 0);
                break;
            default:
                break;
        }
    }

    private void initUI() {
        TextView title = (TextView) findViewById(R.id.head_text_title);
        TextView tvCardName = (TextView) findViewById(R.id.tv_change_bank_card);
        CircleImageView ivBankLogo = (CircleImageView) findViewById(R.id.iv_change_bank_card);
        TextView tvBankNumber = (TextView) findViewById(R.id.tv_change_bank_card_number);
        tvBills = (TextView) findViewById(R.id.tv_change_bank_bills);
        tvRepayment = (TextView) findViewById(R.id.tv_change_bank_repay);
        findViewById(R.id.tv_change_bank_confirm).setOnClickListener(this);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        title.setText("信用卡信息修改");
        head_img_right = (ImageView) findViewById(R.id.head_img_right);
//        head_img_right.setBackground(new Color(R.drawable.morexf));
        head_img_right.setVisibility(View.VISIBLE);
        head_img_right.setOnClickListener(this);
        tvRepayment.setOnClickListener(this);
        tvBills.setOnClickListener(this);
        tvCardName.setText(getIntent().getStringExtra("NBankName"));
        Glide.with(this).load(getIntent().getStringExtra("NLogoUrl")).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankNumber.setText(getIntent().getStringExtra("NBankNo").substring(0, 4)
                + " **** **** " + getIntent().getStringExtra("NBankNo").substring(getIntent().getStringExtra("NBankNo").length() - 4, getIntent().getStringExtra("NBankNo").length()));
    }

    private void showDatePop(int type) {
        View view = initDateView(type);
        datePopWindow = new CustomPopWindow.Builder(this).setAnimationStyle(android.R.style.Animation_InputMethod).setFocusable(true)
                .setOutsideFocusable(true).setContentView(view).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .build();
        datePopWindow.showAtLocation(tvRepayment, Gravity.BOTTOM);
    }

    private View initDateView(final int type) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_select_date, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_select_date);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider_1dp));
        recyclerView.addItemDecoration(decoration);
        SelectDateAdapter adapter = new SelectDateAdapter(this);
        adapter.setListener(new SelectDateAdapter.OnDateSelectListener() {
            @Override
            public void dateSelect(int position) {
                if (type == TYPE_BILL) {
                    tvBills.setText(String.format("%d日", position));
                } else if (type == TYPE_REPAYMENT) {
                    tvRepayment.setText(String.format("%d日", position));
                }
                datePopWindow.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    private boolean canConfirm() {
        if (TextUtils.isEmpty(tvBills.getText().toString())) {
            tipView("1", "请选择还款日");
            return false;
        }
        if (TextUtils.isEmpty(tvRepayment.getText().toString())) {
            tipView("1", "请选择账单日");
            return false;
        }
        return true;
    }

    /**
     * 修改信用卡信息
     */
    private void confirm() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("NCardId"));
        String billsString = tvBills.getText().toString();
        if (billsString.length() < 3) {
            billsString = "0".concat(billsString);
        }
        params.put("bills", billsString.substring(0, billsString.length() - 1));
        String repaymentString = tvRepayment.getText().toString();
        if (repaymentString.length() < 3) {
            repaymentString = "0".concat(repaymentString);
        }
        params.put("repayment", repaymentString.substring(0, repaymentString.length() - 1));
        Connect.getInstance().post(getApplicationContext(), IService.CHANGE_BANK_INFO, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old info = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (info.getResult().getCode() == 10000) {
                    tipView("2", "修改成功");
                } else if (info.getResult().getCode() == 9999) {
                    tipView("1", info.getResult().getMsg());
                } else {
                    tipView("1", info.getResult().getMsg());
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
        backDialog = new BackDialog("", msg, "确定", BankChangeActivity.this,
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