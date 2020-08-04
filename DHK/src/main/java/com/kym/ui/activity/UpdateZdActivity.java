package com.kym.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;


public class UpdateZdActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_zd);
        initHead();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("同步账单");
        ImageView right_tv = (ImageView) findViewById(R.id.head_img_right);
        right_tv.setVisibility(View.VISIBLE);
        right_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
