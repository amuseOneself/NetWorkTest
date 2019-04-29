package com.liang.network.bean;


import java.lang.reflect.Method;

public class MethodManager {
    private Class<?> type;
    private Method method;

    public MethodManager(Class<?> type, Method method) {
        this.type = type;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public Method getMethod() {
        return method;
    }
}
