package com.kym.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kym.ui.BackDialog3;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.hualuo.HL_BK_WebActivity;
import com.kym.ui.model.Result;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;
import com.kym.ui.util.MyDataCleanManager;
import com.zzss.jindy.appconfig.Clone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kym.ui.util.MyDataCleanManager.clearAllCache;
import static com.zzss.jindy.appconfig.Clone.APP_NAME;

/**
 * 设置
 *
 * @author sun
 * @date 2019/12/3
 */
public class SheZhiActivity extends BaseActivity {

    @BindView(R.id.head_text_title)
    TextView headTextTitle;
    @BindView(R.id.head_img_left)
    ImageView headImgLeft;
    @BindView(R.id.cache)
    TextView cache;
    @BindView(R.id.banben)
    TextView banben;
    @BindView(R.id.li1)
    LinearLayout li1;
    @BindView(R.id.li2)
    LinearLayout li2;
    @BindView(R.id.tuichu)
    LinearLayout tuichu;
    @BindView(R.id.zhuxiao)
    LinearLayout zhuxiao;
    private BackDialog3 backDialog4;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Intent intent;
    private static String code ;
    private static PackageInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi);



        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        try {
            pref = PreferenceManager.getDefaultSharedPreferences(this);
            String dataSize = MyDataCleanManager.getTotalCacheSize(getApplicationContext());
            cache.setText(dataSize);
            packageCode(getApplicationContext());
            headTextTitle.setText("设置");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.head_img_left, R.id.cache, R.id.li1, R.id.li2, R.id.tuichu, R.id.zhuxiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.cache:
                showCache();
                break;
            case R.id.li1:

                intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                intent.putExtra("WEB_TITLE", "注册协议");
                intent.putExtra("WEB_URL", Clone.HOST+"api/Tpl/link/useragree/views/customer/ra.html?app_name=" + APP_NAME);
                startActivity(intent);
                break;
            case R.id.li2:
                intent = new Intent(getApplicationContext(), HL_BK_WebActivity.class);
                intent.putExtra("WEB_TITLE", "隐私政策");
                intent.putExtra("WEB_URL", Clone.HOST+"api/Tpl/link/useragree/views/customer/index.html?app_name=" + APP_NAME +
                        "&company=" + Clone.COMPANY + "&email=" + Clone.EMAIL + "&mobile=" + Clone.MOBILE);
                startActivity(intent);
                break;
            case R.id.tuichu:
                ExitDialog();
                break;
            case R.id.zhuxiao:
                ToastUtil.showTextToas(getApplicationContext(), "账户注销,请联系平台客服");
                break;
        }
    }

    public String packageCode(Context context) {
        PackageManager manager = context.getPackageManager();

        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionName;
            banben.setText(APP_NAME + "  " + code);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    private void showCache() {
        //显示对话框
        backDialog4 = new BackDialog3("确定", "取消", "提示", "确定要清除缓存吗？",
                SheZhiActivity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.textView1:
                        clearAllCache(getApplicationContext());
                        cache.setText("0.0B");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }).start();

                        backDialog4.dismiss();
                        break;
                    case R.id.textView2:
                        backDialog4.dismiss();
                        break;
                }
            }
        });
        backDialog4.setCancelable(false);
        backDialog4.show();
    }

    /**
     * 退出账号
     */
    protected void ExitDialog() {
        backDialog4 = new BackDialog3("确定", "取消", "提示", "确定要退出当前账号吗？",
                SheZhiActivity.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.textView1:
                        LoginOut();
                        backDialog4.dismiss();
                        break;
                    case R.id.textView2:
                        backDialog4.dismiss();
                        break;
                }
            }
        });
        backDialog4.setCancelable(false);
        backDialog4.show();
    }

    private void LoginOut() {
        final DialogUtil loading = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.USER_LOGOUT, null, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                loading.dismiss();
                Result response = (Result) JsonUtils.parse((String) result, Result.class);
                if (response.getResult().getCode() == 10000) {
                    finish();
                    SecondActivity.activity.finish();
                    editor = pref.edit();
                    editor.putString("patternPwd", ""); //验证手势密码
                    editor.putString("isPwd", "NO");
                    editor.putString("isFinger", "NO");
                    editor.apply();
                    System.gc();
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                loading.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }
}
