package com.kym.ui;


import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.MerChant;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.DialogUtil.OnCancleAndConfirmListener;
import com.kym.ui.util.JsonUtils;

import java.util.List;

import static com.zzss.jindy.appconfig.Clone.OMID;


/**
 * 我要OEM系统
 *
 * @author sun
 * @date 2019/12/3
 */
public class LianxiActivity_new extends BaseActivity implements OnClickListener {
    private String phone_sj;
    private String qq_num;
    private String wx_connection;
    private String public_num;
    private String tel;
    private DialogUtil loadDialogUti;
    private TextView banben;
    private BackDialog3 backDialog4;
    private BackDialog backDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lianxi_xin_x);
        TextView textV_title = (TextView) findViewById(R.id.head_text_title);
        if (getIntent().getStringExtra("title").equals("1")) {
            if (OMID.equals("1H1AJD6SLKVADDM6")) {
                textV_title.setText("我要OEM系统");
            } else {
                textV_title.setText("系统开发咨询");
            }
        } else {
            textV_title.setText("商务合作");
        }
        findViewById(R.id.head_img_left).setOnClickListener(this);
        banben = (TextView) findViewById(R.id.banben);
        findViewById(R.id.lay_lx_ph).setOnClickListener(this);
        findViewById(R.id.lay_lx_qq).setOnClickListener(this);
        findViewById(R.id.lay_lx_wx).setOnClickListener(this);
        findViewById(R.id.lay_lx_gz).setOnClickListener(this);
        findViewById(R.id.lay_lx_zs).setOnClickListener(this);
        TextView textV_zs_x = (TextView) findViewById(R.id.textv_lx_zs_x);
        textV_zs_x.setText("欢迎拨打招商热线：" + phone_sj);
        LevelinFormation();

    }

    private void LevelinFormation() {

        loadDialogUti = new DialogUtil(this);
        Connect.getInstance().post(LianxiActivity_new.this, IService.GET_MERCHANT, null, new Connect.OnResponseListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object result) {
                MerChant response = (MerChant) JsonUtils.parse((String) result, MerChant.class);
                if (response.getResult().getCode() == 10000) {
                    MerChant.DataBean data = response.getData();
                    qq_num = data.getQq_connection();
                    tel = data.getTel();
                    wx_connection = data.getWx_connection();
                } else {
                    Toast.makeText(LianxiActivity_new.this, response.getResult().getMsg(), Toast.LENGTH_SHORT).show();
                }
                loadDialogUti.dismiss();
            }

            @Override
            public void onFailure(String message) {
                loadDialogUti.dismiss();
                Toast.makeText(LianxiActivity_new.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lianxi, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.lay_lx_ph:
                backDialog4 = new BackDialog3("确定", "取消", "联系客服", "服务热线:" + tel,
                        LianxiActivity_new.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.textView1:
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:" + tel);
                                intent.setData(data);
                                startActivity(intent);
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
                break;
            case R.id.lay_lx_wx:
                ClipboardManager cmb2 = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                assert cmb2 != null;
                cmb2.setText(wx_connection + "");
                backDialog = new BackDialog("", "微信客服号复制成功,赶紧去微信粘贴吧", "确定", LianxiActivity_new.this,
                        R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                    @Override
                    public void onClick(View view) {
                        backDialog.dismiss();
                    }
                });
                backDialog.setCancelable(false);
                backDialog.show();
                break;
            case R.id.lay_lx_qq:
                backDialog4 = new BackDialog3("确定", "取消", "联系客服", "需要访问qq，确定要打开软件吗",
                        LianxiActivity_new.this, R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.textView1:
                                if (isQQClientAvailable()) {
                                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq_num
                                            + "&version=1";
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                } else {
                                    Toast.makeText(LianxiActivity_new.this, "您还没安装qq，请先安装qq，谢谢", Toast.LENGTH_LONG).show();
                                }
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
                break;
            case R.id.lay_lx_gz:
                ClipboardManager cmb3 = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                assert cmb3 != null;
                cmb3.setText(public_num + "");
                Toast.makeText(LianxiActivity_new.this, "复制公众号到剪切板",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.lay_lx_zs:
                dialog2();
                break;

            default:
                break;
        }

    }

    public boolean isQQClientAvailable() {
        final PackageManager packageManager = getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;

                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;

    }

    @SuppressLint("SetTextI18n")
    private void dialog2() {
        DialogUtil dialog = new DialogUtil(LianxiActivity_new.this, "联系上级服务商",
                2, new OnCancleAndConfirmListener() {

            @Override
            public void confirm() {
                Intent intent = new Intent();

                // 系统默认的action，用来打开默认的电话界面
                intent.setAction(Intent.ACTION_CALL);
                // 需要拨打的号码
                intent.setData(Uri.parse("tel:" + phone_sj));
                startActivity(intent);

            }

            @Override
            public void cancle() {

            }
        });
        TextView content = new TextView(LianxiActivity_new.this);
        content.setText("服务热线：" + phone_sj);
        dialog.setContent(content);

    }
}
