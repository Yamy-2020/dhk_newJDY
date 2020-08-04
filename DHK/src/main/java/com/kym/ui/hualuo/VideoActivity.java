package com.kym.ui.hualuo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.adapter.VideoListAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.VideoResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.List;


public class VideoActivity extends BaseActivity implements View.OnClickListener {

    private VideoListAdapter adapter;
    private BackDialog backDialog;
    private VideoView videoView;
    private LinearLayout zanwu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initHead();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getVideo();
    }

    private void initHead() {
        TextView tv = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv.setText("商学院");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        RecyclerView mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoListAdapter(this, new VideoListAdapter.OnCardList() {

            @Override
            public void kj_card_list(VideoResponse.VideoInfo allCardListInfo) {

            }
        });
        videoView = findViewById(R.id.video_player);
        if (videoView != null && !videoView.isFullScreen()) {
            videoView.release();
        }
        mRecyclerView.setAdapter(adapter);
        zanwu = findViewById(R.id.zanwu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        VideoViewManager.instance().release();
    }

    @Override
    public void onBackPressed() {
        if (!VideoViewManager.instance().onBackPressed()) {
            super.onBackPressed();
        }
    }

    /**
     * 获取预览计划
     */
    private void getVideo() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.VIDEO, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                VideoResponse response = (VideoResponse) JsonUtils.parse((String) result, VideoResponse.class);
                if (response.getResult().getCode() == 10000) {
                    List<VideoResponse.VideoInfo> data = response.getData();
                    if (data != null && data.size() > 0) {
                        adapter.setData(data);
                        zanwu.setVisibility(View.GONE);
                    } else {
                        adapter.clearData();
                        adapter.notifyDataSetChanged();
                        zanwu.setVisibility(View.VISIBLE);
                    }
                } else if (response.getResult().getCode() == 601) {
                    tipView("1", response.getResult().getMsg());
                } else {
                    tipView("1", response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                tipView("1", message);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", VideoActivity.this,
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
