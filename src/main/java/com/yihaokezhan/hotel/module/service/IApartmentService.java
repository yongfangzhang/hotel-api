package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Apartment;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 公寓表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = Apartment.TABLE_NAME)
public interface IApartmentService extends IBaseService<Apartment> {

}

