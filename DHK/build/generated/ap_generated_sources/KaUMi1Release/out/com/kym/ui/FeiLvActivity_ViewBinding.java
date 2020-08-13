// Generated code from Butter Knife. Do not modify!
package com.kym.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FeiLvActivity_ViewBinding implements Unbinder {
  private FeiLvActivity target;

  private View view2131296669;

  @UiThread
  public FeiLvActivity_ViewBinding(FeiLvActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FeiLvActivity_ViewBinding(final FeiLvActivity target, View source) {
    this.target = target;

    View view;
    target.headTextTitle = Utils.findRequiredViewAsType(source, R.id.head_text_title, "field 'headTextTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.head_img_left, "field 'headImgLeft' and method 'onViewClicked'");
    target.headImgLeft = Utils.castView(view, R.id.head_img_left, "field 'headImgLeft'", ImageView.class);
    view2131296669 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.dengji = Utils.findRequiredViewAsType(source, R.id.dengji, "field 'dengji'", TextView.class);
    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", TextView.class);
    target.rate = Utils.findRequiredViewAsType(source, R.id.rate, "field 'rate'", TextView.class);
    target.fee = Utils.findRequiredViewAsType(source, R.id.fee, "field 'fee'", TextView.class);
    target.feilvBox = Utils.findRequiredViewAsType(source, R.id.feilv_box, "field 'feilvBox'", RecyclerView.class);
    target.zhiji = Utils.findRequiredViewAsType(source, R.id.zhiji, "field 'zhiji'", TextView.class);
    target.jyjl = Utils.findRequiredViewAsType(source, R.id.jyjl, "field 'jyjl'", TextView.class);
    target.ztjl = Utils.findRequiredViewAsType(source, R.id.ztjl, "field 'ztjl'", TextView.class);
    target.tdjl = Utils.findRequiredViewAsType(source, R.id.tdjl, "field 'tdjl'", TextView.class);
    target.rvBankList = Utils.findRequiredViewAsType(source, R.id.rv_bank_list, "field 'rvBankList'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FeiLvActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.headTextTitle = null;
    target.headImgLeft = null;
    target.dengji = null;
    target.name = null;
    target.rate = null;
    target.fee = null;
    target.feilvBox = null;
    target.zhiji = null;
    target.jyjl = null;
    target.ztjl = null;
    target.tdjl = null;
    target.rvBankList = null;

    view2131296669.setOnClickListener(null);
    view2131296669 = null;
  }
}
