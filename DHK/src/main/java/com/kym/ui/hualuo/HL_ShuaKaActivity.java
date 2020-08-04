package com.kym.ui.hualuo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.bpbro_home.bpbro_sk.SK_BankList_Activity;
import com.kym.ui.activity.bpbro_home.bpbro_yk.XiaoFeiActivity;
import com.kym.ui.appconfig.SPConfig;

public class HL_ShuaKaActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hl__shua_ka);
        initHead();
        initView();
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tvTitle.setText("刷卡");
    }

    private void initView() {
        LinearLayout sk1 = findViewById(R.id.sk1);
        LinearLayout sk2 = findViewById(R.id.sk2);
        sk1.setOnClickListener(this);
        sk2.setOnClickListener(this);
        TextView sk = findViewById(R.id.sk);
        TextView xf = findViewById(R.id.xf);
        sk.setText("一键刷卡("+SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getRec()+")");
        xf.setText("规划刷卡("+SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getCon()+")");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.sk1:
                startActivity(new Intent(getApplicationContext(), SK_BankList_Activity.class));
                break;
            case R.id.sk2:
                startActivity(new Intent(getApplicationContext(), XiaoFeiActivity.class));
                break;
        }
    }
}
