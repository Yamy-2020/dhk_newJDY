package com.kym.ui.activity.mendianma;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.RongHuiWebActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kym.ui.appconfig.IService.RESMARK;
import static com.kym.ui.util.Ckeck.isEmail;
import static com.kym.ui.util.Ckeck.isName;
import static com.kym.ui.util.IDCard.IDCardValidate;

public class MenDianActivity extends Activity implements View.OnClickListener {

    private LinearLayout sh_type, sh_sheng, sh_city, sh_xianqu, btn_login;
    private EditText sh_name, sh_detail, sh_credit, fr_name, fr_cardId, lx_name,
            lx_phone, lx_email;
    private TextView sh_typeInfo, sh_shengInfo, sh_xianquInfo, sh_cityInfo, hy_typeInfo, jy_typeInfo;
    private StringBuilder response;
    private final int what = 1;
    private SharedPreferences pref;
    private List<Map<String, String>> lsitData1 = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> lsitData2 = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> lsitData3 = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> lsitData4 = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> lsitData5 = new ArrayList<Map<String, String>>();
    private final static String fileName = "industry";
    private final static String fileName1 = "myarea.json";
    private String hangyeleixing, jingyingleixing;
    private SPConfig spConfig;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men_dian);
        initHead();
        initView();
        String jsonStr = getJson(fileName);
        String jsonStr1 = getJson(fileName1);
        setData(jsonStr);
        setData1(jsonStr1);
        spConfig = SPConfig.getInstance(this);
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        if (getIntent().getStringExtra("type").equals("meimei")) {
            tv.setText("通道签约进件");
        } else if (getIntent().getStringExtra("type").equals("dongzai")) {
            tv.setText("门店二维码申请");
        } else {
            tv.setText("通道签约进件");
        }
    }

    public void initView() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        sh_name = (EditText) findViewById(R.id.sh_name);
        sh_type = (LinearLayout) findViewById(R.id.sh_type);
        sh_sheng = (LinearLayout) findViewById(R.id.sh_sheng);
        sh_city = (LinearLayout) findViewById(R.id.sh_city);
        sh_xianqu = (LinearLayout) findViewById(R.id.sh_xianqu);
        sh_typeInfo = (TextView) findViewById(R.id.sh_typeInfo);
        sh_shengInfo = (TextView) findViewById(R.id.sh_shengInfo);
        sh_cityInfo = (TextView) findViewById(R.id.sh_cityInfo);
        sh_xianquInfo = (TextView) findViewById(R.id.sh_xianquInfo);
        sh_detail = (EditText) findViewById(R.id.sh_detail);
        sh_credit = (EditText) findViewById(R.id.sh_credit);
        fr_name = (EditText) findViewById(R.id.fr_name);
        fr_cardId = (EditText) findViewById(R.id.fr_cardId);
        lx_name = (EditText) findViewById(R.id.lx_name);
        lx_phone = (EditText) findViewById(R.id.lx_phone);
        lx_email = (EditText) findViewById(R.id.lx_email);
        btn_login = (LinearLayout) findViewById(R.id.btn_login);
        sh_type.setOnClickListener(this);
        sh_sheng.setOnClickListener(this);
        sh_city.setOnClickListener(this);
        sh_xianqu.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        findViewById(R.id.xinyong_box).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
