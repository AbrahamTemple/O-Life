package com.example.myapplication;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class MyApplication extends Application {

    private boolean isDebugARouter = true;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public void init(){
        if (isDebugARouter) {
            //必须在init之前
            ARouter.openLog();//打印日志
            ARouter.openDebug();//线上版本需要关闭
        }
        //官方推荐在Application中初始化
        ARouter.init(this);
    }

}
