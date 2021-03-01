package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.RoomPrice;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 房间价格 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = RoomPrice.TABLE_NAME)
public interface IRoomPriceService extends IBaseService<RoomPrice> {

}

