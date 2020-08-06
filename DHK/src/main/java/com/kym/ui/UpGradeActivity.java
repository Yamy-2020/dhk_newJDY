package com.kym.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.dialog.GuiBinDialog1;
import com.kym.ui.dialog.GuiBinDialog2;
import com.kym.ui.info.GouMaiQuanYi;
import com.kym.ui.info.LoginInfo;
import com.kym.ui.info.RegisterInfo;
import com.kym.ui.info.RequestParam;
import com.kym.ui.sp.SharedPrefrenceUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

/**
 * 购买权益
 *
 * @author sun
 * @date 2019/12/3
 */

public class UpGradeActivity extends BaseActivity implements View.OnClickListener {

    private static final int SDK_PAY_FLAG = 2;
    public static String weizhi;
    private GuiBinDialog1 guiBinDialog;
    private GuiBinDialog2 guiBinDialog1;

    private TextView lv0_tv1, lv0_tv2;
    private ImageView lv0_head_img;
    private ListView lv;
    private BackDialog backDialog;
    private RecyclerView mRecGounai;
    private GouMaiAdapter adapter;
    private List<GouMaiQuanYi.DataBean.PaymentListBean> payment_list;
    private ArrayList<GouMaiQuanYi.DataBean.PaymentListBean> list;
    private GouMaiQuanYi.DataBean data2;
    private PopupWindow popupWindow;
    private View popupWindow_view;
    private TextView mKehu;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private TextView mTv7;
    private TextView mTv8;
    private TextView mTv9;
    private String name;
    private String zt;
    private String lf;
    private String td;
    private String zt1;
    private String lf1;
    private String td1;
    private String zt2;
    private String lf2;
    private String td2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_grade);
        initHead();
        initView();

        List<GouMaiQuanYi.DataBean.PaymentListBean> list1 = SharedPrefrenceUtils.getSerializableList(UpGradeActivity.this, "payment_list");
        if (list1 == null) {

            getHomeYouHuiShengJi();

        } else {
            getHomeYouHuiShengJi();

            adapter.notifyDataSetChanged();
        }

