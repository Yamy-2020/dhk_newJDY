package com.kym.ui.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Constants;
import com.kym.ui.R;
import com.kym.ui.wxapi.WXEntryActivity;
import com.zzss.jindy.appconfig.Clone;

import java.io.ByteArrayOutputStream;

public class ShareUtil {

    private static ShareUtil instance;
    private IWXAPI api;
    public static final String APP_ID = WXEntryActivity.APP_ID;

    public ShareUtil() {
        super();

    }

    public static ShareUtil getInstance() {
        if (instance == null) {
            synchronized (ShareUtil.class) {
                if (instance == null) {
                    synchronized (ShareUtil.class) {
                        instance = new ShareUtil();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * 微信分享
     *
     */
    public void wechatShare(int flag, String webpageUrl, String title, String content, String pic_path,
                            Activity activity) {
        api = WXAPIFactory.createWXAPI(activity, APP_ID, true);

        api.registerApp(APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webpageUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if (flag == 0) {
            msg.title = title;
            msg.description = content; // 这里替换一张自己工程里的图片资源
        } else {
            msg.title = content;
            msg.description = ""; // 这里替换一张自己工程里的图片资源
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;


        if (pic_path.equals("APP_IMG") || null == BitmapFactory.decodeFile(pic_path)) {

            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.push);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            msg.thumbData = bmpToByteArray(thumbBmp, true);
        } else {

            Bitmap bmp = BitmapFactory.decodeFile(pic_path);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 20, 20, true);
            msg.thumbData = bmpToByteArray(thumbBmp, true);
            bmp.recycle();

        }

        api.sendReq(req);
    }

    /**
     * QQ分享
     *
     */
    public Bundle qqShare(String url, String title, String remark, String appname) {
        Bundle bundle = new Bundle();
        // 这条分享消息被好友点击后的跳转URL。
        bundle.putString(Constants.PARAM_TARGET_URL, url);
        // 分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(Constants.PARAM_TITLE, title);
        // 分享的图片URL
        bundle.putString(Constants.PARAM_IMAGE_URL, Clone.HOST + Clone.IMG_URL);
        // 分享的消息摘要，最长50个字
        bundle.putString(Constants.PARAM_SUMMARY, remark);
        // 手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        bundle.putString(Constants.PARAM_APPNAME, appname);
        // 标识该消息的来源应用，值为应用名称+AppId。
        bundle.putString(Constants.PARAM_APP_SOURCE, appname + Clone.QQ_APPID);

        return bundle;
    }

    /**
     * 短信分享
     *
     * @param title   消息主题
     * @param content 消息内容
     * @return
     */
    public Intent smsShare(String title, String content) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        return sendIntent;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
