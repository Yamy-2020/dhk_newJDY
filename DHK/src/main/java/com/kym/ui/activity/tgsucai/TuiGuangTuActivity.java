package com.kym.ui.activity.tgsucai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.adapter.TuiGuangImgAdapter;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.TuiGuangSuCaiResponse;
import com.kym.ui.sp.SharedPrefrenceUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.JsonUtils;

import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;


/**
 * 推广图素材
 *
 * @author sun
 * @date 2019/12/3
 */
public class TuiGuangTuActivity extends BaseActivity implements View.OnClickListener {

    public  TuiGuangSuCaiResponse response;
    private TuiGuangImgAdapter adapter;
    private Intent intent;
    private PromptDialog promptDialog;
    private LinearLayout zanwu;
    private List<TuiGuangSuCaiResponse.TuiGuangInfo> data;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang_tu);
        initView();
        List<TuiGuangSuCaiResponse.TuiGuangInfo> data= SharedPrefrenceUtils.getSerializableList(this, Constant.TUI_GUANG);
        if (data==null){
            getImg();}
        else {
            adapter.setData(data);
         }
    }
    private void initView() {
        zanwu = findViewById(R.id.zanwu);
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        RecyclerView mRecyclerView = findViewById(R.id.rv_bank_list);
        tvTitle.setText("推广图");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TuiGuangImgAdapter(this, new TuiGuangImgAdapter.OnKuaiJieInfo() {

            @Override
            public void kuaijieClick(TuiGuangSuCaiResponse.TuiGuangInfo tuiGuangInfo) {
                intent = new Intent(getApplicationContext(), TuiGuangTuDetailsActivity.class);
                intent.putExtra("img", tuiGuangInfo.getShareImg());
                intent.putExtra("name", tuiGuangInfo.getName());
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }
    private void getImg() {
            promptDialog = new PromptDialog(this);
            promptDialog.showLoading("加载中");
            Connect.getInstance().post(getApplicationContext(), IService.TUIGUANGTU_SUCAI, null, new Connect.OnResponseListener() {

                @Override
                public void onSuccess(Object result) {

                    promptDialog.dismissImmediately();
                     response = (TuiGuangSuCaiResponse) JsonUtils.parse((String) result, TuiGuangSuCaiResponse.class);

                    if (response.getResult().getCode() == 10000) {
                        data = response.getData();

                        if (data != null && data.size() > 0) {
                            adapter.setData(data);
                            zanwu.setVisibility(View.GONE);

                        } else {
                            adapter.clearData();
                        }

                        SharedPrefrenceUtils.putSerializableList(TuiGuangTuActivity.this, Constant.TUI_GUANG, data);


                        zanwu.setVisibility(View.GONE);
                    } else if (response.getResult().getCode() == 99999) {
                        zanwu.setVisibility(View.VISIBLE);
                    }

                }
                @Override
                public void onFailure(String message) {
                    promptDialog.dismissImmediately();
                }
            });
        }
}
