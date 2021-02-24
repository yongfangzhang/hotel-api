package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Apartment;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 公寓表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@CacheConfig(cacheNames = "Apartment")
public interface IApartmentService extends IBaseService<Apartment> {

}
