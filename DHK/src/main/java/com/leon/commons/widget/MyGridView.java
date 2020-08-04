package com.leon.commons.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView extends GridView {
	private boolean haveScrollbar = true; 
	
	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyGridView(Context context, AttributeSet attrs, int defStyle) {   
        super(context, attrs, defStyle);   
    }   
	
	
    public void setHaveScrollbar(boolean haveScrollbar) {   
        this.haveScrollbar = haveScrollbar;   
    }   
    @Override   
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
        if (haveScrollbar == false) {   	
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);   
            super.onMeasure(widthMeasureSpec, expandSpec);   
        } else {   
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);   
        }   
    }   



}
