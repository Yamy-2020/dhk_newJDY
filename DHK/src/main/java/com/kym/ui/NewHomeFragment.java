package com.kym.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.donkingliang.banner.CustomBanner;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.SheZhiActivity;
import com.kym.ui.bean.CornerTransform;
import com.kym.ui.bean.SwitchTextView;
import com.kym.ui.bean.XiaoXiBean;
import com.kym.ui.info.GouMaiQuanYi;
import com.kym.ui.sp.SharedPrefrenceUtils;
import com.kym.ui.util.JsonUtils;
import com.paradigm.botkit.BotKitClient;
import com.paradigm.botlib.VisitorInfo;
import com.kym.ui.activity.AllCardListActivity;
import com.kym.ui.activity.DingDanActivity;
import com.kym.ui.activity.bpbro_home.bpbro_hk.HK_Sb_planinfoActivity;
import com.kym.ui.activity.bpbro_real_name.Bpbro_Idcardid_Activity;
import com.kym.ui.activity.YeJiActivity;
import com.kym.ui.activity.fee_kf.ChatActivity;
import com.kym.ui.activity.bpbro_home.bpbro_sk.SK_BankList_Activity;
import com.kym.ui.activity.news.NewsActivity;
import com.kym.ui.activity.ShareActivity;
import com.kym.ui.activity.YouHuiActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.activity.tgsucai.TuiGuangWenAnActivity;
import com.kym.ui.activity.tgsucai.TuiGuangTuActivity;
import com.kym.ui.activity.bpbro_home.bpbro_yk.XiaoFeiActivity;
import com.kym.ui.activity.zhanghu.ZhangHuActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.dialog.ImageDialog;
import com.kym.ui.hualuo.HL_BK_WebActivity;
import com.kym.ui.hualuo.HL_HuanKuanActivity;
import com.kym.ui.hualuo.RotateRecyclerViewActivity;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.newutil.DragFloatActionButton;
import com.kym.ui.util.Connect;
import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;
import static com.tencent.plus.DensityUtil.dip2px;
import static com.zzss.jindy.appconfig.Clone.OMID;

/**
 * 首页
 *
 * @author sun
 * @date 2019/12/3
 */

