package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.ClipboardManager;

import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.ShareResponse;
import com.zzss.jindy.appconfig.Clone;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ShareAdapter extends RecyclerView.Adapter {

    private Context context;
    private OnShare listener;
    private ArrayList<ShareResponse.ShareInfo> models = new ArrayList<>();

    public ShareAdapter(Context context, OnShare listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_share_list, parent, false);
        return new ShareAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShareAdapter.ContentViewHolder) {
            ShareAdapter.ContentViewHolder viewHolder = (ShareAdapter.ContentViewHolder) holder;
            ShareResponse.ShareInfo model = models.get(position);
            viewHolder.name.setText(model.getTitle());
            viewHolder.money.setText(model.getAbout_income());
            viewHolder.content.setText(model.getContent());
            if (Clone.OMID.equals("SX90IOKIPZNCO5O1")) {
                if (model.getShare_code().equals("ZS001")) {
                    viewHolder.btn.setText("分享好友,共同守护");
                } else if (model.getShare_code().equals("ZS002")) {
                    viewHolder.btn.setText("分享专属链接");
                } else if (model.getShare_code().equals("ZS003")) {
                    viewHolder.btn.setText("立即邀请合伙人");
                } else if (model.getShare_code().equals("ZS004")) {
                    viewHolder.btn.setText("邀请好友,助力提额");
                }
            } else {
                if (model.getShare_code().equals("ZS001")) {
                    viewHolder.btn.setText("立即邀请好友");
                } else if (model.getShare_code().equals("ZS002")) {
                    viewHolder.btn.setText("立即邀请好友");
                } else if (model.getShare_code().equals("ZS003")) {
                    viewHolder.btn.setText("立即邀请好友");
                } else if (model.getShare_code().equals("ZS004")) {
                    viewHolder.btn.setText("立即邀请好友");
                }
            }
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

    public void setData(List<ShareResponse.ShareInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnShare {
        void onShare(ShareResponse.ShareInfo shareInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView name, money, content, btn;

        public ContentViewHolder(View view) {
            super(view);
            name = (TextView) itemView.findViewById(R.id.name);
            money = (TextView) itemView.findViewById(R.id.money);
            content = (TextView) itemView.findViewById(R.id.content);
            btn = (TextView) itemView.findViewById(R.id.btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onShare(models.get(getAdapterPosition()));
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    listener.kuaijieClick(models.get(getAdapterPosition()));
                    ClipboardManager cmb2 = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                    assert cmb2 != null;
                    cmb2.setText(content.getText() + "注册链接：" + SPConfig.getInstance(context).getUserAllInfoNew().getSharelink());
                    ToastUtil.showTextToas(context, "文本已成功复制到剪贴板");
                }
            });
        }
    }
}
