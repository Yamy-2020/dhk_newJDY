package com.kym.ui.shaixun;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kym.ui.R;
import com.kym.ui.ShaiXunActivity;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
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

@SuppressLint("ValidFragment")
public class ShaiXun1Fragment extends Fragment {
    private View layout;
    private List<YeJi_XiangQingBean.DataBean> hotlist_shouru = new ArrayList<>();
    private ShangHuListAdapter adapter_fen;

    private String title;
    private String headImg;
    private String lid;
    private ShaiXunActivity shaiXunActivity;

    private ListViewFinal mLv;
//    private PtrClassicFrameLayout mPtrLayout;
    private int mPage = 1;
    private FrameLayout mFlEmptyView;
    private int position;

    public ShaiXun1Fragment(ShaiXunActivity shaiXunActivity, String lid) {
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
//        mPtrLayout = (PtrClassicFrameLayout) layout.findViewById(R.id.ptr_layout);
        mLv.setAdapter(adapter_fen);
//        setSwipeRefreshInfo();
    }

//    private void setSwipeRefreshInfo() {
//        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {// 下拉刷新
//                hotlist_shouru.clear();
//                getdata(1);
//            }
//        });
//        mPtrLayout.setLastUpdateTimeRelateObject(this);
//        mLv.setOnLoadMoreListener(new OnLoadMoreListener() {// 上拉加载
//            @Override
//            public void loadMore() {
////                getdata(mPage);
//            }
//        });
//    }


    private void getdata(final int page) {
        hotlist_shouru.clear();
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("status_auth",0+"");
        paramx.put("level",lid+"");
//        paramx.put("p", page + "");
//        paramx.put("status_auth", "");
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
                ToastUtil.showTextToas(getActivity(), message);
            }
        });
    }


}
