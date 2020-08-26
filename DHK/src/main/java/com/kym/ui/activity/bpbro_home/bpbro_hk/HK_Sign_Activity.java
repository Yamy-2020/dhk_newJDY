package com.kym.ui.activity.bpbro_home.bpbro_hk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_home.bpbro_sk.KuaiJieTFTShanghuActivity;
import com.paradigm.botkit.BotKitClient;
import com.paradigm.botlib.VisitorInfo;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.fee_kf.ChatActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.CardSignAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.hualuo.HL_BK_WebActivity;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.info.CardSignResponse;
import com.kym.ui.info.HKSignResponse;
import com.kym.ui.newutil.DragFloatActionButton;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;
import static com.zzss.jindy.appconfig.Clone.OMID;

/**
 * 还款信用卡签约
 *
 * @author sun
 * @date 2019/12/3
 */

public class HK_Sign_Activity extends BaseActivity implements View.OnClickListener {

    private BankListResponse.BankInfo bankInfo;
    private CardSignAdapter adapter;
    private String ID, isEntry, NCardId, NBankName, NLogoUrl, NBankNo;
    private ArrayList<JSONObject> stringArrayList = new ArrayList<>();
    private BackDialog backDialog;
    private DragFloatActionButton circle_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hk__sign);
        bankInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("bankData");
        NBankName = bankInfo.getBank_name();
        NBankNo = bankInfo.getBank_no();
        NCardId = bankInfo.getCardid();
        NLogoUrl = bankInfo.getLogo_url();
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
            case R.id.circle_button:
                VisitorInfo info = new VisitorInfo();
                info.nickName = Clone.APP_NAME + "_" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getName() + "_" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getUid();
                info.userName = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getName();
                info.phone = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getMobile();
                BotKitClient.getInstance().setVisitor(info);
                BotKitClient.getInstance().setPortraitUser(getResources().getDrawable(R.drawable.icon));
                BotKitClient.getInstance().setPortraitRobot(getResources().getDrawable(R.drawable.tianjia));
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                break;
            case R.id.change_card:
                //【快付通 大额1 小额2】 【融汇4】  【佳付通大额5 9 11 小额8 10 12】   【腾付通6  速辰商通7】
                // 【首信易大额13 小额14 29】【平安付 30】 【新生大额 15 19 小额16 20】 【工易付 小额21 22】【融宝 25 26】【信之对 27】【腾付通 28】
                // 【快捷通 24】
                if (isEntry.equals("1")) {
                    sq_sign();
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
        title.setText("信用卡签约通道选择");
    }

    private void initUI() {
        circle_button = (DragFloatActionButton) findViewById(R.id.circle_button);
        circle_button.setOnClickListener(this);
        if (OMID.equals("E1TDVFFY8JX3RY62")) {
            circle_button.setVisibility(View.GONE);
        }
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
     * 签约通道列表
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
                            if (!(isEntry.equals("0"))) {
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
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", HK_Sign_Activity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(HK_Sign_Activity.this, LoginActivity.class));

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
     * 申请签约
     */
    private void sq_sign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", ID);
        params.put("cardid", NCardId);
        params.put("province", "山西省");
        params.put("city", "晋城市");
        params.put("district", "高平市");
        Connect.getInstance().post(getApplicationContext(), IService.TONGYI_SIGN_SHENQING, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                HKSignResponse response = (HKSignResponse) JsonUtils.parse((String) result, HKSignResponse.class);
                if (response.getResult().getCode() == 10000) {
                    if (response.getData().getId().equals("1") || response.getData().getId().equals("2") ||
                            response.getData().getId().equals("4") || response.getData().getId().equals("15")
                            || response.getData().getId().equals("28")) {
                        Intent intent = new Intent(getApplicationContext(), HK_QRsignActivity.class);
                        intent.putExtra("bankData", bankInfo);
                        intent.putExtra("id", response.getData().getId());
                        intent.putExtra("orderid", response.getData().getOrderid());
                        intent.putExtra("smsSeq", response.getData().getSmsSeq());
                        intent.putExtra("tip", response.getResult().getMsg());
                        startActivity(intent);
                        finish();
                    } else if (response.getData().getId().equals("5") || response.getData().getId().equals("8") ||
                            response.getData().getId().equals("9") || response.getData().getId().equals("10") ||
                            response.getData().getId().equals("11") || response.getData().getId().equals("12") ||
                            response.getData().getId().equals("25") || response.getData().getId().equals("26") || response.getData().getId().equals("27")) {
                        if (response.getData().getIs_url().equals("0")) {
                            Intent intent = new Intent(getApplicationContext(), HK_QRsignActivity.class);
                            intent.putExtra("bankData", bankInfo);
                            intent.putExtra("id", response.getData().getId());
                            intent.putExtra("orderid", response.getData().getOrderid());
                            intent.putExtra("smsSeq", response.getData().getSmsSeq());
                            intent.putExtra("tip", response.getResult().getMsg());
                            startActivity(intent);
                            finish();
                        } else if (response.getData().getIs_url().equals("1")) {
                            Intent intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                            intent.putExtra("WEB_TITLE", "订单支付");
                            intent.putExtra("WEB_URL", response.getData().getUrl());
                            startActivity(intent);
                            finish();
                        }
                    } else if (response.getData().getId().equals("6")) {
                        if (response.getData().getIsMsg().equals("1")) {
                            Intent intent = new Intent(getApplicationContext(), HK_QRsignActivity.class);
                            intent.putExtra("bankData", bankInfo);
                            intent.putExtra("id", response.getData().getId());
                            intent.putExtra("orderid", response.getData().getOrderid());
                            intent.putExtra("smsSeq", response.getData().getSmsSeq());
                            intent.putExtra("tip", response.getResult().getMsg());
                            startActivity(intent);
                            finish();
                        } else if (response.getData().getIsMsg().equals("0")) {
                            tipView("2", response.getResult().getMsg());
                        }
                    } else if (response.getData().getId().equals("7")) {
                        if (response.getData().getBind_status().equals("1")) {
                            Intent intent = new Intent(getApplicationContext(), HK_QRsignActivity.class);
                            intent.putExtra("bankData", bankInfo);
                            intent.putExtra("id", response.getData().getId());
                            intent.putExtra("orderid", response.getData().getOrderid());
                            intent.putExtra("smsSeq", bankInfo.getCardid());
                            intent.putExtra("tip", response.getResult().getMsg());
                            startActivity(intent);
                            finish();
                        } else {
                            tipView("2", response.getResult().getMsg());
                        }
                    } else if (response.getData().getId().equals("13") || response.getData().getId().equals("14") ||
                            response.getData().getId().equals("29")) {
                        if (response.getData().getIs_bind().equals("1")) {
                            Intent intent = new Intent(getApplicationContext(), HK_QRsignActivity.class);
                            intent.putExtra("bankData", bankInfo);
                            intent.putExtra("id", response.getData().getId());
                            intent.putExtra("orderid", response.getData().getOrderid());
                            intent.putExtra("smsSeq", response.getData().getSmsSeq());
                            intent.putExtra("tip", response.getResult().getMsg());
                            startActivity(intent);
                            finish();
                        } else {
                            tipView("2", response.getResult().getMsg());
                        }
                    } else if (response.getData().getId().equals("21") || response.getData().getId().equals("22")) {
                        if (response.getData().getIs_url().equals("0")) {
                            tipView("2", response.getResult().getMsg());
                            finish();
                        } else if (response.getData().getIs_url().equals("1")) {
                            Intent intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                            intent.putExtra("WEB_TITLE", "签约订单");
                            intent.putExtra("WEB_URL", response.getData().getUrl());
                            startActivity(intent);
                            finish();
                        }
                    } else if (response.getData().getId().equals("30") || response.getData().getId().equals("31")) {
                        if (response.getData().getStatus().equals("2")) {
                            tipView("2", response.getData().getExtra());
                        } else {
                            Intent intent = new Intent(getApplicationContext(), HK_QRsignActivity.class);
                            intent.putExtra("bankData", bankInfo);
                            intent.putExtra("id", response.getData().getId());
                            intent.putExtra("tip", response.getResult().getMsg());
                            startActivity(intent);
                            finish();
                        }
                    } else if (response.getData().getId().equals("24")) {
                        if (response.getData().getBindStatus().equals("true")) {
                            Intent intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                            intent.putExtra("WEB_TITLE", "签约订单");
                            intent.putExtra("WEB_URL", response.getData().getUrl());
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    tipView("2", response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", HK_Sign_Activity.this,
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
