package com.kym.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.Sj_oneAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.UserMyMerchant;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;
import static com.zzss.jindy.appconfig.Clone.OMID;

/**
 * 我的商户
 */

public class ShangHuActivity extends BaseActivity {
    private List<UserMyMerchant.DataBean.ListBean> data_dj;
    private ListView listView;
    private DialogUtil dialogUtil;
    private TextView textV_tg_all;
    private TextView textV_tg_zhijie;
    private TextView textV_tg_jianjie;
    private TextView textV_tg_shimin;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_hu);
        initview();
    }

    private void initview() {
        TextView textV_title = (TextView) findViewById(R.id.head_text_title);
        if (OMID.equals("VIB0T31Q2L7DNDK5")) {
            /**
             * 花螺生活
             */
            textV_title.setText("团队");
        } else if (OMID.equals("X9FN9CEDKB0C9A43")) {
            /**
             * 天天智还
             */
            textV_title.setText("我的商户");
        } else {
            textV_title.setText("商户");
        }
        findViewById(R.id.head_img_left).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textV_tg_all = (TextView) findViewById(R.id.textV_tuiguang_all);
        textV_tg_zhijie = (TextView) findViewById(R.id.textV_tuiguang_zhijie);
        textV_tg_jianjie = (TextView) findViewById(R.id.textV_tuiguang_jianjie);
        textV_tg_shimin = (TextView) findViewById(R.id.textV_tuiguang_shiming);
        listView = (ListView) findViewById(R.id.listView_sj);
        getDengji();
        listView.setOnItemClickListener(new OnItemClickListener() {
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
                    intent_p.setClass(ShangHuActivity.this, ShaiXunActivity.class);
                    startActivity(intent_p);
                }
            }
        });
    }

    private void getDengji() {
        dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(ShangHuActivity.this, IService.USER_MYMERCHANT, null, new Connect.OnResponseListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object result) {
                UserMyMerchant response = (UserMyMerchant) JsonUtils.parse((String) result, UserMyMerchant.class);
                if (response.getResult().getCode() == 10000) {
                    UserMyMerchant.DataBean data = response.getData();
                    int i = data.getSum() - data.getSubReal();
                    data_dj = data.getList();
                    textV_tg_zhijie.setText("" + i);
                    textV_tg_jianjie.setText("" + data.getSubIndirect());
                    textV_tg_shimin.setText("" + data.getSubReal());
                    textV_tg_all.setText("" + data.getSum());
                    Sj_oneAdapter sj_oneAdapter = new Sj_oneAdapter(ShangHuActivity.this, data_dj);
                    listView.setAdapter(sj_oneAdapter);
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", ShangHuActivity.this,
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
