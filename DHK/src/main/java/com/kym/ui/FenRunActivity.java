package com.kym.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.log;
import com.kym.ui.adapter.FenRunAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.SplitterTotalTime;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 还款账户分润明细
 * Created by sunmiaolong on 2019/6/20.
 */

public class FenRunActivity extends BaseActivity implements OnClickListener {
    private DialogUtil dialogUtil;
    private ListView listV_fr;
    private List<SplitterTotalTime.DataBean> list;
    private String starttime = "";
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fen_run);
        Intent intent = getIntent();
        starttime = intent.getStringExtra("s");
        initview();
        getdata();

    }

    private void getdata() {
        dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("starttime", starttime);
        Connect.getInstance().post(FenRunActivity.this, IService.SPLITTER_TOTAL_TIME, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                SplitterTotalTime response = (SplitterTotalTime) JsonUtils.parse((String) result, SplitterTotalTime.class);
                if (response.getResult().getCode() == 10000) {
                    list = response.getData();
                    FenRunAdapter fenrn_adapter = new FenRunAdapter(FenRunActivity.this,
                            list);
                    listV_fr.setAdapter(fenrn_adapter);
                } else {
                    tipView("1", response.getResult().getMsg());
                }
                FenRunActivity.this.dialogUtil.dismiss();
            }

            @Override
            public void onFailure(String message) {
                FenRunActivity.this.dialogUtil.dismiss();
                tipView("1", message);
            }
        });
    }

    private void initview() {
        ImageView imageV_fanhui = (ImageView) findViewById(R.id.head_img_left);
        imageV_fanhui.setVisibility(View.VISIBLE);
        imageV_fanhui.setOnClickListener(this);
        TextView textV_title = (TextView) findViewById(R.id.head_text_title);
        textV_title.setVisibility(View.VISIBLE);
        textV_title.setText("还款账户分润列表");
        listV_fr = (ListView) findViewById(R.id.listV_fr);
        listV_fr.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                SplitterTotalTime.DataBean DataBean = list.get(arg2);
                Intent intent = new Intent(FenRunActivity.this, FenRunDetailActivity.class);
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

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", FenRunActivity.this,
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