public class NewHomeFragment extends Fragment implements View.OnClickListener {

//    private DragFloatActionButton circle_button;
        private LinearLayout circle_button;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CustomBanner<String> mBanner;
    private TextView   home5_text, sk_text, hk_text, xf_text, home8_text, home2_text_red, home4_text_red, home7_text_red;
    private boolean isGetData = false;
    private ArrayList<String> images;
    private BackDialog3 backDialog3;
    private BackResDialog backRedDialog;
    private ImageDialog imageDialog;
    private BackDialog backDialog;
    private LinearLayout home_yk;
    private ImageView ad_gif;
private SwitchTextView xx_text;
    private Intent intent;
    private View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_home_che_shi, null);
        }
        initView(rootView);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        getSamllRed();
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter && !isGetData) {
            isGetData = true;
            mBanner = rootView.findViewById(R.id.banner);


            getBanner();
            //首页中间的消息推送更新
            getXX();
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
                CornerTransform transformation = new CornerTransform(context, dip2px(context, 10));
                //只是绘制左上角和右上角圆角
                transformation.setExceptCorner(false, false, false, false);
                Glide.with(context).load(entity).asBitmap().skipMemoryCache(true).placeholder(R.drawable.shape_point_select).error(R.drawable.shape_point_select).transform(transformation).into((ImageView) view);
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


    @Override
    public void onClick(View v) {
        new Repeat().isFastClick();
        int id = v.getId();
        switch (id) {
            case R.id.home_sk:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), SK_BankList_Activity.class));

                    rootView.setEnabled(false);


                }
                break;
            case R.id.home_hk:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), HL_HuanKuanActivity.class));

                }
                break;
            case R.id.home_yk:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), XiaoFeiActivity.class));
                }
                break;
            case R.id.view_new_home_1:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), AllCardListActivity.class));
                }
                break;
            case R.id.view_new_home_2:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), ZhangHuActivity.class));
                }
                break;
            case R.id.view_new_home_3:
                if (canJump()) {
                    if (OMID.equals("X9FN9CEDKB0C9A43")) {
                        startActivity(new Intent(getActivity(), YouHuiActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), UpGradeActivity.class));
                    }
                }
                break;
            case R.id.view_new_home_4:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), YeJiActivity.class));
                }
                break;

            case R.id.view_new_home_6:
                if (canJump()) {
                    NewUserResponse.DataBean userInfo = SPConfig.getInstance(getContext()).getUserAllInfoNew();
                    intent = new Intent(getActivity(), ShareActivity.class);
                    intent.putExtra("shareUrl", userInfo.getSharelink());
                    intent.putExtra("showUrl", userInfo.getShowlink());
                    intent.putExtra("shareDesc", userInfo.getShareremark());
                    intent.putExtra("shareTitle", userInfo.getSharetitle());
                    intent.putExtra("share_bgimg", userInfo.getShare_bgimg());
                    startActivity(intent);
                }
                break;
            case R.id.view_new_home_7:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), DingDanActivity.class));
                }
                break;
            case R.id.view_new_home_8:
                if (canJump()) {
                        intent = new Intent(getActivity(), HL_BK_WebActivity.class);
                        intent.putExtra("WEB_TITLE", "系统合作");
                        intent.putExtra("WEB_URL", Clone.HOST + "Oem/bpbro_index.html");


                    startActivity(intent);

                }
                break;
            case R.id.li_course:
                startActivity(new Intent(getActivity(), CourseActivity.class));
                break;
            case R.id.li_problem:
                startActivity(new Intent(getActivity(), RotateRecyclerViewActivity.class));
                break;
            case R.id.more:
                startActivity(new Intent(getActivity(), NewsActivity.class));
                break;
            case R.id.circle_button:
                if (canJump()) {
                    VisitorInfo info = new VisitorInfo();
                    info.nickName = Clone.APP_NAME + "_" + SPConfig.getInstance(getActivity()).getUserAllInfoNew().getName() + "_" + SPConfig.getInstance(getActivity()).getUserAllInfoNew().getUid();
                    info.userName = SPConfig.getInstance(getActivity()).getUserAllInfoNew().getName();
                    info.phone = SPConfig.getInstance(getActivity()).getUserAllInfoNew().getMobile();
                    BotKitClient.getInstance().setVisitor(info);
                    BotKitClient.getInstance().setPortraitUser(getResources().getDrawable(R.drawable.image_home));
//                    BotKitClient.getInstance().setPortraitRobot(getResources().getDrawable(R.drawable.tianjia));
                    startActivity(new Intent(getActivity(), ChatActivity.class));
                }
                break;
            case R.id.li_img:
                if (canJump()) {

                    intent = new Intent(getActivity(), TuiGuangTuActivity.class);
                    startActivity(intent);

                }
                break;
            case R.id.li_text:
                if (canJump()) {
                    intent = new Intent(getActivity(), TuiGuangWenAnActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ad_gif:
                intent = new Intent(getActivity(), PublicWebActivity.class);
                intent.putExtra("WEB_TITLE", SPConfig.getInstance(getContext()).getUserAllInfoNew().getAd_title());
                intent.putExtra("WEB_URL", SPConfig.getInstance(getContext()).getUserAllInfoNew().getAd_to_url());
                intent.putExtra("WX_URL", SPConfig.getInstance(getContext()).getUserAllInfoNew().getAd_to_url() + "?reurl=" + SPConfig.getInstance(getContext()).getUserAllInfoNew().getYuyue_url());
                intent.putExtra("RIGHT", "SHARE-ONLYWX");
                intent.putExtra("WX_TITLE", SPConfig.getInstance(getContext()).getUserAllInfoNew().getYuyue_title());
                intent.putExtra("PIC", "APP_IMG");
                intent.putExtra("WX_CONTENT", SPConfig.getInstance(getContext()).getUserAllInfoNew().getYuyue_introduce());
                startActivity(intent);
                break;
        }
    }

    private void initView(View rootView) {
        if (OMID.equals("1H1AJD6SLKVADDM6")) {
            if (SPConfig.getInstance(getContext()).getUserAllInfoNew().getIs_ad().equals("1")) {
                imageDialog = new ImageDialog(getContext(),
                        R.style.Theme_Dialog_Scale, new ImageDialog.DialogClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.dc_img:
                                intent = new Intent(getActivity(), PublicWebActivity.class);
                                intent.putExtra("WEB_TITLE", SPConfig.getInstance(getContext()).getUserAllInfoNew().getAd_title());
                                intent.putExtra("WEB_URL", SPConfig.getInstance(getContext()).getUserAllInfoNew().getAd_to_url());
                                intent.putExtra("WX_URL", SPConfig.getInstance(getContext()).getUserAllInfoNew().getAd_to_url() + "?reurl=" + SPConfig.getInstance(getContext()).getUserAllInfoNew().getYuyue_url());
                                intent.putExtra("RIGHT", "SHARE-ONLYWX");
                                intent.putExtra("WX_TITLE", SPConfig.getInstance(getContext()).getUserAllInfoNew().getYuyue_title());
                                intent.putExtra("PIC", "APP_IMG");
                                intent.putExtra("WX_CONTENT", SPConfig.getInstance(getContext()).getUserAllInfoNew().getYuyue_introduce());
                                startActivity(intent);
                                imageDialog.dismiss();
                                break;
                            case R.id.close:
                                imageDialog.dismiss();
                                break;
                        }
                    }
                });
                imageDialog.setCancelable(false);
                imageDialog.show();
            }
        }

