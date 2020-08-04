package com.leon.commons.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kym.ui.R;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.FileManager;
import com.kym.ui.util.MyApplication;
import com.kym.ui.util.log;

import java.util.List;

/**
 * Created by zhangph on 2017/12/14.
 *
 * @author zhangph
 */

public class SpecialProgressWebView extends WebView {
    private Context context;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private Uri fileUri;
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_GALLERY = 2;
    private DialogUtil loading;
    private TextView text_title;
    private Intent intent;
    private String web_title;

    public SpecialProgressWebView(Context context, TextView text_title, Intent intent) {
        super(context);
        this.context = context;
        this.text_title = text_title;
        this.intent = intent;
        init();
    }

    private void init() {
        web_title = intent.getStringExtra("WEB_TITLE");//网页标题
        String web_url = intent.getStringExtra("WEB_URL");
        text_title.setText(web_title);
        loading = new DialogUtil(context);
        ProgressBar progressBar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 3, 0, 0);
        progressBar.setLayoutParams(lp);
        this.addView(progressBar);
        WebSettings webSettings = getSettings();
        //清除网页访问留下的缓存

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
        loadUrl(web_url);
        setWebViewClient(new MyWebViewClient());
        setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    public void destroy() {
        //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        clearCache(true);

        //清除当前webview访问的历史记录
        //只会webview访问历史记录里的所有记录除了当前访问记录
        clearHistory();

        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        clearFormData();
        super.destroy();
    }

    private class ReOnCancelListener implements
            DialogInterface.OnCancelListener {

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
            }
        }
    }

    /**
     * 包含拍照和相册选择
     */
    public void showOptions() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setOnCancelListener(new ReOnCancelListener());
        alertDialog.setTitle("选择");
        alertDialog.setItems(R.array.options,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            toCamera();

                        } else {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
                            ((Activity) context).startActivityForResult(i,
                                    TYPE_GALLERY);
                        }
                    }
                });
        alertDialog.show();
    }

    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(FileManager.getImgFile(MyApplication.getInstance().getApplicationContext()));
    }

    // 请求拍照
    public void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android的相机
        // 创建一个文件保存图片
        fileUri = getOutputMediaFileUri();
        Log.d("ProgressWebview", "fileUri=" + fileUri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        ((Activity) context).startActivityForResult(intent, TYPE_CAMERA);
    }

    private class MyWebChromeClient extends WebChromeClient {
        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            showOptions();
        }

        // For Android > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            showOptions();
        }

        // For Android > 5.0支持多张上传
        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> uploadMsg,
                                         FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = uploadMsg;
            showOptions();
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //作用：获得网页的加载进度并显示
            if (newProgress < 100) {
                String progress = newProgress + "%";
                //progressBar.setText(progress);//暂无用处
            } else {
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            //作用：获取Web页中的标题
            if (web_title.equals("")) {
                text_title.setText(title);
            } else {
                text_title.setText(web_title);
            }

        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            //作用：支持javascript的警告框
            new AlertDialog.Builder(context)
                    .setTitle("JsAlert")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setCancelable(false)
                    .show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            //作用：支持javascript的确认框
            new AlertDialog.Builder(context)
                    .setTitle("JsConfirm")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    })
                    .setCancelable(false)
                    .show();
            // 返回布尔值：判断点击时确认还是取消
            // true表示点击了确认；false表示点击了取消；
            return true;


        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
            //作用：支持javascript输入框
            final EditText et = new EditText(context);
            et.setText(defaultValue);
            new AlertDialog.Builder(context)
                    .setTitle(message)
                    .setView(et)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm(et.getText().toString());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    })
                    .setCancelable(false)
                    .show();

            return true;

        }


        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            //定位回调
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }

    }

    public boolean isQQClientAvailable() {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;

                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
//            if (url.startsWith("wpa.b.qq.com/cgi/wpa.php?ln=2&uin=")) {//针对银闪付在线客服的BUG，对方提供的web无法弹出qq
//                if (isQQClientAvailable()) {
//                    String replace_url = url.replace("wpa.b.qq.com/cgi/wpa.php?ln=2&uin=", "");
//                    String qq_num = "mqqwpa://im/chat?chat_type=wpa&uin=" + replace_url + "&version=1";
//                    context.startActivity(news_img Intent(Intent.ACTION_VIEW, Uri.parse(qq_num)));
//                } else {
//                    Toast.makeText(context, "您还没安装qq，请先安装qq，谢谢", Toast.LENGTH_LONG).show();
//                }
//            } else
            if (url.contains("jumptoweixin") || url.contains("platformapi/startapp") || url.contains("mqqapi")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
                return true;
            }
//            view.loadUrl(url);
            return !url.startsWith("http://") && !url.startsWith("https://");//屏蔽跳转;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            log.e(url+"");
            //设定加载结束的操作
            loading.dismiss();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //设定加载开始的操作
            loading.show();
        }



        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            log.e("onReceivedError【errorCode:" + errorCode + ",description:" + description + ",description:" + description + ",failingUrl:" + failingUrl + "】");
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();    //表示等待证书响应
            // handler.cancel();      //表示挂起连接，为默认方式
            // handler.handleMessage(null);    //可做其他处理
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            //设定加载资源的操作
            super.onLoadResource(view, url);
        }

    }

    /**
     * 回调到网页
     *
     * @param isCamera
     * @param uri
     */
    public void onActivityCallBack(boolean isCamera, Uri uri) {
        if (isCamera) {
            uri = fileUri;
        }

        if (mUploadCallbackAboveL != null) {
            Uri[] uris = new Uri[]{uri};
            mUploadCallbackAboveL.onReceiveValue(uris);
            mUploadCallbackAboveL = null;
        } else if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(uri);
            mUploadMessage = null;
        } else {
            Toast.makeText(context, "无法获取数据", Toast.LENGTH_LONG).show();
        }
    }
}
