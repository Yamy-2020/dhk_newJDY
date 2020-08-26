package com.kym.ui.activity.zhanghu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.bean.FenRun_ZhangHuBean;
import com.kym.ui.fragment.ShouYi_Show_Fragment;
import com.kym.ui.fragment.ShouYi_Show_Fragment1;
import com.kym.ui.fragment.ShouYi_Show_Fragment2;
import com.kym.ui.fragment.ShouYi_Show_Fragment3;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import com.kym.ui.util.Connect.OnResponseListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.loadingview.PagerSlidingTabStrip;
import widget.CustomPopWindow;

/**
 * 分润账户
 *
 * @author sun
 * @date 2019/12/3
 */

public class ZhangHuActivity extends FragmentActivity implements OnClickListener {
    private final String[] TITLES = {"收款分润", "还款分润",/* "智收分润",*/ "升级分润"};
    @BindView(R.id.sum_splitter)
    TextView mSumSplitter;
    @BindView(R.id.sum_splitter3)
    TextView mSumSplitter3;
    @BindView(R.id.sum_splitter1)
    TextView mSumSplitter1;
    @BindView(R.id.sum_splitter33)
    TextView mSumSplitter33;
    @BindView(R.id.button_money)
    LinearLayout button_money;
    private CustomPopWindow datePopWindow;
    private TextView mShoukuanZhanghu;
    private TextView mHuankuanZhanghu;
    private TextView mShengjiZhanghu;
    private TextView mQuxiao;
    private FenRun_ZhangHuBean.DataBean data;
    private String hk_withdraw;
    private String skzs_withdraw;
    private String sj_withdraw;
    private String withdraw_msg;
    private String withdraw_time_msg;
    private String withdraw_amount_min;
    private String withdraw_amount_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fenrun_zhanghu_show);
        ButterKnife.bind(this);
        initView();
        getSystemMessage();


    }

    private void initView() {
        PagerSlidingTabStrip psts = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("分润账户");
        FragmentManager fm = getSupportFragmentManager();
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fm);
        pager.setAdapter(myPagerAdapter);
        psts.setViewPager(pager);
        button_money.setOnClickListener(this);
    }
