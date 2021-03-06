package com.example.myapplication.data.network.block;


import com.example.myapplication.data.network.scheduler.BaseSchedulerProvider;
import com.example.myapplication.domain.RegisterDto;

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
        Disposable disposable = model.getPhone(token)
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

    public void getAllStaff(String token){
        Map<String, String> headers = new HashMap<>();
        headers.put("access_token",token);
        Disposable disposable = model.getAllStaff(headers)
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

    public void getLoginUser(String token){
        Disposable disposable = model.getLoginUser(token)
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


    public void loginAuth(String name,String pass,String cli,String sec){
        Map<String, String> param = new HashMap<>();
        param.put("username",name);
        param.put("password",pass);
        param.put("clientId",cli);
        param.put("clientSecret",sec);
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

    public void getHospitalDoctor(Long id,String token){
        Disposable disposable = model.getHospitalDoctor(id, token)
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

    public void RedisEscort(Long id,String token){
        Disposable disposable = model.RedisEscort(id,"Bearer "+token,token)
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

    public void RegisterSet(RegisterDto dao, String token){
        Disposable disposable = model.RegisterSet(dao,"Bearer "+token,token)
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

    public void RegisterGet(Long id, String token){
        Disposable disposable = model.RegisterGet(id,"Bearer "+token,token)
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
