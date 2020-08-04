package com.kym.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.GuiZuQuanYiAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.GuiBinDcResponse;
import com.kym.ui.info.GuiZuQuanYi;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.List;

public class GuiBinDialog extends Dialog implements View.OnClickListener {
    private GuiZuQuanYiAdapter adapter;
    private Context context;
    private DialogClickListener cl;
    private LinearLayout close, pay;
    private TextView tip, money;

    public interface DialogClickListener {
        void onClick(View view);
    }

    public GuiBinDialog(Context context, int theme, DialogClickListener cl) {
        super(context, theme);
        this.context = context;
        this.cl = cl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.guibindalog);
        initUI();
        initValue();
        getSjQy();
        getSjQyInfo();
    }

    private void initUI() {
        RecyclerView mRecyclerView = findViewById(R.id.sjhyqy_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new GuiZuQuanYiAdapter(context, new GuiZuQuanYiAdapter.OnKuaiJieCardList() {

            @Override
            public void kj_card_list(GuiZuQuanYi.GuiZuQuanYiInfo kuaiJieCardListInfo) {

            }
        });
        mRecyclerView.setAdapter(adapter);
        close = findViewById(R.id.close);
        close.setOnClickListener(this);
        pay = findViewById(R.id.pay);
        pay.setOnClickListener(this);
        tip = findViewById(R.id.tip);
        tip.setOnClickListener(this);
        money = findViewById(R.id.money);
        money.setOnClickListener(this);
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
     * 获取用户升级权益
     */
    private void getSjQy() {
        Connect.getInstance().post(context, IService.SHENGJIQUANYI, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                GuiBinDcResponse response = (GuiBinDcResponse) JsonUtils.parse((String) result, GuiBinDcResponse.class);
                if (response.getResult().getCode() == 10000) {
                    tip.setText(response.getData().getSheng());
                    money.setText("¥" + response.getData().getFee() + "/年");
                } else {
                    ToastUtil.showTextToas(getContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getContext(), message);
            }
        });
    }

    /**
     * 获取用户升级权益信息
     */
    private void getSjQyInfo() {
        final DialogUtil dialogUtil = new DialogUtil(getContext());
        Connect.getInstance().post(context, IService.SHENGJIQUANYIINFO, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                GuiZuQuanYi response = (GuiZuQuanYi) JsonUtils.parse((String) result, GuiZuQuanYi.class);
                if (response.getResult().getCode() == 10000) {
                    List<GuiZuQuanYi.GuiZuQuanYiInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                    } else {
                        adapter.clearData();
                    }
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getContext(), message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        cl.onClick(v);
    }

}
