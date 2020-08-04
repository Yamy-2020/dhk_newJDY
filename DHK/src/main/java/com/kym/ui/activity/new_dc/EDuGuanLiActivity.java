package com.kym.ui.activity.new_dc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.LineChartView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EDuGuanLiActivity extends BaseActivity implements View.OnClickListener {

    LineChartView lineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_guan_li);
        initHead();
        initView();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("额度管理");
    }

    private void initView() {
        lineChartView = findViewById(R.id.line_chart);
        String[] xdate = new String[]{"11.03", "11.04", "11.05", "11.06", "11.07", "11.08", "11.09"};
        String[] ydata = lineChartView.getFundWeekYdata("5.00", "1.00");
        float[] data1 = new float[]{4.00f, 2.00f, 3.40f, 2.50f, 5.00f, 1.50f, 5f};
        lineChartView.setData(xdate, ydata, data1);
    }

    public static String bigDecimalMoney(String money) {
        BigDecimal bd = new BigDecimal(Integer.parseInt(money));
        DecimalFormat df = new DecimalFormat(",###,###.00");
        return df.format(bd);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }
}
