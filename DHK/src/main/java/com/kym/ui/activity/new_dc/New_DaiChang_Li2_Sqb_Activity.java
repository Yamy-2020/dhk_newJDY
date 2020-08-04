package com.kym.ui.activity.new_dc;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;

/**
 * 申请临时额度
 *
 * @author sun
 * @date 2019/12/25
 */

public class New_DaiChang_Li2_Sqb_Activity extends BaseActivity implements View.OnClickListener {

    private EditText name, mobile, edu;
    private TextView place, xsed, dyw, wd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__dai_chang__li2__sqb);
        initHead();
        initView();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("申请临时额度");
    }

    private void initView() {
        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        edu = (EditText) findViewById(R.id.edu);
        place = (TextView) findViewById(R.id.place);
        xsed = (TextView) findViewById(R.id.xsed);
        dyw = (TextView) findViewById(R.id.dyw);
        wd = (TextView) findViewById(R.id.wd);
        place.setOnClickListener(this);
        xsed.setOnClickListener(this);
        dyw.setOnClickListener(this);
        wd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.place:
                break;
            case R.id.xsed:
                break;
            case R.id.dyw:
                break;
            case R.id.wd:
                break;
        }
    }
}
