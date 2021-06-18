package com.example.myapplication.router;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.example.myapplication.util.SharedPreferencesUtils;

import java.util.Objects;

@Interceptor(name = "login",priority = 6)
public class LoginInterceptorImpl implements IInterceptor {

    private SharedPreferencesUtils tokenShared;

    /**
     *  callback.onContinue(postcard)：放行所有
     *  callback.onInterrupt(null)：拦截所有
     * @param postcard 路由表
     * @param callback 拦截回调
     */
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        Log.i("拦截器截获的路由",path);
        if (isNotLogin()) {
            switch (Objects.requireNonNull(RoutePath.getByPath(path))) {
                case START:
                case HOME:
                case LOGIN:
                    Log.d("拦截器","不需要登录的路由");
                    callback.onContinue(postcard);
                    break;
                default:
                    Log.d("拦截器","需要登录的路由");
                    callback.onInterrupt(null);
                    break;
            }
        } else {
            Log.i("当前的登录令牌",tokenShared.getString("token"));
            callback.onContinue(postcard);// 已登录了不再拦截
        }
    }

    /**
     * @param context 当前拦截的上下文
     */
    @Override
    public void init(Context context) {
        tokenShared = SharedPreferencesUtils.init(context,"oauth");
    }

    private boolean isNotLogin() {
//        String accessToken = BuildConfig.ACCESS_TOKEN;
        return tokenShared.getString("token").isEmpty();
    }
}
