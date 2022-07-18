/**
 * Copyright 2022 bejson.com
 */
package com.example.wanapplication.bean;

/**
 * Auto-generated: 2022-05-13 14:9:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BaseBean<T> {

    private T data;
    private int errorCode;
    private String errorMsg;
    public void setData(T data) {
        this.data = data;
    }
    public T getData() {
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