package com.example.wanapplication.qa

import android.content.Context
import androidx.annotation.MainThread
import com.example.wanapplication.bean.home.ArticleBean
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.function.Consumer

class NewQAPresenter(
    val context: Context,
    val newQAView:NewQAContact.IQAView):NewQAContact.IQAPresenter {

    private val newQAModel = NewQAModel(context)

    override fun getQA(page: Int) {
        newQAModel.getQA(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                if (it.errorCode == 0){
                    newQAView.getSuccess(it.data.datas)
                }
            }
    }
}

