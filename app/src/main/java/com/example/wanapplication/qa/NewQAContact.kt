package com.example.wanapplication.qa

import com.example.wanapplication.bean.home.ArticleBean
import com.example.wanapplication.bean.home.Datas
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

interface NewQAContact {
    interface IQAPresenter{
        fun getQA(page: Int)
    }
    interface IQAModel{
        fun getQA(page:Int) : Observable<ArticleBean>
    }
    interface IQAView{
        fun getSuccess(datas: List<Datas>)
    }
}