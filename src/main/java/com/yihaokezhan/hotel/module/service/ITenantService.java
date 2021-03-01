package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Tenant;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 租户表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = Tenant.TABLE_NAME)
public interface ITenantService extends IBaseService<Tenant> {

}

