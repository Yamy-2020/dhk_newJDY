package com.kym.ui.activity.bpbro_home.bpbro_sk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
/**
 * 刷卡
 *
 * @author sun
 * @date 2019/12/3
 */

public class SK_ShouKuan_Activity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hl__shua_ka);
        initHead();
        initView();
    }

    private void initHead() {
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tvTitle.setText("刷卡");
    }

    private void initView() {
        LinearLayout sk1 = (LinearLayout) findViewById(R.id.sk1);
        LinearLayout sk2 = (LinearLayout) findViewById(R.id.sk2);
        sk1.setOnClickListener(this);
        sk2.setOnClickListener(this);
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
        }
    }

}
