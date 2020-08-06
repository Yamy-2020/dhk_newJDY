package com.kym.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.igexin.sdk.PushManager;
import com.jaeger.library.StatusBarUtil;
import com.leon.commons.util.ApplicationUtil;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.RegisterActivity;
import com.kym.ui.activity.SecondActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.LoginInfo;
import com.kym.ui.info.RegisterInfo;
import com.kym.ui.info.RequestParam;
import com.kym.ui.info.Shenji_all_Info;
import com.kym.ui.info.Shenji_data_info;
import com.kym.ui.info.TokenInfo;
import com.kym.ui.info.Token_infoData;
import com.kym.ui.info.TongZhi;
import com.kym.ui.info.TongZhiDatum;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.newutil.SharedPreferencesHelper;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.kym.ui.util.LockView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.kym.ui.activity.LoginActivity.user_id;

/**
 * 手势登录
 *
 * @author sun
 * @date 2019/12/3
 */

public class PatterLoginActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private static final int PERMISSIONS_REQUEST_CODE = 10086;
    private static final String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

    private Context mContext;
    private LockView lvLock;
    private TextView lockText, fg_text, text_title_code;
    private String pwd;
    public static Activity activity;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Intent intent;
    private DialogUtil dialogUtil;
    public static String dl_phone;
    public static String dl_pw;
    private SharedPreferencesHelper sp;
    private SPConfig spConfig;
    public static String accessToken_xin = "";
    private List<TongZhiDatum> tzlist = new ArrayList<>();
    private BackDialog backDialog;
    private Dialog getCodeDialog;
    private EditText etCode;
    private TextView tvGetCode;
    private CountDownTimer timer;
    private AMapLocation bpLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patter_login);
        //声明AMapLocationClient类对象
        AMapLocationClient mLocationClient = null;
        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = null;
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //声明定位回调监听器
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        bpLocation = amapLocation;
                        //可在其中解析amapLocation获取相应内容。
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation);
                    }
                }

            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息
        mLocationOption.setNeedAddress(true);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

        permissionsTask();

        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_color), 0);
        mContext = this;
        sp = new SharedPreferencesHelper(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        spConfig = SPConfig.getInstance(getApplicationContext());
        lvLock = findViewById(R.id.lv_lock);
        lockText = findViewById(R.id.lock_text);
        fg_text = findViewById(R.id.fg_text);
        fg_text.setOnClickListener(this);
        initHead();
        initToken(2);
        final Animation shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
        lvLock.setOnDrawFinish(new LockView.OnDrawFinish() {
            @Override
            public void oneDraw(List<Integer> passwords) {
                pwd = passwords.toString();
                String patternPwd = pref.getString("patternPwd", "");
                String num = pref.getString("num", "");
                if (pwd.equals(patternPwd)) {
                    if (getIntent().getStringExtra("type").equals("1")) {
                        editor = pref.edit();
                        editor.putString("isPwd", "NO");
                        editor.putString("patternPwd", "");
                        editor.apply();
                        startActivity(new Intent(PatterLoginActivity.this, PassWordActivity.class));
                        finish();
                    } else if (getIntent().getStringExtra("type").equals("2")) {
                        startActivity(new Intent(PatterLoginActivity.this, PatternActivity.class));
                        finish();
                    } else if (getIntent().getStringExtra("type").equals("3")) {

                        login(pref.getString("zhanghao", ""),
                                pref.getString("mima", ""), "");
                    }

                } else {
                    if (num.equals("0")) {
                        editor = pref.edit();
                        editor.putString("isPwd", "NO");
                        editor.putString("patternPwd", "");
                        editor.apply();
                        intent = new Intent(PatterLoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                        PatterLoginActivity.this.finish();
                        ToastUtil.showTextToas(getApplicationContext(), "手势密码已失效，请重新登录");
                    } else {
                        lockText.setText("密码错误，还可以再输入" + num + "次");
                        lockText.setTextColor(Color.parseColor("#9e9e9e"));
                        lockText.startAnimation(shakeAnim);
                        editor = pref.edit();
                        editor.putString("num", String.valueOf(Integer.parseInt(num) - 1));
                        editor.apply();
                        lvLock.cancel();
                    }
                }
            }

            @Override
            public void twoDraw(List<Integer> passwords) {

            }
        });
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        tvTitle.setText(getIntent().getStringExtra("title"));
        if (getIntent().getStringExtra("type").equals("3")) {
            findViewById(R.id.head_img_left).setVisibility(View.GONE);
            lockText.setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.head_img_left).setOnClickListener(this);
            findViewById(R.id.head_img_left).setVisibility(View.VISIBLE);
            lockText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fg_text:
                backDialog4 = new BackDialog3("确定", "取消", "提示", "忘记手势密码，需要重新登录",
                        PatterLoginActivity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.textView1:
                                backDialog4.dismiss();
                                Intent intent = new Intent(PatterLoginActivity.this, LoginActivity.class);
                                editor = pref.edit();
                                editor.putString("isPwd", "NO");
                                editor.putString("patternPwd", "");
                                editor.apply();
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.textView2:
                                backDialog4.dismiss();
                                break;
                        }
                    }
                });
                backDialog4.setCancelable(false);
                backDialog4.show();
                break;
            case R.id.head_img_left:
                finish();
                break;
        }
    }

    /**
     * 获取AccessToken
     */
    private void initToken(final int type) {
        dialogUtil = new DialogUtil(this);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setAccount("credit_android_01");
        tokenInfo.setKey("6D22CF9BBBD804E730A7AF355F44CFBD");
        RequestParam param = new RequestParam(IService.GET_TOKEN, tokenInfo, this, Constant.GET_TOKEN);
        Connect.getInstance().httpUtil(param, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                Token_infoData tokenData = (Token_infoData) result;
                accessToken_xin = tokenData.getAccessToken();
                switch (type) {
                    case 0:
                        login(pref.getString("zhanghao", ""),
                                pref.getString("mima", ""), "");
                        break;
                    case 1:
                        Intent intent = new Intent(PatterLoginActivity.this, RegisterActivity.class);
                        intent.putExtra("register", 1);
                        startActivity(intent);
                        break;
                    case 2:
                        sp.putString("new_accesstoken", accessToken_xin);
                        checkAppVersion();
                        break;
                    default:
                        break;
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
     * 检查版本更新
     */
    protected void checkAppVersion() {
        dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        int versionCode = ApplicationUtil.getLocalVersionCode(this);
        params.put("version_code", String.valueOf(versionCode));
        params.put("os", "1");
        Connect.getInstance().post(this, IService.UPGRADE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                Shenji_all_Info info = (Shenji_all_Info) JsonUtils.parse((String) result, Shenji_all_Info.class);
                if (info.getResult().getCode() == 10000) {
                    dialogUtil.dismiss();
                    Shenji_data_info shenji_data_info = info.getData();
                    switch (shenji_data_info.getIs_update()) {
                        case "1":
                            /**
                             * 非强制更新
                             */
                            dialog_qz_xz(shenji_data_info.getVersion_desc(), shenji_data_info.getDown_url());
                            break;
                        case "2":
                            /**
                             * 强制更新
                             */
                            dialog_qz_img(shenji_data_info.getVersion_desc(), shenji_data_info.getDown_url());
                            break;
                        case "0":
                            /**
                             * 无需更新
                             */
                            getSystemMessage();
                            break;
                        default:
                            break;
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), info.getResult().getMsg());
                    dialogUtil.dismiss();
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
     * 强制更新-图片
     *
     * @param img_url
     */
    private void dialog_qz_img(String img_url, String url) {
        Dialog dialog_two = new Dialog(this, R.style.MyDialgoStyle_xin_x);
        dialog_two.setCancelable(false);
        Window window = dialog_two.getWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_model_img, null);
        ImageView update_img = (ImageView) view.findViewById(R.id.update_img);
        Glide.with(getApplicationContext()).load(img_url).dontAnimate().into(update_img);
        view.findViewById(R.id.update_img).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse(url));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        });
        window.setContentView(view);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog_two.show();
    }

    private void dialog_qz_xz(String desc, final String url) {
        final Dialog dialog_thre = new Dialog(this, R.style.MyDialgoStyle_xin_x);
        dialog_thre.setCancelable(false);
        Window window = dialog_thre.getWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_model_xx, null);
        TextView textV_neirong = (TextView) view.findViewById(R.id.textV_neirong_xx);
        textV_neirong.setText(desc);
        view.findViewById(R.id.textv_ok).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse(url));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.textv_qx).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_thre.dismiss();
            }
        });
        window.setContentView(view);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog_thre.show();
    }

    /**
     * 获取系统消息
     */
    private void getSystemMessage() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(this, IService.MSGPUSH_FORCE, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                TongZhi info_msg = (TongZhi) JsonUtils.parse((String) result, TongZhi.class);
                if (info_msg.getResult().getCode() == 10000) {
                    if (info_msg.getData().size() != 0) {
                        tzlist.addAll(info_msg.getData());
                        if (getIntent().getStringExtra("title").equals("登录")) {
                            showUpdata();
                        }
                    }
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
            }
        });
    }

    private BackDialog3 backDialog4;

    private void showUpdata() {
        for (int i = 0; i < tzlist.size(); i++) {
            if (tzlist.get(i).getType().equals("1")) {
                backDialog = new BackDialog(tzlist.get(i).getTitle(), tzlist.get(i).getContent(), "确定", PatterLoginActivity.this,
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
    }


    /**
     * 登录
     */
    private void login(final String username, final String password, String code) {
        final DialogUtil dialogUtil = new DialogUtil(this);
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
        registerInfo.setCode(code);
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
                dialogUtil.dismiss();
                dl_phone = username;
                dl_pw = password;
                LoginInfo loginData = (LoginInfo) result;
                int code = loginData.getResult().getCode();
                if (code == 10000) {
                    SPConfig spConfig = SPConfig.getInstance(getApplicationContext());
                    LoginInfo.LoginChildInfo data = loginData.getData();
                    spConfig.setUserInfoStatus(data.getStatus_auth());
                    spConfig.setUserInfoPercent(data.getStatus_perfect());
                    getUserInfo(username, password);
                } else if (code == 103) {//异地登录
                    if (dialogUtil != null) {
                        dialogUtil.dismiss();
                    }
                    if (getCodeDialog == null) {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_get_code, null);
                        initGetCodeDialog(view, "");
                        showGetCodeDialog(view);
                    } else {
                        etCode.setText("");
                        getCodeDialog.show();
                    }
                } else if (code == 104) {//不同设备登录
                    if (dialogUtil != null) {
                        dialogUtil.dismiss();
                    }
                    if (getCodeDialog == null) {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_get_divide_code, null);
                        initGetCodeDialog(view, loginData.getResult().getMsg());
                        showGetCodeDialog(view);
                    } else {
                        etCode.setText("");
                        getCodeDialog.show();
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), loginData.getResult().getMsg());
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
     * 获取用户信息
     */
    private void getUserInfo(final String username, final String password) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(this, IService.USER_INFO, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                NewUserResponse newresponse = (NewUserResponse) JsonUtils.parse((String) result, NewUserResponse.class);
                if (newresponse.getResult().getCode() == 10000) {
                    NewUserResponse.DataBean data = newresponse.getData();
                    editor = pref.edit();
                    sp.putString("new_user_id", user_id);
                    editor.putString("u_id", data.getUid());
                    user_id = data.getUid();
                    sp.putString("idcard_name", data.getName());
                    sp.putString("idcard_number", data.getIdcard());
                    // 推绑定别名
                    PushManager.getInstance().bindAlias(PatterLoginActivity.this, username + "1");
                    sp.putString("SFZ_NAME", data.getName());
                    spConfig.setAccountInfo(username, password);
                    spConfig.saveUserAllInfo(data);

                    Intent intent = new Intent(mContext, SecondActivity.class);
                    intent.putExtra("tzlist", tzlist.toString());
                    intent.putExtra("level", data.getLevel());
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), newresponse.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    private void showGetCodeDialog(View view) {
        getCodeDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        getCodeDialog.setContentView(view, lp);
        getCodeDialog.setCancelable(true);
        getCodeDialog.setCanceledOnTouchOutside(false);
        getCodeDialog.show();
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", pref.getString("zhanghao", ""));
        params.put("type", "6");
        Connect.getInstance().post(this, IService.AUTHCODE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                if (response.getResult().getCode() == 10000) {
                    startCountDown();
                }
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

    private void initGetCodeDialog(View view, String mobile) {
        etCode = view.findViewById(R.id.et_get_code);
        tvGetCode = view.findViewById(R.id.tv_get_code);
        text_title_code = view.findViewById(R.id.text_title_code);
        text_title_code.setText("设备验证,已发送至" + mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length()));
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
                    ToastUtil.showTextToas(getApplicationContext(), "请输入有效验证码");
                    return;
                }
                login(pref.getString("zhanghao", ""),
                        pref.getString("mima", ""), etCode.getText().toString());
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @AfterPermissionGranted(PERMISSIONS_REQUEST_CODE)
    public void permissionsTask() {
        if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {

        } else {
            EasyPermissions.requestPermissions(this, "请开启权限,以免影响正常功能使用", PERMISSIONS_REQUEST_CODE, PERMISSIONS);
        }
    }
}
