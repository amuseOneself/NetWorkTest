package com.liang.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.liang.network.annotation.Network;
import com.liang.network.bean.MethodManager;
import com.liang.network.type.NetType;
import com.liang.network.utils.Constants;
import com.liang.network.utils.NetworkUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;

    public NetStateReceiver() {
        this.netType = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            NetType nType = NetworkUtils.getNetType();
            if (netType != nType) {
                netType = nType;
                NetworkBus.post(netType);
            }
        }
    }

    public void registerObserver(Object object) {
        List<MethodManager> methodManagers = NetworkManager.getInstance().getMethodManagers().get(object);
        if (methodManagers == null) {
            methodManagers = findAnnotationMethods(object);
            NetworkManager.getInstance().getMethodManagers().put(object, methodManagers);
        }
    }

    private List<MethodManager> findAnnotationMethods(Object object) {
        List<MethodManager> methodManagers = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            Network network = method.getAnnotation(Network.class);
            if (network == null) {
                continue;
            }

            Type returnType = method.getGenericReturnType();
            if (!"void".equals(returnType.toString())) {
                throw new RuntimeException("method's returnType must be void ！");
            }

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException("method's parameterTypes length must be 1");
            }

            MethodManager methodManager = new MethodManager(parameterTypes[0], method);
            methodManagers.add(methodManager);
        }

        return methodManagers;
    }

    public void unRegisterObserver(Object object) {
        if (!NetworkManager.getInstance().getMethodManagers().isEmpty()) {
            NetworkManager.getInstance().getMethodManagers().remove(object);
        }
    }

    public void unRegisterAllObserver() {
        if (!NetworkManager.getInstance().getMethodManagers().isEmpty()) {
            NetworkManager.getInstance().getMethodManagers().clear();
        }
        NetworkManager.getInstance().getApplication().unregisterReceiver(this);
    }
}
