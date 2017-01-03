package com.cretin.www.carchargeserver.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cretin.www.carchargeserver.R;
import com.cretin.www.carchargeserver.base.BaseFragment;
import com.qy.easyframe.common.ResultSubscriber;
import com.qy.easyframe.model.IModel;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends BaseFragment implements ResultSubscriber.OnResultListener {
    @Bind( R.id.btn )
    Button btn;
    @Bind( R.id.textview )
    TextView textview;
    private int code = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onStart(int requestType) {
        Log.e("HHHHHHHHH", "onStart");
        textview.setText("");
    }

    @Override
    public void onError(int requestType, Throwable e) {
        Log.e("HHHHHHHHH", "onError");
        textview.setText("网络错误");
    }

    @Override
    public void onResult(IModel model, int requestType) {
        Log.e("HHHHHHHHH", "onResult");
        if ( requestType == code ) {
        }
    }
}
