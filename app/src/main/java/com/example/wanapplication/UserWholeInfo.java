package com.example.wanapplication;

import com.example.wanapplication.bean.CollectArticleInfo;
import com.example.wanapplication.bean.UserInfo;
import com.example.wanapplication.bean.mine.CoinRankBean;

public class UserWholeInfo {
    private CoinRankBean coinInfo;
    private CollectArticleInfo collectArticleInfo;
    private UserInfo userInfo;
    public void setCoinInfo(CoinRankBean coinInfo) {
        this.coinInfo = coinInfo;
    }
    public CoinRankBean getCoinInfo() {
        return coinInfo;
    }

    public void setCollectArticleInfo(CollectArticleInfo collectArticleInfo) {
        this.collectArticleInfo = collectArticleInfo;
    }
    public CollectArticleInfo getCollectArticleInfo() {
        return collectArticleInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }
}
