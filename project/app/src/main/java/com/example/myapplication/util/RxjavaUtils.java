package com.example.myapplication.util;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxjavaUtils<T> {

    private String TAG = "RxjavaUtils";

    Observable<T> observable; //被观察者
    Observer<T> observer; //观察者
    List<T> list;


    public RxjavaUtils(List<T> list) {
        this.list = list;
    }

    public void init(){
        initObservable();
        initObserver();
        observable.safeSubscribe(observer);
    }

    private void initObservable(){
        observable = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                for (T item:list) {
                    emitter.onNext(item);
                }
                emitter.onComplete();
            }
        });
    }

    private void initObserver(){
        observer = new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(T value) {
                Log.d(TAG, "对Next事件作出响应" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        };
    }



}
