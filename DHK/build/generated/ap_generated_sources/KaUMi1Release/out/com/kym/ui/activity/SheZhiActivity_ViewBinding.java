// Generated code from Butter Knife. Do not modify!
package com.kym.ui.activity;

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

public class SheZhiActivity_ViewBinding implements Unbinder {
  private SheZhiActivity target;

  private View view2131296669;

  private View view2131296402;

  private View view2131296857;

  private View view2131296860;

  private View view2131297395;

  private View view2131297668;

  @UiThread
  public SheZhiActivity_ViewBinding(SheZhiActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SheZhiActivity_ViewBinding(final SheZhiActivity target, View source) {
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
    view = Utils.findRequiredView(source, R.id.cache, "field 'cache' and method 'onViewClicked'");
    target.cache = Utils.castView(view, R.id.cache, "field 'cache'", TextView.class);
    view2131296402 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.banben = Utils.findRequiredViewAsType(source, R.id.banben, "field 'banben'", TextView.class);
    view = Utils.findRequiredView(source, R.id.li1, "field 'li1' and method 'onViewClicked'");
    target.li1 = Utils.castView(view, R.id.li1, "field 'li1'", LinearLayout.class);
    view2131296857 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.li2, "field 'li2' and method 'onViewClicked'");
    target.li2 = Utils.castView(view, R.id.li2, "field 'li2'", LinearLayout.class);
    view2131296860 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tuichu, "field 'tuichu' and method 'onViewClicked'");
    target.tuichu = Utils.castView(view, R.id.tuichu, "field 'tuichu'", LinearLayout.class);
    view2131297395 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.zhuxiao, "field 'zhuxiao' and method 'onViewClicked'");
    target.zhuxiao = Utils.castView(view, R.id.zhuxiao, "field 'zhuxiao'", LinearLayout.class);
    view2131297668 = view;
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
    SheZhiActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.headTextTitle = null;
    target.headImgLeft = null;
    target.cache = null;
    target.banben = null;
    target.li1 = null;
    target.li2 = null;
    target.tuichu = null;
    target.zhuxiao = null;

    view2131296669.setOnClickListener(null);
    view2131296669 = null;
    view2131296402.setOnClickListener(null);
    view2131296402 = null;
    view2131296857.setOnClickListener(null);
    view2131296857 = null;
    view2131296860.setOnClickListener(null);
    view2131296860 = null;
    view2131297395.setOnClickListener(null);
    view2131297395 = null;
    view2131297668.setOnClickListener(null);
    view2131297668 = null;
  }
}
