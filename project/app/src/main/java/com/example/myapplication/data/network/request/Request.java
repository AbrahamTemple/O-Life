package com.example.myapplication.data.network.request;

import com.example.myapplication.data.model.BlogResponse;
import com.example.myapplication.vo.Response;
import com.example.myapplication.vo.RestResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 封装请求的接口
 */

public interface Request {

    public static String HOST = "https://localhost:8070/";

    @POST("?service=User.getList")
    Observable<Response<List<BlogResponse.Blog>>> getList(@Query("userId") String userId);

}
