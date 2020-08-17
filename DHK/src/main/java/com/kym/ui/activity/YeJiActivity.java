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

public class YeJiActivity extends BaseActivity {

    private String day_total_vol, day_rec_vol, day_repx_vol, day_conx_vol, last_day_total_vol, last_team_total_vol,
            team_total_vol, team_rec_vol, team_repx_vol, team_conx_vol, curr_day_total_vol, curr_team_total_vol;
    private TextView total, xf_total, hk_total, sk_total, by_total, sy_total,total1, xf_total1, hk_total1, sk_total1, by_total1, sy_total1;
    private List<UserMyMerchant.DataBean.ListBean> data_dj;
    private ListView listView;
    private DialogUtil dialogUtil;
    private TextView textV_tg_all;
    private TextView textV_tg_zhijie;
    private TextView textV_tg_shimin;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiaoyi_yeji);
        initView();
        getJiaoYi();
    }

    private void initView() {
        xf_total = findViewById(R.id.xf_total);
        hk_total = findViewById(R.id.hk_total);
        sk_total = findViewById(R.id.sk_total);
        by_total = findViewById(R.id.by_total);
        sy_total = findViewById(R.id.sy_total);
        total = findViewById(R.id.total);
        xf_total1 = findViewById(R.id.xf_total1);
        hk_total1 = findViewById(R.id.hk_total1);
        sk_total1 = findViewById(R.id.sk_total1);
        by_total1 = findViewById(R.id.by_total1);
        sy_total1 = findViewById(R.id.sy_total1);
        total1 = findViewById(R.id.total1);

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
                        team_total_vol = obj2.getString("team_total_vol");
                        team_rec_vol = obj2.getString("team_rec_vol");
                        team_repx_vol = obj2.getString("team_repx_vol");
                        team_conx_vol = obj2.getString("team_conx_vol");
                        /*个人*/
                        total.setText(day_total_vol);//昨日交易量
                        sk_total.setText(day_rec_vol);//收款
                        hk_total.setText(day_repx_vol);//还款
                        xf_total.setText(day_conx_vol);//智收
                        by_total.setText(curr_day_total_vol);//本月总交易量
                        sy_total.setText(last_day_total_vol);//上月总交易量
                        /*团队*/
                        total1.setText(team_total_vol);//昨日交易量
                        sk_total1.setText(team_rec_vol);//收款
                        hk_total1.setText(team_repx_vol);//还款
                        xf_total1.setText(team_conx_vol);//智收
                        by_total1.setText(curr_team_total_vol);//本月总交易量
                        sy_total1.setText(last_team_total_vol);//上月总交易量
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


}
