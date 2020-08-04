package com.kym.ui.activity.zhanghu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.jock.pickerview.view.TimePickerView;
import com.kym.ui.BackDialog;
import com.kym.ui.DateUtils;
import com.kym.ui.FenRunActivity;
import com.kym.ui.R;
import com.kym.idcard.GsonUtils;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.NewDetailAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.newutil.NewDetail;
import com.kym.ui.newutil.NewDetailDatum;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.finalteam.loadingview.ListViewFinal;
import cn.finalteam.loadingview.OnDefaultRefreshListener;
import cn.finalteam.loadingview.OnLoadMoreListener;
import cn.finalteam.loadingview.PtrClassicFrameLayout;
import cn.finalteam.loadingview.PtrFrameLayout;

import static com.zzss.jindy.appconfig.Clone.OMID;


/**
 * 分润收益明细
 *
 * @author sun
 * @date 2019/12/3
 */

public class NewDetailActivity extends BaseActivity implements OnClickListener {
    private List<NewDetailDatum> data = new ArrayList<>();
    private ListViewFinal mLv;
    private PtrClassicFrameLayout mPtrLayout;
    private NewDetailAdapter newDetailAdapter;
    private int request_type;
    private int money = 0;
    private TextView textView3;
    private int flag = 0;
    private TimePickerView pvTime;
    private long start_time = 1;
    private long end_time = 1;
    private TextView tv_time_before;
    private TextView tv_time_after;
    private TextView head_title;
    private DialogUtil loadDialogUti;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);
        initHead();
        initUI();
        RequestData("", "");
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void initHead() {
        head_title = (TextView) findViewById(R.id.head_text_title);
        head_title.setVisibility(View.VISIBLE);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView textV_title = (TextView) findViewById(R.id.head_text_title);
        textV_title.setVisibility(View.VISIBLE);
        textV_title.setText("还款收益明细");
    }

    private void initUI() {
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        // 时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                switch (flag) {
                    case 0:
                        break;
                    case 1:
                        tv_time_before.setText(getTime(date));
                        start_time = (DateUtils.getStringToDate1(getTime(date)));
                        break;
                    case 2:
                        tv_time_after.setText(getTime(date));
                        end_time = DateUtils.getStringToDate1(getTime(date)) + 86400000;
                        if (start_time == 1) {
                            tipView("1", "请选择开始时间");
                        } else if (start_time > end_time) {
                            tipView("1", "结束时间必须大于开始时间");
                        } else {
                            // Scr(start_time,end_time);//筛选
                            RequestData(start_time / 1000 + "", end_time / 1000 + "");
                        }
                        break;

                    default:
                        break;
                }
            }

        });
        tv_time_before = (TextView) findViewById(R.id.textView1);
        // DateUtils.getDateToString5(Long.parseLong(datum.getAddtime()) *
        // 1000);
        tv_time_before.setOnClickListener(this);
        tv_time_after = (TextView) findViewById(R.id.textView2);
        tv_time_after.setOnClickListener(this);
        tv_time_after.setText(DateUtils.getCurrentDate1());
        String currentDate1 = DateUtils.getCurrentDate1();
        long stringToDate = DateUtils.getStringToDate(currentDate1);
        tv_time_before.setText(DateUtils.getDateToString8(stringToDate - 86400 * 1000 * 6));
        Intent intent = getIntent();
        request_type = intent.getIntExtra("type", 0);
        switch (request_type) {
            case 4:
                head_title.setText("还款收益明细");
                break;
            case 5:
                head_title.setText("推广收益明细");
                break;
            case 6:
                head_title.setText("收款收益明细");
                break;
            case 7:
                head_title.setText("贷偿收益明细");
                break;
            case 8:
                if (OMID.equals("VIB0T31Q2L7DNDK5")) {
                    head_title.setText("办卡分润明细");
                } else {
                    head_title.setText("升级收益明细");
                }

                break;
            case 9:
                head_title.setText("活动收益明细");
                break;
            case 10:
                head_title.setText("扫码收益明细");
                break;
            default:
                break;
        }
        newDetailAdapter = new NewDetailAdapter(this, data, request_type);
        mLv = findViewById(R.id.lv);
        mPtrLayout = findViewById(R.id.ptr_layout);
        textView3 = findViewById(R.id.textView3);
        mLv.setAdapter(newDetailAdapter);
       /* mLv.setOnItemClickListener(new OnItemClickListener() {


            //详情页
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (request_type) {
                    case 4:
                        String addtime1s = data.get(position).getAddtime();
                        int parseInt1s = Integer.parseInt(addtime1s);
                        Intent fenrundel_intent1 = new Intent(NewDetailActivity.this, FenRunActivity.class);
                        fenrundel_intent1.putExtra("name", "还款收益明细");
                        fenrundel_intent1.putExtra("type", "");
                        fenrundel_intent1.putExtra("is_splittertype", "8");
                        fenrundel_intent1.putExtra("is_fix_qrcode", "");
                        fenrundel_intent1.putExtra("s", data.get(position).getAddtime() + "");
                        fenrundel_intent1.putExtra("e", parseInt1s + 86400 + "");
                        startActivity(fenrundel_intent1);
                        break;
                    case 5:
                        String add_time1s = data.get(position).getAddtime();
                        int parse_Int1s = Integer.parseInt(add_time1s);
                        Intent intent2 = new Intent(NewDetailActivity.this, FanXianDetailActivity_x.class);
                        intent2.putExtra("name", "推广收益明细");
                        intent2.putExtra("type", "");
                        intent2.putExtra("is_splittertype", "8");
                        intent2.putExtra("is_fix_qrcode", "");
                        intent2.putExtra("s", data.get(position).getAddtime() + "");
                        intent2.putExtra("e", parse_Int1s + 86400 + "");
                        startActivity(intent2);
                        break;
                    case 6:
                        String add_time2s = data.get(position).getAddtime();
                        int parse_Int2s = Integer.parseInt(add_time2s);
                        Intent intent3 = new Intent(NewDetailActivity.this, ShouKuanDetailActivity.class);
                        intent3.putExtra("name", "收款收益明细");
                        intent3.putExtra("type", "");
                        intent3.putExtra("is_splittertype", "8");
                        intent3.putExtra("is_fix_qrcode", "");
                        intent3.putExtra("s", data.get(position).getAddtime() + "");
                        intent3.putExtra("e", parse_Int2s + 86400 + "");
                        startActivity(intent3);
                        break;
                    case 7:
                        String add_time3s = data.get(position).getAddtime();
                        int parse_Int3s = Integer.parseInt(add_time3s);
                        Intent intent4 = new Intent(NewDetailActivity.this, DaiChangDetailActivity.class);
                        intent4.putExtra("name", "贷偿收益明细");
                        intent4.putExtra("type", "");
                        intent4.putExtra("is_splittertype", "8");
                        intent4.putExtra("is_fix_qrcode", "");
                        intent4.putExtra("s", data.get(position).getAddtime() + "");
                        intent4.putExtra("e", parse_Int3s + 86400 + "");
                        startActivity(intent4);
                        break;
                    case 8:
                        String add_time4s = data.get(position).getAddtime();
                        int parse_Int4s = Integer.parseInt(add_time4s);
                        Intent intent5 = new Intent(NewDetailActivity.this, ShengJiDetailActivity.class);
                        head_title.setText("升级收益明细");
                        intent5.putExtra("type", "");
                        intent5.putExtra("is_splittertype", "8");
                        intent5.putExtra("is_fix_qrcode", "");
                        intent5.putExtra("s", data.get(position).getAddtime() + "");
                        intent5.putExtra("e", parse_Int4s + 86400 + "");
                        startActivity(intent5);
                        break;
                    default:
                        break;
                }
            }
        });*/
        setSwipeRefreshInfo();
    }

    /**
     * 设置上拉下拉刷新
     */
    private void setSwipeRefreshInfo() {
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {// 下拉刷新
                mPtrLayout.onRefreshComplete();
                mLv.onLoadMoreComplete();
            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mLv.setOnLoadMoreListener(new OnLoadMoreListener() {// 上拉加载
            @Override
            public void loadMore() {
            }
        });
    }

    private void RequestData(String s, String e) {
        switch (request_type) {
            case 4:
                loadDialogUti = new DialogUtil(this);
                Connect.getInstance().post(NewDetailActivity.this, IService.SPLITTER_DETAIL, null, new Connect.OnResponseListener() {
                    @Override
                    public void onSuccess(Object result) {
                        data.clear();
                        NewDetail nd = GsonUtils.parseJSON((String) result, NewDetail.class);
                        if (nd.getResult().getCode() == 10000) {
                            data.addAll(nd.getData());
                            for (int i = 0; i < data.size(); i++) {
                                String totalMoney = data.get(i).getTotalMoney();
                                int parseInt2 = Integer.parseInt(totalMoney);
                                money = money + parseInt2;
                            }
                            try {
                                textView3.setText("¥" + AmountUtils.changeF2Y((long) money) + "");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (data != null && data.size() > 1) {

                                String addtime = data.get(0).getAddtime();
                                String addtime1 = data.get(data.size() - 1).getAddtime();
                                long l = Long.parseLong(addtime);
                                long l1 = Long.parseLong(addtime1);
                                tv_time_before.setText(DateUtils.getDateToString8(l1 * 1000));
                                tv_time_after.setText(DateUtils.getDateToString8(l * 1000));

                            }
                            newDetailAdapter.notifyDataSetChanged();
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        } else {
                            data.clear();
                            newDetailAdapter.notifyDataSetChanged();
                            ToastUtil.showTextToas(getApplicationContext(), nd.getResult().getMsg());
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        }
                        loadDialogUti.dismiss();
                    }

                    @Override
                    public void onFailure(String message) {
                        loadDialogUti.dismiss();
                        ToastUtil.showTextToas(getApplicationContext(), message);
                    }
                });
                break;
            case 5:
                loadDialogUti = new DialogUtil(this);
                Connect.getInstance().post(NewDetailActivity.this, IService.SPLITTER_BACK_DETAIL, null, new Connect.OnResponseListener() {
                    @Override
                    public void onSuccess(Object result) {
                        data.clear();
                        NewDetail nd = GsonUtils.parseJSON((String) result, NewDetail.class);
                        if (nd.getResult().getCode() == 10000) {
                            data.addAll(nd.getData());
                            for (int i = 0; i < data.size(); i++) {
                                String totalMoney = data.get(i).getTotal_cash_money();
                                int parseInt2 = Integer.parseInt(totalMoney);
                                money = money + parseInt2;
                            }
                            try {
                                textView3.setText("¥" + AmountUtils.changeF2Y((long) money) + "");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (data != null && data.size() > 1) {
                                String addtime = data.get(0).getAddtime();
                                String addtime1 = data.get(data.size() - 1).getAddtime();
                                long l = Long.parseLong(addtime);
                                long l1 = Long.parseLong(addtime1);
                                tv_time_before.setText(DateUtils.getDateToString8(l1 * 1000));
                                tv_time_after.setText(DateUtils.getDateToString8(l * 1000));
                            }
                            newDetailAdapter.notifyDataSetChanged();
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        } else {
                            data.clear();
                            newDetailAdapter.notifyDataSetChanged();
                            ToastUtil.showTextToas(getApplicationContext(), nd.getResult().getMsg());
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        }
                        loadDialogUti.dismiss();
                    }

                    @Override
                    public void onFailure(String message) {
                        loadDialogUti.dismiss();
                        ToastUtil.showTextToas(getApplicationContext(), message);
                    }
                });
                break;
            case 6:
                loadDialogUti = new DialogUtil(this);
                Connect.getInstance().post(NewDetailActivity.this, IService.SPLITTER_SHOUKUAN_DETAIL, null, new Connect.OnResponseListener() {
                    @Override
                    public void onSuccess(Object result) {
                        data.clear();
                        NewDetail nd = GsonUtils.parseJSON((String) result, NewDetail.class);
                        if (nd.getResult().getCode() == 10000) {
                            data.addAll(nd.getData());
                            for (int i = 0; i < data.size(); i++) {
                                String totalMoney = data.get(i).getTotal_receive_money();
                                int parseInt2 = Integer.parseInt(totalMoney);
                                money = money + parseInt2;
                            }
                            try {
                                textView3.setText("¥" + AmountUtils.changeF2Y((long) money) + "");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (data != null && data.size() > 1) {

                                String addtime = data.get(0).getAddtime();
                                String addtime1 = data.get(data.size() - 1).getAddtime();
                                long l = Long.parseLong(addtime);
                                long l1 = Long.parseLong(addtime1);
                                tv_time_before.setText(DateUtils.getDateToString8(l1 * 1000));
                                tv_time_after.setText(DateUtils.getDateToString8(l * 1000));

                            }
                            newDetailAdapter.notifyDataSetChanged();
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        } else {
                            data.clear();
                            newDetailAdapter.notifyDataSetChanged();
                            ToastUtil.showTextToas(getApplicationContext(), nd.getResult().getMsg());
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        }
                        loadDialogUti.dismiss();
                    }

                    @Override
                    public void onFailure(String message) {
                        loadDialogUti.dismiss();
                        ToastUtil.showTextToas(getApplicationContext(), message);
                    }
                });
                break;
            case 7:
                loadDialogUti = new DialogUtil(this);
                Connect.getInstance().post(NewDetailActivity.this, IService.DAICHANG_MINGXI, null, new Connect.OnResponseListener() {
                    @Override
                    public void onSuccess(Object result) {
                        data.clear();
                        NewDetail nd = GsonUtils.parseJSON((String) result, NewDetail.class);
                        if (nd.getResult().getCode() == 10000) {
                            data.addAll(nd.getData());
                            for (int i = 0; i < data.size(); i++) {
                                String totalMoney = data.get(i).getTotal_kade_money();
                                int parseInt2 = Integer.parseInt(totalMoney);
                                money = money + parseInt2;
                            }
                            try {
                                textView3.setText("¥" + AmountUtils.changeF2Y((long) money) + "");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (data != null && data.size() > 1) {

                                String addtime = data.get(0).getAddtime();
                                String addtime1 = data.get(data.size() - 1).getAddtime();
                                long l = Long.parseLong(addtime);
                                long l1 = Long.parseLong(addtime1);
                                tv_time_before.setText(DateUtils.getDateToString8(l1 * 1000));
                                tv_time_after.setText(DateUtils.getDateToString8(l * 1000));

                            }
                            newDetailAdapter.notifyDataSetChanged();
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        } else {
                            data.clear();
                            newDetailAdapter.notifyDataSetChanged();
                            ToastUtil.showTextToas(getApplicationContext(), nd.getResult().getMsg());
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        }
                        loadDialogUti.dismiss();
                    }

                    @Override
                    public void onFailure(String message) {
                        loadDialogUti.dismiss();
                        ToastUtil.showTextToas(getApplicationContext(), message);
                    }
                });
                break;
            case 8:
                loadDialogUti = new DialogUtil(this);
                Connect.getInstance().post(NewDetailActivity.this, IService.SHENGJI_MINGXI, null, new Connect.OnResponseListener() {
                    @Override
                    public void onSuccess(Object result) {
                        data.clear();
                        NewDetail nd = GsonUtils.parseJSON((String) result, NewDetail.class);
                        if (nd.getResult().getCode() == 10000) {
                            data.addAll(nd.getData());
                            for (int i = 0; i < data.size(); i++) {
                                String totalMoney = data.get(i).getTotal_update_money();
                                int parseInt2 = Integer.parseInt(totalMoney);
                                money = money + parseInt2;
                            }
                            try {
                                textView3.setText("¥" + AmountUtils.changeF2Y((long) money) + "");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (data != null && data.size() > 1) {

                                String addtime = data.get(0).getAddtime();
                                String addtime1 = data.get(data.size() - 1).getAddtime();
                                long l = Long.parseLong(addtime);
                                long l1 = Long.parseLong(addtime1);
                                tv_time_before.setText(DateUtils.getDateToString8(l1 * 1000));
                                tv_time_after.setText(DateUtils.getDateToString8(l * 1000));
                            }
                            newDetailAdapter.notifyDataSetChanged();
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        } else {
                            data.clear();
                            newDetailAdapter.notifyDataSetChanged();
                            ToastUtil.showTextToas(getApplicationContext(), nd.getResult().getMsg());
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        }
                        loadDialogUti.dismiss();
                    }

                    @Override
                    public void onFailure(String message) {
                        loadDialogUti.dismiss();
                        ToastUtil.showTextToas(getApplicationContext(), message);
                    }
                });
                break;
            case 9:
                loadDialogUti = new DialogUtil(this);
                Connect.getInstance().post(NewDetailActivity.this, IService.HUODONG_MINGXI, null, new Connect.OnResponseListener() {
                    @Override
                    public void onSuccess(Object result) {
                        data.clear();
                        NewDetail nd = GsonUtils.parseJSON((String) result, NewDetail.class);
                        if (nd.getResult().getCode() == 10000) {
                            data.addAll(nd.getData());
                            for (int i = 0; i < data.size(); i++) {
                                String totalMoney = data.get(i).getTotal_active_cash_money();
                                int parseInt2 = Integer.parseInt(totalMoney);
                                money = money + parseInt2;
                            }
                            try {
                                textView3.setText("¥" + AmountUtils.changeF2Y((long) money) + "");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (data != null && data.size() > 1) {

                                String addtime = data.get(0).getAddtime();
                                String addtime1 = data.get(data.size() - 1).getAddtime();
                                long l = Long.parseLong(addtime);
                                long l1 = Long.parseLong(addtime1);
                                tv_time_before.setText(DateUtils.getDateToString8(l1 * 1000));
                                tv_time_after.setText(DateUtils.getDateToString8(l * 1000));
                            }
                            newDetailAdapter.notifyDataSetChanged();
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        } else {
                            data.clear();
                            newDetailAdapter.notifyDataSetChanged();
                            ToastUtil.showTextToas(getApplicationContext(), nd.getResult().getMsg());
                            mPtrLayout.onRefreshComplete();
                            mLv.onLoadMoreComplete();
                        }
                        loadDialogUti.dismiss();
                    }

                    @Override
                    public void onFailure(String message) {
                        loadDialogUti.dismiss();
                        ToastUtil.showTextToas(getApplicationContext(), message);
                    }
                });
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.textView1:
                flag = 1;
                pvTime.show();
                break;
            case R.id.textView2:
                flag = 2;
                pvTime.show();
                break;
            default:
                break;
        }
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", NewDetailActivity.this,
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
