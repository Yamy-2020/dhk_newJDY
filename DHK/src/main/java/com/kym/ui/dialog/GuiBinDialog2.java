package com.kym.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.UpGradeActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.GouMaiQuanYi;
import com.kym.ui.util.Connect;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import java.util.HashMap;

public class GuiBinDialog2 extends Dialog implements View.OnClickListener {
    private Context context;
    private DialogClickListener cl;
    private LinearLayout close, pay;
    private TextView tip;


    public interface DialogClickListener {
        void onClick(View view);
    }

    public GuiBinDialog2(Context context, int theme, DialogClickListener cl) {
        super(context, theme);
        this.context = context;
        this.cl = cl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.guibindalog1);
        initUI();
        initValue();
        getSjQy();
    }

    private void initUI() {
        close = findViewById(R.id.close);
        close.setOnClickListener(this);
        pay = findViewById(R.id.pay);
        pay.setOnClickListener(this);
        tip = findViewById(R.id.tip);
        tip.setOnClickListener(this);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("level", UpGradeActivity.weizhi);
        Connect.getInstance().post(context, IService.HOME_YOUHUI, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                GouMaiQuanYi response = (GouMaiQuanYi) JsonUtils.parse((String) result, GouMaiQuanYi.class);
                if (response.getResult().getCode() == 10000) {
                    if (Clone.OMID.equals("BSV3MJA0ZC6DAV11")) {
                        tip.setText(response.getData().getPayment_list().get(0).getPayment_amount() + "元/年" + response.getData().getPayment_list().get(0).getName() + "专享权益");
                    } else {
                        tip.setText(response.getData().getPayment_list().get(0).getPayment_amount() + "元/年" + response.getData().getPayment_list().get(0).getName() + "专享权益");
                    }
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

    @Override
    public void onClick(View v) {
        cl.onClick(v);
    }

}
