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

    @Override
    public Observable<ResponseBody> getAllDoctor(Map<String, String> headers) {
        return NetWorkManager.getRequest().getAllDoctor(headers);
    }

    @Override
    public Observable<ResponseBody> getAllStaff(Map<String, String> headers) {
        return NetWorkManager.getRequest().getAllStaff(headers);
    }

    @Override
    public Observable<ResponseBody> loginAuth(Map<String, String> map) {
        return NetWorkManager.getRequest().loginAuth(map);
    }

    @Override
    public Observable<ResponseBody> getHospitalDoctor(Long id, String token) {
        return NetWorkManager.getRequest().getHospitalDoctor(id,token);
    }
}
