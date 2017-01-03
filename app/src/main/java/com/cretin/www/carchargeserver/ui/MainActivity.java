package com.cretin.www.carchargeserver.ui;

import android.view.KeyEvent;
import android.view.View;

import com.cretin.www.carchargeserver.R;
import com.cretin.www.carchargeserver.base.BaseActivity;
import com.cretin.www.carchargeserver.utils.UiUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    private long lastBackTime;

    //在需要监听的activity中重写onKeyDown()。
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0 ) {
            long currentTime = System.currentTimeMillis();
            if ( currentTime - lastBackTime > 1 * 1000 ) {
                lastBackTime = currentTime;
                UiUtils.showToastInAnyThread("再按一次退出程序");
            } else {
                MainActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
