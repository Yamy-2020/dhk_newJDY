package com.kym.ui.activity.shiming;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.paradigm.botkit.BotKitClient;
import com.paradigm.botlib.VisitorInfo;
import com.kym.ui.CXKDialog;
import com.kym.ui.R;
import com.kym.ui.SearchActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.fee_kf.ChatActivity;
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
import com.kym.ui.newutil.DragFloatActionButton;
import com.kym.ui.newutil.SharedPreferencesHelper;
import com.kym.ui.newutil.StringList;
import com.kym.ui.newutilutil.CityDialog;
import com.kym.ui.newutilutil.CityDialog.ClickListener;
import com.kym.ui.newutilutil.CityDialog.ItemClickListener;
import com.kym.ui.util.Connect;
import com.kym.ui.util.Connect.OnResponseListener;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.LoginActivity.accessToken_xin;
import static com.kym.ui.activity.LoginActivity.user_id;
import static com.zzss.jindy.appconfig.Clone.OMID;

/**
 * 新实名认证
 *
 * @author sun
 * @date 2019/12/3
 */

public class NewShiMingActivity extends BaseActivity implements OnClickListener {
    private ArrayList<StringList> pro_list = new ArrayList<>();
    private ArrayList<StringList> bank_list = new ArrayList<>();
    private ArrayList<StringList> city_list = new ArrayList<>();
    private EditText et_yhkh, editText_name, editText_cardId;
    private TextView et_sf, et_cs, edit_yh, edit_shouji, et_zh, btn_next;
    private LinearLayout khh_zh;
    private CityDialog CityDialog;
    private int flag = 0;
    private String pro = "", cit = "", bank_code = "";
    private SPConfig spConfig;
    private CXKDialog showDialog;
    private SharedPreferencesHelper sp;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private DragFloatActionButton circle_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shiming);
        spConfig = SPConfig.getInstance(getApplicationContext());
        sp = new SharedPreferencesHelper(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        initHead();
        initUi();
        initData();
        getshengfen();
    }

    private void initUi() {
        LinearLayout btn_bank = (LinearLayout) findViewById(R.id.btn_banks);
        LinearLayout btn_sf = (LinearLayout) findViewById(R.id.btn_sf);
        LinearLayout btn_cs = (LinearLayout) findViewById(R.id.btn_cs);
        btn_bank.setOnClickListener(this);
        btn_sf.setOnClickListener(this);
        btn_cs.setOnClickListener(this);

        khh_zh = (LinearLayout) findViewById(R.id.khh_zhs);
        editText_cardId = (EditText) findViewById(R.id.editText_cardId);
        editText_name = (EditText) findViewById(R.id.editText_name);
        btn_next = (TextView) findViewById(R.id.rela_4);
        et_yhkh = (EditText) findViewById(R.id.editText1);
        et_sf = (TextView) findViewById(R.id.editText2);
        et_zh = (TextView) findViewById(R.id.editText3);
        et_cs = (TextView) findViewById(R.id.editText4);
        edit_yh = (TextView) findViewById(R.id.edit_yh);
        edit_shouji = (TextView) findViewById(R.id.edit_shouji);

        btn_next.setVisibility(View.VISIBLE);
        et_yhkh.setEnabled(true);
        btn_bank.setEnabled(true);
        btn_sf.setEnabled(true);
        btn_cs.setEnabled(true);
        khh_zh.setEnabled(true);
        edit_shouji.setEnabled(true);
        circle_button = (DragFloatActionButton) findViewById(R.id.circle_button);
        circle_button.setOnClickListener(this);
        if (OMID.equals("E1TDVFFY8JX3RY62")) {
            circle_button.setVisibility(View.GONE);
        }
    }

    private void getchengshi(String shenfen) {
        final DialogUtil loadDialogUti = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("province", shenfen);
        Connect.getInstance().post(NewShiMingActivity.this, IService.GET_BANKCITY, params, new OnResponseListener() {
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

    private void getdata2() {
        final DialogUtil loadDialogUti = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("name", editText_name.getText().toString().trim());
        params.put("idcard", editText_cardId.getText().toString().trim());
        params.put("bank_no", et_yhkh.getText().toString().trim());
        params.put("provincename", et_sf.getText().toString().trim());
        params.put("cityname", et_cs.getText().toString().trim());
        params.put("bank_name", edit_yh.getText().toString().trim());
        params.put("bank_sub", et_zh.getText().toString().trim());
        params.put("bank_mobile", edit_shouji.getText().toString().trim());
        params.put("bank_code", bank_code);
        Connect.getInstance().post(NewShiMingActivity.this, IService.NEW_SHIMING, params, new OnResponseListener() {
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
        Connect.getInstance().post(this, IService.USER_INFO, null, new OnResponseListener() {
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
                    PushManager.getInstance().bindAlias(NewShiMingActivity.this, username + "1");
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

    private void initHead() {
        TextView title = (TextView) findViewById(R.id.head_text_title);
        title.setText("实名认证");
        findViewById(R.id.head_img_left).setOnClickListener(this);
    }

    private void initData() {
        khh_zh.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
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
            case R.id.rela_4:
                String yhkh = et_yhkh.getText().toString();
                String sf = et_sf.getText().toString();
                String zh = et_cs.getText().toString();
                if (editText_name.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入真实姓名");
                } else if (editText_cardId.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入本人身份证号码");
                } else if (yhkh.equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "银行卡号格式不正确");
                } else if (sf.equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "开户行省市不可为空");
                } else if (zh.equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "开户行支行不可为空");
                } else {
                    showDialog = new CXKDialog(this, R.style.Theme_Dialog_Scale, new CXKDialog.ExitDialogListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.textView4:
                                    getdata2();
                                    showDialog.dismiss();
                                    break;
                                case R.id.textView5:
                                    showDialog.dismiss();
                                    break;
                            }
                        }
                    });
                    showDialog.show();
                }

                break;
            case R.id.khh_zhs:
                if (et_yhkh.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "银行卡号格式不正确");
                } else if (et_sf.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "银行省份不可为空");
                } else if (et_cs.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "开户行省市不可为空");
                    return;
                } else if (edit_yh.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "开户行银行不可为空");
                    return;
                } else {
                    Intent search_intent = new Intent(NewShiMingActivity.this, SearchActivity.class);
                    search_intent.putExtra("province", et_sf.getText().toString().trim());
                    search_intent.putExtra("city", et_cs.getText().toString().trim());
                    search_intent.putExtra("bank_name", edit_yh.getText().toString().trim());
                    startActivityForResult(search_intent, 0);
                }
                break;
            case R.id.btn_banks:
                if (et_yhkh.getText().toString().equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "银行卡号格式不正确");
                } else if (pro.equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请先选择省份");
                } else if (cit.equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请先选择城市");
                } else {
                    flag = 3;
                    AddProvince("银行", bank_list);
                }
                break;
            case R.id.btn_sf:
                flag = 1;
                AddProvince("省份", pro_list);
                break;
            case R.id.btn_cs:
                if (pro.equals("")) {
                    ToastUtil.showTextToas(getApplicationContext(), "请先选择省份");
                } else {
                    flag = 2;
                    AddProvince("城市", city_list);
                }
                break;
            default:
                break;
        }
    }

    private void getbanlist(String province, String city) {
        final DialogUtil loadDialogUti = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("province", province);
        params.put("city", city);
        Connect.getInstance().post(NewShiMingActivity.this, IService.GET_SETBANKNAME, params, new OnResponseListener() {
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

    private void AddProvince(String title, ArrayList<StringList> s_list) {
        CityDialog = new CityDialog(s_list, title, NewShiMingActivity.this, R.style.Theme_Dialog_Scale,
                new ClickListener() {

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

                }, new ItemClickListener() {

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
                StringList stringList4 = pro_list.get(position);
                pro = stringList4.getS();
                et_sf.setText(pro);
                et_cs.setText("");
                edit_yh.setText("");
                city_list.clear();
                getchengshi(pro);
                break;
            case 2:
                StringList stringList = city_list.get(position);
                cit = stringList.getS();
                et_cs.setText(cit);
                edit_yh.setText("");
                getbanlist(pro, cit);
                break;
            case 3:
                StringList bank_name = bank_list.get(position);
                String bsub = bank_name.getS();
                edit_yh.setText(bsub);
                break;
            default:
                break;

        }
        CityDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                String stringExtra = data.getStringExtra("ZHIHANG");
                bank_code = data.getStringExtra("ZHIHANGCODE");
                et_zh.setText(stringExtra);
                break;
            default:
                break;
        }
    }

    /**
     * 获取实名省份
     */
    private void getshengfen() {
        final DialogUtil loadDialogUti = new DialogUtil(this);
        Connect.getInstance().post(NewShiMingActivity.this, IService.GET_BANKPROVINCE, null, new OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                loadDialogUti.dismiss();
                ShengFen response = (ShengFen) JsonUtils.parse((String) result, ShengFen.class);
                if (response.getResult().getCode() == 10000) {
                    List<ShengFen.DataBean> shenfenlist = response.getData();
                    if (shenfenlist != null) {
                        for (int i = 0; i < shenfenlist.size(); i++) {
                            String provinceName = shenfenlist.get(i).getProvince();
                            pro_list.add(new StringList(provinceName));
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
}
