package com.example.wanapplication.qa;

import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.home.Datas;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface QAContact {
    interface IQAPresenter{
        void getQA(int page);
        void loadMoreQA(int page);
    }
    interface IQAModel{
        Flowable<ArticleBean> getQA(int page);
    }
    interface IQAView{
        void getSuccess(List<Datas> datas);
        void getLoadMoreSuccess(List<Datas> datas);
        void getError(Throwable throwable);
    }
}
