package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.RoleRoute;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 角色路由表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = RoleRoute.TABLE_NAME)
public interface IRoleRouteService extends IBaseService<RoleRoute> {

}

