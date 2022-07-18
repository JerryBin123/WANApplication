package com.example.wanapplication.network.service

import com.example.wanapplication.bean.home.ArticleBean
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface NewQAService {

    @GET("wenda/list/{currPage}/json ")
    fun getQA(@Path("currPage")currPage:Int) : Observable<ArticleBean>
}