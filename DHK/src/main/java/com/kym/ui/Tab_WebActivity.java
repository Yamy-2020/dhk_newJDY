package com.kym.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.util.DialogUtil;
import com.zzss.jindy.appconfig.Clone;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 公用的web页面
 *
 * @author sun
 * @date 2019/12/3
 */

public class Tab_WebActivity extends BaseActivity {
    private WebView webView;
    private PromptDialog promptDialog;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_web);
        initView();
    }

    public void initView() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//是否支持JavaScrip
        webSettings.setDefaultTextEncodingName("utf-8");//设置解码时默认编码
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialogUtil.dismiss();
            }

            @Override //页面启动的时候
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        webView.loadUrl(getIntent().getStringExtra("WEB_URL"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()是获取当前时间，返回的是毫秒
            {
                ToastUtil.showTextToas(getApplicationContext(), "再按一次退出" + Clone.APP_NAME);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
