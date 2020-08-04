package com.kym.ui.activity.rongxinfen;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.XinYongZuJiAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.XinYongZuJiResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 信用足迹
 *
 * @author sun
 * @date 2019/12/3
 */
public class XinYong_ZuJi_Activity extends BaseActivity implements View.OnClickListener {

    private TextView li1, li2, li3;
    private XinYongZuJiAdapter adapter;
    private LinearLayout zanwu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_yong_zu_ji);
        initHead();
        initView();
        getInfo("waitkeep");
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
        tv.setText("信用足迹");
    }

    private void initView() {
        li1 = (TextView) findViewById(R.id.li1);
        li2 = (TextView) findViewById(R.id.li2);
        li3 = (TextView) findViewById(R.id.li3);
        zanwu = (LinearLayout) findViewById(R.id.zanwu);
        li1.setOnClickListener(this);
        li2.setOnClickListener(this);
        li3.setOnClickListener(this);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new XinYongZuJiAdapter(this, new XinYongZuJiAdapter.OnXinYongZuJi() {
            @Override
            public void kj_card_list(XinYongZuJiResponse.XinYongZuJiInfo xinYongZuJiInfo) {

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
            case R.id.li1:
                getInfo("waitkeep");
                li1.setTextColor(getResources().getColor(R.color.blue_2e));
                li2.setTextColor(getResources().getColor(R.color.black_33));
                li3.setTextColor(getResources().getColor(R.color.black_33));
                li1.setBackgroundResource(R.drawable.textview_botbor);
                li2.setBackgroundResource(R.drawable.textview_nobotbor);
                li3.setBackgroundResource(R.drawable.textview_nobotbor);
                break;
            case R.id.li2:
                getInfo("keep");
                li2.setTextColor(getResources().getColor(R.color.blue_2e));
                li3.setTextColor(getResources().getColor(R.color.black_33));
                li1.setTextColor(getResources().getColor(R.color.black_33));
                li2.setBackgroundResource(R.drawable.textview_botbor);
                li1.setBackgroundResource(R.drawable.textview_nobotbor);
                li3.setBackgroundResource(R.drawable.textview_nobotbor);
                break;
            case R.id.li3:
                getInfo("nokeep");
                li3.setTextColor(getResources().getColor(R.color.blue_2e));
                li2.setTextColor(getResources().getColor(R.color.black_33));
                li1.setTextColor(getResources().getColor(R.color.black_33));
                li3.setBackgroundResource(R.drawable.textview_botbor);
                li1.setBackgroundResource(R.drawable.textview_nobotbor);
                li2.setBackgroundResource(R.drawable.textview_nobotbor);
                break;
        }
    }

    /**
     * 获取信用足迹基本数据
     */
    private void getInfo(final String type) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type);
        Connect.getInstance().post(getApplicationContext().getApplicationContext(), IService.RONGXINFEN_DETAIL, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                XinYongZuJiResponse response = (XinYongZuJiResponse) JsonUtils.parse((String) result, XinYongZuJiResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<XinYongZuJiResponse.XinYongZuJiInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                        zanwu.setVisibility(View.GONE);
                    } else {
                        adapter.clearData();
                        zanwu.setVisibility(View.VISIBLE);
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
