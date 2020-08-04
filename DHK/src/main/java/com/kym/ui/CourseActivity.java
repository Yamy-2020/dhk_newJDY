package com.kym.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.CourseAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.CourseResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 新手教程
 *
 * @author sun
 * @date 2019/12/3
 */
public class CourseActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout wenti1, wenti2, wenti3, wenti4, wenti5, wenti6, wenti7, wenti8;
    private CourseAdapter adapter;
    private LinearLayout zanwu;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        initView();
        initHead();
        getInfo("hot"); //热门问题
    }

    private void initView() {

        zanwu = (LinearLayout) findViewById(R.id.zanwu);
        wenti1 = (LinearLayout) findViewById(R.id.wenti1);
        wenti2 = (LinearLayout) findViewById(R.id.wenti2);
        wenti3 = (LinearLayout) findViewById(R.id.wenti3);
//        wenti4 = (LinearLayout) findViewById(R.id.wenti4);
        wenti5 = (LinearLayout) findViewById(R.id.wenti5);
        wenti6 = (LinearLayout) findViewById(R.id.wenti6);
        wenti7 = (LinearLayout) findViewById(R.id.wenti7);
        wenti8 = (LinearLayout) findViewById(R.id.wenti8);
        wenti1.setOnClickListener(this);
        wenti2.setOnClickListener(this);
        wenti3.setOnClickListener(this);
//        wenti4.setOnClickListener(this);
        wenti5.setOnClickListener(this);
        wenti6.setOnClickListener(this);
        wenti7.setOnClickListener(this);
        wenti8.setOnClickListener(this);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseAdapter(this, new CourseAdapter.OnCurse() {
            @Override
            public void kj_card_list(CourseResponse.CourseInfo courseInfo) {

            }
        });
        mRecyclerView.setAdapter(adapter);
        zanwu.setVisibility(View.GONE);

    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("新手教程");
        findViewById(R.id.gone).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.wenti1:
                tiaozhuan("sm", "实名问题");
                break;
            case R.id.wenti2:
                tiaozhuan("sk", "刷卡问题");
                break;
            case R.id.wenti3:
                tiaozhuan("hk", "还款问题");
                break;
//            case R.id.wenti4:
//                tiaozhuan("fr","分润问题");
//                break;
            case R.id.wenti5:
                tiaozhuan("rx", "可信分");
                break;
            case R.id.wenti6:
                tiaozhuan("zd", "账单问题");
                break;
            case R.id.wenti7:
                tiaozhuan("xf", "消费计划");
                break;
            case R.id.wenti8:
                tiaozhuan("qt", "其他问题");
                break;
        }
    }

    private void tiaozhuan(String age, String name) {
        intent = new Intent(CourseActivity.this, Course1Activity.class);
        intent.putExtra("type", age);
        intent.putExtra("title", name);
        startActivity(intent);
    }

    /**
     * 获取新手教程的信息
     */
    private void getInfo(String type) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("mark", "ksi8wjk");
        Connect.getInstance().post(this.getApplicationContext(), IService.Course, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                CourseResponse response = (CourseResponse) JsonUtils.parse((String) result, CourseResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<CourseResponse.CourseInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                        zanwu.setVisibility(View.GONE);
                    } else {
                        adapter.clearData();
                        zanwu.setVisibility(View.VISIBLE);
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
