// Generated code from Butter Knife. Do not modify!
package com.kym.ui.activity.bpbro_safe;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.kym.ui.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_x_ViewBinding implements Unbinder {
  private RegisterActivity_x target;

  private View view2131296671;

  private View view2131296985;

  private View view2131297240;

  @UiThread
  public RegisterActivity_x_ViewBinding(RegisterActivity_x target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_x_ViewBinding(final RegisterActivity_x target, View source) {
    this.target = target;

    View view;
    target.headTextTitle = Utils.findRequiredViewAsType(source, R.id.head_text_title, "field 'headTextTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.head_img_left, "field 'headImgLeft' and method 'onViewClicked'");
    target.headImgLeft = Utils.castView(view, R.id.head_img_left, "field 'headImgLeft'", ImageView.class);
    view2131296671 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mobile = Utils.findRequiredViewAsType(source, R.id.mobile, "field 'mobile'", EditText.class);
    target.message = Utils.findRequiredViewAsType(source, R.id.message, "field 'message'", EditText.class);
    view = Utils.findRequiredView(source, R.id.msg_btn, "field 'msgBtn' and method 'onViewClicked'");
    target.msgBtn = Utils.castView(view, R.id.msg_btn, "field 'msgBtn'", TextView.class);
    view2131296985 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.password = Utils.findRequiredViewAsType(source, R.id.password, "field 'password'", EditText.class);
    target.repassword = Utils.findRequiredViewAsType(source, R.id.repassword, "field 'repassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.submit, "field 'submit' and method 'onViewClicked'");
    target.submit = Utils.castView(view, R.id.submit, "field 'submit'", TextView.class);
    view2131297240 = view;
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
    RegisterActivity_x target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.headTextTitle = null;
    target.headImgLeft = null;
    target.mobile = null;
    target.message = null;
    target.msgBtn = null;
    target.password = null;
    target.repassword = null;
    target.submit = null;

    view2131296671.setOnClickListener(null);
    view2131296671 = null;
    view2131296985.setOnClickListener(null);
    view2131296985 = null;
    view2131297240.setOnClickListener(null);
    view2131297240 = null;
  }
}
