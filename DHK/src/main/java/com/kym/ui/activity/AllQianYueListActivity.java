package com.kym.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.AllQianYueListAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.AllQianYueListResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.List;


public class AllQianYueListActivity extends BaseActivity implements View.OnClickListener {

    private AllQianYueListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_qian_yue_list);
        initHead();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        getAllQianYueList();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("签约列表");
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
        adapter = new AllQianYueListAdapter(this, new AllQianYueListAdapter.OnCardList() {

            @Override
            public void kj_card_list(AllQianYueListResponse.AllQianYueListInfo allCardListInfo, String type) {

            }
        });
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 获取用户的所有信用卡
     */
    private void getAllQianYueList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.ALL_QIANYUE_LIST, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {

                    dialogUtil.dismiss();
                    AllQianYueListResponse response = (AllQianYueListResponse) JsonUtils.parse((String) result, AllQianYueListResponse.class);
                    List<AllQianYueListResponse.AllQianYueListInfo> data = response.getData();
                if (data!=null){
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }else {
                    initView();
                }
               /* dialogUtil.dismiss();
                AllQianYueListResponse response = (AllQianYueListResponse) JsonUtils.parse((String) result, AllQianYueListResponse.class);
                List<AllQianYueListResponse.AllQianYueListInfo> data = response.getData();
                adapter.setData(data);*/
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
        }
    }
}
