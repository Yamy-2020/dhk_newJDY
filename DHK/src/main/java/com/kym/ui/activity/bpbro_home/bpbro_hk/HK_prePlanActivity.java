package com.kym.ui.activity.bpbro_home.bpbro_hk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kym.ui.ImgDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.HKprePlanAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.HKplanResponse;
import com.kym.ui.info.XFaddPlan;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 还款预览计划
 *
 * @author sun
 * @date 2020/1/8
 */

public class HK_prePlanActivity extends BaseActivity {
    @BindView(R.id.head_text_title)
    TextView headTextTitle;
    @BindView(R.id.head_img_left)
    ImageView headImgLeft;
    @BindView(R.id.listView_sj)
    ListView listViewSj;
    @BindView(R.id.change_card)
    TextView changeCard;
    private List<HKplanResponse.HKplanInfo.ListBean> data_dj;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ImgDialog imgDialog;
    private Intent intent;
    private Dialog getCodeDialog;
    private EditText etCode;
    private TextView tvGetCode, xf_text;
    private CountDownTimer timer;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hkpre_plan);
        ButterKnife.bind(this);
        mContext = this;
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        initView();
    }

    private void initView() {
        headTextTitle.setText("预览还款计划");
        getPrePlan();
    }

    private void initGetCodeDialog(View view) {
        etCode = view.findViewById(R.id.et_get_code);
        tvGetCode = view.findViewById(R.id.tv_get_code);
        xf_text = view.findViewById(R.id.xf_text);
        LinearLayout close = view.findViewById(R.id.close);
        xf_text.setText("请使用手机尾号" + SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getMobile().substring(7, 11) + "获取验证码");
        TextView tvConfirm = view.findViewById(R.id.tv_get_code_confirm);
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvGetCode.setEnabled(false);
                getCode();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etCode.getText().toString()) || etCode.getText().toString().length() < 4) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入有效的验证码");
                    return;
                }
                getCodeDialog.dismiss();
                addPaln();

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCodeDialog.dismiss();
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardnum", getIntent().getStringExtra("cardno"));
        params.put("mobile", SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getMobile());
        params.put("type", "8");
        Connect.getInstance().post(this, IService.AUTHCODE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (response.getResult().getCode() == 10000) {
                    startCountDown();
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                    getCodeDialog.dismiss();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
                getCodeDialog.dismiss();
            }
        });
    }

    // 开始倒计时
    private void startCountDown() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                tvGetCode.setText(String.format("重新发送(%d秒)", l / 1000));
            }

            @Override
            public void onFinish() {
                tvGetCode.setText("获取验证码");
                tvGetCode.setEnabled(true);
            }
        };
        timer.start();
    }

    private void showGetCodeDialog(View view) {
        getCodeDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        getCodeDialog.setContentView(view, lp);
        getCodeDialog.setCancelable(false);
        getCodeDialog.setCanceledOnTouchOutside(false);
        getCodeDialog.show();
    }

    /**
     * 获取预览计划
     */
    private void getPrePlan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("cardid"));
        params.put("money", getIntent().getStringExtra("money"));
        params.put("type", getIntent().getStringExtra("type"));
        params.put("number", getIntent().getStringExtra("number"));
        params.put("area", getIntent().getStringExtra("area"));
        params.put("date", getIntent().getStringExtra("date"));
        params.put("mode", getIntent().getStringExtra("mode"));
        if (getIntent().getStringExtra("type").equals("3")) {
            params.put("channelType","3");
        }
        params.put("cardsurplus", getIntent().getStringExtra("cardsurplus"));
        Connect.getInstance().post(getApplicationContext(), IService.NEW_HK_PLAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                HKplanResponse response = (HKplanResponse) JsonUtils.parse((String) result, HKplanResponse.class);
                if (response.getData() != null) {
                    data_dj = response.getData().getResult();
                    HKprePlanAdapter sj_oneAdapter = new HKprePlanAdapter(HK_prePlanActivity.this, data_dj);
                    listViewSj.setAdapter(sj_oneAdapter);
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

    /**
     * 添加计划
     */
    private void addPaln() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("cardid"));
        params.put("mobile", SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getMobile());
        params.put("auth_code", etCode.getText().toString());
        Connect.getInstance().post(getApplicationContext(), IService.ADD_HK_PLAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                XFaddPlan response = (XFaddPlan) JsonUtils.parse((String) result, XFaddPlan.class);
                if (response.getResult().getCode() == 10000) {
                    if (!Clone.OMID.equals("1H1AJD6SLKVADDM6")) {
                        intent = new Intent(getApplicationContext(), HK_planinfoActivity.class);
                        intent.putExtra("type", "only");
                        intent.putExtra("NCardId", getIntent().getStringExtra("cardid"));
                        startActivity(intent);
                        finish();
                    } else {
                        if (pref.getString("hk_kf", "").equals("NO")) {
                            imgDialog = new ImgDialog(HK_prePlanActivity.this, R.style.Theme_Dialog_Scale, new ImgDialog.Dialog3ClickListener() {
                                @Override
                                public void onClick(View view) {
                                    switch (view.getId()) {
                                        case R.id.textView1:
                                            imgDialog.dismiss();
                                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jdkf);
                                            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "jdkf", "");
                                            ToastUtil.showTextToas(getApplicationContext(), "保存成功");
                                            editor.putString("hk_kf", "YES");
                                            editor.apply();
                                            intent = new Intent(getApplicationContext(), HK_planinfoActivity.class);
                                            intent.putExtra("type", "only");
                                            intent.putExtra("NCardId", getIntent().getStringExtra("cardid"));
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case R.id.textView2:
                                            imgDialog.dismiss();
                                            editor.putString("hk_kf", "YES");
                                            editor.apply();
                                            intent = new Intent(getApplicationContext(), HK_planinfoActivity.class);
                                            intent.putExtra("type", "only");
                                            intent.putExtra("NCardId", getIntent().getStringExtra("cardid"));
                                            startActivity(intent);
                                            finish();
                                            break;
                                    }
                                }
                            });
                            imgDialog.setCancelable(false);
                            imgDialog.show();
                        } else {
                            intent = new Intent(getApplicationContext(), HK_planinfoActivity.class);
                            intent.putExtra("type", "only");
                            intent.putExtra("NCardId", getIntent().getStringExtra("cardid"));
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                    finish();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    @OnClick({R.id.head_img_left, R.id.change_card})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.change_card:
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_get_xfcode, null);
                initGetCodeDialog(view);
                showGetCodeDialog(view);
                break;
        }
    }
}
