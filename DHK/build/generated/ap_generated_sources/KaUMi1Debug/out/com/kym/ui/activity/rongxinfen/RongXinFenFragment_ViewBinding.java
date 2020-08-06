// Generated code from Butter Knife. Do not modify!
package com.kym.ui.activity.rongxinfen;

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

public class RongXinFenFragment_ViewBinding implements Unbinder {
  private RongXinFenFragment target;

  private View view2131297638;

  private View view2131297641;

  private View view2131297639;

  private View view2131297640;

  @UiThread
  public RongXinFenFragment_ViewBinding(final RongXinFenFragment target, View source) {
    this.target = target;

    View view;
    target.rxImg = Utils.findRequiredViewAsType(source, R.id.rx_img, "field 'rxImg'", ImageView.class);
    target.score = Utils.findRequiredViewAsType(source, R.id.score, "field 'score'", TextView.class);
    target.levelName = Utils.findRequiredViewAsType(source, R.id.level_name, "field 'levelName'", TextView.class);
    target.waitCount = Utils.findRequiredViewAsType(source, R.id.wait_count, "field 'waitCount'", TextView.class);
    target.keepCount = Utils.findRequiredViewAsType(source, R.id.keep_count, "field 'keepCount'", TextView.class);
    target.nokeepCount = Utils.findRequiredViewAsType(source, R.id.nokeep_count, "field 'nokeepCount'", TextView.class);
    view = Utils.findRequiredView(source, R.id.xinyong_guanli, "field 'xinyongGuanli' and method 'onViewClicked'");
    target.xinyongGuanli = Utils.castView(view, R.id.xinyong_guanli, "field 'xinyongGuanli'", LinearLayout.class);
    view2131297638 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.xinyong_zuji, "field 'xinyongZuji' and method 'onViewClicked'");
    target.xinyongZuji = Utils.castView(view, R.id.xinyong_zuji, "field 'xinyongZuji'", LinearLayout.class);
    view2131297641 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.xinyong_guize, "field 'xinyongGuize' and method 'onViewClicked'");
    target.xinyongGuize = Utils.castView(view, R.id.xinyong_guize, "field 'xinyongGuize'", LinearLayout.class);
    view2131297639 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.xinyong_shenghuo, "field 'xinyongShenghuo' and method 'onViewClicked'");
    target.xinyongShenghuo = Utils.castView(view, R.id.xinyong_shenghuo, "field 'xinyongShenghuo'", LinearLayout.class);
    view2131297640 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.rxTitle = Utils.findRequiredViewAsType(source, R.id.rx_title, "field 'rxTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RongXinFenFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rxImg = null;
    target.score = null;
    target.levelName = null;
    target.waitCount = null;
    target.keepCount = null;
    target.nokeepCount = null;
    target.xinyongGuanli = null;
    target.xinyongZuji = null;
    target.xinyongGuize = null;
    target.xinyongShenghuo = null;
    target.rxTitle = null;

    view2131297638.setOnClickListener(null);
    view2131297638 = null;
    view2131297641.setOnClickListener(null);
    view2131297641 = null;
    view2131297639.setOnClickListener(null);
    view2131297639 = null;
    view2131297640.setOnClickListener(null);
    view2131297640 = null;
  }
}