//            case R.id.sh_type:
////                ShowLeixing();
//                break;
            case R.id.sh_sheng:
                showSheng();
                break;
            case R.id.sh_city:
                if (sh_shengInfo.getText().toString().trim().equals("")) {
                    tipView("1", "商户所在省份不能为空");
                } else {
                    lsitData4 = new ArrayList<Map<String, String>>();
                    showCity();
                }
                break;
            case R.id.sh_xianqu:
                if (sh_shengInfo.getText().toString().trim().equals("")) {
                    tipView("1", "商户所在省份不能为空");
                } else if (sh_cityInfo.getText().toString().trim().equals("")) {
                    tipView("1", "商户所在省份不能为空");
                } else {
                    lsitData5 = new ArrayList<Map<String, String>>();
                    showXianqu();
                }
                break;
            case R.id.btn_login:
                if (sh_name.getText().toString().trim().equals("")) {
                    tipView("1", "商户名称不能为空");
                } else if (sh_typeInfo.getText().toString().trim().equals("")) {
                    tipView("1", "商户类型不能为空");
                } else if (sh_shengInfo.getText().toString().trim().equals("")) {
                    tipView("1", "商户所在省份不能为空");
                } else if (sh_cityInfo.getText().toString().trim().equals("")) {
                    tipView("1", "商户所在城市不能为空");
                } else if (sh_xianquInfo.getText().toString().trim().equals("")) {
                    tipView("1", "商户所在县区不能为空");
                } else if (sh_detail.getText().toString().trim().equals("")) {
                    tipView("1", "详细地址不能为空");
                } else if (sh_credit.getText().toString().trim().equals("") && !(sh_typeInfo.getText().toString().equals("个人商户"))) {
                    tipView("1", "信用代码不能为空");
                } else if (sh_credit.getText().toString().trim().length() != 18 && !(sh_typeInfo.getText().toString().equals("个人商户"))) {
                    tipView("1", "信用代码格式不正确");
                } else if (fr_name.getText().toString().trim().equals("") && !sh_typeInfo.getText().toString().equals("个人商户")) {
                    tipView("1", "法人姓名不能为空");
                } else if (!isName(fr_name.getText().toString()) && !sh_typeInfo.getText().toString().equals("个人商户")) {
                    tipView("1","法人姓名格式不正确");
                } else if (fr_cardId.getText().toString().trim().equals("") && !sh_typeInfo.getText().toString().equals("个人商户")) {
                    tipView("1","法人身份证号不能为空");
                } else if (!IDCardValidate(fr_cardId.getText().toString()) && !sh_typeInfo.getText().toString().equals("个人商户")) {
                    tipView("1","法人身份证号格式不正确");
                } else if (lx_name.getText().toString().trim().equals("")) {
                    tipView("1","商家联系人姓名不能为空");
                } else if (lx_email.getText().toString().trim().equals("")) {
                    tipView("1","商家联系人邮箱不能为空");
                } else if (!isEmail(lx_email.getText().toString())) {
                    tipView("1","商家联系人邮箱格式不正确");
                } else if (lx_phone.getText().toString().trim().equals("")) {
                    tipView("1","商家联系人电话不能为空");
                } else if (lx_phone.getText().toString().length() != 11) {
                    tipView("1","商家联系人电话格式不正确");
                } else {
                    if (getIntent().getStringExtra("type").equals("dongzai")) {
                        submitInfo();
                    } else if (getIntent().getStringExtra("type").equals("meimei")) {
                        rongHui();
                    } else {
                        rongHuishoukuan();
                    }
                }
                break;
        }
    }

    /**
     * 读取本地文件中JSON字符串
     *
     * @param fileName
     * @return
     */
    private String getJson(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 将JSON字符串转化为Adapter数据
     *
     * @param str
     */
    private void setData(String str) {
        try {
            JSONArray array = new JSONArray(str);
            int len = array.length();
            Map<String, String> map;
            for (int i = 0; i < len; i++) {
                JSONObject object = array.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("name", object.getString("name"));
                map.put("type", object.getString("type"));
                lsitData1.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setData1(String str) {
        try {
            JSONArray array = new JSONArray(str);
            int len = array.length();
            Map<String, String> map;
            for (int i = 0; i < len; i++) {
                JSONObject object = array.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("region", object.getString("region"));
                map.put("regionEntitys", object.getString("regionEntitys"));
                lsitData3.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setData2(String str) {
        try {
            JSONArray array = new JSONArray(str);
            int len = array.length();
            Map<String, String> map;
            for (int i = 0; i < len; i++) {
                JSONObject object = array.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("region", object.getString("region"));
                map.put("regionEntitys", object.getString("regionEntitys"));
                lsitData4.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setData3(String str) {
        try {
            JSONArray array = new JSONArray(str);
            int len = array.length();
            Map<String, String> map;
            for (int i = 0; i < len; i++) {
                JSONObject object = array.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("region", object.getString("region"));
                lsitData5.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //    省份数据
    public void showSheng() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.json_sheng, null);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.listview_sheng);
        TextView kaihu = (TextView) view.findViewById(R.id.kaihu);
        LinearLayout cancel = (LinearLayout) view.findViewById(R.id.cancel);
        kaihu.setText("所在省份");
        for (final Map<String, String> m : lsitData3) {
            TextView tv = new TextView(this);
            tv.setText(m.get("region"));
            final String type = m.get("regionEntitys");
            tv.setTextSize(17);
            tv.setClickable(true);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setHeight(150);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.line));
            ll.addView(tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    sh_shengInfo.setText(m.get("region"));
                    dialog.cancel();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.cancel();
                }
            });
        }
        dialog.setCancelable(false);
        dialog.setView(view);
        dialog.show();
    }

    //    城市数据
    public void showCity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.json_sheng, null);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.listview_sheng);
        TextView kaihu = (TextView) view.findViewById(R.id.kaihu);
        LinearLayout cancel = (LinearLayout) view.findViewById(R.id.cancel);
        kaihu.setText("所在城市");
        for (final Map<String, String> m : lsitData3) {
            final String regionEntitys = m.get("regionEntitys");
            if (m.get("region").equals(sh_shengInfo.getText().toString())) {
                setData2(regionEntitys);
                for (final Map<String, String> s : lsitData4) {
                    TextView tv = new TextView(this);
                    tv.setText(s.get("region"));
                    tv.setTextSize(17);
                    tv.setClickable(true);
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setHeight(150);
                    tv.setGravity(Gravity.CENTER);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.line));
                    ll.addView(tv);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            sh_cityInfo.setText(s.get("region"));
                            dialog.cancel();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            dialog.cancel();
                        }
                    });
                }
            }
        }
        dialog.setCancelable(false);
        dialog.setView(view);
        dialog.show();
    }

    //    县区数据
    public void showXianqu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.json_sheng, null);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.listview_sheng);
        TextView kaihu = (TextView) view.findViewById(R.id.kaihu);
        LinearLayout cancel = (LinearLayout) view.findViewById(R.id.cancel);
        kaihu.setText("所在县区");
        for (final Map<String, String> m : lsitData3) {
            final String regionEntitys = m.get("regionEntitys");
            if (m.get("region").equals(sh_shengInfo.getText().toString())) {
                lsitData4 = new ArrayList<Map<String, String>>();
                setData2(regionEntitys);
                for (final Map<String, String> s : lsitData4) {
                    final String regionEntitys1 = s.get("regionEntitys");
                    if (s.get("region").equals(sh_cityInfo.getText().toString())) {
                        lsitData5 = new ArrayList<Map<String, String>>();
                        setData3(regionEntitys1);
                        for (final Map<String, String> k : lsitData5) {
                            TextView tv = new TextView(this);
                            if (!(k.get("region").equals("市辖区"))) {
                                tv.setText(k.get("region"));
                                tv.setTextSize(17);
                                tv.setClickable(true);
                                tv.setTextColor(getResources().getColor(R.color.black));
                                tv.setHeight(150);
                                tv.setGravity(Gravity.CENTER);
                                tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.line));
                                ll.addView(tv);
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        sh_xianquInfo.setText(k.get("region"));
                                        dialog.cancel();
                                    }
                                });
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        dialog.cancel();
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        dialog.setCancelable(false);
        dialog.setView(view);
        dialog.show();
    }

