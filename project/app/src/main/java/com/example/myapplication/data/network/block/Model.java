package com.example.myapplication.data.network.block;

import com.example.myapplication.data.model.BannerResponse;
import com.example.myapplication.data.network.bean.NetWorkManager;
import com.example.myapplication.vo.Response;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class Model implements Contract.Model{

    @Override
    public Observable<ResponseBody> rxBanner() {
        return NetWorkManager.getRequest().rxBanner();
    }
}
