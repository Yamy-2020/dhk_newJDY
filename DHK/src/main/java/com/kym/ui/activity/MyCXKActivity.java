package com.kym.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.shiming.NewUpdateCxkActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.AccessTodebit;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

/**
 * 我的储蓄卡
 *
 * @author sun
 * @date 2019/12/3
 */

public class MyCXKActivity extends BaseActivity implements View.OnClickListener {

    private TextView bank_name, bank_num, change_card;
    private CircleImageView bank_logo;
    private Intent intent;
    private BackDialog backDialog;
    private final int FANHUICODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cxk);
        initView();
        initHead();
        getCXK();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("我的储蓄卡");
    }

    private void initView() {
        bank_logo = findViewById(R.id.bank_logo);
        bank_name = findViewById(R.id.bank_name);
        bank_num = findViewById(R.id.bank_num);
        change_card = findViewById(R.id.change_card);
        change_card.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                intent = new Intent(MyCXKActivity.this, NewUpdateCxkActivity.class);
                startActivityForResult(intent, FANHUICODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FANHUICODE) {
            getCXK();
        }
    }

    /**
     * 获取用户的储蓄卡信息
     */
    private void getCXK() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.ACCESSTODEBIT, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                AccessTodebit response = (AccessTodebit) JsonUtils.parse((String) result, AccessTodebit.class);
                if (response.getResult().getCode() == 10000) {
                    AccessTodebit.DataBean bank = response.getData();
                    String bank_no = bank.getBank_no();
                    String substring = bank_no.substring(bank_no.length() - 4, bank_no.length());
                    bank_num.setText("**** **** **** " + substring);
                    bank_name.setText(bank.getBank_name());
                    Glide.with(MyCXKActivity.this).load(bank.getLogo_url()).dontAnimate().placeholder(R.drawable.default_image).into(bank_logo);
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

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", MyCXKActivity.this,
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