//    private void ShowLeixing() {
//        //显示对话框
//        AlertDialog.Builder builder = news_img AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
//        final AlertDialog dialog = builder.create();
//        View view = View.inflate(this, R.layout.dialog_leixing, null);
//        LinearLayout cancel = (LinearLayout) view.findViewById(R.id.cancel);
//        LinearLayout leixing1 = (LinearLayout) view.findViewById(R.id.leixing1);
//        LinearLayout leixing2 = (LinearLayout) view.findViewById(R.id.leixing2);
//        LinearLayout leixing3 = (LinearLayout) view.findViewById(R.id.leixing3);
//        LinearLayout leixing4 = (LinearLayout) view.findViewById(R.id.leixing4);
//        cancel.setOnClickListener(news_img View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        leixing1.setOnClickListener(news_img View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sh_typeInfo.setText("企业商户");
//                findViewById(R.id.xinyong_box).setVisibility(View.VISIBLE);
//                dialog.cancel();
//            }
//        });
//        leixing2.setOnClickListener(news_img View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sh_typeInfo.setText("事业单位商户");
//                findViewById(R.id.xinyong_box).setVisibility(View.VISIBLE);
//                dialog.cancel();
//            }
//        });
//        leixing3.setOnClickListener(news_img View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sh_typeInfo.setText("个体工商户");
//                findViewById(R.id.xinyong_box).setVisibility(View.VISIBLE);
//                dialog.cancel();
//            }
//        });
//        leixing4.setOnClickListener(news_img View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sh_typeInfo.setText("个人商户");
//                findViewById(R.id.xinyong_box).setVisibility(View.GONE);
//                dialog.cancel();
//            }
//        });
//        dialog.setCancelable(false);
//        dialog.setView(view);
//        dialog.show();
//    }

    private void ShowJingYing() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.json_sheng, null);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.listview_sheng);
        TextView kaihu = (TextView) view.findViewById(R.id.kaihu);
        LinearLayout cancel = (LinearLayout) view.findViewById(R.id.cancel);
        kaihu.setText("经营类型");
        for (final Map<String, String> m : lsitData1) {
            TextView tv = new TextView(this);
            tv.setText(m.get("name"));
            tv.setTextSize(17);
            tv.setClickable(true);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setHeight(150);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.line));
            ll.addView(tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    jy_typeInfo.setText(m.get("name"));
                    jingyingleixing = m.get("type");
                    dialog.cancel();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.cancel();
                }
            });
        }
        dialog.setCancelable(false);
        dialog.setView(view);
        dialog.show();
    }

    //行业类型
    private void ShowIndustry() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.json_sheng, null);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.listview_sheng);
        TextView kaihu = (TextView) view.findViewById(R.id.kaihu);
        LinearLayout cancel = (LinearLayout) view.findViewById(R.id.cancel);
        kaihu.setText("行业类型");
        for (final Map<String, String> m : lsitData2) {
            TextView tv = new TextView(this);
            tv.setText(m.get("name"));
            tv.setTextSize(17);
            tv.setClickable(true);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setHeight(150);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.line));
            ll.addView(tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    hy_typeInfo.setText(m.get("name"));
                    hangyeleixing = m.get("code");
                    checkJingying();
                    dialog.cancel();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.cancel();
                }
            });
        }
        dialog.setCancelable(false);
        dialog.setView(view);
        dialog.show();
    }

    /**
     * 获取行业类型
     */
    private void getHangYeType() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();

        HashMap<String, String> params = new HashMap<>();
        params.put("resmark", RESMARK);
        Connect.getInstance().post(this.getApplicationContext(), IService.GET_HANGYE_TYPE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                Map<String, String> map;
                try {
                    JSONArray array = new JSONArray(result.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("name"); //商户类型
                        String data = object.getString("data");
                        JSONArray array1 = new JSONArray(data);
                        for (int j = 0; j < array1.length(); j++) {
                            JSONObject object1 = array1.getJSONObject(j);
                            String name1 = object1.getString("name"); //经营类型
                            String data1 = object1.getString("data");
                            JSONArray array2 = new JSONArray(data1);
                            for (int n = 0; n < array2.length(); n++) {
                                JSONObject object2 = array2.getJSONObject(n);
                                final String name2 = object2.getString("name"); //行业类型
                                String code = object2.getString("code");
                                if (name.equals(sh_typeInfo.getText().toString())) {
                                    map = new HashMap<String, String>();
                                    map.put("name", name2);
                                    map.put("code", code);
                                    lsitData2.add(map);
                                }
                            }
                        }
                    }
                    ShowIndustry();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1",message);
            }
        });
    }

    /**
     * 融汇进件
     */
    private void rongHui() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
