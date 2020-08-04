package com.kym.ui.activity.bpbro_home.bpbro_yk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.paradigm.botkit.BotKitClient;
import com.paradigm.botlib.VisitorInfo;
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
import com.kym.ui.info.XFSignResponse;
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

import static com.zzss.jindy.appconfig.Clone.OMID;


/**
 * 养卡通道签约
 *
 * @author sun
 * @date 2019/12/3
 */
public class XFsignActivity extends BaseActivity implements View.OnClickListener {

    private BankListResponse.BankInfo bankInfo;
    private CardSignAdapter adapter;
    private String ID, NCardId, NBankName, NLogoUrl, NBankNo;
    private ArrayList<JSONObject> stringArrayList;
    private DragFloatActionButton circle_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfsign);
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
                if (ID.equals("1")) {
                    KFT_sign();
                } else if (ID.equals("3")) {
                    JFT_sign(IService.XF_JFT_SHENQING_SIGN);
                } else if (ID.equals("4")) {
                    JFT_sign(IService.XF_JQJFT_SHENQING_SIGN);
                } else if (ID.equals("5")) {
                    JFT_sign(IService.XF_JDJQJFT_SHENQING_SIGN);
                } else if (ID.equals("6")) {
                    SXY_sign();
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
        title.setText("养卡通道进件");
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
        Connect.getInstance().post(getApplicationContext(), IService.XF_BANK_SIGN_LIST, params, new Connect.OnResponseListener() {
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
     * 首信易签约
     */
    private void SXY_sign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", NCardId);
        Connect.getInstance().post(getApplicationContext(), IService.SXY_YANGKA_QIANYUE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                XFSignResponse response = (XFSignResponse) JsonUtils.parse((String) result, XFSignResponse.class);
                if (response.getData() != null) {
                    if (response.getData().getIs_bind().equals("1")) {
                        Intent intent = new Intent(getApplicationContext(), XF_QRsignActivity.class);
                        intent.putExtra("bankData", bankInfo);
                        intent.putExtra("orderid", response.getData().getOrderid());
                        intent.putExtra("smsSeq", response.getData().getSmsSeq());
                        intent.putExtra("tip", response.getData().getTip());
                        intent.putExtra("ID", ID);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                        finish();
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
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
     * 快付通签约
     */
    private void KFT_sign() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", NCardId);
        Connect.getInstance().post(getApplicationContext(), IService.XF_SHENQING_SIGN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                XFSignResponse response = (XFSignResponse) JsonUtils.parse((String) result, XFSignResponse.class);
                if (response.getData() != null) {
                    Intent intent = new Intent(getApplicationContext(), XF_QRsignActivity.class);
                    intent.putExtra("bankData", bankInfo);
                    intent.putExtra("orderid", response.getData().getOrderid());
                    intent.putExtra("smsSeq", response.getData().getSmsSeq());
                    intent.putExtra("tip", response.getData().getTip());
                    intent.putExtra("ID", ID);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
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
     * 佳付通签约
     */
    private void JFT_sign(String api) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", NCardId);
        Connect.getInstance().post(getApplicationContext(), api, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                XFSignResponse response = (XFSignResponse) JsonUtils.parse((String) result, XFSignResponse.class);
                if (response.getData() != null) {
                    if (response.getData().getIs_url().equals("0")) {
                        Intent intent = new Intent(getApplicationContext(), XF_QRsignActivity.class);
                        intent.putExtra("bankData", bankInfo);
                        intent.putExtra("orderid", response.getData().getOrderid());
                        intent.putExtra("smsSeq", response.getData().getSmsSeq());
                        intent.putExtra("tip", response.getData().getTip());
                        intent.putExtra("ID", ID);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                        intent.putExtra("WEB_TITLE", "养卡进件订单");
                        intent.putExtra("WEB_URL", response.getData().getUrl());
                        startActivity(intent);
                        finish();
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
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
