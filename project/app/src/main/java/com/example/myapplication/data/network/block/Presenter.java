package com.example.myapplication.data.network.block;


import com.example.myapplication.data.network.scheduler.BaseSchedulerProvider;
import com.example.myapplication.vo.ResponseTransformer;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
        headers.put("Authorization","Bearer " + token);
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
        headers.put("Authorization","Bearer " + token);
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
        headers.put("Authorization","Bearer " + token);
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
}
