// Generated code from Butter Knife. Do not modify!
package com.kym.ui.activity.bpbro_safe;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.kym.ui.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserAnQuanActivity_ViewBinding implements Unbinder {
  private UserAnQuanActivity target;

  private View view2131296671;

  private View view2131296859;

  private View view2131296865;

  private View view2131296869;

  @UiThread
  public UserAnQuanActivity_ViewBinding(UserAnQuanActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserAnQuanActivity_ViewBinding(final UserAnQuanActivity target, View source) {
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
    view = Utils.findRequiredView(source, R.id.li1, "field 'li1' and method 'onViewClicked'");
    target.li1 = Utils.castView(view, R.id.li1, "field 'li1'", LinearLayout.class);
    view2131296859 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.li2 = Utils.findRequiredViewAsType(source, R.id.li2, "field 'li2'", LinearLayout.class);
    target.phone = Utils.findRequiredViewAsType(source, R.id.phone, "field 'phone'", TextView.class);
    view = Utils.findRequiredView(source, R.id.li3, "field 'li3' and method 'onViewClicked'");
    target.li3 = Utils.castView(view, R.id.li3, "field 'li3'", LinearLayout.class);
    view2131296865 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.li4, "field 'li4' and method 'onViewClicked'");
    target.li4 = Utils.castView(view, R.id.li4, "field 'li4'", LinearLayout.class);
    view2131296869 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.li3Text = Utils.findRequiredViewAsType(source, R.id.li3_text, "field 'li3Text'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserAnQuanActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.headTextTitle = null;
    target.headImgLeft = null;
    target.li1 = null;
    target.li2 = null;
    target.phone = null;
    target.li3 = null;
    target.li4 = null;
    target.li3Text = null;

    view2131296671.setOnClickListener(null);
    view2131296671 = null;
    view2131296859.setOnClickListener(null);
    view2131296859 = null;
    view2131296865.setOnClickListener(null);
    view2131296865 = null;
    view2131296869.setOnClickListener(null);
    view2131296869 = null;
  }
}
