package com.example.demojwt.util;

/**
 * @Author: SYA
 * @Date: 2023/4/19
 * @Version: V1.0
 * @Description:
 */
public class TokenContext {

    private static final ThreadLocal<String> TENANT_KEY = new ThreadLocal<String>();

    public static void setToken(String tenantKey) {
        TENANT_KEY.set(tenantKey);
    }

    public static String getToken() {
        return TENANT_KEY.get();
    }

    public static void remove() {
        TENANT_KEY.remove();
    }
}
