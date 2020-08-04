package com.kym.ui.activity.new_dc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.activity.zhanghu.DaiChangzhActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.util.Connect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class New_DaiChang_Li4_Activity extends BaseActivity implements View.OnClickListener {

    private CustomBanner<String> mBanner;
    private ArrayList<String> images;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__dai_chang__li4);
        initView();
        getBanner();
        initHead();
    }

    private void initView() {
        mBanner = (CustomBanner) findViewById(R.id.banner);
        LinearLayout dc_li1 = findViewById(R.id.dc_li1);
        LinearLayout dc_li2 = findViewById(R.id.dc_li2);
        LinearLayout dc_li3 = findViewById(R.id.dc_li3);
        LinearLayout dc_li4 = findViewById(R.id.dc_li4);
        LinearLayout dc_li5 = findViewById(R.id.dc_li5);
        dc_li1.setOnClickListener(this);
        dc_li2.setOnClickListener(this);
        dc_li3.setOnClickListener(this);
        dc_li4.setOnClickListener(this);
        dc_li5.setOnClickListener(this);
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("服务商");
    }

    /**
     * 获取轮播图
     */
    private void getBanner() {
        Connect.getInstance().post(getApplicationContext(), IService.HOME_BANNER, null, new Connect.OnResponseListener() {
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
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.dc_li1:
                intent = new Intent(getApplicationContext(), BenJinGuanLiActivity.class);
                startActivity(intent);
                break;
            case R.id.dc_li2:
                intent = new Intent(getApplicationContext(), EDuGuanLiActivity.class);
                startActivity(intent);
                break;
            case R.id.dc_li3:
                startActivity(new Intent(getApplicationContext(), DaiChangTotalActivity.class));
                break;
            case R.id.dc_li4:
                startActivity(new Intent(this, DaiChangzhActivity.class));
                break;
            case R.id.dc_li5:
                startActivity(new Intent(this, KeHuGuanLiActivity.class));
                break;
        }
    }
}
