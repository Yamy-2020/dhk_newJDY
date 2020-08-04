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

  private View view2131296943;

  private View view2131296518;

  private View view2131297671;

  private View view2131296976;

  private View view2131296938;

  private View view2131296944;

  private View view2131296815;

  private View view2131297329;

  private View view2131297330;

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
    view2131296943 = view;
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
    view2131297671 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mima_login, "method 'onViewClicked'");
    view2131296976 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_btn_ok, "method 'onViewClicked'");
    view2131296938 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.loging_text_forget, "method 'onViewClicked'");
    view2131296944 = view;
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
    view2131297329 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.textView2, "method 'onViewClicked'");
    view2131297330 = view;
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

    view2131296943.setOnClickListener(null);
    view2131296943 = null;
    view2131296518.setOnClickListener(null);
    view2131296518 = null;
    view2131297671.setOnClickListener(null);
    view2131297671 = null;
    view2131296976.setOnClickListener(null);
    view2131296976 = null;
    view2131296938.setOnClickListener(null);
    view2131296938 = null;
    view2131296944.setOnClickListener(null);
    view2131296944 = null;
    view2131296815.setOnClickListener(null);
    view2131296815 = null;
    view2131297329.setOnClickListener(null);
    view2131297329 = null;
    view2131297330.setOnClickListener(null);
    view2131297330 = null;
  }
}
