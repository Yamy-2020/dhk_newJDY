package com.kym.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.CourseAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.CourseResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

public class Course1Activity extends BaseActivity implements View.OnClickListener {

    private CourseAdapter adapter;
    private LinearLayout zanwu;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course1);
        initHead();
        initView();
        getInfo(getIntent().getStringExtra("type"));
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText(getIntent().getStringExtra("title"));
    }

    private void initView() {
        zanwu = (LinearLayout) findViewById(R.id.zanwu);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseAdapter(this, new CourseAdapter.OnCurse() {
            @Override
            public void kj_card_list(CourseResponse.CourseInfo courseInfo) {

            }
        });
        mRecyclerView.setAdapter(adapter);
        zanwu.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }

    /**
     * 获取新手教程的信息
     */
    private void getInfo(String type) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("mark", "ksi8wjk");
        Connect.getInstance().post(this.getApplicationContext(), IService.Course, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                android.util.Log.d("常见问题", "" + result.toString());
                CourseResponse response = (CourseResponse) JsonUtils.parse((String) result, CourseResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<CourseResponse.CourseInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                        zanwu.setVisibility(View.GONE);
                    } else {
                        adapter.clearData();
                        zanwu.setVisibility(View.VISIBLE);
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", Course1Activity.this,
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
