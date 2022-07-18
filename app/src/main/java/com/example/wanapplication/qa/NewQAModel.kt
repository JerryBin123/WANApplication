package com.example.wanapplication.qa

import android.content.Context
import com.example.wanapplication.bean.home.ArticleBean
import com.example.wanapplication.network.NewRetrofitClient
import com.example.wanapplication.network.service.NewQAService
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

class NewQAModel(val context: Context):NewQAContact.IQAModel {

    override fun getQA(page: Int): Observable<ArticleBean> = NewRetrofitClient
        .getSerivce(NewQAService::class.java,context).getQA(page)


}