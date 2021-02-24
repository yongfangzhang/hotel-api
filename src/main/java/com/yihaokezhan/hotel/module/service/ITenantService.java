package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Tenant;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@CacheConfig(cacheNames = "Tenant")
public interface ITenantService extends IBaseService<Tenant> {
}
