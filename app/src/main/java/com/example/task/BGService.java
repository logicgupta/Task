package com.example.task;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;


public class BGService extends Service {

    private String TAG="BSGService";

    public BGService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    public void sendData(String s){
        EventBus.getDefault().post(new EventMessage(s+"Hello"));
    }
}

