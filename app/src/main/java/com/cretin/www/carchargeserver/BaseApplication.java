package com.cretin.www.carchargeserver;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.qy.easyframe.common.AppFrame;

/**
 * Created by wanglei on 2016/12/9.
 */

public class BaseApplication extends Application {
    private static Context context;
    private static int mainTid;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();

        mainTid = android.os.Process.myTid();
        handler = new Handler();
        context = getApplicationContext();

        AppFrame.initDebug(true);

        //初始化Hawk
        initHawk();
    }

    //手动配置Hawk
    private void initHawk() {
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();
    }

    public static Context getContext() {
        return context;
    }

    public static int getMainTid() {
        return mainTid;
    }

    public static Handler getHandler() {
        return handler;
    }
}
