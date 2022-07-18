package com.example.wanapplication.qa;

import android.content.Context;

import com.example.wanapplication.bean.home.ArticleBean;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QAPresenter implements QAContact.IQAPresenter {
    private final QAContact.IQAView qaView;
    private QAContact.IQAModel qaModel;
    public QAPresenter(Context context, QAContact.IQAView qaView) {
        this.qaView = qaView;
        qaModel = new QAModel(context);
    }

    @Override
    public void getQA(int page) {
        qaModel.getQA(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArticleBean>() {
                    @Override
                    public void accept(ArticleBean articleBean) throws Throwable {
                        if (articleBean.getErrorCode() == 0){
                            qaView.getSuccess(articleBean.getData().getDatas());
                        }
                    }
                });
    }

    @Override
    public void loadMoreQA(int page) {
        qaModel.getQA(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArticleBean>() {
                    @Override
                    public void accept(ArticleBean articleBean) throws Throwable {
                        if (articleBean.getErrorCode() == 0){
                            qaView.getLoadMoreSuccess(articleBean.getData().getDatas());
                        }
                    }
                });
    }
}
