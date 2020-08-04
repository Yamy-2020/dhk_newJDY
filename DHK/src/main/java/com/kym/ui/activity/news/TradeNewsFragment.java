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

import com.kym.ui.activity.MessageCenterContentActivity;
import com.kym.ui.activity.sun_util.ToastUtil;

import com.kym.ui.R;
import com.kym.ui.adapter.TradeNewsAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.SplitterList;

import com.kym.ui.util.Connect;
import com.kym.ui.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.finalteam.loadingview.ListViewFinal;
import cn.finalteam.loadingview.OnDefaultRefreshListener;
import cn.finalteam.loadingview.OnLoadMoreListener;
import cn.finalteam.loadingview.PtrClassicFrameLayout;
import cn.finalteam.loadingview.PtrFrameLayout;

public class TradeNewsFragment extends Fragment {

    private View layout;

    private List<SplitterList.DataBean> allhotlist_shouru = new ArrayList<SplitterList.DataBean>();

    private ListViewFinal mLv;

    private FrameLayout mFlEmptyView;
    private int mPage = 1;
    private PtrClassicFrameLayout mPtrLayout;
    private TradeNewsAdapter adapter_fen;
    private LinearLayout zanwu;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(R.layout.fragment_news, null);
            initview();
            NewsData(mPage);
        }
        ViewGroup parent = (ViewGroup) layout.getParent();
        if (parent != null) {
            parent.removeView(layout);
        }
        return layout;

    }

    private void initview() {
        adapter_fen = new TradeNewsAdapter(getActivity(), allhotlist_shouru);
        mLv = layout.findViewById(R.id.ListViewFinal1);
        mLv.setAdapter(adapter_fen);
        mFlEmptyView = layout.findViewById(R.id.fl_empty_view);
        mPtrLayout = layout.findViewById(R.id.ptr_layout);
        setSwipeRefreshInfo();
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SplitterList.DataBean pushMessage = allhotlist_shouru.get(position);
                Intent toMessageCenterContentActivity = new Intent(getActivity(), MessageCenterContentActivity.class);
                toMessageCenterContentActivity.putExtra("time", pushMessage.getAddtime());
                toMessageCenterContentActivity.putExtra("title", pushMessage.getTypename());
                try {
                    toMessageCenterContentActivity.putExtra("content", pushMessage.getText());
//                    toMessageCenterContentActivity.putExtra("content", "    恭喜您：" + pushMessage.getF_name().substring(0, pushMessage.getF_name().length() - 1) +
//                            "*" + "为你贡献了" + AmountUtils.changeF2Y(Long.parseLong("" + pushMessage.getSplitter())) + " 元，请继续努力！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                NewsData(1);
            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mLv.setOnLoadMoreListener(new OnLoadMoreListener() {// 上拉加载
            @Override
            public void loadMore() {
                NewsData(mPage);
            }
        });
    }

    private void NewsData(final int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        params.put("type", "");
        params.put("starttime", "");
        params.put("endtime", "");

        Connect.getInstance().post(getActivity(), IService.SPLITTERLIST, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                SplitterList response = (SplitterList) JsonUtils.parse((String) result, SplitterList.class);
                if (response.getResult().getCode() == 10000) {
                    if (response.getData() != null) {
                        if (page == 1) {
                            allhotlist_shouru.clear();
                        }
                        mPage = page + 1;
                        allhotlist_shouru.addAll(response.getData());
                        adapter_fen.notifyDataSetChanged();
                        mPtrLayout.onRefreshComplete();
                        mLv.onLoadMoreComplete();
                        zanwu.setVisibility(View.GONE);
                    } else {
                        if (mPage == 1) {
                            zanwu.setVisibility(View.VISIBLE);
                        }
                        mPtrLayout.onRefreshComplete();
                        mLv.onLoadMoreComplete();
                    }
                } else {
                    if (page == 1) {
                        allhotlist_shouru.clear();
                    }
                    adapter_fen.notifyDataSetChanged();
                    mPtrLayout.onRefreshComplete();
                    mLv.onLoadMoreComplete();
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getActivity(), message);
            }
        });
    }
}
