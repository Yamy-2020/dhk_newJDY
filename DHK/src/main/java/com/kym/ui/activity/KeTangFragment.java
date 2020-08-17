package com.kym.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.KuaiJieCardList;
import com.kym.ui.info.WebViewBean;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;


public class KeTangFragment extends Fragment {

    private WebView webView;
    private KuaiJieCardList.KuaiJieCardListInfo kuaiJieCardListInfo;
    private Map<String, String> map;
    private String s;
    private View view;
    private DialogUtil dialogUtil;
    private PromptDialog promptDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_web, container, false);
//        initHead();
        dialogUtil = new DialogUtil(getContext());

        initView();
//        ifshop();
        checkAppVersion();
        return view;
    }
    protected void checkAppVersion() {
//        dialogUtil = new DialogUtil(getContext());

        Connect.getInstance().post(getContext(), IService.WEB_URL, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                WebViewBean info = (WebViewBean) JsonUtils.parse((String) result, WebViewBean.class);
                if (info.getResult().getCode() == 10000) {
//                    promptDialog.dismiss();
//                    dialogUtil.dismiss();

                    Log.e("1111111", "onSuccess: " + info.getData());
                    map = new HashMap<>();
                    map.put("KYT-OSVC-Token", info.getData().getKYTOSVCToken());

                    webView.loadUrl(info.getData().getUrl(), map);

                } else {
                    ToastUtil.showTextToas(getContext(), "请联系客服");
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }


    public void initView() {
//        promptDialog = new PromptDialog(getActivity());

        view.findViewById(R.id.ll111).setVisibility(View.GONE);
        webView = view.findViewById(R.id.webView);
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

                        try {
                            if (url.startsWith("http:") || url.startsWith("https:")) {
                                view.loadUrl(url);

                            } else {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
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
                        ToastUtil.showTextToas(getContext(), "请检查网络是否可用");
                    }

                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                        super.onReceivedError(webView, webResourceRequest, webResourceError);
                        if (webResourceRequest.isForMainFrame()) {//是否是为 main frame创建
                            view.loadUrl("about:blank");// 避免出现默认的错误界面
                            ToastUtil.showTextToas(getContext(), "请检查网络是否可用");
                        }
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        //设定加载结束的操作
//                         promptDialog.dismiss();
                        dialogUtil.dismiss();
                    }
                });
                webView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键

//                        时的操作
                                webView.goBack();   //后退


                                //webview.goForward();//前进
                                return true;    //已处理
                            }
                        }
                        return false;
                    }
                });
            }
        });





    }


    private void initHead() {
        TextView tvTitle = view.findViewById(R.id.head_text_title);
        view.findViewById(R.id.head_img_left).setVisibility(View.GONE);
        tvTitle.setText("课堂");
    }
}
