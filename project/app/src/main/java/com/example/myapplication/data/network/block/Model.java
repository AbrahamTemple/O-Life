package com.example.myapplication.data.network.block;

import com.example.myapplication.data.network.bean.NetWorkManager;
import com.example.myapplication.domain.RegisterDto;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class Model implements Contract.Model{

    @Override
    public Observable<ResponseBody> getPhone(String token) {
        return NetWorkManager.getRequest().getPhone(token);
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
    public Observable<ResponseBody> getLoginUser(String token) {
        return NetWorkManager.getRequest().getLoginUser(token);
    }

    @Override
    public Observable<ResponseBody> getHospitalDoctor(Long id, String token) {
        return NetWorkManager.getRequest().getHospitalDoctor(id,token);
    }

    @Override
    public Observable<ResponseBody> RedisEscort(Long id,String header,String token) {
        return NetWorkManager.getRequest().RedisEscort(id,header,token);
    }

    @Override
    public Observable<ResponseBody> RegisterSet(RegisterDto dao,String header,String token) {
        return NetWorkManager.getRequest().RegisterSet(dao,header,token);
    }

    @Override
    public Observable<ResponseBody> RegisterGet(Long id,String header,String token) {
        return NetWorkManager.getRequest().RegisterGet(id,header,token);
    }
}
