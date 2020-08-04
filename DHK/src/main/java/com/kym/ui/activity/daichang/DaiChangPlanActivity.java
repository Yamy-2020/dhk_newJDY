package com.kym.ui.activity.daichang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaiChangPlanActivity extends BaseActivity implements View.OnClickListener {

    private BankListResponse.BankInfo bankInfo;
    private LinearLayout li_dc_way, li_dc_city;
    private PopupWindow popWindow;
    private TextView dc_money, dc_way, dc_city, change_card;
    private String modeltype; //modeltype 还款模式 1按序还款 2随机还款
    private Intent intent;
    private String province, city, area, JsonData;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private BackDialog backDialog;
    private static boolean isLoaded = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_chang_plan);
        bankInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("bankInfo");
        initHead();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCity();
    }

    private void initHead() {
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
        tvTitle.setText("预览贷偿计划");
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
        Glide.with(this).load(bankInfo.getLogo_url()).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(bankInfo.getBank_name());
        tvBankNumber.setText(bankInfo.getBank_no().substring(0, 4)
                + " **** **** " + bankInfo.getBank_no().substring(bankInfo.getBank_no().length() - 4, bankInfo.getBank_no().length()));
        li_dc_way = (LinearLayout) findViewById(R.id.li_dc_way);
        li_dc_city = (LinearLayout) findViewById(R.id.li_dc_city);
        dc_way = (TextView) findViewById(R.id.dc_way);
        dc_money = (TextView) findViewById(R.id.dc_money);
        dc_city = (TextView) findViewById(R.id.dc_city);
        change_card = (TextView) findViewById(R.id.change_card);
        li_dc_way.setOnClickListener(this);
        li_dc_city.setOnClickListener(this);
        change_card.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.li_dc_way:
                showModePopup();
                break;
            case R.id.li_dc_city:
                if (isLoaded) {
                    showPickerView();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                break;
            case R.id.cancel:
                popWindow.dismiss();
                break;
            case R.id.photograph:
                dc_way.setText("按序还款");
                dc_way.setTextColor(getResources().getColor(R.color.black_33));
                modeltype = "1";
                popWindow.dismiss();
                break;
            case R.id.albums:
                dc_way.setText("随机还款");
                dc_way.setTextColor(getResources().getColor(R.color.black_33));
                modeltype = "2";
                popWindow.dismiss();
                break;
            case R.id.change_card:
                if (canConfirm()) {
                    intent = new Intent(getApplicationContext(), DaiChangPlanPreviewActivity.class);
                    intent.putExtra("cardid", bankInfo.getCardid());
                    intent.putExtra("money", dc_money.getText().toString());
                    intent.putExtra("modeltype", modeltype);
                    intent.putExtra("city", city);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    private void showModePopup() {
        if (popWindow == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.pop_select_photo, null);
            initModeView(view);
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            popWindow.setContentView(view);
            popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
            popWindow.setFocusable(true);
            popWindow.setOutsideTouchable(true);
            popWindow.setBackgroundDrawable(new BitmapDrawable());
            popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        popWindow.showAtLocation(li_dc_way, Gravity.CENTER, 0, 0);
    }

    private void initModeView(View view) {
        view.findViewById(R.id.cancel).setOnClickListener(this);
        TextView first = (TextView) view.findViewById(R.id.photograph);
        TextView second = (TextView) view.findViewById(R.id.albums);
        first.setText("按序还款");
        first.setTextColor(0xFF333333);
        first.setOnClickListener(this);
        second.setText("随机还款");
        second.setTextColor(0xFF333333);
        second.setOnClickListener(this);
    }

    private boolean canConfirm() {
        if (TextUtils.isEmpty(dc_money.getText().toString())) {
            tipView("1", "请输入还款金额");
            return false;
        } else if (dc_way.getText().toString().equals("请选择还款方式")) {
            tipView("1", "请选择还款方式");
            return false;
        } else if (dc_city.getText().toString().equals("请选择落地城市")) {
            tipView("1", "请选择落地城市");
            return false;
        }
        return true;
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

                dc_city.setText(province + city + area);
                dc_city.setTextColor(getResources().getColor(R.color.black_33));
            }
        })
                .setTitleText("消费地区选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//二级选择器

        pvOptions.show();
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

    /**
     * 获取空卡代还落地城市
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", DaiChangPlanActivity.this,
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