//        home5_text = this.rootView.findViewById(R.id.home5_text);
        home8_text = this.rootView.findViewById(R.id.home8_text);
        home2_text_red = this.rootView.findViewById(R.id.home2_text_red);
        home4_text_red = this.rootView.findViewById(R.id.home4_text_red);
        home7_text_red = this.rootView.findViewById(R.id.home7_text_red);
        sk_text = this.rootView.findViewById(R.id.sk_text);
        hk_text = this.rootView.findViewById(R.id.hk_text);
        xf_text = this.rootView.findViewById(R.id.xf_text);
        if (OMID.equals("1H1AJD6SLKVADDM6")) {
            home5_text.setText("在线客服");
            home8_text.setText("我要OEM");
            sk_text.setText("快收款");
            hk_text.setText("好还款");
            xf_text.setText("精养卡");
        }

        if (OMID.equals("rd500ZbaNVcKVr8g")) {
            home8_text.setText("系统合作");
        }
        if (OMID.equals("rd500ZbaNVcKVr8g")) {
            home5_text.setText("添加客服");
        }
        rootView.findViewById(R.id.view_new_home_1).setOnClickListener(this);
        rootView.findViewById(R.id.view_new_home_2).setOnClickListener(this);
        rootView.findViewById(R.id.view_new_home_3).setOnClickListener(this);
       rootView.findViewById(R.id.view_new_home_4).setOnClickListener(this);
//        rootView.findViewById(R.id.view_new_home_5).setOnClickListener(this);
        rootView.findViewById(R.id.view_new_home_6).setOnClickListener(this);
        rootView.findViewById(R.id.view_new_home_7).setOnClickListener(this);
        rootView.findViewById(R.id.view_new_home_8).setOnClickListener(this);
        rootView.findViewById(R.id.li_problem).setOnClickListener(this);
        rootView.findViewById(R.id.li_course).setOnClickListener(this);
        rootView.findViewById(R.id.home_sk).setOnClickListener(this);
        rootView.findViewById(R.id.home_hk).setOnClickListener(this);
        rootView.findViewById(R.id.more).setOnClickListener(this);
        home_yk = this.rootView.findViewById(R.id.home_yk);
//        ad_gif = this.rootView.findViewById(R.id.ad_gif);
        //        rootView.findViewById(R.id.li_text).setOnClickListener(this);
//        rootView.findViewById(R.id.li_img).setOnClickListener(this);

//        ad_gif.setOnClickListener(this);
       /* if (SPConfig.getInstance(getContext()).getUserAllInfoNew().getIs_ad().equals("1")) {
            ad_gif.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load("http://www.bpbro.com/h5/ad_img/ad_img.gif").diskCacheStrategy(DiskCacheStrategy.ALL).into(ad_gif);
        } else {
            ad_gif.setVisibility(View.GONE);
        }*/
        home_yk.setOnClickListener(this);
        xx_text = rootView.findViewById(R.id.xx_text);
//        xx_time = rootView.findViewById(R.id.xx_time);
//        rootView.findViewById(R.id.textView).setVisibility(View.GONE);
//        circle_button = rootView.findViewById(R.id.circle_button);
//        circle_button.setOnClickListener(this);
        if (OMID.equals("E1TDVFFY8JX3RY62")) {
            circle_button.setVisibility(View.GONE);
        }
