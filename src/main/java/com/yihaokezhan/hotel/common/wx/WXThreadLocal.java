package com.yihaokezhan.hotel.common.wx;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class WXThreadLocal {

    private static final ThreadLocal<String> contextOpenId = new ThreadLocal<>();

    public static void setOpenId(String openId) {
        contextOpenId.set(openId);
    }

    public static String getOpenId() {
        return contextOpenId.get();
    }

    public static void clearTenant() {
        contextOpenId.remove();
    }
}
