package com.example.wanapplication.bean;

import com.example.wanapplication.bean.home.Data;
import com.example.wanapplication.bean.mine.CoinRankBean;

public class MineShareBean {
    private CoinRankBean coinInfo;
    private Data shareArticles;

    @Override
    public String toString() {
        return "MineShareBean{" +
                "coinRankBean=" + coinInfo +
                ", data=" + shareArticles +
                '}';
    }

    public CoinRankBean getCoinRankBean() {
        return coinInfo;
    }

    public void setCoinRankBean(CoinRankBean coinInfo) {
        this.coinInfo = coinInfo;
    }

    public Data getData() {
        return shareArticles;
    }

    public void setData(Data data) {
        this.shareArticles = data;
    }
}
