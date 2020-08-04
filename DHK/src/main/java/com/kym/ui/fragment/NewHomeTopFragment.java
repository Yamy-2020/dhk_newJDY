package com.kym.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.activity.huankuan.NewRepaymentDetailActivity;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.util.AmountUtils;

/**
 * Created by zachary on 2018/2/1.
 */

public class NewHomeTopFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private BankListResponse.BankInfo info;

    public static NewHomeTopFragment newInstance(BankListResponse.BankInfo bankInfo) {
        Bundle args = new Bundle();
        args.putSerializable("data", bankInfo);
        NewHomeTopFragment fragment = new NewHomeTopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.item_new_home_top, container, false);
            info = (BankListResponse.BankInfo) getArguments().getSerializable("data");
            initView(info);
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NewRepaymentDetailActivity.class);
        intent.putExtra("billId", info.getBill_id());
        intent.putExtra("status", "1");
        intent.putExtra("bankInfo", info);
        startActivity(intent);
    }

    private void initView(BankListResponse.BankInfo info) {
        TextView tvPlanAmount = (TextView) rootView.findViewById(R.id.tv_new_home_top_plan_amount);
        TextView tvBankNumber = (TextView) rootView.findViewById(R.id.tv_new_home_top_bank_number);
        TextView tvBankName = (TextView) rootView.findViewById(R.id.tv_new_home_top_bank_name);
        TextView tvPlanPercent = (TextView) rootView.findViewById(R.id.tv_new_home_top_plan_percent);

        tvPlanAmount.setText(AmountUtils.round(Double.parseDouble(info.getRepay_money()) / 100));
        tvBankName.setText(info.getBank_name());
        tvBankNumber.setText(info.getBank_no());
        tvPlanPercent.setText(String.format("当前进度：%s/%s", info.getCash_number(), info.getRepay_number()));

        rootView.setOnClickListener(this);
    }
}