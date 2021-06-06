package com.example.myapplication.router;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 拦截器回调函数1
 */
public class LoginCallbackImpl implements NavigationCallback {
    @Override
    public void onFound(Postcard postcard) {

    }

    @Override
    public void onLost(Postcard postcard) {

    }

    @Override
    public void onArrival(Postcard postcard) {

    }

    @Override
    public void onInterrupt(Postcard postcard) {
        Log.i("拦截器回调接收的路由",postcard.getPath());
        // 拦截下来的要去登录
        ARouter.getInstance().build(RoutePath.LOGIN.toString()).navigation();
    }
}
