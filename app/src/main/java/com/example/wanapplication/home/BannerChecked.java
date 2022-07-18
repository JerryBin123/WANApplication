package com.example.wanapplication.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BannerChecked extends BroadcastReceiver {

    private OnBannerListener onBannerListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("测试", "onReceive: "+ intent.getBooleanExtra("check",false));
        boolean open = intent.getBooleanExtra("check", true);
        if (onBannerListener != null){
            onBannerListener.onBanner(open);
        }

    }
    public void setOnBannerListener(OnBannerListener onBannerListener){
        this.onBannerListener = onBannerListener;
    }



    interface OnBannerListener{
        void onBanner(Boolean open);
    }


}
