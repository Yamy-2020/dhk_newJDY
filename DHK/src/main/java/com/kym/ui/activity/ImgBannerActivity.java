package com.kym.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lzj.gallery.library.views.BannerViewPager;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.tgsucai.TuiGuangTuDetailsActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.TuiGuangSuCaiResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;


public class ImgBannerActivity extends BaseActivity implements View.OnClickListener {
    BannerViewPager banner_3d;
    List<String> urlList;
    List<String> urlList1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_banner);
        init();
        getImg();
        initHead();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("推广图");
    }

    private void init() {
        banner_3d = findViewById(R.id.banner_3d);
//        urlList = new ArrayList<>();
//        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543221773&di=c63f30c7809e518cafbff961bcd9ec2a&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0116605851154fa8012060c8587ca1.jpg");
//        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042541&di=3ad9deeefff266e76d1f5d57a58f63d1&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F69%2F99%2F66%2F9fce5755f081660431464492a9aeb003.jpg");
//        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042539&di=95bd41d43c335e74863d9bb540361906&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F019a0558be22d6a801219c77d0578a.jpg%402o.jpg");
//        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042539&di=cdd54bffd2aac448c70ae6b416a004d4&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01edb3555ea8100000009af0ba36f5.jpg%401280w_1l_2o_100sh.jpg");
    }

    /**
     *
     */
    private void initData() {

        banner_3d.initBanner(urlList, true)//开启3D画廊效果
                .addPageMargin(10, 50)//参数1page之间的间距,参数2中间item距离边界的间距
//                .addPoint(6)//添加指示器
//                .addStartTimer(8)//自动轮播5秒间隔
                .addPointBottom(7)
//                .addRoundCorners(12)//圆角
                .finishConfig()//这句必须加
                .addBannerListener(new BannerViewPager.OnClickBannerListener() {
                    @Override
                    public void onBannerClick(int position) {
                        //点击item
                        Intent intent = new Intent(getApplicationContext(), TuiGuangTuDetailsActivity.class);
                        intent.putExtra("img", urlList.get(position));
                        intent.putExtra("name", urlList1.get(position));
                        startActivity(intent);
                    }
                });
    }

    private void getImg() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.TUIGUANGTU_SUCAI, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                TuiGuangSuCaiResponse response = (TuiGuangSuCaiResponse) JsonUtils.parse((String) result, TuiGuangSuCaiResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<TuiGuangSuCaiResponse.TuiGuangInfo> data = response.getData();
                    urlList = new ArrayList<>();
                    urlList1 = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        urlList.add(data.get(i).getShareImg());
                        urlList1.add(data.get(i).getName());
                    }
//                    urlList = new ArrayList<>();
//                    urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543221773&di=c63f30c7809e518cafbff961bcd9ec2a&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0116605851154fa8012060c8587ca1.jpg");
//                    urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042541&di=3ad9deeefff266e76d1f5d57a58f63d1&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F69%2F99%2F66%2F9fce5755f081660431464492a9aeb003.jpg");
//                    urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042539&di=95bd41d43c335e74863d9bb540361906&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F019a0558be22d6a801219c77d0578a.jpg%402o.jpg");
//                    urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042539&di=cdd54bffd2aac448c70ae6b416a004d4&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01edb3555ea8100000009af0ba36f5.jpg%401280w_1l_2o_100sh.jpg");

                    initData();
                    Log.d("lubotu",urlList.toString());

                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }
}
