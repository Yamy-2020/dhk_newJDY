package com.kym.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kym.ui.R;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.zzss.jindy.appconfig.Clone;

import java.util.HashMap;

/**
 * 测试页面
 *
 * @author sun
 * @date 2019/12/3
 */

public class CommonTestActivity extends Activity implements View.OnClickListener {

    private EditText test, test1_1, test1_2, test2_1, test2_2,
            test3_1, test3_2, test4_1, test4_2, test5_1, test5_2;
    private TextView tip, btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_test);
        initView();
    }

    private void initView() {
        test = (EditText) findViewById(R.id.test);
        test1_1 = (EditText) findViewById(R.id.test1_1);
        test1_2 = (EditText) findViewById(R.id.test1_2);
        test2_1 = (EditText) findViewById(R.id.test2_1);
        test2_2 = (EditText) findViewById(R.id.test2_2);
        test3_1 = (EditText) findViewById(R.id.test3_1);
        test3_2 = (EditText) findViewById(R.id.test3_2);
        test4_1 = (EditText) findViewById(R.id.test4_1);
        test4_2 = (EditText) findViewById(R.id.test4_2);
        test5_1 = (EditText) findViewById(R.id.test5_1);
        test5_2 = (EditText) findViewById(R.id.test5_2);
        tip = (TextView) findViewById(R.id.tip);
        btn = (TextView) findViewById(R.id.login_btn_ok);
        btn.setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tvTitle.setText("测试");
    }

    /**
     * 获取银行卡签约通道
     */
    private void getCardSign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        if (!test1_1.getText().toString().equals("")) {
            params.put(test1_1.getText().toString(), test1_2.getText().toString());
        }
        if (!test2_1.getText().toString().equals("")) {
            params.put(test2_1.getText().toString(), test2_2.getText().toString());
        }
        if (!test3_1.getText().toString().equals("")) {
            params.put(test3_1.getText().toString(), test3_2.getText().toString());
        }
        if (!test4_1.getText().toString().equals("")) {
            params.put(test4_1.getText().toString(), test4_2.getText().toString());
        }
        if (!test5_1.getText().toString().equals("")) {
            params.put(test5_1.getText().toString(), test5_2.getText().toString());
        }
        Connect.getInstance().post(getApplicationContext(), Clone.HOST + "api.php/" + test.getText().toString(), params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                android.util.Log.d("信用卡列表签约", result.toString());
                // 0-已签约  1-进件  2-绑卡
                dialogUtil.dismiss();
                tip.setText(result.toString());
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.login_btn_ok:
                getCardSign();
                break;
        }
    }
}
