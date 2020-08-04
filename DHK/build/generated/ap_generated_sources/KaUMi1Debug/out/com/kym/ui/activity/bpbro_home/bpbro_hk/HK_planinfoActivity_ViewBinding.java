// Generated code from Butter Knife. Do not modify!
package com.kym.ui.activity.bpbro_home.bpbro_hk;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
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

public class HK_planinfoActivity_ViewBinding implements Unbinder {
  private HK_planinfoActivity target;

  private View view2131296671;

  @UiThread
  public HK_planinfoActivity_ViewBinding(HK_planinfoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HK_planinfoActivity_ViewBinding(final HK_planinfoActivity target, View source) {
    this.target = target;

    View view;
    target.headTextTitle = Utils.findRequiredViewAsType(source, R.id.head_text_title, "field 'headTextTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.head_img_left, "field 'headImgLeft' and method 'onViewClicked'");
    target.headImgLeft = Utils.castView(view, R.id.head_img_left, "field 'headImgLeft'", ImageView.class);
    view2131296671 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.rvBankList = Utils.findRequiredViewAsType(source, R.id.rv_bank_list, "field 'rvBankList'", RecyclerView.class);
    target.zanwu = Utils.findRequiredViewAsType(source, R.id.zanwu, "field 'zanwu'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HK_planinfoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.headTextTitle = null;
    target.headImgLeft = null;
    target.rvBankList = null;
    target.zanwu = null;

    view2131296671.setOnClickListener(null);
    view2131296671 = null;
  }
}
