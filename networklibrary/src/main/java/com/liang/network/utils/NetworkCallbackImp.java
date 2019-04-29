package com.liang.network.utils;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import com.liang.network.NetworkBus;
import com.liang.network.type.NetType;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImp extends ConnectivityManager.NetworkCallback {

    private NetType mNetType;

    public NetworkCallbackImp() {
        mNetType = null;
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        NetType netType = NetType.NONE;
        if (netType != mNetType) {
            mNetType = netType;
            NetworkBus.post(mNetType);
        }
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        NetType netType = null;
        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            netType = NetType.WIFI;
        }

        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
            netType = NetType.MOBILE;
        }

        if (netType != mNetType) {
            mNetType = netType;
            NetworkBus.post(mNetType);
        }
    }
}
