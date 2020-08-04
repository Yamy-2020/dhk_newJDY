package com.kym.ui.activity.bpbro_home.bpbro_yk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.kym.ui.Course1Activity;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.XiaoFeiCardAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.DaiChangCardInfo;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 养卡计划列表详情
 *
 * @author sun
 * @date 2019/12/3
 */

public class XFplanlistdetailActivity extends BaseActivity implements View.OnClickListener {

    private TopRightMenu mTopRightMenu;
    private XiaoFeiCardAdapter adapter;
    private ImageView head_img_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfplanlistdetail);
        initView();
        initHead();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPreview();
    }

    private void initHead() {
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
        head_img_right = (ImageView) findViewById(R.id.head_img_right);
        head_img_right.setVisibility(View.VISIBLE);
        head_img_right.setOnClickListener(this);
        head_img_right.setBackgroundResource(R.drawable.morexf);
        tvTitle.setText("养卡消费计划详情");
    }

    private void initView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new XiaoFeiCardAdapter(this, new XiaoFeiCardAdapter.OnDaiChangCardInfo() {
            @Override
            public void daichangClick(DaiChangCardInfo.DaiChangCardData dachangcardInfo) {

            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 获取消费计划详情
     */
    private void getPreview() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("bill_id", getIntent().getStringExtra("bill_id"));
        Connect.getInstance().post(getApplicationContext(), IService.XF_PLAN_LIST_DETAIL, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                DaiChangCardInfo response = (DaiChangCardInfo) JsonUtils.parse((String) result, DaiChangCardInfo.class);
                dialogUtil.dismiss();
                if (response.getResult().getCode() == 10000) {
                    List<DaiChangCardInfo.DaiChangCardData> data = response.getData();
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

    /**
     * 获取消费计划详情
     */
    private void stopPlan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("bill_id", getIntent().getStringExtra("bill_id"));
        Connect.getInstance().post(getApplicationContext(), IService.XF_STOP_PLAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                DaiChangCardInfo response = (DaiChangCardInfo) JsonUtils.parse((String) result, DaiChangCardInfo.class);
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.head_img_right:
                mTopRightMenu = new TopRightMenu(XFplanlistdetailActivity.this);
                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem("帮助中心"));
                menuItems.add(new MenuItem("终止计划"));
                mTopRightMenu
                        .setHeight(300)     //默认高度480
                        .showIcon(false) //默认宽度wrap_content
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                if (position == 0) {
                                    Intent intent = new Intent(XFplanlistdetailActivity.this, Course1Activity.class);
                                    intent.putExtra("type", "xf");
                                    intent.putExtra("title", "消费计划");
                                    startActivity(intent);
                                } else {
                                    stopPlan();
                                }
                            }
                        })
                        .showAsDropDown(head_img_right, -140, 0);
                break;
        }
    }
}
