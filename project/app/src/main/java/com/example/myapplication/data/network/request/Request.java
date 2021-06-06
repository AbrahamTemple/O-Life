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
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 封装请求的接口
 */

public interface Request {

//    public static String HOST = "https://www.wanandroid.com";

//    @POST("/banner/json")
//    Observable<Response<List<BlogResponse.Blog>>> setBlog(@QueryMap Map<String, String> map);

    String HOST = "http://121.37.178.107:8079";

    @GET("/base/wrr/phone")
    Observable<ResponseBody> getPhone(@QueryMap Map<String, String> headers);

    @GET("/base/oh/all")
    Observable<ResponseBody> getAllHospital(@QueryMap Map<String, String> headers);

    @GET("/base/oe/all")
    Observable<ResponseBody> getAllDoctor(@QueryMap Map<String, String> headers);

    @GET("/base/oe/get/{id}")
    Observable<ResponseBody> getHospitalDoctor(@Path("id") Long id, @Query("access_token") String token);

    @GET("/base/os/all")
    Observable<ResponseBody> getAllStaff(@QueryMap Map<String,String> headers);

    @GET("/auth/o/pass")
    Observable<ResponseBody> loginAuth(@QueryMap Map<String,String> map);
}
