package com.kym.ui.activity.bpbro_home;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.kym.ui.R;


public class JsbridgeActivity extends Activity implements View.OnClickListener {

    BridgeWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsbridge);

        webView = (BridgeWebView) findViewById(R.id.webView);

        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl("file:///android_asset/demo.html");

        findViewById(R.id.java_to_js_spec).setOnClickListener(this);
        findViewById(R.id.java_to_js_default).setOnClickListener(this);

        //默认接收
        webView.setDefaultHandler(new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                String msg = "默认接收到js的数据：" + data;
                Toast.makeText(JsbridgeActivity.this, msg, Toast.LENGTH_LONG).show();

                function.onCallBack("java默认接收完毕，并回传数据给js"); //回传数据给js
            }
        });
        //指定接收 submitFromWeb 与js保持一致
        webView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                String msg = "指定接收到js的数据：" + data;
                Toast.makeText(JsbridgeActivity.this, msg, Toast.LENGTH_LONG).show();

                function.onCallBack("java指定接收完毕，并回传数据给js"); //回传数据给js
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.java_to_js_default:
                //默认接收
                webView.send("发送数据给js默认接收", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) { //处理js回传的数据
                        Toast.makeText(JsbridgeActivity.this, data, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.java_to_js_spec:
                //指定接收参数 functionInJs
                webView.callHandler("functionInJs", "发送数据给js指定接收", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) { //处理js回传的数据
                        Toast.makeText(JsbridgeActivity.this, data, Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }

}
