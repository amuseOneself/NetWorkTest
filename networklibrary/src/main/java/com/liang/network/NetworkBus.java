package com.liang.network;

import com.liang.network.bean.MethodManager;
import com.liang.network.type.NetType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class NetworkBus {
    public static void post(NetType netType) {
        Set<Object> keys = NetworkManager.getInstance().getMethodManagers().keySet();
        for (Object key : keys) {
            List<MethodManager> methodManagers = NetworkManager.getInstance().getMethodManagers().get(key);
            if (methodManagers != null) {
                for (MethodManager manager : methodManagers) {
                    if (manager.getType().isAssignableFrom(netType.getClass())) {
                        invoke(manager, key, netType);
                    }
                }
            }
        }
    }

    private static void invoke(MethodManager manager, Object object, NetType netType) {
        Method method = manager.getMethod();
        try {
            method.invoke(object, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
