package com.example.myapplication.app;

import android.app.Activity;
import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.data.network.bean.NetWorkManager;

import java.util.Stack;

public class MyApplication extends Application {

    private static MyApplication singleton;
    private static Stack<Activity> activityStack;
    private boolean isDebugARouter = true;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        initRouter();
        initNetWork();
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

    public void initNetWork(){
        NetWorkManager.getInstance().init();
    }

    public static MyApplication getInstance() {
        return singleton;
    }

    /**
     * 添加Activity到栈
     * @param activity 页面
     */
    public void addActivity(Activity activity){
        if(activityStack ==null){
            activityStack =new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 结束指定类名的Activity
     * @param activity 页面
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
