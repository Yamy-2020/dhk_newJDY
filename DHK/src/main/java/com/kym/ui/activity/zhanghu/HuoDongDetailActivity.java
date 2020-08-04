package com.kym.ui.activity.zhanghu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.FenRunAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.SplitterTotalTime;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.kym.ui.util.log;

import java.util.HashMap;
import java.util.List;


public class HuoDongDetailActivity extends BaseActivity implements View.OnClickListener {

    private DialogUtil dialogUtil;
    private ListView listV_fr;
    private List<SplitterTotalTime.DataBean> list;
    private String starttime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheng_ji_detail);
        Intent intent = getIntent();
        starttime = intent.getStringExtra("s");
        initview();
        getdata();
    }

    private void getdata() {
        dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("starttime", starttime);
        Connect.getInstance().post(getApplicationContext(), IService.HUODONG_DANGRIFENRUN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                SplitterTotalTime response = (SplitterTotalTime) JsonUtils.parse((String) result, SplitterTotalTime.class);
                if (response.getResult().getCode() == 10000) {
                    list = response.getData();
                    FenRunAdapter fenrn_adapter = new FenRunAdapter(HuoDongDetailActivity.this,
                            list);
                    listV_fr.setAdapter(fenrn_adapter);
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
                HuoDongDetailActivity.this.dialogUtil.dismiss();
            }

            @Override
            public void onFailure(String message) {
                HuoDongDetailActivity.this.dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    private void initview() {
        ImageView imageV_fanhui = (ImageView) findViewById(R.id.head_img_left);
        imageV_fanhui.setVisibility(View.VISIBLE);
        imageV_fanhui.setOnClickListener(this);
        TextView textV_title = (TextView) findViewById(R.id.head_text_title);
        textV_title.setVisibility(View.VISIBLE);
        textV_title.setText("活动账户分润列表");
        listV_fr = (ListView) findViewById(R.id.listV_fr);
        listV_fr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                SplitterTotalTime.DataBean DataBean = list.get(arg2);
                Intent intent = new Intent(HuoDongDetailActivity.this, HuoDongDetailListActivity.class);
                intent.putExtra("name", DataBean.getName());
                intent.putExtra("type", DataBean.getType() + "");
                log.e("getType:" + DataBean.getType());
                intent.putExtra("s", starttime);
                int i = Integer.parseInt(starttime);
                intent.putExtra("e", (i + 86400) + "");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fenruntixian, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            default:
                break;
        }

    }
}
