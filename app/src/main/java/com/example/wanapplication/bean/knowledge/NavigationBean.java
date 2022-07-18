package com.example.wanapplication.bean.knowledge;

import com.example.wanapplication.bean.home.Datas;

import java.util.List;

public class NavigationBean {
    private List<Datas> articles;
    private int cid;
    private String name;
    public void setArticles(List<Datas> articles) {
        this.articles = articles;
    }
    public List<Datas> getArticles() {
        return articles;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
    public int getCid() {
        return cid;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
