package com.kym.ui.activity.bpbro_safe;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.igexin.sdk.PushManager;
import com.kym.ui.PassWordActivity;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.GuiZuQuanYi;
import com.kym.ui.info.LoginInfo;
import com.kym.ui.info.RegisterInfo;
import com.kym.ui.info.RequestParam;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.newutil.SharedPreferencesHelper;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kym.ui.activity.LoginActivity.accessToken_xin;
import static com.kym.ui.activity.LoginActivity.user_id;

/**
 * 安全中心
 *
 * @author sun
 * @date 2019/12/3
 */

public class UserAnQuanActivity extends BaseActivity {

    @BindView(R.id.head_text_title)
    TextView headTextTitle;
    @BindView(R.id.head_img_left)
    ImageView headImgLeft;
    @BindView(R.id.li1)
    LinearLayout li1;
    @BindView(R.id.li2)
    LinearLayout li2;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.li3)
    LinearLayout li3;
    @BindView(R.id.li4)
    LinearLayout li4;
    @BindView(R.id.li3_text)
    TextView li3Text;
    private Context mContext;
    private Dialog getCodeDialog;
    private EditText etCode, et_get_text;
    private TextView tvGetCode, title;
    private CountDownTimer timer;
    private String strPhone;
    private SPConfig spConfig;
    private SharedPreferencesHelper sp;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private AMapLocation bpLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_an_quan);
        ButterKnife.bind(this);
        spConfig = SPConfig.getInstance(getApplicationContext());
        sp = new SharedPreferencesHelper(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        mContext = this;
        intiView();
    }

    private void intiView() {
//        //声明AMapLocationClient类对象
//        AMapLocationClient mLocationClient = null;
//        //声明AMapLocationClientOption对象
//        AMapLocationClientOption mLocationOption = null;
//        //初始化AMapLocationClientOption对象
//        mLocationOption = new AMapLocationClientOption();
//        //声明定位回调监听器
//        AMapLocationListener mLocationListener = new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation amapLocation) {
//                if (amapLocation != null) {
//                    bpLocation = amapLocation;
//                }
//            }
//        };
//        //初始化定位
//        mLocationClient = new AMapLocationClient(getApplicationContext());
//        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener);
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
//        //获取一次定位结果：
//        //该方法默认为false。
//        mLocationOption.setOnceLocation(true);
//        //获取最近3s内精度最高的一次定位结果：
//        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
//        mLocationOption.setOnceLocationLatest(true);
//        //设置是否返回地址信息（默认返回地址信息
//        mLocationOption.setNeedAddress(true);
//        //关闭缓存机制
//        mLocationOption.setLocationCacheEnable(false);
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();
        findViewById(R.id.li2).setVisibility(View.GONE);
        headTextTitle.setText("安全中心");
        strPhone = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getSafe_mobile();
        if (!strPhone.equals("")) {
            li3Text.setText("修改安全手机号");
            phone.setText(strPhone.substring(0, 3) +
                    " **** " + strPhone.substring(7, 11));
        } else {
            li3Text.setText("绑定安全手机号");
        }
    }

    @OnClick({R.id.head_img_left, R.id.li1/*, R.id.li2*/, R.id.li3, R.id.li4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.li1:
                Intent toRegisterActivity1 = new Intent(getApplicationContext(), RegisterActivity_x.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("register", 1);
                toRegisterActivity1.putExtras(bundle1);
                startActivity(toRegisterActivity1);
                break;
         /*   case R.id.li2:
                startActivity(new Intent(UserAnQuanActivity.this, PassWordActivity.class));
                break;*/
            case R.id.li3:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.layout_aqsjh_code, null);
                initGetCodeDialog(view1);
                showGetCodeDialog(view1);
                break;
            case R.id.li4:
                if (!strPhone.equals("")) {
                    delAqMobile();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), "请先绑定安全手机号");
                }
                break;
        }
    }

    private void initGetCodeDialog(View view) {
        etCode = view.findViewById(R.id.et_get_code);
        et_get_text = view.findViewById(R.id.et_get_text);
        tvGetCode = view.findViewById(R.id.tv_get_code);
        title = view.findViewById(R.id.title);
        LinearLayout close = view.findViewById(R.id.close);
        if (!strPhone.equals("")) {
            title.setText("修改安全手机号");
        } else {
            title.setText("绑定安全手机号");
        }
        TextView tvConfirm = view.findViewById(R.id.tv_get_code_confirm);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCodeDialog.dismiss();
            }
        });
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_get_text.getText().toString()) || et_get_text.getText().toString().length() != 11) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入有效的手机号");
                    return;
                } else if (et_get_text.getText().toString().equals(SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getMobile())) {
                    ToastUtil.showTextToas(getApplicationContext(), "安全手机号不能和注册手机号一致");
                } else if (et_get_text.getText().toString().equals(SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getSafe_mobile())) {
                    ToastUtil.showTextToas(getApplicationContext(), "不能绑定相同的安全手机号");
                } else {
                    tvGetCode.setEnabled(false);
                    getCode();
                }
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_get_text.getText().toString()) || et_get_text.getText().toString().length() != 11) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入有效的手机号");
                    return;
                } else if (et_get_text.getText().toString().equals(SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getMobile())) {
                    ToastUtil.showTextToas(getApplicationContext(), "安全手机号不能和注册手机号一致");
                } else if (et_get_text.getText().toString().equals(SPConfig.getInstance(getApplicationContext().getApplicationContext()).getUserAllInfoNew().getSafe_mobile())) {
                    ToastUtil.showTextToas(getApplicationContext(), "不能绑定相同的安全手机号");
                } else if (TextUtils.isEmpty(etCode.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入有效的验证码");
                    return;
                } else {
                    setAqMobile();
                }
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
        params.put("mobile", et_get_text.getText().toString());
        params.put("type", "9");
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

    /**
     * 设置安全手机号
     */
    private void setAqMobile() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("safe_mobile", et_get_text.getText().toString());
        params.put("auth_code", etCode.getText().toString());
        Connect.getInstance().post(this, IService.ANQUAN_PHONE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                String zhanghao = pref.getString("zhanghao", "");
                String mima = pref.getString("mima", "");
                login(zhanghao, mima);
                ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 设置安全手机号
     */
    private void delAqMobile() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(this, IService.DELETEAQMOBILE, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                GuiZuQuanYi response = (GuiZuQuanYi) JsonUtils.parse((String) result, GuiZuQuanYi.class);
                String zhanghao = pref.getString("zhanghao", "");
                String mima = pref.getString("mima", "");
                login(zhanghao, mima);
                ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    // 开始倒计时
    private void startCountDown() {
        timer = new CountDownTimer(300000, 1000) {
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
     * 登录
     */
    private void login(final String username, final String password) {
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String szImei;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            szImei = "";
        } else {
            szImei = TelephonyMgr.getDeviceId();
        }
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setMobile(username);
        registerInfo.setPassword(password);
        String cid = pref.getString("cid", "");
        registerInfo.setCid(cid);
        registerInfo.setDevice(szImei);
        registerInfo.setAccessToken(accessToken_xin);
//        if (bpLocation.getErrorCode() == 0) {
//            registerInfo.setIs_openposition(String.valueOf(1));
//            registerInfo.setLatitude(String.format("%.2f", Double.valueOf(bpLocation.getLatitude())));
//            registerInfo.setLongitude(String.format("%.2f", Double.valueOf(bpLocation.getLongitude())));
//            registerInfo.setCurrentCity(bpLocation.getCity());
//            registerInfo.setCurrentProvinces(bpLocation.getProvince());
//        } else {
        registerInfo.setIs_openposition(String.valueOf(2));
        registerInfo.setLatitude("");
        registerInfo.setLongitude("");
        registerInfo.setCurrentCity("");
        registerInfo.setCurrentProvinces("");
//        }
        RequestParam param = new RequestParam(IService.LOGIN, registerInfo, this, Constant.LOGIN);
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
                    getUserInfo(username, password);
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

    /**
     * 获取用户信息
     */
    private void getUserInfo(final String username, final String password) {
        Connect.getInstance().post(this, IService.USER_INFO, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                NewUserResponse newresponse = (NewUserResponse) JsonUtils.parse((String) result, NewUserResponse.class);
                if (newresponse.getResult().getCode() == 10000) {
                    NewUserResponse.DataBean data = newresponse.getData();
                    editor = pref.edit();
                    sp.putString("new_user_id", user_id);
                    editor.putString("u_id", data.getUid());
                    editor.putString("zhanghao", username);
                    editor.putString("mima", password);
                    editor.apply();
                    user_id = data.getUid();
                    sp.putString("idcard_name", data.getName());
                    sp.putString("idcard_number", data.getIdcard());
                    /**
                     * 推绑定别名
                     */
                    PushManager.getInstance().bindAlias(UserAnQuanActivity.this, username + "1");
                    sp.putString("SFZ_NAME", data.getName());
                    spConfig.setAccountInfo(username, password);
                    spConfig.saveUserAllInfo(data);
                    finish();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), newresponse.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }
}
