package com.kym.ui.activity.bpbro_home.bpbro_dc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.wxapi.WXEntryActivity;
import com.zzss.jindy.appconfig.Clone;

import java.util.HashMap;

import widget.CustomPopWindow;

import static com.kym.ui.util.ShareUtil.bmpToByteArray;

/**
 * 贷偿权益
 *
 * @author sun
 * @date 2019/12/27
 */

public class Bpbro_DaiChangQyActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView image_head;
    private TextView text_mobile, text_stoptime, text_kade;
    private CustomPopWindow popupWindow;
    private LinearLayout li2;
    private IWXAPI api;
    private final int TYPE_WX_FRIEND = 0;
    private final int TYPE_WX_FRIENDS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpbro__dai_chang_qy);
        initView();
    }

    private void initView() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.li1).setOnClickListener(this);
        li2 = (LinearLayout) findViewById(R.id.li2);
        li2.setOnClickListener(this);
        findViewById(R.id.li3).setOnClickListener(this);
        image_head = (CircleImageView) findViewById(R.id.imageV_dj_log);
        image_head.setOnClickListener(this);
        Glide.with(getApplicationContext()).load(SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getHeadimage()).placeholder(R.drawable.icon)
                .dontAnimate().into(image_head);
        text_mobile = (TextView) findViewById(R.id.text_mobile);
        text_stoptime = (TextView) findViewById(R.id.text_stoptime);
        HashMap<String, String> accountInfo = SPConfig.getInstance(getApplicationContext()).getAccountInfo();
        text_mobile.setText(accountInfo.get(Constant.USERNAME).substring(0, 3) +
                "****" + accountInfo.get(Constant.USERNAME).substring(7, 11));
        text_kade = (TextView) findViewById(R.id.kade_name);
        if (!getIntent().getStringExtra("stoptime").equals("yes")) {
            text_stoptime.setVisibility(View.VISIBLE);
            text_stoptime.setText("到期日期：" + getIntent().getStringExtra("stoptime"));
        }
        text_kade.setText("尊享贷偿权益");
        text_kade.setTextColor(0xFF34A350);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.li1:
                startActivity(new Intent(getApplicationContext(), Bpbro_DaiChang_PayListActivity.class));
                break;
            case R.id.li2:
                showPop();
                break;
            case R.id.li3:
                ToastUtil.showTextToas(getApplicationContext(), "暂无更多优惠");
                break;
            case R.id.iv_share_qr_wx_1:
                // 微信好友
                share(TYPE_WX_FRIEND);
                popupWindow.dismiss();
                break;
            case R.id.iv_share_qr_wx_2:
                // 微信朋友圈
                share(TYPE_WX_FRIENDS);
                popupWindow.dismiss();
                break;
            case R.id.tv_share_qr_cancel:
                popupWindow.dismiss();
                break;
        }
    }

    private void showPop() {
        popupWindow = new CustomPopWindow.Builder(this).setContentView(initPopView()).setAnimationStyle(R.style.anim_popup_rise_down)
                .setOutsideFocusable(false).setFocusable(false).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .build();
        popupWindow.showAtLocation(li2, Gravity.BOTTOM);
    }

    private View initPopView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_share_qr, null);
        view.findViewById(R.id.iv_share_qr_wx_1).setOnClickListener(this);
        view.findViewById(R.id.iv_share_qr_wx_2).setOnClickListener(this);
        view.findViewById(R.id.iv_share_qr_save).setOnClickListener(this);
        view.findViewById(R.id.tv_share_qr_cancel).setOnClickListener(this);
        view.findViewById(R.id.iv_share_qr_save).setVisibility(View.GONE);
        view.findViewById(R.id.tv_share_qr_save).setVisibility(View.GONE);
        return view;
    }

    private void share(int type) {
        api = WXAPIFactory.createWXAPI(getApplicationContext(), WXEntryActivity.APP_ID);
        api.registerApp(WXEntryActivity.APP_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!api.isWXAppInstalled()) {
            ToastUtil.showTextToas(getApplicationContext(), "您还没有安装微信");
            return;
        }
        WXWebpageObject object = new WXWebpageObject();
        object.webpageUrl = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getSharelink();
        WXMediaMessage msg = new WXMediaMessage(object);
        msg.title = "零额贷偿,利润共享";
        msg.description = "我正在'" + Clone.APP_NAME + "'软件尊享贷偿权益,你赶紧来试试吧";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.push);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        if (type == TYPE_WX_FRIEND) {
            //设置发送给朋友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == TYPE_WX_FRIENDS) {
            //设置发送到朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        req.transaction = String.valueOf(type);
        api.sendReq(req);
    }
}
