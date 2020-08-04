package com.kym.ui.activity.bpbro_real_name;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.igexin.sdk.PushManager;
import com.kym.ui.BackDialog3;
import com.kym.ui.R;
import com.kym.ui.SearchActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.bpbro_untils.bank_ocr.FileUtil;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.LoginInfo;
import com.kym.ui.info.RegisterInfo;
import com.kym.ui.info.RequestParam;
import com.kym.ui.model.BankList;
import com.kym.ui.model.ChengShi;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.model.Result;
import com.kym.ui.model.ShengFen;
import com.kym.ui.newutil.SharedPreferencesHelper;
import com.kym.ui.newutil.StringList;
import com.kym.ui.newutilutil.CityDialog;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kym.ui.activity.LoginActivity.accessToken_xin;
import static com.kym.ui.activity.LoginActivity.user_id;

/**
 * 实名认证
 *
 * @author sun
 * @date 2020/1/6
 */

public class Bpbro_Idcardid_Activity extends BaseActivity {

    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_BANKCARD = 111;

    private ArrayList<StringList> province_list = new ArrayList<>();
    private ArrayList<StringList> bank_list = new ArrayList<>();
    private ArrayList<StringList> city_list = new ArrayList<>();
    private SharedPreferencesHelper sp;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String pro = "", cit = "", bank_code = "";
    private CityDialog CityDialog;
    private BackDialog3 dialog3;
    private int flag = 0;
    private AMapLocation bpLocation;
    private Intent intent;

