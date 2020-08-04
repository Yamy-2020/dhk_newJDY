package com.kym.ui.when_page;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.kym.ui.R;

/**
 * 首次启动展示的引导图
 */
public class PageActivity extends FragmentActivity {

    private PageFrameLayout contentFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_page);
        contentFrameLayout = findViewById(R.id.contentFrameLayout);
        /**
         * 设置资源文件和选中圆点
         */
        contentFrameLayout.setUpViews(new int[]{
//                R.layout.page_tab1,
                R.layout.page_tab4,
                R.layout.page_tab2,
                R.layout.page_tab3,
                R.layout.page_tab5,
        }, R.drawable.banner_on, R.drawable.banner_off);
    }
}
