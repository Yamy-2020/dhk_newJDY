package com.kym.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import org.json.JSONObject;

public class JiaoYi_Activity extends BaseActivity implements View.OnClickListener {

    private String day_total_vol, day_rec_vol, day_repx_vol, day_conx_vol,
            team_total_vol, team_rec_vol, team_repx_vol, team_conx_vol;
    private TextView total, xf_total, hk_total, sk_total, total_tip,
            btn_gr1, btn_gr2, btn_team1, btn_team2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiao_yi_);
        initHead();
        initView();
        getJiaoYi();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("昨日交易量");
    }

    private void initView() {
        total = findViewById(R.id.total);
        xf_total = findViewById(R.id.xf_total);
        hk_total = findViewById(R.id.hk_total);
        sk_total = findViewById(R.id.sk_total);
        total_tip = findViewById(R.id.total_tip);
        btn_gr1 = findViewById(R.id.btn_gr1);
        btn_team1 = findViewById(R.id.btn_team1);
        btn_gr2 = findViewById(R.id.btn_gr2);
        btn_team2 = findViewById(R.id.btn_team2);
        btn_gr1.setOnClickListener(this);
        btn_team1.setOnClickListener(this);
        btn_gr2.setOnClickListener(this);
        btn_team2.setOnClickListener(this);
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
                        day_total_vol = obj2.getString("day_total_vol");
                        day_rec_vol = obj2.getString("day_rec_vol");
                        day_repx_vol = obj2.getString("day_repx_vol");
                        day_conx_vol = obj2.getString("day_conx_vol");
                        team_total_vol = obj2.getString("team_total_vol");
                        team_rec_vol = obj2.getString("team_rec_vol");
                        team_repx_vol = obj2.getString("team_repx_vol");
                        team_conx_vol = obj2.getString("team_conx_vol");
                        total.setText(day_total_vol);
                        sk_total.setText(day_rec_vol);
                        hk_total.setText(day_repx_vol);
                        xf_total.setText(day_conx_vol);
                        total_tip.setText("个人总交易量");
                        btn_gr1.setVisibility(View.VISIBLE);
                        btn_team1.setVisibility(View.VISIBLE);
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
                total_tip.setText("个人总交易量");
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
                total_tip.setText("团队总交易量");
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
                total_tip.setText("个人总交易量");
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
                total_tip.setText("团队总交易量");
                btn_gr1.setVisibility(View.GONE);
                btn_gr2.setVisibility(View.VISIBLE);
                btn_team1.setVisibility(View.GONE);
                btn_team2.setVisibility(View.VISIBLE);
                break;
        }
    }
}
