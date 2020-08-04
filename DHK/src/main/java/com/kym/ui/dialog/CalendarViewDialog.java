package com.kym.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kym.ui.R;
import com.kym.ui.util.CalendarView;

import java.util.Calendar;
import java.util.List;

public class CalendarViewDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private CalendarView mCalendarView;
    private TextView mTxtDate;
    private long startMillis = -1;
    private int area = -1;
    private OnDateSelectConfirmListener listener;
    private List<String> mSelectData;

    public CalendarViewDialog(Activity context, int themeDialogScale) {
        super(context, themeDialogScale);
        this.context = context;
    }

    public CalendarViewDialog(Activity context, int themeDialogScale, long startMillis, int area, OnDateSelectConfirmListener listener) {
        super(context, themeDialogScale);
        this.context = context;
        this.startMillis = startMillis;
        this.area = area;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_calendar);
        initValue();
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.last).setOnClickListener(this);
        findViewById(R.id.tv_select_date).setOnClickListener(this);
        mTxtDate = findViewById(R.id.txt_date);
        mCalendarView = findViewById(R.id.calendarView);

        // 指定显示的日期, 如当前月的下个月
        Calendar calendar = mCalendarView.getCalendar();
        calendar.add(Calendar.DATE, 1);
        mCalendarView.setCalendar(calendar);
        // 设置字体
        mCalendarView.setTypeface(Typeface.SERIF);
        // 设置是否能够改变日期状态
        mCalendarView.setChangeDateStatus(true);
        // 设置是否能够点击
        mCalendarView.setClickable(true);
        // 设置选择开始位置
        if (startMillis != -1) {
            mCalendarView.setStartLillis(startMillis);
        }
        // 设置选择范围
        if (area != -1) {
            mCalendarView.setChooseArea(area);
        }
        // 设置已选数据
        if (mSelectData != null) {
            mCalendarView.setSelectDate(mSelectData);
        }
        setCurDate();
    }

    private void initValue() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    @SuppressLint("SetTextI18n")
    private void setCurDate() {
        mTxtDate.setText(mCalendarView.getYear() + "年" + (mCalendarView.getMonth() + 1) + "月");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.next://下个月
                mCalendarView.nextMonth();
                setCurDate();
                break;
            case R.id.last://上个月
                mCalendarView.lastMonth();
                setCurDate();
                break;
            case R.id.tv_select_date://确定
                List<String> selectDate = mCalendarView.getSelectDate();
                listener.confirm(selectDate);
                break;
            default:
                break;
        }
    }

    public void setSelectData(List<String> mSelectData) {
        this.mSelectData = mSelectData;
    }

    public interface OnDateSelectConfirmListener {
        void confirm(List<String> selectDate);
    }
}