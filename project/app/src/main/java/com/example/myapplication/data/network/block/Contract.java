package com.example.myapplication.data.network.block;

import com.example.myapplication.domain.RegisterDto;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class Contract {
    public interface Persenter {
        public void getCarList(String userId);
    }

    public interface View {
        void getDataSuccess(ResponseBody body);
        void getDataFail(Throwable throwable);
    }

    public interface Model {
        Observable<ResponseBody> getPhone(String token);
        Observable<ResponseBody> getAllHospital(Map<String, String> headers);
        Observable<ResponseBody> getAllDoctor(Map<String, String> headers);
        Observable<ResponseBody> getAllStaff(Map<String,String> headers);
        Observable<ResponseBody> loginAuth(Map<String,String> map);
        Observable<ResponseBody> getLoginUser(String token);
        Observable<ResponseBody> getHospitalDoctor(Long id, String token);
        Observable<ResponseBody> RedisEscort(Long id,String header,String token);
        Observable<ResponseBody> RegisterSet(RegisterDto dao,String header,String token);
        Observable<ResponseBody> RegisterGet(Long id,String header,String token);
}
}
