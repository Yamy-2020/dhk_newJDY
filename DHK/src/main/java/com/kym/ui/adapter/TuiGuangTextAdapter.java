package com.kym.ui.adapter;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.TuiGuangSuCaiResponse;
import com.kym.ui.wxapi.WXEntryActivity;

import java.util.ArrayList;
import java.util.List;

import widget.CustomPopWindow;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Sunmiaolong on 2018/7/29.
 * .
 */

public class TuiGuangTextAdapter extends RecyclerView.Adapter {
    private Context context;
    private TuiGuangTextAdapter.OnKuaiJieInfo listener;
    private ArrayList<TuiGuangSuCaiResponse.TuiGuangInfo> models = new ArrayList<>();
    private IWXAPI api;
    private final int TYPE_WX_FRIEND = 0;
    private final int TYPE_WX_FRIENDS = 1;
    private CustomPopWindow popupWindow;
    private TextView fenxiang;

    public TuiGuangTextAdapter(Context context, TuiGuangTextAdapter.OnKuaiJieInfo listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tuiguang_text, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TuiGuangTextAdapter.ContentViewHolder) {
            TuiGuangTextAdapter.ContentViewHolder viewHolder = (TuiGuangTextAdapter.ContentViewHolder) holder;
            TuiGuangSuCaiResponse.TuiGuangInfo model = models.get(position);
//            SpannableStringBuilder span = new SpannableStringBuilder("缩进" + model.getText());
//            span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
//                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            viewHolder.text.setText(model.getText());
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

    public void setData(List<TuiGuangSuCaiResponse.TuiGuangInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {
        private TextView text, baocun;
        private ImageView home_selector;

        ContentViewHolder(final View view) {
            super(view);
            text = (TextView) itemView.findViewById(R.id.text);
            baocun = (TextView) itemView.findViewById(R.id.baocun);
            baocun.setVisibility(View.GONE);
            fenxiang = (TextView) itemView.findViewById(R.id.fenxiang);
            home_selector=(ImageView) itemView.findViewById(R.id.image_home_selector);
            /*分享界面*/
            baocun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.kuaijieClick(models.get(getAdapterPosition()));
                    showPop(text.getText().toString());
                }
            });
            fenxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kuaijieClick(models.get(getAdapterPosition()));
                    ClipboardManager cmb2 = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                    assert cmb2 != null;
                    cmb2.setText(text.getText() + "注册链接：" + SPConfig.getInstance(context).getUserAllInfoNew().getSharelink());
                    ToastUtil.showTextToas(context, "复制成功");
                }
            });
        }
    }

    public interface OnKuaiJieInfo {

        void kuaijieClick(TuiGuangSuCaiResponse.TuiGuangInfo tuiGuangInfo);
    }

    private void showPop(String text) {
        popupWindow = new CustomPopWindow.Builder((Activity) context).setContentView(initPopView(text)).setAnimationStyle(R.style.anim_popup_rise_down)
                .setOutsideFocusable(true).setFocusable(true).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .build();
        popupWindow.showAtLocation(fenxiang, Gravity.BOTTOM);
    }

    private View initPopView(String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_share_qr, null);
        view.findViewById(R.id.iv_share_qr_save).setVisibility(View.GONE);
        view.findViewById(R.id.tv_share_qr_save).setVisibility(View.GONE);

        view.findViewById(R.id.iv_share_qr_wx_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(TYPE_WX_FRIEND, text);
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.iv_share_qr_wx_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(TYPE_WX_FRIENDS, text);
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

    private void share(int type, String text) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, WXEntryActivity.APP_ID);
            api.registerApp(WXEntryActivity.APP_ID);
        }
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = text + "注册链接：" + SPConfig.getInstance(context).getUserAllInfoNew().getSharelink();

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("text");
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

}
