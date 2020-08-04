package com.kym.ui.activity.huankuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.mendianma.DynamicQRcodeActivity;
import com.kym.ui.activity.mendianma.MenDianActivity;
import com.kym.ui.R;
import com.kym.ui.RongHuiWebActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.CardSignAdapter;

import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.info.CardSignResponse;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 代还信用卡签约
 * Created by Sunmiaolong on 2018/7/29.
 * .
 */

public class CardSignActivity extends BaseActivity implements View.OnClickListener {

    private BankListResponse.BankInfo bankInfo;
    private CardSignAdapter adapter;
    private String ID, isEntrySun;
    private String NCardId, NBankName, NLogoUrl, NBankNo;
    private ArrayList<JSONObject> stringArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_sign);
        if (getIntent().getStringExtra("turn_type").equals("banks")) {
            bankInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("bankData");
            NBankName = bankInfo.getBank_name();
            NBankNo = bankInfo.getBank_no();
            NCardId = bankInfo.getCardid();
            NLogoUrl = bankInfo.getLogo_url();
        } else {
            NBankName = getIntent().getStringExtra("NBankName");
            NBankNo = getIntent().getStringExtra("NBankNo");
            NCardId = getIntent().getStringExtra("NCardId");
            NLogoUrl = getIntent().getStringExtra("NLogoUrl");
        }
        initUI();
        initHead();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCardSign();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                if (ID.equals("2")) {
                    Intent intent = new Intent(CardSignActivity.this, BankSigNingActivity.class);
                    intent.putExtra("turn_type", "bills");
                    intent.putExtra("NCardId", NCardId);
                    intent.putExtra("NBankName", NBankName);
                    intent.putExtra("NBankNo", NBankNo);
                    intent.putExtra("NLogoUrl", NLogoUrl);
                    startActivity(intent);
                    finish();
                } else if (ID.equals("4")) {
                    if (isEntrySun.equals("1")) {
                        getInit();
                        finish();
                    } else if (isEntrySun.equals("2")) {
                        bangKa();
                    }
                } else if (ID.equals("1")) {
                    tengFuTong();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initHead() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.head_text_title);
        title.setText("选择信用签约通道");
    }

    private void initUI() {
        CircleImageView ivBankLogo = (CircleImageView) findViewById(R.id.iv_bank_sign);
        TextView tvBankName = (TextView) findViewById(R.id.tv_bank_sign_card_name);
        TextView tvBankNumber = (TextView) findViewById(R.id.tv_bank_sign_card_number);
        findViewById(R.id.change_card).setOnClickListener(this);
        Glide.with(this).load(NLogoUrl).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(NBankName);
        tvBankNumber.setText(NBankNo);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CardSignAdapter(this, new CardSignAdapter.OnCardSignInfo() {

            @Override
            public void cardSignClick(CardSignResponse.CardSignInfo cardSignInfo) {
                ID = cardSignInfo.getId();
                isEntrySun = cardSignInfo.getIsEntry();
            }

        });
        mRecyclerView.setAdapter(adapter);
    }

    private void getInit() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("resmark", IService.RESMARK);
        params.put("uid", SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getUid());

        Connect.getInstance().post(getApplicationContext().getApplicationContext(), IService.INIT, params, new Connect.OnResponseListener() {
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
                        if (statusName.equals("input_sellermsg")) {
                            Intent intent = new Intent(CardSignActivity.this, MenDianActivity.class);
                            intent.putExtra("type", "meimei");
                            intent.putExtra("NCardId", NCardId);
                            startActivity(intent);
                        } else if (statusName.equals("input_money")) {
                            startActivity(new Intent(getApplicationContext(), DynamicQRcodeActivity.class));
                        } else if (statusName.equals("display_error")) {
                            ToastUtil.showTextToas(getApplicationContext(), msg);
                        }
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
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

    /**
     * 腾付通签约
     */
    private void tengFuTong() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", NCardId);
        Connect.getInstance().post(getApplicationContext(), IService.TENGFUTONG, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                android.util.Log.d("腾付通", result.toString());
                // 0-已签约  1-进件  2-绑卡
                dialogUtil.dismiss();
                YanzhengInfo_old info = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (info.getResult().getCode() == 10000) {
                    ToastUtil.showTextToas(getApplicationContext(), "签约成功");
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), info.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 获取银行卡签约通道
     */
    private void getCardSign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", NCardId);
        Connect.getInstance().post(getApplicationContext(), IService.PLAN_SIGN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                // 0-已签约  1-进件  2-绑卡
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONArray array = new JSONArray(data1);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String isEntry = object.getString("isEntry");
                            String isId = object.getString("id");
                            if (!(isEntry.equals("0"))) {
                                JSONObject map = new JSONObject();
                                map.put("id", isId);
                                map.put("isEntry", isEntry);
                                map.put("nickname", object.getString("nickname"));
                                stringArrayList.add(map);
                            }
                        }
                        isEntrySun = stringArrayList.get(0).getString("isEntry");
                        ID = stringArrayList.get(0).getString("id");

                        JSONObject sun = new JSONObject("{\"result\":" +
                                "{\"code\":10000,\"msg\":\"请求成功\"}," +
                                "\"data\":" + String.valueOf(stringArrayList) + "}");
                        android.util.Log.d("fdfd", stringArrayList.get(0).getString("isEntry"));
                        CardSignResponse response = (CardSignResponse) JsonUtils.parse(String.valueOf(sun), CardSignResponse.class);
                        List<CardSignResponse.CardSignInfo> data = response.getData();
                        adapter.setData(data);
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
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

    /**
     * 融汇绑卡
     */
    private void bangKa() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", NCardId);
        Connect.getInstance().post(getApplicationContext(), IService.RONGHUI_BANGKA, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                // 0-已签约  1-进件  2-绑卡
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
                        String data2 = obj2.getString("data");
                        JSONObject obj3 = new JSONObject(data2);
                        Intent intent = new Intent(getApplicationContext(), RongHuiWebActivity.class);
                        intent.putExtra("data2", data2);
                        intent.putExtra("accNo", obj3.getString("accNo"));
                        intent.putExtra("merchantNo", obj3.getString("merchantNo"));
                        intent.putExtra("orderNum", obj3.getString("orderNum"));
                        intent.putExtra("encrypt", obj3.getString("encrypt"));
                        intent.putExtra("type", obj3.getString("type"));
                        intent.putExtra("phone", obj3.getString("phone"));
                        intent.putExtra("cvn2", obj3.getString("cvn2"));
                        intent.putExtra("callBackUrl", obj3.getString("callBackUrl"));
                        intent.putExtra("serverCallBackUrl", obj3.getString("serverCallBackUrl"));
                        intent.putExtra("sign", obj3.getString("sign"));
                        intent.putExtra("url", obj2.getString("url"));
                        startActivity(intent);
                        finish();
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
