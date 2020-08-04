package com.kym.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.selectcity.FileUtil;
import com.kym.ui.util.EncryptUtils;
import com.kym.ui.wxapi.WXEntryActivity;

import java.io.File;
import java.util.concurrent.ExecutionException;

import widget.CustomPopWindow;

import static com.kym.ui.util.ShareUtil.bmpToByteArray;

/**
 * Created by zachary on 2018/2/10.
 */

public class ShareQRCodeActivity extends BaseActivity implements View.OnClickListener {

    private final int TYPE_WX_FRIEND = 0;
    private final int TYPE_WX_FRIENDS = 1;

    private ImageView ivQRCode;

    private String shareUrl;
    private String shareDesc;
    private String shareTitle;
    private String showUrl;

    private CustomPopWindow popupWindow;
    private IWXAPI api;//微信api

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_qrcode);
        Intent intent = getIntent();
        shareUrl = intent.getStringExtra("shareUrl");
        shareDesc = intent.getStringExtra("shareDesc");
        shareTitle = intent.getStringExtra("shareTitle");
        showUrl = intent.getStringExtra("showUrl");
        initView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_share_qr:
                // 弹出选择框
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
            case R.id.iv_share_qr_save:
                // 保存本地
                savePicture(showUrl);
                popupWindow.dismiss();
                break;
            case R.id.tv_share_qr_cancel:
                popupWindow.dismiss();
                break;
            case R.id.head_img_left:
                finish();
                break;
            default:
                break;
        }
    }

    private void share(int type) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(getApplicationContext(), WXEntryActivity.APP_ID);
            api.registerApp(WXEntryActivity.APP_ID);
        }

        WXWebpageObject object = new WXWebpageObject();
        object.webpageUrl = shareUrl;
        WXMediaMessage msg = new WXMediaMessage(object);
        msg.title = shareTitle;
        msg.description = shareDesc;
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
     * 保存图片到本地
     */
    private void                                                                                                                                      savePicture(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FutureTarget<File> target = Glide.with(ShareQRCodeActivity.this).load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                try {
                    File file = target.get();// 源文件

                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
                    if (!dir.exists() && !dir.mkdirs()) {
                        return;
                    }
                    File dest = new File(dir, EncryptUtils.md5(url) + ".jpg");//目标文件
                    if (FileUtil.copy(file, dest)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ShareQRCodeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                popupWindow.dismiss();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showPop() {
        if (popupWindow == null) {
            popupWindow = new CustomPopWindow.Builder(this).setContentView(initPopView()).setAnimationStyle(R.style.anim_popup_rise_down)
                    .setOutsideFocusable(true).setFocusable(true).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .build();
        }
        popupWindow.showAtLocation(ivQRCode, Gravity.BOTTOM);
    }

    private View initPopView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_share_qr, null);
        view.findViewById(R.id.iv_share_qr_wx_1).setOnClickListener(this);
        view.findViewById(R.id.iv_share_qr_wx_2).setOnClickListener(this);
        view.findViewById(R.id.iv_share_qr_save).setOnClickListener(this);
        view.findViewById(R.id.tv_share_qr_cancel).setOnClickListener(this);
        return view;
    }

    private void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(getApplicationContext()).clearDiskCache();
            }
        }).start();
        ivQRCode = (ImageView) findViewById(R.id.iv_share_qr);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.head_text_title);
        TextView tvShare = (TextView) findViewById(R.id.tv_share_qr);

        title.setText("推广");
        tvShare.setOnClickListener(this);
        Glide.with(this).load(showUrl).placeholder(R.drawable.default_image).dontAnimate().into(ivQRCode);
    }
}