/*    @Override
    protected void onResume() {
        super.onResume();
        getSystemMessage();

    }*/
    public class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ShouYi_Show_Fragment(ZhangHuActivity.this);
                case 1:
                    return new ShouYi_Show_Fragment1(ZhangHuActivity.this);
              /*  case 2:
                    return new ShouYi_Show_Fragment2(ZhangHuActivity.this);*/
                case 2:
                    return new ShouYi_Show_Fragment3(ZhangHuActivity.this);
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return TITLES.length;
        }
    }
    private void getSystemMessage() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", LoginActivity.uid);
        Connect.getInstance().post(this, IService.FENRUN_ZHANGHU, params, new OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                FenRun_ZhangHuBean info_msg = (FenRun_ZhangHuBean) JsonUtils.parse((String) result, FenRun_ZhangHuBean.class);
                if (info_msg.getResult().getCode() == 10000) {
                    data = info_msg.getData();
                    hk_withdraw = data.getHk_withdraw();
                    skzs_withdraw = data.getSkzs_withdraw();
                    sj_withdraw = data.getSj_withdraw();
                    withdraw_msg = data.getWithdraw_msg();
                    withdraw_amount_min = data.getWithdraw_amount_min();
                    withdraw_amount_max = data.getWithdraw_amount_max();
                    withdraw_time_msg = data.getWithdraw_time_msg();
                    try {
                           mSumSplitter.setText(AmountUtils.changeF2Y(data.getSum_splitter()));
                           mSumSplitter1.setText(AmountUtils.changeF2Y(data.getYesterday_splitter()));
                           mSumSplitter3.setText(AmountUtils.changeF2Y(data.getToday_splitter()));
                           mSumSplitter33.setText(AmountUtils.changeF2Y(data.getThis_month_splitter()));

                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
            }
        });
    }

    private View initDateView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_select_date1, null);
        mShoukuanZhanghu = view.findViewById(R.id.shoukuan_zhanghu);
        mHuankuanZhanghu = view.findViewById(R.id.huankuan_zhanghu);
        mShengjiZhanghu = view.findViewById(R.id.shengji_zhanghu);
        TextView mTiXian = view.findViewById(R.id.tixian_mingxi);
        mQuxiao = view.findViewById(R.id.quxiao);
        mShoukuanZhanghu.setOnClickListener(this);
        mHuankuanZhanghu.setOnClickListener(this);
        mShengjiZhanghu.setOnClickListener(this);
        mTiXian.setOnClickListener(this);
        mQuxiao.setOnClickListener(this);

        return view;


    }

    private void showDatePop() {
        View view = initDateView();
        datePopWindow = new CustomPopWindow.Builder(this).setAnimationStyle(android.R.style.Animation_InputMethod).setFocusable(true)
                .setOutsideFocusable(true).setContentView(view).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .build();
        datePopWindow.showAtLocation(button_money, Gravity.BOTTOM);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;

            case R.id.button_money:
                showDatePop();
                break;
            case R.id.shoukuan_zhanghu:
                Intent intent = new Intent(ZhangHuActivity.this, ShouKuantxActivity.class);
                try {
                    intent.putExtra("yue", AmountUtils.changeF2Y(data.getSum_splitter3()) + "");
                    intent.putExtra("hk_withdraw",AmountUtils.changeF2Y(hk_withdraw) + ""	);


                } catch (Exception e) {
                    e.printStackTrace();
                }


                intent.putExtra("withdraw_msg",withdraw_msg	);

                intent.putExtra("withdraw_amount_max",withdraw_amount_max);
                intent.putExtra("withdraw_amount_min",withdraw_amount_min);
                intent.putExtra("withdraw_time_msg",withdraw_time_msg	);

                startActivity(intent);
                datePopWindow.dismiss();

                break;
            case R.id.huankuan_zhanghu:
                Intent intent5 = new Intent(ZhangHuActivity.this, BankTiXianActivity.class);
                try {
                    intent5.putExtra("yue", AmountUtils.changeF2Y(data.getSum_splitter1()));
                    intent5.putExtra("skzs_withdraw",AmountUtils.changeF2Y(skzs_withdraw)	 + "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent5.putExtra("withdraw_msg",withdraw_msg	);

                intent5.putExtra("withdraw_amount_max",withdraw_amount_max);
                intent5.putExtra("withdraw_amount_min",withdraw_amount_min);
                intent5.putExtra("withdraw_time_msg",withdraw_time_msg	);
                startActivity(intent5);
                datePopWindow.dismiss();

                break;
            case R.id.shengji_zhanghu:
                Intent intent1 = new Intent(ZhangHuActivity.this, ShengJiTiXianActivity.class);
                try {
                    intent1.putExtra("yue", AmountUtils.changeF2Y(data.getSum_splitter2()) + "");
                    intent1.putExtra("sj_withdraw",AmountUtils.changeF2Y(sj_withdraw)	 + "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent1.putExtra("withdraw_msg",withdraw_msg	);

                intent1.putExtra("withdraw_amount_max",withdraw_amount_max);
                intent1.putExtra("withdraw_amount_min",withdraw_amount_min);
                intent1.putExtra("withdraw_time_msg",withdraw_time_msg	);
                startActivity(intent1);
                datePopWindow.dismiss();

                break;
            case R.id.quxiao:
                datePopWindow.dismiss();
                break;
            case R.id.tixian_mingxi:
                Intent intent8 = new Intent(this, BankTiXianDetailActivity.class);
//                intent8.putExtra("bank_name", bank_name);
                startActivity(intent8);
                break;
        }
    }

}
