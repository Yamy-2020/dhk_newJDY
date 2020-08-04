package com.kym.ui.activity.new_dc;

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

import com.littlejie.circleprogress.CircleProgress;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.util.Connect;
import com.kym.ui.wxapi.WXEntryActivity;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import widget.CustomPopWindow;

import static com.kym.ui.util.ShareUtil.bmpToByteArray;

public class New_DaiChang_Li2_Activity extends BaseActivity implements View.OnClickListener {

    private CircleProgress mCircleProgress3;
    private CustomPopWindow popupWindow;
    private TextView xfed, gded, lsed, fenxiang, shenqingtiaoe, linshiedu, time;
    private LinearLayout box1, box2;
    private final int TYPE_WX_FRIEND = 0;
    private final int TYPE_WX_FRIENDS = 1;
    private Intent intent;
    private IWXAPI api;
    private float all_quota, surplus_quota;
    private String fixed_quota, temp_quota, due_time, is_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__dai_chang__li2);
        getEduInfo();
        initHead();
    }

    private void initView() {
        mCircleProgress3 = findViewById(R.id.circle_progress_bar3);
        //在代码中动态改变渐变色，可能会导致颜色跳跃
        mCircleProgress3.setMaxValue(all_quota);
        mCircleProgress3.setValue(surplus_quota);
        gded = findViewById(R.id.gudingedu);
        linshiedu = findViewById(R.id.linshiedu);
        fenxiang = findViewById(R.id.fenxiang);
        xfed = findViewById(R.id.xiaofeiedu);
        shenqingtiaoe = findViewById(R.id.shenqingtiaoe);
        lsed = findViewById(R.id.lsed);
        time = findViewById(R.id.time);
        box1 = findViewById(R.id.box1);
        box2 = findViewById(R.id.box2);
        fenxiang.setOnClickListener(this);
        shenqingtiaoe.setOnClickListener(this);
        xfed.setText("消费额度：￥" + bigDecimalMoney(all_quota));
        gded.setText("固定额度：￥" + fixed_quota);
        linshiedu.setText("临时额度：￥" + temp_quota);
        lsed.setText("临时额度：￥" + temp_quota);
        time.setText("有效期：" + due_time);
        if (is_temp.equals("1")) {
            /**
             * 有临时额度
             */
            box1.setVisibility(View.GONE);
            box2.setVisibility(View.VISIBLE);
        } else {
            /**
             * 没有临时额度
             */
            box1.setVisibility(View.VISIBLE);
            box2.setVisibility(View.GONE);
        }
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("临时提额");
    }

    public static String bigDecimalMoney(float money) {
        DecimalFormat df = new DecimalFormat(",##0.00");
        return df.format(money);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.fenxiang:
                showPop();
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
            case R.id.shenqingtiaoe:
                ToastUtil.showTextToas(getApplicationContext(), "如想调额,请联系客服");
//                intent = new Intent(getApplicationContext(), New_DaiChang_Li2_Sqb_Activity.class);
//                startActivity(intent);
                break;
        }
    }

    private void showPop() {
        popupWindow = new CustomPopWindow.Builder(this).setContentView(initPopView()).setAnimationStyle(R.style.anim_popup_rise_down)
                .setOutsideFocusable(false).setFocusable(false).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .build();
        popupWindow.showAtLocation(fenxiang, Gravity.BOTTOM);
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


    /*
    微信分享好友+朋友圈
     */
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
        msg.title = "助我提额";
        msg.description = "我正用使用'" + Clone.APP_NAME + "'全额贷偿信用卡，还能参加立返活动，你也注册试试吧。";
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

    /**
     * 获取额度信息
     */
    private void getEduInfo() {
        Connect.getInstance().post(getApplicationContext(), IService.LINSHITIE, null, new Connect.OnResponseListener() {
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
                        all_quota = Float.parseFloat(obj_data.getString("all_quota").replaceAll(",", ""));
                        surplus_quota = Float.parseFloat(obj_data.getString("surplus_quota").replaceAll(",", ""));
                        fixed_quota = obj_data.getString("fixed_quota");
                        temp_quota = obj_data.getString("temp_quota");
                        due_time = obj_data.getString("due_time");
                        is_temp = obj_data.getString("is_temp");
                        initView();
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
}
