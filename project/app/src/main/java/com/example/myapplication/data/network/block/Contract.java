package com.example.myapplication.data.network.block;

import com.example.myapplication.data.model.BannerResponse;
import com.example.myapplication.vo.Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.HeaderMap;

public class Contract {
    public interface Persenter {
        public void getCarList(String userId);
    }

    public interface View {
        void getDataSuccess(ResponseBody body);
        void getDataFail(Throwable throwable);
    }

    public interface Model {
        Observable<ResponseBody> getPhone(Map<String, String> headers);
        Observable<ResponseBody> getAllHospital(Map<String, String> headers);
        Observable<ResponseBody> getAllDoctor(Map<String, String> headers);
    }
}
