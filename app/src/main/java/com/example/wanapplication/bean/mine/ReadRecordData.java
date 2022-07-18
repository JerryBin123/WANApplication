package com.example.wanapplication.bean.mine;

public class ReadRecordData {
    private int id;
    private String title;
    private String date;
    private String url;

    public ReadRecordData(int id, String title, String date, String url) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
