package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Route;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 路由表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = Route.TABLE_NAME)
public interface IRouteService extends IBaseService<Route> {

}

