package com.kym.ui.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.kym.ui.R;
import com.kym.ui.util.DIsplayUtils;

/**
 * Created by sunmiaolong on 2018/3/9.
 */

public class NoticeDialog extends DialogFragment implements View.OnClickListener {

    private View.OnClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setCancelable(true);
//        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.layout_notice, container);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DIsplayUtils.getScreenWidth(getActivity()) - DIsplayUtils.dp2px(getActivity(), 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    private void initView(View view) {
        view.findViewById(R.id.tv_exit_confirm).setOnClickListener(this);
        view.findViewById(R.id.tv_exit_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_exit_cancel:
                listener.onClick(v);
                break;
            case R.id.tv_exit_confirm:
                dismiss();
                break;
            default:
                break;
        }
    }

    public void setConfiemClickListener(View.OnClickListener l) {
        listener = l;
    }
}
