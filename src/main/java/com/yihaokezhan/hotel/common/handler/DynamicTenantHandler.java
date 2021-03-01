package com.yihaokezhan.hotel.common.handler;

import com.yihaokezhan.hotel.common.utils.Constant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicTenantHandler {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置租户UUID
    public static void setTenant(String tenant) {
        log.info("set tenant {}", tenant);
        contextHolder.set(tenant);
    }

    // 获取租户UUID
    public static String getTenant() {
        return contextHolder.get();
    }

    // 清空租户UUID
    public static void clearTenant() {
        contextHolder.remove();
    }

    public static boolean isRootTenant() {
        return Constant.ROOT_TENANT.equals(getTenant());
    }
}
