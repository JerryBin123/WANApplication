package com.example.wanapplication.network.service;

import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.home.BannerData;
import com.example.wanapplication.bean.home.BannerListData;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.bean.home.HotWordData;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HomeService {

    @GET("banner/json")
    Observable<BannerListData> getBanners();

    @GET("article/list/{currPage}/json")
    Observable<ArticleBean> getArticle(@Path("currPage")int currPage);

    @GET("article/top/json")
    Flowable<BaseBean<List<Datas>>> getTopArticle();

    @GET("/hotkey/json")
    Flowable<BaseBean<List<HotWordData>>> getHotWord();

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Flowable<ArticleBean> getSearchArticle(@Path("page")int page, @Field("k")String k);
}
