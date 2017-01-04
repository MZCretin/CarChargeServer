package com.cretin.www.carchargeserver.manager;

import android.os.Bundle;
import android.view.View;

import com.cretin.www.carchargeserver.R;
import com.cretin.www.carchargeserver.base.BackFragmentActivity;
import com.cretin.www.carchargeserver.base.BaseFragment;
import com.cretin.www.carchargeserver.fragment.LoginRegisterFragment;


public class LoginRegisterManger extends BackFragmentActivity<Bundle> {

    @Override
    protected int getFragmentContentId() {
        return R.id.fragment_container;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        BaseFragment fragment = null;
        if ( LoginRegisterFragment.TAG.equals(tag_fragment) ) {
            fragment = new LoginRegisterFragment();
        }

        return fragment;
    }

    @Override
    protected void initView(View view) {

    }
}
