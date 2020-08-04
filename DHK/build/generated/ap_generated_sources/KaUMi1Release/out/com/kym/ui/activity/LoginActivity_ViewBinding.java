// Generated code from Butter Knife. Do not modify!
package com.kym.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.kym.ui.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131296936;

  private View view2131296518;

  private View view2131297664;

  private View view2131296969;

  private View view2131296931;

  private View view2131296937;

  private View view2131296815;

  private View view2131297322;

  private View view2131297323;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.loginTet = Utils.findRequiredViewAsType(source, R.id.login_tet, "field 'loginTet'", TextView.class);
    target.li2 = Utils.findRequiredViewAsType(source, R.id.li2, "field 'li2'", LinearLayout.class);
    target.logingTextTel = Utils.findRequiredViewAsType(source, R.id.loging_text_tel, "field 'logingTextTel'", EditText.class);
    target.logingTextPwd = Utils.findRequiredViewAsType(source, R.id.loging_text_pwd, "field 'logingTextPwd'", EditText.class);
    target.li1 = Utils.findRequiredViewAsType(source, R.id.li1, "field 'li1'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.loging_img_select, "field 'logingImgSelect' and method 'onViewClicked'");
    target.logingImgSelect = Utils.castView(view, R.id.loging_img_select, "field 'logingImgSelect'", ImageView.class);
    view2131296936 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.loginLogo = Utils.findRequiredViewAsType(source, R.id.login_logo, "field 'loginLogo'", TextView.class);
    view = Utils.findRequiredView(source, R.id.del_phone_iv_new, "field 'delPhoneIvNew' and method 'onViewClicked'");
    target.delPhoneIvNew = Utils.castView(view, R.id.del_phone_iv_new, "field 'delPhoneIvNew'", ImageView.class);
    view2131296518 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.zhiwen_login, "method 'onViewClicked'");
    view2131297664 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mima_login, "method 'onViewClicked'");
    view2131296969 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_btn_ok, "method 'onViewClicked'");
    view2131296931 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.loging_text_forget, "method 'onViewClicked'");
    view2131296937 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.keep_password, "method 'onViewClicked'");
    view2131296815 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.textView1, "method 'onViewClicked'");
    view2131297322 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.textView2, "method 'onViewClicked'");
    view2131297323 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loginTet = null;
    target.li2 = null;
    target.logingTextTel = null;
    target.logingTextPwd = null;
    target.li1 = null;
    target.logingImgSelect = null;
    target.loginLogo = null;
    target.delPhoneIvNew = null;

    view2131296936.setOnClickListener(null);
    view2131296936 = null;
    view2131296518.setOnClickListener(null);
    view2131296518 = null;
    view2131297664.setOnClickListener(null);
    view2131297664 = null;
    view2131296969.setOnClickListener(null);
    view2131296969 = null;
    view2131296931.setOnClickListener(null);
    view2131296931 = null;
    view2131296937.setOnClickListener(null);
    view2131296937 = null;
    view2131296815.setOnClickListener(null);
    view2131296815 = null;
    view2131297322.setOnClickListener(null);
    view2131297322 = null;
    view2131297323.setOnClickListener(null);
    view2131297323 = null;
  }
}
