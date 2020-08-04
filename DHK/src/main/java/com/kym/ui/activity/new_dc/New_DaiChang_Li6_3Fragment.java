package com.kym.ui.activity.new_dc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.adapter.JieKuanToTalMoneyAdapter;
import com.kym.ui.appconfig.IService;
import com.kym.ui.info.JieKuanTotalMoneyResponse;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.finalteam.loadingview.ListViewFinal;

@SuppressLint("ValidFragment")
public class New_DaiChang_Li6_3Fragment extends Fragment {
    private List<JieKuanTotalMoneyResponse.JieKuanTotalMoneyInfo> dc_list = new ArrayList<>();
    private JieKuanToTalMoneyAdapter adapter_fen;
    private New_DaiChang_Li6_Activity recordActivity;
    private View layout;
    private ListViewFinal mLv;
    private LinearLayout zanwu;

    public New_DaiChang_Li6_3Fragment(New_DaiChang_Li6_Activity recordActivity) {
        this.recordActivity = recordActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(R.layout.new_dc, container, false);
            getAllPlanList();
            initUI();
        }
        ViewGroup parent = (ViewGroup) layout.getParent();
        if (parent != null) {
            parent.removeView(layout);
        }
        return layout;
    }

    private void initUI() {
        zanwu = layout.findViewById(R.id.zanwu);
        TextView zanwu_text = layout.findViewById(R.id.zanwu_text);
        zanwu_text.setText("暂无更多数据");
        zanwu.setVisibility(View.GONE);
        mLv = layout.findViewById(R.id.dc_lv);
        adapter_fen = new JieKuanToTalMoneyAdapter(recordActivity, dc_list);
        mLv.setAdapter(adapter_fen);
    }

    /**
     * 获取所有空卡代还信用卡
     */
    private void getAllPlanList() {
        final DialogUtil dialogUtil = new DialogUtil(getActivity());
        HashMap<String, String> paramx = new HashMap<>();
        paramx.put("type", "2");
        Connect.getInstance().post(getActivity(), IService.HUANKUANLIST, paramx, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                JieKuanTotalMoneyResponse response = (JieKuanTotalMoneyResponse) JsonUtils.parse((String) result, JieKuanTotalMoneyResponse.class);
                dialogUtil.dismiss();
                if (response.getResult().getCode() == 10000) {
                    if (response.getData() != null) {
                        dc_list.addAll(response.getData());
                        adapter_fen.notifyDataSetChanged();
                        zanwu.setVisibility(View.GONE);
                    } else {
                        zanwu.setVisibility(View.VISIBLE);
                    }
                    mLv.onLoadMoreComplete();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getContext(), message);
            }
        });
    }

}
