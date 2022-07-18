package com.example.wanapplication.network

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cookie
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NewRetrofitClient {
    val baseURL = "https://www.wanandroid.com/"
    var cookieStore = ArrayList<Cookie>()

    fun <T> getSerivce(cls:Class<T>,context: Context) = getRetrofit(context).create(cls)


    private fun getRetrofit(context: Context):Retrofit{
        var cookieJar = PersistentCookieJar(SetCookieCache(),SharedPrefsCookiePersistor(context))

        var okHttpClient =OkHttpClient.Builder().cookieJar(cookieJar).build()

        var retrofit =Retrofit.Builder().baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        return retrofit
    }
}