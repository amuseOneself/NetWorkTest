package com.liang.networktest;

import android.app.Application;

import com.liang.network.NetworkManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance().init(this);
    }
}
