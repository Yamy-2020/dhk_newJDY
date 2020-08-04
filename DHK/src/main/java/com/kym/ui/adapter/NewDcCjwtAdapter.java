package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.CourseResponse;

import java.util.ArrayList;
import java.util.List;

public class NewDcCjwtAdapter extends RecyclerView.Adapter {
    private Context context;
    private NewDcCjwtAdapter.OnZDClick listener;
    private ArrayList<CourseResponse.CourseInfo> models = new ArrayList<>();
    private Boolean flag1, flag2;
    private int selectPosition;

    public NewDcCjwtAdapter(Context context, NewDcCjwtAdapter.OnZDClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_dc_cjwt, parent, false);
        return new NewDcCjwtAdapter.ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewDcCjwtAdapter.ContentViewHolder) {
            NewDcCjwtAdapter.ContentViewHolder viewHolder = (NewDcCjwtAdapter.ContentViewHolder) holder;
            CourseResponse.CourseInfo model = models.get(position);
            viewHolder.wt_title.setText(model.getSubject());
            viewHolder.wt_conner.setText(model.getAnswer());
            if (selectPosition == position) {
                viewHolder.wt_conner.setVisibility(View.VISIBLE);
                viewHolder.wt_img.setVisibility(View.GONE);
            } else {
                viewHolder.wt_conner.setVisibility(View.GONE);
                viewHolder.wt_img.setImageResource(R.drawable.bottom);
                viewHolder.wt_img.setVisibility(View.VISIBLE);
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

    public void setData(List<CourseResponse.CourseInfo> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    public interface OnZDClick {
        void OnZDClick(CourseResponse.CourseInfo zdInfo);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView wt_title;
        TextView wt_conner;
        ImageView wt_img;
        LinearLayout wt_li;

        public ContentViewHolder(View view) {
            super(view);
            wt_title =  itemView.findViewById(R.id.wt_title);
            wt_conner =  itemView.findViewById(R.id.wt_conner);
            wt_img =  itemView.findViewById(R.id.wt_img);
            wt_li =  itemView.findViewById(R.id.wt_li);
            wt_li.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
