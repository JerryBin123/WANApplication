package com.example.wanapplication.qa;

import android.content.Context;

import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.NewQAService;
import com.example.wanapplication.network.service.QAService;

import io.reactivex.rxjava3.core.Flowable;

public class QAModel implements QAContact.IQAModel {

    private Context context;

    public QAModel(Context context) {
        this.context = context;
    }

    @Override
    public Flowable<ArticleBean> getQA(int page) {
        return RetrofitClient.getInstance().getService(QAService.class, context).getQA(page);
    }
}
