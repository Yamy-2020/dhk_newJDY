package com.kym.ui.activity.rongxinfen;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;

/**
 * 信用规则
 *
 * @author sun
 * @date 2019/12/3
 */
public class XinYong_GuiZe_Activity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_yong_guize);
        initHead();
    }

    private void initHead() {
        TextView tv =  findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("信用规则");
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
