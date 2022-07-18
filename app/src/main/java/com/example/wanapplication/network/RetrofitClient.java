package com.example.wanapplication.network;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static volatile RetrofitClient mInstance;
    private static final String BASE_URL = "https://www.wanandroid.com/";
    private Retrofit retrofit;

    private final List<Cookie> cookieStore = new ArrayList<>();

    private RetrofitClient() {
    }

    public static RetrofitClient getInstance(){
        if (mInstance == null){
            synchronized (RetrofitClient.class){
                if (mInstance == null){
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    public <T>T getService(Class<T> cls, Context context){
        return getRetrofit(context).create(cls);
    }

    private synchronized Retrofit getRetrofit(Context context) {
        if (retrofit == null){
            ClearableCookieJar cookieJar =
                    new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));

            OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).build();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