//        String shanghuleixing = null;
//        if (sh_typeInfo.getText().toString().equals("企业商户")) {
//            shanghuleixing = "ENTERPRISE";
//        } else if (sh_typeInfo.getText().toString().equals("事业单位商户")) {
//            shanghuleixing = "INSTITUTION";
//        } else if (sh_typeInfo.getText().toString().equals("个体工商户")) {
//            shanghuleixing = "INDIVIDUALBISS";
//        } else if (sh_typeInfo.getText().toString().equals("个人商户")) {
//            shanghuleixing = "PERSON";
//        }
        HashMap<String, String> params = new HashMap<>();
        params.put("resmark", RESMARK);
        params.put("merchantname", sh_name.getText().toString());
        params.put("merchanttype", "PERSON");
        params.put("installprovince", sh_shengInfo.getText().toString());
        params.put("installcity", sh_cityInfo.getText().toString());
        params.put("installcounty", sh_xianquInfo.getText().toString());
        params.put("operateaddress", sh_detail.getText().toString());
        params.put("uid", spConfig.getUserAllInfoNew().getUid());
        params.put("merchantpersonname", lx_name.getText().toString());
        params.put("merchantpersonemail", lx_email.getText().toString());
        params.put("merchantpersonphone", lx_phone.getText().toString());
//        if (!sh_typeInfo.getText().toString().equals("个人商户")) {
//            params.put("businesslicense", sh_credit.getText().toString());
//            params.put("legalpersonname", fr_name.getText().toString());
//            params.put("legalpersonid", fr_cardId.getText().toString());
//        }
        Connect.getInstance().post(this.getApplicationContext(), IService.SUBMIT_INFO, params, new Connect.OnResponseListener() {
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
                        String statusName = obj2.get("statusName").toString();
                        String statusMsg = obj2.get("statusMsg").toString();
                        if (statusName.equals("input_sellermsg")) {
                            tipView("1",statusMsg);
                        } else if (statusName.equals("input_money")) {
                            bangKa();
                            finish();
                        } else {
                            tipView("1",statusMsg);
                        }
                    } else {
                        tipView("1",msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1",message);
            }
        });
    }

    /**
     * 融汇进件
     */
    private void rongHuishoukuan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
//        String shanghuleixing = null;
//        if (sh_typeInfo.getText().toString().equals("企业商户")) {
//            shanghuleixing = "PERSON";
//        } else if (sh_typeInfo.getText().toString().equals("事业单位商户")) {
//            shanghuleixing = "PERSON";
//        } else if (sh_typeInfo.getText().toString().equals("个体工商户")) {
//            shanghuleixing = "PERSON";
//        } else if (sh_typeInfo.getText().toString().equals("个人商户")) {
//            shanghuleixing = "PERSON";
//        }
        HashMap<String, String> params = new HashMap<>();
        params.put("resmark", RESMARK);
        params.put("merchantname", sh_name.getText().toString());
        params.put("merchanttype", "PERSON");
        params.put("installprovince", sh_shengInfo.getText().toString());
        params.put("installcity", sh_cityInfo.getText().toString());
        params.put("installcounty", sh_xianquInfo.getText().toString());
        params.put("operateaddress", sh_detail.getText().toString());
        params.put("uid", spConfig.getUserAllInfoNew().getUid());
        params.put("merchantpersonname", lx_name.getText().toString());
        params.put("merchantpersonemail", lx_email.getText().toString());
        params.put("merchantpersonphone", lx_phone.getText().toString());
