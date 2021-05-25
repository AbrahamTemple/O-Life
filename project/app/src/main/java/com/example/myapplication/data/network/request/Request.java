package com.example.myapplication.data.network.request;

import com.example.myapplication.vo.Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 封装请求的接口
 */

public interface Request {

//    public static String HOST = "https://www.wanandroid.com";

//    @POST("/banner/json")
//    Observable<Response<List<BlogResponse.Blog>>> setBlog(@QueryMap Map<String, String> map);

//    @GET("banner/json")
//    Call<ResponseBody> getBanner();

    public static String HOST = "http://124.71.185.137:8081";

    @GET("/wrr/phone")
    Observable<ResponseBody> getPhone(@HeaderMap Map<String, String> headers);

    @GET("/oh/all")
    Observable<ResponseBody> getAllHospital(@HeaderMap Map<String, String> headers);
}
