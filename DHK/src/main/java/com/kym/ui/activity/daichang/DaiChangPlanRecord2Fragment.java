package com.kym.ui.activity.daichang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.DaiChangRecordAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.DaiChangRecord;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
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
public class DaiChangPlanRecord2Fragment extends Fragment {
    private List<DaiChangRecord.DataBean> dc_list = new ArrayList<>();
    private DaiChangRecordAdapter adapter_fen;
    private DaiChangPlanRecordActivity recordActivity;
    private View layout;
    private ListViewFinal mLv;
    private PtrClassicFrameLayout mPtrLayout;
    private int mPage = 1;
    private LinearLayout zanwu;


    public DaiChangPlanRecord2Fragment(DaiChangPlanRecordActivity recordActivity) {
        this.recordActivity = recordActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(R.layout.fragment_dai_chang_plan_record, container, false);
            getAllPlanList(1);
            initUI();
        }
        ViewGroup parent = (ViewGroup) layout.getParent();
        if (parent != null) {
            parent.removeView(layout);
        }
        return layout;
    }

    private void initUI() {
        zanwu = (LinearLayout) layout.findViewById(R.id.zanwu);
        zanwu.setVisibility(View.GONE);
        mLv = (ListViewFinal) layout.findViewById(R.id.dc_lv);
        mPtrLayout = (PtrClassicFrameLayout) layout.findViewById(R.id.dc_ptr_layout);
        adapter_fen = new DaiChangRecordAdapter(recordActivity, dc_list);
        mLv.setAdapter(adapter_fen);
        setSwipeRefreshInfo();
    }

    private void setSwipeRefreshInfo() {
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {// 下拉刷新
                getAllPlanList(1);
            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mLv.setOnLoadMoreListener(new OnLoadMoreListener() {// 上拉加载
            @Override
            public void loadMore() {
                getAllPlanList(mPage);
            }
        });
    }

    /**
     * 获取所有空卡代还信用卡
     */
    private void getAllPlanList(final int page) {
        final DialogUtil dialogUtil = new DialogUtil(getActivity());
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("p", page + "");
        paramx.put("type", "1");
        Connect.getInstance().post(getActivity(), IService.DC_PLAN_LIST, paramx, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                DaiChangRecord response = (DaiChangRecord) JsonUtils.parse((String) result, DaiChangRecord.class);
                dialogUtil.dismiss();
                android.util.Log.d("空卡代还计划列表", result.toString());
                if (response.getResult().getCode() == 10000) {
                    if (response.getData() != null) {
                        if (page == 1) {
                            dc_list.clear();
                        }
                        mPage = page + 1;
                        dc_list.addAll(response.getData());
                        adapter_fen.notifyDataSetChanged();
                        zanwu.setVisibility(View.GONE);
                    } else {
                        if (mPage == 1) {
                            zanwu.setVisibility(View.VISIBLE);
                        }
                    }
                    mPtrLayout.onRefreshComplete();
                    mLv.onLoadMoreComplete();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getContext(),message);
            }
        });
    }
}
