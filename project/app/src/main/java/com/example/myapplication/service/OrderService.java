package com.example.myapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.data.model.BoolResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.data.network.block.Model;
import com.example.myapplication.data.network.block.Presenter;
import com.example.myapplication.data.network.scheduler.SchedulerProvider;
import com.example.myapplication.domain.RegisterDto;
import com.example.myapplication.router.LoginCallbackImpl;
import com.example.myapplication.router.RoutePath;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.rabbitmq.tools.json.JSONUtil;

import java.io.IOException;

import okhttp3.ResponseBody;


public class OrderService extends IntentService implements Contract.View{

    private static final String ACTION_POST = "com.example.myapplication.service.action.POST";

    private static final String EXTRA_REGISTER = "com.example.myapplication.service.extra.REGISTER";

    private Presenter presenter;
    private SharedPreferencesUtils tokenShared;

    public OrderService() {
        super("OrderService");
        init();
    }

    private  void init(){
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
        tokenShared = SharedPreferencesUtils.init(this,"oauth");
    }

    public static void startRequest(Context context, String dto) {
        Intent intent = new Intent(context, OrderService.class);
        intent.setAction(ACTION_POST);
        intent.putExtra(EXTRA_REGISTER, dto);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (intent.getAction().equals(ACTION_POST)) {
                String body = intent.getStringExtra(EXTRA_REGISTER);
                RegisterDto dto = GsonUtils.fromJson(body, RegisterDto.class);
                presenter.RegisterSet(dto,tokenShared.getString("token"));
            }
        }
    }

    @Override
    public void getDataSuccess(ResponseBody body) {
        try {
            String result = body.string();
            Log.e("网络请求", "响应结果: " + result);
            BoolResponse bool = GsonUtils.fromJson(result, BoolResponse.class);
            if (bool.getData()){
                ARouter.getInstance().build(RoutePath.ORDER.toString()).navigation(this,new LoginCallbackImpl());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }
}
