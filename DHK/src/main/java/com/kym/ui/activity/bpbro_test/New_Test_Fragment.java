package com.kym.ui.activity.bpbro_test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.paradigm.botkit.BotKitClient;
import com.paradigm.botlib.VisitorInfo;
import com.kym.ui.BackDialog;
import com.kym.ui.Course1Activity;
import com.kym.ui.R;
import com.kym.ui.ShaiXunActivity;
import com.kym.ui.activity.fee_kf.ChatActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.activity.tgsucai.TuiGuangTuDetailsActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.hualuo.HL_BK_WebActivity;
import com.kym.ui.util.Connect;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;


/**
 * 测试页面
 *
 * @author sun
 * @date 2019/12/26
 */

public class New_Test_Fragment extends Fragment {

    @BindView(R.id.banner)
    CustomBanner banner;
    @BindView(R.id.view_new_home_1)
    View viewNewHome1;
    @BindView(R.id.view_new_home_2)
    View viewNewHome2;
    @BindView(R.id.view_new_home_3)
    View viewNewHome3;
    @BindView(R.id.view_new_home_4)
    View viewNewHome4;
    @BindView(R.id.li_course)
    LinearLayout liCourse;
    @BindView(R.id.li_problem)
    LinearLayout liProblem;
    Unbinder unbinder;
    private View rootView;
    private ArrayList<String> images;
    private BackDialog backDialog;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_test, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getBanner();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 设置轮播图参数
     *
     * @param beans
     */
    private void setBean(final ArrayList<String> beans) {
        banner.setPages(new CustomBanner.ViewCreator<String>() {
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

    /**
     * 获取轮播图
     */
    private void getBanner() {
        Connect.getInstance().post(getActivity().getApplicationContext(), IService.HOME_TEST_BANNER, null, new Connect.OnResponseListener() {
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
                                restartApp(getContext());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.view_new_home_1, R.id.view_new_home_2, R.id.view_new_home_3, R.id.view_new_home_4, R.id.li_course, R.id.li_problem})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_new_home_1:
                intent = new Intent(getContext(), TuiGuangTuDetailsActivity.class);
                intent.putExtra("img", "https://credit.liuxingkeji.com/var/shareImg/1H1AJD6SLKVADDM6/UIDWTTJ9IBD9/share_117.png");
                intent.putExtra("name", "今日推荐");
                startActivity(intent);
                break;
            case R.id.view_new_home_2:
                intent = new Intent(getContext(), TuiGuangTuDetailsActivity.class);
                intent.putExtra("img", "https://credit.liuxingkeji.com/var/shareImg/1H1AJD6SLKVADDM6/UIDWTTJ9IBD9/share_116.png");
                intent.putExtra("name", "邀请好友");
                startActivity(intent);
                break;
            case R.id.view_new_home_3:
                VisitorInfo info = new VisitorInfo();
                info.nickName = Clone.APP_NAME + "_" + SPConfig.getInstance(getActivity()).getUserAllInfoNew().getName() + "_" + SPConfig.getInstance(getActivity()).getUserAllInfoNew().getUid();
                info.userName = SPConfig.getInstance(getActivity()).getUserAllInfoNew().getName();
                info.phone = SPConfig.getInstance(getActivity()).getUserAllInfoNew().getMobile();
                BotKitClient.getInstance().setVisitor(info);
                BotKitClient.getInstance().setPortraitUser(getResources().getDrawable(R.drawable.icon));
                BotKitClient.getInstance().setPortraitRobot(getResources().getDrawable(R.drawable.tianjia));
                startActivity(new Intent(getActivity(), ChatActivity.class));
                break;
            case R.id.view_new_home_4:
                Intent intent_p = new Intent();
                intent_p.putExtra("name", "我的商户");
                intent_p.putExtra("lid", "131");
                intent_p.putExtra("head_img", "https://credit.liuxingkeji.com/var/upcloud/images/JIUDING34081DC2C15370205F9515094/20191031/yonghu.png");
                intent_p.setClass(getContext(), ShaiXunActivity.class);
                startActivity(intent_p);
                break;
            case R.id.li_course:
                intent = new Intent(getContext(), Course1Activity.class);
                intent.putExtra("type", "sm");
                intent.putExtra("title", "新手教程");
                startActivity(intent);
                break;
            case R.id.li_problem:
                intent = new Intent(getActivity(), HL_BK_WebActivity.class);
                intent.putExtra("WEB_TITLE", "商学院");
                intent.putExtra("WEB_URL", "https://credit.liuxingkeji.com/Oem/");
                startActivity(intent);
                break;
        }
    }
}
