// Generated code from Butter Knife. Do not modify!
package com.cretin.www.carchargeserver.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TestFragment$$ViewBinder<T extends com.cretin.www.carchargeserver.fragment.TestFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493020, "field 'btn'");
    target.btn = finder.castView(view, 2131493020, "field 'btn'");
    view = finder.findRequiredView(source, 2131493021, "field 'textview'");
    target.textview = finder.castView(view, 2131493021, "field 'textview'");
  }

  @Override public void unbind(T target) {
    target.btn = null;
    target.textview = null;
  }
}
