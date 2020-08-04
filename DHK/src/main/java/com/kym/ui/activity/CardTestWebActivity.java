package com.kym.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.log;
import com.leon.commons.util.ProgressWebViewx;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.kym.ui.R;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.util.FileCache;
import com.kym.ui.util.ShareUtil;
import com.zzss.jindy.appconfig.Clone;

import org.json.JSONObject;

public class CardTestWebActivity extends BaseActivity implements View.OnClickListener, IUiListener {
    private Dialog dialog;
    private Context mContext;
    private Tencent mTencent; // 腾讯接口实例
    private String url;
    private ProgressWebViewx webView;
    private String money;
    private String name_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_test_web);
        mContext = this;
        mTencent = Tencent.createInstance(Clone.QQ_APPID, getApplicationContext());
        name_user = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew().getName();
        TextView textV_right_tv = (TextView) findViewById(R.id.right_tv);
        textV_right_tv.setVisibility(View.VISIBLE);
        textV_right_tv.setOnClickListener(this);
        textV_right_tv.setText("分享");
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        TextView textV_title = (TextView) findViewById(R.id.head_text_title);
        textV_title.setText(title);
        url = intent.getStringExtra("web_url");
        findViewById(R.id.fenxiang_btn_ok).setOnClickListener(this);
        money = intent.getStringExtra("money");
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.fenxiang_btn_ok).setOnClickListener(this);
        bindEvent();
    }

    // 1
    private Bitmap captureWebView(WebView webView) {
        @SuppressWarnings("deprecation")
        Picture snapShot = webView.capturePicture();

        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        return bmp;
    }

    @SuppressLint("JavascriptInterface")
    private void bindEvent() {

        webView = (ProgressWebViewx) findViewById(R.id.mywebview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(true);

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSaveFormData(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        // webView.addJavascriptInterface(this, "WebViewJavascriptBridge");
        webView.addJavascriptInterface(this, "myobj");
        webView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                webView.requestFocus();
                return false;
            }

        });

        webView.setWebViewClient(new LeonWebViewClient());

        // intent = this.getIntent();
        if (!url.equals(null)) {

            webView.setVisibility(View.VISIBLE);
            webView.clearView();
            webView.loadUrl(url);

        }
        // webView.loadUrl(intent_uri);

    }

    private class LeonWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("jumptoweixin") || url.contains("platformapi/startapp") || url.contains("mqqapi")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }

    }

    /**
     * 防止webview缩放时退出崩溃。
     */
    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.baocunsj:
                // 保存
                dialog.dismiss();
                Toast.makeText(this, "保存成功,请到相册中查看", Toast.LENGTH_SHORT).show();
                FileCache.getInstance().saveToAlbum(captureWebView(webView), "付款二维码", this);
                break;
            case R.id.right_tv:
                dialog();
                break;
            case R.id.fenxiang_btn_ok:
                // dialog();

                ShareUtil.getInstance().wechatShare(SendMessageToWX.Req.WXSceneSession, url, "扫二维码就可以支付！",
                        " 商户：" + name_user + " 正在向您发起一笔金额为￥" + money + "的收款，该二维码支持以下所有平台扫码收款！", "APP_IMG",
                        CardTestWebActivity.this);
                break;

            case R.id.lay_no:
                dialog.dismiss();
                break;
            case R.id.lay_diss:
                dialog.dismiss();
                break;
         /*   case R.id.qq_share_iv_new:
                dialog.dismiss();
                qqShare();
                break;*/
            case R.id.addregistersms_new:
                dialog.dismiss();
                startActivityForResult(ShareUtil.getInstance().smsShare("扫二维码就可以支付！",
                        " 商户：" + name_user + " 正在向您发起一笔金额为￥" + money + "的收款，该二维码支持以下所有平台扫码收款！" + url), 1002);
                break;
            // // 分享微信好友
            case R.id.addregisterweixin_new:
                dialog.dismiss();

                ShareUtil.getInstance().wechatShare(SendMessageToWX.Req.WXSceneSession, url, money + "",
                        money + "", "APP_IMG",
                        CardTestWebActivity.this);

                break;
            // 分享朋友圈
            case R.id.addregisterfriend_new:
                dialog.dismiss();
                ShareUtil.getInstance().wechatShare(SendMessageToWX.Req.WXSceneTimeline, url, money + "",
                        money + "", "APP_IMG", CardTestWebActivity.this);
                break;
            default:
                break;
        }

    }

    private static double convert(double value) {
        long l1 = Math.round(value * 100); // 四舍五入
        double ret = l1 / 100.0; // 注意：使用 100.0 而不是 100
        return ret;
    }

    // 2
    private static Bitmap getBitmapFromRootView(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        if (bmp != null) {
            return bmp;
        } else {
            return null;
        }
    }

    private void dialog() {
        dialog = new Dialog(CardTestWebActivity.this, R.style.MyDialgoStyle);
        Window window = dialog.getWindow();
        View view = LayoutInflater.from(CardTestWebActivity.this).inflate(R.layout.addpopwindow_web_tt, null);
        view.findViewById(R.id.lay_no).setOnClickListener(this);
        view.findViewById(R.id.lay_diss).setOnClickListener(this);
        view.findViewById(R.id.baocunsj).setOnClickListener(this);
//        view.findViewById(R.id.qq_share_iv_new).setOnClickListener(this);
        view.findViewById(R.id.addregisterweixin_new).setOnClickListener(this);
        view.findViewById(R.id.addregisterfriend_new).setOnClickListener(this); // 分享微信好友
        view.findViewById(R.id.addregistersms_new).setOnClickListener(this); // 分享朋友圈
        window.setContentView(view);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();

    }

    /**
     * qq分享
     */
    private void qqShare() {
        mTencent.shareToQQ(this,
                ShareUtil.getInstance().qqShare(url, money + "",
                        money + "",
                        Clone.APP_NAME),
                this);
    }

    // 腾讯回调
    @Override
    public void onCancel() {
        log.i("分享取消");
        Toast.makeText(mContext, "分享取消", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete(JSONObject values) {
        log.i("分享成功");
        Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(UiError e) {
        log.i("onError:code:" + e.errorCode + ", msg:" + e.errorMessage + ", detail:" + e.errorDetail);
        Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
    }
}
