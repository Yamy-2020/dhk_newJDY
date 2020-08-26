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
import com.kym.ui.activity.LoginActivity;
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
import static com.kym.ui.appconfig.IService.SHOUXINYI_PAY;
import static com.kym.ui.appconfig.IService.SHOUXINYI_PAY1;


/**
 * 选择刷卡商户城市
 *
 * @author sun
 * @date 2019/12/3
 */

public class KuaiJieTFTShanghuActivity extends BaseActivity implements View.OnClickListener {

    private KuaiJieCardList.KuaiJieCardListInfo kuaiJieInfo;
    private LinearLayout li_ld_city;
    private TextView ld_city, change_card;
    private String province, city, area, JsonData;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;
    private BackDialog backDialog;
    private String orderid, requestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tft_shanghu);
        kuaiJieInfo = (KuaiJieCardList.KuaiJieCardListInfo) getIntent().getSerializableExtra("data");
        initHead();
        initView();
        getCity();
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
        tvTitle.setText("选择刷卡商户城市");
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
                                initJsonData1();
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
        CircleImageView ivBankLogo = findViewById(R.id.iv_bank_sign);
        TextView tvBankName = findViewById(R.id.tv_bank_sign_card_name);
        TextView tvBankNumber = findViewById(R.id.tv_bank_sign_card_number);
        findViewById(R.id.change_card).setOnClickListener(this);
        Glide.with(this).load(kuaiJieInfo.getLogo_url()).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(kuaiJieInfo.getBank_name());
        tvBankNumber.setText(kuaiJieInfo.getBank_no().substring(0, 4) + " **** **** " + kuaiJieInfo.getBank_no().substring(kuaiJieInfo.getBank_no().length() - 4, kuaiJieInfo.getBank_no().length()));
        ld_city = findViewById(R.id.ld_city);
        li_ld_city = findViewById(R.id.li_ld_city);
        li_ld_city.setOnClickListener(this);
        change_card = findViewById(R.id.change_card);
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
                    showPickerView1();
                }
                break;
            case R.id.change_card:
                if (ld_city.getText().toString().equals("请选择刷卡商户城市")) {
                    tipView("请选择刷卡商户城市");
                } else {
                    if (getIntent().getStringExtra("ID").equals("11")) {
                        sxy_QianYue();
                    } else if (getIntent().getStringExtra("ID").equals("16")) {
                        sxyQ_QianYue(SHOUXINYI_PAY);
                    } else if (getIntent().getStringExtra("ID").equals("18")) {
                        sxyQ_QianYue(SHOUXINYI_PAY1);
                    }else {
                        Intent intent = new Intent(getApplicationContext(), KuaiJieMsgPayActivity.class);
                        intent.putExtra("data", kuaiJieInfo);
                        intent.putExtra("ID", getIntent().getStringExtra("ID"));
                        intent.putExtra("cardid", getIntent().getStringExtra("cardid"));
                        intent.putExtra("money", getIntent().getStringExtra("money"));
                        intent.putExtra("city", city);
                        startActivity(intent);
                        finish();
                    }
                }
                break;
        }
    }

    /**
     * 首信易
     */
    private void sxy_QianYue() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("money", getIntent().getStringExtra("money"));
        params.put("cardid", getIntent().getStringExtra("cardid"));
        params.put("id", getIntent().getStringExtra("ID"));
        params.put("city", city);
        Connect.getInstance().post(getApplicationContext().getApplicationContext(), IService.SXY_QIANYUE, params, new Connect.OnResponseListener() {
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
                        String statusName = obj2.getString("statusName");
                        String statusMsg = obj2.getString("statusMsg");
                        if (statusName.equals("sms_sended")) {
                            orderid = obj2.getString("orderid");
                            requestId = obj2.getString("requestId");
                            if (obj2.getString("is_bind").equals("1")) {
                                Intent intent = new Intent(getApplicationContext(), KuaiJieMsgPayActivity.class);
                                intent.putExtra("data", kuaiJieInfo);
                                intent.putExtra("ID", getIntent().getStringExtra("ID"));
                                intent.putExtra("cardid", getIntent().getStringExtra("cardid"));
                                intent.putExtra("money", getIntent().getStringExtra("money"));
                                intent.putExtra("requestId", requestId);
                                intent.putExtra("orderid", orderid);
                                intent.putExtra("tip", statusMsg);
                                intent.putExtra("city", city);
                                startActivity(intent);
                                finish();
                            } else {
                                tipView(statusMsg);
                                finish();
                            }
                        } else {
                            tipView(statusMsg);
                            finish();
                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", KuaiJieTFTShanghuActivity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(KuaiJieTFTShanghuActivity.this, LoginActivity.class));

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
     * 首信易
     */
    private void sxyQ_QianYue(String type) {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("money", getIntent().getStringExtra("money"));
        params.put("cardid", getIntent().getStringExtra("cardid"));
        params.put("id", getIntent().getStringExtra("ID"));
        params.put("city", city);
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
                        String statusName = obj2.getString("statusName");
                        String statusMsg = obj2.getString("statusMsg");
                        if (statusName.equals("sms_sended")) {
                            requestId = obj2.getString("requestId");
                            Intent intent = new Intent(getApplicationContext(), KuaiJieMsgPayActivity.class);
                            intent.putExtra("data", kuaiJieInfo);
                            intent.putExtra("ID", getIntent().getStringExtra("ID"));
                            intent.putExtra("cardid", getIntent().getStringExtra("cardid"));
                            intent.putExtra("requestId", requestId);
                            intent.putExtra("statusMsg",statusMsg);
                            startActivity(intent);
                            finish();
                        } else {
                            tipView(statusMsg);
                            finish();
                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", KuaiJieTFTShanghuActivity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(KuaiJieTFTShanghuActivity.this, LoginActivity.class));

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
     * 获取消费地区
     */
    private void getCity() {
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

    private void showPickerView1() {// 弹出选择器

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
                ld_city.setText(province + city + area);
                ld_city.setTextColor(getResources().getColor(R.color.black_33));
            }
        })
                .setTitleText("选择刷卡商户城市")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//二级选择器

        pvOptions.show();
    }

    private void initJsonData1() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        ArrayList<JsonBean> jsonBean = parseData1(JsonData);

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
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    public void tipView(String msg) {
        backDialog = new BackDialog("", msg, "确定", KuaiJieTFTShanghuActivity.this,
                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
            @Override
            public void onClick(View view) {
                backDialog.dismiss();
            }
        });
        backDialog.setCancelable(false);
        backDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backDialog = null;
    }
}
