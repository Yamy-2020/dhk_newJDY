package com.kym.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RongHuiWebActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }

    public void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tvTitle.setText("信用卡签约绑卡");
        bangKa();
        webView = (WebView) findViewById(R.id.webView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }

    /**
     * 融汇绑卡
     */
    private void bangKa() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        //使用Gson 添加 依赖 compile 'com.google.code.gson:gson:2.8.1'
        Gson gson = new Gson();
        //使用Gson将对象转换为json字符串
//        String json = gson.toJson(getIntent().getStringExtra("data2"));

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , getIntent().getStringExtra("data2"));

        Request request = new Request.Builder()
                .url(getIntent().getStringExtra("url"))//请求的url
                .post(requestBody)
                .build();

        //创建/Call
        Call call = okHttpClient.newCall(request);
        //加入队列 异步操作
        call.enqueue(new Callback() {
            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("连接失败");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    String json = response.body().string();
                    JSONObject obj = new JSONObject(json);
                    String respCode = obj.getString("respCode");
                    final String respMsg = obj.getString("respMsg");
                    if (respCode.equals("0000")) {
                        final String content = obj.getString("html");
                        runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void run() {
                                WebSettings mWebSettings = webView.getSettings();
                                mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
                                mWebSettings.setJavaScriptEnabled(true);//是否允许JavaScript脚本运行，默认为false。设置true时，会提醒可能造成XSS漏洞
                                mWebSettings.setSupportZoom(true);//是否可以缩放，默认true
                                mWebSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
                                mWebSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
                                mWebSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
                                mWebSettings.setAppCacheEnabled(true);//是否使用缓存
                                mWebSettings.setDomStorageEnabled(true);//开启本地DOM存储
                                mWebSettings.setLoadsImagesAutomatically(true); // 加载图片
                                mWebSettings.setAllowUniversalAccessFromFileURLs(true);
                                webView.setWebViewClient(new WebViewClient() {
                                    // 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
                                webView.loadDataWithBaseURL(null, content, "text/html; charset=UTF-8", "utf-8", null);
                            }
                        });
                    } else if (respCode.equals("2021")) {
                        dialogUtil.dismiss();
                        runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void run() {
                                bangKaKuaiJie();
                            }
                        });
                    } else {
                        dialogUtil.dismiss();
                        runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void run() {
                                tipView("2", "小额落地" + respMsg);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 融汇绑卡已开通快捷支付
     */
    private void bangKaKuaiJie() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("orderNum", getIntent().getStringExtra("orderNum"));
        Connect.getInstance().post(getApplicationContext(), IService.RONGHUI_KUAIJIE, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                // 0-已签约  1-进件  2-绑卡
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        tipView("1", "签约成功");
                    } else {
                        tipView("1", msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1", message);
            }
        });
    }

    public void tipView(final String flag, String msg) {
        if (backDialog == null) {
            backDialog = new BackDialog("", msg, "确定", RongHuiWebActivity.this,
                    R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                @Override
                public void onClick(View view) {
                    if (!flag.equals("1")) {
                        finish();
                    }
                    backDialog.dismiss();
                }
            });
            backDialog.setCancelable(false);
            backDialog.show();
        }
    }
}
