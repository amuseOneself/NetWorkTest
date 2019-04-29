package com.liang.networktest;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.liang.network.NetworkManager;
import com.liang.network.annotation.Network;
import com.liang.network.type.NetType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkManager.getInstance().registerObserver(this);
    }

    @Network
    public void network(NetType netType) {
        switch (netType) {
            case WIFI:
                Log.e("MainActivity", "network 已连接到WIFI： " + netType);
                break;
            case MOBILE:
                Log.e("MainActivity", "network 已连接到MOBILE： " + netType);
                break;
            case NONE:
                Log.e("MainActivity", "network 已连接断开： " + netType);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().unRegisterObserver(this);
    }
}
