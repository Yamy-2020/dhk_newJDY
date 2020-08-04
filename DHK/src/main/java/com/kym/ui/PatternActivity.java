package com.kym.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.util.LockView;

import java.util.List;

public class PatternActivity extends BaseActivity implements View.OnClickListener {

    private LockView lvLock;
    private TextView lock_text;
    private TextView text_pardon;
    private String pwd1, pwd2;
    private ImageView back, img1, img2, img3, img4, img5, img6, img7, img8, img9;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
        initView();
    }

    public void initView() {
        lvLock = (LockView) findViewById(R.id.lv_lock);
        lock_text = (TextView) findViewById(R.id.lock_text);
        text_pardon = (TextView) findViewById(R.id.text_pardon);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        img1 = (ImageView) findViewById(R.id.small_circle1);
        img2 = (ImageView) findViewById(R.id.small_circle2);
        img3 = (ImageView) findViewById(R.id.small_circle3);
        img4 = (ImageView) findViewById(R.id.small_circle4);
        img5 = (ImageView) findViewById(R.id.small_circle5);
        img6 = (ImageView) findViewById(R.id.small_circle6);
        img7 = (ImageView) findViewById(R.id.small_circle7);
        img8 = (ImageView) findViewById(R.id.small_circle8);
        img9 = (ImageView) findViewById(R.id.small_circle9);
        initClick();
    }

    public void initClick() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        text_pardon.setOnClickListener(this);
        lvLock.setOnDrawFinish(new LockView.OnDrawFinish() {
            @Override
            public void oneDraw(List<Integer> passwords) {
                Log.d("fdf", passwords.toArray().length + "");
                if (passwords.toArray().length < 4) {
                    lock_text.setText("最少连接4个点，请重新输入");
                    lock_text.setTextColor(Color.parseColor("#9e9e9e"));

                } else {
                    pwd1 = passwords.toString();
                    lock_text.setText("再次绘制解锁图案");
                    lock_text.setTextColor(Color.parseColor("#9e9e9e"));
                    lvLock.drawTwo();
                    String sTemp = pwd1.substring(1, pwd1.length() - 1);
                    String[] sArray = sTemp.split(",");
                    for (int i = 0; i < sArray.length; i++) {
                        if (sTemp.indexOf("0") != -1) {
                            img1.setImageResource(R.drawable.small_disc);
                        }
                        if (sTemp.indexOf("1") != -1) {
                            img2.setImageResource(R.drawable.small_disc);
                        }
                        if (sTemp.indexOf("2") != -1) {
                            img3.setImageResource(R.drawable.small_disc);
                        }
                        if (sTemp.indexOf("3") != -1) {
                            img4.setImageResource(R.drawable.small_disc);
                        }
                        if (sTemp.indexOf("4") != -1) {
                            img5.setImageResource(R.drawable.small_disc);
                        }
                        if (sTemp.indexOf("5") != -1) {
                            img6.setImageResource(R.drawable.small_disc);
                        }
                        if (sTemp.indexOf("6") != -1) {
                            img7.setImageResource(R.drawable.small_disc);
                        }
                        if (sTemp.indexOf("7") != -1) {
                            img8.setImageResource(R.drawable.small_disc);
                        }
                        if (sTemp.indexOf("8") != -1) {
                            img9.setImageResource(R.drawable.small_disc);
                        }
                    }
                }
            }

            @Override
            public void twoDraw(List<Integer> passwords) {
                pwd2 = passwords.toString();
                if (pwd1.equals(pwd2)) {
                    ShowDialog();
                    editor = pref.edit();
                    editor.putString("patternPwd", pwd1);
                    editor.putString("isPwd", "YES");
                    editor.putString("isFinger", "NO");
                    editor.putString("num", "4");
                    editor.apply();
                } else {
                    lock_text.setText("与上一次绘制不一致，请重新绘制");
                    lock_text.setTextColor(Color.parseColor("#9e9e9e"));
                    reDraw();
                }
            }
        });
    }

    private void ShowDialog() {
        backDialog = new BackDialog("", "设置成功!", "确定", PatternActivity.this,
                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatternActivity.this, PassWordActivity.class));
                backDialog.dismiss();
                finish();
                backDialog.dismiss();
            }
        });
        backDialog.setCancelable(false);
        backDialog.show();
    }

    public void reDraw() {
        img1.setImageResource(R.drawable.small_circle);
        img2.setImageResource(R.drawable.small_circle);
        img3.setImageResource(R.drawable.small_circle);
        img4.setImageResource(R.drawable.small_circle);
        img5.setImageResource(R.drawable.small_circle);
        img6.setImageResource(R.drawable.small_circle);
        img7.setImageResource(R.drawable.small_circle);
        img8.setImageResource(R.drawable.small_circle);
        img9.setImageResource(R.drawable.small_circle);
        lvLock.cancel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                PatternActivity.this.finish();
                break;
            case R.id.text_pardon:
                lock_text.setText("绘制解锁图案");
                lock_text.setTextColor(Color.parseColor("#9e9e9e"));
                reDraw();
                break;
        }
    }

    public void tipView(final String flag, String msg) {
        backDialog = new BackDialog("", msg, "确定", PatternActivity.this,
                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag.equals("1")) {
                    finish();
                }
                backDialog.dismiss();
            }
        });
        backDialog.setCancelable(false);
        backDialog.show();
    }

}
