package com.kym.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.info.CourseResponse;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter {

    private Context context;
    private CourseAdapter.OnCurse listener;
    private ArrayList<CourseResponse.CourseInfo> models = new ArrayList<>();
    private int selectPosition,lastPosition;
    private String type = "no";
    private int expandPosition = -1;

    public CourseAdapter(Context context, CourseAdapter.OnCurse listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_list, parent, false);
        return new CourseAdapter.ContentViewHolder(view);
    }

    public void togglePosition(int position) {
        if (expandPosition != position) {//如果闪屏就用notifydatasetchange
            notifyItemChanged(expandPosition);
            expandPosition = position;
        } else {
            expandPosition = -1;
        }
        notifyItemChanged(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CourseAdapter.ContentViewHolder) {
            final CourseAdapter.ContentViewHolder viewHolder = (CourseAdapter.ContentViewHolder) holder;
            CourseResponse.CourseInfo model = models.get(position);
            viewHolder.title.setText(model.getSubject());
            viewHolder.content.setText("      " + model.getAnswer());
            viewHolder.content.setVisibility(position == expandPosition ? View.VISIBLE : View.GONE);
            viewHolder.img_grxx.setImageResource(position == expandPosition ? R.drawable.top : R.drawable.bottom);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.kj_card_list(models.get(position));
                    togglePosition(position);
                }
            });
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

    public interface OnCurse {
        void kj_card_list(CourseResponse.CourseInfo courseInfo);
    }



    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView title, content;
        private ImageView img_grxx;

        public ContentViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
            img_grxx = view.findViewById(R.id.img_grxx);
//            itemView.setOnClickListener(news_img View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    selectPosition = getAdapterPosition();
//                    listener.kj_card_list(models.get(getAdapterPosition()));
//                    notifyDataSetChanged();
//                }
//            });
        }
    }
}
