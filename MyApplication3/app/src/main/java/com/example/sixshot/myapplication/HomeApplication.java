package com.example.sixshot.myapplication;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by sixshot on 2021.5.2.
 */

public class HomeApplication extends Application {
    private boolean isDebugArouter = true;
    @Override
    public void onCreate() {
        super.onCreate();
        if (isDebugArouter) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
