package com.example.wanapplication.loginregister;

import com.example.wanapplication.bean.Data;

public class LoginInformation {
    private Data data;
    private static volatile LoginInformation mInstance;
    private LoginInformation() {
        data = new Data();
    }

    public static LoginInformation getInstance(){
        if (mInstance == null){
            synchronized (LoginInformation.class){
                if (mInstance == null){
                    mInstance = new LoginInformation();
                }
            }
        }
        return mInstance;
    }

    public void setLoginInformation(Data data){
        this.data = data;
    }

    public Data getLoginInformation(){
        return data;
    }
}
