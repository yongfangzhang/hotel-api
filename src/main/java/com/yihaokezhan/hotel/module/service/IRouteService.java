package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Route;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 路由表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@CacheConfig(cacheNames = "Route")
public interface IRouteService extends IBaseService<Route> {
}
