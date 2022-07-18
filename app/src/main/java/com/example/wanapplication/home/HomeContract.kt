package com.example.wanapplication.home

import com.example.wanapplication.bean.BaseBean
import com.example.wanapplication.bean.home.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

interface HomeContract {
    interface IHomePresenter{
        fun getBanners()
        fun getArticles(currPage: Int)
        fun getLoadMoreArticles(currPage: Int)
        fun getTopArticles()
    }
    interface IHomeModel{
        fun getBanners(): Observable<BannerListData>
        fun getArticles(currPage: Int) : Observable<ArticleBean>
        fun getTopArticles(): Flowable<BaseBean<List<Datas>>>
    }
    interface IHomeView{
        fun getBannerSuccess(data: List<BannerData> )
        fun getArticlesSuccess(data:Data)
        fun getLoadMoreArticlesSuccess(data:Data)
        fun getTopArticleSuccess(data : List<Datas>)
    }
}