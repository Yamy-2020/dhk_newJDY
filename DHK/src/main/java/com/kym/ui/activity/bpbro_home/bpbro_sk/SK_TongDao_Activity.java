package com.kym.ui.activity.bpbro_home.bpbro_sk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.paradigm.botkit.BotKitClient;
import com.paradigm.botlib.VisitorInfo;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.kym.ui.BackDialog;
import com.kym.ui.Course1Activity;
import com.kym.ui.R;
import com.kym.ui.WebActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.fee_kf.ChatActivity;
import com.kym.ui.activity.shiming.ZhengJianActivity;
import com.kym.ui.activity.sun_util.ShoukuanBean;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.hualuo.HL_BK_WebActivity;
import com.kym.ui.info.KuaiJieCardList;
import com.kym.ui.info.KuaiJieResponse;
import com.kym.ui.newutil.DragFloatActionButton;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;
import static com.kym.ui.appconfig.IService.SHOUXINYI_SIGN;
import static com.kym.ui.appconfig.IService.SHOUXINYI_SIGN1;
import static com.zzss.jindy.appconfig.Clone.OMID;

/**
 * 选择刷卡通道
 *
 * @author sun
 * @date 2019/12/3
 */

public class SK_TongDao_Activity extends BaseActivity implements View.OnClickListener {

