package com.example.myapplication.data.network.config;

import com.example.myapplication.data.network.request.Request;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  API初始化类
 */
public class NetWorkInit {

    private static NetWorkInit mInstance;
    private static Retrofit retrofit;
    private static volatile Request request = null;
    private static boolean ok = false;

    public static NetWorkInit getInstance() {
        if (mInstance == null) {
            synchronized (NetWorkInit.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkInit();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        // 初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Request.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Request getRequest() {
        if (request == null) {
            synchronized (Request.class) {
                request = retrofit.create(Request.class);
            }
        }
        return request;
    }

    public static void setOk(boolean ok) {
        NetWorkInit.ok = ok;
    }

    public static boolean isOk() {
        return ok;
    }
}