    @BindView(R.id.head_text_title)
    TextView headTextTitle;
    @BindView(R.id.head_img_left)
    ImageView headImgLeft;
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.name_photo)
    TextView namePhoto;
    @BindView(R.id.id_card)
    EditText idCard;
    @BindView(R.id.bank_no)
    EditText bankNo;
    @BindView(R.id.bank_photo)
    TextView bankPhoto;
    @BindView(R.id.bank_province)
    TextView bankProvince;
    @BindView(R.id.bank_province_btn)
    LinearLayout bankProvinceBtn;
    @BindView(R.id.bank_city)
    TextView bankCity;
    @BindView(R.id.bank_city_btn)
    LinearLayout bankCityBtn;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.bank_name_btn)
    LinearLayout bankNameBtn;
    @BindView(R.id.bank_zh)
    TextView bankZh;
    @BindView(R.id.bank_zh_btn)
    LinearLayout bankZhBtn;
    @BindView(R.id.mobile_no)
    EditText mobileNo;
    @BindView(R.id.submit)
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpbro_idcardid);
        ButterKnife.bind(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        sp = new SharedPreferencesHelper(this);
        initView();
        getBankProvince();
    }

    private void initView() {
        headTextTitle.setText("实名认证");
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
        //  初始化本地质量控制模型,释放代码在onDestory中
//          调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
        CameraNativeHelper.init(this, OCR.getInstance(this).getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
                        Log.d("结果本地质量控制初始化错误，错误原因： ", msg);
                    }
                });
    }

    @OnClick({R.id.head_img_left, R.id.name_photo, R.id.bank_photo, R.id.bank_province_btn, R.id.bank_city_btn, R.id.bank_name_btn, R.id.bank_zh_btn, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.name_photo:
                intent = new Intent(Bpbro_Idcardid_Activity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
                // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
                // 请手动使用CameraNativeHelper初始化和释放模型
                // 推荐这样做，可以避免一些activity切换导致的不必要的异常
                intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                break;
            case R.id.bank_photo:
                intent = new Intent(Bpbro_Idcardid_Activity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, REQUEST_CODE_BANKCARD);
                break;
            case R.id.bank_province_btn:
                if (bankNo.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入储蓄卡号");
                } else {
                    flag = 1;
                    AddProvince("省份", province_list);
                }
                break;
            case R.id.bank_city_btn:
                if (pro.equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请先选择开户行所在省份");
                } else {
                    flag = 2;
                    AddProvince("城市", city_list);
                }
                break;
            case R.id.bank_name_btn:
                if (cit.equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择开户行所在城市");
                } else {
                    flag = 3;
                    AddProvince("银行", bank_list);
                }
                break;
            case R.id.bank_zh_btn:
                if (bankName.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择开户行所在城市");
                } else {
                    Intent search_intent = new Intent(Bpbro_Idcardid_Activity.this, SearchActivity.class);
                    search_intent.putExtra("province", bankProvince.getText().toString().replace(" ", ""));
                    search_intent.putExtra("city", bankCity.getText().toString().replace(" ", ""));
                    search_intent.putExtra("bank_name", bankName.getText().toString().replace(" ", ""));
                    startActivityForResult(search_intent, 0);
                }
                break;
            case R.id.submit:
                if (userName.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入真实姓名");
                } else if (idCard.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入本人身份证号码");
                } else if (bankNo.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入储蓄卡号");
                } else if (mobileNo.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入银行预留手机号");
                } else {
                    dialog3 = new BackDialog3("确定", "取消", "温馨提示", "请确认信息无误！",
                            Bpbro_Idcardid_Activity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {

                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.textView1:
                                    dialog3.dismiss();
                                    submitBnaks();
                                    break;
                                case R.id.textView2:
                                    dialog3.dismiss();
                                    break;
                            }

                        }
                    });
                    dialog3.setCancelable(false);
                    dialog3.show();
                }
                break;
        }
    }

    /**
     * 获取银行所在省份
     */
    private void getBankProvince() {
        final DialogUtil loadDialogUti = new DialogUtil(this);
        Connect.getInstance().post(this, IService.GET_BANKPROVINCE, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                loadDialogUti.dismiss();
                ShengFen response = (ShengFen) JsonUtils.parse((String) result, ShengFen.class);
                if (response.getResult().getCode() == 10000) {
                    List<ShengFen.DataBean> bank_province_list = response.getData();
                    if (bank_province_list != null) {
                        for (int i = 0; i < bank_province_list.size(); i++) {
                            String provinceName = bank_province_list.get(i).getProvince();
                            province_list.add(new StringList(provinceName));
                        }
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                loadDialogUti.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 获取银行所在城市
     */
    private void getBankCity(String bank_pro) {
        final DialogUtil loadDialogUti = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("province", bank_pro);
        Connect.getInstance().post(this, IService.GET_BANKCITY, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                loadDialogUti.dismiss();
                ChengShi response = (ChengShi) JsonUtils.parse((String) result, ChengShi.class);
                if (response.getResult().getCode() == 10000) {
                    List<ChengShi.DataBean> shiqulist = response.getData();
                    if (shiqulist != null) {
                        for (int i = 0; i < shiqulist.size(); i++) {
                            city_list.add(new StringList(shiqulist.get(i).getCity()));
                        }
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                loadDialogUti.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 储蓄卡列表
     *
     * @param province
     * @param city
     */
    private void getBankList(String province, String city) {
        final DialogUtil loadDialogUti = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("province", province);
        params.put("city", city);
        Connect.getInstance().post(this, IService.GET_SETBANKNAME, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                loadDialogUti.dismiss();
                BankList response = (BankList) JsonUtils.parse((String) result, BankList.class);
                if (response.getResult().getCode() == 10000) {
                    List<BankList.DataBean> bank_data_list = response.getData();
                    if (bank_data_list != null) {

                        for (int i = 0; i < bank_data_list.size(); i++) {
                            bank_list.add(new StringList(bank_data_list.get(i).getName()));
                        }
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                loadDialogUti.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    private void submitBnaks() {
        final DialogUtil loadDialogUti = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("name", userName.getText().toString().replace(" ", ""));
        params.put("idcard", idCard.getText().toString().replace(" ", ""));
        params.put("bank_no", bankNo.getText().toString().replace(" ", ""));
        params.put("provincename", bankProvince.getText().toString().replace(" ", ""));
        params.put("cityname", bankCity.getText().toString().replace(" ", ""));
        params.put("bank_name", bankName.getText().toString().replace(" ", ""));
        params.put("bank_sub", bankZh.getText().toString().replace(" ", ""));
        params.put("bank_mobile", mobileNo.getText().toString().replace(" ", ""));
        params.put("bank_code", bank_code);
        Connect.getInstance().post(this, IService.NEW_SHIMING, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                loadDialogUti.dismiss();
                Result response = (Result) JsonUtils.parse((String) result, Result.class);
                if (response.getResult().getCode() == 10000) {
                    String zhanghao = pref.getString("zhanghao", "");
                    String mima = pref.getString("mima", "");
                    login(zhanghao, mima);
                    ToastUtil.showTextToas(getApplicationContext(), "实名认证成功");
                    finish();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                loadDialogUti.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    private void AddProvince(String title, ArrayList<StringList> s_list) {
        CityDialog = new CityDialog(s_list, title, Bpbro_Idcardid_Activity.this, R.style.Theme_Dialog_Scale,
                new CityDialog.ClickListener() {

                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.btn_cancel:
                                CityDialog.dismiss();
                                break;

                            default:
                                break;
                        }
                    }

                }, new CityDialog.ItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()) {
                    case R.id.listView1:
                        Switchs(position);
                        break;

                    default:
                        break;
                }
            }
        });
        CityDialog.show();
    }

    private void Switchs(int position) {
        switch (flag) {
            case 1:
                StringList stringList4 = province_list.get(position);
                pro = stringList4.getS();
                bankProvince.setText(pro);
                bankCity.setText("");
                city_list.clear();
                getBankCity(pro);
                break;
            case 2:
                StringList stringList = city_list.get(position);
                cit = stringList.getS();
                bankCity.setText(cit);
                getBankList(pro, cit);
                break;
            case 3:
                StringList bank_name = bank_list.get(position);
                String bsub = bank_name.getS();
                bankName.setText(bsub);
                break;
            default:
                break;

        }
        CityDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release();
        super.onDestroy();
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    Log.d("结果", result.toString());
                    userName.setText(result.getName().toString());
                    idCard.setText(result.getIdNumber().toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                ToastUtil.showTextToas(getApplicationContext(), error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                String stringExtra = data.getStringExtra("ZHIHANG");
                bank_code = data.getStringExtra("ZHIHANGCODE");
                bankZh.setText(stringExtra);
                break;
            default:
                break;
        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }

        // 识别成功回调，银行卡识别
        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {
            BankCardParams param = new BankCardParams();
            param.setImageFile(new File(FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath()));
            OCR.getInstance(this).recognizeBankCard(param, new OnResultListener<BankCardResult>() {

                @Override
                public void onResult(BankCardResult bankCardResult) {
                    if (bankCardResult.getBankCardType().toString().equals("Debit")) {
                        bankNo.setText(bankCardResult.getBankCardNumber());
                        bankName.setText(bankCardResult.getBankName());
                    } else if (bankCardResult.getBankCardType().toString().equals("Credit")) {
                        ToastUtil.showTextToas(getApplicationContext(), "请绑定储蓄卡");
                        bankNo.setText("");
                        bankName.setText("");
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), "未识别出你的卡片,请手动输入或更换卡片");
                        bankNo.setText("");
                        bankName.setText("");
                    }
                }

                @Override
                public void onError(OCRError error) {
                    ToastUtil.showTextToas(getApplicationContext(), error.getMessage());
                }
            });
        }
    }

    /**
     * 登录
     *
     * @param username
     * @param password
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
     *
     * @param username
     * @param password
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
                    PushManager.getInstance().bindAlias(Bpbro_Idcardid_Activity.this, username + "1");
                    sp.putString("SFZ_NAME", data.getName());
                    SPConfig.getInstance(getApplicationContext()).setAccountInfo(username, password);
                    SPConfig.getInstance(getApplicationContext()).saveUserAllInfo(data);
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
