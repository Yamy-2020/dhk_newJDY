package com.kym.ui.activity.zhanghu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;


import com.kym.ui.DoubleDatePickerDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.FenrundelAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.SplitterList;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.kym.ui.util.MarqueTextView;

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


public class HuoDongDetailListActivity extends BaseActivity implements View.OnClickListener {

    private ListViewFinal mLv;
    private PtrClassicFrameLayout mPtrLayout;
    private List<SplitterList.DataBean> allhotlist_shouru = new ArrayList<SplitterList.DataBean>();
    private FenrundelAdapter adapter_fen;
    private int mPage = 1;
    private DialogUtil loadDialogUtil;
    private String s_sat;
    private String s_end;
    private String type;
    private Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheng_ji_detail_list);
        initUI();
        initHead();
        getdata(mPage);
    }

    private void initUI() {
        mLv = (ListViewFinal) findViewById(R.id.lv);
        adapter_fen = new FenrundelAdapter(HuoDongDetailListActivity.this, allhotlist_shouru);
        mPtrLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_layout);
        mLv.setAdapter(adapter_fen);
        setSwipeRefreshInfo();
    }

    @SuppressLint("SetTextI18n")
    private void initHead() {
        c = Calendar.getInstance();
        Intent intent = getIntent();
        s_sat = intent.getStringExtra("s");
        s_end = intent.getStringExtra("e");
        String name = intent.getStringExtra("name");
        type = intent.getStringExtra("type");
        MarqueTextView mMarque = (MarqueTextView) findViewById(R.id.tv_marque);
        mMarque.setText("   交易产生的分润要大于0.01元才能收到分润款，低于0.01元银行无法打款，请用户谅解！");
        ImageView img_left = (ImageView) findViewById(R.id.head_img_left);
        ImageView img_right = (ImageView) findViewById(R.id.head_img_right);
        img_right.setBackgroundResource(R.drawable.ic_riqi_b);
        img_right.setVisibility(View.VISIBLE);
        img_left.setOnClickListener(this);
        img_right.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.head_text_title);
        tv_title.setText(name);
    }

    /**
     * 设置上拉下拉刷新
     */
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

    // 获取提现记录
    private void getdata(final int page) {

        loadDialogUtil = new DialogUtil(HuoDongDetailListActivity.this);
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        params.put("type", type);
        params.put("starttime", s_sat);
        params.put("endtime", s_end);
        Connect.getInstance().post(HuoDongDetailListActivity.this, IService.HUODONGFENRUNMINGXI, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                SplitterList response = (SplitterList) JsonUtils.parse((String) result, SplitterList.class);
                if (response.getResult().getCode() == 10000) {
                    if (response.getData() != null) {
                        mPage = page + 1;
                        allhotlist_shouru.addAll(response.getData());
                        adapter_fen.notifyDataSetChanged();
                        mPtrLayout.onRefreshComplete();
                        mLv.onLoadMoreComplete();
                    }
                } else {
                    if (page == 1) {
                        allhotlist_shouru.clear();
                    }
                    adapter_fen.notifyDataSetChanged();
                    mPtrLayout.onRefreshComplete();
                    mLv.onLoadMoreComplete();
                    ToastUtil.showTextToas(getApplicationContext(), "当前没有更多数据可以添加");
                }
                loadDialogUtil.dismiss();
            }

            @Override
            public void onFailure(String message) {
                loadDialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void test() {
        new DoubleDatePickerDialog(HuoDongDetailListActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth,
                                  DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth) {
                int stam = startMonthOfYear + 1;
                int endm = endMonthOfYear + 1;
                String textString_one = String.format("%d-%d-%d", startYear, stam, startDayOfMonth);
                String textString_two = String.format("%d-%d-%d", endYear, endm, endDayOfMonth);
                try {
                    s_sat = dateToStamp_three(textString_one);
                    s_end = dateToStamp_three(textString_two);
                    allhotlist_shouru.clear();
                    adapter_fen.notifyDataSetChanged();
                    getdata(1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.head_img_right:
                test();
                break;
        }
    }
}
