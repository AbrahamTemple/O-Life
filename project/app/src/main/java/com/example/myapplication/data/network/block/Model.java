package com.example.myapplication.data.network.block;

import com.example.myapplication.data.network.bean.NetWorkManager;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class Model implements Contract.Model{

    @Override
    public Observable<ResponseBody> getPhone(Map<String, String> headers) {
        return NetWorkManager.getRequest().getPhone(headers);
    }

    @Override
    public Observable<ResponseBody> getAllHospital(Map<String, String> headers) {
        return NetWorkManager.getRequest().getAllHospital(headers);
    }
}
