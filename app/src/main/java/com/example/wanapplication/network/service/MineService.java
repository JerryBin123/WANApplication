package com.example.wanapplication.network.service;

import com.example.wanapplication.UserWholeInfo;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.MineShareBean;
import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.mine.CoinData;
import com.example.wanapplication.bean.mine.CoinListData;
import com.example.wanapplication.bean.mine.CoinRankBean;
import com.example.wanapplication.bean.mine.CollectArticleBean;
import com.example.wanapplication.bean.mine.CollectArticleData;
import com.example.wanapplication.bean.mine.CollectWebData;

import org.intellij.lang.annotations.Flow;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MineService {

    @GET("/user/lg/userinfo/json")
    Flowable<BaseBean<UserWholeInfo>> getUserInfo();

    @GET("lg/coin/userinfo/json")
    Flowable<BaseBean<CoinRankBean>> getCoinRecord();

    @GET("/lg/coin/list/{currPage}/json")
    Flowable<BaseBean<CoinData>> getCoinList(@Path("currPage")int currPage);

    @GET("coin/rank/{currPage}/json")
    Flowable<BaseBean<CoinListData>> getCoinRankList(@Path("currPage")int currPage);

    @GET("user/lg/private_articles/{currPage}/json")
    Flowable<BaseBean<MineShareBean>> getMineShareArticleList(@Path("currPage") int currPage);

    @POST("lg/user_article/add/json")
    @FormUrlEncoded
    Flowable<BaseBean> shareArticle(@Field("title")String title, @Field("link")String link);

    @POST("lg/user_article/delete/{id}/json")
    Flowable<BaseBean> deleteArticle(@Path("id")int id);

    @GET("lg/collect/list/{page}/json")
    Flowable<BaseBean<CollectArticleBean>> getCollectArticle(@Path("page")int page);

    @POST("lg/collect/{id}/json")
    Flowable<BaseBean> collectArticle(@Path("id") int id);

    @POST("lg/uncollect_originId/{id}/json")
    Flowable<BaseBean> unCollectArticle(@Path("id") int id);

    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Flowable<BaseBean> unCollectPageArticle(@Path("id") int id, @Field("originId") int originId);

    @POST("lg/collect/add/json")
    @FormUrlEncoded
    Flowable<BaseBean<CollectArticleData>> collectOutsideArticle(@Field("title")String title, @Field("author")String author
    , @Field("link")String link);

    @GET("lg/collect/usertools/json")
    Flowable<BaseBean<List<CollectWebData>>> getCollectWeb();

    @POST("lg/collect/updatetool/json")
    @FormUrlEncoded
    Flowable<BaseBean<CollectWebData>> editCollectWeb(@Field("id") int id, @Field("name")String name
            ,@Field("link")String link);

    @POST("lg/collect/deletetool/json")
    @FormUrlEncoded
    Flowable<BaseBean> deleteCollectWeb(@Field("id")int id);

    @POST("lg/collect/addtool/json")
    @FormUrlEncoded
    Flowable<BaseBean<CollectWebData>> addCollectWeb(@Field("name")String name,@Field("link")String link);

}
