package com.kym.ui.hualuo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;

import me.leefeng.promptlibrary.PromptDialog;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * 公用的web页面
 *
 * @author sun
 * @date 2019/12/3
 */

public class HL_BK_WebActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;
    private PromptDialog promptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }

    public void initView() {
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("加载中");
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tvTitle.setText(getIntent().getStringExtra("WEB_TITLE"));
        webView = findViewById(R.id.webView);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//允许js弹出窗口
                //后台配置必须一致
                webSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 5.0.2; vivo X5Pro V Build/LRX22G; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/45016 Mobile Safari/537.36 MMWEBID/8379 MicroMessenger/7.0.9.1560(0x27000934) Process/tools NetType/WIFI Language/zh_CN ABI/arm64/" + LoginActivity.accessToken_xin);
                webSettings.setAllowFileAccess(true); // 设置可以访问文件
                //支持插件
                webSettings.setPluginState(WebSettings.PluginState.ON);
//                //设置自适应屏幕，两者合用
                webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
                webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
                webSettings.setTextZoom(100);
//
//                //缩放操作
                webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
                webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
                webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
                webSettings.setLoadsImagesAutomatically(true); // 支持自动加载图片
                webView.clearCache(true);
                webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                webView.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
                webView.setWebViewClient(new WebViewClient() {

                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        handler.proceed();  // 接受所有网站的证书
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                        //启动支付宝,并跳转到付款页面
//                        if (parseScheme(url)) {
//                            try {
//                                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                                intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                                intent.setComponent(null);
//                                startActivity(intent);



//                        }
//                        // 如下方案可在非微信内部WebView的H5页面中调出微信支付
//                        else if (url.startsWith("weixin://wap/pay?") || url.contains("weixin")) {
//                            Intent intent = new Intent();
//                            intent.setAction(Intent.ACTION_VIEW);
//                            intent.setData(Uri.parse(url));
//                            startActivity(intent);
//                            finish();
//                            return true;
//                        }
//                        return false;
                        try {
                            if (url.startsWith("http:") || url.startsWith("https:")) {
                                view.loadUrl(url);
                            } else {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
//                                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                                intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                                intent.setComponent(null);
//                                startActivity(intent);
//                                finish();
                            }
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    }

                    @Override
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        super.onReceivedError(view, errorCode, description, failingUrl);
                        //6.0以下执行
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            return;
                        }
                        view.loadUrl("about:blank");// 避免出现默认的错误界面
                        ToastUtil.showTextToas(getApplicationContext(), "请检查网络是否可用");
                    }

                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                        super.onReceivedError(webView, webResourceRequest, webResourceError);
                        if (webResourceRequest.isForMainFrame()) {//是否是为 main frame创建
                            view.loadUrl("about:blank");// 避免出现默认的错误界面
                            ToastUtil.showTextToas(getApplicationContext(), "请检查网络是否可用");
                        }
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        //设定加载结束的操作
                        promptDialog.dismissImmediately();
                    }

                });
                webView.loadUrl(getIntent().getStringExtra("WEB_URL"));
            }
        });
    }

    public boolean parseScheme(String url) {
        if (url.contains("platformapi/startapp")) {
            return true;
        } else return (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) && (url.contains("platformapi") && url.contains("startapp"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && webView.canGoBack()) {
//            webView.goBack();
            finish();
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
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
