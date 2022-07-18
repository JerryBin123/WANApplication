package com.example.wanapplication.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TopChecked extends BroadcastReceiver {

    private OnTopListener onTopListener;

    public void setOnTopListener(OnTopListener onTopListener){
        this.onTopListener = onTopListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean top = intent.getBooleanExtra("top", true);
        if (onTopListener != null){
            onTopListener.onTop(top);
        }
    }

    interface OnTopListener{

        void onTop(Boolean top);
    }
}
