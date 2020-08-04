package com.kym.ui.activity.daichang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.bean.JsonBean;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 融汇城市选择
 * Created by sunmiaolong on 2018/7/29.
 * Updated by sunmiaolong on 2019/6/21.
 */

public class DaiChangCityActivity extends BaseActivity implements View.OnClickListener {

    private BankListResponse.BankInfo kuaiJieInfo;
    private LinearLayout li_ld_hy;
    private TextView ld_hy, change_card;
    private EditText ld_city;
    private String province, city, area, JsonData;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ArrayList<JSONObject> stringArrayList = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daichang_city);
        kuaiJieInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("data");
        initHead();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getRHcity();
    }

    private void initHead() {
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
        tvTitle.setText("商户进件");
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    private void initView() {
        CircleImageView ivBankLogo = (CircleImageView) findViewById(R.id.iv_bank_sign);
        TextView tvBankName = (TextView) findViewById(R.id.tv_bank_sign_card_name);
        TextView tvBankNumber = (TextView) findViewById(R.id.tv_bank_sign_card_number);
        findViewById(R.id.change_card).setOnClickListener(this);
        Glide.with(this).load(kuaiJieInfo.getLogo_url()).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(kuaiJieInfo.getBank_name());
        tvBankNumber.setText(kuaiJieInfo.getBank_no().substring(0, 4)
                + " **** **** " + kuaiJieInfo.getBank_no().substring(kuaiJieInfo.getBank_no().length() - 4, kuaiJieInfo.getBank_no().length()));
        ld_city = (EditText) findViewById(R.id.ld_city);
        ld_hy = (TextView) findViewById(R.id.ld_hy);
        li_ld_hy = (LinearLayout) findViewById(R.id.li_ld_hy);
        li_ld_hy.setOnClickListener(this);
        change_card = (TextView) findViewById(R.id.change_card);
        change_card.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.li_ld_hy:
//                if (ld_city.getText().toString().equals("")) {
//                    tipView("1", "商户名称不能为空");
//                } else if (ld_city.getText().toString().length() < 6 || ld_city.getText().toString().length() > 12) {
//                    tipView("1", "商户名称不能小于5位或大于12位");
//                } else {
                if (isLoaded) {
                    showPickerView();
                }
//                }
                break;
            case R.id.change_card:
//                if (ld_city.getText().toString().equals("")) {
//                    tipView("1", "商户名称不能为空");
//                } else if (ld_city.getText().toString().length() < 6 || ld_city.getText().toString().length() > 12) {
//                    tipView("1", "商户名称不能小于5位或大于12位");
//                } else
                if (ld_hy.getText().toString().equals("请选择所在城市")) {
                    tipView("1", "请选择所在城市");
                } else {
                    getsubmit();
                }
                break;
        }
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                province = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                city = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                area = options3Items.size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";
                ld_hy.setText(province + city + area);
                ld_hy.setTextColor(getResources().getColor(R.color.black_33));

                try {
                    stringArrayList = new ArrayList<>();
                    JSONObject map = new JSONObject();
                    map.put("merchantName", ld_city.getText().toString());
                    map.put("installProvince", province);
                    map.put("installCity", city);
                    map.put("installCounty", area);
                    stringArrayList.add(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        })
                .setTitleText("所在城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//二级选择器
        pvOptions.show();
    }

    /**
     * 获取空卡融汇进件城市
     */
    private void getRHcity() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.RH_SIGN_CITY, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    if (code.equals("10000")) {
                        JsonData = obj.get("data").toString();
                        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
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
     * 提交签约或进件信息
     */
    private void getsubmit() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("province", province);
        params.put("city", city);
        params.put("country", area);
        Connect.getInstance().post(getApplicationContext(), IService.RH_ShangChuan_CITY, params, new Connect.OnResponseListener() {
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
                        getBangKa();
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
     * 融汇绑卡
     */
    private void getBangKa() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", getIntent().getStringExtra("id"));
        params.put("cardid", kuaiJieInfo.getCardid());
        Connect.getInstance().post(getApplicationContext(), IService.ALL_SIGN, params, new Connect.OnResponseListener() {
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
                        String data = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data);
                        Intent intent = new Intent(getApplicationContext(), DaiChangRongHuiWebActivity.class);
                        intent.putExtra("html", obj2.getString("html"));
                        startActivity(intent);
                        finish();
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

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        ArrayList<JsonBean> jsonBean = parseData(JsonData);

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> areaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> arealist = new ArrayList<>();
                for (int n = 0; n < jsonBean.get(i).getCityList().get(c).getArea().size(); n++) {//遍历该省份的所有县区
                    String areaName = jsonBean.get(i).getCityList().get(c).getArea().get(n).getName();
                    arealist.add(areaName);
                }
                areaList.add(arealist);//添加县区
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加县区数据
             */
            options3Items.add(areaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", DaiChangCityActivity.this,
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
