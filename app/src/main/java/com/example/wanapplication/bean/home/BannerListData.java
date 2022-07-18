/**
 * Copyright 2022 bejson.com
 */
package com.example.wanapplication.bean.home;
import java.util.List;

/**
 * Auto-generated: 2022-05-16 11:0:51
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BannerListData {

    private List<BannerData> data;
    private int errorCode;
    private String errorMsg;
    public void setData(List<BannerData> data) {
        this.data = data;
    }
    public List<BannerData> getData() {
        return data;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

}