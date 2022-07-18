package com.example.wanapplication.home

import android.content.Context
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


class HomePresenter(val context: Context,val homeView: HomeContract.IHomeView):HomeContract.IHomePresenter {
    private var iHomeModel = HomeModel(context)

    override fun getBanners() {
        iHomeModel.getBanners().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                homeView.getBannerSuccess(it.data)
            }
    }

    override fun getArticles(currPage: Int) {
        iHomeModel.getArticles(currPage).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                homeView.getArticlesSuccess(it.data)
            }
    }

    override fun getLoadMoreArticles(currPage: Int) {
        iHomeModel.getArticles(currPage).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                homeView.getLoadMoreArticlesSuccess(it.data)
            }
    }

    override fun getTopArticles() {
        iHomeModel.getTopArticles().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                homeView.getTopArticleSuccess(it.data)
            }
    }
}