package com.kym.ui.activity.bpbro_home.bpbro_yk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
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
import com.kym.ui.BackDialog;
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
import com.kym.ui.info.XFplanResponse;
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
 * 制定养卡计划
 *
 * @author sun
 * @date 2019/12/3
 */

public class XFplanActivity extends BaseActivity implements View.OnClickListener {

    private BankListResponse.BankInfo bankInfo;
    private String NCardId, NBankName, NLogoUrl, NBankNo;
    private TextView xf_city, xf_way, xf_time;
    private EditText xf_money, xf_num;
    private static boolean isLoaded = false;
    private String province, city, area, JsonData, type;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private BackDialog backDialog;
    private PopupWindow popWindow;
    private CalendarViewDialog dateDialog;
    private List<String> mSelectData;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String isClick;
    private TopRightMenu mTopRightMenu;
    private ImageView head_img_right;
    private DragFloatActionButton circle_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfplan);
        bankInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("bankData");
        NBankName = bankInfo.getBank_name();
        NBankNo = bankInfo.getBank_no();
        NCardId = bankInfo.getCardid();
        NLogoUrl = bankInfo.getLogo_url();
        initHead();
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getXFcity();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        isClick = pref.getString("isClick", "");
        if (isClick.equals("NO")) {
            backDialog = new BackDialog("消费计划规则",
                    "消费计划是为有效帮助提额,小额多笔多元化消费,单笔请勿超过5000元,可根据需求选择自助结算或集中结算。" +
                            "自助结算是指每笔消费计划自动到账,但需要额外支付每笔1元代付费;集中结算是每笔消费完成后资金暂由支付公司代管,待整个计划执行完成,集中金额一次代付到储蓄卡。" +
                            "单个账单总额根据您卡内余额以及单月限额,可在银联云闪付查询。", "我已知晓", XFplanActivity.this,
                    R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putString("isClick", "YES");
                    editor.apply();
                    backDialog.dismiss();
                }
            });
            backDialog.setCancelable(false);
            backDialog.show();
        }
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
        title.setText("制定养卡消费计划");
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
        xf_money = findViewById(R.id.xf_money);
        xf_city = findViewById(R.id.xf_city);
        xf_way = findViewById(R.id.xf_way);
        xf_num = findViewById(R.id.xf_num);
        xf_time = findViewById(R.id.xf_time);
        xf_city.setOnClickListener(this);
        xf_way.setOnClickListener(this);
        xf_time.setOnClickListener(this);
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
                Intent intent = new Intent(XFplanActivity.this, Course1Activity.class);
                intent.putExtra("type", "xf");
                intent.putExtra("title", "养卡消费计划");
                startActivity(intent);
                break;
            case R.id.xf_city:
                if (isLoaded) {
                    showPickerView();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                break;
            case R.id.xf_way:
                showPopupWindow(xf_way);
                break;
            case R.id.photograph:
                xf_way.setText("集中结算");
                xf_way.setTextColor(getResources().getColor(R.color.black_33));
                type = "1";
                popWindow.dismiss();
                break;
            case R.id.albums:
                xf_way.setText("自动结算");
                xf_way.setTextColor(getResources().getColor(R.color.black_33));
                type = "2";
                popWindow.dismiss();
                break;
            case R.id.cancel:
                popWindow.dismiss();
                break;
            case R.id.xf_time:
                showDateDialog();
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(xf_money.getText().toString())) {
                    tipView("1", "请输入刷卡总额");
                } else if (TextUtils.isEmpty(xf_city.getText().toString())) {
                    tipView("1", "请选择消费地区");
                } else if (TextUtils.isEmpty(xf_time.getText().toString())) {
                    tipView("1", "请输入消费时间");
                } else if (TextUtils.isEmpty(xf_num.getText().toString())) {
                    tipView("1", "请输入消费笔数");
                } else if (TextUtils.isEmpty(xf_way.getText().toString())) {
                    tipView("1", "请选择结算方式");
                } else {
                    getPrePlan();
                }
                break;
            case R.id.head_img_right:
                mTopRightMenu = new TopRightMenu(XFplanActivity.this);
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
                                    Intent intent = new Intent(XFplanActivity.this, Course1Activity.class);
                                    intent.putExtra("type", "xf");
                                    intent.putExtra("title", "养卡消费计划");
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), XFplaninfoActivity.class);
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
        TextView first = (TextView) view.findViewById(R.id.photograph);
        TextView second = (TextView) view.findViewById(R.id.albums);
        first.setText("集中结算");
        first.setTextColor(0xFF333333);
        first.setOnClickListener(this);
        second.setText("自动结算");
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
     * 获取消费地区
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

    /**
     * 获取预览计划
     */
    private void getPrePlan() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("cardid", NCardId);
        params.put("money", xf_money.getText().toString());
        params.put("type", type);
        params.put("number", xf_num.getText().toString());
        params.put("area", city);
        params.put("date", xf_time.getText().toString());
        Connect.getInstance().post(getApplicationContext(), IService.XF_YULAN_PLAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                XFplanResponse response = (XFplanResponse) JsonUtils.parse((String) result, XFplanResponse.class);
                if (response.getData() != null) {
                    Intent intent = new Intent(getApplicationContext(), XFprePlanActivity.class);
                    intent.putExtra("cardid", NCardId);
                    intent.putExtra("money", xf_money.getText().toString());
                    intent.putExtra("type", type);
                    intent.putExtra("number", xf_num.getText().toString());
                    intent.putExtra("code", city);
                    intent.putExtra("date", xf_time.getText().toString());
                    intent.putExtra("cardno", bankInfo.getBank_no());
                    intent.putExtra("bankData", bankInfo);
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

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        dateDialog = new CalendarViewDialog(this, R.style.Theme_Dialog_Scale,
                calendar.getTime().getTime(), 300, new CalendarViewDialog.OnDateSelectConfirmListener() {
            @Override
            public void confirm(List<String> selectDate) {
                mSelectData = selectDate;
                dateDialog.dismiss();
                if (selectDate != null && selectDate.size() > 0) {
                    Collections.sort(mSelectData, new XFplanActivity.SortByDate());
                    StringBuilder builder = new StringBuilder();
                    for (String date : mSelectData) {
                        Log.d("date", date);
                        builder.append(date).append(",");
                    }
                    xf_time.setText(builder);
                }
            }
        });
        dateDialog.show();
    }

    private class SortByDate implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            int leftInt = Integer.parseInt(lhs);
            int rightInt = Integer.parseInt(rhs);
            return leftInt > rightInt ? 1 : -1;
        }
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", XFplanActivity.this,
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
