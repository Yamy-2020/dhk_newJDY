package com.kym.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageCenterContentActivity extends BaseActivity implements OnClickListener {
    private ImageView head_img_left;
    private TextView head_text_title, content_tv, content_title, time_tv;
    private String time;
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_center_content_activity);
        initview();
    }

    /**
     * 初始化
     */
    private void initview() {
        time = getIntent().getStringExtra("time");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        head_img_left = (ImageView) findViewById(R.id.head_img_left);  //返回
        head_img_left.setVisibility(View.VISIBLE);
        head_img_left.setOnClickListener(this);
        head_text_title = (TextView) findViewById(R.id.head_text_title);
        head_text_title.setText(title + "");

        content_tv = (TextView) findViewById(R.id.content_tv);   //消息内容
//        content_title = (TextView) findViewById(R.id.content_title);  //消息标题
        time_tv = (TextView) findViewById(R.id.time_tv);  //添加时间
        content_tv.setText(content + "");
//        content_title.setText(title + "");
        time_tv.setText(gettime(time) + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.head_img_left:
                finish();
                break;
            default:
                break;
        }
    }

    // 把时间戳转为string类型
    public static String gettime(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd     HH:mm");
        long cc_time = Long.valueOf(string);
        return simpleDateFormat.format(new Date(cc_time * 1000L));
    }
}
