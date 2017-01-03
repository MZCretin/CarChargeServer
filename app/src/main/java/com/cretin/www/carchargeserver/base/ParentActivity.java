package com.cretin.www.carchargeserver.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cretin on 16/10/27.
 */

public abstract class ParentActivity extends AppCompatActivity {
    //记录下所有的Activity
    public final static List<ParentActivity> mActivities = new LinkedList<ParentActivity>();
    public static boolean isKitkat;
    public static ParentActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized ( mActivities ) {
            mActivities.add(this);
            for ( ParentActivity p :
                    mActivities ) {
            }
        }
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            isKitkat = true;
        }
        initView(null);
    }


    protected abstract void initView(View view);

    @Override
    protected void onResume() {
        super.onResume();
        mActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized ( mActivities ) {
            mActivities.remove(this);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
