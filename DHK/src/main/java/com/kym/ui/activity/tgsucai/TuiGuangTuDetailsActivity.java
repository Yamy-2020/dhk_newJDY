package com.kym.ui.activity.tgsucai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.selectcity.FileUtil;
import com.kym.ui.util.EncryptUtils;
import com.kym.ui.wxapi.WXEntryActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import widget.CustomPopWindow;

import static com.kym.ui.util.ShareUtil.bmpToByteArray;

public class TuiGuangTuDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Intent intent;
    private IWXAPI api;
    private final int TYPE_WX_FRIEND = 0;
    private final int TYPE_WX_FRIENDS = 1;
    private CustomPopWindow popupWindow;
    private TextView fenxiang;
    private File share_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang_tu_details);
        initHead();
        initView();
    }



    private void initHead() {
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tvTitle.setText(getIntent().getStringExtra("name"));
    }

    private void initView() {
        intent = getIntent();
        ImageView img = (ImageView) findViewById(R.id.img);
//        TextView baocun = (TextView) findViewById(R.id.baocun);
//        baocun.setVisibility(View.GONE);
        fenxiang = (TextView) findViewById(R.id.fenxiang);
        fenxiang.setText("保存");

//        .setOnClickListener(this);
        fenxiang.setOnClickListener(this);
        savePicture1(intent.getStringExtra("img"));
        Glide.with(getApplicationContext()).load(intent.getStringExtra("img")).placeholder(R.drawable.tgt_def).dontAnimate().into(img);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
           /* case R.id.baocun:
                savePicture(intent.getStringExtra("img"));
                break;*/
            case R.id.fenxiang:
                savePicture(intent.getStringExtra("img"));
               /* if (share_pic == null) {
                    ToastUtil.showTextToas(getApplicationContext(),"请稍后,图片正在加载中");
                } else {
                    showPop();
                }*/
                break;
        }
    }

    private void showPop() {
        popupWindow = new CustomPopWindow.Builder(this).setContentView(initPopView()).setAnimationStyle(R.style.anim_popup_rise_down)
                .setOutsideFocusable(true).setFocusable(true).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .build();
        popupWindow.showAtLocation(fenxiang, Gravity.BOTTOM);
    }

    private View initPopView() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_share_qr, null);
        view.findViewById(R.id.iv_share_qr_save).setVisibility(View.GONE);
        view.findViewById(R.id.tv_share_qr_save).setVisibility(View.GONE);
        view.findViewById(R.id.iv_share_qr_wx_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImg(TYPE_WX_FRIEND);
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.iv_share_qr_wx_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImg(TYPE_WX_FRIENDS);
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.iv_share_qr_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.findViewById(R.id.tv_share_qr_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return view;
    }

    private void shareImg(int type) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(getApplicationContext(), WXEntryActivity.APP_ID);
            api.registerApp(WXEntryActivity.APP_ID);
        }
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.daiyue);
        Bitmap bmp = BitmapFactory.decodeFile(share_pic.getAbsolutePath());
        // 初始化WXImageObject，并将图片传入
        WXImageObject imgObject = new WXImageObject(bmp);
        // 初始化WXMediaMessage对象,并将imgObj赋值给媒体对象mediaObject
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObject;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        if (type == TYPE_WX_FRIEND) {
            //设置发送给朋友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == TYPE_WX_FRIENDS) {
            //设置发送到朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 保存图片到本地
     */
    private void savePicture(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FutureTarget<File> target = Glide.with(TuiGuangTuDetailsActivity.this).load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                    Date date = new Date(System.currentTimeMillis());

                    final File file = target.get();
                    final File dest = new File(Environment.getExternalStorageDirectory(), "/bpbro/ZhiHuan/" + EncryptUtils.md5(url) + simpleDateFormat.format(date) + ".jpg");//目标文件
                    if (FileUtil.copy(file, dest)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                        Uri.fromFile(new File(dest.getPath()))));
                                ToastUtil.showTextToas(getApplicationContext(), "图片保存成功");
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

    /**
     * 保存图片到本地
     */
    private void savePicture1(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FutureTarget<File> target = Glide.with(TuiGuangTuDetailsActivity.this).load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                try {
                    share_pic = target.get();// 源文件
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
