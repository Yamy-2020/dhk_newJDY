package com.kym.ui.activity.new_dc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.google.gson.Gson;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.SelectBankAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.bean.JsonBean;
import com.kym.ui.info.BankNameResponse;
import com.kym.ui.info.YanzhengInfo_old;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
import widget.CustomPopWindow;

/**
 * 服务商申请
 *
 * @author sun
 * @date 2019/12/25
 */
public class New_DaiChang_Li4_Sqb_Activity extends BaseActivity implements View.OnClickListener {

    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;
    private Thread thread;
    private EditText name, mobile;
    private TextView place, type;
    private PromptDialog promptDialog;
    private String province, city, area, JsonData, type_id;
    private CustomPopWindow bankPopWindow;
    private List<BankNameResponse.BankNameInfo> banks;
    private LinearLayout li_place, li_type;
    private CustomBanner<String> mBanner;
    private ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_sqb);
        initView();
        initHead();
        getBanner();
        getCity();
    }

    private void initView() {
        mBanner = (CustomBanner) findViewById(R.id.banner);
        TextView tvConfirm = (TextView) findViewById(R.id.tv_add_new_credit_confirm);
        tvConfirm.setOnClickListener(this);
        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        place = (TextView) findViewById(R.id.place);
        type = (TextView) findViewById(R.id.type);
        li_place = (LinearLayout) findViewById(R.id.li_place);
        li_type = (LinearLayout) findViewById(R.id.li_type);
        li_place.setOnClickListener(this);
        li_type.setOnClickListener(this);
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

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("服务商申请");
    }

    /**
     * 获取轮播图
     */
    private void getBanner() {
        Connect.getInstance().post(getApplicationContext(), IService.HOME_BANNER, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.getString("result");
                    JSONObject obj_result = new JSONObject(result1);
                    String code = obj_result.getString("code");
                    if (code.equals("10000")) {
                        String data = obj.getString("data");
                        JSONObject obj_data = new JSONObject(data);
                        String statusName = obj_data.getString("statusName");
                        String data1 = obj_data.getString("data");
                        JSONArray array = new JSONArray(data1);
                        if (statusName.equals("display")) {
                            images = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                images.add(array.getJSONObject(i).getString("img"));
                            }
                            setBean(images);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 设置轮播图参数
     *
     * @param beans
     */
    private void setBean(final ArrayList<String> beans) {
        mBanner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, String entity) {
                Glide.with(context).load(entity).dontAnimate().into((ImageView) view);
            }
        }, beans)
                /**
                 * 设置指示器为普通指示器
                 * 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                 * 设置指示器的方向
                 * 设置指示器的指示点间隔
                 * 设置自动翻页
                 */
                .setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY)
                .setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect)
                .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER)
                .setIndicatorInterval(20)
                .startTurning(3000);
    }

    private boolean canConfirm() {
        if (TextUtils.isEmpty(name.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "请输入申请人姓名");
            return false;
        }
        if (TextUtils.isEmpty(mobile.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "请输入申请人手机号");
            return false;
        }
        if (TextUtils.isEmpty(place.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "请选择业务地区");
            return false;
        }
        if (TextUtils.isEmpty(type.getText().toString())) {
            ToastUtil.showTextToas(getApplicationContext(), "请选择业行业类别");
            return false;
        }
        return true;
    }

    /**
     * 服务商申请表 - 提交
     */
    private void confirm() {
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("加载中");
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name.getText().toString().trim());
        params.put("mobile", mobile.getText().toString().trim());
        params.put("area", place.getText().toString());
        params.put("type", type_id);
        Connect.getInstance().post(this, IService.FUWUSHANGSHENGQING, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                promptDialog.dismissImmediately();
                YanzhengInfo_old response = (YanzhengInfo_old) JsonUtils.parse((String) result, YanzhengInfo_old.class);
                ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                finish();
            }

            @Override
            public void onFailure(String message) {
                promptDialog.dismissImmediately();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_new_credit_confirm:
                if (canConfirm()) {
                    confirm();
                }
                break;
            case R.id.head_img_left:
                finish();
                break;
            case R.id.li_place:
                if (isLoaded) {
                    showPickerView();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                break;
            case R.id.li_type:
                li_type.setEnabled(false);
                getType();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                place.setText(province + city + area);
                place.setTextColor(getResources().getColor(R.color.black_33));
            }
        })
                .setTitleText("业务地区选择")
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
     * 获取业务地区
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

    /**
     * 获取行业类别
     */
    private void getType() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.FUWUSHANGTYPE, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                li_type.setEnabled(true);
                BankNameResponse response = (BankNameResponse) JsonUtils.parse((String) result, BankNameResponse.class);
                if (response.getResult().getCode() == 10000) {
                    banks = response.getData();
                    showBankPop();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                li_type.setEnabled(true);
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    private void showBankPop() {
        if (bankPopWindow == null) {
            View view = initBankView();
            bankPopWindow = new CustomPopWindow.Builder(this).setAnimationStyle(android.R.style.Animation_InputMethod).setFocusable(true)
                    .setOutsideFocusable(true).setContentView(view).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .build();
//            bankPopWindow = new CustomPopWindow.Builder(this).setFocusable(true).setOutsideFocusable(true)
//                    .setContentView(view).setTranslucent(false).size(type.getWidth(), 480)
//                    .build();
        }
        bankPopWindow.showAtLocation(type, Gravity.BOTTOM);
    }


    private View initBankView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_select_bank, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_select_bank);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider_1dp));
        recyclerView.addItemDecoration(decoration);
        SelectBankAdapter adapter = new SelectBankAdapter(this);
        adapter.setData(banks);
        adapter.setListener(new SelectBankAdapter.SelectBankListener() {
            @Override
            public void selectBank(BankNameResponse.BankNameInfo bankInfo) {
                type.setText(bankInfo.getName());
                type_id = bankInfo.getId();
                bankPopWindow.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }


}
