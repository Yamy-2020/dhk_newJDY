package com.kym.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.BenJinZhangHuAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.BenJinZhangHu;
import com.kym.ui.util.Connect;
import com.kym.ui.util.JsonUtils;

import java.util.List;

/**
 * 本金转存弹窗
 */
public class BenJinZCDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private DialogClickListener cl;
    private LinearLayout btn, close;
    private BenJinZhangHuAdapter adapter;
    public static String type;


    public interface DialogClickListener {
        void onClick(View view);
    }

    public BenJinZCDialog(Context context, int theme, DialogClickListener cl) {
        super(context, theme);
        this.context = context;
        this.cl = cl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.benjinzcdalog);
        initUI();
        initValue();
        getZczh();
    }

    private void initUI() {
        RecyclerView mRecyclerView = findViewById(R.id.bjzh_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BenJinZhangHuAdapter(context, new BenJinZhangHuAdapter.OnKuaiJieCardList() {

            @Override
            public void kj_card_list(BenJinZhangHu.BenJInZhangHuInfo kuaiJieCardListInfo) {
                type = kuaiJieCardListInfo.getType();
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        mRecyclerView.setAdapter(adapter);
        close = findViewById(R.id.close);
        close.setOnClickListener(this);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    private void initValue() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }


    /**
     * 获取可转存账户
     */
    private void getZczh() {
        Connect.getInstance().post(context, IService.BRNJINYONGHUZHANGHU, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                BenJinZhangHu response = (BenJinZhangHu) JsonUtils.parse((String) result, BenJinZhangHu.class);
                if (response.getResult().getCode() == 10000) {
                    List<BenJinZhangHu.BenJInZhangHuInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                        type = data.get(0).getType();
                    } else {
                        adapter.clearData();
                    }
                }

            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(context, message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        cl.onClick(v);
    }

}
