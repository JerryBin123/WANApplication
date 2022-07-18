package com.example.wanapplication.network.service;

import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.knowledge.KnowledgeData;
import com.example.wanapplication.bean.knowledge.NavigationBean;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KnowledgeService {

    @GET("tree/json")
    Flowable<BaseBean<List<KnowledgeData>>> getKnowledge();

    @GET("navi/json")
    Flowable<BaseBean<List<NavigationBean>>> getNavi();

    @GET("article/list/{currPage}/json")
    Flowable<ArticleBean> getArticle(@Path("currPage") int currPage, @Query("cid") int cid);
}
