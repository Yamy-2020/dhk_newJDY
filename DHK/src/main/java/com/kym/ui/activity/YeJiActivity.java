package com.kym.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.ShaiXunActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.PaiHangBangAdapter;
import com.kym.ui.adapter.Sj_oneAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.PaiHangBangResponse;
import com.kym.ui.model.UserMyMerchant;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONObject;

import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

/**
 * 业绩管理
 *
 * @author sun
 * @date 2019/12/3
 */

public class YeJiActivity extends BaseActivity implements View.OnClickListener {

    private String day_total_vol, day_rec_vol, day_repx_vol, day_conx_vol, last_day_total_vol, last_team_total_vol,
            team_total_vol, team_rec_vol, team_repx_vol, team_conx_vol, day_kadex_vol,
            team_kadex_vol, curr_day_total_vol, curr_team_total_vol;
    private TextView total, xf_total, hk_total, sk_total, total_tip,
            btn_gr1, btn_gr2, btn_team1, btn_team2, dc_total, by_total, sy_total;
    private List<UserMyMerchant.DataBean.ListBean> data_dj;
    private ListView listView;
    private DialogUtil dialogUtil;
    private TextView textV_tg_all;
    private TextView textV_tg_zhijie;
    private TextView textV_tg_shimin;
    private BackDialog backDialog;
    private PaiHangBangAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiaoyi_ln);
        initView();
        getJiaoYi();
        getPaiHangBang();
    }

    private void initView() {
        total = findViewById(R.id.total);
        LinearLayout daichang = findViewById(R.id.daichang);
        daichang.setVisibility(View.GONE);
        findViewById(R.id.jiaoyi).setVisibility(View.GONE);
        xf_total = findViewById(R.id.xf_total);
        hk_total = findViewById(R.id.hk_total);
        sk_total = findViewById(R.id.sk_total);
        dc_total = findViewById(R.id.dc_total);
        by_total = findViewById(R.id.by_total);
        sy_total = findViewById(R.id.sy_total);
//        total_tip = findViewById(R.id.total_tip);
        btn_gr1 = findViewById(R.id.btn_gr1);
        btn_team1 = findViewById(R.id.btn_team1);
        btn_gr2 = findViewById(R.id.btn_gr2);
        btn_team2 = findViewById(R.id.btn_team2);
        btn_gr1.setOnClickListener(this);
        btn_team1.setOnClickListener(this);
        btn_gr2.setOnClickListener(this);
        btn_team2.setOnClickListener(this);
        LinearLayout gone1 = findViewById(R.id.gone1);
        if (Clone.OMID.equals("SX90IOKIPZNCO5O1")) {
            gone1.setVisibility(View.GONE);
        } else {
            gone1.setVisibility(View.VISIBLE);
        }
        TextView textV_title = findViewById(R.id.head_text_title);
        textV_title.setText("业绩管理");
        findViewById(R.id.head_img_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textV_tg_all = findViewById(R.id.textV_tuiguang_all);
        textV_tg_zhijie = findViewById(R.id.textV_tuiguang_zhijie);
        textV_tg_shimin = findViewById(R.id.textV_tuiguang_shiming);
        listView = findViewById(R.id.listView_sj);
        getDengji();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                UserMyMerchant.DataBean.ListBean dataInfo = data_dj.get(arg2);
                if (dataInfo.getNum1() == 0) {
                    ToastUtil.showTextToas(getApplicationContext(), "去邀请好友试一试吧");
                } else {
                    String name2 = dataInfo.getName();
                    String id2 = dataInfo.getLfid();
                    Intent intent_p = new Intent();
                    intent_p.putExtra("name", name2);
                    intent_p.putExtra("lid", id2);
                    intent_p.putExtra("head_img", dataInfo.getHead_img());
                    intent_p.setClass(YeJiActivity.this, ShaiXunActivity.class);
                    startActivity(intent_p);
                }
            }
        });
        final RecyclerView mRecyclerView = findViewById(R.id.phb_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PaiHangBangAdapter(this, new PaiHangBangAdapter.OnZDClick() {

            @Override
            public void OnZDClick(PaiHangBangResponse.PaiHangBangInfo zdInfo) {

            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    private void getJiaoYi() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.JiaoYiLiang, null, new Connect.OnResponseListener() {
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
                        last_day_total_vol = obj2.getString("last_day_total_vol");
                        last_team_total_vol = obj2.getString("last_team_total_vol");
                        curr_day_total_vol = obj2.getString("curr_day_total_vol");
                        curr_team_total_vol = obj2.getString("curr_team_total_vol");
                        day_total_vol = obj2.getString("day_total_vol");
                        day_rec_vol = obj2.getString("day_rec_vol");
                        day_repx_vol = obj2.getString("day_repx_vol");
                        day_conx_vol = obj2.getString("day_conx_vol");
                        day_kadex_vol = obj2.getString("day_kadex_vol");
                        team_kadex_vol = obj2.getString("team_kadex_vol");
                        team_total_vol = obj2.getString("team_total_vol");
                        team_rec_vol = obj2.getString("team_rec_vol");
                        team_repx_vol = obj2.getString("team_repx_vol");
                        team_conx_vol = obj2.getString("team_conx_vol");
                        total.setText(day_total_vol);
                        sk_total.setText(day_rec_vol);
                        hk_total.setText(day_repx_vol);
                        xf_total.setText(day_conx_vol);
                        dc_total.setText(day_kadex_vol);
                        by_total.setText(curr_day_total_vol);
                        sy_total.setText(last_day_total_vol);
//                        total_tip.setText("个人总交易量");
                        btn_gr1.setVisibility(View.VISIBLE);
                        btn_team1.setVisibility(View.VISIBLE);
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", YeJiActivity.this,
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.btn_gr1:
                total.setText(day_total_vol);
                sk_total.setText(day_rec_vol);
                hk_total.setText(day_repx_vol);
                xf_total.setText(day_conx_vol);
                dc_total.setText(day_kadex_vol);
                by_total.setText(curr_day_total_vol);
                sy_total.setText(last_day_total_vol);
//                total_tip.setText("个人总交易量");
                btn_gr1.setVisibility(View.VISIBLE);
                btn_gr2.setVisibility(View.GONE);
                btn_team1.setVisibility(View.VISIBLE);
                btn_team2.setVisibility(View.GONE);
                break;
            case R.id.btn_team1:
                total.setText(team_total_vol);
                sk_total.setText(team_rec_vol);
                hk_total.setText(team_repx_vol);
                xf_total.setText(team_conx_vol);
                dc_total.setText(team_kadex_vol);
                by_total.setText(curr_team_total_vol);
                sy_total.setText(last_team_total_vol);
//                total_tip.setText("团队总交易量");
                btn_gr1.setVisibility(View.GONE);
                btn_gr2.setVisibility(View.VISIBLE);
                btn_team1.setVisibility(View.GONE);
                btn_team2.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_gr2:
                total.setText(day_total_vol);
                sk_total.setText(day_rec_vol);
                hk_total.setText(day_repx_vol);
                xf_total.setText(day_conx_vol);
                dc_total.setText(day_kadex_vol);
//                total_tip.setText("个人总交易量");
                by_total.setText(curr_day_total_vol);
                sy_total.setText(last_day_total_vol);
                btn_gr1.setVisibility(View.VISIBLE);
                btn_gr2.setVisibility(View.GONE);
                btn_team1.setVisibility(View.VISIBLE);
                btn_team2.setVisibility(View.GONE);
                break;
            case R.id.btn_team2:
                total.setText(team_total_vol);
                sk_total.setText(team_rec_vol);
                hk_total.setText(team_repx_vol);
                xf_total.setText(team_conx_vol);
                dc_total.setText(team_kadex_vol);
                by_total.setText(curr_team_total_vol);
                sy_total.setText(last_team_total_vol);
//                total_tip.setText("团队总交易量");
                btn_gr1.setVisibility(View.GONE);
                btn_gr2.setVisibility(View.VISIBLE);
                btn_team1.setVisibility(View.GONE);
                btn_team2.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void getDengji() {
        dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(YeJiActivity.this, IService.USER_MYMERCHANT, null, new Connect.OnResponseListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object result) {
                UserMyMerchant response = (UserMyMerchant) JsonUtils.parse((String) result, UserMyMerchant.class);
                if (response.getResult().getCode() == 10000) {
                    UserMyMerchant.DataBean data = response.getData();
                    int i = data.getSum() - data.getSubReal();
                    data_dj = data.getList();
                    textV_tg_zhijie.setText("" + i);
                    textV_tg_shimin.setText("" + data.getSubReal());
                    textV_tg_all.setText("" + data.getSum());
                    Sj_oneAdapter sj_oneAdapter = new Sj_oneAdapter(YeJiActivity.this, data_dj);
                    listView.setAdapter(sj_oneAdapter);
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", YeJiActivity.this,
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
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
                dialogUtil.dismiss();
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.myshanghu_new, menu);
        return true;
    }

    /**
     * 获取还款信用卡列表
     */
    private void getPaiHangBang() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.PAIHANGBANG, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                PaiHangBangResponse response = (PaiHangBangResponse) JsonUtils.parse((String) result, PaiHangBangResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<PaiHangBangResponse.PaiHangBangInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                    } else {
                        adapter.clearData();
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", YeJiActivity.this,
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
