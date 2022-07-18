package com.example.wanapplication.network.service;

import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.Data;

import java.util.Observable;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginService {

    @POST("user/login")
    @FormUrlEncoded
    Flowable<BaseBean<Data>> login(@Field("username") String username , @Field("password") String pwd);

    @GET("user/logout/json")
    Flowable<BaseBean<Data>> logout();

    @POST("user/register")
    @FormUrlEncoded
    Flowable<BaseBean<Data>> register(@Field("username")String username, @Field("password")String password
            ,@Field("repassword")String repassword);

}
