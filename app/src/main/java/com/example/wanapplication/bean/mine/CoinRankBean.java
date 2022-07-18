package com.example.wanapplication.bean.mine;

public class CoinRankBean {
    private int coinCount;
    private int level;
    private String nickname;
    private String rank;
    private long userId;
    private String username;

    @Override
    public String toString() {
        return "CoinRecordBean{" +
                "coinCount=" + coinCount +
                ", level=" + level +
                ", nickname='" + nickname + '\'' +
                ", rank='" + rank + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }
    public int getCoinCount() {
        return coinCount;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
    public String getRank() {
        return rank;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
}
