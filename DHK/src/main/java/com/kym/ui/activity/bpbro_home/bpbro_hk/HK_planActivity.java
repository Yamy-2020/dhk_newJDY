package com.kym.ui.activity.bpbro_home.bpbro_hk;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.paradigm.botkit.BotKitClient;
import com.paradigm.botlib.VisitorInfo;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.kym.ui.Course1Activity;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.fee_kf.ChatActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.bean.JsonBean;
import com.kym.ui.dialog.CalendarViewDialog;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.info.HKplanResponse;
import com.kym.ui.newutil.DragFloatActionButton;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static com.zzss.jindy.appconfig.Clone.OMID;


/**
 * 还款制定计划
 *
 * @author sun
 * @date 2019/12/3
 */

public class HK_planActivity extends BaseActivity implements View.OnClickListener {

    private final int TYPE_OVER_DATE = -2;
    private final int TYPE_UNCREATE = -3;
    private BankListResponse.BankInfo bankInfo;
    private String NCardId, NBankName, NLogoUrl, NBankNo;
    private TextView xf_city, hk_model, hk_time;
    private EditText hk_money, hk_num, hk_yue;
    private static boolean isLoaded = false;
    private String province, city, area, JsonData, model;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private PopupWindow popWindow;
    private CalendarViewDialog dateDialog;
    private List<String> mSelectData;
    private int statementDate;//账单日
    private int dueDate;//还款日
    private int time_area;
    private long startMillis;
    private TextView head_img_right;
    private TopRightMenu mTopRightMenu;
    private DragFloatActionButton circle_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hkplan);
        bankInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("bankData");
        NBankName = bankInfo.getBank_name();
        NBankNo = bankInfo.getBank_no();
        NCardId = bankInfo.getCardid();
        NLogoUrl = bankInfo.getLogo_url();
        initHead();
        initUI();
        calculateDueAndStatement();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getXFcity();
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
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = findViewById(R.id.head_text_title);
        title.setText("制定还款计划");
        head_img_right = findViewById(R.id.head_img_right);
        head_img_right.setVisibility(View.VISIBLE);
        head_img_right.setOnClickListener(this);
    }

    private void initUI() {
        circle_button = findViewById(R.id.circle_button);
        circle_button.setOnClickListener(this);
        if (OMID.equals("E1TDVFFY8JX3RY62")) {
            circle_button.setVisibility(View.GONE);
        }
        CircleImageView ivBankLogo = findViewById(R.id.iv_bank_sign);
        TextView tvBankName = findViewById(R.id.tv_bank_sign_card_name);
        TextView tvBankNumber = findViewById(R.id.tv_bank_sign_card_number);
        findViewById(R.id.submit).setOnClickListener(this);
        Glide.with(this).load(NLogoUrl).dontAnimate().placeholder(R.drawable.default_image).into(ivBankLogo);
        tvBankName.setText(NBankName);
        tvBankNumber.setText(NBankNo.substring(0, 4) +
                " **** **** " + NBankNo.substring(NBankNo.length() - 4, NBankNo.length()));
        hk_money = findViewById(R.id.hk_money);
        xf_city = findViewById(R.id.xf_city);
        hk_model = findViewById(R.id.hk_model);
        hk_num = findViewById(R.id.hk_num);
        hk_time = findViewById(R.id.hk_time);
        hk_yue = findViewById(R.id.hk_yue);
        xf_city.setOnClickListener(this);
        hk_model.setOnClickListener(this);
        hk_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.right_tv:
                Intent intent = new Intent(HK_planActivity.this, Course1Activity.class);
                intent.putExtra("type", "hk");
                intent.putExtra("title", "还款问题");
                startActivity(intent);
                break;
            case R.id.xf_city:
                if (isLoaded) {
                    showPickerView();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                break;
            case R.id.hk_model:
                showPopupWindow(hk_model);
                break;
            case R.id.photograph:
                hk_model.setText("均衡模式");
                hk_model.setTextColor(getResources().getColor(R.color.black_33));
                model = "1";
                popWindow.dismiss();
                break;
            case R.id.albums:
                hk_model.setText("驼峰模式");
                hk_model.setTextColor(getResources().getColor(R.color.black_33));
                model = "2";
                popWindow.dismiss();
                break;
            case R.id.cancel:
                popWindow.dismiss();
                break;
            case R.id.hk_time:
                showDateDialog();
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(hk_money.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入账单总额");
                } else if (TextUtils.isEmpty(xf_city.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择还款城市");
                } else if (TextUtils.isEmpty(hk_time.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入还款时间");
                } else if (TextUtils.isEmpty(hk_num.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入还款笔数");
                } else if (TextUtils.isEmpty(hk_yue.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请输入预留金额");
                } else if (TextUtils.isEmpty(hk_model.getText().toString())) {
                    ToastUtil.showTextToas(getApplicationContext(), "请选择还款模式");
                } else {
//                    double money = Double.parseDouble(hk_money.getText().toString());
//                    double k_money = Double.parseDouble(hk_yue.getText().toString());
//                    double num = Double.parseDouble(hk_num.getText().toString());
//                    double n = money / num + money * 0.1;
//                    if (k_money < n) {
//                        ToastUtil.showTextToas(getApplicationContext(), "为避免计划失败,请保证预留金额不能低于" + new BigDecimal(n).setScale(0, BigDecimal.ROUND_HALF_UP) + "元");
//                    } else {
                    getPrePlan();
//                    }
                }
                break;
            case R.id.head_img_right:
                mTopRightMenu = new TopRightMenu(HK_planActivity.this);
                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem("帮助中心"));
                menuItems.add(new MenuItem("计划列表"));
                mTopRightMenu
                        .setHeight(300)     //默认高度480
                        .showIcon(false) //默认宽度wrap_content
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                if (position == 0) {
                                    Intent intent = new Intent(HK_planActivity.this, Course1Activity.class);
                                    intent.putExtra("type", "hk");
                                    intent.putExtra("title", "还款计划");
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), HK_planinfoActivity.class);
                                    intent.putExtra("NCardId", NCardId);
                                    intent.putExtra("type", "only");
                                    startActivity(intent);
                                }
                            }
                        })
                        .showAsDropDown(head_img_right, -140, 0);
                break;
            default:
                break;
        }
    }

    @SuppressLint("InflateParams")
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.pop_select_photo, null);
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            initModeView(view);
        }
        popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    private void initModeView(View view) {
        view.findViewById(R.id.cancel).setOnClickListener(this);
        TextView first = view.findViewById(R.id.photograph);
        TextView second = view.findViewById(R.id.albums);
        first.setText("均衡模式");
        first.setTextColor(0xFF333333);
        first.setOnClickListener(this);
        second.setText("驼峰模式");
        second.setTextColor(0xFF333333);
        second.setOnClickListener(this);
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
                xf_city.setText(province + city + area);
                xf_city.setTextColor(getResources().getColor(R.color.black_33));
            }
        })
                .setTitleText("还款城市选择")
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
     * 获取还款城市
     */
    private void getXFcity() {
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

    private void calculateDueAndStatement() {
        statementDate = bankInfo.getBills();
        dueDate = bankInfo.getRepayment();
        if (dueDate != -1 && statementDate != -1) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);//当天
            if (dueDate > statementDate) {
                /**
                 * 同一个月
                 */
                if (day >= statementDate && day < dueDate) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    time_area = dueDate - day;
                    startMillis = calendar.getTime().getTime();
                } else {
                    /**
                     * 账单未生成
                     */
                    if (day == dueDate) {
                        time_area = TYPE_OVER_DATE;
                    } else {
                        time_area = TYPE_UNCREATE;
                    }
                }
            } else {
                /**
                 * 不同月
                 */
                if (day < dueDate) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    startMillis = calendar.getTime().getTime();
                    time_area = dueDate - day;
                } else if (day >= statementDate) {
                    int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    startMillis = calendar.getTime().getTime();
                    time_area = dayCount - day + dueDate;
                } else {
                    if (day == dueDate) {
                        time_area = TYPE_OVER_DATE;
                    } else {
                        time_area = TYPE_UNCREATE;
                    }
                }
            }
        } else {
            ToastUtil.showTextToas(getApplicationContext(), "未传入账单日和还款日");
        }
    }

    private void showDateDialog() {
        if (dueDate != -1 && statementDate != -1) {
            if (time_area == TYPE_UNCREATE) {
                ToastUtil.showTextToas(getApplicationContext(), "账单未生成");
            } else if (time_area == TYPE_OVER_DATE) {
                ToastUtil.showTextToas(getApplicationContext(), "超过可规划还款时间");
            } else {
                dateDialog = new CalendarViewDialog(this, R.style.Theme_Dialog_Scale,
                        startMillis, time_area, new CalendarViewDialog.OnDateSelectConfirmListener() {
                    @Override
                    public void confirm(List<String> selectDate) {
                        mSelectData = selectDate;
                        dateDialog.dismiss();
                        if (selectDate != null && selectDate.size() > 0) {
                            Collections.sort(mSelectData, new SortByDate());
                            StringBuilder builder = new StringBuilder();
                            for (String date : mSelectData) {
                                builder.append(date).append(",");
                            }
                            hk_time.setText(builder);
                        }
                    }
                });
                if (mSelectData != null) {
                    dateDialog.setSelectData(mSelectData);
                }
                dateDialog.show();
            }
        }
    }

    private class SortByDate implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            int leftInt = Integer.parseInt(lhs);
            int rightInt = Integer.parseInt(rhs);
            return leftInt > rightInt ? 1 : -1;
        }
    }

    private void getPrePlan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", NCardId);
        params.put("money", hk_money.getText().toString());
        params.put("type", getIntent().getStringExtra("type"));
        params.put("number", hk_num.getText().toString());
        params.put("area", city);
        params.put("date", hk_time.getText().toString());
        params.put("mode", model);
        params.put("cardsurplus", hk_yue.getText().toString());
        if (getIntent().getStringExtra("type").equals("3")) {
            params.put("channelType","3");
        }
        Connect.getInstance().post(getApplicationContext(), IService.NEW_HK_PLAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                HKplanResponse response = (HKplanResponse) JsonUtils.parse((String) result, HKplanResponse.class);
                if (response.getData() != null) {
                    Intent intent = new Intent(getApplicationContext(), HK_prePlanActivity.class);
                    intent.putExtra("cardid", NCardId);
                    intent.putExtra("money", hk_money.getText().toString());
                    intent.putExtra("type", getIntent().getStringExtra("type"));
                    intent.putExtra("number", hk_num.getText().toString());
                    intent.putExtra("area", city);
                    intent.putExtra("date", hk_time.getText().toString());
                    intent.putExtra("mode", model);
                    intent.putExtra("cardsurplus", hk_yue.getText().toString());
                    intent.putExtra("cradno", bankInfo.getBank_no().substring(bankInfo.getBank_no().length() - 4, bankInfo.getBank_no().length()));
                    startActivity(intent);
                    finish();
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

}
