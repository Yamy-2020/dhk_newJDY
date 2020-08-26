package com.kym.ui.activity.daichang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.BackDialog3;
import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_home.bpbro_sk.KuaiJieTFTShanghuActivity;
import com.kym.ui.activity.rongxinfen.XinYong_GuiZe_Activity;
import com.kym.ui.activity.shiming.NewShiMingActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.KongKaDaiHuanAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.KongKaDaiInfo;
import com.kym.ui.util.Connect;
import com.kym.ui.util.JsonUtils;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

/**
 * 贷偿
 * Created by SunMiaoLong on 2018/1/30.
 */

public class KongKaDaiHuanFragment extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout dc_li1, dc_li2, dc_li3, dc_li4;
    private TextView more, money, btn;
    private LinearLayout zanwu;
    private KongKaDaiHuanAdapter adapter;
    private SPConfig spConfig;
    private boolean isGetData = false;
    private String score, statusMsg;
    private BackDialog backDialog;
    private int CARDLIST = 1;
    private BackDialog3 backDialog3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kongkadaihuan, container, false);
        initView();
        return view;
    }

    public void initView() {
        dc_li1 = (LinearLayout) view.findViewById(R.id.dc_li1);
        dc_li2 = (LinearLayout) view.findViewById(R.id.dc_li2);
        dc_li3 = (LinearLayout) view.findViewById(R.id.dc_li3);
        dc_li4 = (LinearLayout) view.findViewById(R.id.dc_li4);
        zanwu = (LinearLayout) view.findViewById(R.id.zanwu);
        money = (TextView) view.findViewById(R.id.money);
        btn = (TextView) view.findViewById(R.id.btn);
        btn.setOnClickListener(this);
        zanwu.setVisibility(View.GONE);
        more = (TextView) view.findViewById(R.id.more);
        more.setOnClickListener(this);
        dc_li1.setOnClickListener(this);
        dc_li2.setOnClickListener(this);
        dc_li3.setOnClickListener(this);
        dc_li4.setOnClickListener(this);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_bank_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new KongKaDaiHuanAdapter(getActivity(), new KongKaDaiHuanAdapter.OnKongKaInfo() {
            @Override
            public void kongkaClick(KongKaDaiInfo.KongKaInfo kongKaInfo) {
                Intent intent = new Intent(getActivity(), DaiChangCardPlanDetailActivity.class);
                intent.putExtra("bill_id", kongKaInfo.getBill_id());
                startActivity(intent);

            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CARDLIST){
            getAllPlanList();
            getMoney();
            getScore();
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
            getAllPlanList();
            getMoney();
            getScore();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dc_li1:
                if (canJump()) {
                    if (score.equals("YES_500")) {
                        startActivityForResult(new Intent(getActivity(), DaiChangBankCardListActivity.class),CARDLIST);
                    } else {
                        tipView(statusMsg);
                    }
                }
                break;
            case R.id.dc_li2:
                if (canJump()) {
                    if (score.equals("YES_500")) {
                        startActivity(new Intent(getActivity(), DaiChangPlanRecordActivity.class));
                    } else {
                        tipView(statusMsg);
                    }
                }
                break;
            case R.id.dc_li3:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), DaiChangPlanRulesActivity.class));
                }
                break;
            case R.id.dc_li4:
                if (canJump()) {
                    tipView("暂未开放,敬请期待");
                }
                break;
            case R.id.more:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), DaiChangPlanRecordActivity.class));
                }
                break;
            case R.id.btn:
                if (canJump()) {
                    if (btn.getText().toString().equals("提升信用")) {
                        startActivity(new Intent(getActivity(), XinYong_GuiZe_Activity.class));
                    } else {
                        startActivity(new Intent(getActivity(), DaiChangBankCardListActivity.class));
                    }
                }
                break;
        }
    }

    /**
     * 获取所有空卡代还信用卡
     */
    private void getAllPlanList() {
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("p", "1");
        Connect.getInstance().post(getActivity(), IService.DC_PLAN_LIST, paramx, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                KongKaDaiInfo response = (KongKaDaiInfo) JsonUtils.parse((String) result, KongKaDaiInfo.class);
                if (response.getResult().getCode() == 10000) {
                    List<KongKaDaiInfo.KongKaInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                        zanwu.setVisibility(View.GONE);
                    } else {
                        zanwu.setVisibility(View.VISIBLE);
                    }
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", getContext(),
                            R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getContext(), LoginActivity.class));

//                            restartApp(getContext());
                            backDialog.dismiss();
                        }
                    });
                    backDialog.setCancelable(false);
                    backDialog.show();
                } else {
                    ToastUtil.showTextToas(getContext(),response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getContext(),message);
            }
        });
    }

    /**
     * 获取所有空卡代还信用卡
     */
    private void getScore() {
        Connect.getInstance().post(getActivity(), IService.DC_SCORE, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    if (code.equals("10000")) {
                        String data = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data);
                        String statusName = obj2.getString("statusName");
                        if (statusName.equals("display_success")) {
                            score = "YES_500";
                        } else if (statusName.equals("display_error")) {
//                            score = "NO_500";
                            score = "YES_500";

//                            statusMsg = obj2.getString("statusMsg");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getContext(),message);
            }
        });
    }


    /**
     * 获取所有空卡代还信用卡
     */
    private void getMoney() {
        Connect.getInstance().post(getActivity(), IService.DC_GET_EDU, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    if (code.equals("10000")) {
                        String data = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data);
                        String money1 = obj2.getString("money");
                        if (money1.equals("0")) {
                            money.setText("暂不具备贷偿资格");
                            money.setTextSize(16);
                            btn.setText("提升信用");
                        } else {
                            int str = Integer.parseInt(money1) / 100;
                            String str1 = String.valueOf(str);
                            money.setText(bigDecimalMoney(str1));
                            btn.setText("立即还款");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
            }
        });
    }

    public static String bigDecimalMoney(String money) {
        BigDecimal bd = new BigDecimal(Integer.parseInt(money));
        DecimalFormat df = new DecimalFormat(",###,###.00");
        return df.format(bd);
    }

    private boolean canJump() {
        if (spConfig == null) {
            spConfig = SPConfig.getInstance(getActivity().getApplicationContext());
        }
        int status = spConfig.getUserInfoStatus();
        switch (status) {
            case 1:
                backDialog3 = new BackDialog3("确定", "取消", "提示", "请先完成实名认证", getActivity(), R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        backDialog3.dismiss();
                        switch (view.getId()) {
                            case R.id.textView2:
                                break;
                            case R.id.textView1:
                                startActivity(new Intent(getActivity(), NewShiMingActivity.class));
                                break;
                        }
                    }
                });
                backDialog3.setCancelable(false);
                backDialog3.show();
                return false;
            case 2:
                tipView("您的资料审核中,无法使用该功能");
                return false;
            case 3:
                return true;
            case 4:
                tipView("您的资料审核未通过,无法使用该功能");
                return false;
            default:
                return false;
        }
    }

    public void tipView(String msg) {
        backDialog = new BackDialog("", msg, "确定", getContext(),
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
