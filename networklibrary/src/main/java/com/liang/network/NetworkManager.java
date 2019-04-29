package com.liang.network;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.liang.network.bean.MethodManager;
import com.liang.network.utils.Constants;
import com.liang.network.utils.NetworkCallbackImp;
import com.liang.network.utils.NetworkUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkManager {
    private static volatile NetworkManager instance;
    private NetStateReceiver mNetStateReceiver;
    private Application mApplication;
    private Map<Object, List<MethodManager>> mMethodManagers;

    private NetworkManager() {
        this.mMethodManagers = new HashMap<>();
        mNetStateReceiver = new NetStateReceiver();
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    @SuppressLint("MissingPermission")
    public void init(Application application) {
        mApplication = application;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager.NetworkCallback networkCallback =
                    new NetworkCallbackImp();
            ConnectivityManager mgr = NetworkUtils.getConnectivityManager();
            if (mgr != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mgr.registerDefaultNetworkCallback(networkCallback);
                } else {
                    NetworkRequest request = new NetworkRequest.Builder().build();
                    mgr.registerNetworkCallback(request, networkCallback);
                }
            }
        } else {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
            mApplication.registerReceiver(mNetStateReceiver, intentFilter);
        }
    }

    public void registerObserver(Object object) {
        mNetStateReceiver.registerObserver(object);
    }

    public void unRegisterObserver(Object object) {
        mNetStateReceiver.unRegisterObserver(object);
    }

    public void unRegisterAllObserver() {
        mNetStateReceiver.unRegisterAllObserver();
    }

    public Application getApplication() {
        if (mApplication == null) {
            throw new NullPointerException("NetworkManager还未执行init()方法!");
        }
        return mApplication;
    }

    public Map<Object, List<MethodManager>> getMethodManagers() {
        return mMethodManagers;
    }
}
