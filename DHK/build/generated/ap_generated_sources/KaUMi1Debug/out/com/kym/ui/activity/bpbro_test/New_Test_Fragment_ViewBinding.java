// Generated code from Butter Knife. Do not modify!
package com.kym.ui.activity.bpbro_test;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.donkingliang.banner.CustomBanner;
import com.kym.ui.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class New_Test_Fragment_ViewBinding implements Unbinder {
  private New_Test_Fragment target;

  private View view2131297581;

  private View view2131297582;

  private View view2131297583;

  private View view2131297584;

  private View view2131296877;

  private View view2131296889;

  @UiThread
  public New_Test_Fragment_ViewBinding(final New_Test_Fragment target, View source) {
    this.target = target;

    View view;
    target.banner = Utils.findRequiredViewAsType(source, R.id.banner, "field 'banner'", CustomBanner.class);
    view = Utils.findRequiredView(source, R.id.view_new_home_1, "field 'viewNewHome1' and method 'onViewClicked'");
    target.viewNewHome1 = view;
    view2131297581 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.view_new_home_2, "field 'viewNewHome2' and method 'onViewClicked'");
    target.viewNewHome2 = view;
    view2131297582 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.view_new_home_3, "field 'viewNewHome3' and method 'onViewClicked'");
    target.viewNewHome3 = view;
    view2131297583 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.view_new_home_4, "field 'viewNewHome4' and method 'onViewClicked'");
    target.viewNewHome4 = view;
    view2131297584 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.li_course, "field 'liCourse' and method 'onViewClicked'");
    target.liCourse = Utils.castView(view, R.id.li_course, "field 'liCourse'", LinearLayout.class);
    view2131296877 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.li_problem, "field 'liProblem' and method 'onViewClicked'");
    target.liProblem = Utils.castView(view, R.id.li_problem, "field 'liProblem'", LinearLayout.class);
    view2131296889 = view;
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
    New_Test_Fragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.banner = null;
    target.viewNewHome1 = null;
    target.viewNewHome2 = null;
    target.viewNewHome3 = null;
    target.viewNewHome4 = null;
    target.liCourse = null;
    target.liProblem = null;

    view2131297581.setOnClickListener(null);
    view2131297581 = null;
    view2131297582.setOnClickListener(null);
    view2131297582 = null;
    view2131297583.setOnClickListener(null);
    view2131297583 = null;
    view2131297584.setOnClickListener(null);
    view2131297584 = null;
    view2131296877.setOnClickListener(null);
    view2131296877 = null;
    view2131296889.setOnClickListener(null);
    view2131296889 = null;
  }
}
