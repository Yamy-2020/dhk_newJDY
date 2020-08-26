package com.kym.ui.activity.bpbro_home.bpbro_hk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.kym.ui.BackDialog;
import com.kym.ui.Course1Activity;
import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

/**
 * 还款信用卡列表
 *
 * @author sun
 * @date 2019/12/3
 */
public class HK_BankList_Activity extends BaseActivity implements View.OnClickListener {

    private final int FANHUICODE = 2;
    private NewBankListAdapter adapter;
    private RelativeLayout lay_foot_add;
    private Intent intent;
    private String type;
    private TextView head_img_right;
    private TopRightMenu mTopRightMenu;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hk__bank_list);
        initHead();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getHK_bank_list();
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        if (getIntent().getStringExtra("type").equals("X")) {
            type = "2";
            tvTitle.setText("选择信用卡(小额)");
        } else if (getIntent().getStringExtra("type").equals("D")) {
            type = "1";
            tvTitle.setText("选择信用卡(大额)");
        } else {
            type = "3";
            tvTitle.setText("选择信用卡(新小额)");
        }
        head_img_right = findViewById(R.id.head_img_right);
        head_img_right.setVisibility(View.VISIBLE);
        head_img_right.setOnClickListener(this);
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
        adapter = new NewBankListAdapter(this, new NewBankListAdapter.OnBankCardClickListener() {
            @Override
            public void cardClick(BankListResponse.BankInfo bankInfo) {
                if (bankInfo.getIs_sign().equals("1")) {
                    /**
                     *
                     * 签约
                     */
//                    intent = new Intent(getApplicationContext(), HK_Sign_Activity.class);
//                    intent.putExtra("bankData", bankInfo);
//                    startActivity(intent);
                    intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                    intent.putExtra("WEB_TITLE", "信用卡签约");
                    intent.putExtra("WEB_URL", Clone.HOST+"Bpbro_Sun/view/bpbro_hk/bpbro_hk_sign_pass.html?bank_name=" + bankInfo.getBank_name() +
                            "&bank_no=" + bankInfo.getBank_no() + "&logo_url=" + bankInfo.getLogo_url() + "&cardid=" + bankInfo.getCardid() +
                            "&omid=" + Clone.OMID + "&token=" + LoginActivity.accessToken_xin);
                    startActivity(intent);
                } else {
                    if (bankInfo.getIs_plan().equals("1")) {
                        /**
                         * 查看计划
                         */
                        intent = new Intent(getApplicationContext(), HK_planinfoActivity.class);
                        intent.putExtra("type", "only");
                        intent.putExtra("NCardId", bankInfo.getCardid());
                        startActivity(intent);
                    } else {
                        /**
                         * 制定计划
                         */
                        intent = new Intent(getApplicationContext(), HK_planActivity.class);
                        intent.putExtra("bankData", bankInfo);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }
                }
            }
        }, "HK");
        mRecyclerView.setAdapter(adapter);
        lay_foot_add = (RelativeLayout) findViewById(R.id.lay_foot_add);
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
            case R.id.head_img_right:
                mTopRightMenu = new TopRightMenu(HK_BankList_Activity.this);
                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem("帮助中心"));
                menuItems.add(new MenuItem("计划列表"));
                mTopRightMenu
                        .setHeight(300)     //默认高度480
                        .showIcon(false) //默认宽度wrap_content
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                if (position == 0) {
                                    intent = new Intent(HK_BankList_Activity.this, Course1Activity.class);
                                    intent.putExtra("type", "hk");
                                    intent.putExtra("title", "还款问题");
                                    startActivity(intent);
                                } else {
                                    intent = new Intent(getApplicationContext(), HK_planinfoActivity.class);
                                    intent.putExtra("type", "all");
                                    startActivity(intent);
                                }
                            }
                        })
                        .showAsDropDown(head_img_right, -140, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FANHUICODE) {
            getHK_bank_list();
        }
    }

    /**
     * 获取还款信用卡列表
     */
    private void getHK_bank_list() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("type", getIntent().getStringExtra("type"));
        Connect.getInstance().post(getApplicationContext(), IService.NEWXINYONGKA, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                BankListResponse response = (BankListResponse) JsonUtils.parse((String) result, BankListResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<BankListResponse.BankInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                        lay_foot_add.setVisibility(View.VISIBLE);
                    } else {
                        adapter.clearData();
                        lay_foot_add.setVisibility(View.VISIBLE);
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", HK_BankList_Activity.this,
                            R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(HK_BankList_Activity.this, LoginActivity.class));

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
}
