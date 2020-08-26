package com.kym.ui.activity.rongxinfen;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog;
import com.kym.ui.BackDialog3;
import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_real_name.Bpbro_Idcardid_Activity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.KeXinFenBean;
import com.kym.ui.info.KuaiJieCardList;
import com.kym.ui.info.WebViewBean;
import com.kym.ui.util.Connect;
import com.kym.ui.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;
import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;
import static com.zzss.jindy.appconfig.Clone.OMID;

/**
 * 可信分
 *
 * @author sun
 * @date 2019/12/3
 */

public class RongXinFenFragment extends Fragment {
    @BindView(R.id.rx_img)
    ImageView rxImg;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.level_name)
    TextView levelName;
    @BindView(R.id.wait_count)
    TextView waitCount;
    @BindView(R.id.keep_count)
    TextView keepCount;
    @BindView(R.id.nokeep_count)
    TextView nokeepCount;
    @BindView(R.id.xinyong_guanli)
    LinearLayout xinyongGuanli;
    @BindView(R.id.xinyong_zuji)
    LinearLayout xinyongZuji;
    @BindView(R.id.xinyong_guize)
    LinearLayout xinyongGuize;
    @BindView(R.id.xinyong_shenghuo)
    LinearLayout xinyongShenghuo;
    Unbinder unbinder;
    @BindView(R.id.rx_title)
    TextView rxTitle;
    private View view;
    private SPConfig spConfig;
    private boolean isGetData = false;
    private BackDialog backDialog;
    private BackDialog3 backDialog3;







 private String s;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rongxinfen, container, false);

        unbinder = ButterKnife.bind(this, view);
        initView();
