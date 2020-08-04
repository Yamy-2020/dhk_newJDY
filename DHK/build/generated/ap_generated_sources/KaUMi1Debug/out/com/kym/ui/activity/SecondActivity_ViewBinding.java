// Generated code from Butter Knife. Do not modify!
package com.kym.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.kym.ui.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SecondActivity_ViewBinding implements Unbinder {
  private SecondActivity target;

  @UiThread
  public SecondActivity_ViewBinding(SecondActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SecondActivity_ViewBinding(SecondActivity target, View source) {
    this.target = target;

    target.llTab = Utils.findRequiredViewAsType(source, R.id.ll_tab, "field 'llTab'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SecondActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.llTab = null;
  }
}
