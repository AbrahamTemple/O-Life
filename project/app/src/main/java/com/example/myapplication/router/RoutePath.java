package com.example.myapplication.router;

import androidx.annotation.NonNull;

/**
 * 所有的路由页面
 */
public enum RoutePath {

    START("/olife/start",false),
    LOGIN("/olife/login",false),
    HOME("/olife/home",false),
    ESCORT("/olife/escort",true),
    CALL("/olife/call",true),
    REGISTER("/olife/register",true),
    CHAT("/olife/chat",true),
    LIST("/olife/list",true),
    ORDER("/olife/order",true),
    DETAIL("/olife/detail",true);

    private final String path;
    private final Boolean interceptor;

    RoutePath(String path, Boolean interceptor) {
        this.path = path;
        this.interceptor = interceptor;
    }

    /**
     * @return 是否被拦截
     */
    public Boolean value() {
        return interceptor;
    }



    /**
     * @return 路由路径
     */
    @NonNull
    @Override
    public String toString() {
        return path;
    }

    /**
     * 用于生成switch...case所必须的常量
     * @param path 路径
     * @return 路径常量
     */
    public static RoutePath getByPath(String path){
        for(RoutePath routePath:values()){
            if (routePath.toString().equals(path)){
                return routePath;
            }
        }
        return null;
    }
}
