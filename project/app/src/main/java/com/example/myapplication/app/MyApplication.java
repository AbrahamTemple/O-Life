package com.example.myapplication.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class MyApplication extends Application {

    private boolean isDebugARouter = true;

    @Override
    public void onCreate() {
        super.onCreate();
        initRouter();
    }

    public void initRouter(){
        if (isDebugARouter) {
            //必须在init之前
            ARouter.openLog();//打印日志
            ARouter.openDebug();//线上版本需要关闭
        }
        //在Application中初始化路由
        ARouter.init(this);
    }

}
