package com.kym.ui.activity.mendianma;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.ZDActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 门店码输入金额
 * Created by sunmiaolong on 2019/6/20.
 */

public class DynamicQRcodeActivity extends BaseActivity implements View.OnClickListener {
    private EditText money;
    private LinearLayout btn_queding;
    private Intent intent;
    private SPConfig spConfig;
    private BackDialog backDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_qrcode);
        initView();
    }

    public void initView() {
        ImageView right_tv = (ImageView) findViewById(R.id.head_img_right);
        right_tv.setVisibility(View.VISIBLE);
        right_tv.setOnClickListener(this);
        ImageView imageV_fanhui = (ImageView) findViewById(R.id.head_img_left);
        imageV_fanhui.setVisibility(View.VISIBLE);
        imageV_fanhui.setOnClickListener(this);
        TextView textV_title = (TextView) findViewById(R.id.head_text_title);
        textV_title.setVisibility(View.VISIBLE);
        textV_title.setText("输入金额");
        spConfig = SPConfig.getInstance(this);
        money = (EditText) findViewById(R.id.money);
        btn_queding = (LinearLayout) findViewById(R.id.btn_queding);
        btn_queding.setOnClickListener(this);
        money.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        money.setText(s);
                        money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    money.setText(s);
                    money.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        money.setText(s.subSequence(0, 1));
                        money.setSelection(1);
                        return;
                    }
                }

                if (s.toString().length() < 1) {
                    btn_queding.setBackgroundDrawable(getResources().getDrawable(R.drawable.submit_btn2));
                    btn_queding.setEnabled(false);
                } else {
                    btn_queding.setBackgroundDrawable(getResources().getDrawable(R.drawable.submit_btn));
                    btn_queding.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                DynamicQRcodeActivity.this.finish();
                break;
            case R.id.head_img_right:
                startActivity(new Intent(getApplicationContext(),ZDActivity.class));
                break;
            case R.id.btn_queding:
                if (money.getText().toString().equals("")) {
                    tipView("1","请先输入收款金额");
                }else{
                    getQRcode();
                }
                break;
        }
    }

    private void getQRcode() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("resmark", IService.RESMARK);
        params.put("uid", spConfig.getUserAllInfoNew().getUid());
        params.put("amount", money.getText().toString());
        Connect.getInstance().post(this.getApplicationContext(), IService.QRCODE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
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
                        if (statusName.equals("success")){
                            String data = obj2.get("data").toString();
                            String onumber = obj2.get("onumber").toString();
                            intent = new Intent(DynamicQRcodeActivity.this, QRcodeActivity.class);
                            intent.putExtra("imgUrl", data);
                            intent.putExtra("onumber", onumber);
                            intent.putExtra("mPay", money.getText().toString());
                            startActivity(intent);
                            finish();
                        } else if(statusName.equals("display_error")){
                            String statusMsg = obj2.get("statusMsg").toString();
                            tipView("1",statusMsg);
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
                dialogUtil.dismiss();
                tipView("1",message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", DynamicQRcodeActivity.this,
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
