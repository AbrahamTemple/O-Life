package com.example.myapplication.data.network.block;


import com.example.myapplication.data.network.scheduler.BaseSchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Presenter {
    private Model model;

    private Contract.View view;

    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable mDisposable;

    public Presenter(Model model,
                     Contract.View view,
                     BaseSchedulerProvider schedulerProvider) {
        this.model = model;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        mDisposable = new CompositeDisposable();

    }

    public void despose(){
        mDisposable.dispose();
    }

    public void getPone(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("access_token",token);
        Disposable disposable = model.getPhone(headers)
//                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
                .subscribe(data -> {
                    view.getDataSuccess(data);
                }, throwable -> {
                    // 处理异常
                    view.getDataFail(throwable);
                });
        mDisposable.add(disposable);
    }

    public void getAllHospital(String token){
        Map<String, String> headers = new HashMap<>();
        headers.put("access_token",token);
        Disposable disposable = model.getAllHospital(headers)
//                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
                .subscribe(data -> {
                    view.getDataSuccess(data);
                }, throwable -> {
                    // 处理异常
                    view.getDataFail(throwable);
                });
        mDisposable.add(disposable);
    }

    public void getAllDoctor(String token){
        Map<String, String> headers = new HashMap<>();
        headers.put("access_token",token);
        Disposable disposable = model.getAllDoctor(headers)
//                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
                .subscribe(data -> {
                    view.getDataSuccess(data);
                }, throwable -> {
                    // 处理异常
                    view.getDataFail(throwable);
                });
        mDisposable.add(disposable);
    }

    public void loginAuth(String name,String pass){
        Map<String, String> param = new HashMap<>();
        param.put("username",name);
        param.put("password",pass);
        param.put("clientId","cli");
        param.put("clientSecret","sec");
        param.put("access_token","");
        Disposable disposable = model.loginAuth(param)
//                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
                .subscribe(data -> {
                    view.getDataSuccess(data);
                }, throwable -> {
                    // 处理异常
                    view.getDataFail(throwable);
                });
        mDisposable.add(disposable);
    }
}
