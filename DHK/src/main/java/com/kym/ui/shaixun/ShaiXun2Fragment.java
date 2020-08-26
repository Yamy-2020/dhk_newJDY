package com.kym.ui.shaixun;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kym.ui.R;
import com.kym.ui.ShaiXunActivity;
import com.kym.ui.adapter.ShangHuListAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.bean.YeJi_XiangQingBean;
import com.kym.ui.model.MyChild;
import com.kym.ui.newutil.EmptyViewUtils;
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

@SuppressLint("ValidFragment")
public class ShaiXun2Fragment extends Fragment {
    private ShangHuListAdapter adapter_fen;
    private List<YeJi_XiangQingBean.DataBean> hotlist_shouru = new ArrayList<>();
    private String title;
    private String id;
    private String headImg;
    private View layout;
    private String lid;
    private ShaiXunActivity shaiXunActivity;


    private ListViewFinal mLv;
    private PtrClassicFrameLayout mPtrLayout;
    private int mPage = 1;
    private FrameLayout mFlEmptyView;
    private int position;

    public ShaiXun2Fragment(ShaiXunActivity shaiXunActivity, String lid) {
        this.lid = lid;
        this.shaiXunActivity = shaiXunActivity;
        this.title = title;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {

            layout = inflater.inflate(R.layout.fragment_shai_xun1, container, false);
            initUI();
            getdata(mPage);
        }
        ViewGroup parent = (ViewGroup) layout.getParent();
        if (parent != null) {
            parent.removeView(layout);
        }
        return layout;
    }


    private void initUI() {
        mFlEmptyView = layout.findViewById(R.id.fl_empty_view);
        mLv = layout.findViewById(R.id.lv);
        adapter_fen = new ShangHuListAdapter(shaiXunActivity, hotlist_shouru);
        mPtrLayout = layout.findViewById(R.id.ptr_layout);
        mLv.setAdapter(adapter_fen);
//        setSwipeRefreshInfo();
    }

    /**
     * 设置上拉下拉刷新
     */
    private void setSwipeRefreshInfo() {
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {// 下拉刷新
                hotlist_shouru.clear();
                getdata(1);
            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mLv.setOnLoadMoreListener(new OnLoadMoreListener() {// 上拉加载
            @Override
            public void loadMore() {
//                getdata(mPage);
            }
        });
    }


    private void getdata(final int page) {
        hotlist_shouru.clear();
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("status_auth",1+"");
        paramx.put("level",lid+"");
        Connect.getInstance().post(getActivity(), IService.YEJIGUANLI_XIANGQING, paramx, new Connect.OnResponseListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object result) {
                YeJi_XiangQingBean response = (YeJi_XiangQingBean) JsonUtils.parse((String) result, YeJi_XiangQingBean.class);
                if (response.getResult().getCode() == 10000) {
                    mPage = page + 1;

                    if (response.getData() != null) {
                        EmptyViewUtils.goneNoDataEmpty1(mFlEmptyView);
                        hotlist_shouru.addAll(response.getData());
                    }

                    adapter_fen.notifyDataSetChanged();
//                    mPtrLayout.onRefreshComplete();
                    mLv.onLoadMoreComplete();

                } else {
                    if (page == 1) {
                        hotlist_shouru.clear();
                        EmptyViewUtils.showNoDataEmpty1(mFlEmptyView, "暂无数据");
                    } else {
                        EmptyViewUtils.goneNoDataEmpty1(mFlEmptyView);
                    }
                    adapter_fen.notifyDataSetChanged();
//                    mPtrLayout.onRefreshComplete();
                    mLv.onLoadMoreComplete();

                }
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
