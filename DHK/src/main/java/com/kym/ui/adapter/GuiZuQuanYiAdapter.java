package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.R;
import com.kym.ui.info.GuiZuQuanYi;

import java.util.ArrayList;
import java.util.List;

public class GuiZuQuanYiAdapter extends RecyclerView.Adapter {
    private Context context;
    private GuiZuQuanYiAdapter.OnKuaiJieCardList listener;
    private ArrayList<GuiZuQuanYi.GuiZuQuanYiInfo> models = new ArrayList<>();

    public GuiZuQuanYiAdapter(Context context, GuiZuQuanYiAdapter.OnKuaiJieCardList listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_guizuquanyi, parent, false);
        return new GuiZuQuanYiAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GuiZuQuanYiAdapter.ContentViewHolder) {
            GuiZuQuanYiAdapter.ContentViewHolder viewHolder = (GuiZuQuanYiAdapter.ContentViewHolder) holder;
            GuiZuQuanYi.GuiZuQuanYiInfo model = models.get(position);
            viewHolder.dc_text.setText(model.getText());
            Glide.with(context).load(model.getUrl()).into(viewHolder.dc_img);
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

    public void setData(List<GuiZuQuanYi.GuiZuQuanYiInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnKuaiJieCardList {

        void kj_card_list(GuiZuQuanYi.GuiZuQuanYiInfo kuaiJieCardListInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView dc_img;
        private TextView dc_text;


        ContentViewHolder(View view) {
            super(view);
            dc_text = (TextView) itemView.findViewById(R.id.dc_text);
            dc_img = (ImageView) itemView.findViewById(R.id.dc_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kj_card_list(models.get(getAdapterPosition()));
                }
            });
        }
    }
}
