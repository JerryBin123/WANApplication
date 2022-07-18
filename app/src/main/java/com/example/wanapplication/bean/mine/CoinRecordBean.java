package com.example.wanapplication.bean.mine;

public class CoinRecordBean {
    private int coinCount;
    private long date;
    private String desc;
    private long id;
    private String reason;
    private int type;
    private long userId;
    private String userName;

    @Override
    public String toString() {
        return "CoinRecordBean{" +
                "coinCount=" + coinCount +
                ", date=" + date +
                ", desc='" + desc + '\'' +
                ", id=" + id +
                ", reason='" + reason + '\'' +
                ", type=" + type +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }
    public int getCoinCount() {
        return coinCount;
    }

    public void setDate(long date) {
        this.date = date;
    }
    public long getDate() {
        return date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReason() {
        return reason;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
}
