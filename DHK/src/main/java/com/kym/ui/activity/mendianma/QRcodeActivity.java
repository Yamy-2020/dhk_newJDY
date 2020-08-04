package com.kym.ui.activity.mendianma;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.util.Connect;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 门店码二维码
 * Created by sunmiaolong on 2019/6/20.
 */

public class QRcodeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back, img;
    private TextView mPay, btn_pay1;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        initView();
        TimerHandler.postDelayed(myTimerRun, 1000);
    }

    public void initView() {
        String url = getIntent().getStringExtra("imgUrl");
        back = (ImageView) findViewById(R.id.back);
        img = (ImageView) findViewById(R.id.img);
        mPay = (TextView) findViewById(R.id.mPay);
        btn_pay1 = (TextView) findViewById(R.id.btn_pay1);
        back.setOnClickListener(this);
        btn_pay1.setOnClickListener(this);
        String num = getIntent().getStringExtra("mPay");
        if (num.indexOf(".") != -1) {
            //获取小数点的位置
            int num1 = 0;
            //找到小数点在字符串中的位置,找到返回一个int类型的数字,不存在的话返回 -1
            num1 = num.indexOf(".");

            String dianAfter = num.substring(num1 + 1, num.length());//输入100.30,dianAfter = 30.
            if (dianAfter.length() == 1) {
                mPay.setText("￥" + getIntent().getStringExtra("mPay") + "0");
            } else {
                mPay.setText("￥" + getIntent().getStringExtra("mPay"));
            }
        } else {
            mPay.setText("￥" + getIntent().getStringExtra("mPay") + ".00");
        }
        Glide.with(this).load(url).placeholder(R.drawable.default_image).into(img);
    }

    Handler TimerHandler = new Handler();                   //创建一个Handler对象

    Runnable myTimerRun = new Runnable()                //创建一个runnable对象

    {

        @Override
        public void run()

        {

            //要做的事情这里

            getQRcode();
            TimerHandler.postDelayed(this, 1000);

        }


    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                QRcodeActivity.this.finish();
                break;
            case R.id.btn_pay1:
                Intent intent = new Intent(QRcodeActivity.this, DynamicQRcodeActivity.class);
                startActivity(intent);
                QRcodeActivity.this.finish();
                break;
        }
    }


    private void getQRcode() {
        String onumber = getIntent().getStringExtra("onumber");
        HashMap<String, String> params = new HashMap<>();
        params.put("onumber", onumber);
        Connect.getInstance().post(this.getApplicationContext(), IService.YINLIAN_STATUS, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        String statusName = obj2.get("statusName").toString();
                        if (statusName.equals("success_pay")) {
                            tipView("1","支付成功");
                            img.setImageResource(R.drawable.dui);
                            btn_pay1.setVisibility(View.GONE);
                            mPay.setText("支付成功");
                            mPay.setTextSize(25);
                            TimerHandler.removeCallbacks(myTimerRun);
                        } else if (statusName.equals("fail_pay")) {
                            tipView("1","支付失败");
                            img.setImageResource(R.drawable.cuo);
                            mPay.setText("支付失败");
                            mPay.setTextSize(25);
                            TimerHandler.removeCallbacks(myTimerRun);
                            btn_pay1.setVisibility(View.GONE);
                        }
                    } else {
                        tipView("1",msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
              tipView("1",message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        //将线程销毁掉
        TimerHandler.removeCallbacks(myTimerRun);
        super.onDestroy();
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", QRcodeActivity.this,
                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag.equals("1")) {
                    finish();
                }
                backDialog.dismiss();
            }
        });
        backDialog.setCancelable(false);
        backDialog.show();
    }

}
