package com.kym.ui.activity.bpbro_home.bpbro_sk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
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
import com.kym.ui.info.KuaiJieCardList;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


/**
 * 选择刷卡商户
 *
 * @author sun
 * @date 2019/12/3
 */

public class KuaiJieShanghuActivity extends BaseActivity implements View.OnClickListener {

    private KuaiJieCardList.KuaiJieCardListInfo kuaiJieInfo;
    private LinearLayout li_ld_city, li_ld_hy;
    private TextView ld_city, ld_hy, change_card;
    private String province, city, cityCode, JsonData, JsonData1, mcc;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> codeItems = new ArrayList<>();

    private List<JsonBean> options3Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options4Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> mccItems = new ArrayList<>();
    private Thread thread, thread1;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;
    private static boolean isLoaded1 = false;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yang_pu_tao);
        kuaiJieInfo = (KuaiJieCardList.KuaiJieCardListInfo) getIntent().getSerializableExtra("data");
        initHead();
        initView();
        getGHTCity();
    }

    private void initHead() {
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
        tvTitle.setText("选择刷卡商户");
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler1 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    thread1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 子线程中解析省市区数据
                            initJsonData1();
                        }
                    });
                    thread1.start();
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded1 = true;
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
        tvBankNumber.setText(kuaiJieInfo.getBank_no().substring(0, 4) + " **** **** " + kuaiJieInfo.getBank_no().substring(kuaiJieInfo.getBank_no().length() - 4, kuaiJieInfo.getBank_no().length()));
        ld_city = (TextView) findViewById(R.id.ld_city);
        ld_hy = (TextView) findViewById(R.id.ld_hy);
        li_ld_city = (LinearLayout) findViewById(R.id.li_ld_city);
        li_ld_city.setOnClickListener(this);
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
            case R.id.li_ld_city:
                if (isLoaded) {
                    showPickerView();
                }
                break;
            case R.id.li_ld_hy:
                if (ld_city.getText().toString().equals("请选择刷卡商户城市")) {
                    tipView("请选择刷卡商户城市");
                } else {
                    if (isLoaded1) {
                        showPickerView1();
                    }
                }
                break;
            case R.id.change_card:
                if (ld_city.getText().toString().equals("请选择刷卡商户城市")) {
                    tipView("请选择刷卡商户城市");
                } else if (ld_hy.getText().toString().equals("请选择刷卡商户类别")) {
                    tipView("请选择刷卡商户类别");
                } else {
                    Intent intent = new Intent(getApplicationContext(), KuaiJieMsgPayActivity.class);
                    intent.putExtra("data", kuaiJieInfo);
                    intent.putExtra("ID", getIntent().getStringExtra("ID"));
                    intent.putExtra("cardid", getIntent().getStringExtra("cardid"));
                    intent.putExtra("money", getIntent().getStringExtra("money"));
                    intent.putExtra("citycode", cityCode);
                    intent.putExtra("mcc", mcc);
                    startActivity(intent);
                    finish();
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

                ld_city.setText(province + city);
                ld_city.setTextColor(getResources().getColor(R.color.black_33));

                cityCode = codeItems.size() > 0
                        && codeItems.get(options1).size() > 0 ?
                        codeItems.get(options1).get(options2) : "";
                getGHTCity1(cityCode);
            }
        })
                .setTitleText("商户城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    private void showPickerView1() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                province = options3Items.size() > 0 ?
                        options3Items.get(options1).getPickerViewText() : "";

                city = options4Items.size() > 0
                        && options4Items.get(options1).size() > 0 ?
                        options4Items.get(options1).get(options2) : "";

                ld_hy.setText(province + city);
                ld_hy.setTextColor(getResources().getColor(R.color.black_33));

                mcc = mccItems.size() > 0
                        && mccItems.get(options1).size() > 0 ?
                        mccItems.get(options1).get(options2) : "";
            }
        })
                .setTitleText("商户类别选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options3Items, options4Items);//二级选择器
        pvOptions.show();

    }

    /**
     * 获取新生落地城市
     */
    private void getGHTCity() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.XS_LD_CITY, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.getString("msg");
                    if (code.equals("10000")) {
                        JsonData = obj.get("data").toString();
                        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", KuaiJieShanghuActivity.this,
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
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView(message);

            }
        });
    }

    /**
     * 获取空卡代高汇通落地城市
     */
    private void getGHTCity1(String cityCode) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("citycode", cityCode);
        Connect.getInstance().post(getApplicationContext(), IService.XS_LD_HY, params, new Connect.OnResponseListener() {
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
                        JsonData1 = obj.get("data").toString();
                        mHandler1.sendEmptyMessage(MSG_LOAD_DATA);
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", KuaiJieShanghuActivity.this,
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
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView(message);
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
            ArrayList<String> codeList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                String cityCode = jsonBean.get(i).getCityList().get(c).getCode();
                cityList.add(cityName);//添加城市
                codeList.add(cityCode);
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            codeItems.add(codeList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    private void initJsonData1() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */

        ArrayList<JsonBean> jsonBean = parseData1(JsonData1);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options3Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<String> codeList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市

                String infoName = jsonBean.get(i).getCityList().get(c).getName();
                ;
                String infoMcc = jsonBean.get(i).getCityList().get(c).getMcc();
                cityList.add(infoName);//添加城市
                codeList.add(infoMcc);

            }

            /**
             * 添加城市数据
             */
            options4Items.add(cityList);
            mccItems.add(codeList);
        }
        mHandler1.sendEmptyMessage(MSG_LOAD_SUCCESS);

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

    public ArrayList<JsonBean> parseData1(String result) {//Gson 解析
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
            mHandler1.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mHandler1 != null) {
            mHandler1.removeCallbacksAndMessages(null);
        }
    }

    public void tipView(String msg) {
        backDialog = new BackDialog("", msg, "确定", KuaiJieShanghuActivity.this,
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