//        NewbieGuide.with(this)
//                .setLabel("page")
//                .setOnGuideChangedListener(new OnGuideChangedListener() {
//                    @Override
//                    public void onShowed(Controller controller) {
//
//                    }
//
//                    @Override
//                    public void onRemoved(Controller controller) {
//
//                    }
//                })
//                .setOnPageChangedListener(new OnPageChangedListener() {
//
//                    @Override
//                    public void onPageChanged(int page) {
//
//                    }
//                })
//                .alwaysShow(false)
//                .addGuidePage(
//                        GuidePage.newInstance()
//                                .addHighLight(jyl)
//                                .setLayoutRes(R.layout.view_guide_simple)
//                )
//                .addGuidePage(
//                        GuidePage.newInstance()
//                                .addHighLight(tgsc)
//                                .setLayoutRes(R.layout.view_guide_simple2)
//                )
//                .addGuidePage(
//                        GuidePage.newInstance()
//                                .addHighLight(circle_button)
//                                .setLayoutRes(R.layout.view_guide_simple3)
//                )
//                .show();
    }

    private boolean canJump() {

        int status = SPConfig.getInstance(getContext()).getUserInfoStatus();
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
                                startActivity(new Intent(getActivity(), Bpbro_Idcardid_Activity.class));
                                break;
                        }
                    }
                });
                backDialog3.setCancelable(false);
                backDialog3.show();
                return false;
            case 2:
                ToastUtil.showTextToas(getContext(), "您的资料审核中,无法使用该功能");
                return false;
            case 3:
                return true;
            case 4:
                ToastUtil.showTextToas(getContext(), "您的资料审核未通过,无法使用该功能");
                return false;
            default:
                return false;
        }
    }


    /**
     * 获取轮播图
     */
    private void getBanner() {
        Connect.getInstance().post(getActivity().getApplicationContext(), IService.HOME_BANNER, null, new Connect.OnResponseListener() {
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

                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", getContext(),
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getContext(), LoginActivity.class));

                             //   restartApp(getContext()); //退出app
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        ToastUtil.showTextToas(getActivity(), obj_result.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getActivity(), message);
            }
        });
    }

    private void getXX() {
        HashMap<String, String> params = new HashMap<>();
        params.put("p", "1");
        params.put("type", "");
        params.put("starttime", "");
        params.put("endtime", "");
        Connect.getInstance().post(getActivity().getApplicationContext(), IService.SPLITTERLIST, params, new Connect.OnResponseListener() {

            private String addtime,s;

            @Override
            public void onSuccess(Object result) {
                try {
                    XiaoXiBean data1 = (XiaoXiBean) JsonUtils.parse((String) result, XiaoXiBean.class);
                    if (data1.getResult().getCode()==10000) {
                        int position = 0;
                        int len;
                        for (int i = 0; i < data1.getData().size(); i++) {
                            addtime = data1.getData().get(i).getAddtime();

                             s = data1.getData().get(i).getText();
                            xx_text.setText(s);

                        }
                        len = data1.getData().size();

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
//                                position++;
                               xx_text.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        xx_text.setText(data1.getData().get(position%len).getText());
                                    }
                                });
                            }
                        }, 2000, 2000);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getActivity(), message);
            }
        });
    }


    private void getSamllRed() {
        Connect.getInstance().post(getActivity().getApplicationContext(), IService.SMALLRED, null, new Connect.OnResponseListener() {
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
                        home2_text_red.setText(obj2.getString("fenrunzhanghu"));
                        home4_text_red.setText(obj2.getString("yejiguanli"));
                        home7_text_red.setText(obj2.getString("dingdanguanli"));
                        String failplans = obj2.get("failplans").toString();
                        JSONObject obj3 = new JSONObject(failplans);
                        String repaymentPlanFails = obj3.getString("repaymentPlanFails");
                        Log.d("repaymentPlanFails", repaymentPlanFails);
                        if (!repaymentPlanFails.equals("0")) {
                            backRedDialog = new BackResDialog("温馨提示", "您有还款失败订单请及时处理", "确定", getContext(),
                                    R.style.Theme_Dialog_Scale, new BackResDialog.DialogClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getContext(), HK_Sb_planinfoActivity.class);
                                    intent.putExtra("type", "all");
                                    startActivity(intent);
                                    backRedDialog.dismiss();
                                }
                            });
                            backRedDialog.setCancelable(false);
                            backRedDialog.show();
                        }
                        home2_text_red.setVisibility(View.VISIBLE);
                        home4_text_red.setVisibility(View.VISIBLE);
                        home7_text_red.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getActivity(), message);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        backDialog = null;
        backDialog3 = null;
    }


}