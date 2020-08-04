package com.kym.ui;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.commons.util.ProgressWebView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.util.ShareUtil;

import static android.view.KeyEvent.KEYCODE_BACK;

public class PublicWebActivity extends BaseActivity implements View.OnClickListener {

    private ProgressWebView web;
    private Dialog dialog;
    private String wx_title;
    private String wx_content,wx_url;
    private String web_title;
    private String web_url;
    private String pic;
    private String right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_web);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        WebSetting();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && web.canGoBack()) {
            web.goBack();
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        web.onResume();
        web.resumeTimers();
        super.onResume();
    }

    @Override
    protected void onPause() {
        web.onPause();
        web.pauseTimers();
        super.onPause();
    }

    private void WebSetting() {
        Intent intent = getIntent();
        wx_title = intent.getStringExtra("WX_TITLE");//分享——标题
        wx_content = intent.getStringExtra("WX_CONTENT");//分享——内容
        wx_url = intent.getStringExtra("WX_URL");//分享——内容
        web_title = intent.getStringExtra("WEB_TITLE");//标题
        web_url = intent.getStringExtra("WEB_URL");//标题
        pic = intent.getStringExtra("PIC");//分享——图片
        right = intent.getStringExtra("RIGHT");//
        TextView text_title = (TextView) findViewById(R.id.head_text_title);
        LinearLayout mLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        TextView right_tv = (TextView) findViewById(R.id.right_tv);
        right_tv.setOnClickListener(this);
        if (TextUtils.equals("SHARE-ONLYWX", right)) {
            right_tv.setVisibility(View.VISIBLE);
            right_tv.setText("分享");
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        web = new ProgressWebView(this, text_title, intent);
        web.setLayoutParams(params);
        mLayout.addView(web);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.right_tv:
                if (right.equals("SHARE-ONLYWX")) {
                    dialog();
                }

                break;
            case R.id.lay_no:
                dialog.dismiss();

                break;
            case R.id.lay_diss:
                dialog.dismiss();

                break;


            case R.id.addregisterweixin_new:
                dialog.dismiss();

                ShareUtil.getInstance().wechatShare(SendMessageToWX.Req.WXSceneSession, wx_url, wx_title, wx_content,
                        pic, PublicWebActivity.this);

                break;

            case R.id.addregisterfriend_new:
                dialog.dismiss();

                ShareUtil.getInstance().wechatShare(SendMessageToWX.Req.WXSceneTimeline, wx_url, wx_title, wx_content,
                        pic, PublicWebActivity.this);

                break;
            default:
                break;
        }
    }

    private void dialog() {
        dialog = new Dialog(PublicWebActivity.this, R.style.MyDialgoStyle);
        Window window = dialog.getWindow();
        View view = LayoutInflater.from(PublicWebActivity.this).inflate(R.layout.addpopwindow_web_x, null);
        view.findViewById(R.id.lay_no).setOnClickListener(this);
        view.findViewById(R.id.lay_diss).setOnClickListener(this);
        view.findViewById(R.id.addregisterweixin_new).setOnClickListener(this);
        view.findViewById(R.id.addregisterfriend_new).setOnClickListener(this);
        assert window != null;
        window.setContentView(view);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ProgressWebView.TYPE_CAMERA) { // 相册选择
                web.onActivityCallBack(true, null);
            } else if (requestCode == ProgressWebView.TYPE_GALLERY) {// 相册选择
                if (data != null) {
                    Uri uri = data.getData();
                    Log.d("uri", "uri=" + uri);
                    if (uri != null) {
                        web.onActivityCallBack(false, uri);
                    } else {
                        Toast.makeText(PublicWebActivity.this, "获取数据为空", Toast.LENGTH_LONG).show();
                    }
                }
            }

        }
    }


}
