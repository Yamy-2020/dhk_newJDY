package com.kym.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kym.ui.PublicWebActivity;
import com.kym.ui.R;
import com.kym.ui.info.HomePicturesResponse;
import com.zzss.jindy.appconfig.Clone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zachary on 2018/1/30.
 */

public class NewHomeListAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<HomePicturesResponse.HomePicture> models = new ArrayList<>();

    public NewHomeListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_home_list, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentViewHolder viewHolder = (ContentViewHolder) holder;
        HomePicturesResponse.HomePicture model = models.get(position);
        Glide.with(context).load(model.getImg_url()).placeholder(R.drawable.default_image).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setData(List<HomePicturesResponse.HomePicture> pictures) {
        models.clear();
        models.addAll(pictures);
        notifyDataSetChanged();
    }

    private class ContentViewHolder extends RecyclerAdapter.ViewHolder {

        private final ImageView imageView;

        ContentViewHolder(View view) {
            super(view);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_new_home_list);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = models.get(getAdapterPosition()).getJump_url();
                    Intent intent = new Intent(context, PublicWebActivity.class);
                    intent.putExtra("WEB_TITLE", Clone.APP_NAME);
                    intent.putExtra("RIGHT", "SHARE-ONLYWX");
                    intent.putExtra("WX_TITLE", Clone.APP_NAME);
                    intent.putExtra("PIC", "APP_IMG");
                    intent.putExtra("WX_CONTENT", url);
                    intent.putExtra("WEB_URL", url);
                    context.startActivity(intent);
                }
            });
        }
    }
}
