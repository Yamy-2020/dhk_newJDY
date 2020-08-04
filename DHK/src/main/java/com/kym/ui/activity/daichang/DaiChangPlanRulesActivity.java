package com.kym.ui.activity.daichang;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;

public class DaiChangPlanRulesActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_chang_plan_rules);
        initHead();
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
        tvTitle.setText("贷偿规则");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }
}
