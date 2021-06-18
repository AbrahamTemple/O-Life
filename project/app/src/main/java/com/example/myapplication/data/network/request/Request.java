package com.example.myapplication.data.network.request;

import com.example.myapplication.domain.RegisterDto;
import com.example.myapplication.vo.Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 封装请求的接口
 */

public interface Request {

    String HOST = "http://124.71.82.74:8079";

    @GET("/base/wrr/phone")
    Observable<ResponseBody> getPhone(@Query("access_token") String token);

    @GET("/base/oh/all")
    Observable<ResponseBody> getAllHospital(@QueryMap Map<String, String> headers);

    @GET("/base/oe/all")
    Observable<ResponseBody> getAllDoctor(@QueryMap Map<String, String> headers);

    @GET("/base/oe/get/{id}")
    Observable<ResponseBody> getHospitalDoctor(@Path("id") Long id, @Query("access_token") String token);

    @GET("/base/os/all")
    Observable<ResponseBody> getAllStaff(@QueryMap Map<String,String> headers);

    @GET("/base/ou/get")
    Observable<ResponseBody> getLoginUser(@Query("access_token") String token);

    @GET("/auth/o/pass")
    Observable<ResponseBody> loginAuth(@QueryMap Map<String,String> map);

    @GET("/admin/ac/get/{id}")
    Observable<ResponseBody> RedisEscort(@Path("id") Long id,@Header("Authorization") String header,@Query("access_token") String token);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/admin/re/set")
    Observable<ResponseBody> RegisterSet(@Body RegisterDto dao,@Header("Authorization") String header,@Query("access_token") String token);

    @GET("/admin/re/get/{id}")
    Observable<ResponseBody> RegisterGet(@Path("id") Long id,@Header("Authorization") String header,@Query("access_token") String token);


}
