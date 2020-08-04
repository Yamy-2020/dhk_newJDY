package com.kym.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.bpbro_home.bpbro_sk.KuaiJieDetailActivity;
import com.kym.ui.activity.bpbro_home.bpbro_hk.HK_planinfoActivity;
import com.kym.ui.activity.bpbro_home.bpbro_yk.XFplaninfoActivity;
import com.kym.ui.activity.daichang.DaiChangPlanRecordActivity;

/**
 * 订单管理
 *
 * @author sun
 * @date 2019/12/3
 */
public class DingDanActivity extends BaseActivity implements View.OnClickListener {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_dan);
        initHead();
        initView();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("订单管理");
    }

    private void initView() {
        findViewById(R.id.dingdan1).setOnClickListener(this);
        findViewById(R.id.dingdan2).setOnClickListener(this);
        findViewById(R.id.dingdan3).setOnClickListener(this);
//        findViewById(R.id.dingdan4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.dingdan1:
                startActivity(new Intent(getApplicationContext(), KuaiJieDetailActivity.class));
                break;
            case R.id.dingdan2:
                intent = new Intent(getApplicationContext(), XFplaninfoActivity.class);
                intent.putExtra("type", "all");
                startActivity(intent);
                break;
            case R.id.dingdan3:
                intent = new Intent(getApplicationContext(), HK_planinfoActivity.class);
                intent.putExtra("type", "all");
                startActivity(intent);
                break;
            /*case R.id.dingdan4:
                startActivity(new Intent(getApplicationContext(), DaiChangPlanRecordActivity.class));

                break;*/
        }
    }
}
