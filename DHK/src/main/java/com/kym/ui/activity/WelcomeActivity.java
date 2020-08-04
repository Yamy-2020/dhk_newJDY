package com.kym.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.PatterLoginActivity;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.bpbro_untils.MyCheckTextView;
import com.zzss.jindy.appconfig.Clone;

/**
 * 启动页
 *
 * @author sun
 * @date 2019/12/3
 */
public class WelcomeActivity extends BaseActivity {

    private Handler mHandler = new Handler();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public static final int SHARE_APP_TAG = 0;
    private Intent intent;
    private Dialog getXieYiDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  if (OMID.equals("E1TDVFFY8JX3RY62") || OMID.equals("rd500ZbaNVcKVr8g") ||
                OMID.equals("VIB0T31Q2L7DNDK5") || OMID.equals("Z89LQDU9XYWN4EE8") ||
                OMID.equals("1H1AJD6SLKVADDM6") || OMID.equals("SX90IOKIPZNCO5O1")) {*/
            setContentView(R.layout.datadownloadbackground1);
      /*  } else {
            setContentView(R.layout.datadownloadbackground1);*/
//        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        SharedPreferences setting = getSharedPreferences(String.valueOf(SHARE_APP_TAG), 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        /**
         * patternPwd 验证手势密码
         */
        if (user_first) {
            setting.edit().putBoolean("FIRST", false).commit();
            editor.putString("patternPwd", "");
            editor.putString("isPwd", "NO");
            editor.putString("isFinger", "NO");
            editor.putString("isClick", "NO");
            editor.putString("isAnQuan", "NO");
            editor.putString("img_type", "1");
            editor.putString("xieyi", "NO");
            editor.putString("hk_kf", "NO");
            editor.putString("yk_kf", "NO");
        }
        editor.apply();
        editor = pref.edit();
        if (pref.getString("xieyi", "").equals("NO")) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_xieyi, null);
            initXiYiDialog(view);
            showXiYiDialog(view);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toLoginActivity();
                }
            }, 3000L);
        }
/*        if (!(OMID.equals("E1TDVFFY8JX3RY62") || OMID.equals("rd500ZbaNVcKVr8g") ||
          OMID.equals("VIB0T31Q2L7DNDK5") || OMID.equals("Z89LQDU9XYWN4EE8") ||
          OMID.equals("1H1AJD6SLKVADDM6") || OMID.equals("SX90IOKIPZNCO5O1"))) {
            LinearLayout login_logo1 = findViewById(R.id.login_logo1);
            LinearLayout login_logo2 = findViewById(R.id.login_logo2);
            login_logo1.setVisibility(View.GONE);
            login_logo2.setVisibility(View.GONE);
        if (OMID.equals("X9FN9CEDKB0C9A43")) {
            login_logo1.setVisibility(View.VISIBLE);
            login_logo2.setVisibility(View.VISIBLE);
        }
    }*/
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toLoginActivity() {
        String isPwd = pref.getString("isPwd", "");
        if (isPwd.equals("YES")) {
            intent = new Intent(this, PatterLoginActivity.class);
            intent.putExtra("title", "登录");
            intent.putExtra("type", "3");
            startActivity(intent);
        } else if (isPwd.equals("NO")) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
//        Intent intent = new Intent(getApplicationContext(), Tab_WebActivity.class);
//        intent.putExtra("WEB_URL", "http://www.bpbro.cn/wap/");
//        intent.putExtra("WEB_URL","http://www.61gjw.com/");
//        startActivity(intent);
        finish();
    }

    /**
     * 弹窗操作
     *
     * @param view
     */
    private void initXiYiDialog(View view) {
        TextView app_text = view.findViewById(R.id.app_text);
        String text = "欢迎使用" + Clone.APP_NAME + "，为了加强对您个人信息的保护，向您说明我们在收集和" +
                "使用您个人信息时的处理规则。请您仔细阅读《隐私政策》及《注册协议》(特别是加黑加粗的内容)，我们将严格按照隐" +
                "私政策内容使用和保护您的个人信息，为您提供更好的服务。点击同意即表示您已阅读并同意使用我们的服务。" +
                "点击不同意无法使用我们提供的服务，将立即退出当前状态。";
        SpannableString spannableString = new SpannableString(text);
        if (Clone.APP_NAME.length() == 2) {
            spannableString.setSpan(new MyCheckTextView(this, "1"), 51, 57,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(R.color.blue_public, 51, 57, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            //MyCheckTextView+隐私政策+注册协议
            spannableString.setSpan(new MyCheckTextView(this, "2"), 58, 64,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(R.color.blue_public, 58, 64, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            app_text.setMovementMethod(LinkMovementMethod.getInstance());
        } else if (Clone.APP_NAME.length() == 3) {
            spannableString.setSpan(new MyCheckTextView(this, "1"), 52, 58,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(R.color.blue_public, 52, 58, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new MyCheckTextView(this, "2"), 59, 65,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(R.color.blue_public, 59, 65, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            app_text.setMovementMethod(LinkMovementMethod.getInstance());
        } else if (Clone.APP_NAME.length() == 4) {
            spannableString.setSpan(new MyCheckTextView(this, "1"), 53, 59,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(R.color.blue_public, 53, 59, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new MyCheckTextView(this, "2"), 60, 66,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(R.color.blue_public, 60, 66, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            app_text.setMovementMethod(LinkMovementMethod.getInstance());
        } else if (Clone.APP_NAME.length() == 5) {
            spannableString.setSpan(new MyCheckTextView(this, "1"), 54, 60,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(R.color.blue_public, 54, 60, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new MyCheckTextView(this, "2"), 61, 67,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(R.color.blue_public, 61, 67, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            app_text.setMovementMethod(LinkMovementMethod.getInstance());
        } else if (Clone.APP_NAME.length() == 6) {
            spannableString.setSpan(new MyCheckTextView(this, "1"), 55, 61,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(R.color.blue_public, 55, 61, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new MyCheckTextView(this, "2"), 62, 68,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(R.color.blue_public, 62, 68, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            app_text.setMovementMethod(LinkMovementMethod.getInstance());
        }
        app_text.setText(spannableString);
        TextView no_agree = view.findViewById(R.id.no_agree);
        TextView yes_agree = view.findViewById(R.id.yes_agree);

        yes_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getXieYiDialog.dismiss();
                editor = pref.edit();
                editor.putString("xieyi", "YES");
                editor.apply();
                toLoginActivity();
            }
        });
        no_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getXieYiDialog.dismiss();
                finish();
                System.gc();
            }
        });
    }

    private void showXiYiDialog(View view) {
        getXieYiDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        getXieYiDialog.setContentView(view, lp);
        getXieYiDialog.setCancelable(false);
        getXieYiDialog.setCanceledOnTouchOutside(false);
        getXieYiDialog.show();
    }
}