//        initHead();
//        initView();
//        ifshop();
//        checkAppVersion();
        return view;
    }
    public void initView() {
       // if (OMID.equals("1H1AJD6SLKVADDM6")) {
            rxTitle.setText("可信分");
       // } else {
          //  rxTitle.setText("融信分");
       // }
    }

    /*protected void ifshop() {
        Connect.getInstance().post(getContext(), IService.KEXINFEN_SHOP, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                KeXinFenBean shop = (KeXinFenBean) JsonUtils.parse((String) result, KeXinFenBean.class);
                if (shop.getResult().getCode() == 10000) {
                    s = shop.getData().getShop();
                    Log.e("1111111", "onSuccess: " +s.toString());

                } *//*else {
                    ToastUtil.showTextToas(getContext(), "请联系客服");
                }*//*
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }*/
    /*protected void checkAppVersion() {
        Connect.getInstance().post(getContext(), IService.WEB_URL, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                WebViewBean info = (WebViewBean) JsonUtils.parse((String) result, WebViewBean.class);
                if (info.getResult().getCode() == 10000) {
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
                    }
                });
            }
        });
    }


    private void initHead() {
        TextView tvTitle = view.findViewById(R.id.head_text_title);
        view.findViewById(R.id.head_img_left).setVisibility(View.GONE);
        tvTitle.setText("课堂");
        rxTitle.setText("信用分");
    }*/
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
            getInfo();

        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }



    @OnClick({R.id.xinyong_guanli, R.id.xinyong_zuji, R.id.xinyong_guize, R.id.xinyong_shenghuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xinyong_guanli:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), XinYong_GuanLi_Activity.class));
                }
                break;
            case R.id.xinyong_zuji:
                if (canJump()) {
                    tipView("暂未开放,敬请期待");
//                    startActivity(new Intent(getActivity(), XinYong_ZuJi_Activity.class));
                }
                break;
            case R.id.xinyong_guize:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), XinYong_GuiZe_Activity.class));
                }
                break;
            case R.id.xinyong_shenghuo:
                if (canJump()) {
                    tipView("暂未开放,敬请期待");
                }
                break;
        }
    }

    /**
     * 获取融信分基本数据
     */
    private void getInfo() {
        Connect.getInstance().post(getActivity().getApplicationContext(), IService.RONGCINFEN_INFO, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.getString("result");
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.getString("code");
                    String msg = obj1.getString("msg");
                    if (code.equals("10000")) {
                        String data1 = obj.getString("data");
                        JSONObject obj2 = new JSONObject(data1);
                        String statusName = obj2.getString("statusName");
                        if (statusName.equals("display")) {
                            score.setText(obj2.getString("score"));
                            levelName.setText(obj2.getString("level_name"));
                            waitCount.setText(obj2.getString("wait_count"));
                            keepCount.setText(obj2.getString("keep_count"));
                            nokeepCount.setText(obj2.getString("nokeep_count"));
                            if (Integer.parseInt(obj2.getString("score")) >= 150 && Integer.parseInt(obj2.getString("score")) < 200) {
                                rxImg.setImageResource(R.drawable.rx_img15);  //150
                            } else if (Integer.parseInt(obj2.getString("score")) >= 200 && Integer.parseInt(obj2.getString("score")) < 250) {
                                rxImg.setImageResource(R.drawable.rx_img16);  //200
                            } else if (Integer.parseInt(obj2.getString("score")) >= 250 && Integer.parseInt(obj2.getString("score")) < 300) {
                                rxImg.setImageResource(R.drawable.rx_img17);  //250
                            } else if (Integer.parseInt(obj2.getString("score")) >= 300 && Integer.parseInt(obj2.getString("score")) < 350) {
                                rxImg.setImageResource(R.drawable.rx_img1);  //300
                            } else if (Integer.parseInt(obj2.getString("score")) >= 350 && Integer.parseInt(obj2.getString("score")) < 400) {
                                rxImg.setImageResource(R.drawable.rx_img18);  //350
                            } else if (Integer.parseInt(obj2.getString("score")) >= 400 && Integer.parseInt(obj2.getString("score")) < 450) {
                                rxImg.setImageResource(R.drawable.rx_img2);  //400
                            } else if (Integer.parseInt(obj2.getString("score")) >= 450 && Integer.parseInt(obj2.getString("score")) < 500) {
                                rxImg.setImageResource(R.drawable.rx_img3);  //450
                            } else if (Integer.parseInt(obj2.getString("score")) >= 500 && Integer.parseInt(obj2.getString("score")) < 550) {
                                rxImg.setImageResource(R.drawable.rx_img4);  //500
                            } else if (Integer.parseInt(obj2.getString("score")) >= 550 && Integer.parseInt(obj2.getString("score")) < 600) {
                                rxImg.setImageResource(R.drawable.rx_img5);  //550
                            } else if (Integer.parseInt(obj2.getString("score")) >= 600 && Integer.parseInt(obj2.getString("score")) < 650) {
                                rxImg.setImageResource(R.drawable.rx_img6);  //600
                            } else if (Integer.parseInt(obj2.getString("score")) >= 650 && Integer.parseInt(obj2.getString("score")) < 700) {
                                rxImg.setImageResource(R.drawable.rx_img7);  //650
                            } else if (Integer.parseInt(obj2.getString("score")) >= 700 && Integer.parseInt(obj2.getString("score")) < 750) {
                                rxImg.setImageResource(R.drawable.rx_img8);  //700
                            } else if (Integer.parseInt(obj2.getString("score")) >= 750 && Integer.parseInt(obj2.getString("score")) < 800) {
                                rxImg.setImageResource(R.drawable.rx_img9);  //750
                            } else if (Integer.parseInt(obj2.getString("score")) >= 800 && Integer.parseInt(obj2.getString("score")) < 850) {
                                rxImg.setImageResource(R.drawable.rx_img10);  //800
                            } else if (Integer.parseInt(obj2.getString("score")) >= 850 && Integer.parseInt(obj2.getString("score")) < 900) {
                                rxImg.setImageResource(R.drawable.rx_img11);  //850
                            } else if (Integer.parseInt(obj2.getString("score")) >= 900 && Integer.parseInt(obj2.getString("score")) < 950) {
                                rxImg.setImageResource(R.drawable.rx_img12);  //900
                            } else if (Integer.parseInt(obj2.getString("score")) >= 950 && Integer.parseInt(obj2.getString("score")) < 1000) {
                                rxImg.setImageResource(R.drawable.rx_img13);  //950
                            } else if (Integer.parseInt(obj2.getString("score")) >= 1000) {
                                rxImg.setImageResource(R.drawable.rx_img14);  //1000
                            }
                        } else {
                            score.setText("300");
                            levelName.setText("信用正常");
                            waitCount.setText("0");
                            keepCount.setText("0");
                            nokeepCount.setText("0");
                            rxImg.setImageResource(R.drawable.rx_img1);
                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", getContext(),
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getContext(), LoginActivity.class));

//                                restartApp(getContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        ToastUtil.showTextToas(getActivity(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getActivity(), message);
            }
        });
    }

    private boolean canJump() {
        if (spConfig == null) {
            spConfig = SPConfig.getInstance(getActivity().getApplicationContext());
        }
        int status = spConfig.getUserInfoStatus();
        switch (status) {
            case 1:
                backDialog3 = new BackDialog3("确定", "取消", "提示", "请先完成实名认证", getActivity(), R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        backDialog3.dismiss();
                        switch (view.getId()) {
                            case R.id.textView2:
                                break;
                            case R.id.textView1:
                                startActivity(new Intent(getActivity(), Bpbro_Idcardid_Activity.class));
                                break;
                        }
                    }
                });
                backDialog3.setCancelable(false);
                backDialog3.show();
                return false;
            case 2:
                tipView("您的资料审核中,无法使用该功能");
                return false;
            case 3:
                return true;
            case 4:
                tipView("您的资料审核未通过,无法使用该功能");
                return false;
            default:
                return false;
        }
    }

    public void tipView(String msg) {
        backDialog = new BackDialog("", msg, "确定", getContext(),
                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
            @Override
            public void onClick(View view) {
                backDialog.dismiss();
            }
        });
        backDialog.setCancelable(false);
        backDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
