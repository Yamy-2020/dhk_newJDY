package com.kym.ui.activity.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kym.ui.R;
import com.kym.ui.activity.MessageCenterContentActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.SystemNewsAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.SySMessage;
import com.kym.ui.newutil.EmptyViewUtils;
import com.kym.ui.util.Connect;
import com.kym.ui.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.loadingview.ListViewFinal;
import cn.finalteam.loadingview.OnDefaultRefreshListener;
import cn.finalteam.loadingview.OnLoadMoreListener;
import cn.finalteam.loadingview.PtrClassicFrameLayout;
import cn.finalteam.loadingview.PtrFrameLayout;

public class SystemNewsFragment extends Fragment {

    private View layout;
    private SystemNewsAdapter newsadapter;
    private List<SySMessage.DataBean> news_data = new ArrayList<SySMessage.DataBean>();
    private ListViewFinal mLv;
    private FrameLayout mFlEmptyView;
    private PtrClassicFrameLayout mPtrLayout;
    private LinearLayout zanwu;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(R.layout.fragment_news, null);
            initview();
            NewsData();
        }
        ViewGroup parent = (ViewGroup) layout.getParent();
        if (parent != null) {
            parent.removeView(layout);
        }
        return layout;

    }
    private void initview() {
        newsadapter = new SystemNewsAdapter(getActivity(), news_data);
        mLv = layout.findViewById(R.id.ListViewFinal1);
        mLv.setAdapter(newsadapter);
        mFlEmptyView = layout.findViewById(R.id.fl_empty_view);
        mPtrLayout = layout.findViewById(R.id.ptr_layout);
        setSwipeRefreshInfo();
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SySMessage.DataBean pushMessage = news_data.get(position);
                Intent toMessageCenterContentActivity = new Intent(getActivity(), MessageCenterContentActivity.class);
                toMessageCenterContentActivity.putExtra("time", pushMessage.getAddtime());
                toMessageCenterContentActivity.putExtra("title", pushMessage.getTitle());
                toMessageCenterContentActivity.putExtra("content", pushMessage.getContent());
                startActivity(toMessageCenterContentActivity);

            }
        });
        zanwu = layout.findViewById(R.id.zanwu);
        zanwu.setVisibility(View.GONE);
    }

    /**
     * 设置上拉下拉刷新
     */
    private void setSwipeRefreshInfo() {
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {// 下拉刷新
                news_data.clear();
                NewsData();
            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mLv.setOnLoadMoreListener(new OnLoadMoreListener() {// 上拉加载
            @Override
            public void loadMore() {

            }
        });
    }

    private void NewsData() {

        Connect.getInstance().post(getActivity(), IService.GET_SYSTEM_MESSAGE, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                SySMessage response = (SySMessage) JsonUtils.parse((String) result, SySMessage.class);
                if (response.getResult().getCode() == 10000) {
                    if (response.getData() != null) {
                        EmptyViewUtils.goneNoDataEmpty(mFlEmptyView);
                        news_data.addAll(response.getData());
                        newsadapter.notifyDataSetChanged();
                        mPtrLayout.onRefreshComplete();
                        mLv.onLoadMoreComplete();
                        zanwu.setVisibility(View.GONE);
                    }else{
                        mPtrLayout.onRefreshComplete();
                        mLv.onLoadMoreComplete();
                        zanwu.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getActivity(),message);
            }
        });

    }
}
