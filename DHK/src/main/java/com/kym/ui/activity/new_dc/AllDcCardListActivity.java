package com.kym.ui.activity.new_dc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.BankChangeActivity;
import com.kym.ui.JieBangCardActivity;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.AllCardListAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.AllCardListResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

/**
 * 所有信用卡列表
 * Created by Sunmiaolong on 2018/7/29.
 * .
 */

public class AllDcCardListActivity extends BaseActivity implements View.OnClickListener {

    private AllCardListAdapter adapter;
    private ArrayList<JSONObject> stringArrayList = new ArrayList<>();
    private SPConfig spConfig;
    private RelativeLayout lay_foot_add;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_card_list);
        initHead();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        getAllCard();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("信用卡列表");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }


    private void initView() {
        final RecyclerView mRecyclerView = findViewById(R.id.rv_bank_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AllCardListAdapter(this, new AllCardListAdapter.OnCardList() {

            @Override
            public void kj_card_list(AllCardListResponse.AllCardListInfo allCardListInfo, String type) {
                if (type.equals("0")) {
                    Intent changeIntent = new Intent(getApplicationContext(), BankChangeActivity.class);
                    changeIntent.putExtra("NCardId", allCardListInfo.getCardid());
                    changeIntent.putExtra("NBankName", allCardListInfo.getBank_name());
                    changeIntent.putExtra("NBankNo", allCardListInfo.getBank_no());
                    changeIntent.putExtra("NLogoUrl", allCardListInfo.getLogo_url());
                    startActivity(changeIntent);
                } else if (type.equals("3")) {
                    Intent intent = new Intent(getApplicationContext(), JieBangCardActivity.class);
                    intent.putExtra("NCardId", allCardListInfo.getCardid());
                    intent.putExtra("NBankName", allCardListInfo.getBank_name());
                    intent.putExtra("NBankNo", allCardListInfo.getBank_no());
                    intent.putExtra("NLogoUrl", allCardListInfo.getLogo_url());
                    startActivity(intent);
                    finish();
                }
            }
        });
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(adapter);
        lay_foot_add = findViewById(R.id.lay_foot_add);
        lay_foot_add.setOnClickListener(this);
        lay_foot_add.setVisibility(View.GONE);
    }

    /**
     * 获取用户的所有信用卡
     */
    private void getAllCard() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.ALL_CARD_LIST, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String data = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(data);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    stringArrayList = new ArrayList<>();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        String statusName = obj2.getString("statusName");
                        if (statusName.equals("display")) {
                            String credit = obj2.getString("credit");
                            JSONArray array = new JSONArray(credit);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String image_url = object.getString("image_url");
                                String logo_url = object.getString("logo_url");
                                String bank_name = object.getString("bank_name");
                                String bank_no = object.getString("bank_no");
                                String card_type = object.getString("card_type");
                                String bills = object.getString("bills");
                                String repayment = object.getString("repayment");
                                JSONObject map = new JSONObject();
                                map.put("image_url", image_url);
                                map.put("logo_url", logo_url);
                                map.put("bank_name", bank_name);
                                map.put("bank_no", bank_no);
                                map.put("card_type", card_type);
                                map.put("cardid", object.getString("cardid"));
                                map.put("bills", bills);
                                map.put("repayment", repayment);
                                stringArrayList.add(map);
                            }

                            JSONObject sun = new JSONObject("{\"result\":" +
                                    "{\"code\":10000,\"msg\":\"请求成功\"}," +
                                    "\"data\":" + stringArrayList + "}");

                            AllCardListResponse response = (AllCardListResponse) JsonUtils.parse(String.valueOf(sun), AllCardListResponse.class);
                            List<AllCardListResponse.AllCardListInfo> data3 = response.getData();
                            adapter.setData(data3);
                        } else {
                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", AllDcCardListActivity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                restartApp(getApplicationContext());
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

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", AllDcCardListActivity.this,
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
