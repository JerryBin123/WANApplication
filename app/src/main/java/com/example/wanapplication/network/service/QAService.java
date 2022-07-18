package com.example.wanapplication.network.service;

import com.example.wanapplication.bean.home.ArticleBean;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QAService {

    @GET("wenda/list/{currPage}/json ")
    Flowable<ArticleBean> getQA(@Path("currPage") int currPage);

}
