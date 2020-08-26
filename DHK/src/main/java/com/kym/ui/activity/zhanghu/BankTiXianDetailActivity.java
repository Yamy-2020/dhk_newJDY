package com.kym.ui.activity.zhanghu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.DoubleDatePickerDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.log;
import com.kym.ui.adapter.TiXianAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.bean.YongHuFenRunXiangQingBean;
import com.kym.ui.model.CashList;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.newutil.EmptyViewUtils;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.finalteam.loadingview.ListViewFinal;
import cn.finalteam.loadingview.OnDefaultRefreshListener;
import cn.finalteam.loadingview.OnLoadMoreListener;
import cn.finalteam.loadingview.PtrClassicFrameLayout;
import cn.finalteam.loadingview.PtrFrameLayout;

public class BankTiXianDetailActivity extends BaseActivity implements OnClickListener {
    private List<YongHuFenRunXiangQingBean.DataBean.ListBean> allhotlist_shouru = new ArrayList<>();
    private ListViewFinal mLv;
    private PtrClassicFrameLayout mPtrLayout;
    private int mPage = 1;
    private DialogUtil Loading;
    private TiXianAdapter adapter_fen;
    private TextView textV_txall;
    private Dialog dialog_tt;
    private TextView textV_time;
    private TextView tv_show;
    private Calendar c;
    private FrameLayout mFlEmptyView;
    private NewUserResponse.DataBean userAllInfoNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian_detail);
        userAllInfoNew = SPConfig.getInstance(BankTiXianDetailActivity.this).getUserAllInfoNew();
        initUI();
        getdata(1);
        c = Calendar.getInstance();
        TextView textV_title = findViewById(R.id.head_text_title);
        textV_title.setText("提现明细");
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView imageV_right = findViewById(R.id.head_img_right);
        tv_show = findViewById(R.id.textView2);
        tv_show.setText("累计提现(元)");
        imageV_right.setBackgroundResource(R.drawable.ic_riqi_b);
        imageV_right.setVisibility(View.GONE);
        imageV_right.setOnClickListener(this);
        textV_txall = findViewById(R.id.textV_txall);
        textV_time = findViewById(R.id.textV_time);
    }


    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void dialog_one(String bank, String acont, String addtime) {
        dialog_tt = new Dialog(BankTiXianDetailActivity.this, R.style.MyDialgoStyle_xin_x);
        Window window = dialog_tt.getWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.addpopwindow_tt, null);

        CircleImageView imageV_top = view.findViewById(R.id.ige_top);
        TextView textV_name = view.findViewById(R.id.textV_name_t);
        TextView textV_bank = view.findViewById(R.id.textV_bank_t);
        TextView textV_time = view.findViewById(R.id.textV_time_t);
        TextView textV_money = view.findViewById(R.id.textV_money_t);

        view.findViewById(R.id.layout_diss).setOnClickListener(this);
        Glide.with(this).load(userAllInfoNew.getHeadimage()).placeholder(R.drawable.default_image).error(R.drawable.default_image)
                .dontAnimate().into(imageV_top);

        textV_name.setText(userAllInfoNew.getName() + "");

        textV_time.setText(gettime_x(addtime) + "");

        textV_bank.setText(bank + "");

        textV_money.setText(acont + " 元");

        assert window != null;
        window.setContentView(view);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog_tt.show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.layout_diss:

                dialog_tt.dismiss();

                break;
            case R.id.head_img_right:
                test();
                break;

            default:
                break;
        }

    }

    private static String gettime_x(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long cc_time = Long.valueOf(string);

        return simpleDateFormat.format(new Date(cc_time * 1000L));
    }

    private void initUI() {
        mLv = (ListViewFinal) findViewById(R.id.lv);
        adapter_fen = new TiXianAdapter(BankTiXianDetailActivity.this, allhotlist_shouru);
        mLv.setAdapter(adapter_fen);

        mFlEmptyView = (FrameLayout) findViewById(R.id.fl_empty_view);
        mPtrLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_layout);

        mLv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//
                YongHuFenRunXiangQingBean.DataBean.ListBean info = allhotlist_shouru.get(arg2);
                if (info.getStatus().equals("3")) {
                    try {

                        dialog_one(info.getStatus_name(), AmountUtils.round(info.getWithdraw_amount()/100), info.getApply_time());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), "结算中");
                }

            }
        });
        setSwipeRefreshInfo();
    }

    private void setSwipeRefreshInfo() {
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {// 下拉刷新
                allhotlist_shouru.clear();
                getdata(1);
            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mLv.setOnLoadMoreListener(new OnLoadMoreListener() {// 上拉加载
            @Override
            public void loadMore() {
                getdata(mPage);
            }
        });
    }

    private void getdata(final int page) {

        Loading = new DialogUtil(BankTiXianDetailActivity.this);
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        Connect.getInstance().post(BankTiXianDetailActivity.this, IService.YONGHUFENXIANGPING, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                log.e("查询用户提现记录：" + result);
                YongHuFenRunXiangQingBean response = (YongHuFenRunXiangQingBean) JsonUtils.parse((String) result, YongHuFenRunXiangQingBean.class);
                if (response.getResult().getCode() == 10000) {
                    List<YongHuFenRunXiangQingBean.DataBean.ListBean> list = response.getData().getList();
                    textV_txall.setText(AmountUtils.round(response.getData().getSum_cash()/100));

                    if ( list!= null&&list.size()>0) {
                        allhotlist_shouru.addAll(list);
                        adapter_fen.notifyDataSetChanged();


                     /*   EmptyViewUtils.goneNoDataEmpty1(mFlEmptyView);
                        mPage = page + 1;
                        CashList.DataBean data = response.getData();
                        List<CashList.DataBean.CashListBean> cash_list = data.getCash_list();
                        allhotlist_shouru.addAll(cash_list);
                        if (allhotlist_shouru.size() > 0) {
                            String cash_sum = response.getData().getTotal_cash();
                            int i = Integer.parseInt(cash_sum);
                            try {
                                String s = AmountUtils.changeF2Y(Long.parseLong("" + i));
                                textV_txall.setText(s + " 元");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
*/

                    } else {
                        if (page == 1) {
                            EmptyViewUtils.showNoDataEmpty1(mFlEmptyView, "暂无数据");
                        }
                    }
                    Loading.dismiss();
                    adapter_fen.notifyDataSetChanged();
                    mPtrLayout.onRefreshComplete();
                    mLv.onLoadMoreComplete();
                } else {
                    if (page == 1) {
                        allhotlist_shouru.clear();
                        EmptyViewUtils.showNoDataEmpty1(mFlEmptyView, "暂无数据");
                    }
                    if (allhotlist_shouru.size() > 0) {
                      /*  String cash_sum = response.getData().getTotal_cash();
                        int i = Integer.parseInt(cash_sum);
                        try {
                            String s = AmountUtils.changeF2Y(Long.parseLong("" + i));
                            textV_txall.setText(s + " 元");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                    } else {
                        textV_txall.setText("0.00 元");
                    }
                    adapter_fen.notifyDataSetChanged();
                    mPtrLayout.onRefreshComplete();
                    mLv.onLoadMoreComplete();
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
                Loading.dismiss();
            }

            @Override
            public void onFailure(String message) {
                Loading.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void test() {
        new DoubleDatePickerDialog(BankTiXianDetailActivity.this, 0,
                new DoubleDatePickerDialog.OnDateSetListener() {
                    @SuppressLint({"DefaultLocale", "SetTextI18n"})
                    @Override
                    public void onDateSet(DatePicker startDatePicker,
                                          int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker,
                                          int endYear, int endMonthOfYear, int endDayOfMonth) {
                        int stam = startMonthOfYear + 1;
                        int endm = endMonthOfYear + 1;

                        String textString_one = String.format("%d-%d-%d",
                                startYear, stam, startDayOfMonth);
                        String textString_two = String.format("%d-%d-%d",
                                endYear, endm, endDayOfMonth);
                        textV_time.setText(textString_one + " —— " + textString_two);
                        allhotlist_shouru.clear();
                        getdata(1);
                        tv_show.setText("日期间累计提现");
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DATE), true).show();
    }


    @SuppressLint("SimpleDateFormat")
    public static String dateToStamp_three(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res.substring(0, 10);
    }

}
