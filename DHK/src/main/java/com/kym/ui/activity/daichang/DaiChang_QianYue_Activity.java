package com.kym.ui.activity.daichang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.kym.ui.activity.shiming.RhZhengJianActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.CardSignAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.info.CardSignResponse;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


/**
 * 贷偿签约
 *
 * @author sun
 * @date 2019/12/26
 */

public class DaiChang_QianYue_Activity extends BaseActivity implements View.OnClickListener {

    private BankListResponse.BankInfo bankInfo;
    private CardSignAdapter adapter;
    private String NCardId, NBankName, NLogoUrl, NBankNo, ID, isEntry;
    private ArrayList<JSONObject> stringArrayList = new ArrayList<>();
    private Intent intent;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_chang__qian_yue);
        bankInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("bankInfo");
        NBankName = bankInfo.getBank_name();
        NBankNo = bankInfo.getBank_no();
        NCardId = bankInfo.getCardid();
        NLogoUrl = bankInfo.getLogo_url();
        initHead();
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCardSign();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                getImg();
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
        title.setText("选择签约通道");
    }

    private void initUI() {
        CircleImageView ivBankLogo = (CircleImageView) findViewById(R.id.iv_bank_sign);
        TextView tvBankName = (TextView) findViewById(R.id.tv_bank_sign_card_name);
        TextView tvBankNumber = (TextView) findViewById(R.id.tv_bank_sign_card_number);
        findViewById(R.id.change_card).setOnClickListener(this);
        Glide.with(this).load(NLogoUrl).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(NBankName);
        tvBankNumber.setText(NBankNo.substring(0, 4)
                + " **** **** " + NBankNo.substring(NBankNo.length() - 4, NBankNo.length()));
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CardSignAdapter(this, new CardSignAdapter.OnCardSignInfo() {

            @Override
            public void cardSignClick(CardSignResponse.CardSignInfo cardSignInfo) {
                ID = cardSignInfo.getId();
                isEntry = cardSignInfo.getIsEntry();
            }

        });
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 判断是否上传照片
     */
    private void getImg() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.DC_RH_IS_UPIMG, null, new Connect.OnResponseListener() {
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
                        String code1 = obj2.get("code").toString();
                        String msg1 = obj2.get("msg").toString();
                        if (code1.equals("1")) {
                            isShangchuanDiqu();
                        } else if (code1.equals("5")) {
                            ToastUtil.showTextToas(getApplicationContext(), msg1 + ",如需处理请联系在线客服");
                        } else {
                            ToastUtil.showTextToas(getApplicationContext(), msg1 + ",请上传照片");
                            intent = new Intent(getApplicationContext(), RhZhengJianActivity.class);
                            intent.putExtra("code1", code1);
                            intent.putExtra("bankInfo", bankInfo);
                            intent.putExtra("id", ID);
                            intent.putExtra("type", "ronghui");
                            startActivity(intent);
                            finish();
                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", DaiChang_QianYue_Activity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(DaiChang_QianYue_Activity.this, LoginActivity.class));

//                                restartApp(getApplicationContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
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
     * 判断是否上传地区
     */
    private void isShangchuanDiqu() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.IS_CHUANDIQU, null, new Connect.OnResponseListener() {
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
                        String code1 = obj2.get("code").toString();
                        if (code1.equals("1")) {
                            /**
                             * 已上传
                             */
                            getBangKa();
                        } else {
                            /**
                             * 未上传
                             */
                            intent = new Intent(getApplicationContext(), DaiChangCityActivity.class);
                            intent.putExtra("data", bankInfo);
                            intent.putExtra("id", ID);
                            startActivity(intent);
                            finish();

                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", DaiChang_QianYue_Activity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(DaiChang_QianYue_Activity.this, LoginActivity.class));

//                                restartApp(getApplicationContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
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
    private void getBangKa() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", ID);
        params.put("cardid", bankInfo.getCardid());
        Connect.getInstance().post(getApplicationContext(), IService.ALL_SIGN, params, new Connect.OnResponseListener() {
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
                        String data = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data);
                        intent = new Intent(getApplicationContext(), DaiChangRongHuiWebActivity.class);
                        intent.putExtra("html", obj2.getString("html"));
                        startActivity(intent);
                        finish();
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
     * 获取贷偿银行卡签约通道
     */
    private void getCardSign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", NCardId);
        Connect.getInstance().post(getApplicationContext(), IService.DC_BANK_TONGDAO, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    stringArrayList = new ArrayList<>();
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
                            if (!(isEntry.equals("0")) && isId.equals("4")) {
                                JSONObject map = new JSONObject();
                                map.put("id", isId);
                                map.put("isEntry", isEntry);
                                map.put("nickname", object.getString("nickname"));
                                stringArrayList.add(map);
                            }
                        }
                        ID = stringArrayList.get(0).getString("id");
                        isEntry = stringArrayList.get(0).getString("isEntry");
                        JSONObject sun = new JSONObject("{\"result\":" +
                                "{\"code\":10000,\"msg\":\"请求成功\"}," +
                                "\"data\":" + String.valueOf(stringArrayList) + "}");
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
}
