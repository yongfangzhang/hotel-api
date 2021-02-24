package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.RoleRoute;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 角色路由表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@CacheConfig(cacheNames = "RoleRoute")
public interface IRoleRouteService extends IBaseService<RoleRoute> {
}
