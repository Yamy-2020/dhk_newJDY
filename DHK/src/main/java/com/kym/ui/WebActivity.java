package com.kym.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.util.DialogUtil;

import org.apache.http.util.EncodingUtils;

/**
 * 快捷收款银生宝支付订单
 * Created by sunmiaolong on 2018/7/29.
 * Updated by sunmiaolong on 2019/6/21.
 */

public class WebActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }

    public void initView() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tvTitle.setText("支付订单");
        final Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        final String postDate = "accountId=" + intent.getStringExtra("accountId") +
                "&orderNo=" + intent.getStringExtra("orderNo") +
                "&amount=" + intent.getStringExtra("amount") +
                "&memberId=" + intent.getStringExtra("memberId") +
                "&merchantNo=" + intent.getStringExtra("merchantNo") +
                "&deductCardToken=" + intent.getStringExtra("deductCardToken") +
                "&repayCardToken=" + intent.getStringExtra("repayCardToken") +
                "&repayCycle=" + intent.getStringExtra("repayCycle") +
                "&purpose=" + intent.getStringExtra("purpose") +
                "&quickPayResponseUrl=" + intent.getStringExtra("quickPayResponseUrl") +
                "&delegatePayResponseUrl=" + intent.getStringExtra("delegatePayResponseUrl") +
                "&pageResponseUrl=" + intent.getStringExtra("pageResponseUrl") +
                "&mac=" + intent.getStringExtra("mac");

        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            // 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try{
                    if(url.startsWith("tbopen://")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){
                    return false;
                }
                view.loadUrl(url);
                return true;
                //开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
                dialogUtil.dismiss();
            }

        });
        webView.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));
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
