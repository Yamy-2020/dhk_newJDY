// Generated code from Butter Knife. Do not modify!
package com.kym.ui.activity.bpbro_home.bpbro_hk;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.kym.ui.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HK_prePlanActivity_ViewBinding implements Unbinder {
  private HK_prePlanActivity target;

  private View view2131296669;

  private View view2131296428;

  @UiThread
  public HK_prePlanActivity_ViewBinding(HK_prePlanActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HK_prePlanActivity_ViewBinding(final HK_prePlanActivity target, View source) {
    this.target = target;

    View view;
    target.headTextTitle = Utils.findRequiredViewAsType(source, R.id.head_text_title, "field 'headTextTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.head_img_left, "field 'headImgLeft' and method 'onViewClicked'");
    target.headImgLeft = Utils.castView(view, R.id.head_img_left, "field 'headImgLeft'", ImageView.class);
    view2131296669 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.listViewSj = Utils.findRequiredViewAsType(source, R.id.listView_sj, "field 'listViewSj'", ListView.class);
    view = Utils.findRequiredView(source, R.id.change_card, "field 'changeCard' and method 'onViewClicked'");
    target.changeCard = Utils.castView(view, R.id.change_card, "field 'changeCard'", TextView.class);
    view2131296428 = view;
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
    HK_prePlanActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.headTextTitle = null;
    target.headImgLeft = null;
    target.listViewSj = null;
    target.changeCard = null;

    view2131296669.setOnClickListener(null);
    view2131296669 = null;
    view2131296428.setOnClickListener(null);
    view2131296428 = null;
  }
}
