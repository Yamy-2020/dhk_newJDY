package com.kym.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener;
import com.dueeeke.videoplayer.player.VideoView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.VideoResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.wxapi.WXEntryActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import widget.CustomPopWindow;

import static com.kym.ui.util.ShareUtil.bmpToByteArray;

public class RotateRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private RotateRecyclerViewAdapter.OnCardList listener;
    private ArrayList<VideoResponse.VideoInfo> models = new ArrayList<>();
    private CustomPopWindow popupWindow;
    private LinearLayout li_fx;
    private IWXAPI api;
    private final int TYPE_WX_FRIEND = 0;
    private final int TYPE_WX_FRIENDS = 1;

    public RotateRecyclerViewAdapter(Context context, RotateRecyclerViewAdapter.OnCardList listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_rotate, parent, false);
        return new RotateRecyclerViewAdapter.VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RotateRecyclerViewAdapter.VideoHolder) {
            RotateRecyclerViewAdapter.VideoHolder viewHolder = (RotateRecyclerViewAdapter.VideoHolder) holder;
            VideoResponse.VideoInfo model = models.get(position);
            ImageView thumb = viewHolder.controller.getThumb();
            Glide.with(thumb.getContext()).load(model.getImg()).placeholder(R.drawable.tgt_def).dontAnimate().into(thumb);
            viewHolder.mVideoView.setUrl(model.getUrl());
            viewHolder.text_shareUrl.setText(model.getUrl());
            viewHolder.text_shareTitle.setText(model.getTitle());
            viewHolder.text_shareDesc.setText(model.getDesc());
            viewHolder.text_id.setText(model.getId());
            viewHolder.text_img.setText(model.getIs_agree());
            //0-没点赞  1-点赞
            if (model.getIs_agree().equals("0")) {
                viewHolder.dz_img.setImageResource(R.drawable.dz_blurs);
            } else if (model.getIs_agree().equals("1")) {
                viewHolder.dz_img.setImageResource(R.drawable.dz_focus);
            }
            viewHolder.yl_text.setText(model.getSee_times());
            viewHolder.dz_text.setText(model.getZan_times());
            viewHolder.fx_text.setText(model.getShare_times());
            viewHolder.controller.setTitle(model.getTitle());
            viewHolder.mVideoView.setVideoController(viewHolder.controller);
            viewHolder.title.setText(model.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        return models.size();
    }

    public void setData(List<VideoResponse.VideoInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnCardList {
        void kj_card_list(VideoResponse.VideoInfo allCardListInfo);
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        private VideoView mVideoView;
        private RotateInFullscreenController controller;
        private TextView title, yl_text, dz_text, fx_text, text_id, text_img, text_shareDesc, text_shareTitle, text_shareUrl;
        private LinearLayout li_dz;
        private ImageView dz_img;

        @SuppressLint("WrongViewCast")
        VideoHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            yl_text = itemView.findViewById(R.id.yl_text);
            dz_text = itemView.findViewById(R.id.dz_text);
            fx_text = itemView.findViewById(R.id.fx_text);
            text_id = itemView.findViewById(R.id.text_id);
            text_img = itemView.findViewById(R.id.text_img);
            text_shareDesc = itemView.findViewById(R.id.text_shareDesc);
            text_shareTitle = itemView.findViewById(R.id.text_shareTitle);
            text_shareUrl = itemView.findViewById(R.id.text_shareUrl);
            li_dz = itemView.findViewById(R.id.li_dz);
            li_fx = itemView.findViewById(R.id.li_fx);
            li_fx.setVisibility(View.GONE);
            dz_img = itemView.findViewById(R.id.dz_img);
            controller = new RotateInFullscreenController(context);
            mVideoView = itemView.findViewById(R.id.video_player);

            int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
            mVideoView.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, widthPixels * 9 / 16 + 1));
            controller.getThumb().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getYulan(text_id.getText().toString());
                    mVideoView.start();
                    yl_text.setText((Integer.parseInt(yl_text.getText().toString()) + 1) + "");
                }
            });
            mVideoView.setOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
                @Override
                public void onPlayerStateChanged(int playerState) {
                    if (playerState == VideoView.PLAYER_FULL_SCREEN) {
                        mVideoView.setMute(false);
                    } else if (playerState == VideoView.PLAYER_NORMAL) {
                        mVideoView.setMute(true);
                    }
                }

                @Override
                public void onPlayStateChanged(int playState) {
                    //小屏状态下播放出来之后，把声音关闭
                    if (playState == VideoView.STATE_PREPARED && !mVideoView.isFullScreen()) {
                        mVideoView.setMute(false);
                    }
                }
            });
            li_dz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDianzan(text_id.getText().toString());
                    if (text_img.getText().toString().equals("0")) {
                        dz_img.setImageResource(R.drawable.dz_focus);
                        dz_text.setText((Integer.parseInt(dz_text.getText().toString()) + 1) + "");
                        text_img.setText("1");
                    } else {
                        dz_img.setImageResource(R.drawable.dz_blurs);
                        dz_text.setText((Integer.parseInt(dz_text.getText().toString()) - 1) + "");
                        text_img.setText("0");
                    }
                }
            });

           /* li_fx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPop(text_shareDesc.getText().toString(), text_shareTitle.getText().toString(), text_shareUrl.getText().toString());
                    getZhuanfa(text_id.getText().toString());
                    notifyDataSetChanged();
                }
            });*/
        }
    }

    private void showPop(String shareDesc, String shareTitle, String shareUrl) {
        popupWindow = new CustomPopWindow.Builder((Activity) context).setContentView(initPopView(shareDesc, shareTitle, shareUrl)).setAnimationStyle(R.style.anim_popup_rise_down)
                .setOutsideFocusable(true).setFocusable(true).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .build();
        popupWindow.showAtLocation(li_fx, Gravity.BOTTOM);
    }

    private View initPopView(String shareDesc, String shareTitle, String shareUrl) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_share_qr, null);
        view.findViewById(R.id.iv_share_qr_save).setVisibility(View.GONE);
        view.findViewById(R.id.tv_share_qr_save).setVisibility(View.GONE);
        view.findViewById(R.id.iv_share_qr_wx_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(TYPE_WX_FRIEND, shareDesc, shareTitle, shareUrl);
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.iv_share_qr_wx_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(TYPE_WX_FRIENDS, shareDesc, shareTitle, shareUrl);
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.iv_share_qr_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.findViewById(R.id.tv_share_qr_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return view;
    }

    private void share(int type, String shareDesc, String shareTitle, String shareUrl) {
        api = WXAPIFactory.createWXAPI(context, WXEntryActivity.APP_ID);
        api.registerApp(WXEntryActivity.APP_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!api.isWXAppInstalled()) {
            ToastUtil.showTextToas(context, "您还没有安装微信");
            return;
        }
        WXWebpageObject object = new WXWebpageObject();
        object.webpageUrl = shareUrl;
        WXMediaMessage msg = new WXMediaMessage(object);
        msg.title = shareTitle;
        msg.description = shareDesc;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.push);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        if (type == TYPE_WX_FRIEND) {
            //设置发送给朋友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == TYPE_WX_FRIENDS) {
            //设置发送到朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        req.transaction = String.valueOf(type);
        api.sendReq(req);
    }


    /**
     * 视频点赞
     */
    private void getDianzan(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "1");
        params.put("id", id);
        Connect.getInstance().post(context, IService.VIDEO_DIANZAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
            }

            @Override
            public void onFailure(String message) {
            }
        });
    }

    /**
     * 视频预览
     */
    private void getYulan(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "1");
        params.put("id", id);
        Connect.getInstance().post(context, IService.VIDEO_YULAN, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
            }

            @Override
            public void onFailure(String message) {
            }
        });
    }

    /**
     * 视频转发
     */
    private void getZhuanfa(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "1");
        params.put("id", id);
        Connect.getInstance().post(context, IService.VIDEO_ZHUANFA, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
            }

            @Override
            public void onFailure(String message) {
            }
        });
    }
}