//        initUI();
    }

    private void initView() {


        adapter.setmOnItemClickListenter(new GouMaiAdapter.OnItemClickListenter() {
            @Override
            public void onItemClickListenter(int position) {
                getOnClickfeiyong(position);
            }

            @Override
            public void onItemClickListenter2(int position, View view) {
                showPopupWindow(view);
            }


        });

    }

    /**
     * 弹出popupwindow
     */
    private void showPopupWindow(View v) {
        name = data2.getPayment_list().get(0).getName();
        zt = payment_list.get(0).getRate_list().getSk().getZt();
        lf = payment_list.get(0).getRate_list().getSk().getLf();
        td = payment_list.get(0).getRate_list().getSk().getTd();
        zt1 = payment_list.get(0).getRate_list().getYk().getZt();
        lf1 = payment_list.get(0).getRate_list().getYk().getLf();
        td1 = payment_list.get(0).getRate_list().getYk().getTd();
        zt2 = payment_list.get(0).getRate_list().getHk().getZt();
        lf2 = payment_list.get(0).getRate_list().getHk().getLf();
        td2 = payment_list.get(0).getRate_list().getHk().getTd();
        if (popupWindow == null) {
            // 将自己定义的布局文件泵出来
            popupWindow_view = LayoutInflater.from(UpGradeActivity.this).inflate(
                    R.layout.popuplayout, null, false);
            // 创建PopupWindow实例,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT分别是宽度和高度
            popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //弹出的时候设置
            setWindowBackgroundAlpha(0.7f);
            // 设置动画效果
            popupWindow.setOutsideTouchable(true);
//            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            // 点击其他地方消失
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //背景恢复正常
                    setWindowBackgroundAlpha(1.0f);
                    popupWindow = null;
                }
            });
        }
        // 这里是位置显示方式,在屏幕的中间，0,0分别表示X,Y的偏移量
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        mKehu = popupWindow_view.findViewById(R.id.kehu);
        mTv1 = popupWindow_view.findViewById(R.id.tv1);
        mTv2 = popupWindow_view.findViewById(R.id.tv2);
        mTv3 = popupWindow_view.findViewById(R.id.tv3);
        mTv4 = popupWindow_view.findViewById(R.id.tv4);
        mTv5 = popupWindow_view.findViewById(R.id.tv5);
        mTv6 = popupWindow_view.findViewById(R.id.tv6);
        mTv7 = popupWindow_view.findViewById(R.id.tv7);
        mTv8 = popupWindow_view.findViewById(R.id.tv8);
        mTv9 = popupWindow_view.findViewById(R.id.tv9);
        mKehu.setText(name + "权益优惠");
        mTv1.setText(zt);
        mTv2.setText(lf);
        mTv3.setText(td);

        mTv4.setText(zt2);
        mTv5.setText(lf2);
        mTv6.setText(td2);

        mTv7.setText(lf1);
        mTv8.setText(zt1);
        mTv9.setText(td1);

    }

    /**
     * 关闭popupWindow
     */

    private void closePopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * 设置添加屏幕背景透明度
     *
     * @param bgAlpha
     */
    public void setWindowBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = (UpGradeActivity.this).getWindow().getAttributes();
        lp.alpha = bgAlpha;//0.0-1.0
        //有的手机需要加上下面这句，否则无效
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    private void getOnClickfeiyong(int position) {
        if (Clone.OMID.equals("IDC5EAD0TUM2QHK3") || Clone.OMID.equals("ZD4CTNB7DSLXS9B8") ||
                Clone.OMID.equals("3STWH8VK0S27SW31") || Clone.OMID.equals("ROWH57OIXPQ0R198")) {
            if (position == 0) {
                int upgrade_level = list.get(position).getUpgrade_level();
                weizhi = String.valueOf(upgrade_level);
                if (upgrade_level == 3) {
                    guiBinDialog = new GuiBinDialog1(UpGradeActivity.this,
                            R.style.Theme_Dialog_Scale, new GuiBinDialog1.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.pay:
                                    zhifubao(IService.UPGRADEORDER, String.valueOf(upgrade_level)
                                    );
                                    break;
                                case R.id.close:
                                    guiBinDialog.dismiss();
                                    break;
                            }
                        }
                    });
                    guiBinDialog.setCancelable(false);
                    guiBinDialog.show();
                } else {
                    tipView("您已经大于该等级");
                }
            }
        } else if (Clone.OMID.equals("1H1AJD6SLKVADDM6")) {
            /**
             * 九鼎信用
             */
            int upgrade_level = list.get(position).getUpgrade_level();
            weizhi = String.valueOf(upgrade_level);

            if (upgrade_level == 3) {
                guiBinDialog = new GuiBinDialog1(UpGradeActivity.this,
                        R.style.Theme_Dialog_Scale, new GuiBinDialog1.DialogClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.pay:
                                zhifubao(IService.UPGRADEORDER, String.valueOf(upgrade_level));
                                break;
                            case R.id.close:
                                guiBinDialog.dismiss();
                                break;
                        }
                    }
                });
                guiBinDialog.setCancelable(false);
                guiBinDialog.show();
            } else {
                tipView("您已经大于该等级");
            }
        } else if (Clone.OMID.equals("VIB0T31Q2L7DNDK5")) {
            if (position == 0) {
                int upgrade_level = list.get(position).getUpgrade_level();
                weizhi = String.valueOf(upgrade_level);

                if (upgrade_level == 2) {
                    guiBinDialog = new GuiBinDialog1(UpGradeActivity.this,
                            R.style.Theme_Dialog_Scale, new GuiBinDialog1.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.pay:
                                    zhifubao(IService.UPGRADEORDER, String.valueOf(upgrade_level));
                                    break;
                                case R.id.close:
                                    guiBinDialog.dismiss();
                                    break;
                            }
                        }
                    });
                    guiBinDialog.setCancelable(false);
                    guiBinDialog.show();
                } else {
                    tipView("您已经大于该等级");
                }
            }
        } else {

            if (position == 0) {
                int upgrade_level3 = list.get(0).getUpgrade_level();

                weizhi = String.valueOf(upgrade_level3);
                if (upgrade_level3 == 1) {
                    guiBinDialog1 = new GuiBinDialog2(UpGradeActivity.this,
                            R.style.Theme_Dialog_Scale, new GuiBinDialog2.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.pay:
                                    zhifubao(IService.UPGRADEORDER, String.valueOf(upgrade_level3));
                                    break;
                                case R.id.close:
                                    guiBinDialog1.dismiss();
                                    break;
                            }
                        }
                    });
                    guiBinDialog1.setCancelable(false);
                    guiBinDialog1.show();
                } else {
                    guiBinDialog1 = new GuiBinDialog2(UpGradeActivity.this,
                            R.style.Theme_Dialog_Scale, new GuiBinDialog2.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.pay:
                                    zhifubao(IService.UPGRADEORDER, String.valueOf(upgrade_level3));
                                    break;
                                case R.id.close:
                                    guiBinDialog1.dismiss();
                                    break;
                            }
                        }
                    });
                    guiBinDialog1.setCancelable(false);
                    guiBinDialog1.show();
                }
            }
            if (position == 1) {
                int upgrade_level = list.get(1).getUpgrade_level();
                weizhi = String.valueOf(upgrade_level);

                if (upgrade_level == 3) {
                    guiBinDialog = new GuiBinDialog1(UpGradeActivity.this,
                            R.style.Theme_Dialog_Scale, new GuiBinDialog1.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.pay:
                                    zhifubao(IService.UPGRADEORDER, String.valueOf(upgrade_level));
                                    break;
                                case R.id.close:
                                    guiBinDialog.dismiss();
                                    break;
                            }
                        }
                    });
                    guiBinDialog.setCancelable(false);
                    guiBinDialog.show();
                } else {
                    tipView("您已经大于该等级");
                }
            }
        }
    }

    //新政策升级
    private void getHomeYouHuiShengJi() {
        final DialogUtil dialogUtil = new DialogUtil(this);

        Connect.getInstance().post(UpGradeActivity.this, IService.HOME_YOUHUI, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                GouMaiQuanYi data1 = (GouMaiQuanYi) JsonUtils.parse((String) result, GouMaiQuanYi.class);
                if (data1.getResult().getCode() == 10000) {
                    data2 = data1.getData();
                    payment_list = data2.getPayment_list();


                    SharedPrefrenceUtils.putSerializableList(UpGradeActivity.this, "payment_list", payment_list);

                    String s = data1.getData().getCurrent_list().getCurrent_msg();
                    String name = data1.getData().getCurrent_list().getName();
                    String imgurl = data1.getData().getCurrent_list().getTop_imgurl();
                    if (payment_list != null && payment_list.size() > 0) {
                        list.addAll(payment_list);
                    }
                    adapter.notifyDataSetChanged();

                    if (Clone.OMID.equals("1H1AJD6SLKVADDM6")) {
                        lv0_tv2.setText(s);
                        lv0_tv1.setText(name);
                        Glide.with(UpGradeActivity.this).load(imgurl).dontAnimate().placeholder(R.drawable.default_image).into(lv0_head_img);

                    } else {
                        lv0_tv2.setText(s);
                        lv0_tv1.setText(name);
                        Glide.with(UpGradeActivity.this).load(imgurl).dontAnimate().placeholder(R.drawable.default_image).into(lv0_head_img);

                    }

                } else if (data1.getResult().getCode() == 101 || data1.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", UpGradeActivity.this,
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
                    ToastUtil.showTextToas(getApplicationContext(), data1.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }


    private void initHead() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = findViewById(R.id.head_text_title);
        findViewById(R.id.gone).setVisibility(View.GONE);
        title.setText("购买权益");

        lv0_tv1 = findViewById(R.id.lv0_tv1);
        lv0_tv2 = findViewById(R.id.lv0_tv2);
        lv0_head_img = findViewById(R.id.lv0_head_img);
        mRecGounai = findViewById(R.id.rec_gounai);
        mRecGounai.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new GouMaiAdapter(this, list);
        mRecGounai.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up:
                Intent intent1 = new Intent(UpGradeActivity.this, LianxiActivity_new.class);
                intent1.putExtra("title", "2");
                startActivity(intent1);
                break;
            case R.id.head_img_left:
                finish();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //**对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        login();
                        backDialog = new BackDialog("", "支付成功", "确定", UpGradeActivity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        tipView("支付失败");
                    }

                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private void login() {
        SPConfig spConfig = SPConfig.getInstance(getApplicationContext());
        HashMap<String, String> accountInfo = spConfig.getAccountInfo();
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setMobile(accountInfo.get(Constant.USERNAME));
        registerInfo.setPassword(accountInfo.get(Constant.PASSWORD));
        registerInfo.setCode("");
        registerInfo.setAccessToken(LoginActivity.accessToken_xin);
        registerInfo.setIs_openposition(String.valueOf(2));
        registerInfo.setLatitude("");
        registerInfo.setLongitude("");
        RequestParam param = new RequestParam(IService.LOGIN, registerInfo, getApplicationContext(), Constant.LOGIN);
        Connect.getInstance().httpUtil(param, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                LoginInfo loginData = (LoginInfo) result;
                int code = loginData.getResult().getCode();
                if (code == 10000) {
                    SPConfig spConfig = SPConfig.getInstance(getApplicationContext());
                    LoginInfo.LoginChildInfo data = loginData.getData();
                    spConfig.setUserInfoStatus(data.getStatus_auth());
                    spConfig.setUserInfoPercent(data.getStatus_perfect());
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), loginData.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    private void zhifubao(String url, String level) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("upgrade_level", level);
        Connect.getInstance().post(this, url, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String data = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(data);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        final String orderInfo = obj2.get("data").toString();
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(UpGradeActivity.this);
                                Map<String, String> result = alipay.payV2(orderInfo, true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (JSONException e) {
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
        backDialog = new BackDialog("", msg, "确定", UpGradeActivity.this,
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
