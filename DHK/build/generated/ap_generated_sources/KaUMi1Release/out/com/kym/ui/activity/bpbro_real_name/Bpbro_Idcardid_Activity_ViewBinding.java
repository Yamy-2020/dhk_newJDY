// Generated code from Butter Knife. Do not modify!
package com.kym.ui.activity.bpbro_real_name;

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

public class Bpbro_Idcardid_Activity_ViewBinding implements Unbinder {
  private Bpbro_Idcardid_Activity target;

  private View view2131296671;

  private View view2131296986;

  private View view2131296336;

  private View view2131296339;

  private View view2131296330;

  private View view2131296333;

  private View view2131296341;

  private View view2131297234;

  @UiThread
  public Bpbro_Idcardid_Activity_ViewBinding(Bpbro_Idcardid_Activity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Bpbro_Idcardid_Activity_ViewBinding(final Bpbro_Idcardid_Activity target, View source) {
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
    target.userName = Utils.findRequiredViewAsType(source, R.id.user_name, "field 'userName'", EditText.class);
    view = Utils.findRequiredView(source, R.id.name_photo, "field 'namePhoto' and method 'onViewClicked'");
    target.namePhoto = Utils.castView(view, R.id.name_photo, "field 'namePhoto'", TextView.class);
    view2131296986 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.idCard = Utils.findRequiredViewAsType(source, R.id.id_card, "field 'idCard'", EditText.class);
    target.bankNo = Utils.findRequiredViewAsType(source, R.id.bank_no, "field 'bankNo'", EditText.class);
    view = Utils.findRequiredView(source, R.id.bank_photo, "field 'bankPhoto' and method 'onViewClicked'");
    target.bankPhoto = Utils.castView(view, R.id.bank_photo, "field 'bankPhoto'", TextView.class);
    view2131296336 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.bankProvince = Utils.findRequiredViewAsType(source, R.id.bank_province, "field 'bankProvince'", TextView.class);
    view = Utils.findRequiredView(source, R.id.bank_province_btn, "field 'bankProvinceBtn' and method 'onViewClicked'");
    target.bankProvinceBtn = Utils.castView(view, R.id.bank_province_btn, "field 'bankProvinceBtn'", LinearLayout.class);
    view2131296339 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.bankCity = Utils.findRequiredViewAsType(source, R.id.bank_city, "field 'bankCity'", TextView.class);
    view = Utils.findRequiredView(source, R.id.bank_city_btn, "field 'bankCityBtn' and method 'onViewClicked'");
    target.bankCityBtn = Utils.castView(view, R.id.bank_city_btn, "field 'bankCityBtn'", LinearLayout.class);
    view2131296330 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.bankName = Utils.findRequiredViewAsType(source, R.id.bank_name, "field 'bankName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.bank_name_btn, "field 'bankNameBtn' and method 'onViewClicked'");
    target.bankNameBtn = Utils.castView(view, R.id.bank_name_btn, "field 'bankNameBtn'", LinearLayout.class);
    view2131296333 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.bankZh = Utils.findRequiredViewAsType(source, R.id.bank_zh, "field 'bankZh'", TextView.class);
    view = Utils.findRequiredView(source, R.id.bank_zh_btn, "field 'bankZhBtn' and method 'onViewClicked'");
    target.bankZhBtn = Utils.castView(view, R.id.bank_zh_btn, "field 'bankZhBtn'", LinearLayout.class);
    view2131296341 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mobileNo = Utils.findRequiredViewAsType(source, R.id.mobile_no, "field 'mobileNo'", EditText.class);
    view = Utils.findRequiredView(source, R.id.submit, "field 'submit' and method 'onViewClicked'");
    target.submit = Utils.castView(view, R.id.submit, "field 'submit'", TextView.class);
    view2131297234 = view;
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
    Bpbro_Idcardid_Activity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.headTextTitle = null;
    target.headImgLeft = null;
    target.userName = null;
    target.namePhoto = null;
    target.idCard = null;
    target.bankNo = null;
    target.bankPhoto = null;
    target.bankProvince = null;
    target.bankProvinceBtn = null;
    target.bankCity = null;
    target.bankCityBtn = null;
    target.bankName = null;
    target.bankNameBtn = null;
    target.bankZh = null;
    target.bankZhBtn = null;
    target.mobileNo = null;
    target.submit = null;

    view2131296671.setOnClickListener(null);
    view2131296671 = null;
    view2131296986.setOnClickListener(null);
    view2131296986 = null;
    view2131296336.setOnClickListener(null);
    view2131296336 = null;
    view2131296339.setOnClickListener(null);
    view2131296339 = null;
    view2131296330.setOnClickListener(null);
    view2131296330 = null;
    view2131296333.setOnClickListener(null);
    view2131296333 = null;
    view2131296341.setOnClickListener(null);
    view2131296341 = null;
    view2131297234.setOnClickListener(null);
    view2131297234 = null;
  }
}