    private KuaiJieCardList.KuaiJieCardListInfo kuaiJieCardListInfo;
    private TopRightMenu mTopRightMenu;
    private TextView head_img_right;
    ArrayList<ShoukuanBean> testList;
    private BackDialog backDialog;
    private LabelsView labelsView;
    private TextView shiji_money;
    private String id = "kong";
    private float rate, fee;
    private EditText money;
    private Intent intent;
    private DragFloatActionButton circle_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sk_tongdao);
        initHead();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUI();
        getShouKuanList();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("选择刷卡通道");
        head_img_right = findViewById(R.id.head_img_right);
        head_img_right.setVisibility(View.VISIBLE);
        head_img_right.setOnClickListener(this);

    }

    private void initUI() {
        circle_button = findViewById(R.id.circle_button);
        circle_button.setOnClickListener(this);
        if (OMID.equals("E1TDVFFY8JX3RY62")) {
            circle_button.setVisibility(View.GONE);
        }
        kuaiJieCardListInfo = (KuaiJieCardList.KuaiJieCardListInfo) getIntent().getSerializableExtra("data");
        final LinearLayout submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        shiji_money = findViewById(R.id.shiji_money);
        money = findViewById(R.id.money);
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        money.setText(s);
                        money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().equals(".")) {
                    s = "0" + s;
                    money.setText(s);
                    money.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        money.setText(s.subSequence(0, 1));
                        money.setSelection(1);
                        return;
                    }
                }
                if (s.toString().length() < 1) {
                    submit.setBackgroundDrawable(getResources().getDrawable(R.drawable.submit_btn2));
                    submit.setEnabled(false);
                    shiji_money.setText("0.00");
                } else {
                    submit.setBackgroundDrawable(getResources().getDrawable(R.drawable.submit_btn));
                    submit.setEnabled(true);
                    float sj_money = Float.parseFloat(money.getText().toString()) - Float.parseFloat(money.getText().toString()) * rate - fee;
                    float sj = (float) (Math.round(sj_money * 100)) / 100;
                    shiji_money.setText(Float.toString(sj));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        labelsView = findViewById(R.id.labels);
        labelsView.clearAllSelect();
        labelsView.setSelectType(LabelsView.SelectType.SINGLE);

        labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                String str_data = data.toString().split("houkuanBean")[1];
                try {
                    JSONObject json = new JSONObject(str_data);
                    id = json.getString("id");
                    rate = Float.parseFloat(json.getString("rate")) / 100;
                    fee = Float.parseFloat(json.getString("fee"));
                    if (money.getText().toString().equals("")) {
                        tipView("请输入金额");
                    } else {
                        float sj_money = Float.parseFloat(money.getText().toString()) - Float.parseFloat(money.getText().toString()) * rate - fee;
                        float sj = (float) (Math.round(sj_money * 100)) / 100;
                        shiji_money.setText(Float.toString(sj));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 易宝
     */
    private void getYiBaoImg() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        Connect.getInstance().post(getApplicationContext(), IService.YIBAO_IMG, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        String code1 = obj2.get("code").toString();
                        if (code1.equals("1")) {
                            getYiBaoPay();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), ZhengJianActivity.class);
                            intent.putExtra("code1", code1);
                            intent.putExtra("type", "yibao");
                            startActivity(intent);
                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", SK_TongDao_Activity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(SK_TongDao_Activity.this, LoginActivity.class));

//                                restartApp(getApplicationContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
     * 易宝
     */
    private void getYiBaoPay() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("cardid", kuaiJieCardListInfo.getCardid());
        params.put("money", money.getText().toString());
        Connect.getInstance().post(getApplicationContext(), IService.YIBAO, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        Intent intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                        intent.putExtra("WEB_TITLE", "订单支付");
                        intent.putExtra("WEB_URL", obj2.getString("url"));
                        startActivity(intent);
                        finish();
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", SK_TongDao_Activity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(SK_TongDao_Activity.this, LoginActivity.class));

//                                restartApp(getApplicationContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
     * 获取银生宝订单
     */
    private void getPay() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("cardid", kuaiJieCardListInfo.getCardid());
        params.put("money", money.getText().toString());
        Connect.getInstance().post(getApplicationContext(), IService.KUAIJIE_PAY, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                        intent.putExtra("accountId", obj2.getString("accountId"));
                        intent.putExtra("orderNo", obj2.getString("orderNo"));
                        intent.putExtra("amount", obj2.getString("amount"));
                        intent.putExtra("memberId", obj2.getString("memberId"));
                        intent.putExtra("merchantNo", obj2.getString("merchantNo"));
                        intent.putExtra("deductCardToken", obj2.getString("deductCardToken"));
                        intent.putExtra("repayCardToken", obj2.getString("repayCardToken"));
                        intent.putExtra("repayCycle", obj2.getString("repayCycle"));
                        intent.putExtra("purpose", obj2.getString("purpose"));
                        intent.putExtra("quickPayResponseUrl", obj2.getString("quickPayResponseUrl"));
                        intent.putExtra("delegatePayResponseUrl", obj2.getString("delegatePayResponseUrl"));
                        intent.putExtra("pageResponseUrl", obj2.getString("pageResponseUrl"));
                        intent.putExtra("mac", obj2.getString("mac"));
                        intent.putExtra("url", obj2.getString("url"));
                        startActivity(intent);
                        finish();
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", SK_TongDao_Activity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(SK_TongDao_Activity.this, LoginActivity.class));

//                                restartApp(getApplicationContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
     * 佳付通交易.
     */
    private void getJFT_TRADE(String api) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("money", money.getText().toString());
        params.put("cardid", kuaiJieCardListInfo.getCardid());
        params.put("id", id);
        Connect.getInstance().post(getApplicationContext().getApplicationContext(), api, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        String is_url = obj2.getString("is_url");
                        if (is_url.equals("1")) {
                            String url = obj2.getString("url");
                            Intent intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                            intent.putExtra("WEB_TITLE", "刷卡订单");
                            intent.putExtra("WEB_URL", url);
                            startActivity(intent);
                            finish();
                        } else {

                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", SK_TongDao_Activity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(SK_TongDao_Activity.this, LoginActivity.class));

//                                restartApp(getApplicationContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
     * 获取快捷收款通道列表
     */
    private void getShouKuanList() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("channel", kuaiJieCardListInfo.getChannel());
        Connect.getInstance().post(getApplicationContext().getApplicationContext(), IService.KUAIJIE_SHOUKUAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                KuaiJieResponse response = (KuaiJieResponse) JsonUtils.parse((String) result, KuaiJieResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<KuaiJieResponse.KuaiJieInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        testList = new ArrayList<>();
                        for (int i = 1; i <= data.size(); i++) {
                            if (data.get(i - 1).getIsUse().equals("1")) {
                                String str;
                                if (data.get(i - 1).getIs_land().equals("1")) {
                                    str = "(落地)";
                                } else {
                                    str = "(线上)";
                                }
                                testList.add(new ShoukuanBean(data.get(i - 1).getName() + str, Integer.parseInt(data.get(i - 1).getId()), data.get(i - 1).getFee(), data.get(i - 1).getRate()));
                            }
                        }
                        labelsView.setLabels(testList, new LabelsView.LabelTextProvider<ShoukuanBean>() {
                            @Override
                            public CharSequence getLabelText(TextView label, int position, ShoukuanBean data) {
                                return data.getName();
                            }
                        });
                    }

                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", SK_TongDao_Activity.this,
                            R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(SK_TongDao_Activity.this, LoginActivity.class));

//                            restartApp(getApplicationContext());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.circle_button:
                VisitorInfo info = new VisitorInfo();
                info.nickName = Clone.APP_NAME + "_" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getName() + "_" + SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getUid();
                info.userName = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getName();
                info.phone = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getMobile();
                BotKitClient.getInstance().setVisitor(info);
                BotKitClient.getInstance().setPortraitUser(getResources().getDrawable(R.drawable.icon));
                BotKitClient.getInstance().setPortraitRobot(getResources().getDrawable(R.drawable.tianjia));
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                break;
            case R.id.head_img_right:
                mTopRightMenu = new TopRightMenu(SK_TongDao_Activity.this);
                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem("帮助中心"));
                menuItems.add(new MenuItem("刷卡明细"));
                menuItems.add(new MenuItem("限额列表"));
                mTopRightMenu
                        .setHeight(450)     //默认高度480
                        .showIcon(false) //默认宽度wrap_content
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                if (position == 0) {
                                    intent = new Intent(SK_TongDao_Activity.this, Course1Activity.class);
                                    intent.putExtra("type", "sk");
                                    intent.putExtra("title", "刷卡问题");
                                    startActivity(intent);
                                } else if (position == 1) {
                                    intent = new Intent(getApplicationContext(), KuaiJieDetailActivity.class);
                                    startActivity(intent);
                                } else {
                                    intent = new Intent(getApplicationContext(), SK_XianE_Activity.class);
                                    intent.putExtra("channel", kuaiJieCardListInfo.getChannel());
                                    startActivity(intent);
                                }
                            }
                        })
                        .showAsDropDown(head_img_right, -140, 0);
                break;
            case R.id.submit:
                if (money.getText().toString().equals("")) {
                    tipView("请输入刷卡金额");
                } else if (id.equals("kong")) {
                    tipView("请选择通道");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } else {
                    /**
                     * 收款通道
                     */
                    if (id.equals("1") || id.equals("4") || id.equals("14")) {
                        Intent intent = new Intent(getApplicationContext(), KuaiJieShanghuActivity.class);
                        intent.putExtra("data", kuaiJieCardListInfo);
                        intent.putExtra("ID", id);
                        intent.putExtra("cardid", kuaiJieCardListInfo.getCardid());
                        intent.putExtra("money", money.getText().toString());
                        startActivity(intent);
                        finish();
                    } else if (id.equals("2") || id.equals("3")) {
                        Intent intent = new Intent(getApplicationContext(), KuaiJieTFTShanghuActivity.class);
                        intent.putExtra("data", kuaiJieCardListInfo);
                        intent.putExtra("ID", id);
                        intent.putExtra("cardid", kuaiJieCardListInfo.getCardid());
                        intent.putExtra("money", money.getText().toString());
                        startActivity(intent);
                        finish();
                    } else if (id.equals("5")) {

                    } else if (id.equals("6")) {

                    } else if (id.equals("7")) {
                        getYiBaoImg();
                    } else if (id.equals("8")) {
                        getPay();
                    } else if (id.equals("9")) {
                        getJFT_TRADE(IService.JFT_TRADE);
                    } else if (id.equals("10")) {
                        getJFT_TRADE(IService.JQJFT_TRADE);
                    } else if (id.equals("16")|| id.equals("11") || id.equals("12") ||id.equals("13") ||id.equals("15")) {
                        getSXYQ(SHOUXINYI_SIGN);
                    } else if(id.equals("18")) {
                        getSXYQ(SHOUXINYI_SIGN1);
                    }
                }
                break;
        }
    }

    /**
     * 佳付通交易.
     */
    private void getSXYQ(String type) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", kuaiJieCardListInfo.getCardid());
        params.put("id", id);
        Connect.getInstance().post(getApplicationContext().getApplicationContext(), type, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        String is_bind = obj2.getString("is_bind");
                        if (is_bind.equals("0")) {
                            Intent intent = new Intent(getApplicationContext(), KuaiJieTFTShanghuActivity.class);
                            intent.putExtra("data", kuaiJieCardListInfo);
                            intent.putExtra("ID", id);
                            intent.putExtra("cardid", kuaiJieCardListInfo.getCardid());
                            intent.putExtra("money", money.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), KuaiJieBangKaMsgActivity.class);
                            intent.putExtra("data", kuaiJieCardListInfo);
                            intent.putExtra("ID", id);
                            intent.putExtra("cardid", kuaiJieCardListInfo.getCardid());
                            intent.putExtra("money", money.getText().toString());
                            intent.putExtra("requestId", obj2.getString("requestId"));
                            intent.putExtra("statusMsg", obj2.getString("statusMsg"));
                            startActivity(intent);
                            finish();
                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", SK_TongDao_Activity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(SK_TongDao_Activity.this, LoginActivity.class));

//                                restartApp(getApplicationContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    public void tipView(String msg) {
        backDialog = new BackDialog("", msg, "确定", SK_TongDao_Activity.this,
                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
            @Override
            public void onClick(View view) {
                backDialog.dismiss();
            }
        });
        backDialog.setCancelable(false);
        backDialog.show();
    }
}
