// Generated code from Butter Knife. Do not modify!
package com.fangrongjie.www.srjie.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TestFragment$$ViewBinder<T extends com.fangrongjie.www.srjie.fragment.TestFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427422, "field 'btn'");
    target.btn = finder.castView(view, 2131427422, "field 'btn'");
    view = finder.findRequiredView(source, 2131427423, "field 'textview'");
    target.textview = finder.castView(view, 2131427423, "field 'textview'");
  }

  @Override public void unbind(T target) {
    target.btn = null;
    target.textview = null;
  }
}