//        if (!sh_typeInfo.getText().toString().equals("个人商户")) {
//            params.put("businesslicense", sh_credit.getText().toString());
//            params.put("legalpersonname", fr_name.getText().toString());
//            params.put("legalpersonid", fr_cardId.getText().toString());
//        }
        Connect.getInstance().post(this.getApplicationContext(), IService.SUBMIT_INFO, params, new Connect.OnResponseListener() {
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
                        String statusName = obj2.get("statusName").toString();
                        String statusMsg = obj2.get("statusMsg").toString();
                        if (statusName.equals("input_sellermsg")) {
                            tipView("1",statusMsg);
                        } else if (statusName.equals("input_money")) {
                            RH_pay();
                            finish();
                        } else {
                            tipView("1",statusMsg);
                        }
                    } else {
                        tipView("1",msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1",message);
            }
        });
    }

    /**
     * 融汇绑卡
     */
    private void RH_pay() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("cardid"));
        params.put("money", getIntent().getStringExtra("money"));
        params.put("id", getIntent().getStringExtra("ID"));
        Connect.getInstance().post(getApplicationContext(), IService.RH_PAY, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                // 0-已签约  1-进件  2-绑卡
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
                        String data2 = obj2.getString("data");
                        JSONObject obj3 = new JSONObject(data2);
//                        Intent intent = news_img Intent(getApplicationContext(), RongHuiWebActivity.class);
//                        finish();
//                        intent.putExtra("data2", data2);
//                        intent.putExtra("accNo", obj3.getString("accNo"));
//                        intent.putExtra("merchantNo", obj3.getString("merchantNo"));
//                        intent.putExtra("orderNum", obj3.getString("orderNum"));
//                        intent.putExtra("encrypt", obj3.getString("encrypt"));
//                        intent.putExtra("type", obj3.getString("type"));
//                        intent.putExtra("phone", obj3.getString("phone"));
//                        intent.putExtra("cvn2", obj3.getString("cvn2"));
//                        intent.putExtra("callBackUrl", obj3.getString("callBackUrl"));
//                        intent.putExtra("serverCallBackUrl", obj3.getString("serverCallBackUrl"));
//                        intent.putExtra("sign", obj3.getString("sign"));
//                        intent.putExtra("url", obj2.getString("url"));
//                        startActivity(intent);
                    } else {
                        tipView("1",msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1",message);
            }
        });
    }

    /**
     * 融汇绑卡
     */
    private void bangKa() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", getIntent().getStringExtra("NCardId"));
        Connect.getInstance().post(getApplicationContext(), IService.RONGHUI_BANGKA, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                // 0-已签约  1-进件  2-绑卡
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
                        String data2 = obj2.getString("data");
                        JSONObject obj3 = new JSONObject(data2);
                        Intent intent = new Intent(getApplicationContext(), RongHuiWebActivity.class);
                        finish();
                        intent.putExtra("data2", data2);
                        intent.putExtra("accNo", obj3.getString("accNo"));
                        intent.putExtra("merchantNo", obj3.getString("merchantNo"));
                        intent.putExtra("orderNum", obj3.getString("orderNum"));
                        intent.putExtra("encrypt", obj3.getString("encrypt"));
                        intent.putExtra("type", obj3.getString("type"));
                        intent.putExtra("phone", obj3.getString("phone"));
                        intent.putExtra("cvn2", obj3.getString("cvn2"));
                        intent.putExtra("callBackUrl", obj3.getString("callBackUrl"));
                        intent.putExtra("serverCallBackUrl", obj3.getString("serverCallBackUrl"));
                        intent.putExtra("sign", obj3.getString("sign"));
                        intent.putExtra("url", obj2.getString("url"));
                        startActivity(intent);
                    } else {
                        tipView("1",msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1",message);
            }
        });
    }

    /**
     * 提交门店信息
     */
    private void submitInfo() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.show();
//        String shanghuleixing = null;
//        if (sh_typeInfo.getText().toString().equals("企业商户")) {
//            shanghuleixing = "ENTERPRISE";
//        } else if (sh_typeInfo.getText().toString().equals("事业单位商户")) {
//            shanghuleixing = "INSTITUTION";
//        } else if (sh_typeInfo.getText().toString().equals("个体工商户")) {
//            shanghuleixing = "INDIVIDUALBISS";
//        } else if (sh_typeInfo.getText().toString().equals("个人商户")) {
//            shanghuleixing = "PERSON";
//        }
        HashMap<String, String> params = new HashMap<>();
        params.put("resmark", RESMARK);
        params.put("merchantname", sh_name.getText().toString());
        params.put("merchanttype", "PERSON");
        params.put("installprovince", sh_shengInfo.getText().toString());
        params.put("installcity", sh_cityInfo.getText().toString());
        params.put("installcounty", sh_xianquInfo.getText().toString());
        params.put("operateaddress", sh_detail.getText().toString());
        params.put("uid", spConfig.getUserAllInfoNew().getUid());
        params.put("merchantpersonname", lx_name.getText().toString());
        params.put("merchantpersonemail", lx_email.getText().toString());
//        params.put("merchantcategory", jingyingleixing);
        params.put("merchantpersonphone", lx_phone.getText().toString());
