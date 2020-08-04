package com.kym.ui.activity.lingqian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.CourseActivity;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import static com.zzss.jindy.appconfig.Clone.APP_NAME;


/**
 * 我的退款
 *
 * @author sun
 * @date 2019/12/26
 */

public class LingQianActivity extends BaseActivity implements View.OnClickListener {

    private TextView money, chongzhi, tixian, wenti, fuwu, tvRight;
    private String encrypt;
    private Intent intent;
    private String moneyString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ling_qian);
        initView();
        initHead();
    }

    @Override
    protected void onStart() {
        super.onStart();
        lingqian();
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        tvRight = findViewById(R.id.right_tv);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(this);
        tvRight.setText("退款明细");
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tvTitle.setText("我的退款");
    }

    private void initView() {
        money = findViewById(R.id.money);
        chongzhi = findViewById(R.id.chongzhi);
        tixian = findViewById(R.id.tixian);
        wenti = findViewById(R.id.wenti);
        fuwu = findViewById(R.id.fuwu);
        money.setOnClickListener(this);
        chongzhi.setOnClickListener(this);
        tixian.setOnClickListener(this);
        wenti.setOnClickListener(this);
        fuwu.setOnClickListener(this);
        fuwu.setText("本服务由" + APP_NAME + "提供");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.wenti:
                startActivity(new Intent(LingQianActivity.this, CourseActivity.class));
                break;
            case R.id.tixian:
                intent = new Intent(LingQianActivity.this, LingQianTiXianActivity.class);
                intent.putExtra("yue", moneyString);
                startActivity(intent);
                break;
            case R.id.chongzhi:
                lingqian_suiji();
                break;
            case R.id.right_tv:
                startActivity(new Intent(LingQianActivity.this, LingQianListActivity.class));
                break;
        }
    }

    private void lingqian() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
        Connect.getInstance().post(this.getApplicationContext(), IService.WODE_LINGQIAN, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String data = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(data);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        moneyString = obj2.getString("account");
                        money.setText("¥ " + (Double.parseDouble(obj2.getString("account"))));
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    private void lingqian_suiji() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
        Connect.getInstance().post(this.getApplicationContext(), IService.WODE_SUIJISHU, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String data = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(data);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        encrypt = obj2.getString("encrypt");
                        Intent intent = new Intent(LingQianActivity.this, LingQianMoneyActivity.class);
                        intent.putExtra("encrypt", encrypt);
                        startActivity(intent);
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }


}
