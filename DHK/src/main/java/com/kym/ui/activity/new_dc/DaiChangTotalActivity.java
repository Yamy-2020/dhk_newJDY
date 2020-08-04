package com.kym.ui.activity.new_dc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littlejie.circleprogress.CircleProgress;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;

public class DaiChangTotalActivity extends BaseActivity implements View.OnClickListener {

    private CircleProgress mCircleProgress3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_chang_total);
        initView();
        initHead();
    }

    private void initView() {
        mCircleProgress3 = findViewById(R.id.circle_progress_bar3);
        //在代码中动态改变渐变色，可能会导致颜色跳跃
        mCircleProgress3.setMaxValue(10000);
        mCircleProgress3.setValue(10000);
        LinearLayout go = findViewById(R.id.go);
        go.setOnClickListener(this);
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("贷偿总量");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.go:
                startActivity(new Intent(getApplicationContext(),PaiHangActivity.class));
                break;
        }
    }
}
