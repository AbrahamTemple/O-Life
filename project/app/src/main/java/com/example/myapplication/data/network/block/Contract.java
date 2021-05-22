package com.example.myapplication.data.network.block;

import com.example.myapplication.data.model.BannerResponse;
import com.example.myapplication.vo.Response;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class Contract {
    public interface Persenter {
        public void getCarList(String userId);
    }

    public interface View {
        void getDataSuccess(ResponseBody body);
        void getDataFail();
    }

    public interface Model {
        Observable<ResponseBody> rxBanner();
    }
}
