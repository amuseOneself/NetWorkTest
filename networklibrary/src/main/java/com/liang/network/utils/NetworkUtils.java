package com.liang.network.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.liang.network.NetworkManager;
import com.liang.network.type.NetType;

public class NetworkUtils {

    public static ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) NetworkManager.getInstance().getApplication().
                getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    //判断网络是否可用
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable() {
        ConnectivityManager mgr = getConnectivityManager();
        if (mgr == null) {
            return false;
        }
        NetworkInfo[] networkIfs = mgr.getAllNetworkInfo();
        if (networkIfs != null) {
            for (NetworkInfo info : networkIfs) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    //WiFi是否连接
    @SuppressLint("MissingPermission")
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager mgr = getConnectivityManager();
        if (mgr == null) {
            return false;
        }
        NetworkInfo wifiNetworkInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo != null) {
            return wifiNetworkInfo.isConnected();
        }
        return false;
    }

    //移动网络是否连接
    @SuppressLint("MissingPermission")
    public static boolean isMobileConnected(Context context) {
        ConnectivityManager mgr = getConnectivityManager();
        if (mgr == null) {
            return false;
        }
        NetworkInfo mobileNetworkInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetworkInfo != null) {
            return mobileNetworkInfo.isConnected();
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static NetType getNetType() {
        ConnectivityManager mgr = getConnectivityManager();
        if (mgr == null) {
            return NetType.NONE;
        }
        NetworkInfo networkInfo = mgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }

        int netType = networkInfo.getType();

        if (netType == ConnectivityManager.TYPE_MOBILE) {
            return NetType.MOBILE;
        } else if (netType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }

        return NetType.NONE;
    }
}
