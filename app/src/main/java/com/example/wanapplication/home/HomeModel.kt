package com.example.wanapplication.home

import android.content.Context
import com.example.wanapplication.bean.BaseBean
import com.example.wanapplication.bean.home.ArticleBean
import com.example.wanapplication.bean.home.BannerListData
import com.example.wanapplication.bean.home.Datas
import com.example.wanapplication.network.NewRetrofitClient
import com.example.wanapplication.network.RetrofitClient
import com.example.wanapplication.network.service.HomeService
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

class HomeModel(val context: Context):HomeContract.IHomeModel {
    override fun getBanners(): Observable<BannerListData> {
        return NewRetrofitClient.getSerivce(HomeService::class.java,context).banners
    }

    override fun getArticles(currPage: Int): Observable<ArticleBean> {
        return NewRetrofitClient.getSerivce(HomeService::class.java,context).getArticle(currPage)
    }

    override fun getTopArticles(): Flowable<BaseBean<List<Datas>>> {
        return NewRetrofitClient.getSerivce(HomeService::class.java,context).topArticle
    }
}