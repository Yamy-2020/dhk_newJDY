package com.kym.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.bumptech.glide.Glide;
import com.igexin.sdk.PushManager;
import com.jaeger.library.StatusBarUtil;
import com.leon.commons.util.ApplicationUtil;
import com.thl.fingerlib.FingerprintIdentify;
import com.thl.fingerlib.base.BaseFingerprint;
import com.kym.ui.BackDialog;
import com.kym.ui.FingerDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.bpbro_safe.RegisterActivity_x;
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
import com.kym.ui.util.Connect.OnResponseListener;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.zzss.jindy.appconfig.Clone.APP_NAME;
import static com.zzss.jindy.appconfig.Clone.OMID;


/**
 * 登录
 *
 * @author sun
 * @date 2019/12/3
 */

public class LoginActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private static final int PERMISSIONS_REQUEST_CODE = 10086;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    public  static final String VERSION="2.0.0";
    public static String accessToken_xin = "", host = "", dl_phone, dl_pw, user_id, isFinger, img_type;
    public static String uid,mobile;

    @BindView(R.id.login_tet)
    TextView loginTet;
    @BindView(R.id.li2)
    LinearLayout li2;
    @BindView(R.id.loging_text_tel)
    EditText logingTextTel;
    @BindView(R.id.loging_text_pwd)
    EditText logingTextPwd;
    @BindView(R.id.li1)
    LinearLayout li1;
    @BindView(R.id.loging_img_select)
    ImageView logingImgSelect;
    @BindView(R.id.login_logo)
    TextView loginLogo;
    @BindView(R.id.del_phone_iv_new)
    ImageView delPhoneIvNew;
    private TextView tvGetCode, text_title_code;
    private FingerDialog fingerDialog, fingerDialog1, fingerDialog2, fingerDialog3;
    private List<TongZhiDatum> tzlist = new ArrayList<>();
    private SPConfig spConfig;
    private Context mContext;
    public static Activity activity;
    private Boolean showPassword = false;
    private SharedPreferencesHelper sp;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Dialog getCodeDialog;
    private EditText etCode;
    private CountDownTimer timer;
    private FingerprintIdentify mFingerprintIdentify;
    private static final int MAX_AVAILABLE_TIMES = 3;
    private BackDialog backDialog;
    private Intent intent;
    private AMapLocation bpLocation;
    private static Token_infoData tokenData;
    public static int versionCode;
    private  Shenji_all_Info info;
    private static Shenji_data_info shenji_data_info;
    private static String code ;
    private static PackageInfo info1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);
        initAccessToken();
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
                    bpLocation = amapLocation;
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
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

        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_color), 0);
        ButterKnife.bind(this);
        sp = new SharedPreferencesHelper(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        mFingerprintIdentify = new FingerprintIdentify(this, null);
        isFinger = pref.getString("isFinger", "");
        mContext = this;
        activity = this;
        initView();
        initToken(2);
        permissionsTask();
    }



    /**
     * 获取AccessToken
     */
    private void initToken(final int type) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setAccount("credit_android_01");
        tokenInfo.setKey("6D22CF9BBBD804E730A7AF355F44CFBD");
        RequestParam param = new RequestParam(IService.GET_TOKEN, tokenInfo, this, Constant.GET_TOKEN);
        Connect.getInstance().httpUtil(param, new OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                tokenData = (Token_infoData) result;
                accessToken_xin = tokenData.getAccessToken();
                switch (type) {
                    case 0:
                        login(logingTextTel.getText().toString(), logingTextPwd.getText().toString(), "");
                        break;
                    case 1:
                        toRegisterActivity(1);
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
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        versionCode = ApplicationUtil.getLocalVersionCode(this);
    /*    params.put("version_code", String.valueOf(versionCode));
        params.put("os", "1");*/
/*              params.put("version", String.valueOf(versionCode));
//        params.put("os", "1");
        params.put("terminal","android");*/
        Connect.getInstance().post(this, IService.UPGRADE, params, new OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                Shenji_all_Info info = (Shenji_all_Info) JsonUtils.parse((String) result, Shenji_all_Info.class);
                if (info.getResult().getCode() == 10000) {
                    shenji_data_info = info.getData();
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
                            dialog_qz_img(shenji_data_info.getVersion_img(), shenji_data_info.getDown_url());
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
                    ToastUtil.showTextToas(getApplicationContext(), "请联系客服");
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
        ImageView update_img = view.findViewById(R.id.update_img);
        Glide.with(getApplicationContext()).load(img_url).dontAnimate().into(update_img);
        view.findViewById(R.id.update_img).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse(url));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        });
        window.setContentView(view);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog_two.show();
    }

    /**
     * 非强制更新
     *
     * @param desc
     * @param url
     */
    private void dialog_qz_xz(String desc, final String url) {
        final Dialog dialog_thre = new Dialog(this, R.style.MyDialgoStyle_xin_x);
        dialog_thre.setCancelable(false);
        Window window = dialog_thre.getWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_model_xx, null);
        TextView textV_neirong = view.findViewById(R.id.textV_neirong_xx);
        textV_neirong.setText(desc);
        view.findViewById(R.id.textv_ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse(url));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.textv_qx).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_thre.dismiss();
            }
        });
        window.setContentView(view);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog_thre.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2000) {
            switch (requestCode) {
                case 8:
                    if (TextUtils.isEmpty(accessToken_xin)) {
                        initToken(0);
                    } else {
                        login(logingTextTel.getText().toString(), logingTextPwd.getText().toString(), "");
                    }
                    break;
                case AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE:
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 获取弹窗信息
     */
    private void getSystemMessage() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(this, IService.MSGPUSH_FORCE, null, new OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                TongZhi info_msg = (TongZhi) JsonUtils.parse((String) result, TongZhi.class);
                if (info_msg.getResult().getCode() == 10000) {
                    if (info_msg.getData().size() != 0) {
                        tzlist.addAll(info_msg.getData());
                        showUpdata();
                    }
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
            }
        });
    }

    /**
     * 展示弹窗
     */
    private void showUpdata() {
        for (int i = 0; i < tzlist.size(); i++) {
            if (tzlist.get(i).getType().equals("1")) {
                backDialog = new BackDialog(tzlist.get(i).getTitle(), tzlist.get(i).getContent(), "确定", LoginActivity.this,
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
     * 初始化
     */
    private void initView() {
        li1.setVisibility(View.GONE);
        li2.setVisibility(View.GONE);
        if (isFinger.equals("YES")) {
            li2.setVisibility(View.VISIBLE);
        } else {
            li1.setVisibility(View.VISIBLE);
        }
        loginTet.setText("欢迎来到" + Clone.APP_NAME);
        delPhoneIvNew.setImageResource(R.drawable.icon_view);
        spConfig = SPConfig.getInstance(getApplicationContext());
        HashMap<String, String> accountInfo = spConfig.getAccountInfo();
        logingTextTel.setText(accountInfo.get(Constant.USERNAME));
        if (spConfig.getKeepPassword()) {
            logingTextPwd.setText(accountInfo.get(Constant.PASSWORD));
        } else {
            logingTextPwd.setText("");
        }
        logingTextPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        loginLogo.setVisibility(View.GONE);
        if (OMID.equals("X9FN9CEDKB0C9A43")) {
            loginLogo.setVisibility(View.VISIBLE);
        }
        if (pref.getString("img_type", "").equals("1")) {
            logingImgSelect.setBackgroundResource(R.drawable.select);
            logingTextPwd.setText("");
            img_type = "1";
        } else {
            logingImgSelect.setBackgroundResource(R.drawable.select_up);
            logingTextPwd.setText(pref.getString("mima", ""));
            img_type = "0";
        }
    }

    private void startFinger() {
        mFingerprintIdentify.startIdentify(MAX_AVAILABLE_TIMES, new BaseFingerprint.FingerprintIdentifyListener() {
            @Override
            public void onSucceed() {
                if (TextUtils.isEmpty(accessToken_xin)) {
                    initToken(0);
                } else {
                    login(pref.getString("zhanghao", ""), pref.getString("mima", ""), "");
                }
                fingerDialog.dismiss();
            }

            @Override
            public void onNotMatch(int availableTimes) {
                if (fingerDialog != null) {
                    fingerDialog.dismiss();
                }
                if (fingerDialog1 != null) {
                    fingerDialog1.dismiss();
                }
                if (fingerDialog2 != null) {
                    fingerDialog2.dismiss();
                }
                if (fingerDialog3 != null) {
                    fingerDialog3.dismiss();
                }
                fingerDialog1 = new FingerDialog("再试一次,还有" + availableTimes + "次机会",
                        LoginActivity.this, R.style.Theme_Dialog_Scale, new FingerDialog.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.textView1:
                                fingerDialog1.dismiss();
                                if (mFingerprintIdentify != null) {
                                    mFingerprintIdentify.cancelIdentify();
                                }
                                editor = pref.edit();
                                editor.putString("isFinger", "NO");
                                editor.putString("patternPwd", ""); //验证手势密码
                                editor.putString("isPwd", "NO");
                                editor.apply();
                                li1.setVisibility(View.VISIBLE);
                                li2.setVisibility(View.GONE);
                                break;
                            case R.id.textView2:
                                fingerDialog1.dismiss();
                                if (mFingerprintIdentify != null) {
                                    mFingerprintIdentify.cancelIdentify();
                                }
                                break;
                        }

                    }
                });
                fingerDialog1.setCancelable(false);
                fingerDialog1.show();
            }

            @Override
            public void onFailed(boolean isDeviceLocked) {
                if (fingerDialog != null) {
                    fingerDialog.dismiss();
                }
                if (fingerDialog1 != null) {
                    fingerDialog1.dismiss();
                }
                if (fingerDialog2 != null) {
                    fingerDialog2.dismiss();
                }
                if (fingerDialog3 != null) {
                    fingerDialog3.dismiss();
                }
                fingerDialog2 = new FingerDialog("错误次数过多,请验证登录密码",
                        LoginActivity.this, R.style.Theme_Dialog_Scale, new FingerDialog.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.textView1:
                                fingerDialog2.dismiss();
                                if (mFingerprintIdentify != null) {
                                    mFingerprintIdentify.cancelIdentify();
                                }
                                editor = pref.edit();
                                editor.putString("isFinger", "NO");
                                editor.putString("patternPwd", ""); //验证手势密码
                                editor.putString("isPwd", "NO");
                                editor.apply();
                                li1.setVisibility(View.VISIBLE);
                                li2.setVisibility(View.GONE);
                                break;
                            case R.id.textView2:
                                fingerDialog2.dismiss();
                                if (mFingerprintIdentify != null) {
                                    mFingerprintIdentify.cancelIdentify();
                                }
                                break;
                        }

                    }
                });
                fingerDialog2.setCancelable(false);
                fingerDialog2.show();
            }

            @Override
            public void onStartFailedByDeviceLocked() {
                if (fingerDialog != null) {
                    fingerDialog.dismiss();
                }
                if (fingerDialog1 != null) {
                    fingerDialog1.dismiss();
                }
                if (fingerDialog2 != null) {
                    fingerDialog2.dismiss();
                }
                if (fingerDialog3 != null) {
                    fingerDialog3.dismiss();
                }
                fingerDialog3 = new FingerDialog("错误次数过多,请验证登录密码",
                        LoginActivity.this, R.style.Theme_Dialog_Scale, new FingerDialog.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.textView1:
                                fingerDialog3.dismiss();
                                if (mFingerprintIdentify != null) {
                                    mFingerprintIdentify.cancelIdentify();
                                }
                                editor = pref.edit();
                                editor.putString("isFinger", "NO");
                                editor.putString("patternPwd", ""); //验证手势密码
                                editor.putString("isPwd", "NO");
                                editor.apply();
                                li1.setVisibility(View.VISIBLE);
                                li2.setVisibility(View.GONE);
                                break;
                            case R.id.textView2:
                                fingerDialog3.dismiss();
                                if (mFingerprintIdentify != null) {
                                    mFingerprintIdentify.cancelIdentify();
                                }
                                break;
                        }

                    }
                });
                fingerDialog3.setCancelable(false);
                fingerDialog3.show();
            }
        });

    }

    @OnClick({R.id.zhiwen_login, R.id.mima_login, R.id.login_btn_ok, R.id.loging_text_forget,
            R.id.keep_password, R.id.del_phone_iv_new, R.id.loging_img_select, R.id.textView1, R.id.textView2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zhiwen_login:
                if (fingerDialog != null) {
                    fingerDialog.dismiss();
                }
                if (fingerDialog1 != null) {
                    fingerDialog1.dismiss();
                }
                if (fingerDialog2 != null) {
                    fingerDialog2.dismiss();
                }
                if (fingerDialog3 != null) {
                    fingerDialog3.dismiss();
                }
                fingerDialog = new FingerDialog("请验证指纹",
                        LoginActivity.this, R.style.Theme_Dialog_Scale, new FingerDialog.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.textView1:
                                fingerDialog.dismiss();
                                if (mFingerprintIdentify != null) {
                                    mFingerprintIdentify.cancelIdentify();
                                }
                                editor = pref.edit();
                                editor.putString("isFinger", "NO");
                                editor.putString("patternPwd", ""); //验证手势密码
                                editor.putString("isPwd", "NO");
                                editor.apply();
                                li1.setVisibility(View.VISIBLE);
                                li2.setVisibility(View.GONE);
                                break;
                            case R.id.textView2:
                                fingerDialog.dismiss();
                                if (mFingerprintIdentify != null) {
                                    mFingerprintIdentify.cancelIdentify();
                                }
                                break;
                        }
                    }
                });
                fingerDialog.setCancelable(false);
                fingerDialog.show();
                startFinger();
                break;
            case R.id.mima_login:
                editor = pref.edit();
                editor.putString("patternPwd", "");
                editor.putString("isPwd", "NO");
                editor.putString("isFinger", "NO");
                editor.apply();
                li2.setVisibility(View.GONE);
                li1.setVisibility(View.VISIBLE);
                break;
            case R.id.login_btn_ok:
                if (TextUtils.isEmpty(accessToken_xin)) {
                    initToken(0);
                } else {
                    login(logingTextTel.getText().toString(), logingTextPwd.getText().toString(), "");
                }
                break;
            case R.id.loging_text_forget:
                if (TextUtils.isEmpty(accessToken_xin)) {
                    initToken(1);
                } else {
                    toRegisterActivity(1);
                }
                break;
            case R.id.keep_password:
                break;
            case R.id.del_phone_iv_new:
                showPassword = (!showPassword);
                if (!showPassword) {
                    logingTextPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    delPhoneIvNew.setImageResource(R.drawable.icon_view);
                } else {
                    logingTextPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    delPhoneIvNew.setImageResource(R.drawable.icon_view_liang);
                }
                break;
            case R.id.loging_img_select:
                if (img_type.equals("1")) {
                    logingImgSelect.setBackgroundResource(R.drawable.select_up);
                    img_type = "0";
                } else {
                    logingImgSelect.setBackgroundResource(R.drawable.select);
                    img_type = "1";
                }
                break;
            case R.id.textView1:
                break;
            case R.id.textView2:
                break;
        }
    }

    private void toRegisterActivity(int i) {
        Intent intent = new Intent(mContext, RegisterActivity_x.class);
        intent.putExtra("register", i);
        startActivity(intent);
    }

    /**
     * 登录
     */
    private void login(final String username, final String password, String code) {
        /**
         * 判断登陆条件 账号与密码是否为空
         */
        if (loginJudge(username, password)) {
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
//            if (bpLocation != null) {
//                if (bpLocation.getErrorCode() == 0) {
//                    registerInfo.setIs_openposition(String.valueOf(1));
//                    registerInfo.setLatitude(String.format("%.2f", Double.valueOf(bpLocation.getLatitude())));
//                    registerInfo.setLongitude(String.format("%.2f", Double.valueOf(bpLocation.getLongitude())));
//                    registerInfo.setCurrentCity(bpLocation.getCity());
//                    registerInfo.setCurrentProvinces(bpLocation.getProvince());
//                } else {
            registerInfo.setIs_openposition(String.valueOf(2));
            registerInfo.setLatitude("");
            registerInfo.setLongitude("");
            registerInfo.setCurrentCity("");
            registerInfo.setCurrentProvinces("");
//                }
//            }
            RequestParam param = new RequestParam(IService.LOGIN, registerInfo, this, Constant.LOGIN);

            Connect.getInstance().httpUtil(param, new OnResponseListener() {
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
                    } else if (code == 103) {
                        /**
                         * 异地登录
                         */
                        if (getCodeDialog == null) {
                            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_get_code, null);
                            initGetCodeDialog(view, "");
                            showGetCodeDialog(view);
                        } else {
                            etCode.setText("");
                            getCodeDialog.show();
                        }
                    } else if (code == 104) {
                        /**
                         * 不同设备登录
                         */
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
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo(final String username, final String password) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(this, IService.USER_INFO, null, new OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                NewUserResponse newresponse = (NewUserResponse) JsonUtils.parse((String) result, NewUserResponse.class);
                if (newresponse.getResult().getCode() == 10000) {
                    NewUserResponse.DataBean data = newresponse.getData();
                    uid = data.getUid();
                    mobile = data.getMobile();
                    editor = pref.edit();
                    sp.putString("new_user_id", user_id);
                    editor.putString(
                            "u_id", data.getUid());
                    editor.putString("zhanghao", username);
                    editor.putString("mima", password);
                    /**
                     * 0-记住   1-没记住
                     */
                    editor.putString("img_type", img_type);
                    editor.apply();
                    user_id = data.getUid();
                    sp.putString("idcard_name", data.getName());
                    sp.putString("idcard_number", data.getIdcard());
                    /**
                     * 推绑定别名
                     */
                    PushManager.getInstance().bindAlias(LoginActivity.this, username + "1");
                    sp.putString("SFZ_NAME", data.getName());
                    spConfig.setAccountInfo(username, password);
                    spConfig.saveUserAllInfo(data);

                    intent = new Intent(mContext, SecondActivity.class);
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

    /**
     * 短信验证码弹窗
     *
     * @param view
     */
    private void showGetCodeDialog(View view) {
        getCodeDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        getCodeDialog.setContentView(view, lp);
        getCodeDialog.setCancelable(false);
        getCodeDialog.setCanceledOnTouchOutside(false);
        getCodeDialog.show();
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", logingTextTel.getText().toString());
        params.put("type", "6");
        Connect.getInstance().post(this, IService.AUTHCODE, params, new OnResponseListener() {
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

            }
        });
    }

    /**
     * 开始倒计时
     */
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

    /**
     * 弹窗操作
     *
     * @param view
     */
    private void initGetCodeDialog(View view, String mobile) {
        etCode = view.findViewById(R.id.et_get_code);
        tvGetCode = view.findViewById(R.id.tv_get_code);
        text_title_code = view.findViewById(R.id.text_title_code);
        text_title_code.setText("设备验证,已发送至" + mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length()));
        TextView tvConfirm = view.findViewById(R.id.tv_get_code_confirm);
        LinearLayout close = view.findViewById(R.id.close);
        tvGetCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tvGetCode.setEnabled(false);
                getCode();
            }
        });
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getCodeDialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etCode.getText().toString()) || etCode.getText().toString().length() < 4) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入有效的验证码");
                    return;
                }
                getCodeDialog.dismiss();
                login(logingTextTel.getText().toString(), logingTextPwd.getText().toString(), etCode.getText().toString());
            }
        });
    }

    /**
     * 对登录条件进行判断
     */
    private boolean loginJudge(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            ToastUtil.showTextToas(getApplicationContext(), getResources().getString(R.string.account_null));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showTextToas(getApplicationContext(), getResources().getString(R.string.password_null));
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getCodeDialog != null && getCodeDialog.isShowing()) {
                getCodeDialog.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFingerprintIdentify != null) {
            mFingerprintIdentify.cancelIdentify();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFingerprintIdentify != null) {
            mFingerprintIdentify.resumeIdentify();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFingerprintIdentify != null) {
            mFingerprintIdentify.cancelIdentify();
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
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

    /**
     * 以license文件方式初始化
     */
    private void initAccessToken() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                editor = pref.edit();
                editor.putString("ocr_token", token);
                editor.apply();
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.d("结果licence方式获取token失败", error.getMessage());
            }
        }, getApplicationContext());
    }


}