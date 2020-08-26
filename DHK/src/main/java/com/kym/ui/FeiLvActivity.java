package com.kym.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.CourseAdapter;
import com.kym.ui.adapter.FeiLvListAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.bean.FeiLv_MyBean;
import com.kym.ui.info.CourseResponse;
import com.kym.ui.info.FeilvResponse;
import com.kym.ui.info.GouMaiQuanYi;
import com.kym.ui.util.AmountUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zzss.jindy.appconfig.Clone.OMID;


/**
 * 我的费率
 *
 * @author sun
 * @date 2019/12/3
 */

public class FeiLvActivity extends BaseActivity {
    @BindView(R.id.head_text_title)
    TextView headTextTitle;
    @BindView(R.id.head_img_left)
    ImageView headImgLeft;
 /*   @BindView(R.id.dengji)
    TextView dengji;*/
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.rate)
    TextView rate;
    @BindView(R.id.fee)
    TextView fee;
    @BindView(R.id.feilv_box)
    RecyclerView feilvBox;
    @BindView(R.id.zhiji)
    TextView zhiji;
    @BindView(R.id.jyjl)
    TextView jyjl;
    @BindView(R.id.ztjl)
    TextView ztjl;
    @BindView(R.id.tdjl)
    TextView tdjl;
    @BindView(R.id.rv_bank_list)
    RecyclerView rvBankList;
    private DialogUtil dialogUtil;
    private FeiLvListAdapter adapter;
    private CourseAdapter adapter1;
    private int i;
    private String level;
    private Button viewById;
    private ArrayList<FeiLv_MyBean.DataBean.RateListBean> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fei_lv);
        ButterKnife.bind(this);
//        getHomeYouHuiShengJi();
//        initHead();
        initView();
        getBaoBiao();
        getInfo();
    }

    private void initHead() {
//        viewById = findViewById(R.id.shengji1);
//    viewById.setVisibility(View.GONE);
//        viewById.setVisibility(View.GONE);
//        viewById.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (level.equals("新客户")){
//                    startActivity(new Intent(FeiLvActivity.this, UpGradeActivity.class));
//                }else if (level.equals("老客户")){
//                    startActivity(new Intent(FeiLvActivity.this,UpGradeActivity.class));
//
//
//                }
//            }
//        });
    }

   /* //新政策升级
    private void getHomeYouHuiShengJi() {
//        final DialogUtil dialogUtil = new DialogUtil(this);

        Connect.getInstance().post(FeiLvActivity.this, IService.HOME_YOUHUI, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
//                dialogUtil.dismiss();
                GouMaiQuanYi data1 = (GouMaiQuanYi) JsonUtils.parse((String) result, GouMaiQuanYi.class);
                if (data1.getResult().getCode() == 10000) {
//                    i = data1.getData().getPayment_list().get(0).getUpgrade_level();
                }
            }

            @Override
            public void onFailure(String message) {
//                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }*/

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        headTextTitle.setText("我的费率");

        feilvBox.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        adapter= new FeiLvListAdapter(this,arrayList);
        feilvBox.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        rvBankList.setLayoutManager(new LinearLayoutManager(this));
        adapter1 = new CourseAdapter(this, new CourseAdapter.OnCurse() {
            @Override
            public void kj_card_list(CourseResponse.CourseInfo courseInfo) {

            }
        });
        rvBankList.setAdapter(adapter1);
    }

    private void getBaoBiao() {
        dialogUtil = new DialogUtil(this);
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("uid", LoginActivity.uid);
        Connect.getInstance().post(this.getApplicationContext(), IService.FRILV_MY, paramx, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                FeiLv_MyBean response = (FeiLv_MyBean) JsonUtils.parse((String) result, FeiLv_MyBean.class);

                if (response.getResult().getCode() == 10000) {
                    if (response.getData()!=null){
                        String level_name = response.getData().getLevel_name();
                    /*    long profit_ratio = response.getData().getProfit_ratio();//团队
                        long profit_ratio_jt = response.getData().getProfit_ratio_jt();//交易
                        long profit_ratio_zt = response.getData().getProfit_ratio_zt();//直推*/
                        zhiji.setText(level_name);
                        try {
                            tdjl.setText(AmountUtils.round(response.getData().getProfit_ratio()*10)+"%");
                            jyjl.setText(AmountUtils.round(response.getData().getProfit_ratio_jt()*10)+"%");
                            ztjl.setText(AmountUtils.round(response.getData().getProfit_ratio_zt()*10)+"%");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    List<FeiLv_MyBean.DataBean.RateListBean> list = response.getData().getRate_list();
                    if (list!=null){
                        arrayList.addAll(list);
                        adapter.notifyDataSetChanged();

                    }


                /*    List<FeilvResponse.FeiLvInfo> data = response.getData();

                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                        level = data.get(0).getLevel();
                        dengji.setText(level);
                        if (level.equals("贵宾客户")){
                            viewById.setVisibility(View.GONE);

                        }
                        zhiji.setText(data.get(0).getLevel());
                        jyjl.setText(data.get(0).getReturn_ratio() + "%");
                        ztjl.setText(data.get(0).getDirect_ratio() + "%");
                        tdjl.setText(data.get(0).getProfits_ratio() + "%");
                    } else {
                        adapter.clearData();
                    }*/
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

    /**
     * 获取职级信息
     */
    private void getInfo() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "zj");
        params.put("mark", "ksi8wjk");
        Connect.getInstance().post(this.getApplicationContext(), IService.Course, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                CourseResponse response = (CourseResponse) JsonUtils.parse((String) result, CourseResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<CourseResponse.CourseInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter1.setData(data);
                    } else {
//                        adapter.clearData();
                        adapter.notifyDataSetChanged();

                    }
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

    @OnClick(R.id.head_img_left)
    public void onViewClicked() {
        finish();
    }
}