//        if (!sh_typeInfo.getText().toString().equals("个人商户")) {
//            params.put("businesslicense", sh_credit.getText().toString());
////            params.put("industrytypecode", hangyeleixing);
//            params.put("legalpersonname", fr_name.getText().toString());
//            params.put("legalpersonid", fr_cardId.getText().toString());
//        }
        Connect.getInstance().post(this.getApplicationContext(), IService.SUBMIT_INFO, params, new Connect.OnResponseListener() {
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
                        String statusName = obj2.get("statusName").toString();
                        String statusMsg = obj2.get("statusMsg").toString();
                        if (statusName.equals("input_sellermsg")) {
                            tipView("1",statusMsg);
                        } else if (statusName.equals("input_money")) {
                            startActivity(new Intent(MenDianActivity.this, DynamicQRcodeActivity.class));
                            tipView("1",statusMsg);
                            finish();
                        } else {
                            tipView("1",statusMsg);
                        }
                    } else {
                        tipView("1",msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1",message);
            }
        });
    }

    public void checkJingying() {
        jy_typeInfo.setEnabled(false);
        if (hy_typeInfo.getText().toString().equals("保险代理公司") ||
                hy_typeInfo.getText().toString().equals("保险公估公司") ||
                hy_typeInfo.getText().toString().equals("保险公司") ||
                hy_typeInfo.getText().toString().equals("保险兼业代理公司") ||
                hy_typeInfo.getText().toString().equals("保险经纪公司") ||
                hy_typeInfo.getText().toString().equals("财经资讯") ||
                hy_typeInfo.getText().toString().equals("股票软件类")) {
            jy_typeInfo.setText("金融/保险");
            jingyingleixing = "FINANCE_INSURANCE";
        } else if (hy_typeInfo.getText().toString().equals("彩票")) {
            jy_typeInfo.setText("彩票");
            jingyingleixing = "LOTTERY";
        } else if (hy_typeInfo.getText().toString().equals("保健品/滋补品") ||
                hy_typeInfo.getText().toString().equals("餐饮") ||
                hy_typeInfo.getText().toString().equals("普通食品")) {
            jy_typeInfo.setText("餐饮/食品");
            jingyingleixing = "FOOD_BEVERAGE";
        } else if (hy_typeInfo.getText().toString().equals("房产预售") ||
                hy_typeInfo.getText().toString().equals("房屋中介")) {
            jy_typeInfo.setText("房地产");
            jingyingleixing = "REAL_ESTATE";
        } else if (hy_typeInfo.getText().toString().equals("水电煤缴费/交通罚款等生活缴费") ||
                hy_typeInfo.getText().toString().equals("事业单位") ||
                hy_typeInfo.getText().toString().equals("政府机构")) {
            jy_typeInfo.setText("公共事业缴费");
            jingyingleixing = "UTILITIES_EXPENSE";
        } else if (hy_typeInfo.getText().toString().equals("海淘") ||
                hy_typeInfo.getText().toString().equals("团购") ||
                hy_typeInfo.getText().toString().equals("线上商超")) {
            jy_typeInfo.setText("电商团购");
            jingyingleixing = "GROUP_PURCHASE";
        } else if (hy_typeInfo.getText().toString().equals("机票代理") ||
                hy_typeInfo.getText().toString().equals("旅馆/酒店/景区/度假区") ||
                hy_typeInfo.getText().toString().equals("旅行社") ||
                hy_typeInfo.getText().toString().equals("航空公司") ||
                hy_typeInfo.getText().toString().equals("旅游服务平台")) {
            jy_typeInfo.setText("票务/旅游");
            jingyingleixing = "TICKETING_TRAVEL";
        } else if (hy_typeInfo.getText().toString().equals("保健器械") ||
                hy_typeInfo.getText().toString().equals("电子元器件/仪器仪表/机械设备及配件") ||
                hy_typeInfo.getText().toString().equals("医疗器械")) {
            jy_typeInfo.setText("机械/电子");
            jingyingleixing = "MACHINE_ELECTRON";
        } else if (hy_typeInfo.getText().toString().equals("公益")) {
            jy_typeInfo.setText("公益");
            jingyingleixing = "PUBLIC_WELFARE";
        } else if (hy_typeInfo.getText().toString().equals("道路运输") ||
                hy_typeInfo.getText().toString().equals("港口经营港口理货") ||
                hy_typeInfo.getText().toString().equals("海运") ||
                hy_typeInfo.getText().toString().equals("航空运输") ||
                hy_typeInfo.getText().toString().equals("水路运输") ||
                hy_typeInfo.getText().toString().equals("铁路货物运输") ||
                hy_typeInfo.getText().toString().equals("租车")) {
            jy_typeInfo.setText("交通运输服务类");
            jingyingleixing = "TRANSPORTATION_SERVICE";
        } else if (hy_typeInfo.getText().toString().equals("教育/培训/考试缴费/学费") ||
                hy_typeInfo.getText().toString().equals("私立院校") ||
                hy_typeInfo.getText().toString().equals("公立院校")) {
            jy_typeInfo.setText("教育/培训");
            jingyingleixing = "EDUCATION_TRAINING";
        } else if (hy_typeInfo.getText().toString().equals("城市交通卡缴费") ||
                hy_typeInfo.getText().toString().equals("其他生活缴费") ||
                hy_typeInfo.getText().toString().equals("停车场") ||
                hy_typeInfo.getText().toString().equals("物业管理费") ||
                hy_typeInfo.getText().toString().equals("有线电视缴费")) {
            jy_typeInfo.setText("生活缴费");
            jingyingleixing = "LIVING_PAYMENT";
        } else if (hy_typeInfo.getText().toString().equals("工艺品/盆栽/室内装饰品") ||
                hy_typeInfo.getText().toString().equals("计生用品") ||
                hy_typeInfo.getText().toString().equals("家居/建材/装饰/布艺类商城") ||
                hy_typeInfo.getText().toString().equals("美妆/护肤/个人护理") ||
                hy_typeInfo.getText().toString().equals("汽车/摩托/自行车/其他交通工具/配件/改装") ||
                hy_typeInfo.getText().toString().equals("家用电器")) {
            jy_typeInfo.setText("家居");
            jingyingleixing = "HOME_FURNISHING";
        } else if (hy_typeInfo.getText().toString().equals("办证/刻章") ||
                hy_typeInfo.getText().toString().equals("广告公司") ||
                hy_typeInfo.getText().toString().equals("会展服务/活动策划") ||
                hy_typeInfo.getText().toString().equals("家政/婚庆服务/摄影服务") ||
                hy_typeInfo.getText().toString().equals("开锁工具") ||
                hy_typeInfo.getText().toString().equals("人才中介机构/招聘/猎头") ||
                hy_typeInfo.getText().toString().equals("丧葬行业") ||
                hy_typeInfo.getText().toString().equals("印刷/维修服务/排版/刻板") ||
                hy_typeInfo.getText().toString().equals("职业社交/婚介/交友") ||
                hy_typeInfo.getText().toString().equals("咨询/法律咨询/金融咨询等") ||
                hy_typeInfo.getText().toString().equals("报社/出版社") ||
                hy_typeInfo.getText().toString().equals("电台/电视台") ||
                hy_typeInfo.getText().toString().equals("网上生活服务平台")) {
            jy_typeInfo.setText("生活/咨询服务");
            jingyingleixing = "LIFE_ADVISORY_SERVICE";
        } else if (hy_typeInfo.getText().toString().equals("服饰类商城/服饰配件/箱包") ||
                hy_typeInfo.getText().toString().equals("户外/运动/健身器材/安防") ||
                hy_typeInfo.getText().toString().equals("黄金珠宝/钻石/玉石") ||
                hy_typeInfo.getText().toString().equals("乐器") ||
                hy_typeInfo.getText().toString().equals("礼品/鲜花/纪念品") ||
                hy_typeInfo.getText().toString().equals("饰品") ||
                hy_typeInfo.getText().toString().equals("手表/钟表/眼镜")) {
            jy_typeInfo.setText("时尚");
            jingyingleixing = "FASHION";
        } else if (hy_typeInfo.getText().toString().equals("保健信息咨询/心理咨询/体检卡") ||
                hy_typeInfo.getText().toString().equals("公立医院") ||
                hy_typeInfo.getText().toString().equals("挂号平台") ||
                hy_typeInfo.getText().toString().equals("药品") ||
                hy_typeInfo.getText().toString().equals("亲子鉴定/催眠") ||
                hy_typeInfo.getText().toString().equals("私立/民营医院/诊所") ||
                hy_typeInfo.getText().toString().equals("中草药原材料")) {
            jy_typeInfo.setText("医疗");
            jingyingleixing = "MEDICAL_CARE";
        } else if (hy_typeInfo.getText().toString().equals("门户/资讯/论坛") ||
                hy_typeInfo.getText().toString().equals("视频/网络小说/在线图书/音乐") ||
                hy_typeInfo.getText().toString().equals("搜索引擎/网络广告/网络推广/视频制作") ||
                hy_typeInfo.getText().toString().equals("游戏/点卡/金币") ||
                hy_typeInfo.getText().toString().equals("域名/建站/主机/代码") ||
                hy_typeInfo.getText().toString().equals("私立/民营医院/诊所") ||
                hy_typeInfo.getText().toString().equals("中草药原材料")) {
            jy_typeInfo.setText("网络虚拟服务");
            jingyingleixing = "NETWORK_VIRTUAL_SERVICE";
        } else if (hy_typeInfo.getText().toString().equals("百货") ||
                hy_typeInfo.getText().toString().equals("便利店") ||
                hy_typeInfo.getText().toString().equals("超市") ||
                hy_typeInfo.getText().toString().equals("其他综合零售") ||
                hy_typeInfo.getText().toString().equals("自动贩卖机") ||
                hy_typeInfo.getText().toString().equals("私立/民营医院/诊所") ||
                hy_typeInfo.getText().toString().equals("中草药原材料")) {
            jy_typeInfo.setText("线下零售");
            jingyingleixing = "OFFLINE_RETAIL";
        } else if (hy_typeInfo.getText().toString().equals("宠物/宠物食品") ||
                hy_typeInfo.getText().toString().equals("非文物类收藏品") ||
                hy_typeInfo.getText().toString().equals("文物复制品销售/典当") ||
                hy_typeInfo.getText().toString().equals("文物经营") ||
                hy_typeInfo.getText().toString().equals("文物拍卖")) {
            jy_typeInfo.setText("收藏/拍卖");
            jingyingleixing = "COLLECTION_AUCTION";
        } else if (hy_typeInfo.getText().toString().equals("俱乐部/高尔夫球场/休闲会所") ||
                hy_typeInfo.getText().toString().equals("美容/健身类会所") ||
                hy_typeInfo.getText().toString().equals("游艺厅/KTV/网吧")) {
            jy_typeInfo.setText("娱乐/健身服务");
            jingyingleixing = "ENTERTAINMENT_FITNESS_SERVICES";
        } else if (hy_typeInfo.getText().toString().equals("文物拍卖") &&
                sh_typeInfo.getText().toString().equals("个体工商户")) {
            jy_typeInfo.setText("收藏/拍卖");
            jingyingleixing = "ENTERTAINMENT_FITNESS_SERVICES";
        } else if (hy_typeInfo.getText().toString().equals("苗木种植") ||
                hy_typeInfo.getText().toString().equals("园林绿化") ||
                hy_typeInfo.getText().toString().equals("化肥/农用药剂等")) {
            jy_typeInfo.setText("娱苗木/绿化");
            jingyingleixing = "GREEN_SEEDLING";
        } else if (hy_typeInfo.getText().toString().equals("母婴类商城") ||
                hy_typeInfo.getText().toString().equals("母婴用品/儿童玩具")) {
            jy_typeInfo.setText("母婴/玩具");
            jingyingleixing = "MATERNAL_CHILD_PRODUCT";
        } else if (hy_typeInfo.getText().toString().equals("电信运营商") ||
                hy_typeInfo.getText().toString().equals("话费通讯") ||
                hy_typeInfo.getText().toString().equals("宽带收费")) {
            jy_typeInfo.setText("通讯");
            jingyingleixing = "COMMUNICATION";
        } else if (hy_typeInfo.getText().toString().equals("火车票/船票/车票等交通票务") ||
                hy_typeInfo.getText().toString().equals("影票/演唱会/赛事等娱乐票务")) {
            jy_typeInfo.setText("票务/旅游");
            jingyingleixing = "TICKETING_TRAVEL";
        } else if (hy_typeInfo.getText().toString().equals("书籍/音像") ||
                hy_typeInfo.getText().toString().equals("文具")) {
            jy_typeInfo.setText("书籍/音像/文具");
            jingyingleixing = "BOOK_STATIONERY_AUDIO_VIDEO";
        } else if (hy_typeInfo.getText().toString().equals("办公设备") ||
                hy_typeInfo.getText().toString().equals("数码产品")) {
            jy_typeInfo.setText("数码");
            jingyingleixing = "DIGITAL";
        } else if (hy_typeInfo.getText().toString().equals("彩铃") ||
                hy_typeInfo.getText().toString().equals("企业")) {
            jy_typeInfo.setText("数字娱乐");
            jingyingleixing = "DIGITAL_ENTERTAINMENT";
        } else if (hy_typeInfo.getText().toString().equals("物流/快递公司")) {
            jy_typeInfo.setText("物流/快递");
            jingyingleixing = "LOGISTICS_EXPRESS";
        } else if (hy_typeInfo.getText().toString().equals("其他行业")) {
            jy_typeInfo.setText("其他");
            jingyingleixing = "OTHER";
        } else if (hy_typeInfo.getText().toString().equals("室内装饰设计服务")) {
            jy_typeInfo.setText("装饰");
            jingyingleixing = "DECORATION";
        } else if (hy_typeInfo.getText().toString().equals("软件")) {
            jy_typeInfo.setText("软件");
            jingyingleixing = "SOFTWARE";
        } else if (hy_typeInfo.getText().toString().equals("境外")) {
            jy_typeInfo.setText("境外");
            jingyingleixing = "ABROAD";
        } else if (hy_typeInfo.getText().toString().equals("平台商")) {
            jy_typeInfo.setText("平台商");
            jingyingleixing = "BUSINESS_PLATFORM";
        } else if (hy_typeInfo.getText().toString().equals("单用途预付卡")) {
            jy_typeInfo.setText("预付卡");
            jingyingleixing = "PREPAID_CARD";
        } else if (hy_typeInfo.getText().toString().equals("单用途预付卡")) {
            jy_typeInfo.setText("直销");
            jingyingleixing = "DIRECT_SELLING";
        } else if (hy_typeInfo.getText().toString().equals("众筹")) {
            jy_typeInfo.setText("众筹");
            jingyingleixing = "CROWD_FUNDING";
        }
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", MenDianActivity.this,
                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
            @Override
            public void onClick(View view) {
                if (flag.equals("1")) {
                    backDialog.dismiss();
                } else {
                    finish();
                }
            }
        });
        backDialog.setCancelable(false);
        backDialog.show();
    }
}
