package com.kym.ui.activity.bpbro_untils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.kym.ui.R;
import com.kym.ui.appconfig.IService;
import com.kym.ui.hualuo.HL_BK_WebActivity;
import com.zzss.jindy.appconfig.Clone;

//import androidx.annotation.NonNull;

/**
 * 协议弹窗跳转
 *
 * @author sun
 * @date 2020/1/8 5:49 PM
 */
public class MyCheckTextView extends ClickableSpan {
    private Context mContext;
    private String type;
    private Intent intent;

    public MyCheckTextView(Context mContext, String type) {
        this.mContext = mContext;
        this.type = type;
    }

    @Override
    public void onClick(@NonNull View widget) {
        if (type.equals("1")) {
            intent = new Intent(mContext, HL_BK_WebActivity.class);
            intent.putExtra("WEB_TITLE", "隐私政策");
            intent.putExtra("WEB_URL", Clone.HOST+"api/Tpl/link/useragree/views/customer/index.html?app_name=" + Clone.APP_NAME +
                    "&company=" + Clone.COMPANY + "&email=" + Clone.EMAIL + "&mobile=" + Clone.MOBILE);
            mContext.startActivity(intent);
        } else {
            intent = new Intent(mContext, HL_BK_WebActivity.class);
            intent.putExtra("WEB_TITLE", "注册协议");
            intent.putExtra("WEB_URL", Clone.HOST+"api/Tpl/link/useragree/views/customer/ra.html?app_name=" + Clone.APP_NAME);
            mContext.startActivity(intent);
        }

    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
//        设置文本颜色
        ds.setColor(mContext.getResources().getColor(R.color.blue_public));
//         超链接形式的下划线，false 表示不显示下划
        ds.setUnderlineText(false);

    }
}
